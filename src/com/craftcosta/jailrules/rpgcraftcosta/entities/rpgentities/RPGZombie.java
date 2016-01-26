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
    public boolean isVillager() {
        return villager;
    }

    public void setVillager(boolean villager) {
        this.villager = villager;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }



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
