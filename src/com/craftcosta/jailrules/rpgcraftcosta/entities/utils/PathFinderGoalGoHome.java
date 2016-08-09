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

    /**
     *
     * @param speed
     * @param entity
     * @param loc
     */
    public PathFinderGoalGoHome(double speed, EntityInsentient entity, Location loc) {
        this.speed = speed;
        this.entity = entity;
        this.loc = loc;

    }

    /**
     *
     * @return
     */
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

    /**
     *
     */
    @Override
    public void c() {
        this.entity.setPosition(loc.getX(), loc.getY(), loc.getZ());
    }
}
