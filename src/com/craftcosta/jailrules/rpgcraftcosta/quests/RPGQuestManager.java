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
package com.craftcosta.jailrules.rpgcraftcosta.quests;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGQuestManager {

    private RPGCraftCosta plugin;
    private File questsFilePath;
    private File questsFileConfig;
    private FileConfiguration qConfig;
    private FileConfiguration qCConfig;
    private Map<String, RPGQuest> questlist;

    /**
     *
     * @param plugin
     */
    public RPGQuestManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading quests module....");
        this.questlist = new HashMap<>();
        this.questsFilePath = new File(RPGFinals.questFilePath);
        if (!questsFilePath.exists()) {
            plugin.getLogger().info("Loading default quests...");
            questsFilePath.getParentFile().mkdirs();
            copy(plugin.getResource("quests.yml"), questsFilePath);
        }
        this.questsFileConfig = new File(RPGFinals.questFileConfig);
        if (!questsFileConfig.exists()) {
            plugin.getLogger().info("Loading default quests config...");
            questsFilePath.getParentFile().mkdirs();
            copy(plugin.getResource("questsConfig.yml"), questsFilePath);
        }
        loadQuestsConfig();
        loadQuests();
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

    private void loadQuestsConfig() {
        qCConfig = YamlConfiguration.loadConfiguration(questsFileConfig);
    }

    private void loadQuests() {
        qConfig = YamlConfiguration.loadConfiguration(questsFilePath);
    }
}
