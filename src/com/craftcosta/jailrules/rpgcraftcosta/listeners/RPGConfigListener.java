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
package com.craftcosta.jailrules.rpgcraftcosta.listeners;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

/**
 *
 * @author jail
 */
public class RPGConfigListener {

    RPGCraftCosta plugin;

    public RPGConfigListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        //PRUEBAS
        //plugin.getServer().getWorlds().get(0).setGameRuleValue("doDaylightCycle", "false");
    }

    /**
     * onPlayerFoodLevelChange captura el evento cuando el hambre de un usuario
     * cambia
     *
     * @param e evento disparado cuando el hambre de un usuario cambia
     */
    @EventHandler
    public void onPlayerFoodLevelChange(FoodLevelChangeEvent e) {
        if (!this.plugin.getConfig().getBoolean("Health")) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    /**
     * onPlayerBreakBlocks captura el evento de romper un bloque disparado por
     * un usuario
     *
     * @param e evento que dispara la accion romper un bloque
     */
    @EventHandler
    public void onPlayerBreakBlocks(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    /**
     * onPlayerPlaceBlocks captura el evento BlockPlaceEvent
     *
     * @param e evento que captura cuando el usuario coloca un bloque
     */
    @EventHandler
    public void onPlayerPlaceBlocks(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    /**
     * onPlayerAchievementAccomplished captura el evento
     * PlayerAchievementAwardedEvent
     *
     * @param event evento que se dispara cuando el usuario completa un reto
     * nativo de minecraft
     */
    @EventHandler
    public void onPlayerAchievementAccomplished(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }

    /**
     * onProjectileLaunchEvent captura el evento ProjectileLaunchEvent
     *
     * @param event evento que captura cuando una entidad lanza un proyectil
     */
    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntityType().equals(EntityType.EGG)) {
            event.setCancelled(true);
        }
    }
}
