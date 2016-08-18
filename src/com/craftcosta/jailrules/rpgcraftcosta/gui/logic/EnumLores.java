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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic;

import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreValueType;

/**
 *
 * @author jail
 */
public enum EnumLores {

    /**
     *
     */
    PHYSICALATTACK("Physical Attack", RPGLoreValueType.VALUEDOUBLE, "Increments the player's physical attack"),
    /**
     *
     */
    PHYSICALDEFENSE("Physical Defense", RPGLoreValueType.VALUEDOUBLE, "Increments the player´s physical defense"),
    /**
     *
     */
    PHYSICALEVASION("Physical Evasion", RPGLoreValueType.PERCENTAGE, "Increments player physical evasion in a percentage"),
    /**
     *
     */
    PHYSICALHITRATE("Physical HitRate", RPGLoreValueType.PERCENTAGE, "Increments player physical hitrate in a percentage"),
    /**
     *
     */
    MAGICALATTACK("Magical Attack", RPGLoreValueType.VALUEDOUBLE, "Increments player magical attack"),
    /**
     *
     */
    MAGICALDEFENSE("Magical Defense", RPGLoreValueType.VALUEDOUBLE, "Increments player magical defense"),
    /**
     *
     */
    MAGICALEVASION("Magical Evasion", RPGLoreValueType.PERCENTAGE, "Increments player magical evasion in a percentage"),
    /**
     *
     */
    MAGICALHITRATE("Magical HitRate", RPGLoreValueType.PERCENTAGE, "Increments player magical hitrate in a percentage"),
    /**
     *
     */
    XPBONUS("XP Bonus", RPGLoreValueType.PERCENTAGE, "Increments player experience in a percentage"),
    /**
     *
     */
    APBONUS("AP Bonus", RPGLoreValueType.PERCENTAGE, "Increments player ability points in a percentage"),
    /**
     *
     */
    MONEYBONUS("Money Bonus", RPGLoreValueType.PERCENTAGE, "Increments player money bonus in a percentage"),
    /**
     *
     */
    CRITICAL("Critical", RPGLoreValueType.PERCENTAGE, "Increments critical's player in a percentage"),
    /**
     *
     */
    HEALTHSTEAL("Health Steal", RPGLoreValueType.PERCENTAGE, "Increments player health steal in a percentage"),
    /**
     *
     */
    MANASTEAL("Mana Steal", RPGLoreValueType.PERCENTAGE, "Increments player mana stesl in a percentage"),
    /**
     *
     */
    HEALTH("Max Health", RPGLoreValueType.PERCENTAGE, "Increments health's player in a percentage"),
    /**
     *
     */
    MANA("Max Mana", RPGLoreValueType.PERCENTAGE, "Increments mana's player in a percentage"),
    /**
     *
     */
    SELLPRICE("Sell Price", RPGLoreValueType.VALUEINT, "Default sell price of the object"),
    /**
     *
     */
    BUYPRICE("Buy Price", RPGLoreValueType.VALUEINT, "Default buy price of the object"),
    /**
     *
     */
    NOCOMERCIABLE("No comerciable", RPGLoreValueType.NONE, "Avoid the object to be commerced"),
    /**
     *
     */
    NOCOMBINABLE("No combinable", RPGLoreValueType.NONE, "Avoid the object to be combinable"),
    /**
     *
     */
    NOUPGRADABLE("No upgradable", RPGLoreValueType.NONE, "Avoid the object to be upgraded");

    private String lorename;
    private RPGLoreValueType value;
    private String info;

    private EnumLores(String lorename, RPGLoreValueType value, String info) {
        this.lorename = lorename;
        this.value = value;
        this.info = info;
    }

    /**
     *
     * @return
     */
    public String getLorename() {
        return lorename;
    }

    /**
     *
     * @param lorename
     */
    public void setLorename(String lorename) {
        this.lorename = lorename;
    }

    /**
     *
     * @return
     */
    public RPGLoreValueType getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(RPGLoreValueType value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
