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
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.OcelotType;

/**
 *
 * @author jail
 */
public class RPGOcelot extends RPGMob{

    private boolean baby;
    private OcelotType oType;
    
    public RPGOcelot(int level, String name, CustomEntityType type, AttackType aType, MobBehaviour mType, double damageattack, double movementspeed, double knockback, double followrange, double maxhealth, double attackspeed, double rangeddamage, float rangedstrength, double money, double exp, double ap,boolean baby,OcelotType otype) {
        this.level = level;
        this.name = name;
        this.type = type;
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
        
        this.baby=baby;
        this.oType=otype;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    public OcelotType getoType() {
        return oType;
    }

    public void setoType(OcelotType oType) {
        this.oType = oType;
    }
    
    
}
