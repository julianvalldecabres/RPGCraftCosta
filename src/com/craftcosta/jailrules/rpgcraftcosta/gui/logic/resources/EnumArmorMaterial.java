/*
 * Copyright (C) 2016 jail
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources;

/**
 *
 * @author jail
 */
public enum EnumArmorMaterial {
    LEATHER("Cuero","Armadura de cuero","logic/resources/items/"+"leather.png"),
    IRON("Hierro","Armadura de hierro","logic/resources/items/"+"iron_ingot.png"),
    GOLD("Oro","Armadura de oro","logic/resources/items/"+"gold_ingot.png"),
    DIAMOND("Diamante","Armadura de diamante","logic/resources/items/"+"diamond.png"),
    CHAINMAL("Cota de malla","Armadura de cota de malla","logic/resources/items/"+"fire.png");
    String name;
    String tooltip;
    String path;

    private EnumArmorMaterial(String name, String tooltip, String path) {
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
