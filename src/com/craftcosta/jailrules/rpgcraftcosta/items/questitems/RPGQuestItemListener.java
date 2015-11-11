/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.questitems;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 *
 * @author jail
 */
public class RPGQuestItemListener implements Listener {

    RPGCraftCosta plugin;

    public RPGQuestItemListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickUpItemQuest(PlayerPickupItemEvent e) {
        //Si el evento de pickup es cancelado o no
        //Se podria avisar de los items restantes si un player tiene cierta mision

    }

}
