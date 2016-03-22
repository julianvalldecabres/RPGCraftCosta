/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public class RPGSpawner {
    private Integer id;
    private Location loc;
    private RPGMob rpgmob;
    private List<UUID> entitiesIds;
    public double cooldown;
    public Integer radius = 8;
    public Integer maxMobs;
    public boolean enabled;

    RPGSpawner(int id,Location loc, RPGMob mob, int maxmobs, int radius, int cooldown) {
        this.id= id;
        this.loc=loc;
        this.rpgmob=mob;
        this.maxMobs=maxmobs;
        this.radius=radius;
        this.cooldown= cooldown;        
        this.enabled=true;
        this.entitiesIds=new ArrayList<>();
    }
    
    RPGSpawner(Location loc, RPGMob mob, int maxmobs, int radius, int cooldown, boolean enabled) {
        this.id= id;
        this.loc=loc;
        this.rpgmob=mob;
        this.maxMobs=maxmobs;
        this.radius=radius;
        this.cooldown= cooldown;        
        this.enabled=enabled;
        this.entitiesIds=new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public RPGMob getRpgmob() {
        return rpgmob;
    }

    public void setRpgmob(RPGMob rpgmob) {
        this.rpgmob = rpgmob;
    }

    public List<UUID> getEntitiesIds() {
        return entitiesIds;
    }

    public void setEntitiesIds(List<UUID> entitiesIds) {
        this.entitiesIds = entitiesIds;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getMaxMobs() {
        return maxMobs;
    }

    public void setMaxMobs(Integer maxMobs) {
        this.maxMobs = maxMobs;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
 
    
    public boolean hasMobWithUUID(UUID uuid){
        return this.entitiesIds.contains(uuid);
    }
}
