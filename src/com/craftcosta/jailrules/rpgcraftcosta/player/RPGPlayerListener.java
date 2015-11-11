/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.questitems.RPGQuestItemManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

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
        Player p = e.getPlayer();
        RPGPlayer rpgP = rpgPMan.getOrCreateRPGPlayer(e.getPlayer());
        
        rpgP.saveRPGPlayer();
        if(rpgP.getPlayerClass().isEmpty()){
            rpgP.setMove(false);
            p.sendMessage(ChatColor.YELLOW+"Selecciona una clase antes de continuar...");
            p.sendMessage(plugin.getRPGClassManager().getListAvailableClasses());
        }
        rpgP.checkAllEquipment();
        plugin.getLogger().info("health: "+e.getPlayer().getHealth()+ " maxHealth: "+e.getPlayer().getMaxHealth()+" healthscale: "+e.getPlayer().getHealthScale());
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
//            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
//                e.setCancelled(true);
//            }
            if (e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
                e.setCancelled(true);
            }
            if (e.getCause().equals(EntityDamageEvent.DamageCause.STARVATION)) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof HumanEntity){
            plugin.getLogger().info("El que realiza el daño es un player");
            if(e.getEntity() instanceof HumanEntity){
                plugin.getLogger().info("El que recibe el daño es un player");
            }else{
                plugin.getLogger().info("El que recibe el daño no es un player");
            }            
        }else{
            plugin.getLogger().info("El que realiza el daño no es un player");
            if(e.getEntity() instanceof Player){
                plugin.getLogger().info("El que recibe el daño es un player");
            }else{
                plugin.getLogger().info("El que recibe el daño no es un player");
            }        
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {

        ItemStack item = e.getItem().getItemStack();
        Set<Integer> freeInventorySlots = RPGPlayerUtils.getFreeInventorySlots(e.getPlayer());
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
        if(rpguser.getPlayerClass().isEmpty()){
            event.setCancelled(true);
        }
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
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event){
        plugin.getLogger().info("//////////////");
        plugin.getLogger().info("Slot: "+event.getSlot());
        plugin.getLogger().info("RawSlot: "+event.getRawSlot());
        plugin.getLogger().info("SlotType: "+event.getSlotType().name());
        plugin.getLogger().info("//////////////");
    }
}
