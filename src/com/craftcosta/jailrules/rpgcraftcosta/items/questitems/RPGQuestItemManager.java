/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.questitems;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
public class RPGQuestItemManager {

    private RPGCraftCosta plugin;
    private HashMap<String, RPGQuestItem> questItemList;
    private File questItemFile;
    private File questItemFileConfig;
    private FileConfiguration config;
    private FileConfiguration qIConfig;

    public RPGQuestItemManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.plugin.getLogger().info("Loading jewels module....");
        this.questItemList = new HashMap<>();
        this.questItemFile = new File(RPGFinals.questItemFilePath);
        this.questItemFileConfig = new File(RPGFinals.questItemConfigPath);
        if (!questItemFile.exists()) {
            plugin.getLogger().info("Loading default quest items...");
            questItemFile.getParentFile().mkdirs();
            copy(this.plugin.getResource("questItems.yml"), questItemFile);
        }

        if (!questItemFileConfig.exists()) {
            plugin.getLogger().info("Loading default quest items config...");
            questItemFileConfig.getParentFile().mkdirs();
            copy(this.plugin.getResource("questItemsConfig.yml"), questItemFileConfig);
        }
        loadQuestItems();
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



    private void loadQuestItems() {
        if (!questItemFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.jewelFilePath));
        } else {
            config = YamlConfiguration.loadConfiguration(questItemFile);
        }
        Set<String> questitems = config.getKeys(false);
        for (String questitem : questitems) {
            String name = "";
            ItemStack item = null;
            Quality quality = null;
            List<String> lores = new ArrayList<>();
            ConfigurationSection section = config.getConfigurationSection(questitem);
            name = section.getString("name");
            quality = Enum.valueOf(Quality.class, section.getString("quality"));
            item = new ItemStack(Material.matchMaterial(section.getString("material")));
            lores = section.getStringList("lores");
            questItemList.put(name, new RPGQuestItem(item, name, quality, lores));
        }

    }

    public ItemStack getRPGQuestItemByName(String name) {
        return this.questItemList.get(name).getItem();
    }
}
