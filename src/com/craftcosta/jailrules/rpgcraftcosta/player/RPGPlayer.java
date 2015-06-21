/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGEconomy;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayer {

    //Player atributes
    private String name;
    private String password;
    private String email;
    private UUID uuid;
    private RPGEconomy econ;
    private Player player;
    private String guild;
    private String playerClass;
    private String party;
    private boolean firstLogin;
    private boolean move;
    private int actualLevel;
    private long actualExp;
    private long actualHealth;
    private long actualMana;
    private long maxMana;
    private long maxHealth;
    //rpg attributes
    private int availablePoints;
    private int constitutionP;
    private int dexteryP;
    private int intelligenceP;
    private int strengthP;
    //rpg dependant attributes
    private float physicalAttack;
    private float physicalDefense;
    private float magicAttack;
    private float magicDefense;
    private float physicalHitRate;
    private float magicHitRate;
    private float physicalEvasion;
    private float magicEvasion;
    private float critical;
    private float deadly;

    public RPGPlayer(Player p) {
        this.player = p;
        if (RPGPlayerUtils.existsPlayerFile(p)) {
            //cargar player
            loadPlayerData();
        } else {
            //crear player nuevo
            createRPGPlayer();
            this.saveRPGPlayer();
        }

    }

    private void createRPGPlayer() {
        this.name = this.player.getName();
        this.uuid = this.player.getUniqueId();
        this.password = "";
        this.firstLogin = true;
        this.move = false;
        this.econ = new RPGEconomy();
        this.guild = "";
        this.playerClass = "";
        this.party = "";
        setRpgPlayerStatistics();
    }

    private void setRpgPlayerStatistics() {
        //general
        this.actualLevel = 0;
        this.actualExp = 0;
        this.actualHealth = 20;
        this.actualMana = 0;
        this.maxMana = 0;
        this.maxHealth = 20;
        //rpg attributes
        this.availablePoints = 0;
        this.constitutionP = 0;
        this.dexteryP = 0;
        this.intelligenceP = 0;
        this.strengthP = 0;
        //rpg dependant attributes
        this.physicalAttack = 0;
        this.physicalDefense = 0;
        this.magicAttack = 0;
        this.magicDefense = 0;
        this.physicalHitRate = 0;
        this.magicHitRate = 0;
        this.physicalEvasion = 0;
        this.magicEvasion = 0;
        this.critical = 0;
        this.deadly = 0;
    }

    public void saveRPGPlayer() {
        File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", getPlayer().getUniqueId().toString()));
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        FileConfiguration section = YamlConfiguration.loadConfiguration(playerFile);
        section.set("name", this.player.getName());
        section.set("firstlogin", this.firstLogin);
        section.set("move", this.move);
        section.set("money", this.econ.getMoney());
        section.set("experience", this.actualExp);
        section.set("level", this.actualExp);
        section.set("password", this.password);
        section.set("guild", this.guild);
        section.set("party", this.party);
        section.set("class", this.playerClass);
        section.set("actualHealth", this.actualHealth);
        section.set("actualMana", this.actualMana);
        section.set("maxHealth", this.maxHealth);
        section.set("maxMana", this.maxMana);
        section.set("ap", this.availablePoints);
        section.set("constP", this.constitutionP);
        section.set("dextP", this.dexteryP);
        section.set("intelP", this.intelligenceP);
        section.set("strghP", this.strengthP);
        section.set("magicattack", this.magicAttack);
        section.set("magicdefense", this.magicDefense);
        section.set("magicevasion", this.magicEvasion);
        section.set("magichitrate", this.magicHitRate);
        section.set("physicalattack", this.physicalAttack);
        section.set("physicaldefense", this.physicalDefense);
        section.set("physicalevasion", this.physicalEvasion);
        section.set("physicalhitrate", this.physicalHitRate);
        section.set("critical", this.critical);
        section.set("deadly", this.deadly);
        try {
            section.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public RPGPlayer getOrCreateRPGPlayer(Player p) {
        RPGPlayer rpgp = null;
        File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", this.getPlayer().getUniqueId().toString()));
        FileConfiguration config;
        if (!playerFile.exists()) {
            //si player no existe fichero crear nuevo
            rpgp = new RPGPlayer(p);
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.dataFolder + "players.yml"));

        } else {
            //si player existe fichero cargar player
            config = YamlConfiguration.loadConfiguration(playerFile);
        }

        return rpgp;
    }

    private void loadPlayerData() {
        //Recupera la informacion del player actual
        File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", this.getPlayer().getUniqueId().toString()));
        FileConfiguration config;
        if (!playerFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.dataFolder + "players.yml"));
        } else {
            config = YamlConfiguration.loadConfiguration(playerFile);
        }
        //Si el player existe entonces empezamos a cargar los datos del player
        if (this.getPlayer().getName() != null && !this.player.getName().equals("")
                && (playerFile.exists() || config.isConfigurationSection(this.player.getName()))) {
            ConfigurationSection section;
            if (!playerFile.exists()) {
                section = config.getConfigurationSection(this.player.getName());
            } else {
                section = config;
            }
            //Cargar estado actual
            this.econ = new RPGEconomy(section.getLong("money"));
            this.actualLevel = section.getInt("level");
            this.actualExp = section.getLong("experience");
            this.uuid = this.player.getUniqueId();
            this.move = section.getBoolean("move");
            this.firstLogin = section.getBoolean("firstlogin");
            this.password = section.getString("password");
            this.guild = section.getString("guild");
            this.party = section.getString("party");
            this.playerClass = section.getString("class");
            this.actualHealth = section.getLong("actualHealth");
            this.player.setHealth(actualHealth);
            this.actualMana = section.getLong("actualMana");
            this.maxHealth = section.getLong("maxHealth");
            this.maxMana = section.getLong("maxMana");
            this.availablePoints = section.getInt("ap");
            //Cargar atributos de player
            this.constitutionP = section.getInt("constP");
            this.dexteryP = section.getInt("dextP");
            this.intelligenceP = section.getInt("intelP");
            this.strengthP = section.getInt("strgthP");
            //Cargar estadisticas
            //Magico
            this.magicAttack = section.getInt("magicattack");
            this.magicDefense = section.getInt("magicdefense");
            this.magicEvasion = section.getInt("magicevasion");
            this.magicHitRate = section.getInt("magichitrate");
            //Fisico
            this.physicalAttack = section.getInt("physicalattack");
            this.physicalDefense = section.getInt("physicaldefense");
            this.physicalEvasion = section.getInt("physicalevasion");
            this.physicalHitRate = section.getInt("physicalhitrate");
            //Especial
            this.critical = section.getInt("critical");
            this.deadly = section.getInt("deadly");
        } else {

        }
    }

    public boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstlogin) {
        this.firstLogin = firstlogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public long getActualHealth() {
        return actualHealth;
    }

    public void setActualHealth(long actualHealth) {
        this.actualHealth = actualHealth;
    }

    public long getActualMana() {
        return actualMana;
    }

    public void setActualMana(long actualMana) {
        this.actualMana = actualMana;
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public void setAvailablePoints(int availablePoints) {
        this.availablePoints = availablePoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEcon(RPGEconomy econ) {
        this.econ = econ;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActualLevel(int actualLevel) {
        this.actualLevel = (int) actualLevel;
    }

    public void setActualExp(long actualExp) {
        this.actualExp = actualExp;
    }

    public void setActualhealth(long actualhealth) {
        this.actualHealth = actualhealth;
    }

    public void setActualmana(long actualmana) {
        this.actualMana = actualmana;
    }

    public void setMaxMana(long maxMana) {
        this.maxMana = maxMana;
    }

    public void setMaxHealth(long maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setConstitutionP(int constitutionP) {
        this.constitutionP = constitutionP;
    }

    public void setDexteryP(int dexteryP) {
        this.dexteryP = dexteryP;
    }

    public void setIntelligenceP(int intelligenceP) {
        this.intelligenceP = intelligenceP;
    }

    public void setStrengthP(int strengthP) {
        this.strengthP = strengthP;
    }

    public void setPhysicalAttack(float physicalAttack) {
        this.physicalAttack = physicalAttack;
    }

    public void setPhysicalDefense(float physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public void setMagicAttack(float magicAttack) {
        this.magicAttack = magicAttack;
    }

    public void setMagicDefense(float magicDefense) {
        this.magicDefense = magicDefense;
    }

    public void setPhysicalHitRate(float physicalHitRate) {
        this.physicalHitRate = physicalHitRate;
    }

    public void setMagicHitRate(float MagicHitRate) {
        this.magicHitRate = MagicHitRate;
    }

    public void setPhysicalEvasion(float physicalEvasion) {
        this.physicalEvasion = physicalEvasion;
    }

    public void setMagicEvasion(float magicEvasion) {
        this.magicEvasion = magicEvasion;
    }

    public void setCritical(float critical) {
        this.critical = critical;
    }

    public void setDeadly(float deadly) {
        this.deadly = deadly;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return this.player;
    }

    public RPGEconomy getEcon() {
        return econ;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public long getActualLevel() {
        return actualLevel;
    }

    public long getActualExp() {
        return actualExp;
    }

    public long getActualhealth() {
        return actualHealth;
    }

    public long getActualmana() {
        return actualMana;
    }

    public long getMaxMana() {
        return maxMana;
    }

    public long getMaxHealth() {
        return maxHealth;
    }

    public int getConstitutionP() {
        return constitutionP;
    }

    public int getDexteryP() {
        return dexteryP;
    }

    public int getIntelligenceP() {
        return intelligenceP;
    }

    public int getStrengthP() {
        return strengthP;
    }

    public float getPhysicalAttack() {
        return physicalAttack;
    }

    public float getPhysicalDefense() {
        return physicalDefense;
    }

    public float getMagicAttack() {
        return magicAttack;
    }

    public float getMagicDefense() {
        return magicDefense;
    }

    public float getPhysicalHitRate() {
        return physicalHitRate;
    }

    public float getMagicHitRate() {
        return magicHitRate;
    }

    public float getPhysicalEvasion() {
        return physicalEvasion;
    }

    public float getMagicEvasion() {
        return magicEvasion;
    }

    public float getCritical() {
        return critical;
    }

    public float getDeadly() {
        return deadly;
    }

    public long getPLevel() {
        return this.actualLevel;
    }

    public void setLevel(int l) {
        this.actualLevel = l;
    }

    public boolean hasGuild() {
        return this.guild != null;
    }

    public String getGuild() {
        return this.guild;
    }

    public double distance(Location l) {
        return 0.0;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void allowMove() {
        this.move = true;
    }

    public boolean getMove() {
        return this.move;
    }

    public String getPassword() {
        return password;
    }

    public boolean passCheck(String pass) {
        return pass.equals(getPassword());
    }
}
