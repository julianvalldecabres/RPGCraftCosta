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
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;


/**
 *
 * @author jail
 */
public class RPGSpawner implements Runnable{

    private String id;
    private Location loc;
    private String rpgmob;
    private List<Integer> entitiesIds;
    public double cooldown;
    public Integer radius = 8;
    public Integer maxMobs;
    public boolean enabled;

    public RPGSpawner(String id, Location loc, String mob, int maxmobs, int radius, int cooldown) {
        this.id = id;
        this.loc = loc;
        this.rpgmob = mob;
        this.maxMobs = maxmobs;
        this.radius = radius;
        this.cooldown = cooldown;
        this.enabled = true;
        this.entitiesIds = new ArrayList<>();
    }

    public RPGSpawner(String id,Location loc, String mob, int maxmobs, int radius, int cooldown, boolean enabled) {
        this.id = id;
        this.loc = loc;
        this.rpgmob = mob;
        this.maxMobs = maxmobs;
        this.radius = radius;
        this.cooldown = cooldown;
        this.enabled = enabled;
        this.entitiesIds = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Location getLoc() {
        return loc;
    }

    /**
     *
     * @param loc
     */
    public void setLoc(Location loc) {
        this.loc = loc;
    }

    /**
     *
     * @return
     */
    public String getRpgmob() {
        return rpgmob;
    }

    /**
     *
     * @param rpgmob
     */
    public void setRpgmob(String rpgmob) {
        this.rpgmob = rpgmob;
    }

    /**
     *
     * @return
     */
    public List<Integer> getEntitiesIds() {
        return entitiesIds;
    }

    /**
     *
     * @param entitiesIds
     */
    public void setEntitiesIds(List<Integer> entitiesIds) {
        this.entitiesIds = entitiesIds;
    }

    /**
     *
     * @return
     */
    public double getCooldown() {
        return cooldown;
    }

    /**
     *
     * @param cooldown
     */
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    /**
     *
     * @return
     */
    public Integer getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     */
    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    public Integer getMaxMobs() {
        return maxMobs;
    }

    /**
     *
     * @param maxMobs
     */
    public void setMaxMobs(Integer maxMobs) {
        this.maxMobs = maxMobs;
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @param uuid
     * @return
     */
    public boolean hasMobWithUUID(Integer uuid) {
        return this.entitiesIds.contains(uuid);
    }

    void start() {
        
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
