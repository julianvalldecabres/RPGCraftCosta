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
public class RPGLore {

    private RPGLores loretype;
    private Object value;

    public RPGLore(RPGLores loretype, Object value) {
        this.loretype = loretype;
        this.value = value;
    }

    public RPGLores getLoretype() {
        return loretype;
    }

    public void setLoretype(RPGLores loretype) {
        this.loretype = loretype;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (loretype) {
            case MAGICALATTACK:
            case MAGICALDEFENSE:
            case PHYSICALATTACK:
            case PHYSICALDEFENSE:
                return this.loretype.getLoreName() + " +" + String.format("%d", this.value);
            case MAGICALEVASION:
            case MAGICALHITRATE:
            case PHYSICALEVASION:
            case PHYSICALHITRATE:
            case HEALTH:
            case MANA:
            case HEALTHSTEAL:
            case MANASTEAL:
            case APBONUS:
            case XPBONUS:
            case MONEYBONUS:
            case CRITICAL:
                return this.loretype.getLoreName() + " +" + String.format("%.2f", this.value);
            case BUYPRICE:
            case SELLPRICE:
                return this.loretype.getLoreName() + " +" + String.format("%d", this.value);
            case NOCOMERCIABLE:
            case NOUPGRADABLE:
            case NOCOMBINABLE:
                return this.loretype.getLoreName();
        }
        return null;
    }
}
