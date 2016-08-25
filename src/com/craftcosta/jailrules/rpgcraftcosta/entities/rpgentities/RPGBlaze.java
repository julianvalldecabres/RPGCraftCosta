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
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumAttackType;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumBehaviour;

/**
 *
 * @author jail
 */
public class RPGBlaze extends RPGMob {

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
     */
    public RPGBlaze(int level, String name, CustomEntityType type, AttackType aType, MobBehaviour mType, double damageattack, double movementspeed, double knockback, double followrange, double maxhealth, double attackspeed, double rangeddamage, float rangedstrength, long money, long exp) {
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
    }
}
