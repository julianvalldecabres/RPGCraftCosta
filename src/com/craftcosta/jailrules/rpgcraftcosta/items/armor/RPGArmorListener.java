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
package com.craftcosta.jailrules.rpgcraftcosta.items.armor;

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
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGArmorListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;
    private RPGArmorManager rpgAMan;
    private RPGLoreManager rpgLMan;

    /**
     *
     * @param plugin
     */
    public RPGArmorListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgAMan = plugin.getRPGItemManager().getRPGArmorManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerUpgradeArmor(InventoryClickEvent e) {
        ItemStack armorUpgrader = rpgAMan.getArmorUpgrader();
        Player p = (Player) e.getWhoClicked();
        RPGPlayer rpgp=rpgPMan.getRPGPlayerByName(p.getName());
        ItemStack cursor = e.getCursor();
        int slot = e.getRawSlot();
        ItemStack armorClicked = e.getCurrentItem();
        if (e.getCursor() == null || e.getCurrentItem() == null) {
            return;
        }
        if (cursor.getType().equals(Material.AIR) || armorClicked.getType().equals(Material.AIR)) {
            return;
        }
        RPGArmor rpgArmor = rpgAMan.getRPGArmorByItem(armorClicked);
        if (rpgArmor == null) {
            return;
        }
        if (!rpgArmor.isUpgradable()) {
            p.sendMessage(ChatColor.RED + "Esta armadura no se puede mejorar");
            return;
        }
        if (cursor.equals(armorUpgrader) && rpgAMan.isRPGArmor(armorClicked)) {
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
            rpgPMan.checkAllEquipment(rpgp);
        }
    }

    private ItemStack downgradeRPGArmor(ItemStack currentItem) {
        RPGArmor rpga = rpgAMan.getRPGArmorByItem(currentItem);
        return rpgAMan.downgradeArmor(currentItem);
        
    }

    private ItemStack improveRPGArmor(ItemStack currentItem) {
        RPGArmor rpga = rpgAMan.getRPGArmorByItem(currentItem);
        return rpgAMan.upgradeArmor(currentItem);
    }

}
