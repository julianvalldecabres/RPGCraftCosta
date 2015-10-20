/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayerManager {

    /**
     *
     */
    public static HashMap<String, RPGPlayer> listRPGPlayers;
    RPGCraftCosta plugin;

    /**
     *
     * @param plugin
     */
    public RPGPlayerManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        listRPGPlayers = new HashMap<>();
    }

    /**
     *
     * @param p
     * @return
     */
    public RPGPlayer getOrCreateRPGPlayer(Player p) {
        RPGPlayer newRPGPlayer = new RPGPlayer(p);
        listRPGPlayers.put(p.getName(), newRPGPlayer);
        return newRPGPlayer;
    }

    /**
     *
     * @return
     */
    public HashMap<String, RPGPlayer> getPlayersOnline() {
        return listRPGPlayers;
    }

    /**
     *
     * @param p
     * @param rpgclass
     */
    public void setRPGClassToPlayer(Player p, RPGClass rpgclass) {
        listRPGPlayers.get(p.getName()).setRPGClass(rpgclass);
    }

    /**
     *
     * @param name
     * @return
     */
    public RPGPlayer getRPGPlayerByName(String name) {
        return listRPGPlayers.get(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public Player getPlayerByName(String name) {
        return listRPGPlayers.get(name).getPlayer();
    }

    /**
     *
     * @param rpgPlayer
     */
    public static void addPlayerToList(RPGPlayer rpgPlayer) {
        listRPGPlayers.put(rpgPlayer.getPlayer().getName(), rpgPlayer);
    }

    /**
     *
     * @param player
     */
    public static void delPlayerFromList(Player player) {
        listRPGPlayers.remove(player.getName());
    }

    /**
     *
     * @param ori
     * @param end
     * @return
     */
    public double distanceBetweenPlayers(Player ori, Player end) {
        double distance = 0.0;
        Location l1 = ori.getLocation();
        Location l2 = end.getLocation();
        double x = l2.getX() - l1.getX();
        double z = l2.getZ() - l1.getZ();
        distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        return distance;
    }

    /**
     *
     */
    public void saveRpgPlayers() {
        plugin.getLogger().info("Salvando todos los players!");
        RPGPlayer rpgP = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            rpgP = getRPGPlayerByName(p.getName());
            rpgP.saveRPGPlayer();
        }
    }

    /**
     *
     */
    public void loadRpgPlayers() {
        Collection<? extends Player> listPlayers = Bukkit.getServer().getOnlinePlayers();
        if (listPlayers.isEmpty()) {
            plugin.getLogger().info("No existen players para cargar!");
        } else {
            plugin.getLogger().info("Cargando todos los players!");
            RPGPlayer rpgP = null;
            for (Player p : Bukkit.getOnlinePlayers()) {
                getOrCreateRPGPlayer(p);
            }
        }
    }

    /**
     *
     * @param rpgPlayer
     */
    public void addRPGPlayerToList(RPGPlayer rpgPlayer) {
        listRPGPlayers.put(rpgPlayer.getName(), rpgPlayer);
    }

    /**
     *
     * @param p
     */
    public void saveRpgPlayer(Player p) {
        RPGPlayer rpgP = getRPGPlayerByName(p.getName());
        rpgP.saveRPGPlayer();
    }

}
