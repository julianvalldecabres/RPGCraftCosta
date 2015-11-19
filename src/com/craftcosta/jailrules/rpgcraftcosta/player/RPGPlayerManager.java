/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGEconomy;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import com.craftcosta.jailrules.rpgcraftcosta.quests.RPGQuest;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGPlayerManager {

    /**
     *
     */
    public static HashMap<String, RPGPlayer> listRPGPlayers;
    private RPGCraftCosta plugin;
    private RPGArmorManager rpgAMan;
    private RPGWeaponManager rpgWMan;
    private RPGJewelManager rpgJMan;
    private File playerFilePath;
    private FileConfiguration pFConfig;

    /**
     *
     * @param plugin
     */
    public RPGPlayerManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.listRPGPlayers = new HashMap<>();
        this.rpgAMan = plugin.getRPGItemManager().getRPGArmorManager();
        this.rpgWMan = plugin.getRPGItemManager().getRPGWeaponManager();
        this.rpgJMan = plugin.getRPGItemManager().getRPGJewelManager();
    }

    /**
     *
     * @return
     */
    public HashMap<String, RPGPlayer> getPlayersOnline() {
        return listRPGPlayers;
    }

    /**
     *
     * @param p
     * @param rpgclass
     */
    public void setRPGClassToPlayer(Player p, RPGClass rpgclass) {
        listRPGPlayers.get(p.getName()).setRPGClass(rpgclass);
    }

    /**
     *
     * @param name
     * @return
     */
    public RPGPlayer getRPGPlayerByName(String name) {
        return listRPGPlayers.get(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public Player getPlayerByName(String name) {
        return listRPGPlayers.get(name).getPlayer();
    }

    /**
     *
     * @param rpgPlayer
     */
    public static void addPlayerToList(RPGPlayer rpgPlayer) {
        listRPGPlayers.put(rpgPlayer.getPlayer().getName(), rpgPlayer);
    }

    /**
     *
     * @param player
     */
    public static void delPlayerFromList(Player player) {
        listRPGPlayers.remove(player.getName());
    }

    /**
     *
     * @param ori
     * @param end
     * @return
     */
    public double distanceBetweenPlayers(Player ori, Player end) {
        double distance = 0.0;
        Location l1 = ori.getLocation();
        Location l2 = end.getLocation();
        double x = l2.getX() - l1.getX();
        double z = l2.getZ() - l1.getZ();
        distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        return distance;
    }

    /**
     *
     */
    public void saveRpgPlayers() {
        plugin.getLogger().info("Salvando todos los players!");
        RPGPlayer rpgP = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            rpgP = getRPGPlayerByName(p.getName());
            rpgP.saveRPGPlayer();
        }
    }

    /**
     *
     * @param rpgPlayer
     */
    public void addRPGPlayerToList(RPGPlayer rpgPlayer) {
        listRPGPlayers.put(rpgPlayer.getName(), rpgPlayer);
    }

    public RPGPlayer loadOrCreateRPGPlayer(Player player) {
        RPGPlayer rpgP = null;
        playerFilePath = new File(RPGFinals.playerFilePath.replace("%player%", player.getUniqueId().toString()));
        if (!playerFilePath.exists()) {
            plugin.getLogger().info("Creando configuración vacía para el jugador " + player.getName());
            rpgP = new RPGPlayer(player);

            playerFilePath.getParentFile().mkdirs();
            rpgP.saveRPGPlayer();
        } else {
            plugin.getLogger().info("Cargando configuración del jugador " + player.getName());
            rpgP = loadRPGPlayer(player);
        }
        return rpgP;
    }

    private RPGPlayer loadRPGPlayer(Player player) {
        playerFilePath = new File(RPGFinals.playerFilePath.replace("%player%", player.getUniqueId().toString()));
        pFConfig = YamlConfiguration.loadConfiguration(playerFilePath);
        String name = player.getName();
        String playerClass = "";
        String guild = "";
        String party = "";
        UUID uuid = player.getUniqueId();

        boolean setResetRequested = false;
        boolean move = false;
        boolean partyChat = true;
        boolean privateChat = true;
        boolean guildChat = true;
        boolean marketChat = true;
        boolean localChat = true;
        boolean globalChat = true;

        RPGEconomy econ;
        List<RPGQuest> finishedQuestsList = new ArrayList<>();
        List<RPGQuest> inProgressQuestsList = new ArrayList<>();

        long experience = 0;
        long ap = 0;

        double actualHealth = 0;
        double maxHealth = 0;
        double actualMana = 0;
        double maxMana = 0;

        double physicalAttack = 0;
        double physicalDefense = 0;
        double physicalEvasion = 0;
        double PhysicalHitRate = 0;

        double magicalAttack = 0;
        double magicalDefense = 0;
        double magicalEvasion = 0;
        double magicalHitRate = 0;

        double critical = 0;

        double expModifiers = 0;
        double moneyModifiers = 0;
        double apModifiers = 0;

        int level;
        int constitutionP;
        int dexteryP;
        int intelligenceP;
        int strengthP;
        //leer del fichero

        playerClass = pFConfig.getString("playerclass");
        guild = pFConfig.getString("guild");
        econ = new RPGEconomy(pFConfig.getLong("money"));

        experience = pFConfig.getLong("experience");
        ap = pFConfig.getLong("ap");

        actualHealth = pFConfig.getDouble("actualhealth");
        maxHealth = pFConfig.getDouble("maxhealth");
        actualMana = pFConfig.getDouble("actualmana");
        maxMana = pFConfig.getDouble("maxmana");

        physicalAttack = pFConfig.getDouble("physicalattack");
        physicalDefense = pFConfig.getDouble("physicaldefense");
        physicalEvasion = pFConfig.getDouble("physicalevasion");
        PhysicalHitRate = pFConfig.getDouble("physicalhitrate");

        magicalAttack = pFConfig.getDouble("magicalattack");
        magicalDefense = pFConfig.getDouble("magicaldefense");
        magicalEvasion = pFConfig.getDouble("magicalevasion");
        magicalHitRate = pFConfig.getDouble("magicalhitrate");

        critical = pFConfig.getDouble("critical");

        level = pFConfig.getInt("level");
        constitutionP = pFConfig.getInt("constitutiop");
        dexteryP = pFConfig.getInt("dexteryp");
        intelligenceP = pFConfig.getInt("intelligencep");
        strengthP = pFConfig.getInt("strengthp");

        //Crear RPGPlayer
        RPGPlayer rpgp = new RPGPlayer(name, uuid, player, guild, playerClass, party, econ, level, ap, ap, finishedQuestsList, inProgressQuestsList, constitutionP, dexteryP, intelligenceP, strengthP, actualMana, maxMana, actualHealth, maxHealth, physicalAttack, physicalDefense, PhysicalHitRate, physicalEvasion, magicalAttack, magicalDefense, magicalHitRate, magicalEvasion, critical);
        //añadir a lista
        checkAllEquipment(rpgp);
        this.listRPGPlayers.put(name, rpgp);
        return rpgp;
    }

    public void checkAllEquipment(RPGPlayer p) {
        ItemStack[] equipedArmor = p.getPlayer().getInventory().getArmorContents();
        ItemStack equipedWeapon = p.getPlayer().getItemInHand();
        int weaponSlot = p.getPlayer().getInventory().getHeldItemSlot();
        System.out.println("Item en mano en slot: " + weaponSlot);
        ItemStack[] quickbarItems = new ItemStack[9];

        System.out.println("Contenido de armadura");
        for (int i = 0; i <= 3; i++) {
            if (!equipedArmor[i].getType().equals(Material.AIR)) {
                if (rpgAMan.isRPGArmor(equipedArmor[i])) {
                    //Metodo para obtener los lores del objeto y poder separar y añadir a las estadisticas del player
                }
            }
        }
        System.out.println("Contenido de la hotbar");
        for (int i = 0; i <= 8; i++) {
            System.out.println("slot: " + i);
            if (p.getPlayer().getInventory().getContents()[i] != null) {
                quickbarItems[i] = p.getPlayer().getInventory().getContents()[i];
            }
        }
        for (ItemStack item : quickbarItems) {
            if (item != null) {
                System.out.println(item.toString());
            }
        }
    }
}
