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
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.ChatColor;
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
public class RPGGuildListener implements Listener {

    RPGCraftCosta plugin;
    RPGGuildManager rpgGMan;
    RPGPlayerManager rpgPMan;
    RPGChatManager rpgCMan;

    public RPGGuildListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgGMan = plugin.getRPGGuildManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgCMan = plugin.getRPGChatManager();
    }

    @EventHandler
    public void onPlayerKillsMob(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        if (ent.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            //Mirar config si toca robar money del mob xD

        }

    }

    @EventHandler
    public void onPlayerJoin(OnRPGPlayerJoinEvent e) {
        RPGPlayer rpgp = e.getRPGPlayer();
        if (e.getRPGPlayer().getGuild()!=null) {
            if (!rpgGMan.getAllAvailableGuilds().contains(rpgp.getGuild())) {
                rpgp.getPlayer().sendMessage(rpgCMan.getPrefixForGuild() + ChatColor.RED + " El clan " + rpgp.getGuild() + " ha sido disuelto");
                rpgp.setGuild("");
            } else {
                rpgGMan.getGuildByName(rpgp.getGuild()).addOnlinePlayer(rpgp.getPlayer());
                rpgGMan.sendMessageToGuild(rpgp.getGuild(), " El camarada " + rpgp.getName() + " se ha conectado al servidor");
            }
        }
    }
}
