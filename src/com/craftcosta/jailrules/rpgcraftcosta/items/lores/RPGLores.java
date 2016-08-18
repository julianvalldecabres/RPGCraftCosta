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
package com.craftcosta.jailrules.rpgcraftcosta.items.lores;

/**
 *
 * @author jail
 */
public enum RPGLores {

    /**
     *
     */
    PHYSICALATTACK("Physical Attack", RPGLoreValueType.VALUEDOUBLE),
    /**
     *
     */
    PHYSICALDEFENSE("Physical Defense", RPGLoreValueType.VALUEDOUBLE),
    /**
     *
     */
    PHYSICALEVASION("Physical Evasion", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    PHYSICALHITRATE("Physical HitRate", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    MAGICALATTACK("Magical Attack", RPGLoreValueType.VALUEDOUBLE),
    /**
     *
     */
    MAGICALDEFENSE("Magical Defense", RPGLoreValueType.VALUEDOUBLE),
    /**
     *
     */
    MAGICALEVASION("Magical Evasion", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    MAGICALHITRATE("Magical HitRate", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    XPBONUS("XP Bonus", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    APBONUS("AP Bonus", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    MONEYBONUS("Money Bonus", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    CRITICAL("Critical", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    HEALTHSTEAL("Health Steal", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    MANASTEAL("Mana Steal", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    HEALTH("Max Health", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    MANA("Max Mana", RPGLoreValueType.PERCENTAGE),
    /**
     *
     */
    SELLPRICE("Sell Price", RPGLoreValueType.VALUEINT),
    /**
     *
     */
    BUYPRICE("Buy Price", RPGLoreValueType.VALUEINT),
    /**
     *
     */
    NOCOMERCIABLE("No comerciable", RPGLoreValueType.NONE),
    /**
     *
     */
    NOCOMBINABLE("No combinable", RPGLoreValueType.NONE),
    /**
     *
     */
    NOUPGRADABLE("No upgradable", RPGLoreValueType.NONE);

    private String lorename;
    private RPGLoreValueType value;

    private RPGLores(String name, RPGLoreValueType value) {
        this.lorename = name;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getLoreName() {
        return lorename;
    }

    /**
     *
     * @param name
     */
    public void setLoreName(String name) {
        this.lorename = name;
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
     * @param rpglore
     * @param value
     * @return
     */
    public String getLoreString(RPGLores rpglore, double value) {
        switch (rpglore) {
            case PHYSICALATTACK:
            case PHYSICALDEFENSE:
            case MAGICALATTACK:
            case MAGICALDEFENSE:
                return rpglore.getLoreName() + " +" + (int) value;
            case PHYSICALEVASION:
            case PHYSICALHITRATE:
            case MAGICALEVASION:
            case MAGICALHITRATE:
            case APBONUS:
            case XPBONUS:
            case MONEYBONUS:
            case MANASTEAL:
            case MANA:
            case HEALTHSTEAL:
            case HEALTH:
            case CRITICAL:
                return rpglore.getLoreName() + " +" + String.format("%.2f", value) + "%";
            case BUYPRICE:
            case SELLPRICE:
                return rpglore.getLoreName() + " " + String.format("%d", getIntegerValue(value)) + "$";
        }
        return null;
    }

    /**
     *
     * @param lore
     * @return
     */
    public RPGLores getLorebyString(String lore) {
        String[] loreparts = lore.split(" ");
        String lorename = "";
        for (int i = 0; i < loreparts.length - 2; i++) {
            lorename += loreparts[i] + " ";
        }
        lorename += loreparts[loreparts.length - 2];
        return getRPGLoreByLorename(lorename);
    }

    /**
     *
     * @param lorename
     * @return
     */
    public RPGLores getRPGLoreByLorename(String lorename) {
        for (RPGLores lore : values()) {
            if (lore.getLoreName().equals(lorename)) {
                return lore;
            }
        }
        return null;
    }

    /**
     *
     * @param lorename
     * @return
     */
    public RPGLores getRPGLoresFromLoreName(String lorename) {
        for (RPGLores rpgLore : values()) {
            if (rpgLore.getLoreName().equals(lorename)) {
                return rpgLore;
            }
        }
        return null;
    }

    private int getIntegerValue(double value) {
        return (int) value;
    }
}
