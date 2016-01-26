/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMob;
import org.bukkit.Chunk;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public class RPGMobSpawner {
    private String Id;
    private Location loc;
    private RPGMob rpgmob;
    private Chunk chunk;
    private int[] entitiesIds;
    public double cooldown;
    public Integer radio = 8;
    public Integer maxMobs;
}
