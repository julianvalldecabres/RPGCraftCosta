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
package com.craftcosta.jailrules.rpgcraftcosta.area;

import java.util.List;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public class RPGArea {
    private String name;
    private String owner;
    private AreaType aType;
    private boolean pvp;
    private boolean canbuild;
    private Location pos1;
    private Location pos2;
    private List<RPGSubzone> subzones;
}