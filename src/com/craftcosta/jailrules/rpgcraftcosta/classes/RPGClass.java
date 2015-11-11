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

    private String nameClass;
    private boolean enabled;
    //BASE STATS
    private double baseHealth;
    private double baseMana;
    private double basePhysicalAttack;
    private double basePhysicalDefense;
    private double basePhysicalHitRate;
    private double basePhysicalEvasion;
    private double baseMagicAttack;
    private double baseMagicDefense;
    private double baseMagicHitRate;
    private double baseMagicEvasion;
    private double baseCritical;
    private double baseDeadly;

    //LEVEL UP STATS 
    private double lvlUpHealth;
    private double lvlUpMana;
    private double lvlUpPhysicalAttack;
    private double lvlUpPhysicalDefense;
    private double lvlUpPhysicalHitRate;
    private double lvlUpPhysicalEvasion;
    private double lvlUpMagicAttack;
    private double lvlUpMagicDefense;
    private double lvlUpMagicHitRate;
    private double lvlUpMagicEvasion;
    private double lvlUpCritical;
    private double lvlUpDeadly;

    //SKILL POINTS
    private double SPUpStrength_Health;
    private double SPUpStrength_PhysicalAttack;
    private double SPUpDextery_Critical;
    private double SPUpDextery_Deadly;
    private double SPUpConstitution_Health;
    private double SPUpConstitution_Mana;
    private double SPUpIntelligence_MagicAttack;
    private double SPUPIntelligence_Mana;

    public RPGClass(String nameClass, boolean enabled, double baseHealth, double baseMana, double basePhysicalAttack, double basePhysicalDefense, double basePhysicalHitRate, double basePhysicalEvasion, double baseMagicAttack, double baseMagicDefense, double baseMagicHitRate, double baseMagicEvasion, double baseCritical, double baseDeadly, double lvlUpHealth, double lvlUpMana, double lvlUpPhysicalAttack, double lvlUpPhysicalDefense, double lvlUpPhysicalHitRate, double lvlUpPhysicalEvasion, double lvlUpMagicAttack, double lvlUpMagicDefense, double lvlUpMagicHitRate, double lvlUpMagicEvasion, double lvlUpCritical, double lvlUpDeadly, double SPUpStrength_Health, double SPUpStrength_PhysicalAttack, double SPUpDextery_Critical, double SPUpDextery_Deadly, double SPUpConstitution_Health, double SPUpConstitution_Mana, double SPUpIntelligence_MagicAttack, double SPUPIntelligence_Mana) {
        this.nameClass = nameClass;
        this.enabled = enabled;
        this.baseHealth = baseHealth;
        this.baseMana = baseMana;
        this.basePhysicalAttack = basePhysicalAttack;
        this.basePhysicalDefense = basePhysicalDefense;
        this.basePhysicalHitRate = basePhysicalHitRate;
        this.basePhysicalEvasion = basePhysicalEvasion;
        this.baseMagicAttack = baseMagicAttack;
        this.baseMagicDefense = baseMagicDefense;
        this.baseMagicHitRate = baseMagicHitRate;
        this.baseMagicEvasion = baseMagicEvasion;
        this.baseCritical = baseCritical;
        this.baseDeadly = baseDeadly;
        this.lvlUpHealth = lvlUpHealth;
        this.lvlUpMana = lvlUpMana;
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
        this.lvlUpMagicAttack = lvlUpMagicAttack;
        this.lvlUpMagicDefense = lvlUpMagicDefense;
        this.lvlUpMagicHitRate = lvlUpMagicHitRate;
        this.lvlUpMagicEvasion = lvlUpMagicEvasion;
        this.lvlUpCritical = lvlUpCritical;
        this.lvlUpDeadly = lvlUpDeadly;
        this.SPUpStrength_Health = SPUpStrength_Health;
        this.SPUpStrength_PhysicalAttack = SPUpStrength_PhysicalAttack;
        this.SPUpDextery_Critical = SPUpDextery_Critical;
        this.SPUpDextery_Deadly = SPUpDextery_Deadly;
        this.SPUpConstitution_Health = SPUpConstitution_Health;
        this.SPUpConstitution_Mana = SPUpConstitution_Mana;
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
        this.SPUPIntelligence_Mana = SPUPIntelligence_Mana;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(double baseHealth) {
        this.baseHealth = baseHealth;
    }

    public double getBaseMana() {
        return baseMana;
    }

    public void setBaseMana(double baseMana) {
        this.baseMana = baseMana;
    }

    public double getBasePhysicalAttack() {
        return basePhysicalAttack;
    }

    public void setBasePhysicalAttack(double basePhysicalAttack) {
        this.basePhysicalAttack = basePhysicalAttack;
    }

    public double getBasePhysicalDefense() {
        return basePhysicalDefense;
    }

    public void setBasePhysicalDefense(double basePhysicalDefense) {
        this.basePhysicalDefense = basePhysicalDefense;
    }

    public double getBasePhysicalHitRate() {
        return basePhysicalHitRate;
    }

    public void setBasePhysicalHitRate(double basePhysicalHitRate) {
        this.basePhysicalHitRate = basePhysicalHitRate;
    }

    public double getBasePhysicalEvasion() {
        return basePhysicalEvasion;
    }

    public void setBasePhysicalEvasion(double basePhysicalEvasion) {
        this.basePhysicalEvasion = basePhysicalEvasion;
    }

    public double getBaseMagicAttack() {
        return baseMagicAttack;
    }

    public void setBaseMagicAttack(double baseMagicAttack) {
        this.baseMagicAttack = baseMagicAttack;
    }

    public double getBaseMagicDefense() {
        return baseMagicDefense;
    }

    public void setBaseMagicDefense(double baseMagicDefense) {
        this.baseMagicDefense = baseMagicDefense;
    }

    public double getBaseMagicHitRate() {
        return baseMagicHitRate;
    }

    public void setBaseMagicHitRate(double baseMagicHitRate) {
        this.baseMagicHitRate = baseMagicHitRate;
    }

    public double getBaseMagicEvasion() {
        return baseMagicEvasion;
    }

    public void setBaseMagicEvasion(double baseMagicEvasion) {
        this.baseMagicEvasion = baseMagicEvasion;
    }

    public double getBaseCritical() {
        return baseCritical;
    }

    public void setBaseCritical(double baseCritical) {
        this.baseCritical = baseCritical;
    }

    public double getBaseDeadly() {
        return baseDeadly;
    }

    public void setBaseDeadly(double baseDeadly) {
        this.baseDeadly = baseDeadly;
    }

    public double getLvlUpHealth() {
        return lvlUpHealth;
    }

    public void setLvlUpHealth(double lvlUpHealth) {
        this.lvlUpHealth = lvlUpHealth;
    }

    public double getLvlUpMana() {
        return lvlUpMana;
    }

    public void setLvlUpMana(double lvlUpMana) {
        this.lvlUpMana = lvlUpMana;
    }

    public double getLvlUpPhysicalAttack() {
        return lvlUpPhysicalAttack;
    }

    public void setLvlUpPhysicalAttack(double lvlUpPhysicalAttack) {
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
    }

    public double getLvlUpPhysicalDefense() {
        return lvlUpPhysicalDefense;
    }

    public void setLvlUpPhysicalDefense(double lvlUpPhysicalDefense) {
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
    }

    public double getLvlUpPhysicalHitRate() {
        return lvlUpPhysicalHitRate;
    }

    public void setLvlUpPhysicalHitRate(double lvlUpPhysicalHitRate) {
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
    }

    public double getLvlUpPhysicalEvasion() {
        return lvlUpPhysicalEvasion;
    }

    public void setLvlUpPhysicalEvasion(double lvlUpPhysicalEvasion) {
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
    }

    public double getLvlUpMagicAttack() {
        return lvlUpMagicAttack;
    }

    public void setLvlUpMagicAttack(double lvlUpMagicAttack) {
        this.lvlUpMagicAttack = lvlUpMagicAttack;
    }

    public double getLvlUpMagicDefense() {
        return lvlUpMagicDefense;
    }

    public void setLvlUpMagicDefense(double lvlUpMagicDefense) {
        this.lvlUpMagicDefense = lvlUpMagicDefense;
    }

    public double getLvlUpMagicHitRate() {
        return lvlUpMagicHitRate;
    }

    public void setLvlUpMagicHitRate(double lvlUpMagicHitRate) {
        this.lvlUpMagicHitRate = lvlUpMagicHitRate;
    }

    public double getLvlUpMagicEvasion() {
        return lvlUpMagicEvasion;
    }

    public void setLvlUpMagicEvasion(double lvlUpMagicEvasion) {
        this.lvlUpMagicEvasion = lvlUpMagicEvasion;
    }

    public double getLvlUpCritical() {
        return lvlUpCritical;
    }

    public void setLvlUpCritical(double lvlUpCritical) {
        this.lvlUpCritical = lvlUpCritical;
    }

    public double getLvlUpDeadly() {
        return lvlUpDeadly;
    }

    public void setLvlUpDeadly(double lvlUpDeadly) {
        this.lvlUpDeadly = lvlUpDeadly;
    }

    public double getSPUpStrength_Health() {
        return SPUpStrength_Health;
    }

    public void setSPUpStrength_Health(double SPUpStrength_Health) {
        this.SPUpStrength_Health = SPUpStrength_Health;
    }

    public double getSPUpStrength_PhysicalAttack() {
        return SPUpStrength_PhysicalAttack;
    }

    public void setSPUpStrength_PhysicalAttack(double SPUpStrength_PhysicalAttack) {
        this.SPUpStrength_PhysicalAttack = SPUpStrength_PhysicalAttack;
    }

    public double getSPUpDextery_Critical() {
        return SPUpDextery_Critical;
    }

    public void setSPUpDextery_Critical(double SPUpDextery_Critical) {
        this.SPUpDextery_Critical = SPUpDextery_Critical;
    }

    public double getSPUpDextery_Deadly() {
        return SPUpDextery_Deadly;
    }

    public void setSPUpDextery_Deadly(double SPUpDextery_Deadly) {
        this.SPUpDextery_Deadly = SPUpDextery_Deadly;
    }

    public double getSPUpConstitution_Health() {
        return SPUpConstitution_Health;
    }

    public void setSPUpConstitution_Health(double SPUpConstitution_Health) {
        this.SPUpConstitution_Health = SPUpConstitution_Health;
    }

    public double getSPUpConstitution_Mana() {
        return SPUpConstitution_Mana;
    }

    public void setSPUpConstitution_Mana(double SPUpConstitution_Mana) {
        this.SPUpConstitution_Mana = SPUpConstitution_Mana;
    }

    public double getSPUpIntelligence_MagicAttack() {
        return SPUpIntelligence_MagicAttack;
    }

    public void setSPUpIntelligence_MagicAttack(double SPUpIntelligence_MagicAttack) {
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
    }

    public double getSPUPIntelligence_Mana() {
        return SPUPIntelligence_Mana;
    }

    public void setSPUPIntelligence_Mana(double SPUPIntelligence_Mana) {
        this.SPUPIntelligence_Mana = SPUPIntelligence_Mana;
    }

}
