/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGWeaponListener implements Listener {

    RPGCraftCosta plugin;
    RPGWeaponManager rpgWMan;
    RPGPlayerManager rpgPMan;

    public RPGWeaponListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
//        //Bukkit.getLogger().info("Debug: Got PrepareItemCraftEvent");
//        HumanEntity entity = event.getViewers().get(0);
//        if ( !(entity instanceof Player) ) {
//            //Only player can move items currently, this is only for future proof code
//            return;
//        }
//        
//        CraftingInventory inv = event.getInventory();
//
//        CustomRecipe recipe = RecipeRegistry.getRecipe(inv);
//        if ( recipe == null ) {
//            if ( RecipeRegistry.hasBukkitRecipe(inv) ) {
//                event.getInventory().setResult(null);
//            } else {
//                //Code from old CraftingListener... temporary
//
//                ItemStack[] items = event.getInventory().getContents();
//                //Bukkit.getLogger().info("Debug: items is " + items.toString());
//                for (int i = 1; i < items.length; i++) {
//                    //i starts from 1 because 0 is crafting result (and maybe custom item)
//                    ItemStack stack = items[i];
//                    CustomStack item = null;
//                    try {
//                        item = new CustomStack(stack);
//                    } catch (NullItemException e) {
//                        //If itemStack is null (or air), there isn't any item, so...
//                        continue;
//                        //It can also avoid IllegalArgumentExceptions
//                    }
//                    if ( item.isCustom() ) {
//                        //Bukkit.getLogger().info("Debug: Stack is custom item");
//                        if ( item.getOriginalCustomItem().getAllowCrafting() ) {
//                            //If crafting is allowed:
//                            continue;
//                        } else {
//                            //Bukkit.getLogger().info("Debug: Crafting is disallowed");
//                            event.getInventory().setResult(null);
//                            return;
//                        }
//                    }
//                }
//            }
//        } else if ( !recipe.canCraft(entity) ) {
//            event.getInventory().setResult(null);
//        }
//
//        //entity.openInventory(inv);
//
//        //((Player) entity).updateInventory();
    }

    @EventHandler
    public void onPlayerEquipWeapon(PlayerItemHeldEvent e) {
        //Deberiamos comprobar que tenia antes y que tiene ahora
        //Si se trata de un arma cambiar cosas y si no pues no
        Player p = e.getPlayer();//
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        Inventory playerInv = p.getInventory();
        if (rpgWMan.isWeapon(playerInv.getItem(e.getPreviousSlot()))) {
            //Cambiar datos de player restando los del item anterior
            ItemStack item = playerInv.getItem(e.getPreviousSlot());
            ItemMeta iMeta = item.getItemMeta();
            List<String> lores = iMeta.getLore();
            for (String lore : lores) {
                String[] loreAttribute = lore.split(" ");
                String loreName = loreAttribute[0];
                switch (lore) {
                    case "Attack":
                        //loreAttribute[3]
                        Double.parseDouble(loreAttribute[3].substring(1));
                        //Modify RPGPlayer attribute
                        break;
                    case "Crit.":
                        //Cual de los dos tipos..
                        if (loreAttribute[2].equals("pct.")) {
                            Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                            //Modify RPGPlayer attribute
                        } else {
                            Double.parseDouble(loreAttribute[3].substring(1));
                            //Modify RPGPlayer attribute
                        }
                        break;
                    case "Health":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    case "XP":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    case "AP":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    case "Money":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    default:
                        break;
                }
            }
        }
        if (rpgWMan.isWeapon(playerInv.getItem(e.getNewSlot()))) {
            //Cambiar datos de player sumando los del item actual
            ItemStack item = playerInv.getItem(e.getNewSlot());
            ItemMeta iMeta = item.getItemMeta();
            List<String> lores = iMeta.getLore();
            for (String lore : lores) {
                String[] loreAttribute = lore.split(" ");
                String loreName = loreAttribute[0];

                switch (lore) {
                    case "Damage":
                        //loreAttribute[3]
                        Double.parseDouble(loreAttribute[3].substring(1));
                        //Modify RPGPlayer attribute
                        break;
                    case "Crit.":
                        //Cual de los dos tipos..
                        if (loreAttribute[2].equals("pct.")) {
                            Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                            //Modify RPGPlayer attribute
                        } else {
                            Double.parseDouble(loreAttribute[3].substring(1));
                            //Modify RPGPlayer attribute
                        }
                        break;
                    case "Health":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    case "XP":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    case "AP":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    case "Money":
                        Double.parseDouble(loreAttribute[3].substring(1, loreAttribute[3].length() - 1));
                        //Modify RPGPlayer attribute
                        break;
                    default:
                        break;
                }

            }
        }

    }

}
