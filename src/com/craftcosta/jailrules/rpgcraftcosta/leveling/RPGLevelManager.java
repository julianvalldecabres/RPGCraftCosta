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

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGLevelManager {

    private RPGCraftCosta plugin;
    private File levelf;
    private FileConfiguration levelfc;
    private double a;
    private double b;
    private double c;
    private int max_level;
    private int ph;
    private TreeMap<Long, Integer> levels;

    /**
     *
     * @param plugin
     */
    public RPGLevelManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.levels = new TreeMap<>();
        this.levelf = new File(RPGFinals.LevelingConfigFile);
        if (!levelf.exists()) {
            try {
                levelf.createNewFile();
                createDefaultConfig();
            } catch (IOException ex) {
                Logger.getLogger(RPGLevelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadLevels();
        for (Map.Entry<Long, Integer> entrySet : levels.entrySet()) {
            plugin.getLogger().info(entrySet.getKey()+" nivel "+entrySet.getValue());
            
        }
        
    }

    private void loadLevels() {
        levelfc = YamlConfiguration.loadConfiguration(levelf);
        this.a = levelfc.getDouble("a");
        this.b = levelfc.getDouble("b");
        this.c = levelfc.getDouble("c");
        this.max_level = levelfc.getInt("max");
        this.ph=levelfc.getInt("ph");
        createTreeLevels();
    }

    private void createDefaultConfig() {
        levelfc = YamlConfiguration.loadConfiguration(levelf);
        levelfc.set("a", 0.0);
        this.a=0;
        levelfc.set("b", 50.0);
        this.b=50;
        levelfc.set("c", 30.0);
        this.c=30;
        levelfc.set("max", 50);
        this.max_level=50;
        levelfc.set("ph", 4);
        this.ph=4;
        try {
            levelfc.save(levelf);
        } catch (IOException ex) {
            Logger.getLogger(RPGLevelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param exp
     * @param inc
     * @return
     */
    public boolean checkLevelUp(long exp, long inc) {
        double actualexp = exp;
        double lvl;
        long newexp = 0;
        if (inc > 0) {
            newexp = exp + inc;
            plugin.getLogger().info(""+exp);
            plugin.getLogger().info(""+newexp);
            return ((int)levels.lowerEntry(exp).getValue())!=((int) levels.lowerEntry(newexp).getValue());
        }
        return false;
    }

    /**
     *
     * @param exp
     * @return
     */
    public float getPercentageForNextLevel(long exp) {
        float result = 0.0f;
        long nextLvlExp;
        double actualExp;
        double actualLvlExp;
        long startLvlExp;
        double expOfLvl;
        actualExp = exp;
        nextLvlExp = levels.higherKey(exp);
        startLvlExp = levels.lowerKey(exp);
        expOfLvl = nextLvlExp - startLvlExp;
        actualLvlExp = actualExp - startLvlExp;
        result = (float) Math.abs((actualLvlExp / expOfLvl) - 1);
        return result;
    }

    /**
     *
     * @param exp
     * @return
     */
    public double getExperienceForNextLevel(long exp) {
        long nextLvlExp = levels.higherKey(exp);
        long actualExp = exp;
        return nextLvlExp - actualExp;
    }

    /**
     *
     * @param exp
     * @return
     */
    public int getNextLevel(long exp) {
        return levels.higherEntry(exp).getValue();
    }

    /**
     *
     * @param exp
     * @return
     */
    public int getLevelBasedOnExp(long exp) {
        return levels.floorEntry(exp).getValue();
    }

    private void createTreeLevels() {
        plugin.getLogger().info("creando niveles");
        this.levels = new TreeMap<>();
        long exp;
        this.levels.put((long)0, 0);
        for (int i = 1; i <= this.max_level; i++) {
            exp = (long) (this.a * Math.pow(i, 3) + this.b * Math.pow(i, 2) + this.c * i);
            this.levels.put(exp, i);
        }
        
    }
}
