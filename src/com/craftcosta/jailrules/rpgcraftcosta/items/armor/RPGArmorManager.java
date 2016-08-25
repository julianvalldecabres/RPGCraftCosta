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
package com.craftcosta.jailrules.rpgcraftcosta.items.armor;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
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

    /**
     *
     * @param plugin
     */
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

    /**
     *
     * @param item
     * @return
     */
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

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public ItemStack getArmorUpgrader() {
        return armorUpgrader;
    }

    /**
     *
     * @return
     */
    public double getBreakprobability() {
        return breakprobability;
    }

    /**
     *
     * @return
     */
    public double getNothingprobability() {
        return nothingprobability;
    }

    /**
     *
     * @return
     */
    public double getImproveprobability() {
        return improveprobability;
    }

    /**
     *
     * @return
     */
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
        String id;
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
        double physicaldefense;
        double incphysicaldefense;
        double physicalevasion;
        double incphysicalevasion;
        double magicaldefense;
        double incmagicaldefense;
        double magicalevasion;
        double incmagicalevasion;
        double xpbonus;
        double moneybonus;

        //RECORRER YAML
        Set<String> armorSets = config.getKeys(false);
        for (String idnumber : armorSets) {
            ConfigurationSection section = config.getConfigurationSection(idnumber);
            name = section.getString("name");
            level = section.getInt("level");
            armorLevel = section.getInt("armorlevel");
            materialS = section.getString("material");
            quality = Quality.valueOf(section.getString("quality"));
            comerciable = section.getBoolean("comerciable");
            upgradable = section.getBoolean("upgradable");
            Set<String> partes = config.getConfigurationSection(idnumber + ".parts").getKeys(false);
            for (String parte : partes) {
                section = config.getConfigurationSection(idnumber + ".parts." + parte);
                materialP = parte;
                mat = Material.matchMaterial(materialS + "_" + materialP);
                physicaldefense = section.getDouble("physicaldefense");
                incphysicaldefense = section.getDouble("incphysicaldefense");
                magicaldefense = section.getDouble("magicaldefense");
                incmagicaldefense = section.getDouble("incmagicaldefense");
                magicalevasion = section.getDouble("magicalevasion");
                incmagicalevasion = section.getDouble("incmagicalevasion");
                physicalevasion = section.getDouble("physicalevasion");
                incphysicalevasion = section.getDouble("incphysicalevasion");
                sellprice = section.getInt("sellprice");
                buyprice = section.getInt("buyprice");
                xpbonus = section.getDouble("xpbonus");
                moneybonus = section.getDouble("moneybonus");
                item = new ItemStack(mat);
                this.armorList.put(name + " " + materialP.toLowerCase(), new RPGArmor(level, item, quality, upgradable, comerciable, armorLevel, mat, name, physicaldefense, incphysicaldefense, physicalevasion, incphysicalevasion, magicaldefense, incmagicaldefense, magicalevasion, incmagicalevasion, moneybonus, xpbonus, buyprice, sellprice));
            }
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public ItemStack getRPGArmorByName(String name) {
        return armorList.get(name).getItem();
    }

    /**
     *
     * @param item
     * @return
     */
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

    /**
     *
     * @return
     */
    public Set<String> getAllArmorNames() {
        Set<String> allArmorNames = new HashSet<>();
        for (Map.Entry<String, RPGArmor> entrySet : armorList.entrySet()) {
            allArmorNames.add(entrySet.getKey());
        }
        return allArmorNames;
    }

    /**
     *
     * @param item
     * @return
     */
    public boolean isRPGArmor(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        return this.armorList.containsKey(getRPGArmorNameByItem(item));
    }

    /**
     *
     * @param item1
     * @param item2
     * @return
     */
    public boolean isSamePart(ItemStack item1, ItemStack item2) {
        String[] partsItem1 = item1.getType().toString().split("_");
        String[] partsItem2 = item2.getType().toString().split("_");
        return partsItem1[1].equals(partsItem2[1]);
    }

    /**
     *
     * @param item
     * @return
     */
    public String getPart(ItemStack item) {
        String[] partItem = item.getType().toString().split("_");
        return partItem[1];
    }

    /**
     *
     * @param armor
     * @return
     */
    public ItemStack upgradeArmor(ItemStack armor) {
        ItemMeta aMeta = armor.getItemMeta();
        String aLongName = aMeta.getDisplayName();
        String[] aLongNameParts = aLongName.split(" ");

        int sizeLongNameParts = aLongNameParts.length;
        String aName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            aName += aLongNameParts[i] + " ";
        }
        RPGArmor rpga = getRPGArmorByItem(armor);
        int nivelactual = rpga.getLevel(aLongNameParts[sizeLongNameParts - 1]);
        int nivelup = nivelactual + 1;
        String displayname = aLongNameParts[0] + " " + aName + "+" + nivelup;
        aMeta.setDisplayName(displayname);
        if (aMeta.hasLore()) {
            List<String> lores = aMeta.getLore();
            List<String> newLores = new ArrayList<>();
            for (String lore : lores) {
                String[] loreparts = lore.split(" ");
                String newLore = loreparts[0] + " " + loreparts[1];
                switch (newLore) {
                    case "Physical Defense":
                        newLores.add(RPGLores.PHYSICALDEFENSE.getLoreString(RPGLores.PHYSICALDEFENSE, rpga.getPhysicalDefense() + rpga.getIncPhysicalDefense() * nivelup));
                        break;
                    case "Physical Evasion":
                        newLores.add(RPGLores.PHYSICALEVASION.getLoreString(RPGLores.PHYSICALEVASION, rpga.getPhysicalEvasion() + rpga.getIncPhysicalEvasion() * nivelup));
                        break;
                    case "Magical Defense":
                        newLores.add(RPGLores.MAGICALDEFENSE.getLoreString(RPGLores.MAGICALDEFENSE, rpga.getMagicaldefense() + rpga.getIncmagicaldefense() * nivelup));
                        break;
                    case "Magical Evasion":
                        newLores.add(RPGLores.MAGICALEVASION.getLoreString(RPGLores.MAGICALEVASION, rpga.getMagicalevasion() + rpga.getIncmagicalevasion() * nivelup));
                        break;
                    case "XP Bonus":
                        newLores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, rpga.getXPBonus()));
                        break;
                    case "Money Bonus":
                        newLores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, rpga.getMoneyBonus()));
                        break;
                }
            }
            if (rpga.isComerciable()) {
                newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, rpga.getBuyPrice()));
                newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, rpga.getSellPrice()));
            } else {
                newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
            }
            if (!rpga.isUpgradable()) {
                newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
            }
            aMeta.setLore(newLores);
            armor.setItemMeta(aMeta);
        }
        return armor;
    }

    /**
     *
     * @param armor
     * @return
     */
    public ItemStack downgradeArmor(ItemStack armor) {
        ItemMeta aMeta = armor.getItemMeta();
        String aLongName = aMeta.getDisplayName();
        String[] aLongNameParts = aLongName.split(" ");
        RPGArmor rpga = getRPGArmorByItem(armor);
        int sizeLongNameParts = aLongNameParts.length;
        String aName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            aName += aLongNameParts[i] + " ";
        }
        int nivelactual = rpga.getLevel(aLongNameParts[sizeLongNameParts - 1]);
        int niveldown = nivelactual - 1;
        String displayname = aLongNameParts[0] + " " + aName + "+" + niveldown;
        aMeta.setDisplayName(displayname);
        if (aMeta.hasLore()) {
            List<String> lores = aMeta.getLore();
            List<String> newLores = new ArrayList<>();
            for (String lore : lores) {
                String[] loreparts = lore.split(" ");
                String newLore = loreparts[0] + " " + loreparts[1];
                switch (newLore) {
                    case "Physical Defense":
                        newLores.add(RPGLores.PHYSICALDEFENSE.getLoreString(RPGLores.PHYSICALDEFENSE, rpga.getPhysicalDefense() + rpga.getIncPhysicalDefense() * niveldown));
                        break;
                    case "Physical Evasion":
                        newLores.add(RPGLores.PHYSICALEVASION.getLoreString(RPGLores.PHYSICALEVASION, rpga.getPhysicalEvasion() + rpga.getIncPhysicalEvasion() * niveldown));
                        break;
                    case "Magical Defense":
                        newLores.add(RPGLores.MAGICALDEFENSE.getLoreString(RPGLores.MAGICALDEFENSE, rpga.getMagicaldefense() + rpga.getIncmagicaldefense() * niveldown));
                        break;
                    case "Magical Evasion":
                        newLores.add(RPGLores.MAGICALEVASION.getLoreString(RPGLores.MAGICALEVASION, rpga.getMagicalevasion() + rpga.getIncmagicalevasion() * niveldown));
                        break;
                    case "XP Bonus":
                        newLores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, rpga.getXPBonus()));
                        break;
                    case "Money Bonus":
                        newLores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, rpga.getMoneyBonus()));
                        break;
                }
            }
            if (rpga.isComerciable()) {
                newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, rpga.getBuyPrice()));
                newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, rpga.getSellPrice()));
            } else {
                newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
            }
            if (!rpga.isUpgradable()) {
                newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
            }
            aMeta.setLore(newLores);
            armor.setItemMeta(aMeta);
        }
        return armor;
    }
}
