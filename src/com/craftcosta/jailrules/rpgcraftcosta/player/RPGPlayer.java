/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGEconomy;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.bukkit.ChatColor;
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
    
    private double xpbonus;
    private double finalxpbonus;
    
    private double moneybonus;
    private double finalmoneybonus;

    /**
     *
     * @param p
     */
    public RPGPlayer(Player p) {
        this.player = p;
        createRPGPlayer();
    }

    /**
     *
     * @param name
     * @param uuid
     * @param player
     * @param guild
     * @param playerClass
     * @param party
     * @param econ
     * @param actualLevel
     * @param actualExp
     * @param ap
     * @param constitutionP
     * @param dexteryP
     * @param intelligenceP
     * @param strengthP
     * @param actualMana
     * @param maxMana
     * @param actualHealth
     * @param maxHealth
     * @param physicalAttack
     * @param physicalDefense
     * @param physicalHitRate
     * @param physicalEvasion
     * @param magicalAttack
     * @param magicalDefense
     * @param magicalHitRate
     * @param magicalEvasion
     * @param critical
     * @param xpbonus
     * @param moneybonus
     */
    public RPGPlayer(String name, UUID uuid, Player player, String guild, String playerClass, String party, RPGEconomy econ, int actualLevel, long actualExp, long ap, int constitutionP, int dexteryP, int intelligenceP, int strengthP, double actualMana, double maxMana, double actualHealth, double maxHealth, double physicalAttack, double physicalDefense, double physicalHitRate, double physicalEvasion, double magicalAttack, double magicalDefense, double magicalHitRate, double magicalEvasion, double critical,double xpbonus, double moneybonus) {
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
        this.constitutionP = constitutionP;
        this.dexteryP = dexteryP;
        this.intelligenceP = intelligenceP;
        this.strengthP = strengthP;
        this.actualMana = actualMana;
        this.maxMana = maxMana;
        this.actualHealth = actualHealth;
        this.maxHealth = maxHealth;
        this.finalMaxHealth=maxHealth;
        this.physicalAttack = physicalAttack;
        this.finalphysicalAttack=physicalAttack;
        this.physicalDefense = physicalDefense;
        this.finalphysicalDefense = physicalDefense; 
        this.physicalHitRate = physicalHitRate;
        this.finalphysicalHitRate = physicalHitRate;
        this.physicalEvasion = physicalEvasion;
        this.finalphysicalEvasion = physicalEvasion;
        this.magicalAttack = magicalAttack;
        this.finalmagicalAttack = magicalAttack;
        this.magicalDefense = magicalDefense;
        this.finalmagicalDefense = magicalDefense;
        this.magicalHitRate = magicalHitRate;
        this.finalmagicalHitRate = magicalHitRate;
        this.magicalEvasion = magicalEvasion;
        this.finalmagicalEvasion = magicalEvasion;
        this.critical = critical;
        this.finalcritical = critical;
        this.privateChat = true;
        this.globalChat = true;
        this.localChat = true;
        this.partyChat = true;
        this.guildChat = true;
        this.marketChat = true;
        this.setResetRequest = false;
        this.slotSelected = 0;
        this.moneybonus=moneybonus;
        this.finalmoneybonus=moneybonus;
        this.xpbonus=xpbonus;
        this.finalxpbonus=xpbonus;
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
        this.privateChat = true;
        this.globalChat = true;
        this.localChat = true;
        this.partyChat = true;
        this.guildChat = true;
        this.marketChat = true;
        this.setResetRequest = false;
        setRpgPlayerStatistics();
    }

    private void setRpgPlayerStatistics() {
        //general
        this.actualLevel = 0;
        this.actualExp = 0;
        this.actualHealth = 20;
        this.finalMaxHealth=20;
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
        this.finalphysicalHitRate = physicalHitRate;
        this.physicalEvasion = 0;
        this.finalphysicalEvasion = physicalEvasion;
        this.critical = 0;
        this.finalcritical=critical;
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
            this.maxHealth = section.getDouble("maxHealth");
            this.finalMaxHealth=section.getDouble("maxHealth");
            this.player.setHealth(getNormalizedHealToPlayer(actualHealth));
            
            this.actualMana = section.getDouble("actualMana");
            
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
            this.moneybonus= section.getDouble("moneybonus");
            this.xpbonus= section.getDouble("xpbonus");
        }
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
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     *
     * @return
     */
    public String getGuild() {
        return guild;
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
     * @return
     */
    public String getPlayerClass() {
        return playerClass;
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
     * @return
     */
    public int getSlotSelected() {
        return slotSelected;
    }

    /**
     *
     * @param slotSelected
     */
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
    public boolean isPartyChat() {
        return partyChat;
    }

    /**
     *
     * @param partyChat
     */
    public void setPartyChat(boolean partyChat) {
        this.partyChat = partyChat;
        this.player.sendMessage(ChatColor.RED + "Chat party activado");
    }

    /**
     *
     * @return
     */
    public boolean isPrivateChat() {
        return privateChat;
    }

    /**
     *
     * @param privateChat
     */
    public void setPrivateChat(boolean privateChat) {
        this.privateChat = privateChat;
        this.player.sendMessage(ChatColor.RED + "Chat private activado");
    }

    /**
     *
     * @return
     */
    public boolean isGuildChat() {
        return guildChat;
    }

    /**
     *
     * @param guildChat
     */
    public void setGuildChat(boolean guildChat) {
        this.guildChat = guildChat;
        this.player.sendMessage(ChatColor.RED + "Chat guild activado");
    }

    /**
     *
     * @return
     */
    public boolean isMarketChat() {
        return marketChat;
    }

    /**
     *
     * @param marketChat
     */
    public void setMarketChat(boolean marketChat) {
        this.marketChat = marketChat;
        this.player.sendMessage(ChatColor.RED + "Chat market activado");
    }

    /**
     *
     * @return
     */
    public boolean isLocalChat() {
        return localChat;
    }

    /**
     *
     * @param localChat
     */
    public void setLocalChat(boolean localChat) {
        this.localChat = localChat;
        this.player.sendMessage(ChatColor.RED + "Chat local activado");
    }

    /**
     *
     * @return
     */
    public boolean isGlobalChat() {
        return globalChat;
    }

    /**
     *
     * @param globalChat
     */
    public void setGlobalChat(boolean globalChat) {
        this.globalChat = globalChat;
        this.player.sendMessage(ChatColor.RED + "Chat global activado");
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
     * @param econ
     */
    public void setEcon(RPGEconomy econ) {
        this.econ = econ;
    }

    /**
     *
     * @return
     */
    public int getActualLevel() {
        return actualLevel;
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
     * @return
     */
    public long getActualExp() {
        return actualExp;
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
     * @return
     */
    public long getAp() {
        return ap;
    }

    /**
     *
     * @param ap
     */
    public void setAp(long ap) {
        this.ap = ap;
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
     * @param constitutionP
     */
    public void setConstitutionP(int constitutionP) {
        this.constitutionP = constitutionP;
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
     * @param dexteryP
     */
    public void setDexteryP(int dexteryP) {
        this.dexteryP = dexteryP;
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
     * @param intelligenceP
     */
    public void setIntelligenceP(int intelligenceP) {
        this.intelligenceP = intelligenceP;
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
     * @param strengthP
     */
    public void setStrengthP(int strengthP) {
        this.strengthP = strengthP;
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
    public double getMaxMana() {
        return maxMana;
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
     * @return
     */
    public double getFinalMaxMana() {
        return finalMaxMana;
    }

    /**
     *
     * @param finalMaxMana
     */
    public void setFinalMaxMana(double finalMaxMana) {
        this.finalMaxMana = finalMaxMana;
    }

    /**
     *
     * @return
     */
    public double getFinalMaxHealth() {
        return finalMaxHealth;
    }

    /**
     *
     * @param finalMaxHealth
     */
    public void setFinalMaxHealth(double finalMaxHealth) {
        this.finalMaxHealth = finalMaxHealth;
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
    public double getMaxHealth() {
        return maxHealth;
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
     * @return
     */
    public double getPhysicalAttack() {
        return physicalAttack;
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
     * @return
     */
    public double getFinalphysicalAttack() {
        return finalphysicalAttack;
    }

    /**
     *
     * @param finalphysicalAttack
     */
    public void setFinalphysicalAttack(double finalphysicalAttack) {
        this.finalphysicalAttack = finalphysicalAttack;
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
     * @param physicalDefense
     */
    public void setPhysicalDefense(double physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    /**
     *
     * @return
     */
    public double getFinalphysicalDefense() {
        return finalphysicalDefense;
    }

    /**
     *
     * @param finalphysicalDefense
     */
    public void setFinalphysicalDefense(double finalphysicalDefense) {
        this.finalphysicalDefense = finalphysicalDefense;
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
     * @param physicalHitRate
     */
    public void setPhysicalHitRate(double physicalHitRate) {
        this.physicalHitRate = physicalHitRate;
    }

    /**
     *
     * @return
     */
    public double getFinalphysicalHitRate() {
        return finalphysicalHitRate;
    }

    /**
     *
     * @param finalphysicalHitRate
     */
    public void setFinalphysicalHitRate(double finalphysicalHitRate) {
        this.finalphysicalHitRate = finalphysicalHitRate;
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
     * @param physicalEvasion
     */
    public void setPhysicalEvasion(double physicalEvasion) {
        this.physicalEvasion = physicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getFinalphysicalEvasion() {
        return finalphysicalEvasion;
    }

    /**
     *
     * @param finalphysicalEvasion
     */
    public void setFinalphysicalEvasion(double finalphysicalEvasion) {
        this.finalphysicalEvasion = finalphysicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getMagicalAttack() {
        return magicalAttack;
    }

    /**
     *
     * @param magicalAttack
     */
    public void setMagicalAttack(double magicalAttack) {
        this.magicalAttack = magicalAttack;
    }

    /**
     *
     * @return
     */
    public double getFinalmagicalAttack() {
        return finalmagicalAttack;
    }

    /**
     *
     * @param finalmagicalAttack
     */
    public void setFinalmagicalAttack(double finalmagicalAttack) {
        this.finalmagicalAttack = finalmagicalAttack;
    }

    /**
     *
     * @return
     */
    public double getMagicalDefense() {
        return magicalDefense;
    }

    /**
     *
     * @param magicalDefense
     */
    public void setMagicalDefense(double magicalDefense) {
        this.magicalDefense = magicalDefense;
    }

    /**
     *
     * @return
     */
    public double getFinalmagicalDefense() {
        return finalmagicalDefense;
    }

    /**
     *
     * @param finalmagicalDefense
     */
    public void setFinalmagicalDefense(double finalmagicalDefense) {
        this.finalmagicalDefense = finalmagicalDefense;
    }

    /**
     *
     * @return
     */
    public double getMagicalHitRate() {
        return magicalHitRate;
    }

    /**
     *
     * @param magicalHitRate
     */
    public void setMagicalHitRate(double magicalHitRate) {
        this.magicalHitRate = magicalHitRate;
    }

    /**
     *
     * @return
     */
    public double getFinalmagicalHitRate() {
        return finalmagicalHitRate;
    }

    /**
     *
     * @param finalmagicalHitRate
     */
    public void setFinalmagicalHitRate(double finalmagicalHitRate) {
        this.finalmagicalHitRate = finalmagicalHitRate;
    }

    /**
     *
     * @return
     */
    public double getMagicalEvasion() {
        return magicalEvasion;
    }

    /**
     *
     * @param magicalEvasion
     */
    public void setMagicalEvasion(double magicalEvasion) {
        this.magicalEvasion = magicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getFinalmagicalEvasion() {
        return finalmagicalEvasion;
    }

    /**
     *
     * @param finalmagicalEvasion
     */
    public void setFinalmagicalEvasion(double finalmagicalEvasion) {
        this.finalmagicalEvasion = finalmagicalEvasion;
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
     * @param critical
     */
    public void setCritical(double critical) {
        this.critical = critical;
    }

    /**
     *
     * @return
     */
    public double getFinalcritical() {
        return finalcritical;
    }

    /**
     *
     * @param finalcritical
     */
    public void setFinalcritical(double finalcritical) {
        this.finalcritical = finalcritical;
    }

    /**
     *
     * @param damagetaken
     * @return
     */
    public double getNormalizedDamageToPlayer(double damagetaken) {
        double maxhealth = 20;
        double res;
        double resthealth = actualHealth - damagetaken;
        if(resthealth<=0){
            return damagetaken;
        }
        return damagetaken * 20 / finalMaxHealth;
    }

    /**
     *
     * @param instanthealing
     * @return
     */
    public double getNormalizedHealToPlayer(double instanthealing) {
        double predhealth = actualHealth + instanthealing;
        if (finalMaxHealth <= predhealth) {
            return finalMaxHealth;
        }
        return predhealth;
    }

    /**
     *
     * @return
     */
    public boolean isSetResetRequested() {
        return this.setResetRequest;
    }

    /**
     *
     * @param b
     */
    public void setSetResetRequested(boolean b) {
        this.setResetRequest = b;
    }

    public boolean isSetResetRequest() {
        return setResetRequest;
    }

    public void setSetResetRequest(boolean setResetRequest) {
        this.setResetRequest = setResetRequest;
    }

    public double getXpbonus() {
        return xpbonus;
    }

    public void setXpbonus(double xpbonus) {
        this.xpbonus = xpbonus;
    }

    public double getFinalxpbonus() {
        return finalxpbonus;
    }

    public void setFinalxpbonus(double finalxpbonus) {
        this.finalxpbonus = finalxpbonus;
    }

    public double getMoneybonus() {
        return moneybonus;
    }

    public void setMoneybonus(double moneybonus) {
        this.moneybonus = moneybonus;
    }

    public double getFinalmoneybonus() {
        return finalmoneybonus;
    }

    public void setFinalmoneybonus(double finalmoneybonus) {
        this.finalmoneybonus = finalmoneybonus;
    }
    
    /**
     *
     * @param listOfLoresFromItem
     */
    public void addStats(List<RPGLore> lores) {
        for (RPGLore lore : lores) {
            switch (lore.getLoretype()) {
                case CRITICAL:
                    this.setFinalcritical(this.getFinalcritical()*(1+(double)lore.getValue()));
                    break;
                case HEALTH:
                    this.setFinalMaxHealth(this.getFinalMaxHealth()*(1+(double)lore.getValue()));
                    break;
//                case HEALTHSTEAL:
//                    rpgp.setFinalHealthSteal(rpgp.getFinalHealthSteal()*(1+(double)lore.getValue()));
//                    break;
                case MAGICALATTACK:
                    this.setFinalmagicalAttack(this.getFinalmagicalAttack()+(double)lore.getValue());
                    break;
                case MAGICALDEFENSE:
                    this.setFinalmagicalDefense(this.getFinalmagicalDefense()+(double)lore.getValue());
                    break;
                case MAGICALEVASION:
                    this.setFinalmagicalEvasion(this.getFinalmagicalEvasion()*(1+(double)lore.getValue()));
                    break;
                case MAGICALHITRATE:
                    this.setFinalmagicalHitRate(this.getFinalmagicalHitRate()*(1+(double)lore.getValue()));
                    break;
                case MANA:
                    this.setFinalMaxMana(this.getFinalMaxMana()*(1+(double)lore.getValue()));
                    break;
//                case MANASTEAL:
//                    break;
                case MONEYBONUS:
                    this.setFinalmoneybonus(this.getFinalmoneybonus()*(1+(double)lore.getValue()));
                    break;
                case PHYSICALATTACK:
                    this.setFinalphysicalAttack(this.getFinalphysicalAttack()+(double)lore.getValue());
                    break;
                case PHYSICALDEFENSE:
                    this.setFinalphysicalDefense(this.getFinalphysicalDefense()+(double)lore.getValue());
                    break;
                case PHYSICALEVASION:
                    this.setFinalphysicalEvasion(this.getFinalphysicalEvasion()*(1+(double)lore.getValue()));
                    break;
                case PHYSICALHITRATE:
                    this.setFinalphysicalHitRate(this.getFinalphysicalHitRate()*(1+(double)lore.getValue()));
                    break;
                case XPBONUS:
                    this.setFinalxpbonus(this.getFinalxpbonus()*(1+(double)lore.getValue()));
                    break;
            }
        }
    }

    /**
     *
     * @param listOfLoresFromItem
     */
    public void subStats(List<RPGLore> lores) {
        for (RPGLore lore : lores) {
            switch (lore.getLoretype()) {
                case CRITICAL:
                    this.setFinalcritical(this.getFinalcritical()/(1+(double)lore.getValue()));
                    break;
                case HEALTH:
                    this.setFinalMaxHealth(this.getFinalMaxHealth()/(1+(double)lore.getValue()));
                    break;
//                case HEALTHSTEAL:
//                    rpgp.setFinalHealthSteal(rpgp.getFinalHealthSteal()*(1+(double)lore.getValue()));
//                    break;
                case MAGICALATTACK:
                    this.setFinalmagicalAttack(this.getFinalmagicalAttack()-(double)lore.getValue());
                    break;
                case MAGICALDEFENSE:
                    this.setFinalmagicalDefense(this.getFinalmagicalDefense()-(double)lore.getValue());
                    break;
                case MAGICALEVASION:
                    this.setFinalmagicalEvasion(this.getFinalmagicalEvasion()/(1+(double)lore.getValue()));
                    break;
                case MAGICALHITRATE:
                    this.setFinalmagicalHitRate(this.getFinalmagicalHitRate()/(1+(double)lore.getValue()));
                    break;
                case MANA:
                    this.setFinalMaxMana(this.getFinalMaxMana()/(1+(double)lore.getValue()));
                    break;
//                case MANASTEAL:
//                    break;
                case MONEYBONUS:
                    this.setFinalmoneybonus(this.getFinalmoneybonus()/(1+(double)lore.getValue()));
                    break;
                case PHYSICALATTACK:
                    this.setFinalphysicalAttack(this.getFinalphysicalAttack()-(double)lore.getValue());
                    break;
                case PHYSICALDEFENSE:
                    this.setFinalphysicalDefense(this.getFinalphysicalDefense()-(double)lore.getValue());
                    break;
                case PHYSICALEVASION:
                    this.setFinalphysicalEvasion(this.getFinalphysicalEvasion()/(1+(double)lore.getValue()));
                    break;
                case PHYSICALHITRATE:
                    this.setFinalphysicalHitRate(this.getFinalphysicalHitRate()/(1+(double)lore.getValue()));
                    break;
                case XPBONUS:
                    this.setFinalxpbonus(this.getFinalxpbonus()/(1+(double)lore.getValue()));
                    break;
            }
        }
    }
}
