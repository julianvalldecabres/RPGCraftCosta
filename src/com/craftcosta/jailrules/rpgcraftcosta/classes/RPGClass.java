/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.classes;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGClass {

    private String nameClass;           //Class name
    private boolean enabled;
    //BASE STATS
    private double baseHealth;            //Increases & improves Attack Speed, Evasion, HitRates
    private double baseMana;              //Increases Attack, Mana& Health 
    private double basePhysicalAttack;   //Increases Mana & MagicAttack
    private double baseMagicAttack;      //Increases MaxHealth & MaxMana
    private double basePhysicalDefense;  //Physic attack unarmed
    private double baseMagicDefense;     //Magical attack unarmed
    private double basePhysicalHitRate;  //Physical defense
    private double baseMagicHitRate;     //Magical defense
    private double basePhysicalEvasion;  //% to evade physical damage
    private double baseMagicEvasion;     //% to evade magic damage
    private double baseCritical;         //% to make critical attack
    private double baseDeadly;           //% to make deadly attack

    //LEVEL UP STATS 
    private double lvlUpHealth;
    private double lvlUpMana;
    private double lvlUpPhysicalAttack;
    private double lvlUpMagicAttack;
    private double lvlUpPhysicalDefense;
    private double lvlUpMagicDefense;
    private double lvlUpPhysicalHitRate;
    private double lvlUpMagicHitRate;
    private double lvlUpPhysicalEvasion;
    private double lvlUpMagicEvasion;
    private double lvlUpCritical;
    private double lvlUpDeadly;

    //SKILL POINTS
    private double SPUpStrength_Health;
    private double SPUpStrength_PhysicalAttack;
    private double SPUpDextery_Critical;
    private double SPUpDextery_Deadly;
    private double SPUpIntelligence_Mana;
    private double SPUpIntelligence_MagicAttack;
    private double SPUpConstitution_Health;
    private double SPUpConstitution_Mana;

    /**
     *
     * @param nameClass
     * @param baseHealth
     * @param baseMana
     * @param basePhysicalAttack
     * @param baseMagicAttack
     * @param basePhysicalDefense
     * @param baseMagicDefense
     * @param basePhysicalHitRate
     * @param baseMagicHitRate
     * @param basePhysicalEvasion
     * @param baseMagicEvasion
     * @param baseCritical
     * @param baseDeadly
     * @param lvlUpHealth
     * @param lvlUpMana
     * @param lvlUpPhysicalAttack
     * @param lvlUpMagicAttack
     * @param lvlUpPhysicalDefense
     * @param lvlUpMagicDefense
     * @param lvlUpPhysicalHitRate
     * @param lvlUpMagicHitRate
     * @param lvlUpPhysicalEvasion
     * @param lvlUpMagicEvasion
     * @param lvlUpCritical
     * @param lvlUpDeadly
     * @param SPUpStrength_Health
     * @param SPUpStrength_PhysicalAttack
     * @param SPUpDextery_Critical
     * @param SPUpDextery_Deadly
     * @param SPUpIntelligence_Mana
     * @param SPUpIntelligence_MagicAttack
     * @param SPUpConstitution_Health
     * @param SPUpConstitution_Mana
     */
    public RPGClass(String nameClass, long baseHealth, long baseMana, float basePhysicalAttack, float baseMagicAttack, float basePhysicalDefense, float baseMagicDefense, float basePhysicalHitRate, float baseMagicHitRate, float basePhysicalEvasion, float baseMagicEvasion, float baseCritical, float baseDeadly, long lvlUpHealth, long lvlUpMana, float lvlUpPhysicalAttack, float lvlUpMagicAttack, float lvlUpPhysicalDefense, float lvlUpMagicDefense, float lvlUpPhysicalHitRate, float lvlUpMagicHitRate, float lvlUpPhysicalEvasion, float lvlUpMagicEvasion, float lvlUpCritical, float lvlUpDeadly, long SPUpStrength_Health, float SPUpStrength_PhysicalAttack, float SPUpDextery_Critical, float SPUpDextery_Deadly, long SPUpIntelligence_Mana, float SPUpIntelligence_MagicAttack, long SPUpConstitution_Health, long SPUpConstitution_Mana) {
        this.nameClass = nameClass;
        this.baseHealth = baseHealth;
        this.baseMana = baseMana;
        this.basePhysicalAttack = basePhysicalAttack;
        this.baseMagicAttack = baseMagicAttack;
        this.basePhysicalDefense = basePhysicalDefense;
        this.baseMagicDefense = baseMagicDefense;
        this.basePhysicalHitRate = basePhysicalHitRate;
        this.baseMagicHitRate = baseMagicHitRate;
        this.basePhysicalEvasion = basePhysicalEvasion;
        this.baseMagicEvasion = baseMagicEvasion;
        this.baseCritical = baseCritical;
        this.baseDeadly = baseDeadly;
        this.lvlUpHealth = lvlUpHealth;
        this.lvlUpMana = lvlUpMana;
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
        this.lvlUpMagicAttack = lvlUpMagicAttack;
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
        this.lvlUpMagicDefense = lvlUpMagicDefense;
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
        this.lvlUpMagicHitRate = lvlUpMagicHitRate;
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
        this.lvlUpMagicEvasion = lvlUpMagicEvasion;
        this.lvlUpCritical = lvlUpCritical;
        this.lvlUpDeadly = lvlUpDeadly;
        this.SPUpStrength_Health = SPUpStrength_Health;
        this.SPUpStrength_PhysicalAttack = SPUpStrength_PhysicalAttack;
        this.SPUpDextery_Critical = SPUpDextery_Critical;
        this.SPUpDextery_Deadly = SPUpDextery_Deadly;
        this.SPUpIntelligence_Mana = SPUpIntelligence_Mana;
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
        this.SPUpConstitution_Health = SPUpConstitution_Health;
        this.SPUpConstitution_Mana = SPUpConstitution_Mana;
        this.save();
    }

    /**
     *
     */
    public RPGClass() {
        this.nameClass = "Default";
        this.enabled = true;
        this.baseHealth = 137.5;
        this.baseMana = 83;
        this.basePhysicalAttack = 24;
        this.baseMagicAttack = 19.25;
        this.basePhysicalDefense = 8;
        this.baseMagicDefense = 0.25;
        this.basePhysicalHitRate = 137.75;
        this.baseMagicHitRate = 137.75;
        this.basePhysicalEvasion = 37;
        this.baseMagicEvasion = 29.5;
        this.baseCritical = 90;
        this.baseDeadly = 60;
        this.lvlUpHealth = 25.5;
        this.lvlUpMana = 17.5;
        this.lvlUpPhysicalAttack = 1.5;
        this.lvlUpMagicAttack = 1.1675;
        this.lvlUpPhysicalDefense = 2.835;
        this.lvlUpMagicDefense = 0.625;
        this.lvlUpPhysicalHitRate = 2.25;
        this.lvlUpMagicHitRate = 1.75;
        this.lvlUpPhysicalEvasion = 2.25;
        this.lvlUpMagicEvasion = 1.75;
        this.lvlUpCritical = 2.4;
        this.lvlUpDeadly = 1.65;
        this.SPUpStrength_Health = 4;
        this.SPUpStrength_PhysicalAttack = 0.67;
        this.SPUpDextery_Critical = 6.9525;
        this.SPUpDextery_Deadly = 4.635;
        this.SPUpIntelligence_Mana = 7.75;
        this.SPUpIntelligence_MagicAttack = 0.67;
        this.SPUpConstitution_Health = 9.75;
        this.SPUpConstitution_Mana = 4;
        this.save();
    }

    private void save() {
        File file = new File(RPGFinals.classFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(RPGClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileConfiguration section = YamlConfiguration.loadConfiguration(file);
        section.set(getNameClass() + ".name", getNameClass());
        section.getConfigurationSection(nameClass);
        section.set("name", getNameClass());
        section.set("enabled", isEnabled());
        section.set("baseHealth", getBaseHealth());
        section.set("baseMana", getBaseMana());
        section.set("basePhysicalAttack", getBasePhysicalAttack());
        section.set("baseMagicAttack", getBaseMagicAttack());
        section.set("basePhysicalDefense", getBasePhysicalDefense());
        section.set("baseMagicDefense", getBaseMagicDefense());
        section.set("basePhysicalHitRate", getBasePhysicalHitRate());
        section.set("baseMagicHitRate", getBaseMagicHitRate());
        section.set("basePhysicalEvasion", getBasePhysicalEvasion());
        section.set("baseMagicEvasion", getBaseMagicEvasion());
        section.set("baseCritical", getBaseCritical());
        section.set("baseDeadly", getBaseDeadly());

        section.set("lvlUpHealth", getLvlUpHealth());
        section.set("lvlUpMana", getLvlUpMana());
        section.set("lvlUpPhysicalAttack", getLvlUpPhysicalAttack());
        section.set("lvlUpMagicAttack", getLvlUpMagicAttack());
        section.set("lvlUpPhysicalDefense", getLvlUpPhysicalDefense());
        section.set("lvlUpMagicDefense", getLvlUpMagicDefense());
        section.set("lvlUpPhysicalHitRate", getLvlUpPhysicalHitRate());
        section.set("lvlUpMagicHitRate", getLvlUpMagicHitRate());
        section.set("lvlUpPhysicalEvasion", getLvlUpPhysicalEvasion());
        section.set("lvlUpMagicEvasion", getLvlUpMagicEvasion());
        section.set("lvlUpCritical", getLvlUpCritical());
        section.set("lvlUpDeadly", getLvlUpDeadly());

        section.set("SPUpStrength_Health", getSPUpStrength_Health());
        section.set("SPUpStrength_PhysicalAttack", getSPUpStrength_PhysicalAttack());
        section.set("SPUpDextery_Critical", getSPUpDextery_Critical());
        section.set("SPUpDextery_Deadly", getSPUpDextery_Deadly());
        section.set("SPUpIntelligence_Mana", getSPUpIntelligence_Mana());
        section.set("SPUpIntelligence_MagicAttack", getSPUpIntelligence_MagicAttack());
        section.set("SPUpConstitution_Health", getSPUpConstitution_Health());
        section.set("SPUpConstitution_Mana", getSPUpConstitution_Mana());
        try {
            section.save(file);
        } catch (IOException ex) {
            Logger.getLogger(RPGClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param file
     * @param nameclass
     */
    public RPGClass(File file, String nameclass) {
        FileConfiguration section = YamlConfiguration.loadConfiguration(file);
        section.getConfigurationSection(nameclass);
        this.nameClass = section.getString("name");
        this.enabled = section.getBoolean("enabled");
        this.baseHealth = section.getDouble("baseHealth");
        this.baseMana = section.getDouble("baseMana");
        this.basePhysicalAttack = section.getDouble("basePhysicalAttack");
        this.baseMagicAttack = section.getDouble("baseMagicAttack");
        this.basePhysicalDefense = section.getDouble("basePhysicalDefense");
        this.baseMagicDefense = section.getDouble("baseMagicDefense");
        this.basePhysicalHitRate = section.getDouble("basePhysicalHitRate");
        this.baseMagicHitRate = section.getDouble("baseMagicHitRate");
        this.basePhysicalEvasion = section.getDouble("basePhysicalEvasion");
        this.baseMagicEvasion = section.getDouble("baseMagicEvasion");
        this.baseCritical = section.getDouble("baseCritical");
        this.baseDeadly = section.getDouble("baseDeadly");

        this.lvlUpHealth = section.getDouble("lvlUpHealth");
        this.lvlUpMana = section.getDouble("lvlUpMana");
        this.lvlUpPhysicalAttack = section.getDouble("lvlUpPhysicalAttack");
        this.lvlUpMagicAttack = section.getDouble("lvlUpMagicAttack");
        this.lvlUpPhysicalDefense = section.getDouble("lvlUpPhysicalDefense");
        this.lvlUpMagicDefense = section.getDouble("lvlUpMagicDefense");
        this.lvlUpPhysicalHitRate = section.getDouble("lvlUpPhysicalHitRate");
        this.lvlUpMagicHitRate = section.getDouble("lvlUpMagicHitRate");
        this.lvlUpPhysicalEvasion = section.getDouble("lvlUpPhysicalEvasion");
        this.lvlUpMagicEvasion = section.getDouble("lvlUpMagicEvasion");
        this.lvlUpCritical = section.getDouble("lvlUpCritical");
        this.lvlUpDeadly = section.getDouble("lvlUpDeadly");

        this.SPUpStrength_Health = section.getDouble("SPUpStrength_Health");
        this.SPUpStrength_PhysicalAttack = section.getDouble("SPUpStrength_PhysicalAttack");
        this.SPUpDextery_Critical = section.getDouble("SPUpDextery_Critical");
        this.SPUpDextery_Deadly = section.getDouble("SPUpDextery_Deadly");
        this.SPUpIntelligence_Mana = section.getDouble("SPUpIntelligence_Mana");
        this.SPUpIntelligence_MagicAttack = section.getDouble("SPUpIntelligence_MagicAttack");
        this.SPUpConstitution_Health = section.getDouble("SPUpConstitution_Health");
        this.SPUpConstitution_Mana = section.getDouble("SPUpConstitution_Mana");
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @return
     */
    public String getNameClass() {
        return nameClass;
    }

    /**
     *
     * @param nameClass
     */
    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    /**
     *
     * @return
     */
    public double getBaseHealth() {
        return baseHealth;
    }

    /**
     *
     * @param baseHealth
     */
    public void setBaseHealth(long baseHealth) {
        this.baseHealth = baseHealth;
    }

    /**
     *
     * @return
     */
    public double getBaseMana() {
        return baseMana;
    }

    /**
     *
     * @param baseMana
     */
    public void setBaseMana(long baseMana) {
        this.baseMana = baseMana;
    }

    /**
     *
     * @return
     */
    public double getBasePhysicalAttack() {
        return basePhysicalAttack;
    }

    /**
     *
     * @param basePhysicalAttack
     */
    public void setBasePhysicalAttack(float basePhysicalAttack) {
        this.basePhysicalAttack = basePhysicalAttack;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicAttack() {
        return baseMagicAttack;
    }

    /**
     *
     * @param baseMagicAttack
     */
    public void setBaseMagicAttack(float baseMagicAttack) {
        this.baseMagicAttack = baseMagicAttack;
    }

    /**
     *
     * @return
     */
    public double getBasePhysicalDefense() {
        return basePhysicalDefense;
    }

    /**
     *
     * @param basePhysicalDefense
     */
    public void setBasePhysicalDefense(float basePhysicalDefense) {
        this.basePhysicalDefense = basePhysicalDefense;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicDefense() {
        return baseMagicDefense;
    }

    /**
     *
     * @param baseMagicDefense
     */
    public void setBaseMagicDefense(float baseMagicDefense) {
        this.baseMagicDefense = baseMagicDefense;
    }

    /**
     *
     * @return
     */
    public double getBasePhysicalHitRate() {
        return basePhysicalHitRate;
    }

    /**
     *
     * @param basePhysicalHitRate
     */
    public void setBasePhysicalHitRate(float basePhysicalHitRate) {
        this.basePhysicalHitRate = basePhysicalHitRate;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicHitRate() {
        return baseMagicHitRate;
    }

    /**
     *
     * @param baseMagicHitRate
     */
    public void setBaseMagicHitRate(float baseMagicHitRate) {
        this.baseMagicHitRate = baseMagicHitRate;
    }

    /**
     *
     * @return
     */
    public double getBasePhysicalEvasion() {
        return basePhysicalEvasion;
    }

    /**
     *
     * @param basePhysicalEvasion
     */
    public void setBasePhysicalEvasion(float basePhysicalEvasion) {
        this.basePhysicalEvasion = basePhysicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicEvasion() {
        return baseMagicEvasion;
    }

    /**
     *
     * @param baseMagicEvasion
     */
    public void setBaseMagicEvasion(float baseMagicEvasion) {
        this.baseMagicEvasion = baseMagicEvasion;
    }

    /**
     *
     * @return
     */
    public double getBaseCritical() {
        return baseCritical;
    }

    /**
     *
     * @param baseCritical
     */
    public void setBaseCritical(float baseCritical) {
        this.baseCritical = baseCritical;
    }

    /**
     *
     * @return
     */
    public double getBaseDeadly() {
        return baseDeadly;
    }

    /**
     *
     * @param baseDeadly
     */
    public void setBaseDeadly(float baseDeadly) {
        this.baseDeadly = baseDeadly;
    }

    /**
     *
     * @return
     */
    public double getLvlUpHealth() {
        return lvlUpHealth;
    }

    /**
     *
     * @param lvlUpHealth
     */
    public void setLvlUpHealth(long lvlUpHealth) {
        this.lvlUpHealth = lvlUpHealth;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMana() {
        return lvlUpMana;
    }

    /**
     *
     * @param lvlUpMana
     */
    public void setLvlUpMana(long lvlUpMana) {
        this.lvlUpMana = lvlUpMana;
    }

    /**
     *
     * @return
     */
    public double getLvlUpPhysicalAttack() {
        return lvlUpPhysicalAttack;
    }

    /**
     *
     * @param lvlUpPhysicalAttack
     */
    public void setLvlUpPhysicalAttack(float lvlUpPhysicalAttack) {
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicAttack() {
        return lvlUpMagicAttack;
    }

    /**
     *
     * @param lvlUpMagicAttack
     */
    public void setLvlUpMagicAttack(float lvlUpMagicAttack) {
        this.lvlUpMagicAttack = lvlUpMagicAttack;
    }

    /**
     *
     * @return
     */
    public double getLvlUpPhysicalDefense() {
        return lvlUpPhysicalDefense;
    }

    /**
     *
     * @param lvlUpPhysicalDefense
     */
    public void setLvlUpPhysicalDefense(float lvlUpPhysicalDefense) {
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicDefense() {
        return lvlUpMagicDefense;
    }

    /**
     *
     * @param lvlUpMagicDefense
     */
    public void setLvlUpMagicDefense(float lvlUpMagicDefense) {
        this.lvlUpMagicDefense = lvlUpMagicDefense;
    }

    /**
     *
     * @return
     */
    public double getLvlUpPhysicalHitRate() {
        return lvlUpPhysicalHitRate;
    }

    /**
     *
     * @param lvlUpPhysicalHitRate
     */
    public void setLvlUpPhysicalHitRate(float lvlUpPhysicalHitRate) {
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicHitRate() {
        return lvlUpMagicHitRate;
    }

    /**
     *
     * @param lvlUpMagicHitRate
     */
    public void setLvlUpMagicHitRate(float lvlUpMagicHitRate) {
        this.lvlUpMagicHitRate = lvlUpMagicHitRate;
    }

    /**
     *
     * @return
     */
    public double getLvlUpPhysicalEvasion() {
        return lvlUpPhysicalEvasion;
    }

    /**
     *
     * @param lvlUpPhysicalEvasion
     */
    public void setLvlUpPhysicalEvasion(float lvlUpPhysicalEvasion) {
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicEvasion() {
        return lvlUpMagicEvasion;
    }

    /**
     *
     * @param lvlUpMagicEvasion
     */
    public void setLvlUpMagicEvasion(float lvlUpMagicEvasion) {
        this.lvlUpMagicEvasion = lvlUpMagicEvasion;
    }

    /**
     *
     * @return
     */
    public double getLvlUpCritical() {
        return lvlUpCritical;
    }

    /**
     *
     * @param lvlUpCritical
     */
    public void setLvlUpCritical(float lvlUpCritical) {
        this.lvlUpCritical = lvlUpCritical;
    }

    /**
     *
     * @return
     */
    public double getLvlUpDeadly() {
        return lvlUpDeadly;
    }

    /**
     *
     * @param lvlUpDeadly
     */
    public void setLvlUpDeadly(float lvlUpDeadly) {
        this.lvlUpDeadly = lvlUpDeadly;
    }

    /**
     *
     * @return
     */
    public double getSPUpStrength_Health() {
        return SPUpStrength_Health;
    }

    /**
     *
     * @param SPUpStrength_Health
     */
    public void setSPUpStrength_Health(long SPUpStrength_Health) {
        this.SPUpStrength_Health = SPUpStrength_Health;
    }

    /**
     *
     * @return
     */
    public double getSPUpStrength_PhysicalAttack() {
        return SPUpStrength_PhysicalAttack;
    }

    /**
     *
     * @param SPUpStrength_PhysicalAttack
     */
    public void setSPUpStrength_PhysicalAttack(float SPUpStrength_PhysicalAttack) {
        this.SPUpStrength_PhysicalAttack = SPUpStrength_PhysicalAttack;
    }

    /**
     *
     * @return
     */
    public double getSPUpDextery_Critical() {
        return SPUpDextery_Critical;
    }

    /**
     *
     * @param SPUpDextery_Critical
     */
    public void setSPUpDextery_Critical(float SPUpDextery_Critical) {
        this.SPUpDextery_Critical = SPUpDextery_Critical;
    }

    /**
     *
     * @return
     */
    public double getSPUpDextery_Deadly() {
        return SPUpDextery_Deadly;
    }

    /**
     *
     * @param SPUpDextery_Deadly
     */
    public void setSPUpDextery_Deadly(float SPUpDextery_Deadly) {
        this.SPUpDextery_Deadly = SPUpDextery_Deadly;
    }

    /**
     *
     * @return
     */
    public double getSPUpIntelligence_Mana() {
        return SPUpIntelligence_Mana;
    }

    /**
     *
     * @param SPUpIntelligence_Mana
     */
    public void setSPUpIntelligence_Mana(long SPUpIntelligence_Mana) {
        this.SPUpIntelligence_Mana = SPUpIntelligence_Mana;
    }

    /**
     *
     * @return
     */
    public double getSPUpIntelligence_MagicAttack() {
        return SPUpIntelligence_MagicAttack;
    }

    /**
     *
     * @param SPUpIntelligence_MagicAttack
     */
    public void setSPUpIntelligence_MagicAttack(float SPUpIntelligence_MagicAttack) {
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
    }

    /**
     *
     * @return
     */
    public double getSPUpConstitution_Health() {
        return SPUpConstitution_Health;
    }

    /**
     *
     * @param SPUpConstitution_Health
     */
    public void setSPUpConstitution_Health(long SPUpConstitution_Health) {
        this.SPUpConstitution_Health = SPUpConstitution_Health;
    }

    /**
     *
     * @return
     */
    public double getSPUpConstitution_Mana() {
        return SPUpConstitution_Mana;
    }

    /**
     *
     * @param SPUpConstitution_Mana
     */
    public void setSPUpConstitution_Mana(long SPUpConstitution_Mana) {
        this.SPUpConstitution_Mana = SPUpConstitution_Mana;
    }

    /**
     *
     * @return
     */
    public RPGClass getRPGClass() {
        return this;
    }

}
