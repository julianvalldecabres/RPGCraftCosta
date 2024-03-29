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
import com.craftcosta.jailrules.rpgcraftcosta.guilds.OnRPGPlayerJoinEvent;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
        if (plugin.getRpgConfig().isEnableParties()) {
            this.rpgPaMan = plugin.getRPGPartyManager();
        }
        if (plugin.getRpgConfig().isEnableGuilds()) {
            this.rpgGMan = plugin.getRPGGuildManager();
        }
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
        //rpgPMan.saveRPGPlayer(rpgP);
        rpgPMan.addRPGPlayerToList(rpgP);
        rpgPMan.checkAllEquipment(rpgP);
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

     @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p=e.getPlayer();
        RPGPlayer rpgp= rpgPMan.getRPGPlayerByName(p.getName());
        rpgp.setActualHealth(rpgp.getFinalMaxHealth());
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
        if (plugin.getRpgConfig().isEnableParties()) {
            if (!rpgP.getParty().isEmpty()) {
                rpgPaMan.sendPartyMessage(rpgP.getParty(), " El compañero " + rpgP.getName() + " se ha desconectado del servidor");
                rpgP.setParty("");
            }
        }
        if (plugin.getRpgConfig().isEnableGuilds()) {
            if (!rpgP.getGuild().isEmpty()) {
                rpgGMan.getGuildByName(rpgP.getGuild()).subOnlinePlayer(e.getPlayer());
            rpgGMan.sendMessageToGuild(rpgP.getGuild(), " El camarada " + rpgP.getName() + " se ha desconectado del servidor");
            }
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
        if (plugin.getRpgConfig().isEnableParties()) {
            if (!rpgP.getParty().isEmpty()) {
                rpgPaMan.sendPartyMessage(rpgP.getParty(), " El compañero " + rpgP.getName() + " se ha desconectado del servidor");
                rpgP.setParty("");
            }
        }
        if (plugin.getRpgConfig().isEnableGuilds()) {
            if (!rpgP.getGuild().isEmpty()) {
                rpgGMan.getGuildByName(rpgP.getGuild()).subOnlinePlayer(rpgP.getPlayer());
            rpgGMan.sendMessageToGuild(rpgP.getGuild(), " El camarada " + rpgP.getName() + " se ha desconectado del servidor");
            }
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
        if(e.getEntity() instanceof Player){
            Player p=(Player) e.getEntity();
            RPGPlayer rpgp= rpgPMan.getRPGPlayerByName(p.getName());
            double res= rpgp.getFinalMaxHealth()*e.getAmount()/20;
            res=res+rpgp.getActualHealth();
            if(res+rpgp.getActualHealth()>rpgp.getFinalMaxHealth()){
                res= rpgp.getFinalMaxHealth();
            }
            rpgp.setActualHealth(res);
        }
        
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.setKeepInventory(true);
        e.setDeathMessage("");
        e.setDroppedExp(0);
        e.setNewTotalExp(0);
        e.setNewLevel(0);
    }

//    /**
//     * onInteract captura el evento PlayerInteractEntityEvent
//     *
//     * @param event evento que captura cuando un usuario interactura con una
//     * entidad
//     */
//    @EventHandler
//    public void onInteract(PlayerInteractEntityEvent event) {
//        Player p = event.getPlayer();
//        //Block b=event.getRightClicked();
//        Entity rightClicked = event.getRightClicked();
//        Location loc = rightClicked.getLocation();
//        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) loc.getWorld()).getHandle();
//        CustomEntityType.spawnEntity(new CZombie(world, loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
//
//        if (p.hasLineOfSight(rightClicked)) {
//
//            if (rightClicked instanceof Player) {
//                //p.trade(rightClicked);
//            }
//            if (rightClicked instanceof Entity) {
//                event.getRightClicked();
//                //rightClicked.remove();
//            }
//        }
//    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        int sum=0;
        for(World w:Bukkit.getServer().getWorlds()){
            sum+=w.getEntities().size();
        }
    }
}
