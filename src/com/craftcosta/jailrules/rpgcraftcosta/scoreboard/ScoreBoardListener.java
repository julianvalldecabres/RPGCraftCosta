/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author jail
 */
public class ScoreBoardListener implements Listener {

    /**
     *
     * @param e
     */
    @EventHandler
    public void OnPlayergetDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            //e.getCause().ENTITY_ATTACK
            Player p = (Player) e.getEntity();
            //ScoreBoardManager.setHealth(p);
        }
    }

}
