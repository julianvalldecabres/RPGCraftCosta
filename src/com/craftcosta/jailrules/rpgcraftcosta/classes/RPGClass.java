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
public abstract class RPGClass {
    String nameClass;           //Class name
    //BASE STATS
    long baseHealth;            //Increases & improves Attack Speed, Evasion, HitRates
    long baseMana;              //Increases Attack, Mana& Health 
    float basePhysicalAttack;   //Increases Mana & MagicAttack
    float baseMagicAttack;      //Increases MaxHealth & MaxMana
    float basePhysicalDefense;  //Physic attack unarmed
    float baseMagicDefense;     //Magical attack unarmed
    float basePhysicalHitRate;  //Physical defense
    float baseMagicHitRate;     //Magical defense
    float basePhysicalEvasion;  //% to evade physical damage
    float baseMagicEvasion;     //% to evade magic damage
    float baseCritical;         //% to make critical attack
    float baseDeadly;           //% to make deadly attack
    
    //LEVEL UP STATS 
    long lvlUpHealth;
    long lvlUpMana;
    float lvlUpPhysicalAttack;
    float lvlUpMagicAttack;
    float lvlUpPhysicalDefense;
    float lvlUpMagicDefense;
    float lvlUpPhysicalHitRate;
    float lvlUpMagicHitRate;
    float lvlUpPhysicalEvasion;
    float lvlUpMagicEvasion;
    float lvlUpCritical;
    float lvlUpDeadly;
    
    //SKILL POINTS
    long SPUpStrength_Health;
    float SPUpStrength_PhysicalAttack;
    float SPUpDextery_Critical;
    float SPUpDextery_Deadly;
    long SPUpIntelligence_Mana;
    float SPUpIntelligence_MagicAttack;
    long SPUpConstitution_Health;
    long SPUpConstitution_Mana;

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
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public long getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(long baseHealth) {
        this.baseHealth = baseHealth;
    }

    public long getBaseMana() {
        return baseMana;
    }

    public void setBaseMana(long baseMana) {
        this.baseMana = baseMana;
    }

    public float getBasePhysicalAttack() {
        return basePhysicalAttack;
    }

    public void setBasePhysicalAttack(float basePhysicalAttack) {
        this.basePhysicalAttack = basePhysicalAttack;
    }

    public float getBaseMagicAttack() {
        return baseMagicAttack;
    }

    public void setBaseMagicAttack(float baseMagicAttack) {
        this.baseMagicAttack = baseMagicAttack;
    }

    public float getBasePhysicalDefense() {
        return basePhysicalDefense;
    }

    public void setBasePhysicalDefense(float basePhysicalDefense) {
        this.basePhysicalDefense = basePhysicalDefense;
    }

    public float getBaseMagicDefense() {
        return baseMagicDefense;
    }

    public void setBaseMagicDefense(float baseMagicDefense) {
        this.baseMagicDefense = baseMagicDefense;
    }

    public float getBasePhysicalHitRate() {
        return basePhysicalHitRate;
    }

    public void setBasePhysicalHitRate(float basePhysicalHitRate) {
        this.basePhysicalHitRate = basePhysicalHitRate;
    }

    public float getBaseMagicHitRate() {
        return baseMagicHitRate;
    }

    public void setBaseMagicHitRate(float baseMagicHitRate) {
        this.baseMagicHitRate = baseMagicHitRate;
    }

    public float getBasePhysicalEvasion() {
        return basePhysicalEvasion;
    }

    public void setBasePhysicalEvasion(float basePhysicalEvasion) {
        this.basePhysicalEvasion = basePhysicalEvasion;
    }

    public float getBaseMagicEvasion() {
        return baseMagicEvasion;
    }

    public void setBaseMagicEvasion(float baseMagicEvasion) {
        this.baseMagicEvasion = baseMagicEvasion;
    }

    public float getBaseCritical() {
        return baseCritical;
    }

    public void setBaseCritical(float baseCritical) {
        this.baseCritical = baseCritical;
    }

    public float getBaseDeadly() {
        return baseDeadly;
    }

    public void setBaseDeadly(float baseDeadly) {
        this.baseDeadly = baseDeadly;
    }

    public long getLvlUpHealth() {
        return lvlUpHealth;
    }

    public void setLvlUpHealth(long lvlUpHealth) {
        this.lvlUpHealth = lvlUpHealth;
    }

    public long getLvlUpMana() {
        return lvlUpMana;
    }

    public void setLvlUpMana(long lvlUpMana) {
        this.lvlUpMana = lvlUpMana;
    }

    public float getLvlUpPhysicalAttack() {
        return lvlUpPhysicalAttack;
    }

    public void setLvlUpPhysicalAttack(float lvlUpPhysicalAttack) {
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
    }

    public float getLvlUpMagicAttack() {
        return lvlUpMagicAttack;
    }

    public void setLvlUpMagicAttack(float lvlUpMagicAttack) {
        this.lvlUpMagicAttack = lvlUpMagicAttack;
    }

    public float getLvlUpPhysicalDefense() {
        return lvlUpPhysicalDefense;
    }

    public void setLvlUpPhysicalDefense(float lvlUpPhysicalDefense) {
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
    }

    public float getLvlUpMagicDefense() {
        return lvlUpMagicDefense;
    }

    public void setLvlUpMagicDefense(float lvlUpMagicDefense) {
        this.lvlUpMagicDefense = lvlUpMagicDefense;
    }

    public float getLvlUpPhysicalHitRate() {
        return lvlUpPhysicalHitRate;
    }

    public void setLvlUpPhysicalHitRate(float lvlUpPhysicalHitRate) {
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
    }

    public float getLvlUpMagicHitRate() {
        return lvlUpMagicHitRate;
    }

    public void setLvlUpMagicHitRate(float lvlUpMagicHitRate) {
        this.lvlUpMagicHitRate = lvlUpMagicHitRate;
    }

    public float getLvlUpPhysicalEvasion() {
        return lvlUpPhysicalEvasion;
    }

    public void setLvlUpPhysicalEvasion(float lvlUpPhysicalEvasion) {
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
    }

    public float getLvlUpMagicEvasion() {
        return lvlUpMagicEvasion;
    }

    public void setLvlUpMagicEvasion(float lvlUpMagicEvasion) {
        this.lvlUpMagicEvasion = lvlUpMagicEvasion;
    }

    public float getLvlUpCritical() {
        return lvlUpCritical;
    }

    public void setLvlUpCritical(float lvlUpCritical) {
        this.lvlUpCritical = lvlUpCritical;
    }

    public float getLvlUpDeadly() {
        return lvlUpDeadly;
    }

    public void setLvlUpDeadly(float lvlUpDeadly) {
        this.lvlUpDeadly = lvlUpDeadly;
    }

    public long getSPUpStrength_Health() {
        return SPUpStrength_Health;
    }

    public void setSPUpStrength_Health(long SPUpStrength_Health) {
        this.SPUpStrength_Health = SPUpStrength_Health;
    }

    public float getSPUpStrength_PhysicalAttack() {
        return SPUpStrength_PhysicalAttack;
    }

    public void setSPUpStrength_PhysicalAttack(float SPUpStrength_PhysicalAttack) {
        this.SPUpStrength_PhysicalAttack = SPUpStrength_PhysicalAttack;
    }

    public float getSPUpDextery_Critical() {
        return SPUpDextery_Critical;
    }

    public void setSPUpDextery_Critical(float SPUpDextery_Critical) {
        this.SPUpDextery_Critical = SPUpDextery_Critical;
    }

    public float getSPUpDextery_Deadly() {
        return SPUpDextery_Deadly;
    }

    public void setSPUpDextery_Deadly(float SPUpDextery_Deadly) {
        this.SPUpDextery_Deadly = SPUpDextery_Deadly;
    }

    public long getSPUpIntelligence_Mana() {
        return SPUpIntelligence_Mana;
    }

    public void setSPUpIntelligence_Mana(long SPUpIntelligence_Mana) {
        this.SPUpIntelligence_Mana = SPUpIntelligence_Mana;
    }

    public float getSPUpIntelligence_MagicAttack() {
        return SPUpIntelligence_MagicAttack;
    }

    public void setSPUpIntelligence_MagicAttack(float SPUpIntelligence_MagicAttack) {
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
    }

    public long getSPUpConstitution_Health() {
        return SPUpConstitution_Health;
    }

    public void setSPUpConstitution_Health(long SPUpConstitution_Health) {
        this.SPUpConstitution_Health = SPUpConstitution_Health;
    }

    public long getSPUpConstitution_Mana() {
        return SPUpConstitution_Mana;
    }

    public void setSPUpConstitution_Mana(long SPUpConstitution_Mana) {
        this.SPUpConstitution_Mana = SPUpConstitution_Mana;
    }
    
    public RPGClass getRPGClass(){
        return this;
    }
}
