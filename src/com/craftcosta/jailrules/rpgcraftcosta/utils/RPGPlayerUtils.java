/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.util.Arrays;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayerUtils {
    public static boolean isPlayerOnline(String name){
        Set<Player> players= (Set<Player>)Arrays.asList(Bukkit.getServer().getOnlinePlayers());
        return (players.contains(name));
    }
    
    public static void sendMessageToPlayer(String player,String message){
        Bukkit.getServer().getPlayer(player).sendMessage(message);
    }
}
