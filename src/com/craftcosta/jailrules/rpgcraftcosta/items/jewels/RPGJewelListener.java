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
package com.craftcosta.jailrules.rpgcraftcosta.items.jewels;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGJewelListener implements Listener {

    RPGCraftCosta plugin;
    RPGJewelManager rpgJMan;
    RPGPlayerManager rpgPMan;

    /**
     *
     * @param plugin
     */
    public RPGJewelListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgJMan = plugin.getRPGItemManager().getRPGJewelManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerCombineJewels(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
        ItemStack cursor = e.getCursor();
        int slot = e.getRawSlot();
        ItemStack currentItem = e.getCurrentItem();
        if (e.getCursor() == null || e.getCurrentItem() == null) {
            return;
        }
        if (cursor.getType().equals(Material.AIR) || currentItem.getType().equals(Material.AIR)) {
            return;
        }
        if (rpgJMan.isRPGJewel(cursor) && rpgJMan.isRPGJewel(currentItem)) {
            e.setCancelled(true);
            RPGJewel jewel1 = rpgJMan.getRPGJewelByItem(cursor);
            RPGJewel jewel2 = rpgJMan.getRPGJewelByItem(currentItem);
            if (jewel1.isCombinable() && jewel2.isCombinable()) {
                //Ambas son combinables
                if (jewel1.getName().equals(jewel2.getName())) {
                    //Ambas tienen el mismo nombre
                    if (cursor.getAmount() == 1 && currentItem.getAmount() == 1) {
                        switch (rpgJMan.upgradeJewel()) {
                            case "break":
                                //Romper ambos items
                                e.setCurrentItem(new ItemStack(Material.AIR));
                                e.setCursor(new ItemStack(Material.AIR));
                                p.sendMessage(ChatColor.RED + "Se han roto ambas joyas en el proceso de combinacion");
                                break;
                            case "lose":
                                //Combinan pero pierden lores
                                e.setCurrentItem(rpgJMan.combineloseJewels(cursor, currentItem));
                                e.setCursor(new ItemStack(Material.AIR));
                                p.sendMessage(ChatColor.YELLOW + "Se han combinado ambas joyas");

                                break;
                            case "nothing":
                                //se combinan pero no suman lores iguales
                                e.setCurrentItem(rpgJMan.combineNoSumJewels(cursor, currentItem));
                                e.setCursor(new ItemStack(Material.AIR));
                                p.sendMessage(ChatColor.GREEN + "Se han combinado ambas joyas");

                                break;
                            case "combine":
                                //se combinan
                                e.setCurrentItem(rpgJMan.combineJewels(cursor, currentItem));
                                e.setCursor(new ItemStack(Material.AIR));
                                p.sendMessage(ChatColor.AQUA + "La combinación de ambas joyas ha sido todo un exito!");

                                break;
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Estas joyas no se pueden combinar deben ser del mismo tipo");
                    return;
                }
            } else {
                p.sendMessage(ChatColor.RED + "Estas joyas no se pueden combinar");
                return;
            }
        }
        rpgPMan.checkAllEquipment(rpgp);
    }
}