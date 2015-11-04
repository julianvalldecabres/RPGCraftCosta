/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGWeaponManager {

    private RPGCraftCosta plugin;
    private HashMap<String, RPGWeapon> weaponList;
    private File weaponFile;
    private FileConfiguration config;

    public RPGWeaponManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getLogger().info("Cargando armas....");
        this.weaponList = new HashMap<>();
        this.weaponFile = new File(RPGFinals.weaponFilePath);
        if (!weaponFile.exists()) {
            try {
                weaponFile.getParentFile().mkdirs();
                weaponFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(RPGWeaponManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadWeapons();
    }

    private void loadWeapons() {
        FileConfiguration config;
        if (!weaponFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.weaponFilePath));
        } else {
            config = YamlConfiguration.loadConfiguration(weaponFile);
        }
        String name;
        ItemStack item; //En el fichero se guardara y salvara el ID del Material (ej. DIAMOND_SWORD)
        int sellprice;
        int buyprice;
        Quality quality;
        int level;
        boolean upgradable;
        int weaponLevel;
        double damage;
        double incdamage;
        double criticalp;
        double inccriticalp;
        double criticaldamage;
        double inccriticaldamage;
        double healthsteal;
        double inchealthsteal;
        double apbonus;
        double xpbonus;
        double moneybonus;

        /**
         * RECORRER YAML DEL ESTILO
         *
         * banhammer: level: 1 damage: 15
         */
        Set<String> armas = config.getKeys(false);
        for (String arma : armas) {
            ConfigurationSection section = config.getConfigurationSection(arma);
            name = arma;
            item = new ItemStack(Material.matchMaterial(section.getString("item")));
            sellprice = section.getInt("sellprice");
            buyprice = section.getInt("buyprice");
            quality = Enum.valueOf(Quality.class, section.getString("quality"));
            level = section.getInt("level");
            upgradable = section.getBoolean("upgradable");
            weaponLevel = section.getInt("weaponlevel");
            damage = section.getDouble("damage");
            incdamage = section.getDouble("incdamage");
            criticalp = section.getDouble("criticalp");
            inccriticalp = section.getDouble("inccriticalp");
            criticaldamage = section.getDouble("criticaldamage");
            inccriticaldamage = section.getDouble("inccriticaldamage");
            healthsteal = section.getDouble("healthsteal");
            inchealthsteal = section.getDouble("inchealthsteal");
            apbonus = section.getDouble("apbunos");
            xpbonus = section.getDouble("xpbonus");
            moneybonus = section.getDouble("moneybonus");
            weaponList.put(arma, new RPGWeapon(item, name, sellprice, buyprice, quality, level, upgradable, weaponLevel, damage, incdamage, criticalp, inccriticalp, criticaldamage, inccriticaldamage, healthsteal, inchealthsteal, apbonus, xpbonus, moneybonus));

        }
        for (Map.Entry<String, RPGWeapon> entrySet : weaponList.entrySet()) {
            String key = entrySet.getKey();
            RPGWeapon value = entrySet.getValue();
        }
    }

    public ItemStack getRPGWeaponByName(String name) {
        RPGWeapon weapon = weaponList.get(name);
        return weapon.getItem();
    }
    
    public RPGWeapon getRPGWeaponByItem(ItemStack item){
        return this.weaponList.get(getRPGWeaponNameByItem(item));
    }
    
    public String getRPGWeaponNameByItem(ItemStack item){
        String []displayname=item.getItemMeta().getDisplayName().split(" ");
        int sizeLongNameParts = displayname.length;
        //wLongNameParts[0] equivale a [LVLXX]
        //wLongNameParts[size-1] equivale al modificador
        String wName = "";
        if (sizeLongNameParts > 3) {
            for (int i = 1; i <= sizeLongNameParts - 3; i++) {
                wName += displayname[i] + " ";
            }
            wName += displayname[sizeLongNameParts - 2];
        } else {
            wName = displayname[1];
        }
        return wName;
    }

    public Set<String> getAllWeaponNames() {
        Set<String> allWeaponNames = new HashSet<>();
        for (Map.Entry<String, RPGWeapon> entrySet : weaponList.entrySet()) {
            String key = entrySet.getKey();
            allWeaponNames.add(key);
        }
        return allWeaponNames;
    }

    public boolean isWeapon(ItemStack item) {
        return (item.getType().equals(Material.DIAMOND_SWORD)
                || item.getType().equals(Material.WOOD_SWORD)
                || item.getType().equals(Material.IRON_SWORD)
                || item.getType().equals(Material.STONE_SWORD)
                || item.getType().equals(Material.GOLD_SWORD)
                || item.getType().equals(Material.BOW)
                || item.getType().equals(Material.DIAMOND_AXE)
                || item.getType().equals(Material.WOOD_AXE)
                || item.getType().equals(Material.IRON_AXE)
                || item.getType().equals(Material.GOLD_AXE)
                || item.getType().equals(Material.STONE_AXE));
    }
    
    public boolean isRPGWeapon(ItemStack item){
        String []displayname=item.getItemMeta().getDisplayName().split(" ");
        int sizeLongNameParts = displayname.length;
        //wLongNameParts[0] equivale a [LVLXX]
        //wLongNameParts[size-1] equivale al modificador
        String wName = "";
        if (sizeLongNameParts > 3) {
            for (int i = 1; i <= sizeLongNameParts - 3; i++) {
                wName += displayname[i] + " ";
            }
            wName += displayname[sizeLongNameParts - 2];
        } else {
            wName = displayname[1];
        }
        return this.weaponList.containsKey(wName);
    }
}
