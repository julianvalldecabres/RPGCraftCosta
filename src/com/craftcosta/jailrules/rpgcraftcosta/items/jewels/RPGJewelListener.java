/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.jewels;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGJewelListener implements Listener {

    RPGCraftCosta plugin;
    RPGJewelManager rpgJMan;
    RPGPlayerManager rpgPMan;

    public RPGJewelListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgJMan = plugin.getRPGItemManager().getRPGJewelManager();
        this.rpgPMan=plugin.getRPGPlayerManager();
    }

    @EventHandler
    public void onPlayerCombineJewels(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
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
        return;
    }

    @EventHandler
    public void onPlayerEquipJewels(InventoryClickEvent event) {
        RPGLoreManager rpgLMan= plugin.getRPGItemManager().getRPGLoreManager();
        Player p = (Player) event.getWhoClicked();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if(event.getRawSlot() >35 && event.getRawSlot()<45){
            if(event.getCursor().getType().equals(Material.AIR)){
                if(!event.getCurrentItem().getType().equals(Material.AIR)){
                    if(rpgJMan.isRPGJewel(event.getCurrentItem())){
                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(event.getCurrentItem()));
                    }
                }
            }else{
                if(event.getCurrentItem().getType().equals(Material.AIR)){
                    if(rpgJMan.isRPGJewel(event.getCursor())){
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(event.getCursor()));
                    }
                }else{
                    if(rpgJMan.isRPGJewel(event.getCurrentItem())){
                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(event.getCurrentItem()));
                    }
                    if(rpgJMan.isRPGJewel(event.getCursor())){
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(event.getCursor()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPickupJewel(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (rpgJMan.isRPGJewel(event.getItem().getItemStack())) {
            rpgPMan.checkAllEquipment(rpgP);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (rpgJMan.isRPGJewel(event.getItemDrop().getItemStack())) {
            rpgPMan.checkAllEquipment(rpgP);
        }
    }
}
