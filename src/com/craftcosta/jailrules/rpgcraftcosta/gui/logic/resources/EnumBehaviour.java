/*
 * Copyright (C) 2016 jail
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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources;


/**
 *
 * @author jail
 */
public enum EnumBehaviour {
    PEACEFUL("peaceful","The mob acts as a peaceful animal, if it is damaged it will try to flee from his attacker","logic/resources/entities/"+"peaceful.png"),
    NORMAL("normal","The mob would not attack unless attacked,  if attacked will pursue his attacker until it comes out of its range","logic/resources/entities/"+"normal.png"),
    NORMAAGGRO("normal_w_agro","The mob would not attack unless attacked, if attacked pursue his attacker and call other mobs to his aid","logic/resources/entities/"+"normal_agro.png"),
    AGGRESSIVE("aggressive","The mob attack anyone that is in its range, only stop if the attacker flees his range","logic/resources/entities/"+"aggressive.png"),
    AGGREAGGRO("aggressive_w_agro","The mob attack anyone that is in its range and will call other mobs to his aid","logic/resources/entities/"+"aggressive_agro.png");
    String name;
    String tooltip;
    String path;

    private EnumBehaviour(String name, String tooltip, String path) {
        this.name = name;
        this.tooltip = tooltip;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
