/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.classes;

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
    private double baseMagicalAttack;
    private double baseMagicalDefense;
    private double baseMagicalHitRate;
    private double baseMagicalEvasion;
    private double baseCritical;

    //LEVEL UP STATS 
    private double lvlUpHealth;
    private double lvlUpMana;
    private double lvlUpPhysicalAttack;
    private double lvlUpPhysicalDefense;
    private double lvlUpPhysicalHitRate;
    private double lvlUpPhysicalEvasion;
    private double lvlUpMagicalAttack;
    private double lvlUpMagicalDefense;
    private double lvlUpMagicalHitRate;
    private double lvlUpMagicalEvasion;
    private double lvlUpCritical;

    //SKILL POINTS
    private double SPUpStrength_Health;
    private double SPUpStrength_PhysicalAttack;
    private double SPUpDextery_Critical;
    private double SPUpConstitution_Health;
    private double SPUpConstitution_Mana;
    private double SPUpIntelligence_MagicAttack;
    private double SPUPIntelligence_Mana;

    public RPGClass(String nameClass, boolean enabled, double baseHealth, double baseMana, double basePhysicalAttack, double basePhysicalDefense, double basePhysicalHitRate, double basePhysicalEvasion, double baseMagicAttack, double baseMagicDefense, double baseMagicHitRate, double baseMagicEvasion, double baseCritical, double lvlUpHealth, double lvlUpMana, double lvlUpPhysicalAttack, double lvlUpPhysicalDefense, double lvlUpPhysicalHitRate, double lvlUpPhysicalEvasion, double lvlUpMagicAttack, double lvlUpMagicDefense, double lvlUpMagicHitRate, double lvlUpMagicEvasion, double lvlUpCritical, double SPUpStrength_Health, double SPUpStrength_PhysicalAttack, double SPUpDextery_Critical, double SPUpConstitution_Health, double SPUpConstitution_Mana, double SPUpIntelligence_MagicAttack, double SPUPIntelligence_Mana) {
        this.nameClass = nameClass;
        this.enabled = enabled;
        this.baseHealth = baseHealth;
        this.baseMana = baseMana;
        this.basePhysicalAttack = basePhysicalAttack;
        this.basePhysicalDefense = basePhysicalDefense;
        this.basePhysicalHitRate = basePhysicalHitRate;
        this.basePhysicalEvasion = basePhysicalEvasion;
        this.baseMagicalAttack = baseMagicAttack;
        this.baseMagicalDefense = baseMagicDefense;
        this.baseMagicalHitRate = baseMagicHitRate;
        this.baseMagicalEvasion = baseMagicEvasion;
        this.baseCritical = baseCritical;
        this.lvlUpHealth = lvlUpHealth;
        this.lvlUpMana = lvlUpMana;
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
        this.lvlUpMagicalAttack = lvlUpMagicAttack;
        this.lvlUpMagicalDefense = lvlUpMagicDefense;
        this.lvlUpMagicalHitRate = lvlUpMagicHitRate;
        this.lvlUpMagicalEvasion = lvlUpMagicEvasion;
        this.lvlUpCritical = lvlUpCritical;
        this.SPUpStrength_Health = SPUpStrength_Health;
        this.SPUpStrength_PhysicalAttack = SPUpStrength_PhysicalAttack;
        this.SPUpDextery_Critical = SPUpDextery_Critical;
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

    public double getBaseMagicalAttack() {
        return baseMagicalAttack;
    }

    public void setBaseMagicalAttack(double baseMagicAttack) {
        this.baseMagicalAttack = baseMagicAttack;
    }

    public double getBaseMagicalDefense() {
        return baseMagicalDefense;
    }

    public void setBaseMagicalDefense(double baseMagicDefense) {
        this.baseMagicalDefense = baseMagicDefense;
    }

    public double getBaseMagicalHitRate() {
        return baseMagicalHitRate;
    }

    public void setBaseMagicalHitRate(double baseMagicHitRate) {
        this.baseMagicalHitRate = baseMagicHitRate;
    }

    public double getBaseMagicalEvasion() {
        return baseMagicalEvasion;
    }

    public void setBaseMagicEvasion(double baseMagicEvasion) {
        this.baseMagicalEvasion = baseMagicEvasion;
    }

    public double getBaseCritical() {
        return baseCritical;
    }

    public void setBaseCritical(double baseCritical) {
        this.baseCritical = baseCritical;
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

    public double getLvlUpMagicalAttack() {
        return lvlUpMagicalAttack;
    }

    public void setLvlUpMagicalAttack(double lvlUpMagicAttack) {
        this.lvlUpMagicalAttack = lvlUpMagicAttack;
    }

    public double getLvlUpMagicalDefense() {
        return lvlUpMagicalDefense;
    }

    public void setLvlUpMagicalDefense(double lvlUpMagicDefense) {
        this.lvlUpMagicalDefense = lvlUpMagicDefense;
    }

    public double getLvlUpMagicalHitRate() {
        return lvlUpMagicalHitRate;
    }

    public void setLvlUpMagicalHitRate(double lvlUpMagicHitRate) {
        this.lvlUpMagicalHitRate = lvlUpMagicHitRate;
    }

    public double getLvlUpMagicalEvasion() {
        return lvlUpMagicalEvasion;
    }

    public void setLvlUpMagicalEvasion(double lvlUpMagicEvasion) {
        this.lvlUpMagicalEvasion = lvlUpMagicEvasion;
    }

    public double getLvlUpCritical() {
        return lvlUpCritical;
    }

    public void setLvlUpCritical(double lvlUpCritical) {
        this.lvlUpCritical = lvlUpCritical;
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

    public double getSPUpIntelligence_MagicalAttack() {
        return SPUpIntelligence_MagicAttack;
    }

    public void setSPUpIntelligence_MagicalAttack(double SPUpIntelligence_MagicAttack) {
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
    }

    public double getSPUPIntelligence_Mana() {
        return SPUPIntelligence_Mana;
    }

    public void setSPUPIntelligence_Mana(double SPUPIntelligence_Mana) {
        this.SPUPIntelligence_Mana = SPUPIntelligence_Mana;
    }

}
