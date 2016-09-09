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
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGEconomy;
import com.craftcosta.jailrules.rpgcraftcosta.items.ItemType;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmor;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeapon;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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
    private RPGChatManager rpgCMan;
    private RPGClassManager rpgCCMan;
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
        this.rpgCCMan = plugin.getRPGClassManager();
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
        rpgCCMan.setRPGPlayerRPGClass(listRPGPlayers.get(p.getName()), rpgclass);
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
        plugin.getLogger().info("Guardando todos los players!");
        RPGPlayer rpgP = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            rpgP = getRPGPlayerByName(p.getName());
            saveRPGPlayer(rpgP);
        }
    }

    /**
     *
     * @param rpgPlayer
     */
    public void addRPGPlayerToList(RPGPlayer rpgPlayer) {
        listRPGPlayers.put(rpgPlayer.getName(), rpgPlayer);
    }

    /**
     *
     * @param player
     * @return
     */
    public RPGPlayer loadOrCreateRPGPlayer(Player player) {
        RPGPlayer rpgP = null;
        playerFilePath = new File(RPGFinals.playerFilePath.replace("%player%", player.getUniqueId().toString()));
        if (!playerFilePath.exists()) {
            plugin.getLogger().info("Creando configuración vacía para el jugador " + player.getName());
            playerFilePath.getParentFile().mkdirs();
            rpgP = new RPGPlayer(player);
            saveRPGPlayer(rpgP);
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
        double xpbonus = 0;
        double moneybonus = 0;
        double expModifiers = 0;
        double moneyModifiers = 0;

        int level;
        int constitutionP;
        int dexteryP;
        int intelligenceP;
        int strengthP;
        //leer del fichero
        ConfigurationSection section = pFConfig;
        level = section.getInt("level");
        playerClass = section.getString("class");
        guild = section.getString("guild");
        party = "";

        econ = new RPGEconomy(section.getLong("money"));
        experience = section.getLong("experience");

        critical = section.getDouble("critical");
        moneybonus = section.getDouble("moneybonus");
        xpbonus = section.getDouble("xpbonus");

        actualHealth = section.getDouble("actualHealth");
        maxHealth = section.getDouble("maxHealth");
        actualMana = section.getDouble("actualMana");
        maxMana = section.getDouble("maxMana");

        physicalAttack = section.getDouble("physicalattack");
        physicalDefense = section.getDouble("physicaldefense");
        physicalEvasion = section.getDouble("physicalevasion");
        PhysicalHitRate = section.getDouble("physicalhitrate");

        magicalAttack = section.getDouble("magicalattack");
        magicalDefense = section.getDouble("magicaldefense");
        magicalEvasion = section.getDouble("magicalevasion");
        magicalHitRate = section.getDouble("magicalhitrate");

        ap = section.getLong("ap");
        constitutionP = section.getInt("constitutionP");
        dexteryP = section.getInt("dexteryP");
        intelligenceP = section.getInt("intelligenceP");
        strengthP = section.getInt("strengthP");

        //Crear RPGPlayer
        RPGPlayer rpgp = new RPGPlayer(name, uuid, player, guild, playerClass, party, econ, level, experience, ap, constitutionP, dexteryP, intelligenceP, strengthP, actualMana, maxMana, actualHealth, maxHealth, physicalAttack, physicalDefense, PhysicalHitRate, physicalEvasion, magicalAttack, magicalDefense, magicalHitRate, magicalEvasion, critical, xpbonus, moneybonus);
        //añadir a lista
        this.listRPGPlayers.put(name, rpgp);
        return rpgp;
    }

    public void applyRPGLoresToPlayer(List<RPGLore> lores, RPGPlayer rpgp) {
        for (RPGLore lore : lores) {
            switch (lore.getLoretype()) {
                case CRITICAL:
                    rpgp.setFinalcritical(rpgp.getFinalcritical() * (1 + (double) lore.getValue()));
                    break;
                case HEALTH:
                    rpgp.setFinalMaxHealth(rpgp.getFinalMaxHealth() * (1 + (double) lore.getValue()));
                    break;
//                case HEALTHSTEAL:
//                    rpgp.setFinalHealthSteal(rpgp.getFinalHealthSteal()*(1+(double)lore.getValue()));
//                    break;
                case MAGICALATTACK:
                    rpgp.setFinalmagicalAttack(rpgp.getFinalmagicalAttack() + (double) lore.getValue());
                    break;
                case MAGICALDEFENSE:
                    rpgp.setFinalmagicalDefense(rpgp.getFinalmagicalDefense() + (double) lore.getValue());
                    break;
                case MAGICALEVASION:
                    rpgp.setFinalmagicalEvasion(rpgp.getFinalmagicalEvasion() * (1 + (double) lore.getValue()));
                    break;
                case MAGICALHITRATE:
                    rpgp.setFinalmagicalHitRate(rpgp.getFinalmagicalHitRate() * (1 + (double) lore.getValue()));
                    break;
                case MANA:
                    rpgp.setFinalMaxMana(rpgp.getFinalMaxMana() * (1 + (double) lore.getValue()));
                    break;
//                case MANASTEAL:
//                    break;
                case MONEYBONUS:
                    rpgp.setFinalmoneybonus(rpgp.getFinalmoneybonus() * (1 + (double) lore.getValue()));
                    break;
                case PHYSICALATTACK:
                    rpgp.setFinalphysicalAttack(rpgp.getFinalphysicalAttack() + (double) lore.getValue());
                    break;
                case PHYSICALDEFENSE:
                    rpgp.setFinalphysicalDefense(rpgp.getFinalphysicalDefense() + (double) lore.getValue());
                    break;
                case PHYSICALEVASION:
                    rpgp.setFinalphysicalEvasion(rpgp.getFinalphysicalEvasion() * (1 + (double) lore.getValue()));
                    break;
                case PHYSICALHITRATE:
                    rpgp.setFinalphysicalHitRate(rpgp.getFinalphysicalHitRate() * (1 + (double) lore.getValue()));
                    break;
                case XPBONUS:
                    rpgp.setFinalxpbonus(rpgp.getFinalxpbonus() * (1 + (double) lore.getValue()));
                    break;
            }
        }
    }

    /**
     *
     * @param p
     */
    public void checkAllEquipment(RPGPlayer p) {
        p.setFinalMaxHealth(p.getMaxHealth());
        p.setFinalMaxMana(p.getMaxMana());
        p.setFinalcritical(p.getCritical());
        p.setFinalmagicalAttack(p.getMagicalAttack());
        p.setFinalmagicalDefense(p.getMagicalDefense());
        p.setFinalmagicalEvasion(p.getMagicalEvasion());
        p.setFinalmagicalHitRate(p.getMagicalHitRate());
        p.setFinalphysicalAttack(p.getPhysicalAttack());
        p.setFinalphysicalDefense(p.getPhysicalDefense());
        p.setFinalphysicalEvasion(p.getPhysicalEvasion());
        p.setFinalphysicalHitRate(p.getPhysicalHitRate());
        p.setFinalmoneybonus(p.getMoneybonus());
        p.setFinalxpbonus(p.getXpbonus());
        ItemStack[] equipedArmor = p.getPlayer().getInventory().getArmorContents();
        ItemStack equipedWeapon = p.getPlayer().getInventory().getItem(0);
        int weaponSlot = p.getPlayer().getInventory().getHeldItemSlot();
        ItemStack[] quickbarItems = new ItemStack[9];
        RPGLoreManager rpgLMan = plugin.getRpgLoreManager();
        System.out.println("Contenido de armadura");
        for (int i = 0; i <= 3; i++) {
            if (equipedArmor != null) {
                if (!equipedArmor[i].getType().equals(Material.AIR)) {
                    if (rpgAMan.isRPGArmor(equipedArmor[i])) {
                        RPGArmor armor = rpgAMan.getRPGArmorByItem(equipedArmor[i]);
                        if (p.getActualLevel() >= armor.getLevel()) {
                            applyRPGLoresToPlayer(rpgLMan.getListOfLoresFromItem(equipedArmor[i]), p);
                        }
                    }
                }
            }
        }
        if (rpgWMan.isRPGWeapon(equipedWeapon)) {
            RPGWeapon weapon = rpgWMan.getRPGWeaponByItem(equipedWeapon);
            if (p.getActualLevel() >= weapon.getLevel()) {
                applyRPGLoresToPlayer(rpgLMan.getListOfLoresFromItem(equipedWeapon), p);
            }
        } else {
            weaponSlot = 0;
        }
        for (int i = 1; i <= 8; i++) {
            if (p.getPlayer().getInventory().getContents()[i] != null) {
                quickbarItems[i] = p.getPlayer().getInventory().getContents()[i];
            }
        }
        for (ItemStack item : quickbarItems) {
            if (item != null) {  
                plugin.getLogger().info(item.toString());
                ItemType type=plugin.getRPGItemManager().getItemType(item);
                if (type==null)
                switch (type) {
                    case JEWEL:
                        applyRPGLoresToPlayer(rpgLMan.getListOfLoresFromItem(equipedWeapon), p);
                        break;
                    default:
                    //DO NOTHING :D
                }
            }
        }
    }

    /**
     *
     * @param rpgP
     */
    public void saveRPGPlayer(RPGPlayer rpgP) {
        File playerFile = new File(RPGFinals.playerFilePath.replace("%player%", rpgP.getPlayer().getUniqueId().toString()));
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        FileConfiguration section = YamlConfiguration.loadConfiguration(playerFile);
        section.set("name", rpgP.getPlayer().getName());
        section.set("move", rpgP.isMove());
        section.set("money", rpgP.getEcon().getMoney());
        section.set("experience", rpgP.getActualExp());
        section.set("level", rpgP.getActualLevel());
        section.set("guild", rpgP.getGuild());
        section.set("party", "");

        section.set("class", rpgP.getPlayerClass());

        section.set("actualHealth", rpgP.getActualHealth());
        section.set("maxHealth", rpgP.getMaxHealth());

        section.set("maxMana", rpgP.getMaxMana());
        section.set("actualMana", rpgP.getActualMana());

        section.set("ap", rpgP.getAp());
        section.set("constitutionP", rpgP.getConstitutionP());
        section.set("dexteryP", rpgP.getDexteryP());
        section.set("intelligenceP", rpgP.getIntelligenceP());
        section.set("strengthP", rpgP.getStrengthP());

        section.set("physicalattack", rpgP.getPhysicalAttack());
        section.set("physicaldefense", rpgP.getPhysicalDefense());
        section.set("physicalevasion", rpgP.getPhysicalEvasion());
        section.set("physicalhitrate", rpgP.getPhysicalHitRate());

        section.set("magicalattack", rpgP.getMagicalAttack());
        section.set("magicaldefense", rpgP.getMagicalDefense());
        section.set("magicalevasion", rpgP.getMagicalEvasion());
        section.set("magicalhitrate", rpgP.getMagicalHitRate());

        section.set("xpbonus", rpgP.getXpbonus());
        section.set("moneybonus", rpgP.getMoneybonus());
        section.set("critical", rpgP.getCritical());
        try {
            section.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
