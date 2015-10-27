/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
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
public class RPGPlayer /*extends EventObject*/ {

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
    private double actualHealth;
    private double actualMana;
    private double maxMana;
    private double maxHealth;
    //rpg attributes
    private int availablePoints;
    private int constitutionP;
    private int dexteryP;
    private int intelligenceP;
    private int strengthP;
    //rpg dependant attributes
    private double physicalAttack;
    private double finalphysicalAttack;
    private double physicalDefense;
    private double finalphysicalDefense;
    private double physicalHitRate;
    private double finalphysicalHitRate;
    private double physicalEvasion;
    private double finalphysicalEvasion;
    private double critical;
    private double finalcritical;
    private double deadly;

    /**
     * Al añadir el extends EventObject a la clase nos da el error siguiente
     * pero es necesario para poder disparar nuestros propios eventos...
     *
     * @param p
     */
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
        this.finalphysicalAttack = physicalAttack;
        this.physicalDefense = 0;
        this.finalphysicalDefense = physicalDefense;
        this.physicalHitRate = 0;
        this.finalphysicalHitRate = this.physicalHitRate;
        this.physicalEvasion = 0;
        this.finalphysicalEvasion = physicalEvasion;
        this.critical = 0;
        this.deadly = 0;
    }

    /**
     *
     */
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

//    public RPGPlayer getOrCreateRPGPlayer(Player p) {
//        RPGPlayer rpgp = null;
//        File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", this.getPlayer().getUniqueId().toString()));
//        FileConfiguration config;
//        if (!playerFile.exists()) {
//            //si player no existe fichero crear nuevo
//            rpgp = new RPGPlayer(p);
//            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.dataFolder + "players.yml"));
//
//        } else {
//            //si player existe fichero cargar player
//            config = YamlConfiguration.loadConfiguration(playerFile);
//        }
//
//        return rpgp;
//    }
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
            this.actualHealth = section.getDouble("actualHealth");
            this.player.setHealth(actualHealth);
            this.actualMana = section.getDouble("actualMana");
            this.maxHealth = section.getDouble("maxHealth");
            this.player.setMaxHealth(maxHealth);
            this.maxMana = section.getDouble("maxMana");
            this.availablePoints = section.getInt("ap");
            //Cargar atributos de player
            this.constitutionP = section.getInt("constP");
            this.dexteryP = section.getInt("dextP");
            this.intelligenceP = section.getInt("intelP");
            this.strengthP = section.getInt("strgthP");
            //Cargar estadisticas
            //Fisico
            this.physicalAttack = section.getDouble("physicalattack");
            this.physicalDefense = section.getDouble("physicaldefense");
            this.physicalEvasion = section.getDouble("physicalevasion");
            this.physicalHitRate = section.getDouble("physicalhitrate");
            //Especial
            this.critical = section.getDouble("critical");
            this.deadly = section.getDouble("deadly");
        } else {

        }
    }

    void setRPGClass(RPGClass rpgclass) {
        setPlayerClass(rpgclass.getNameClass());
        setActualHealth(rpgclass.getBaseHealth());
        setMaxHealth(rpgclass.getBaseHealth());
        setActualMana(rpgclass.getBaseMana());
        setMaxMana(rpgclass.getBaseMana());
        setPhysicalAttack(rpgclass.getBasePhysicalAttack());
        setPhysicalDefense(rpgclass.getBasePhysicalAttack());
        setPhysicalHitRate(rpgclass.getBasePhysicalHitRate());
        setPhysicalEvasion(rpgclass.getBasePhysicalEvasion());
        setCritical(rpgclass.getBaseCritical());
        setDeadly(rpgclass.getBaseDeadly());
        this.saveRPGPlayer();
    }

    /**
     *
     */
    public void levelUP() {
        //aumentar stats
        RPGClass rpgclass = RPGClassManager.getRPGClass(getPlayerClass());
        setActualHealth(rpgclass.getLvlUpHealth() + getMaxHealth());
        setMaxHealth(rpgclass.getLvlUpHealth() + getMaxHealth());
        setActualMana(rpgclass.getLvlUpMana() + getMaxMana());
        setMaxMana(rpgclass.getLvlUpMana() + getMaxMana());
        setPhysicalAttack(rpgclass.getLvlUpPhysicalAttack() + getPhysicalAttack());
        setPhysicalDefense(rpgclass.getLvlUpPhysicalAttack() + getPhysicalDefense());
        setPhysicalHitRate(rpgclass.getLvlUpPhysicalHitRate() + getPhysicalHitRate());
        setPhysicalEvasion(rpgclass.getLvlUpPhysicalEvasion() + getPhysicalEvasion());
        setCritical(rpgclass.getLvlUpCritical() + getCritical());
        setDeadly(rpgclass.getLvlUpDeadly() + getDeadly());
        //añadir skillpoints

        this.saveRPGPlayer();
    }

    /**
     *
     * @return
     */
    public boolean getFirstLogin() {
        return firstLogin;
    }

    /**
     *
     * @param firstlogin
     */
    public void setFirstLogin(boolean firstlogin) {
        this.firstLogin = firstlogin;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     */
    public boolean isMove() {
        return move;
    }

    /**
     *
     * @param move
     */
    public void setMove(boolean move) {
        this.move = move;
    }

    /**
     *
     * @return
     */
    public double getActualHealth() {
        return actualHealth;
    }

    /**
     *
     * @param actualHealth
     */
    public void setActualHealth(double actualHealth) {
        this.actualHealth = actualHealth;
    }

    /**
     *
     * @return
     */
    public double getActualMana() {
        return actualMana;
    }

    /**
     *
     * @param actualMana
     */
    public void setActualMana(double actualMana) {
        this.actualMana = actualMana;
    }

    /**
     *
     * @return
     */
    public int getAvailablePoints() {
        return availablePoints;
    }

    /**
     *
     * @param availablePoints
     */
    public void setAvailablePoints(int availablePoints) {
        this.availablePoints = availablePoints;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param econ
     */
    public void setEcon(RPGEconomy econ) {
        this.econ = econ;
    }

    /**
     *
     * @param guild
     */
    public void setGuild(String guild) {
        this.guild = guild;
    }

    /**
     *
     * @param playerClass
     */
    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param actualLevel
     */
    public void setActualLevel(int actualLevel) {
        this.actualLevel = actualLevel;
    }

    /**
     *
     * @param actualExp
     */
    public void setActualExp(long actualExp) {
        this.actualExp = actualExp;
    }

    /**
     *
     * @param actualhealth
     */
    public void setActualhealth(double actualhealth) {
        this.actualHealth = actualhealth;
    }

    /**
     *
     * @param actualmana
     */
    public void setActualmana(double actualmana) {
        this.actualMana = actualmana;
    }

    /**
     *
     * @param maxMana
     */
    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    /**
     *
     * @param maxHealth
     */
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     *
     * @param constitutionP
     */
    public void setConstitutionP(int constitutionP) {
        this.constitutionP = constitutionP;
    }

    /**
     *
     * @param dexteryP
     */
    public void setDexteryP(int dexteryP) {
        this.dexteryP = dexteryP;
    }

    /**
     *
     * @param intelligenceP
     */
    public void setIntelligenceP(int intelligenceP) {
        this.intelligenceP = intelligenceP;
    }

    /**
     *
     * @param strengthP
     */
    public void setStrengthP(int strengthP) {
        this.strengthP = strengthP;
    }

    /**
     *
     * @param physicalAttack
     */
    public void setPhysicalAttack(double physicalAttack) {
        this.physicalAttack = physicalAttack;
    }

    /**
     *
     * @param physicalDefense
     */
    public void setPhysicalDefense(double physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    /**
     *
     * @param physicalHitRate
     */
    public void setPhysicalHitRate(double physicalHitRate) {
        this.physicalHitRate = physicalHitRate;
    }

    /**
     *
     * @param physicalEvasion
     */
    public void setPhysicalEvasion(double physicalEvasion) {
        this.physicalEvasion = physicalEvasion;
    }

    /**
     *
     * @param critical
     */
    public void setCritical(double critical) {
        this.critical = critical;
    }

    /**
     *
     * @param deadly
     */
    public void setDeadly(double deadly) {
        this.deadly = deadly;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     *
     * @return
     */
    public RPGEconomy getEcon() {
        return econ;
    }

    /**
     *
     * @return
     */
    public String getPlayerClass() {
        return playerClass;
    }

    /**
     *
     * @return
     */
    public long getActualLevel() {
        return actualLevel;
    }

    /**
     *
     * @return
     */
    public long getActualExp() {
        return actualExp;
    }

    /**
     *
     * @return
     */
    public double getActualhealth() {
        return actualHealth;
    }

    /**
     *
     * @return
     */
    public double getActualmana() {
        return actualMana;
    }

    /**
     *
     * @return
     */
    public double getMaxMana() {
        return maxMana;
    }

    /**
     *
     * @return
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     *
     * @return
     */
    public int getConstitutionP() {
        return constitutionP;
    }

    /**
     *
     * @return
     */
    public int getDexteryP() {
        return dexteryP;
    }

    /**
     *
     * @return
     */
    public int getIntelligenceP() {
        return intelligenceP;
    }

    /**
     *
     * @return
     */
    public int getStrengthP() {
        return strengthP;
    }

    /**
     *
     * @return
     */
    public double getPhysicalAttack() {
        return physicalAttack;
    }

    /**
     *
     * @return
     */
    public double getPhysicalDefense() {
        return physicalDefense;
    }

    /**
     *
     * @return
     */
    public double getPhysicalHitRate() {
        return physicalHitRate;
    }

    /**
     *
     * @return
     */
    public double getPhysicalEvasion() {
        return physicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getCritical() {
        return critical;
    }

    /**
     *
     * @return
     */
    public double getDeadly() {
        return deadly;
    }

    /**
     *
     * @return
     */
    public long getPLevel() {
        return this.actualLevel;
    }

    /**
     *
     * @param l
     */
    public void setLevel(int l) {
        this.actualLevel = l;
    }

    /**
     *
     * @return
     */
    public boolean hasGuild() {
        return this.guild != null;
    }

    /**
     *
     * @return
     */
    public String getGuild() {
        return this.guild;
    }

    /**
     *
     * @param l
     * @return
     */
    public double distance(Location l) {
        return 0.0;
    }

    /**
     *
     * @return
     */
    public String getParty() {
        return party;
    }

    /**
     *
     * @param party
     */
    public void setParty(String party) {
        this.party = party;
    }

    /**
     *
     */
    public void allowMove() {
        this.move = true;
    }

    /**
     *
     * @return
     */
    public boolean getMove() {
        return this.move;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param pass
     * @return
     */
    public boolean passCheck(String pass) {
        return pass.equals(getPassword());
    }

}
