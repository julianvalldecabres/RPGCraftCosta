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
package com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities;

import com.craftcosta.jailrules.rpgcraftcosta.entities.CustomEntityType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMob;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.ProjectileType;

/**
 *
 * @author jail
 */
public class RPGZombie extends RPGMob{
    private boolean villager;
    private boolean baby;

    /**
     *
     * @param level
     * @param name
     * @param type
     * @param aType
     * @param mType
     * @param damageattack
     * @param movementspeed
     * @param knockback
     * @param followrange
     * @param maxhealth
     * @param attackspeed
     * @param rangeddamage
     * @param rangedstrength
     * @param money
     * @param exp
     * @param ap
     * @param baby
     * @param villager
     */
    public RPGZombie(int level, String name, CustomEntityType type, AttackType aType, MobBehaviour mType,  double damageattack, double movementspeed, double knockback, double followrange, double maxhealth, double attackspeed, double rangeddamage, float rangedstrength, double money, double exp, double ap, boolean baby, boolean villager) {
        this.level = level;
        this.name = name;
        this.type = CustomEntityType.ZOMBIEX;
        this.aType = aType;
        this.mType = mType;

        this.damageattack = damageattack;
        this.movementspeed = movementspeed;
        this.knockback = knockback;
        this.followrange = followrange;
        this.maxhealth = maxhealth;
        this.attackspeed = attackspeed;
        this.rangeddamage = rangeddamage;
        this.rangedstrength = rangedstrength;
        this.money = money;
        this.exp = exp;
        this.ap = ap;
        
        this.villager=villager;
        this.baby=baby;
    }

    /**
     *
     * @return
     */
    public boolean isVillager() {
        return villager;
    }

    /**
     *
     * @param villager
     */
    public void setVillager(boolean villager) {
        this.villager = villager;
    }

    /**
     *
     * @return
     */
    public boolean isBaby() {
        return baby;
    }

    /**
     *
     * @param baby
     */
    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
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
    public CustomEntityType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(CustomEntityType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public AttackType getaType() {
        return aType;
    }

    /**
     *
     * @param aType
     */
    public void setaType(AttackType aType) {
        this.aType = aType;
    }

    /**
     *
     * @return
     */
    public MobBehaviour getmType() {
        return mType;
    }

    /**
     *
     * @param mType
     */
    public void setmType(MobBehaviour mType) {
        this.mType = mType;
    }

    /**
     *
     * @return
     */
    public ProjectileType getpType() {
        return pType;
    }

    /**
     *
     * @param pType
     */
    public void setpType(ProjectileType pType) {
        this.pType = pType;
    }

    /**
     *
     * @return
     */
    public double getDamageattack() {
        return damageattack;
    }

    /**
     *
     * @param damageattack
     */
    public void setDamageattack(double damageattack) {
        this.damageattack = damageattack;
    }

    /**
     *
     * @return
     */
    public double getMovementspeed() {
        return movementspeed;
    }

    /**
     *
     * @param movementspeed
     */
    public void setMovementspeed(double movementspeed) {
        this.movementspeed = movementspeed;
    }

    /**
     *
     * @return
     */
    public double getKnockback() {
        return knockback;
    }

    /**
     *
     * @param knockback
     */
    public void setKnockback(double knockback) {
        this.knockback = knockback;
    }

    /**
     *
     * @return
     */
    public double getFollowrange() {
        return followrange;
    }

    /**
     *
     * @param followrange
     */
    public void setFollowrange(double followrange) {
        this.followrange = followrange;
    }

    /**
     *
     * @return
     */
    public double getMaxhealth() {
        return maxhealth;
    }

    /**
     *
     * @param maxhealth
     */
    public void setMaxhealth(double maxhealth) {
        this.maxhealth = maxhealth;
    }

    /**
     *
     * @return
     */
    public double getAttackspeed() {
        return attackspeed;
    }

    /**
     *
     * @param attackspeed
     */
    public void setAttackspeed(double attackspeed) {
        this.attackspeed = attackspeed;
    }

    /**
     *
     * @return
     */
    public double getRangeddamage() {
        return rangeddamage;
    }

    /**
     *
     * @param rangeddamage
     */
    public void setRangeddamage(double rangeddamage) {
        this.rangeddamage = rangeddamage;
    }

    /**
     *
     * @return
     */
    public float getRangedstrength() {
        return rangedstrength;
    }

    /**
     *
     * @param rangedstrength
     */
    public void setRangedstrength(float rangedstrength) {
        this.rangedstrength = rangedstrength;
    }

    /**
     *
     * @return
     */
    public double getMoney() {
        return money;
    }

    /**
     *
     * @param money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     *
     * @return
     */
    public double getExp() {
        return exp;
    }

    /**
     *
     * @param exp
     */
    public void setExp(double exp) {
        this.exp = exp;
    }

    /**
     *
     * @return
     */
    public double getAp() {
        return ap;
    }

    /**
     *
     * @param ap
     */
    public void setAp(double ap) {
        this.ap = ap;
    }
    
    
}
