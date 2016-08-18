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
package com.craftcosta.jailrules.rpgcraftcosta.leveling;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGLevels {

    private double a;
    private double b;
    private double c;
    private int max_level;
    private TreeMap<Long, Integer> levels;

    /**
     *
     */
    public RPGLevels() {
        createDefaults();
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param max_level
     */
    public RPGLevels(double a, double b, double c, int max_level) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.max_level = max_level;
        createTreeLevels();
    }

    /**
     *
     * @param levelCfg
     */
    public RPGLevels(File levelCfg) {
        loadConfig(levelCfg);
    }

    void createDefaults() {
        File levelConfig = new File(RPGFinals.LevelingConfigFile);
        if (!levelConfig.exists()) {
            try {
                levelConfig.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(RPGLevels.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileConfiguration section = YamlConfiguration.loadConfiguration(levelConfig);
        this.a = 0;
        section.addDefault("a", 0);
        this.b = 20;
        section.addDefault("b", 20);
        this.c = 30;
        section.addDefault("c", 30);
        this.max_level = 50;
        section.addDefault("max_level", 50);
        try {
            section.save(levelConfig);
        } catch (IOException ex) {
            Logger.getLogger(RPGLevels.class.getName()).log(Level.SEVERE, null, ex);
        }
        createTreeLevels();
    }

    private void loadConfig(File levelConfig) {
        FileConfiguration section = YamlConfiguration.loadConfiguration(levelConfig);
        this.a = section.getDouble("a");
        this.b = section.getDouble("b");
        this.c = section.getDouble("c");
        this.max_level = section.getInt("max_level");
        createTreeLevels();
    }

    private void createTreeLevels() {
        this.levels = new TreeMap<>();
        long exp;
        for (int i = 1; i <= this.max_level; i++) {
            exp = (long) (this.a * Math.pow(i, 3) + this.b * Math.pow(i, 2) + this.c * i);
            this.levels.put(exp, i);
        }
    }

    /**
     *
     * @return
     */
    public TreeMap<Long, Integer> getTree() {
        return levels;
    }

}
