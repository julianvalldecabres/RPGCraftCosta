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
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.entities.MobDrop;
import java.util.List;

/**
 *
 * @author jail
 */
public class RPGMob {

    /**
     *
     */
    public int level;

    /**
     *
     */
    public String name;

    /**
     *
     */
    public CustomEntityType type;

    /**
     *
     */
    public AttackType aType;

    /**
     *
     */
    public MobBehaviour mType;

    /**
     *
     */
    public double damageattack;

    /**
     *
     */
    public double movementspeed;

    /**
     *
     */
    public double knockback;

    /**
     *
     */
    public double followrange;

    /**
     *
     */
    public double maxhealth;

    /**
     *
     */
    public double attackspeed;

    /**
     *
     */
    public double rangeddamage;

    /**
     *
     */
    public float rangedstrength;

    /**
     *
     */
    public long money;

    /**
     *
     */
    public long exp;

    /**
     *
     */
    public List<MobDrop> drops;
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
    public long getMoney() {
        return money;
    }

    /**
     *
     * @param money
     */
    public void setMoney(long money) {
        this.money = money;
    }

    /**
     *
     * @return
     */
    public long getExp() {
        return exp;
    }

    /**
     *
     * @param exp
     */
    public void setExp(long exp) {
        this.exp = exp;
    }

    public List<MobDrop> getDrops() {
        return drops;
    }

    public void setDrops(List<MobDrop> drops) {
        this.drops = drops;
    }

}
