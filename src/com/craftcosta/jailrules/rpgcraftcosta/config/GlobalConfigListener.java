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
package com.craftcosta.jailrules.rpgcraftcosta.config;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author jail
 */
public class GlobalConfigListener implements Listener {

    private RPGCraftCosta plugin;
    private GlobalConfigManager rpgGMan;

    public GlobalConfigListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgGMan = plugin.getRpgConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().isOp()) {
            Location loc = new Location(Bukkit.getServer().getWorld("world"), rpgGMan.getSpawnx(), rpgGMan.getSpawny(), rpgGMan.getSpawnz());
            e.getPlayer().teleport(loc);
        }
    }

    @EventHandler
    public void onPlayerLoseHunger(FoodLevelChangeEvent e) {
        if (!rpgGMan.isHunger()) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent e) {
        plugin.getLogger().info("poner bloques habilitado?"+rpgGMan.isAllowBlockPlace());
        if (!rpgGMan.isAllowBlockPlace()) {
            
            if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDestroyBlock(BlockBreakEvent e) {
                    plugin.getLogger().info("poner bloques habilitado?"+rpgGMan.isAllowBlockDestroy());

        if (!rpgGMan.isAllowBlockDestroy()) {
            if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
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

    @EventHandler
    public void onPlayerAttacksPlayer(EntityDamageByEntityEvent e) {
        if (!rpgGMan.isEnablePvP()) {
            if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent e){
        e.setCancelled(true);    
    }
    @EventHandler
    public void onBlockSpreadFire(BlockSpreadEvent e){
        e.setCancelled(true);
    }
}
