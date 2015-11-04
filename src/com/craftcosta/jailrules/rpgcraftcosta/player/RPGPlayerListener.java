/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author jail
 */
public class RPGPlayerListener implements Listener {

    public RPGCraftCosta plugin;

    /**
     *
     * @param plugin
     */
    public RPGPlayerListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        //e.getPlayer().sendMessage("Evento player Join!");
        RPGPlayerManager rpgPMan = this.plugin.getRPGPlayerManager();
        RPGPlayer rpgP = rpgPMan.getOrCreateRPGPlayer(e.getPlayer());
        rpgP.setMove(false);
        rpgP.saveRPGPlayer();
        if (rpgP.getFirstLogin()) {
            e.getPlayer().sendMessage("Please register in order to play");
            e.getPlayer().sendMessage("/register <password> <repeatpassword>");
        } else {
            e.getPlayer().sendMessage("Please login in order to play");
            e.getPlayer().sendMessage("/login <password>");
        }
        RPGWeaponManager rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
        e.getPlayer().getInventory().addItem(rpgWMan.getRPGWeaponByName("penepenepene"));
        e.getPlayer().getInventory().addItem(rpgWMan.getRPGWeaponByName("banhammer"));
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        RPGPlayerManager rpgManager = this.plugin.getRPGPlayerManager();
        RPGPlayer player = rpgManager.getRPGPlayerByName(e.getPlayer().getName());
        Location loc = player.getPlayer().getLocation();
        if (!player.getMove()) {
            e.getPlayer().teleport(loc);
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onLogoutPlayer(PlayerQuitEvent e) {
        plugin.getRPGPlayerManager().getRPGPlayerByName(e.getPlayer().getName()).setMove(false);
        plugin.getRPGPlayerManager().saveRpgPlayer(e.getPlayer());
        plugin.getRPGPlayerManager().delPlayerFromList(e.getPlayer());
    }

    /**
     *
     * @param e
     */
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
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        e.getDamager();//Who gives the damage
        e.getEntity();//Who receives the damage
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        //e.setCancelled(true);
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerRightClickBlock(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerAttackCreature(EntityDamageByEntityEvent e) {
        //e.getDamager();
        //e.getEntity();
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerFoodLevelChange(FoodLevelChangeEvent e) {
        if (!this.plugin.getConfig().getBoolean("Health")) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerBreakBlocks(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
            e.getPlayer().sendMessage("no me toques el mapa mamon");
            e.setCancelled(true);
        }
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        if (p.hasLineOfSight(rightClicked)) {
            if (rightClicked instanceof Player) {
                //p.trade(rightClicked);
            }
            if (rightClicked instanceof Entity) {
                event.getRightClicked();
                rightClicked.remove();
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onPlayerAchievementAccomplished(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntityType().equals(EntityType.EGG)) {
            event.setCancelled(true);
        }
    }

    /**
     *
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        char c = event.getMessage().charAt(0);
        String tipoChat;
        String lugar = ChatColor.GREEN + "[" + event.getPlayer().getWorld().getName().substring(0, 1).toUpperCase() + "]";
        RPGPlayerManager rpgmanager = this.plugin.getRPGPlayerManager();
        RPGPlayer rpguser = rpgmanager.getRPGPlayerByName(event.getPlayer().getName());
        Player user = rpguser.getPlayer();
        //rpguser.allowMove();
        String clase;
        if (rpguser.getPlayerClass().equals("")) {
            clase = ChatColor.AQUA + "[Newbie]";
        } else {
            clase = ChatColor.AQUA + "[" + rpguser.getPlayerClass() + "]";
        }
        String name = ChatColor.YELLOW + event.getPlayer().getName() + ": ";
        String message = event.getMessage().substring(1);
        switch (c) {
            //global case
            case '!':
                tipoChat = "" + ChatColor.BOLD + ChatColor.GOLD + "[G]";
                event.setFormat(tipoChat + lugar + clase + name + ChatColor.GOLD + message);
                break;
            //global market
            case '$':
                tipoChat = "" + ChatColor.BOLD + ChatColor.GREEN + "[$]";
                event.setFormat(tipoChat + lugar + clase + name + ChatColor.GREEN + message);
                break;
            //Party Chat
            case '#':
                tipoChat = "" + ChatColor.BOLD + ChatColor.AQUA + "[P]";
                event.setFormat(tipoChat + lugar + clase + name + ChatColor.AQUA + message);
                //TODO
                    /*
                 if()
                 */
                break;
            //Guild chat
            case '@':
                tipoChat = "" + ChatColor.BOLD + ChatColor.DARK_PURPLE + "[C]";
                message = tipoChat + lugar + clase + name + ChatColor.DARK_PURPLE + message;
                if (rpguser.getGuild().equals("")) {
                    user.sendMessage(ChatColor.RED + "No perteneces a ninguna Guild");
                } else {
                    //rpguser.getGuild().sendMessageToGuild(message);
                }
                event.setCancelled(true);
                break;

            //local chat
            default:
                HashMap<String, RPGPlayer> temp = plugin.getRPGPlayerManager().getPlayersOnline();
                tipoChat = "" + ChatColor.BOLD + ChatColor.YELLOW + "[L]";
                message = event.getMessage();
                message = tipoChat + lugar + clase + name + ChatColor.YELLOW + message;
                for (Map.Entry<String, RPGPlayer> entrySet : temp.entrySet()) {
                    Object key = entrySet.getKey();
                    RPGPlayer value = entrySet.getValue();
                    if (!key.equals(user.getName())) {
                        if (plugin.getRPGPlayerManager().distanceBetweenPlayers(user, value.getPlayer()) <= 100) {
                            value.getPlayer().sendMessage(message);
                        } else {
                            user.sendMessage(message);
                            user.sendMessage(ChatColor.RED + "Nobody can hear you!!");
                        }
                    }
                }
                event.setCancelled(true);
                break;
        }
    }
}
