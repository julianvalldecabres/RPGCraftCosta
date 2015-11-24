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
package com.craftcosta.jailrules.rpgcraftcosta.items.lores;

/**
 *
 * @author jail
 */
public enum RPGLores {

    PHYSICALATTACK("Physical Attack", RPGLoreValueType.VALUEDOUBLE),
    PHYSICALDEFENSE("Physical Defense", RPGLoreValueType.VALUEDOUBLE),
    PHYSICALEVASION("Physical Evasion", RPGLoreValueType.PERCENTAGE),
    PHYSICALHITRATE("Physical HitRate", RPGLoreValueType.PERCENTAGE),
    MAGICALATTACK("Magical Attack", RPGLoreValueType.VALUEDOUBLE),
    MAGICALDEFENSE("Magical Defense", RPGLoreValueType.VALUEDOUBLE),
    MAGICALEVASION("Magical Evasion", RPGLoreValueType.PERCENTAGE),
    MAGICALHITRATE("Magical HitRate", RPGLoreValueType.PERCENTAGE),
    XPBONUS("XP Bonus", RPGLoreValueType.PERCENTAGE),
    APBONUS("AP Bonus", RPGLoreValueType.PERCENTAGE),
    MONEYBONUS("Money Bonus", RPGLoreValueType.PERCENTAGE),
    CRITICAL("Critical", RPGLoreValueType.PERCENTAGE),
    HEALTHSTEAL("Health Steal", RPGLoreValueType.PERCENTAGE),
    MANASTEAL("Mana Steal", RPGLoreValueType.PERCENTAGE),
    HEALTH("Max Health", RPGLoreValueType.PERCENTAGE),
    MANA("Max Mana", RPGLoreValueType.PERCENTAGE),
    SELLPRICE("Sell Price", RPGLoreValueType.VALUEINT),
    BUYPRICE("Buy Price", RPGLoreValueType.VALUEINT),
    NOCOMERCIABLE("No comerciable", RPGLoreValueType.NONE),
    NOCOMBINABLE("No combinable", RPGLoreValueType.NONE),
    NOUPGRADABLE("No upgradable", RPGLoreValueType.NONE);

    private String lorename;
    private RPGLoreValueType value;

    private RPGLores(String name, RPGLoreValueType value) {
        this.lorename = name;
        this.value = value;
    }

    public String getLoreName() {
        return lorename;
    }

    public void setLoreName(String name) {
        this.lorename = name;
    }

    public RPGLoreValueType getValue() {
        return value;
    }

    public void setValue(RPGLoreValueType value) {
        this.value = value;
    }

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

    public RPGLores getLorebyString(String lore) {
        String[] loreparts = lore.split(" ");
        String lorename = "";
        for (int i = 0; i < loreparts.length - 2; i++) {
            lorename += loreparts[i] + " ";
        }
        lorename += loreparts[loreparts.length - 2];
        return getRPGLoreByLorename(lorename);
    }

    public RPGLores getRPGLoreByLorename(String lorename) {
        for (RPGLores lore : values()) {
            if (lore.getLoreName().equals(lorename)) {
                return lore;
            }
        }
        return null;
    }

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
