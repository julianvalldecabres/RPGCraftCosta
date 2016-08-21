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
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.SheepColor;

/**
 *
 * @author jail
 */
public class RPGSheep extends RPGMob{
    private boolean baby;
    private SheepColor sColor;
    
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
     * @param scolor
     * @param baby
     */
    public RPGSheep(int level, String name, CustomEntityType type, AttackType aType, MobBehaviour mType, double damageattack, double movementspeed, double knockback, double followrange, double maxhealth, double attackspeed, double rangeddamage, float rangedstrength, long money, long exp, double ap,SheepColor scolor, boolean baby) {
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
        this.sColor=scolor;
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
    public SheepColor getsColor() {
        return sColor;
    }

    /**
     *
     * @param sColor
     */
    public void setsColor(SheepColor sColor) {
        this.sColor = sColor;
    }
    
    
    
}
