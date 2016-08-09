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
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author jail
 */
public class RPGPlayerUtils {

    /**
     *
     * @param name
     * @return
     */
    public static boolean isPlayerOnline(String name) {
        return Bukkit.getServer().getPlayer(name) != null;
    }

    /**
     *
     * @param p
     * @return
     */
    public static boolean existsPlayerFile(Player p) {
        File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", p.getUniqueId().toString()));
        return playerFile.exists();
    }

    /**
     *
     * @param player
     * @param message
     */
    public static void sendMessageToPlayer(String player, String message) {
        Bukkit.getServer().getPlayer(player).sendMessage(message);
    }

    /**
     *
     * @param probability
     * @return
     */
    public static boolean checkProbability(double probability) {
        return (new Random().nextDouble() <= probability);
    }

    /**
     *
     * @param player
     * @return
     */
    public static Set<Integer> getFreeInventorySlots(Player player) {
        Set<Integer> freeSlots = new HashSet<>();
        PlayerInventory playerInv = player.getInventory();
        for (int i = 9; i < 36; i++) {
            ItemStack item = playerInv.getContents()[i];
            if (item != null && item.getType().equals(Material.AIR)) {
                //0-8 hotbar
                //9-35 inventory
                //101-104 armor
                //80-83 craftings
                freeSlots.add(i);
            }
        }
        return freeSlots;
    }

}
