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
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jail
 */
public class RPGPartyListener implements Listener {

    RPGCraftCosta plugin;
    RPGPartyManager rpgPaMan;
    RPGPlayerManager rpgPMan;
    RPGChatManager rpgCMan;

    /**
     *
     * @param plugin
     */
    public RPGPartyListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        rpgPaMan= plugin.getRPGPartyManager();
        rpgPMan=plugin.getRPGPlayerManager();
        rpgCMan=plugin.getRPGChatManager();
    }
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e){
        RPGPlayer rpgp= rpgPMan.getRPGPlayerByName(e.getPlayer().getName());
        if(!rpgp.getParty().isEmpty()){
            if(rpgPaMan.getAllAvailableParties().contains(rpgp.getParty())){
                rpgPaMan.sendMessageToParty(rpgp.getParty(), " El compañero "+rpgp.getName()+" se ha desconectado del servidor");
            }else{
                rpgp.setParty("");
            }            
        }
    }
    
    @EventHandler
    public void onPlayerKicked(PlayerKickEvent e){
        RPGPlayer rpgp= rpgPMan.getRPGPlayerByName(e.getPlayer().getName());
        if(!rpgp.getParty().isEmpty()){
            if(rpgPaMan.getAllAvailableParties().contains(rpgp.getParty())){
                rpgPaMan.sendMessageToParty(rpgp.getParty(), " El compañero "+rpgp.getName()+" se ha desconectado del servidor");
            }else{
                rpgp.setParty("");
            }            
        }
    }
    
    @EventHandler
    public void onPlayerKillsMob(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        if (ent.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            //Mirar config si toca repartir money y exp.
            
        }

    }
}
