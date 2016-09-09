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
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author jail
 */
public class RPGSpawnerBukkitRunnable extends BukkitRunnable{
    private RPGSpawner rpgs;
    private RPGCraftCosta plugin;

    public RPGSpawnerBukkitRunnable(RPGSpawner rpgs, RPGCraftCosta plugin) {
        this.rpgs = rpgs;
        this.plugin = plugin;
    }
    
    
    @Override
    public void run() {
        if(rpgs.enabled){
            while(rpgs.getEntitiesUUIDS().size()<rpgs.getMaxMobs()){
                RPGMob rpgm=plugin.getRPGMobManager().getRPGMobByName(rpgs.getRpgmob());
                Entity ent=plugin.getRPGMobManager().spawnRPGMobAtLocation(rpgm, rpgs.getLoc());
                plugin.getRPGMobManager().getEntities().put(ent.getUniqueID(),rpgs.getId());
                rpgs.getEntitiesUUIDS().add(ent.getUniqueID());
            }
        }
    
}
    
}
