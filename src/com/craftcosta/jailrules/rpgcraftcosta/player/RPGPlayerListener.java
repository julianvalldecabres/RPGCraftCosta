/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jail
 */
public class RPGPlayerListener implements Listener{
    RPGCraftCosta plugin;
    public RPGPlayerListener(RPGCraftCosta plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e){
        Player p=(Player)e.getPlayer();
        RPGPlayerManager.addPlayerToList(new RPGPlayer(p));
    }
    
    @EventHandler
        public void onLogoutPlayer(PlayerQuitEvent e){
        Player p=(Player)e.getPlayer();
        RPGPlayerManager.delPlayerFromList(p);
    }
        @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
                e.setCancelled(true);
            }
        }
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.STARVATION)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRightClickBlock(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        if (p.hasLineOfSight(rightClicked)) {
            if (rightClicked instanceof Player) {
                //p.trade(rightClicked);
                }
        }
    }
    
    @EventHandler
    public void onPlayerAchievementAccomplished(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player && event.getEntityType().equals(EntityType.EGG)){
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onLocalPlayerChat(AsyncPlayerChatEvent event){
        char c=event.getMessage().charAt(0);
        String tipoChat;
        String lugar=ChatColor.GREEN+"["+event.getPlayer().getWorld().getName().substring(0, 1)+"]";
        RPGPlayer rpguser=this.plugin.getRPGPlayerManager().getRPGPlayerByName(event.getPlayer().getName());
        Player user=this.plugin.getRPGPlayerManager().getPlayerByName(event.getPlayer().getName());
        String clase;
        if(rpguser.playerClass == null){
            clase= ChatColor.AQUA+"[Newbie]";
        }else{
            clase= ChatColor.AQUA+"["+rpguser.playerClass.getNameClass()+"]";
            
        }
        String name=ChatColor.YELLOW+event.getPlayer().getName()+": ";
        String message= event.getMessage().substring(1);
        switch(c){
                //global case
                case '!':
                    tipoChat=""+ChatColor.BOLD+ChatColor.GOLD+"[G]";
                    event.setFormat(tipoChat+lugar+clase+name+ChatColor.GOLD+message);
                    break;
                //global market
                case '$':
                    tipoChat=""+ChatColor.BOLD+ChatColor.GREEN+"[$]";
                    event.setFormat(tipoChat+lugar+clase+name+ChatColor.GREEN+message);
                    break;
                //Party Chat
                case '#':
                    tipoChat=""+ChatColor.BOLD+ChatColor.AQUA+"[P]";
                    event.setFormat(tipoChat+lugar+clase+name+ChatColor.AQUA+message);
                    break;
                //Guild chat
                case '@':
                    tipoChat=""+ChatColor.BOLD+ChatColor.DARK_PURPLE+"[C]";
                    message=tipoChat+lugar+clase+name+ChatColor.DARK_PURPLE+message;
                    RPGPlayer rpgplayer=plugin.getRPGPlayerManager().getRPGPlayerByName(user.getName());
                    String guild=rpgplayer.getGuild();
                    plugin.getRpgGuildManager().getGuildByName(guild).sendMessageToGuild(message);
                    event.setCancelled(true);
                    break;
                    
                //local chat
                default:
                    HashMap<String,Player> temp=plugin.getRPGPlayerManager().getPlayersOnline();
                    tipoChat=""+ChatColor.BOLD+ChatColor.YELLOW+"[L]";
                    message=event.getMessage();
                    message=tipoChat+lugar+clase+name+ChatColor.YELLOW+message;
                    for (Map.Entry<String, Player> entrySet : temp.entrySet()) {
                            Object key = entrySet.getKey();
                            Player value = entrySet.getValue();
                            if(!key.equals(user.getName())){
                                if(plugin.getRPGPlayerManager().distanceBetweenPlayers(user, value)<=100){
                                    value.sendMessage(message);
                                }
                            }                            
                        }
                    event.setCancelled(true);                   
                    break;
        }  
                
                
        //event.
    }    
}