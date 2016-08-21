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
public enum EnumQuality {
    TRASH("basura","la mas baja calidad","logic/resources/entities/"+"trash.png"),
    COMMON("comun","calidad baja","logic/resources/entities/"+"common.png"),
    UNCOMMON("inusual","calidad normal","logic/resources/entities/"+"uncommon.png"),
    RARE("rara","calidad alta","logic/resources/entities/"+"rare.png"),
    EPIC("epica","calidad muy alta","logic/resources/entities/"+"epic.png");
    String name;
    String tooltip;
    String path;

    private EnumQuality(String name, String tooltip, String path) {
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
