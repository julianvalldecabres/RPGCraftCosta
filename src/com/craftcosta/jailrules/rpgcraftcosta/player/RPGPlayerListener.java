/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGParty;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
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
     *
     * @param plugin
     */
    public RPGPlayerListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgPaMan=plugin.getRPGPartyManager();
        this.rpgGMan= plugin.getRPGGuildManager();
        this.rpgCMan= plugin.getRPGChatManager();
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.getLogger().info("Jugador con nombre: " + p.getName() + " y uuid: " + p.getUniqueId().toString() + " se ha conectado al servidor");
        RPGPlayer rpgP = rpgPMan.loadOrCreateRPGPlayer(e.getPlayer());
        if (rpgP.getPlayerClass().isEmpty()) {
            rpgP.setMove(false);
            p.sendMessage(ChatColor.YELLOW + "Selecciona una clase antes de continuar...");
            p.sendMessage(plugin.getRPGClassManager().getListAvailableClasses());
        }
                //COMPROBAR QUE AUN EXISTA LA GUILD 
        //SINO EXISTE INFORMAR Y CAMBIAR
        if (!rpgP.getGuild().isEmpty()) {
            if (!rpgGMan.getAllAvailableGuilds().contains(rpgP.getGuild())) {
                p.sendMessage(rpgCMan.getPrefixForGuild() + ChatColor.RED + " El clan " + rpgP.getGuild() + " ha sido disuelto");
                rpgP.setGuild("");
            }else{
                rpgGMan.playerConnectedToGuild(p,rpgGMan.getGuildByName(rpgP.getGuild()));
            }
        }
        rpgPMan.saveRPGPlayer(rpgP);
        rpgPMan.addRPGPlayerToList(rpgP);
        RPGWeaponManager rpgWMan= plugin.getRPGItemManager().getRPGWeaponManager();
    }

    /**
     *
     * @param e
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
     *
     * @param e
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
            rpgGMan.sendMessageToGuild(rpgP.getGuild(),ChatColor.LIGHT_PURPLE+"El camarada "+p.getName()+" ha abandonado la partida");
        }
        rpgPMan.saveRPGPlayer(rpgPMan.getRPGPlayerByName(e.getPlayer().getName()));
        rpgPMan.delPlayerFromList(e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerDisconnectedFromServer(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (!rpgP.getParty().isEmpty()) {
            RPGParty rpgParty = rpgPaMan.getParty(rpgP.getParty());
            rpgPaMan.leavePlayerFromParty(p, rpgP.getParty());
        }
        if (!rpgP.getGuild().isEmpty()) {
            rpgGMan.sendMessageToGuild(rpgP.getGuild(),ChatColor.LIGHT_PURPLE+"El camarada "+p.getName()+" ha abandonado la partida");
        }
        rpgPMan.saveRPGPlayer(rpgPMan.getRPGPlayerByName(p.getName()));
        rpgPMan.delPlayerFromList(p);
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
        if(e.getDamager() instanceof Player){
            plugin.getLogger().info("El que realiza el daño es un player");
        }else{
            plugin.getLogger().info("El que realiza el daño no es un player de tipo: "+e.getDamager().getType().name());
        }
        if(e.getEntity() instanceof Player){
            plugin.getLogger().info("El que recibe el daño es un player");
        }else{
            plugin.getLogger().info("El que recibe el daño no es un player de tipo: "+e.getEntityType().name());
        }
        plugin.getLogger().info("Quien realiza el daño: "+e.getDamager().getType().toString()+" con nombre: " +e.getDamager().getName());
        plugin.getLogger().info("Quien recibe el daño: "+e.getEntity().getType().toString()+" con nombre: "+e.getEntity().getName());
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
     * @param event
     */
    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntityType().equals(EntityType.EGG)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        plugin.getLogger().info("//////////////");
        plugin.getLogger().info("Slot: " + event.getSlot());
        plugin.getLogger().info("RawSlot: " + event.getRawSlot());
        plugin.getLogger().info("SlotType: " + event.getSlotType().name());
        plugin.getLogger().info("//////////////");
    }
}
