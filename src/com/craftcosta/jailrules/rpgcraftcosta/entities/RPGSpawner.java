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

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;


/**
 *
 * @author jail
 */
public class RPGSpawner {
  private RPGCraftCosta plugin;
    private String id;
    private Location loc;
    private String rpgmob;
    private List<UUID> entitiesUUIDS;
    public Integer cooldown;
    public Integer radius = 8;
    public Integer maxMobs;
    public boolean enabled;

    public RPGSpawner(RPGCraftCosta plugin,String id, Location loc, String mob, int maxmobs, int radius, int cooldown) {
        this.plugin=plugin;
        this.id = id;
        this.loc = loc;
        this.rpgmob = mob;
        this.maxMobs = maxmobs;
        this.radius = radius;
        this.cooldown = cooldown;
        this.enabled = true;
        this.entitiesUUIDS= new ArrayList<>();
    }

    public RPGSpawner(RPGCraftCosta plugin,String id,Location loc, String mob, int maxmobs, int radius, int cooldown, boolean enabled) {
        this.plugin=plugin;
        
        this.id = id;
        this.loc = loc;
        this.rpgmob = mob;
        this.maxMobs = maxmobs;
        this.radius = radius;
        this.cooldown = cooldown;
        this.enabled = enabled;
        this.entitiesUUIDS= new ArrayList<>();
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
    public int getCooldown() {
        return cooldown;
    }

    /**
     *
     * @param cooldown
     */
    public void setCooldown(int cooldown) {
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
    public boolean hasMobWithUUID(UUID uuid) {
        return this.entitiesUUIDS.contains(uuid);
    }



    

    public List<UUID> getEntitiesUUIDS() {
        return entitiesUUIDS;
    }

    public void setEntitiesUUIDS(List<UUID> entitiesUUIDS) {
        this.entitiesUUIDS = entitiesUUIDS;
    }
    
    

}
