/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.io.File;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        if (Bukkit.getServer().getPlayer(name) == null) {
            return false;
        }
        return true;
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

    public static boolean checkProbability(double probability) {
        return (new Random().nextDouble() <= probability);
    }
}
