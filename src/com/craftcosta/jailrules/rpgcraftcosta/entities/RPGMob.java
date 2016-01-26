/*
 * Copyright (C) 2015 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.ProjectileType;

/**
 *
 * @author jail
 */
public abstract class RPGMob {

    public int level;
    public String name;
    public CustomEntityType type;
    public AttackType aType;
    public MobBehaviour mType;
    public ProjectileType pType;

    public double damageattack;
    public double movementspeed;
    public double knockback;
    public double followrange;
    public double maxhealth;
    public double attackspeed;
    public double rangeddamage;
    public float rangedstrength;

    public double money;
    public double exp;
    public double ap;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomEntityType getType() {
        return type;
    }

    public void setType(CustomEntityType type) {
        this.type = type;
    }

    public AttackType getaType() {
        return aType;
    }

    public void setaType(AttackType aType) {
        this.aType = aType;
    }

    public MobBehaviour getmType() {
        return mType;
    }

    public void setmType(MobBehaviour mType) {
        this.mType = mType;
    }

    public ProjectileType getpType() {
        return pType;
    }

    public void setpType(ProjectileType pType) {
        this.pType = pType;
    }

    public double getDamageattack() {
        return damageattack;
    }

    public void setDamageattack(double damageattack) {
        this.damageattack = damageattack;
    }

    public double getMovementspeed() {
        return movementspeed;
    }

    public void setMovementspeed(double movementspeed) {
        this.movementspeed = movementspeed;
    }

    public double getKnockback() {
        return knockback;
    }

    public void setKnockback(double knockback) {
        this.knockback = knockback;
    }

    public double getFollowrange() {
        return followrange;
    }

    public void setFollowrange(double followrange) {
        this.followrange = followrange;
    }

    public double getMaxhealth() {
        return maxhealth;
    }

    public void setMaxhealth(double maxhealth) {
        this.maxhealth = maxhealth;
    }

    public double getAttackspeed() {
        return attackspeed;
    }

    public void setAttackspeed(double attackspeed) {
        this.attackspeed = attackspeed;
    }

    public double getRangeddamage() {
        return rangeddamage;
    }

    public void setRangeddamage(double rangeddamage) {
        this.rangeddamage = rangeddamage;
    }

    public float getRangedstrength() {
        return rangedstrength;
    }

    public void setRangedstrength(float rangedstrength) {
        this.rangedstrength = rangedstrength;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public double getAp() {
        return ap;
    }

    public void setAp(double ap) {
        this.ap = ap;
    }
    
    
}
