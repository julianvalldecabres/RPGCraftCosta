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

    /**
     *
     * @param plugin
     */
    public RPGQuestItemManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.plugin.getLogger().info("Loading questitems module....");
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
        plugin.getLogger().info("Loading quest items...");

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

    /**
     *
     * @param name
     * @return
     */
    public ItemStack getRPGQuestItemByName(String name) {
        return this.questItemList.get(name).getItem();
    }
}
