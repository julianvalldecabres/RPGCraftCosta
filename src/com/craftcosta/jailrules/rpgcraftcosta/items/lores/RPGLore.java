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
public class RPGLore {

    private RPGLores loretype;
    private Object value;

    /**
     *
     * @param loretype
     * @param value
     */
    public RPGLore(RPGLores loretype, Object value) {
        this.loretype = loretype;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public RPGLores getLoretype() {
        return loretype;
    }

    /**
     *
     * @param loretype
     */
    public void setLoretype(RPGLores loretype) {
        this.loretype = loretype;
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
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
