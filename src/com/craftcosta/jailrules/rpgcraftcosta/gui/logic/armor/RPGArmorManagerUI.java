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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.armor;

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

    /**
     *
     */
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
