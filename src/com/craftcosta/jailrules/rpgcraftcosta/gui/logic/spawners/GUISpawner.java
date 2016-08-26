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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.spawners;

/**
 *
 * @author jail
 */
public class GUISpawner {
    private String name;
    private String world;
    private int x;
    private int y;
    private int z;
    private int radius;
    private String mobname;
    private int maxmobs;
    private int cooldown;
    private boolean enabled;

    public GUISpawner(String name, String world, int x, int y, int z, String mobname, int maxmobs, int cooldown, boolean enabled, int radius) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mobname = mobname;
        this.maxmobs = maxmobs;
        this.cooldown = cooldown;
        this.enabled = enabled;
        this.radius=radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getMobname() {
        return mobname;
    }

    public void setMobname(String mobname) {
        this.mobname = mobname;
    }

    public int getMaxmobs() {
        return maxmobs;
    }

    public void setMaxmobs(int maxmobs) {
        this.maxmobs = maxmobs;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
}
