/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGWeaponListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGWeaponManager rpgWMan;
    private RPGPlayerManager rpgPMan;

    public RPGWeaponListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    @EventHandler
    public void onPlayerUpgradeWeapon(InventoryClickEvent e) {
        ItemStack weaponupgrader = rpgWMan.getWeaponUpgrader();
        Player p = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        int slot = e.getRawSlot();
        ItemStack weaponclicked = e.getCurrentItem();
        if (e.getCursor() == null || e.getCurrentItem() == null) {
            return;
        }
        if (cursor.getType().equals(Material.AIR) || weaponclicked.getType().equals(Material.AIR)) {
            return;
        }
        if (cursor.equals(weaponupgrader) && rpgWMan.isRPGWeapon(weaponclicked)) {
            e.setCancelled(true);
            switch (rpgWMan.getUpgradeResult()) {
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
                    p.sendMessage(ChatColor.RED + "Es posible que haya ocurrido un error. Pongase en contacto con un administrador");
                    break;
            }
            p.updateInventory();
        }
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
