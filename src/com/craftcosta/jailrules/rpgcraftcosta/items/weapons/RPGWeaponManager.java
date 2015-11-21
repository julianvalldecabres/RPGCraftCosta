/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLores;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
public class RPGWeaponManager {

    private RPGCraftCosta plugin;
    private HashMap<String, RPGWeapon> weaponList;
    private File weaponFile;
    private File weaponsConfig;
    private FileConfiguration config;
    private FileConfiguration wConfig;
    private double breakprobability;
    private double nothingprobability;
    private double detteroriateprobability;
    private double improveprobability;
    private ItemStack weaponUpgrader;

    public RPGWeaponManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading weapons module....");
        this.weaponList = new HashMap<>();
        this.weaponFile = new File(RPGFinals.weaponFilePath);
        this.weaponsConfig = new File(RPGFinals.weaponsConfigPath);
        if (!weaponFile.exists()) {
            plugin.getLogger().info("Loading default weapons...");
            weaponFile.getParentFile().mkdirs();
            copy(plugin.getResource("weapons.yml"), weaponFile);
        }
        if (!weaponsConfig.exists()) {
            plugin.getLogger().info("Loading default weapons config...");
            weaponsConfig.getParentFile().mkdirs();
            copy(plugin.getResource("weaponsConfig.yml"), weaponsConfig);
        }
        loadWeapons();
        loadWeaponsConfig();
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

    private void loadWeapons() {
        if (!weaponFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.weaponFilePath));
        } else {
            config = YamlConfiguration.loadConfiguration(weaponFile);
        }
        plugin.getLogger().info("Loading weapons...");
        String name;
        ItemStack item;
        int sellprice;
        int buyprice;
        Quality quality;
        int level;
        boolean comerciable;
        boolean upgradable;
        int weaponLevel;
        double physicalattack;
        double incphysicalattack;
        double physicalhitrate;
        double incphysicalhitrate;
        double magicalattack;
        double incmagicalattack;
        double magicalhitrate;
        double incmagicalhitrate;
        double critical;
        double inccritical;
        double healthsteal;
        double inchealthsteal;
        double manasteal;
        double incmanasteal;
        double apbonus;
        double xpbonus;
        double moneybonus;

        Set<String> armas = config.getKeys(false);
        for (String arma : armas) {
            ConfigurationSection section = config.getConfigurationSection(arma);
            name = arma;
            item = new ItemStack(Material.matchMaterial(section.getString("item")));
            comerciable = section.getBoolean("comerciable");
            sellprice = section.getInt("sellprice");
            buyprice = section.getInt("buyprice");
            quality = Enum.valueOf(Quality.class, section.getString("quality"));
            level = section.getInt("level");
            upgradable = section.getBoolean("upgradable");
            weaponLevel = section.getInt("weaponlevel");
            physicalattack = section.getDouble("physicalattack");
            incphysicalattack = section.getDouble("incphysicalattack");
            physicalhitrate = section.getDouble("physicalhitrate");
            incphysicalhitrate = section.getDouble("incphysicalhitrate");
            magicalattack = section.getDouble("magicalattack");
            incmagicalattack = section.getDouble("incmagicalattack");
            magicalhitrate = section.getDouble("magicalhitrate");
            incmagicalhitrate = section.getDouble("incmagicalhitrate");
            critical = section.getDouble("critical");
            inccritical = section.getDouble("inccritical");
            healthsteal = section.getDouble("healthsteal");
            inchealthsteal = section.getDouble("inchealthsteal");
            manasteal = section.getDouble("manasteal");
            incmanasteal = section.getDouble("incmanasteal");
            apbonus = section.getDouble("apbonus");
            xpbonus = section.getDouble("xpbonus");
            moneybonus = section.getDouble("moneybonus");
            weaponList.put(arma, new RPGWeapon(item, name, comerciable, sellprice, buyprice, quality, level, upgradable, weaponLevel, physicalattack, incphysicalattack, physicalhitrate, incphysicalhitrate, magicalattack, incmagicalattack, magicalhitrate, incmagicalhitrate, healthsteal, inchealthsteal, manasteal, incmanasteal, critical, inccritical, apbonus, xpbonus, moneybonus));

        }
    }

    public ItemStack getRPGWeaponByName(String name) {
        return weaponList.get(name).getItem();
    }

    public RPGWeapon getRPGWeaponByItem(ItemStack item) {
        return this.weaponList.get(getRPGWeaponNameByItem(item));
    }

    public String getRPGWeaponNameByItem(ItemStack item) {
        String[] displayname = item.getItemMeta().getDisplayName().split(" ");
        int sizeLongNameParts = displayname.length;
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
            allWeaponNames.add(entrySet.getKey());
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

    public boolean isRPGWeapon(ItemStack item) {
        return this.weaponList.containsKey(getRPGWeaponNameByItem(item));
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

    public ItemStack getWeaponUpgrader() {
        return weaponUpgrader;
    }

    public double getBreakprobability() {
        return breakprobability;
    }

    public double getDetteroriateprobability() {
        return detteroriateprobability;
    }

    public double getNothingprobability() {
        return nothingprobability;
    }

    public double getImproveprobability() {
        return improveprobability;
    }

    private void loadWeaponsConfig() {
        if (!weaponsConfig.exists()) {
            wConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.weaponsConfigPath));
        } else {
            wConfig = YamlConfiguration.loadConfiguration(weaponsConfig);
        }
        plugin.getLogger().info("Loading weapons config...");

        ConfigurationSection section = wConfig.getConfigurationSection("weapon");
        Material weaponMat = Material.matchMaterial(section.getString("material"));
        String weapondisplayname = section.getString("name");
        List<String> weaponlores = section.getStringList("lores");
        this.weaponUpgrader = new ItemStack(weaponMat);
        ItemMeta weaponMeta = weaponUpgrader.getItemMeta();
        weaponMeta.setDisplayName(weapondisplayname);
        weaponMeta.setLore(weaponlores);
        this.weaponUpgrader.setItemMeta(weaponMeta);

        this.breakprobability = Double.parseDouble(wConfig.getString("breakprobability"));
        this.detteroriateprobability = Double.parseDouble(wConfig.getString("detteroriateprobability"));
        this.nothingprobability = Double.parseDouble(wConfig.getString("nothingprobability"));
        this.improveprobability = Double.parseDouble(wConfig.getString("improveprobability"));
    }

    public ItemStack upgradeWeapon(ItemStack weapon) {
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        RPGWeapon rpgWeapon = getRPGWeaponByItem(weapon);
        ItemMeta wMeta = weapon.getItemMeta();
        //Modificacion del nombre
        String wLongName = wMeta.getDisplayName();
        String[] wLongNameParts = wLongName.split(" ");

        int sizeLongNameParts = wLongNameParts.length;
        String wName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            wName += wLongNameParts[i] + " ";
        }
        int nivelactual = getLevel(wLongNameParts[sizeLongNameParts - 1]);
        int nivelup = nivelactual + 1;
        String displayname = wLongNameParts[0] + " " + wName + "+" + nivelup;
        wMeta.setDisplayName(displayname);
        if (wMeta.hasLore()) {
            List<RPGLore> lores = rpgLMan.getListOfLoresFromItem(weapon);
            List<String> newLores = new ArrayList<>();
            for (RPGLore lore : lores) {
                switch (lore.getLoretype()) {
                    case PHYSICALATTACK:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case PHYSICALHITRATE:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case MAGICALATTACK:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case MAGICALHITRATE:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case HEALTHSTEAL:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case MANASTEAL:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case CRITICAL:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case XPBONUS:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case APBONUS:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        break;
                    case MONEYBONUS:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * nivelup));
                        
                        break;
                }
            }
            if (rpgWeapon.comerciable) {
                newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, rpgWeapon.getBuyPrice()));
                newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, rpgWeapon.getSellPrice()));
            } else {
                newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
            }
            if (!rpgWeapon.isUpgradable()) {
                newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
            }
            wMeta.setLore(newLores);
            weapon.setItemMeta(wMeta);
        }
        return weapon;
    }

    ItemStack downgradeWeapon(ItemStack weapon) {
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        RPGWeapon rpgWeapon = getRPGWeaponByItem(weapon);
        ItemMeta wMeta = weapon.getItemMeta();
        //Modificacion del nombre
        String wLongName = wMeta.getDisplayName();
        String[] wLongNameParts = wLongName.split(" ");

        int sizeLongNameParts = wLongNameParts.length;
        String wName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            wName += wLongNameParts[i] + " ";
        }
        int nivelactual = getLevel(wLongNameParts[sizeLongNameParts - 1]);
        int niveldown = nivelactual - 1;
        String displayname = wLongNameParts[0] + " " + wName + " +" + niveldown;
        wMeta.setDisplayName(displayname);
        if (wMeta.hasLore()) {
            List<RPGLore> lores = rpgLMan.getListOfLoresFromItem(weapon);
            List<String> newLores = new ArrayList<>();
            for (RPGLore lore : lores) {
                switch (lore.getLoretype()) {
                    case PHYSICALATTACK:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case PHYSICALHITRATE:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case MAGICALATTACK:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case MAGICALHITRATE:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case HEALTHSTEAL:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case MANASTEAL:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case CRITICAL:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case XPBONUS:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case APBONUS:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));
                        break;
                    case MONEYBONUS:
                        newLores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, rpgWeapon.getPhysicalattack() + rpgWeapon.getIncphysicalattack() * niveldown));                        
                        break;
                }
            }
            if (rpgWeapon.comerciable) {
                newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, rpgWeapon.getBuyPrice()));
                newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, rpgWeapon.getSellPrice()));
            } else {
                newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
            }
            if (!rpgWeapon.isUpgradable()) {
                newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
            }
            wMeta.setLore(newLores);
            weapon.setItemMeta(wMeta);
        }
        return weapon;
    }

    public int getLevel(String actualLevel) {
        return Integer.parseInt(actualLevel.substring(1));
    }
}
