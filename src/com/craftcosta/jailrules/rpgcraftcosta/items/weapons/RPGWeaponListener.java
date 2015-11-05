/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.recipes.RPGRecipeManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGWeaponListener implements Listener {

    RPGCraftCosta plugin;
    RPGWeaponManager rpgWMan;
    RPGPlayerManager rpgPMan;
    RPGRecipeManager rpgRMan;

    public RPGWeaponListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgRMan = plugin.getRPGRecipeManager();
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
    public void onPlayerTriesToUpgradeWeapon(CraftItemEvent event) {
//        plugin.getLogger().info("entro");
//
//        ItemStack weaponUpgrade = rpgRMan.getWeaponUpgrader();
//        ShapelessRecipe shapelessReceta = (ShapelessRecipe) event.getRecipe();
//        ItemStack[] craftingItems = event.getInventory().getMatrix();
//        ItemStack weapon = null;//Obtenidos de la matriz de crafteo
//        ItemStack upgrader = null;//Obtenido de la matriz de craftteo
//        //Obtenemos los items usados en la receta ya sea en la mesa o inventario de crafteo
//        int count = craftingItems.length;
//
//        for (ItemStack craftingItem : craftingItems) {
//            //Comprobar que los items son compatibles con los del RPG
//            if (craftingItem.equals(weaponUpgrade)) {
//                upgrader = craftingItem;
//            }
//            if (rpgWMan.isWeapon(craftingItem)) {
//                weapon = craftingItem;
//            }
//        }
//        if (weapon.equals(null) || upgrader.equals(null)) {
//            event.setCancelled(true);
//        }
//
//        //Con esto modificamos el objeto para que se vea como seria si la mejora funciona
//        event.getInventory().setResult(weapon);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
//        plugin.getLogger().info("entro");
//        ItemStack weaponupgrader= rpgRMan.getWeaponUpgrader();
//        ItemStack weapon=null;
//        CraftingInventory inv = event.getInventory();
//        ItemStack[] items = inv.getContents();
//        for (ItemStack item : items) {
//            if(item!=null) System.out.println(item.toString());
//        }
//        if(RPGPlayerUtils.checkProbability(0.6)){
//            event.getInventory().setResult(new ItemStack(Material.AIR));
//        }

    }

    @EventHandler
    public void onPlayerUpgradeWeapon(InventoryClickEvent e) {
        ItemStack weaponupgrader = rpgRMan.getWeaponUpgrader();
        Player p = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        int slot = e.getRawSlot();
        ItemStack weaponclicked = e.getCurrentItem();
        if (cursor.equals(weaponupgrader) && rpgWMan.isRPGWeapon(weaponclicked)) {
            //Cancelamos el evento
            e.setCancelled(true);
            //Lo vamos a complicar mas hay 4 posibles resultados de una mejora:
            // - mejora
            // - rompe
            // - empeora
            // - nada
            //en la configuracion guardaremos los rangos de rotura, mejora, desmejora,o nada

            switch (rpgRMan.getUpgradeResult()) {
                case "break":
                    //rotura del arma y consumo del item de mejora
                    e.setCurrentItem(new ItemStack(Material.AIR));
                    e.setCursor(new ItemStack(Material.AIR));
                    p.sendMessage(ChatColor.RED + "Tu arma se ha roto en el proceso de mejora");
                    break;
                case "detteriorate":
                    //consumo del item de mejora
                    e.setCursor(new ItemStack(Material.AIR));
                    //downgrade del arma
                    e.setCurrentItem(downgradeRPGWeapon(e.getCurrentItem()));
                    p.sendMessage(ChatColor.RED + "El proceso de mejora de tu arma ha resultado en una desmejora");
                    break;
                case "nothing":
                    //consumo del cursor
                    e.setCursor(new ItemStack(Material.AIR));
                    p.sendMessage(ChatColor.RED + "El proceso de mejora de tu arma no ha tenido efecto");
                    break;
                case "improve":
                    //consumo del cursor
                    e.setCursor(new ItemStack(Material.AIR));
                    //mejora del arma
                    e.setCurrentItem(improveRPGWeapon(e.getCurrentItem()));
                    p.sendMessage(ChatColor.AQUA + "Enhorabuena! Tu arma ha sido mejorada");
                    break;
                default:
                    //informar de posible error
                    break;

            }
            p.updateInventory();
        } else {
            return;
        }

        return;
    }

    @EventHandler
    public void onPlayerEquipWeapon(PlayerItemHeldEvent e) {
        //Deberiamos comprobar que tenia antes y que tiene ahora
        //Si se trata de un arma cambiar cosas y si no pues no
        Player p = e.getPlayer();//
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        Inventory playerInv = p.getInventory();

        if (playerInv.getItem(e.getPreviousSlot()) != null) {
            if (rpgWMan.isWeapon(playerInv.getItem(e.getPreviousSlot()))) {
                //Cambiar datos de player restando los del item anterior
                ItemStack item = playerInv.getItem(e.getPreviousSlot());
                ItemMeta iMeta = item.getItemMeta();
                if (iMeta.hasLore()) {
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
            }
        }
        if (playerInv.getItem(e.getNewSlot()) != null) {
            if (rpgWMan.isWeapon(playerInv.getItem(e.getNewSlot()))) {
                //Cambiar datos de player sumando los del item actual
                ItemStack item = playerInv.getItem(e.getNewSlot());
                ItemMeta iMeta = item.getItemMeta();
                if (iMeta.hasLore()) {
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

    }

    private ItemStack downgradeRPGWeapon(ItemStack currentItem) {
        RPGWeapon rpgw = rpgWMan.getRPGWeaponByItem(currentItem);
        return rpgw.downgradeWeapon(currentItem);
    }

    private ItemStack improveRPGWeapon(ItemStack currentItem) {
        RPGWeapon rpgw = rpgWMan.getRPGWeaponByItem(currentItem);
        return rpgw.upgradeWeapon(currentItem);
    }

}
