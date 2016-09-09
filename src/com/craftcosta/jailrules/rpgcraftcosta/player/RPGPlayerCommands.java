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
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGPlayerCommands implements CommandExecutor, TabCompleter {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;

    /**
     *
     * @param plugin
     */
    public RPGPlayerCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    /**
     *
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        RPGPlayer rpgP = this.rpgPMan.getRPGPlayerByName(p.getName());
        if (label.equalsIgnoreCase("player")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Use /player help para mostrar la ayuda");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("info")) {
                    p.sendMessage(ChatColor.YELLOW + "----Stats del jugador " + rpgP.getName() + "----");
                    p.sendMessage(ChatColor.YELLOW + "level: " + rpgP.getActualLevel());
                    p.sendMessage(ChatColor.YELLOW + "experience: " + rpgP.getActualExp() + "/" + plugin.getRPGLevelManager().getPercentageForNextLevel(rpgP.getActualExp()) + " " + plugin.getRPGLevelManager().getPercentageForNextLevel(rpgP.getActualExp()));
                    p.sendMessage(ChatColor.YELLOW + "class: " + rpgP.getPlayerClass());
                    p.sendMessage(ChatColor.YELLOW + "guild: " + rpgP.getGuild());
                    p.sendMessage(ChatColor.YELLOW + "party: " + rpgP.getParty());
                    p.sendMessage(ChatColor.YELLOW + "max health: " + rpgP.getFinalMaxHealth());
                    p.sendMessage(ChatColor.YELLOW + "actual health: " + rpgP.getActualHealth());
                    p.sendMessage(ChatColor.YELLOW + "physical attack: " + rpgP.getFinalphysicalAttack());
                    p.sendMessage(ChatColor.YELLOW + "physical defense: " + rpgP.getFinalphysicalDefense());
                    p.sendMessage(ChatColor.YELLOW + "physical evasion: " + rpgP.getFinalphysicalEvasion());
                    p.sendMessage(ChatColor.YELLOW + "physical hitrate: " + rpgP.getFinalphysicalHitRate());
                    p.sendMessage(ChatColor.YELLOW + "magical attack: " + rpgP.getFinalmagicalAttack());
                    p.sendMessage(ChatColor.YELLOW + "magical defense: " + rpgP.getFinalmagicalDefense());
                    p.sendMessage(ChatColor.YELLOW + "magical evasion: " + rpgP.getFinalmagicalEvasion());
                    p.sendMessage(ChatColor.YELLOW + "magical hitrate: " + rpgP.getFinalmagicalHitRate());
                    p.sendMessage(ChatColor.YELLOW + "critical: " + rpgP.getFinalcritical());

                }
                if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage(ChatColor.YELLOW + "----- Player Help----");
                    p.sendMessage(ChatColor.YELLOW + "Usa /player info para mostrar la información de tu personaje");
                    p.sendMessage(ChatColor.YELLOW + "Usa /player help para mostrar esta ayuda");
                }
                if (args[0].equalsIgnoreCase("kit")) {
                    for(String armor:plugin.getRPGItemManager().getRPGArmorManager().getAllArmorNames())
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGArmor(armor)));
                    for(String weapon:plugin.getRPGItemManager().getRPGWeaponManager().getAllWeaponNames())
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGWeapon(weapon)));
                    for(String jewel:plugin.getRPGItemManager().getRPGJewelManager().getAllJewelNames()){
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGItem(jewel,ItemType.JEWEL)));
                    }
                    for(String object:plugin.getRPGItemManager().getRPGQMan().getAllObjectNames()){
                        plugin.getLogger().info("nombre de objeto: "+object);
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getRPGQMan().getRPGQuestItemByName(object)));
                    }
                    for(String upgrader:plugin.getRPGItemManager().getUpgradersNames()){
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                        p.getInventory().addItem(new ItemStack(plugin.getRPGItemManager().getUpgraderByName(upgrader)));
                    }
                    
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("info")) {
                    rpgP = rpgPMan.getRPGPlayerByName(args[1]);
                    if (rpgP == null) {
                        p.sendMessage(ChatColor.RED + "El jugador " + args[1] + " no ha sido encontrado");
                    } else {
                        p.sendMessage(ChatColor.YELLOW + "----Stats del jugador " + rpgP.getName() + "----");
                        p.sendMessage(ChatColor.YELLOW + "level: " + rpgP.getActualLevel());
                        p.sendMessage(ChatColor.YELLOW + "experience: " + rpgP.getActualExp() + "/" + plugin.getRPGLevelManager().getPercentageForNextLevel(rpgP.getActualExp()) + " " + plugin.getRPGLevelManager().getPercentageForNextLevel(rpgP.getActualExp()));
                        p.sendMessage(ChatColor.YELLOW + "class: " + rpgP.getPlayerClass());
                        p.sendMessage(ChatColor.YELLOW + "guild: " + rpgP.getGuild());
                        p.sendMessage(ChatColor.YELLOW + "party: " + rpgP.getParty());
                        p.sendMessage(ChatColor.YELLOW + "max health: " + rpgP.getFinalMaxHealth());
                        p.sendMessage(ChatColor.YELLOW + "actual health: " + rpgP.getActualHealth());
                        p.sendMessage(ChatColor.YELLOW + "physical attack: " + rpgP.getFinalphysicalAttack());
                        p.sendMessage(ChatColor.YELLOW + "physical defense: " + rpgP.getFinalphysicalDefense());
                        p.sendMessage(ChatColor.YELLOW + "physical evasion: " + rpgP.getFinalphysicalEvasion());
                        p.sendMessage(ChatColor.YELLOW + "physical hitrate: " + rpgP.getFinalphysicalHitRate());
                        p.sendMessage(ChatColor.YELLOW + "magical attack: " + rpgP.getFinalmagicalAttack());
                        p.sendMessage(ChatColor.YELLOW + "magical defense: " + rpgP.getFinalmagicalDefense());
                        p.sendMessage(ChatColor.YELLOW + "magical evasion: " + rpgP.getFinalmagicalEvasion());
                        p.sendMessage(ChatColor.YELLOW + "magical hitrate: " + rpgP.getFinalmagicalHitRate());
                        p.sendMessage(ChatColor.YELLOW + "critical: " + rpgP.getFinalcritical());
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmnd, String label, String[] args) {
        List<String> lista = new ArrayList<>();
        if (label.equalsIgnoreCase("player")) {
            if (args.length == 0 || args.length == 1) {
                lista.add("help");
                lista.add("info");
                lista.add("kit");
                return lista;
            } else if (args.length == 2) {
                for (Map.Entry<String, RPGPlayer> entrySet : rpgPMan.getPlayersOnline().entrySet()) {
                    lista.add(entrySet.getKey());
                }
                return lista;
            }
        }
        return null;
    }

}
