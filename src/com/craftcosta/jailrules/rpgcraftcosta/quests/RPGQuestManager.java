/*
 * Copyright (C) 2015 jail
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
