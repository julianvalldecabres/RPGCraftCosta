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

    /**
     *
     * @param nameClass
     * @param enabled
     * @param baseHealth
     * @param baseMana
     * @param basePhysicalAttack
     * @param basePhysicalDefense
     * @param basePhysicalHitRate
     * @param basePhysicalEvasion
     * @param baseMagicAttack
     * @param baseMagicDefense
     * @param baseMagicHitRate
     * @param baseMagicEvasion
     * @param baseCritical
     * @param lvlUpHealth
     * @param lvlUpMana
     * @param lvlUpPhysicalAttack
     * @param lvlUpPhysicalDefense
     * @param lvlUpPhysicalHitRate
     * @param lvlUpPhysicalEvasion
     * @param lvlUpMagicAttack
     * @param lvlUpMagicDefense
     * @param lvlUpMagicHitRate
     * @param lvlUpMagicEvasion
     * @param lvlUpCritical
     * @param SPUpStrength_Health
     * @param SPUpStrength_PhysicalAttack
     * @param SPUpDextery_Critical
     * @param SPUpConstitution_Health
     * @param SPUpConstitution_Mana
     * @param SPUpIntelligence_MagicAttack
     * @param SPUPIntelligence_Mana
     */
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
    public double getBaseHealth() {
        return baseHealth;
    }

    /**
     *
     * @param baseHealth
     */
    public void setBaseHealth(double baseHealth) {
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
    public void setBaseMana(double baseMana) {
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
    public void setBasePhysicalAttack(double basePhysicalAttack) {
        this.basePhysicalAttack = basePhysicalAttack;
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
    public void setBasePhysicalDefense(double basePhysicalDefense) {
        this.basePhysicalDefense = basePhysicalDefense;
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
    public void setBasePhysicalHitRate(double basePhysicalHitRate) {
        this.basePhysicalHitRate = basePhysicalHitRate;
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
    public void setBasePhysicalEvasion(double basePhysicalEvasion) {
        this.basePhysicalEvasion = basePhysicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicalAttack() {
        return baseMagicalAttack;
    }

    /**
     *
     * @param baseMagicAttack
     */
    public void setBaseMagicalAttack(double baseMagicAttack) {
        this.baseMagicalAttack = baseMagicAttack;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicalDefense() {
        return baseMagicalDefense;
    }

    /**
     *
     * @param baseMagicDefense
     */
    public void setBaseMagicalDefense(double baseMagicDefense) {
        this.baseMagicalDefense = baseMagicDefense;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicalHitRate() {
        return baseMagicalHitRate;
    }

    /**
     *
     * @param baseMagicHitRate
     */
    public void setBaseMagicalHitRate(double baseMagicHitRate) {
        this.baseMagicalHitRate = baseMagicHitRate;
    }

    /**
     *
     * @return
     */
    public double getBaseMagicalEvasion() {
        return baseMagicalEvasion;
    }

    /**
     *
     * @param baseMagicEvasion
     */
    public void setBaseMagicEvasion(double baseMagicEvasion) {
        this.baseMagicalEvasion = baseMagicEvasion;
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
    public void setBaseCritical(double baseCritical) {
        this.baseCritical = baseCritical;
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
    public void setLvlUpHealth(double lvlUpHealth) {
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
    public void setLvlUpMana(double lvlUpMana) {
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
    public void setLvlUpPhysicalAttack(double lvlUpPhysicalAttack) {
        this.lvlUpPhysicalAttack = lvlUpPhysicalAttack;
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
    public void setLvlUpPhysicalDefense(double lvlUpPhysicalDefense) {
        this.lvlUpPhysicalDefense = lvlUpPhysicalDefense;
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
    public void setLvlUpPhysicalHitRate(double lvlUpPhysicalHitRate) {
        this.lvlUpPhysicalHitRate = lvlUpPhysicalHitRate;
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
    public void setLvlUpPhysicalEvasion(double lvlUpPhysicalEvasion) {
        this.lvlUpPhysicalEvasion = lvlUpPhysicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicalAttack() {
        return lvlUpMagicalAttack;
    }

    /**
     *
     * @param lvlUpMagicAttack
     */
    public void setLvlUpMagicalAttack(double lvlUpMagicAttack) {
        this.lvlUpMagicalAttack = lvlUpMagicAttack;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicalDefense() {
        return lvlUpMagicalDefense;
    }

    /**
     *
     * @param lvlUpMagicDefense
     */
    public void setLvlUpMagicalDefense(double lvlUpMagicDefense) {
        this.lvlUpMagicalDefense = lvlUpMagicDefense;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicalHitRate() {
        return lvlUpMagicalHitRate;
    }

    /**
     *
     * @param lvlUpMagicHitRate
     */
    public void setLvlUpMagicalHitRate(double lvlUpMagicHitRate) {
        this.lvlUpMagicalHitRate = lvlUpMagicHitRate;
    }

    /**
     *
     * @return
     */
    public double getLvlUpMagicalEvasion() {
        return lvlUpMagicalEvasion;
    }

    /**
     *
     * @param lvlUpMagicEvasion
     */
    public void setLvlUpMagicalEvasion(double lvlUpMagicEvasion) {
        this.lvlUpMagicalEvasion = lvlUpMagicEvasion;
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
    public void setLvlUpCritical(double lvlUpCritical) {
        this.lvlUpCritical = lvlUpCritical;
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
    public void setSPUpStrength_Health(double SPUpStrength_Health) {
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
    public void setSPUpStrength_PhysicalAttack(double SPUpStrength_PhysicalAttack) {
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
    public void setSPUpDextery_Critical(double SPUpDextery_Critical) {
        this.SPUpDextery_Critical = SPUpDextery_Critical;
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
    public void setSPUpConstitution_Health(double SPUpConstitution_Health) {
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
    public void setSPUpConstitution_Mana(double SPUpConstitution_Mana) {
        this.SPUpConstitution_Mana = SPUpConstitution_Mana;
    }

    /**
     *
     * @return
     */
    public double getSPUpIntelligence_MagicalAttack() {
        return SPUpIntelligence_MagicAttack;
    }

    /**
     *
     * @param SPUpIntelligence_MagicAttack
     */
    public void setSPUpIntelligence_MagicalAttack(double SPUpIntelligence_MagicAttack) {
        this.SPUpIntelligence_MagicAttack = SPUpIntelligence_MagicAttack;
    }

    /**
     *
     * @return
     */
    public double getSPUPIntelligence_Mana() {
        return SPUPIntelligence_Mana;
    }

    /**
     *
     * @param SPUPIntelligence_Mana
     */
    public void setSPUPIntelligence_Mana(double SPUPIntelligence_Mana) {
        this.SPUPIntelligence_Mana = SPUPIntelligence_Mana;
    }

}
