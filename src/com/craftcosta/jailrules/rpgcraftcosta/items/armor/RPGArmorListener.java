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
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
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
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerEquipArmorFromHotBar(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        Material mat = p.getItemInHand().getType();
        if (mat.equals(Material.AIR) || mat == null) {
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_AIR) && rpgAMan.isRPGArmor(p.getItemInHand()))) {
            switch (mat) {
                case DIAMOND_HELMET:
                case IRON_HELMET:
                case GOLD_HELMET:
                case CHAINMAIL_HELMET:
                case LEATHER_HELMET:
                    if (p.getInventory().getHelmet() == null) {
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(p.getItemInHand()));
                    }
                    break;
                case DIAMOND_CHESTPLATE:
                case IRON_CHESTPLATE:
                case GOLD_CHESTPLATE:
                case CHAINMAIL_CHESTPLATE:
                case LEATHER_CHESTPLATE:
                    if (p.getInventory().getChestplate() == null) {
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(p.getItemInHand()));
                    }
                    break;
                case DIAMOND_LEGGINGS:
                case IRON_LEGGINGS:
                case GOLD_LEGGINGS:
                case CHAINMAIL_LEGGINGS:
                case LEATHER_LEGGINGS:
                    if (p.getInventory().getLeggings() == null) {
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(p.getItemInHand()));
                    }
                    break;
                case DIAMOND_BOOTS:
                case IRON_BOOTS:
                case GOLD_BOOTS:
                case CHAINMAIL_BOOTS:
                case LEATHER_BOOTS:
                    if (p.getInventory().getBoots() == null) {
                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(p.getItemInHand()));
                    }
                    break;
                default:
                    return;
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onPlayerEquipArmorFromInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        //Slot
        int rawSlot = event.getRawSlot();
        //Items
        ItemStack cursorItem = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();
        if (rawSlot > 8) {
            if (event.isShiftClick() && rpgAMan.isRPGArmor(currentItem)) {
                switch (currentItem.getType()) {
                    case DIAMOND_HELMET:
                    case IRON_HELMET:
                    case GOLD_HELMET:
                    case CHAINMAIL_HELMET:
                    case LEATHER_HELMET:
                        if (p.getInventory().getHelmet() == null) {
                            rpgP.addStats(rpgLMan.getListOfLoresFromItem(currentItem));
                            plugin.getLogger().info("adding stats of: " + currentItem.getItemMeta().getDisplayName());
                        }
                        break;
                    case DIAMOND_CHESTPLATE:
                    case IRON_CHESTPLATE:
                    case GOLD_CHESTPLATE:
                    case CHAINMAIL_CHESTPLATE:
                    case LEATHER_CHESTPLATE:
                        if (p.getInventory().getChestplate() == null) {
                            rpgP.addStats(rpgLMan.getListOfLoresFromItem(currentItem));
                            plugin.getLogger().info("adding stats of: " + currentItem.getItemMeta().getDisplayName());
                        }
                        break;
                    case DIAMOND_LEGGINGS:
                    case IRON_LEGGINGS:
                    case GOLD_LEGGINGS:
                    case CHAINMAIL_LEGGINGS:
                    case LEATHER_LEGGINGS:
                        if (p.getInventory().getLeggings() == null) {
                            rpgP.addStats(rpgLMan.getListOfLoresFromItem(currentItem));
                            plugin.getLogger().info("adding stats of: " + currentItem.getItemMeta().getDisplayName());
                        }
                        break;
                    case DIAMOND_BOOTS:
                    case IRON_BOOTS:
                    case GOLD_BOOTS:
                    case CHAINMAIL_BOOTS:
                    case LEATHER_BOOTS:
                        if (p.getInventory().getBoots() == null) {
                            rpgP.addStats(rpgLMan.getListOfLoresFromItem(currentItem));
                            plugin.getLogger().info("adding stats of: " + currentItem.getItemMeta().getDisplayName());
                        }
                        break;
                    default:
                        return;
                }

            }
        } else {
            if (rawSlot < 9 && rawSlot > 4) {
                //si es un slot de armadura
                switch (rawSlot) {
                    case 5:
                        if (cursorItem.getType().equals(Material.AIR)) {
                            if (!currentItem.getType().equals(Material.AIR)) {
                                rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                plugin.getLogger().info("substract stats of: " + currentItem.getItemMeta().getDisplayName());
                            }
                        } else {
                            if (currentItem.getType().equals(Material.AIR)) {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.getPart(cursorItem).equals("HELMET")) {
                                    rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                    plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName());
                                } else {
                                    event.setCancelled(true);
                                }
                            } else {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.isRPGArmor(currentItem)) {
                                    if (rpgAMan.isSamePart(cursorItem, currentItem)) {
                                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                        plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName()
                                                + " and substract stats of: " + currentItem.getItemMeta().getDisplayName());
                                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                    }
                                }
                            }
                        }
                        break;
                    case 6:
                        if (cursorItem.getType().equals(Material.AIR)) {
                            if (!currentItem.getType().equals(Material.AIR)) {
                                rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                plugin.getLogger().info("substract stats of: " + currentItem.getItemMeta().getDisplayName());
                            }
                        } else {
                            if (currentItem.getType().equals(Material.AIR)) {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.getPart(cursorItem).equals("CHESTPLATE")) {
                                    rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                    plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName());
                                } else {
                                    event.setCancelled(true);
                                }
                            } else {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.isRPGArmor(currentItem)) {
                                    if (rpgAMan.isSamePart(cursorItem, currentItem)) {
                                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                        plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName()
                                                + " and substract stats of: " + currentItem.getItemMeta().getDisplayName());
                                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                    }
                                }
                            }
                        }
                        break;
                    case 7:
                        if (cursorItem.getType().equals(Material.AIR)) {
                            if (!currentItem.getType().equals(Material.AIR)) {
                                rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                plugin.getLogger().info("substract stats of: " + currentItem.getItemMeta().getDisplayName());
                            }
                        } else {
                            if (currentItem.getType().equals(Material.AIR)) {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.getPart(cursorItem).equals("LEGGINGS")) {
                                    rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                    plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName());
                                } else {
                                    event.setCancelled(true);
                                }
                            } else {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.isRPGArmor(currentItem)) {
                                    if (rpgAMan.isSamePart(cursorItem, currentItem)) {
                                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                        plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName()
                                                + " and substract stats of: " + currentItem.getItemMeta().getDisplayName());
                                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                    }
                                }
                            }
                        }
                        break;
                    case 8:
                        if (cursorItem.getType().equals(Material.AIR)) {
                            if (!currentItem.getType().equals(Material.AIR)) {
                                rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                plugin.getLogger().info("substract stats of: " + currentItem.getItemMeta().getDisplayName());
                            }
                        } else {
                            if (currentItem.getType().equals(Material.AIR)) {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.getPart(cursorItem).equals("BOOTS")) {
                                    rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                    plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName());
                                } else {
                                    event.setCancelled(true);
                                }
                            } else {
                                if (rpgAMan.isRPGArmor(cursorItem) && rpgAMan.isRPGArmor(currentItem)) {
                                    if (rpgAMan.isSamePart(cursorItem, currentItem)) {
                                        rpgP.addStats(rpgLMan.getListOfLoresFromItem(cursorItem));
                                        plugin.getLogger().info("adding stats of: " + cursorItem.getItemMeta().getDisplayName()
                                                + " and substract stats of: " + currentItem.getItemMeta().getDisplayName());
                                        rpgP.subStats(rpgLMan.getListOfLoresFromItem(currentItem));
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerDropArmor(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (rpgAMan.isRPGArmor(e.getItemDrop().getItemStack())) {
            rpgPMan.checkAllEquipment(rpgP);
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerPickupArmor(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        plugin.getLogger().info("item: " + e.getItem().toString());
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (rpgAMan.isRPGArmor(e.getItem().getItemStack())) {
            rpgPMan.checkAllEquipment(rpgP);
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
