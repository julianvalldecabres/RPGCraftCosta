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
