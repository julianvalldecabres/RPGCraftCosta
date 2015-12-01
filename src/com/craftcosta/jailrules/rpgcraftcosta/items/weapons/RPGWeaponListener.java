/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
                    e.setCurrentItem(rpgWMan.downgradeWeapon(e.getCurrentItem()));
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
                    e.setCurrentItem(rpgWMan.upgradeWeapon(e.getCurrentItem()));
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
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        Player p = e.getPlayer();//
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        rpgP.setSlotSelected(e.getNewSlot() + 36);
        Inventory playerInv = p.getInventory();
        if (playerInv.getItem(e.getPreviousSlot()) != null) {
            if (rpgWMan.isWeapon(playerInv.getItem(e.getPreviousSlot()))) {
                ItemStack item = playerInv.getItem(e.getPreviousSlot());
                rpgP.subStats(rpgLMan.getListOfLoresFromItem(item));
            }
        }
        if (playerInv.getItem(e.getNewSlot()) != null) {
            if (rpgWMan.isWeapon(playerInv.getItem(e.getNewSlot()))) {
                ItemStack item = playerInv.getItem(e.getNewSlot());
                rpgP.addStats(rpgLMan.getListOfLoresFromItem(item));
            }
        }
    }

    @EventHandler
    public void onPlayerChangeFromInventoryWeaponHeldItem(InventoryClickEvent e) {
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        Player p = (Player) e.getWhoClicked();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (rpgP.getSlotSelected() == e.getRawSlot()) {
            if (e.getCurrentItem().getType().equals(Material.AIR)) {
                if (!e.getCursor().getType().equals(Material.AIR)) {
                    if (rpgWMan.isRPGWeapon(e.getCursor())) {
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(e.getCursor()));
                    }
                }
            } else {
                if (e.getCursor().getType().equals(Material.AIR)) {
                    if (rpgWMan.isRPGWeapon(e.getCurrentItem())) {
                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(e.getCurrentItem()));
                    }
                } else {
                    if (rpgWMan.isRPGWeapon(e.getCurrentItem())) {
                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(e.getCurrentItem()));
                    }
                    if (rpgWMan.isRPGWeapon(e.getCursor())) {
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(e.getCursor()));
                    }
                }
            }

        }
    }
    
    @EventHandler
    public void onPlayerDropWeapon(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        RPGPlayer rpgP= rpgPMan.getRPGPlayerByName(p.getName());
        plugin.getLogger().info(e.getItemDrop().getName());
        if(rpgWMan.isRPGWeapon(e.getItemDrop().getItemStack())){
            rpgPMan.checkAllEquipment(rpgP);
        }
    }
    
    @EventHandler
    public void onPlayerPickupWeapon(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        RPGPlayer rpgP= rpgPMan.getRPGPlayerByName(p.getName());
        if(rpgWMan.isRPGWeapon(e.getItem().getItemStack())){
            rpgPMan.checkAllEquipment(rpgP);
        }
    }
}
