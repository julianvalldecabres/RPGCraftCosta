/*
 * Copyright (C) 2016 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic;

import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGArmorManagerUI {
    private HashMap<String, RPGArmorL> armorList;
    private File armorFile;
    private File armorConfig;
    private FileConfiguration config;
    private FileConfiguration aConfig;
    private double breakprobability;
    private double nothingprobability;
    private double improveprobability;
    private ItemStack armorUpgrader;
    private double detteroriateprobability;

    public RPGArmorManagerUI() {
        armorList= new HashMap<>();
        armorFile= new File(RPGFinals.armorFilePath);
        armorConfig= new File(RPGFinals.armorConfigFilePath);
        loadConfig();
        loadArmor();
        
    }

    private void loadConfig() {
        aConfig = YamlConfiguration.loadConfiguration(armorConfig);
        ConfigurationSection section = aConfig.getConfigurationSection("armor");
        Material armorMat = Material.matchMaterial(section.getString("material"));
        String armordisplayname = section.getString("name");
        List<String> armorlores = section.getStringList("lores");
        this.breakprobability = Double.parseDouble(aConfig.getString("breakprobability"));
        this.detteroriateprobability = Double.parseDouble(aConfig.getString("detteroriateprobability"));
        this.nothingprobability = Double.parseDouble(aConfig.getString("nothingprobability"));
        this.improveprobability = Double.parseDouble(aConfig.getString("improveprobability"));
    }

    private void loadArmor() {
        config = YamlConfiguration.loadConfiguration(armorFile);
        String name;
        //ItemStack item;
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
                section = config.getConfigurationSection(setName + ".parts." + parte);
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
                apbonus = section.getDouble("apbonus");
                xpbonus = section.getDouble("xpbonus");
                moneybonus = section.getDouble("moneybonus");
                this.armorList.put(name + " " + materialP.toLowerCase(), new RPGArmorL(level, quality, upgradable, comerciable, armorLevel, mat, setName, physicaldefense, incphysicaldefense, physicalevasion, incphysicalevasion, magicaldefense, incmagicaldefense, magicalevasion, incmagicalevasion, moneybonus, apbonus, xpbonus, buyprice, sellprice));
            }
        }
        System.out.println(armorList.size());
    }
    
    
}
