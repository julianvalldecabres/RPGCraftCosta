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
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.CustomEntityType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CZombie;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.OnRPGPlayerJoinEvent;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGParty;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * La clase RPGPlayerListener es la encargada de manejar todas las acciones que
 * realice el usuario, entre ellas la conexion, desconexion y aspectos propios
 * de la configuracion del servidor como el hambre, daño por caida o similares
 *
 * @author jail
 */
public class RPGPlayerListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;
    private RPGPartyManager rpgPaMan;
    private RPGGuildManager rpgGMan;
    private RPGChatManager rpgCMan;

    /**
     * Constructor de la clase RPGPlayerListener
     *
     * @param plugin clase RPGCraftCosta
     */
    public RPGPlayerListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgPaMan = plugin.getRPGPartyManager();
        this.rpgGMan = plugin.getRPGGuildManager();
        this.rpgCMan = plugin.getRPGChatManager();
    }

    /**
     * onPlayerJoin captura el evento PlayerJoinEvent
     *
     * @param e evento que se dispara cuando un jugador se conecta al servidor
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.getLogger().info("Jugador con nombre: " + p.getName() + " y uuid: " + p.getUniqueId().toString() + " se ha conectado al servidor");
        RPGPlayer rpgP = rpgPMan.loadOrCreateRPGPlayer(e.getPlayer());
        Bukkit.getPluginManager().callEvent(new OnRPGPlayerJoinEvent(rpgP));
        if (rpgP.getPlayerClass().isEmpty()) {
            rpgP.setMove(false);
            p.sendMessage(ChatColor.YELLOW + "Selecciona una clase antes de continuar...");
            p.sendMessage(plugin.getRPGClassManager().getListAvailableClasses());
        }
        rpgPMan.saveRPGPlayer(rpgP);
        rpgPMan.addRPGPlayerToList(rpgP);
        RPGWeaponManager rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
    }

    /**
     * onPlayerMove captura el evento PlayerMoveEvent
     *
     * @param e evento que se dispara cuando el usuario se mueve
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        RPGPlayer player = rpgPMan.getRPGPlayerByName(e.getPlayer().getName());
        Location loc = e.getPlayer().getLocation();
        if (!player.isMove()) {
            e.getPlayer().teleport(loc);
        }
    }

    /**
     * onPlayerKicked captura el evento PlayerKickEvent
     *
     * @param e evento que sucede al echar a un usuario del servidor
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKicked(PlayerKickEvent e) {
        Player p = e.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (!rpgP.getParty().isEmpty()) {
            RPGParty rpgParty = rpgPaMan.getParty(rpgP.getParty());
            rpgPaMan.leavePlayerFromParty(p, rpgP.getParty());
        }
        if (!rpgP.getGuild().isEmpty()) {
            rpgGMan.sendMessageToGuild(rpgP.getGuild(), ChatColor.LIGHT_PURPLE + "El camarada " + p.getName() + " ha abandonado la partida");
        }
        rpgPMan.saveRPGPlayer(rpgPMan.getRPGPlayerByName(e.getPlayer().getName()));
        rpgPMan.delPlayerFromList(e.getPlayer());
    }

    /**
     * onPlayerDisconnectedFromServer captura el evento PlayerQuitEvent
     *
     * @param event evento que se reproduce al desconectarse un jugador
     */
    @EventHandler
    public void onPlayerDisconnectedFromServer(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (!rpgP.getParty().isEmpty()) {
            RPGParty rpgParty = rpgPaMan.getParty(rpgP.getParty());
            rpgPaMan.leavePlayerFromParty(p, rpgP.getParty());
        }
        if (!rpgP.getGuild().isEmpty()) {
            rpgGMan.sendMessageToGuild(rpgP.getGuild(), ChatColor.LIGHT_PURPLE + "El camarada " + p.getName() + " ha abandonado la partida");
        }
        rpgPMan.saveRPGPlayer(rpgPMan.getRPGPlayerByName(p.getName()));
        rpgPMan.delPlayerFromList(p);
    }

    /**
     * onPlayerRegainHealth captura el evento EntityRegainHealthEvent
     *
     * @param e evento que captura cuando un usuario recupera vida
     */
    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent e) {

        if (e.getRegainReason().equals(RegainReason.SATIATED)) {
            plugin.getLogger().info("Player recupera vida por estar saciado");
            plugin.getLogger().info(e.getAmount() + " de vida");
        } else if (e.getRegainReason().equals(RegainReason.MAGIC_REGEN)) {
            plugin.getLogger().info("Player recupera vida por regeneracion magica");
            plugin.getLogger().info(e.getAmount() + " de vida");
        } else if (e.getRegainReason().equals(RegainReason.MAGIC)) {
            plugin.getLogger().info("Player recupera vida por magia");
            plugin.getLogger().info(e.getAmount() + " de vida");
        } else if (e.getRegainReason().equals(RegainReason.CUSTOM)) {
            plugin.getLogger().info("Player recupera vida personalizado");
            plugin.getLogger().info(e.getAmount() + " de vida");
        }
    }

    /**
     * onInteract captura el evento PlayerInteractEntityEvent
     *
     * @param event evento que captura cuando un usuario interactura con una
     * entidad
     */
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        Location loc = rightClicked.getLocation();
        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) loc.getWorld()).getHandle();
        CustomEntityType.spawnEntity(new CZombie(world, loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));

        if (p.hasLineOfSight(rightClicked)) {

            if (rightClicked instanceof Player) {
                //p.trade(rightClicked);
            }
            if (rightClicked instanceof Entity) {
                event.getRightClicked();
                //rightClicked.remove();
            }
        }
    }
}
