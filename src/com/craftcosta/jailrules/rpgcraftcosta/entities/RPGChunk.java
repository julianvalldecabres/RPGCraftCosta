/*
 * Copyright (C) 2016 jail
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
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

/**
 *
 * @author jail
 */
public class RPGChunk {
    private int x;
    private int z;
    private World world;
    //AQUI

    public RPGChunk(int x, int z, World world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public World getWorld() {
        return world;
    }

    public void setWorldByName(String world) {
        this.world=Bukkit.getServer().getWorld(world);
    }
    
    public void setWorld(World world){
        this.world= world;
                
    }
    
    public RPGChunk getRPGChunkfromChunk(Chunk chunk){
        return new RPGChunk(chunk.getX(), chunk.getZ(), chunk.getWorld());
    }
}
