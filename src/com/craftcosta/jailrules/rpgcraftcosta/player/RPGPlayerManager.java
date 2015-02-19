/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.HashMap;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayerManager {
    static HashMap<String, RPGPlayer> listRPGPlayers;
    static HashMap<String, Player> listPlayers;
    RPGCraftCosta plugin;
    
    
    public RPGPlayerManager(RPGCraftCosta plugin){
        this.plugin=plugin;
        listRPGPlayers = new HashMap<String, RPGPlayer>();
        listPlayers = new HashMap<String, Player>();
    }
    
    
    public RPGPlayer getRPGPlayerByName(String name){
        return listRPGPlayers.get(name);
    }
    public Player getPlayerByName(String name){
        return listRPGPlayers.get(name).getPlayer();
    }
    
    public static void addPlayerToList(RPGPlayer rpgPlayer) {
        listRPGPlayers.put(rpgPlayer.getPlayer().getName(), rpgPlayer);
        listPlayers.put(rpgPlayer.getPlayer().getName(), rpgPlayer.getPlayer());
    }

    public static void delPlayerFromList(Player player) {
        listRPGPlayers.remove(player.getName());
        listPlayers.remove(player.getName());
    }
}
