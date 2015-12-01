/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGEconomy;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore;
import com.craftcosta.jailrules.rpgcraftcosta.quests.RPGQuest;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayer {
    private String name;
    private UUID uuid;
    private Player player;
    private String guild;
    private String playerClass;
    private String party;
    private int slotSelected;
    private boolean setResetRequest;
    private boolean move;
    private boolean partyChat;
    private boolean privateChat;
    private boolean guildChat;
    private boolean marketChat;
    private boolean localChat;
    private boolean globalChat;

    private RPGEconomy econ;

    private int actualLevel;
    private long actualExp;
    private long ap;
    private List<RPGQuest> finishedQuestsList;
    private List<RPGQuest> inProgressQuestsList;

    //rpg attributes
    private int constitutionP;
    private int dexteryP;
    private int intelligenceP;
    private int strengthP;
    //rpg dependant attributes
    private double actualMana;
    private double maxMana;
    private double finalMaxMana;
    
    private double finalMaxHealth;
    private double actualHealth;
    private double maxHealth;
    
    private double physicalAttack;
    private double finalphysicalAttack;
    
    private double physicalDefense;
    private double finalphysicalDefense;
    
    private double physicalHitRate;
    private double finalphysicalHitRate;
    
    private double physicalEvasion;
    private double finalphysicalEvasion;
    
    private double magicalAttack;
    private double finalmagicalAttack;
    
    private double magicalDefense;
    private double finalmagicalDefense;
    
    private double magicalHitRate;
    private double finalmagicalHitRate;
    
    private double magicalEvasion;
    private double finalmagicalEvasion;
    
    private double critical;
    private double finalcritical;

    public RPGPlayer(Player p) {
        this.player = p;
        createRPGPlayer();
    }

    public RPGPlayer(String name, UUID uuid, Player player, String guild, String playerClass, String party, RPGEconomy econ, int actualLevel, long actualExp, long ap, List<RPGQuest> finishedQuests, List<RPGQuest> InProgressQuests, int constitutionP, int dexteryP, int intelligenceP, int strengthP, double actualMana, double maxMana, double actualHealth, double maxHealth, double physicalAttack, double physicalDefense, double physicalHitRate, double physicalEvasion, double magicalAttack, double magicalDefense, double magicalHitRate, double magicalEvasion, double critical) {
        this.name = name;
        this.uuid = uuid;
        this.player = player;
        this.guild = guild;
        this.playerClass = playerClass;
        this.party = party;
        this.econ = econ;
        this.actualLevel = actualLevel;
        this.actualExp = actualExp;
        this.ap = ap;
        this.finishedQuestsList = finishedQuests;
        this.inProgressQuestsList = InProgressQuests;
        this.constitutionP = constitutionP;
        this.dexteryP = dexteryP;
        this.intelligenceP = intelligenceP;
        this.strengthP = strengthP;
        this.actualMana = actualMana;
        this.maxMana = maxMana;
        this.actualHealth = actualHealth;
        this.maxHealth = maxHealth;
        this.physicalAttack = physicalAttack;
        this.physicalDefense = physicalDefense;
        this.physicalHitRate = physicalHitRate;
        this.physicalEvasion = physicalEvasion;
        this.magicalAttack = magicalAttack;
        this.magicalDefense = magicalDefense;
        this.magicalHitRate = magicalHitRate;
        this.magicalEvasion = magicalEvasion;
        this.critical = critical;
        this.privateChat = true;
        this.globalChat = true;
        this.localChat = true;
        this.partyChat = true;
        this.guildChat = true;
        this.marketChat = true;
        this.setResetRequest = false;
        this.slotSelected=0;
        this.move = !this.playerClass.isEmpty();
    }

    private void createRPGPlayer() {
        this.name = this.player.getName();
        this.uuid = this.player.getUniqueId();
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
        this.actualMana = 20;
        this.maxMana = 20;
        this.maxHealth = 20;
        //rpg attributes
        this.ap = 0;
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
        if (this.getPlayer().getName() != null && !this.player.getName().isEmpty()
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
            this.setResetRequest = false;
            this.move = section.getBoolean("move");
            this.guild = section.getString("guild");
            this.party = section.getString("party");
            this.playerClass = section.getString("class");
            this.actualHealth = section.getDouble("actualHealth");
            this.player.setHealth(actualHealth);
            this.actualMana = section.getDouble("actualMana");
            this.maxHealth = section.getDouble("maxHealth");
            //Calcular proporcion de vida max correspondiente a la actual
            
            //this.player.setMaxHealth(maxHealth);
            this.maxMana = section.getDouble("maxMana");
            this.ap = section.getLong("ap");
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
            //Magico
            this.magicalAttack = section.getDouble("magicalattack");
            this.magicalDefense = section.getDouble("magicaldefense");
            this.magicalEvasion = section.getDouble("magicalevasion");
            this.magicalHitRate = section.getDouble("magicalhitrate");
            //Especial
            this.critical = section.getDouble("critical");
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
        setPhysicalDefense(rpgclass.getBasePhysicalDefense());
        setPhysicalHitRate(rpgclass.getBasePhysicalHitRate());
        setPhysicalEvasion(rpgclass.getBasePhysicalEvasion());
        setMagicalAttack(rpgclass.getBaseMagicalAttack());
        setMagicalDefense(rpgclass.getBaseMagicalDefense());
        setMagicalHitRate(rpgclass.getBaseMagicalHitRate());
        setMagicalEvasion(rpgclass.getBaseMagicalEvasion());
        setCritical(rpgclass.getBaseCritical());
    }

    /**
     *
     */
    public void levelUP() {
        //aumentar stats
        RPGClass rpgclass = RPGClassManager.getRPGClass(getPlayerClass());
        setActualHealth(rpgclass.getLvlUpHealth() + getMaxHealth());
        setMaxHealth(rpgclass.getLvlUpHealth() + getMaxHealth());
        setPhysicalAttack(rpgclass.getLvlUpPhysicalAttack() + getPhysicalAttack());
        setPhysicalDefense(rpgclass.getLvlUpPhysicalAttack() + getPhysicalDefense());
        setPhysicalHitRate(rpgclass.getLvlUpPhysicalHitRate() + getPhysicalHitRate());
        setPhysicalEvasion(rpgclass.getLvlUpPhysicalEvasion() + getPhysicalEvasion());
        setCritical(rpgclass.getLvlUpCritical() + getCritical());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;        
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getSlotSelected() {
        return slotSelected;
    }

    public void setSlotSelected(int slotSelected) {
        this.slotSelected = slotSelected;
    }

    public boolean isSetResetRequest() {
        return setResetRequest;
    }

    public void setSetResetRequest(boolean setResetRequest) {
        this.setResetRequest = setResetRequest;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isPartyChat() {
        return partyChat;
    }

    public void setPartyChat(boolean partyChat) {
        this.partyChat = partyChat;
    }

    public boolean isPrivateChat() {
        return privateChat;
    }

    public void setPrivateChat(boolean privateChat) {
        this.privateChat = privateChat;
    }

    public boolean isGuildChat() {
        return guildChat;
    }

    public void setGuildChat(boolean guildChat) {
        this.guildChat = guildChat;
    }

    public boolean isMarketChat() {
        return marketChat;
    }

    public void setMarketChat(boolean marketChat) {
        this.marketChat = marketChat;
    }

    public boolean isLocalChat() {
        return localChat;
    }

    public void setLocalChat(boolean localChat) {
        this.localChat = localChat;
    }

    public boolean isGlobalChat() {
        return globalChat;
    }

    public void setGlobalChat(boolean globalChat) {
        this.globalChat = globalChat;
    }

    public RPGEconomy getEcon() {
        return econ;
    }

    public void setEcon(RPGEconomy econ) {
        this.econ = econ;
    }

    public int getActualLevel() {
        return actualLevel;
    }

    public void setActualLevel(int actualLevel) {
        this.actualLevel = actualLevel;
    }

    public long getActualExp() {
        return actualExp;
    }

    public void setActualExp(long actualExp) {
        this.actualExp = actualExp;
    }

    public long getAp() {
        return ap;
    }

    public void setAp(long ap) {
        this.ap = ap;
    }

    public List<RPGQuest> getFinishedQuestsList() {
        return finishedQuestsList;
    }

    public void setFinishedQuestsList(List<RPGQuest> finishedQuestsList) {
        this.finishedQuestsList = finishedQuestsList;
    }

    public List<RPGQuest> getInProgressQuestsList() {
        return inProgressQuestsList;
    }

    public void setInProgressQuestsList(List<RPGQuest> inProgressQuestsList) {
        this.inProgressQuestsList = inProgressQuestsList;
    }

    public int getConstitutionP() {
        return constitutionP;
    }

    public void setConstitutionP(int constitutionP) {
        this.constitutionP = constitutionP;
    }

    public int getDexteryP() {
        return dexteryP;
    }

    public void setDexteryP(int dexteryP) {
        this.dexteryP = dexteryP;
    }

    public int getIntelligenceP() {
        return intelligenceP;
    }

    public void setIntelligenceP(int intelligenceP) {
        this.intelligenceP = intelligenceP;
    }

    public int getStrengthP() {
        return strengthP;
    }

    public void setStrengthP(int strengthP) {
        this.strengthP = strengthP;
    }

    public double getActualMana() {
        return actualMana;
    }

    public void setActualMana(double actualMana) {
        this.actualMana = actualMana;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public double getFinalMaxMana() {
        return finalMaxMana;
    }

    public void setFinalMaxMana(double finalMaxMana) {
        this.finalMaxMana = finalMaxMana;
    }

    public double getFinalMaxHealth() {
        return finalMaxHealth;
    }

    public void setFinalMaxHealth(double finalMaxHealth) {
        this.finalMaxHealth = finalMaxHealth;
    }

    public double getActualHealth() {
        return actualHealth;
    }

    public void setActualHealth(double actualHealth) {
        this.actualHealth = actualHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getPhysicalAttack() {
        return physicalAttack;
    }

    public void setPhysicalAttack(double physicalAttack) {
        this.physicalAttack = physicalAttack;
    }

    public double getFinalphysicalAttack() {
        return finalphysicalAttack;
    }

    public void setFinalphysicalAttack(double finalphysicalAttack) {
        this.finalphysicalAttack = finalphysicalAttack;
    }

    public double getPhysicalDefense() {
        return physicalDefense;
    }

    public void setPhysicalDefense(double physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public double getFinalphysicalDefense() {
        return finalphysicalDefense;
    }

    public void setFinalphysicalDefense(double finalphysicalDefense) {
        this.finalphysicalDefense = finalphysicalDefense;
    }

    public double getPhysicalHitRate() {
        return physicalHitRate;
    }

    public void setPhysicalHitRate(double physicalHitRate) {
        this.physicalHitRate = physicalHitRate;
    }

    public double getFinalphysicalHitRate() {
        return finalphysicalHitRate;
    }

    public void setFinalphysicalHitRate(double finalphysicalHitRate) {
        this.finalphysicalHitRate = finalphysicalHitRate;
    }

    public double getPhysicalEvasion() {
        return physicalEvasion;
    }

    public void setPhysicalEvasion(double physicalEvasion) {
        this.physicalEvasion = physicalEvasion;
    }

    public double getFinalphysicalEvasion() {
        return finalphysicalEvasion;
    }

    public void setFinalphysicalEvasion(double finalphysicalEvasion) {
        this.finalphysicalEvasion = finalphysicalEvasion;
    }

    public double getMagicalAttack() {
        return magicalAttack;
    }

    public void setMagicalAttack(double magicalAttack) {
        this.magicalAttack = magicalAttack;
    }

    public double getFinalmagicalAttack() {
        return finalmagicalAttack;
    }

    public void setFinalmagicalAttack(double finalmagicalAttack) {
        this.finalmagicalAttack = finalmagicalAttack;
    }

    public double getMagicalDefense() {
        return magicalDefense;
    }

    public void setMagicalDefense(double magicalDefense) {
        this.magicalDefense = magicalDefense;
    }

    public double getFinalmagicalDefense() {
        return finalmagicalDefense;
    }

    public void setFinalmagicalDefense(double finalmagicalDefense) {
        this.finalmagicalDefense = finalmagicalDefense;
    }

    public double getMagicalHitRate() {
        return magicalHitRate;
    }

    public void setMagicalHitRate(double magicalHitRate) {
        this.magicalHitRate = magicalHitRate;
    }

    public double getFinalmagicalHitRate() {
        return finalmagicalHitRate;
    }

    public void setFinalmagicalHitRate(double finalmagicalHitRate) {
        this.finalmagicalHitRate = finalmagicalHitRate;
    }

    public double getMagicalEvasion() {
        return magicalEvasion;
    }

    public void setMagicalEvasion(double magicalEvasion) {
        this.magicalEvasion = magicalEvasion;
    }

    public double getFinalmagicalEvasion() {
        return finalmagicalEvasion;
    }

    public void setFinalmagicalEvasion(double finalmagicalEvasion) {
        this.finalmagicalEvasion = finalmagicalEvasion;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getFinalcritical() {
        return finalcritical;
    }

    public void setFinalcritical(double finalcritical) {
        this.finalcritical = finalcritical;
    }

   
    
    public double getNormalizedDamageToPlayer(double damagetaken) {
        double maxhealth = 20;
        double res;
        double resthealth = actualHealth - damagetaken;
        if (resthealth <= 0) {
            return 20.0;
        }
        return resthealth * 20 / maxHealth;
    }

    public double getNormalizedHealToPlayer(double instanthealing) {
        double predhealth = actualHealth + instanthealing;
        if (maxHealth < predhealth) {
            return maxHealth;
        }
        return predhealth;
        //revisar
    }

    public boolean isSetResetRequested() {
        return this.setResetRequest;
    }

    public void setSetResetRequested(boolean b) {
        this.setResetRequest = b;
    }

    void createNewPlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addStats(List<RPGLore> listOfLoresFromItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void subStats(List<RPGLore> listOfLoresFromItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
