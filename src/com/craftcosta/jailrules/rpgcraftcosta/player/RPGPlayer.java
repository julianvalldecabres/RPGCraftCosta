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
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
        if (RPGPlayerUtils.existsPlayerFile(p)) {
            //cargar player
            loadPlayerData();
        } else {
            //crear player nuevo
            createRPGPlayer();
            this.saveRPGPlayer();
        }

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
        if (this.playerClass.isEmpty()) {
            this.move = false;
        } else {
            this.move = true;
        }
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
        this.actualMana = 0;
        this.maxMana = 0;
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
        section.set("move", this.move);
        section.set("money", this.econ.getMoney());
        section.set("experience", this.actualExp);
        section.set("level", this.actualExp);
        section.set("guild", this.guild);
        section.set("party", this.party);
        section.set("class", this.playerClass);
        section.set("actualHealth", this.actualHealth);
        section.set("actualMana", this.actualMana);
        section.set("maxHealth", this.maxHealth);
        section.set("maxMana", this.maxMana);
        section.set("ap", this.ap);
        section.set("constP", this.constitutionP);
        section.set("dextP", this.dexteryP);
        section.set("intelP", this.intelligenceP);
        section.set("strghP", this.strengthP);
        section.set("physicalattack", this.physicalAttack);
        section.set("physicaldefense", this.physicalDefense);
        section.set("physicalevasion", this.physicalEvasion);
        section.set("physicalhitrate", this.physicalHitRate);
        section.set("magicalattack", this.magicalAttack);
        section.set("magicaldefense", this.magicalDefense);
        section.set("magicalevasion", this.magicalEvasion);
        section.set("magicalhitrate", this.magicalHitRate);
        section.set("critical", this.critical);
        try {
            section.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        setPhysicalAttack(rpgclass.getBasePhysicalAttack());
        setPhysicalDefense(rpgclass.getBasePhysicalAttack());
        setPhysicalHitRate(rpgclass.getBasePhysicalHitRate());
        setPhysicalEvasion(rpgclass.getBasePhysicalEvasion());
        setCritical(rpgclass.getBaseCritical());
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
        setPhysicalAttack(rpgclass.getLvlUpPhysicalAttack() + getPhysicalAttack());
        setPhysicalDefense(rpgclass.getLvlUpPhysicalAttack() + getPhysicalDefense());
        setPhysicalHitRate(rpgclass.getLvlUpPhysicalHitRate() + getPhysicalHitRate());
        setPhysicalEvasion(rpgclass.getLvlUpPhysicalEvasion() + getPhysicalEvasion());
        setCritical(rpgclass.getLvlUpCritical() + getCritical());
        //añadir skillpoints

        this.saveRPGPlayer();
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

    public int getSlotSelected() {
        return slotSelected;
    }

    public void setSlotSelected(int slotSelected) {
        this.slotSelected = slotSelected;
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
    public long getAP() {
        return ap;
    }

    /**
     *
     * @param availablePoints
     */
    public void setAP(int ap) {
        this.ap = ap;
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
        //actualizar atributos del rpgplayer
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
        return !this.guild.isEmpty();
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

//    public void checkAllEquipment() {
//        ItemStack[] equipedArmor = this.getPlayer().getInventory().getArmorContents();
//        ItemStack equipedWeapon = this.getPlayer().getItemInHand();
//        int weaponSlot = this.getPlayer().getInventory().getHeldItemSlot();
//        System.out.println("item en mano en slot: " + weaponSlot);
//        ItemStack[] quickbarItems = new ItemStack[9];
//
//        System.out.println("Contenido de armadura");
//        for (int i = 0; i <= 3; i++) {
//            if (!equipedArmor[i].getType().equals(Material.AIR)) {
//                
//            }
//        }
//        System.out.println("Contenido de la hotbar");
//        for (int i = 0; i <= 8; i++) {
//            System.out.println("slot: " + i);
//            if (this.player.getInventory().getContents()[i] != null) {
//                quickbarItems[i] = this.player.getInventory().getContents()[i];
//            }
//        }
//        for (ItemStack item : quickbarItems) {
//            if (item != null) {
//                System.out.println(item.toString());
//            }
//        }
//    }
    public boolean hasParty() {
        return !this.party.isEmpty();
    }

    public boolean hasClass() {
        return !this.playerClass.isEmpty();
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
