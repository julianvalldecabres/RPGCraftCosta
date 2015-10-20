/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.leveling;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jail
 */
public class RPGLevelManager {

    RPGCraftCosta plugin;
    RPGLevels levels;

    /**
     *
     * @param p
     */
    public RPGLevelManager(RPGCraftCosta p) {
        plugin = p;
        levels = loadLevels();
    }

    private RPGLevels loadLevels() {
        File levelCfg = new File(RPGFinals.LevelingConfigFile);
        if (!levelCfg.exists()) {
            try {
                levelCfg.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(RPGLevelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return createDefaultConfig(levelCfg);
        } else {
            return loadConfig(levelCfg);
        }
    }

    private RPGLevels createDefaultConfig(File levelCfg) {
        return new RPGLevels();
    }

    private RPGLevels loadConfig(File levelCfg) {
        return new RPGLevels(levelCfg);
    }
}
