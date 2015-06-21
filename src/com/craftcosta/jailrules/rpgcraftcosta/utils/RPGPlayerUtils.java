/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayerUtils {

    public static boolean isPlayerOnline(String name) {
        if (Bukkit.getServer().getPlayer(name) == null) {
            return false;
        }
        return true;
    }

    public static boolean existsPlayerFile(Player p){
    File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", p.getUniqueId().toString()));
        return playerFile.exists();
    }
    
    public static void sendMessageToPlayer(String player, String message) {
        Bukkit.getServer().getPlayer(player).sendMessage(message);
    }
}
