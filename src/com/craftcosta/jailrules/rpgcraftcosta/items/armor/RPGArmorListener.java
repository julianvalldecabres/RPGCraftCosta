/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.armor;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
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
public class RPGArmorListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;
    private RPGArmorManager rpgAMan;

    public RPGArmorListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgAMan = plugin.getRPGItemManager().getRPGArmorManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    @EventHandler
    public void onPlayerUpgradeArmor(InventoryClickEvent e) {
        ItemStack armorUpgrader = rpgAMan.getArmorUpgrader();
        Player p = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        int slot = e.getRawSlot();
        ItemStack armorClicked = e.getCurrentItem();
        if (e.getCursor() == null || e.getCurrentItem() == null) {
            return;
        }
        if (cursor.getType().equals(Material.AIR) || armorClicked.getType().equals(Material.AIR)) {
            return;
        }
        if (cursor.equals(armorUpgrader) && rpgAMan.isRPGWeapon(armorClicked)) {
            e.setCancelled(true);
            switch (rpgAMan.getUpgradeResult()) {
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
                    e.setCurrentItem(downgradeRPGArmor(e.getCurrentItem()));
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
                    e.setCurrentItem(improveRPGArmor(e.getCurrentItem()));
                    p.sendMessage(ChatColor.AQUA + "Enhorabuena! Tu arma ha sido mejorada");
                    break;
                default:
                    p.sendMessage(ChatColor.RED + "Es posible que haya ocurrido un error. Pongase en contacto con un administrador");
                    break;
            }
            p.updateInventory();
        }
    }

    private ItemStack downgradeRPGArmor(ItemStack currentItem) {
        RPGArmor rpga = rpgAMan.getRPGArmorByItem(currentItem);
        return rpga.downgradeArmor(currentItem);
    }

    private ItemStack improveRPGArmor(ItemStack currentItem) {
        RPGArmor rpga = rpgAMan.getRPGArmorByItem(currentItem);
        return rpga.upgradeArmor(currentItem);
    }
}
