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
package com.craftcosta.jailrules.rpgcraftcosta.entities.utils;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public class PathFinderGoalGoHome extends PathfinderGoal {

    private double speed;
    private EntityInsentient entity;
    private Location loc;

    public PathFinderGoalGoHome(double speed, EntityInsentient entity, Location loc) {
        this.speed = speed;
        this.entity = entity;
        this.loc = loc;

    }

    @Override
    public boolean a() {
        EntityLiving ent = entity.getGoalTarget();
        Location loc = entity.getBukkitEntity().getLocation();
        if (this.loc.distance(entity.getBukkitEntity().getLocation()) > 15) {
            if (ent == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void c() {
        this.entity.setPosition(loc.getX(), loc.getY(), loc.getZ());
    }
}
