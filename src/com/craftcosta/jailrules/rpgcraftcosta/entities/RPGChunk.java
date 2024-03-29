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

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 *
 * @author jail
 */
public class RPGChunk implements Comparable<RPGChunk>{

    private int x;
    private int z;
    private World world;
    //AQUI

    /**
     *
     * @param x
     * @param z
     * @param world
     */
    public RPGChunk(int x, int z, World world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public int getZ() {
        return z;
    }

    /**
     *
     * @param z
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     *
     * @return
     */
    public World getWorld() {
        return world;
    }

    /**
     *
     * @param world
     */
    public void setWorldByName(String world) {
        this.world = Bukkit.getServer().getWorld(world);
    }

    /**
     *
     * @param world
     */
    public void setWorld(World world) {
        this.world = world;

    }
    
    @Override
    public String toString(){
        return this.world.getName()+" "+this.x+" "+this.z;
    }

    @Override
    public int compareTo(RPGChunk o) {        
        if(this.x==o.x && this.z==z && this.world.equals(o.world)){return 0;}
        return -1;
    }
    
    @Override
    public boolean equals(Object o){
        if(o==null)return false;
        final RPGChunk chunk=(RPGChunk)o;
        return (this.x==chunk.x && this.z==chunk.z && this.world.equals(chunk.world));
    }

}
