/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.armor;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGArmorManager {

    private RPGCraftCosta plugin;
    private HashMap<String, RPGArmor> armorList;
    private File armorFile;
    private File armorConfig;
    private FileConfiguration config;
    private FileConfiguration aConfig;
    private double breakprobability;
    private double nothingprobability;
    private double improveprobability;
    private ItemStack armorUpgrader;
    private double detteroriateprobability;

    public RPGArmorManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading armor module....");
        this.armorList = new HashMap<>();
        this.armorFile = new File(RPGFinals.armorFilePath);
        this.armorConfig = new File(RPGFinals.armorConfigFilePath);
        if (!armorFile.exists()) {
            plugin.getLogger().info("Loading default armor...");
            armorFile.getParentFile().mkdirs();
            copy(plugin.getResource("armor.yml"), armorFile);
        }
        if (!armorConfig.exists()) {
            plugin.getLogger().info("Loading default armor config...");
            armorConfig.getParentFile().mkdirs();
            copy(plugin.getResource("armorConfig.yml"), armorConfig);
        }
        loadArmorConfig();
        loadArmor();
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isArmor(ItemStack item) {
        return (item.getType().equals(Material.DIAMOND_HELMET)
                || item.getType().equals(Material.DIAMOND_CHESTPLATE)
                || item.getType().equals(Material.DIAMOND_LEGGINGS)
                || item.getType().equals(Material.DIAMOND_BOOTS)
                || item.getType().equals(Material.GOLD_HELMET)
                || item.getType().equals(Material.GOLD_CHESTPLATE)
                || item.getType().equals(Material.GOLD_LEGGINGS)
                || item.getType().equals(Material.GOLD_BOOTS)
                || item.getType().equals(Material.IRON_HELMET)
                || item.getType().equals(Material.IRON_CHESTPLATE)
                || item.getType().equals(Material.IRON_LEGGINGS)
                || item.getType().equals(Material.IRON_BOOTS)
                || item.getType().equals(Material.LEATHER_HELMET)
                || item.getType().equals(Material.LEATHER_CHESTPLATE)
                || item.getType().equals(Material.LEATHER_LEGGINGS)
                || item.getType().equals(Material.LEATHER_BOOTS)
                || item.getType().equals(Material.CHAINMAIL_HELMET)
                || item.getType().equals(Material.CHAINMAIL_LEGGINGS)
                || item.getType().equals(Material.CHAINMAIL_CHESTPLATE)
                || item.getType().equals(Material.CHAINMAIL_BOOTS));
    }

    public boolean isRPGArmor(ItemStack item) {
        String[] displayname = item.getItemMeta().getDisplayName().split(" ");
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
        return this.armorList.containsKey(wName);
    }

    public String getUpgradeResult() {
        Double caso = new Random().nextDouble();
        if (caso <= this.breakprobability) {
            return "break";
        } else if (this.breakprobability < caso && caso <= this.detteroriateprobability) {
            return "detteriorate";
        } else if (this.detteroriateprobability < caso && caso <= this.nothingprobability) {
            return "nothing";
        } else {
            return "improve";
        }
    }

    private void loadArmorConfig() {
        if (!armorConfig.exists()) {
            aConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.armorConfigFilePath));
        } else {
            aConfig = YamlConfiguration.loadConfiguration(armorConfig);
        }
        plugin.getLogger().info("Loading armor config...");
        ConfigurationSection section = aConfig.getConfigurationSection("armor");
        Material armorMat = Material.matchMaterial(section.getString("material"));
        String armordisplayname = section.getString("name");
        List<String> armorlores = section.getStringList("lores");
        this.armorUpgrader = new ItemStack(armorMat);
        ItemMeta armorMeta = armorUpgrader.getItemMeta();
        armorMeta.setDisplayName(ChatColor.DARK_PURPLE + armordisplayname);
        armorMeta.setLore(armorlores);
        this.armorUpgrader.setItemMeta(armorMeta);

        this.breakprobability = Double.parseDouble(aConfig.getString("breakprobability"));
        this.detteroriateprobability = Double.parseDouble(aConfig.getString("detteroriateprobability"));
        this.nothingprobability = Double.parseDouble(aConfig.getString("nothingprobability"));
        this.improveprobability = Double.parseDouble(aConfig.getString("improveprobability"));

    }

    public ItemStack getArmorUpgrader() {
        return armorUpgrader;
    }

    public double getBreakprobability() {
        return breakprobability;
    }

    public double getNothingprobability() {
        return nothingprobability;
    }

    public double getImproveprobability() {
        return improveprobability;
    }

    public double getDetteroriateprobability() {
        return detteroriateprobability;
    }

    private void loadArmor() {
        if (!armorFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.armorFilePath));
        } else {
            config = YamlConfiguration.loadConfiguration(armorFile);
        }
        plugin.getLogger().info("Loading armor...");
        String name;
        ItemStack item;
        String materialS;
        String materialP;
        Material mat;
        int sellprice;
        int buyprice;
        Quality quality;
        int armorLevel;
        int level;
        boolean comerciable;
        boolean upgradable;
        int armor;
        int incArmor;
        double evasion;
        double incEvasion;
        double block;
        double incBlock;
        double actualArmor;
        double actualEvasion;
        double actualBlock;
        double xpbonus;
        double apbonus;
        double moneybonus;

        //RECORRER YAML
        Set<String> armorSets = config.getKeys(false);
        for (String setName : armorSets) {
            ConfigurationSection section = config.getConfigurationSection(setName);
            name = setName;
            level = section.getInt("level");
            armorLevel = section.getInt("armorlevel");
            materialS = section.getString("material");
            quality = Quality.valueOf(section.getString("quality"));
            comerciable = section.getBoolean("comerciable");
            upgradable = section.getBoolean("upgradable");
            Set<String> partes = config.getConfigurationSection(setName + ".parts").getKeys(false);
            for (String parte : partes) {
                //por cada parte debemos crear finalmente un item y añadir a la lista
                section = config.getConfigurationSection(setName + ".parts." + parte);
                materialP = parte;
                mat = Material.matchMaterial(materialS + "_" + materialP);
                armor = section.getInt("armor");
                incArmor = section.getInt("incarmor");
                evasion = section.getDouble("evasion");
                incEvasion = section.getDouble("incevasion");
                block = section.getDouble("block");
                incBlock = section.getDouble("incblock");
                sellprice = section.getInt("sellprice");
                buyprice = section.getInt("buyprice");
                apbonus = section.getDouble("apbonus");
                xpbonus = section.getDouble("xpbonus");
                moneybonus = section.getDouble("moneybonus");
                item = new ItemStack(mat);
                plugin.getLogger().info(armor + " " + incArmor + " " + evasion + " " + incEvasion + " " + sellprice + " " + apbonus);
                this.armorList.put(name + " " + materialP.toLowerCase(), new RPGArmor(level, item, quality, upgradable, comerciable, armorLevel, mat, setName, armor, incArmor, evasion, incEvasion, block, incBlock, moneybonus, apbonus, xpbonus, buyprice, sellprice));
            }
        }
    }

    public ItemStack getRPGArmorByName(String name) {
        return armorList.get(name).getItem();
    }

    public RPGArmor getRPGArmorByItem(ItemStack item) {
        return this.armorList.get(getRPGArmorNameByItem(item));
    }

    private String getRPGArmorNameByItem(ItemStack item) {
        String[] displayname = item.getItemMeta().getDisplayName().split(" ");
        int size = displayname.length;
        String aName = "";
        if (size > 3) {
            for (int i = 1; i <= size - 3; i++) {
                aName += displayname[i] + " ";
            }
            aName += displayname[size - 2];
        } else {
            aName = displayname[1];
        }
        return aName;
    }

    public Set<String> getAllWeaponNames() {
        Set<String> allArmorNames = new HashSet<>();
        for (Map.Entry<String, RPGArmor> entrySet : armorList.entrySet()) {
            allArmorNames.add(entrySet.getKey());
        }
        return allArmorNames;
    }

    public boolean isRPGWeapon(ItemStack item) {
        return this.armorList.containsKey(getRPGArmorNameByItem(item));
    }
}
