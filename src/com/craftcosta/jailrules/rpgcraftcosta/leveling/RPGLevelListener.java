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
package com.craftcosta.jailrules.rpgcraftcosta.leveling;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMob;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMobManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author jail
 */
public class RPGLevelListener implements Listener{
    RPGCraftCosta plugin;
    RPGLevelManager rpgLMan;
    RPGPlayerManager rpgPMan;
    RPGMobManager rpgMMan;
    RPGClassManager rpgCMan;

    public RPGLevelListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgLMan= plugin.getRPGLevelManager();
        this.rpgMMan= plugin.getRPGMobManager();
        this.rpgPMan= plugin.getRPGPlayerManager();
        this.rpgCMan= plugin.getRPGClassManager();
        
    }
    
    @EventHandler
    public void onPlayerKillsMonster(EntityDeathEvent e){
        Entity ent = e.getEntity();
            
		if(ent.getLastDamageCause() instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) ent.getLastDamageCause();
			if(nEvent.getDamager() instanceof Player)
			{
                          RPGPlayer rpgp=rpgPMan.getRPGPlayerByName(((Player)nEvent.getDamager()).getName());
                          if(!(nEvent.getEntity() instanceof Player)){
                              String mobName=nEvent.getEntity().getCustomName();
                              RPGMob mob= rpgMMan.getRPGMobByName(mobName);
                              //Comprobar añadir exp y subir de nivel si toca.
                              if(rpgLMan.checkLevelUp(rpgp.getActualExp(), (long) mob.exp)){
                                  //Subir de nivel
                                  rpgp.setActualExp(rpgp.getActualExp()+mob.exp);
                                  rpgCMan.levelUP(rpgp);
                              }else{}
                                  rpgp.setActualExp(rpgp.getActualExp()+mob.exp);
                          }
                        }
                }
    }
}
