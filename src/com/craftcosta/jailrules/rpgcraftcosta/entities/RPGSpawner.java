/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import java.util.UUID;
import org.bukkit.Chunk;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public class RPGSpawner {
    private Integer Id;
    private Location loc;
    private RPGMob rpgmob;
    private UUID[] entitiesIds;
    public double cooldown;
    public Integer radio = 8;
    public Integer maxMobs;
}
