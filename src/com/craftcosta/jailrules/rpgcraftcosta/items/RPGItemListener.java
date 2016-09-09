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
package com.craftcosta.jailrules.rpgcraftcosta.items;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 *
 * @author jail
 */
public class RPGItemListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;

    /**
     *
     * @param plugin
     */
    public RPGItemListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onPlayerEquip(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        rpgPMan.checkAllEquipment(rpgP);
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        rpgPMan.checkAllEquipment(rpgP);
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        rpgPMan.checkAllEquipment(rpgP);

    }
}
