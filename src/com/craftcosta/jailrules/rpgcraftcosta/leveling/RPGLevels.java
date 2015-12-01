/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private TreeMap<Double, Integer> levels;

    /**
     *
     */
    public RPGLevels() {
        createDefaults();
    }

    RPGLevels(File levelCfg) {
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
        double exp;
        for (int i = 1; i <= this.max_level; i++) {
            exp = (int) (this.a * Math.pow(i, 3) + this.b * Math.pow(i, 2) + this.c * i);
            this.levels.put(exp, i);
        }
    }

    /**
     *
     * @param exp
     * @param inc
     * @return
     */
    public boolean checkLevelUp(double exp, double inc) {
        double actualexp = exp;
        double lvl;
        double newexp = 0;
        if (inc > 0) {
            newexp = exp + inc;
            return levels.lowerEntry(exp).getValue() != levels.lowerEntry(newexp).getValue();
        }
        return false;
    }

    /**
     *
     * @return
     */
    public TreeMap<Double, Integer> getTree() {
        return levels;
    }

    /**
     *
     * @param exp
     * @return
     */
    public float getPercentageForNextLevel(double exp) {
        float result = 0.0f;
        double nextLvlExp;
        double actualExp;
        double actualLvlExp;
        double startLvlExp;
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
    public double getExperienceForNectLevel(double exp) {
        double nextLvlExp = levels.higherKey(exp);
        double actualExp = exp;
        return nextLvlExp - actualExp;
    }

    /**
     *
     * @param exp
     * @return
     */
    public int getNextLevel(double exp) {
        return levels.higherEntry(exp).getValue();
    }

    /**
     *
     * @param exp
     * @return
     */
    public int getLevelBasedOnExp(double exp) {
        return levels.floorEntry(exp).getValue();
    }
}
