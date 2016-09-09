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
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

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
public class RPGWeaponListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGWeaponManager rpgWMan;
    private RPGPlayerManager rpgPMan;

    /**
     *
     * @param plugin
     */
    public RPGWeaponListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerUpgradeWeapon(InventoryClickEvent e) {
        ItemStack weaponupgrader = rpgWMan.getWeaponUpgrader();
        Player p = (Player) e.getWhoClicked();
        RPGPlayer rpgp=rpgPMan.getRPGPlayerByName(p.getName());
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
            rpgPMan.checkAllEquipment(rpgp);
        }
    }
}
