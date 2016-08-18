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

/**
 *
 * @author jail
 */
public enum EnumBehaviour {

    /**
     *
     */
    PEACEFUL("peaceful", "The mob acts as a peaceful animal, if it is damaged it will try to flee from his attacker", ""),
    /**
     *
     */
    NORMAL("normal", "The mob would not attack unless attacked,  if attacked will pursue his attacker until it comes out of its range", ""),
    /**
     *
     */
    NORMAAGGRO("normal_w_agro", "The mob would not attack unless attacked, if attacked pursue his attacker and call other mobs to his aid", ""),
    /**
     *
     */
    AGGRESSIVE("aggressive", "The mob attack anyone that is in its range, only stop if the attacker flees his range", ""),
    /**
     *
     */
    AGGREAGGRO("aggressive_w_agro", "The mob attack anyone that is in its range and will call other mobs to his aid", "");
    String name;
    String tooltip;
    String path;

    private EnumBehaviour(String name, String tooltip, String path) {
        this.name = name;
        this.tooltip = tooltip;
        this.path = path;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     *
     * @param tooltip
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

}
