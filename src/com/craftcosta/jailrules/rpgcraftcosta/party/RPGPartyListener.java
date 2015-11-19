/*
 * Copyright (C) 2015 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jail
 */
public class RPGPartyListener implements Listener {

    RPGCraftCosta plugin;
    RPGPartyManager rpgPartyMan;
    RPGPlayerManager rpgPMan;

    public RPGPartyListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKickedFromServer(PlayerKickEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (!rpgP.getParty().isEmpty()) {
            RPGParty rpgParty = rpgPartyMan.getParty(rpgP.getParty());
            rpgPartyMan.leavePlayerFromParty(p, rpgP.getParty());
        }
    }

    @EventHandler//UNIR EN UN MISMO EVENTO PRINCIPAL DEL PROPIO PLAYER
    public void onPlayerDisconnectedFromServer(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());

        if (!rpgP.getParty().isEmpty()) {
            RPGParty rpgParty = rpgPartyMan.getParty(rpgP.getParty());
            rpgPartyMan.leavePlayerFromParty(p, rpgP.getParty());
        }
    }
}
