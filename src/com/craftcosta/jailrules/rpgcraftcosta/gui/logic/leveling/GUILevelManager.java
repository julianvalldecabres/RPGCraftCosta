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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.leveling;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author jail
 */
public class GUILevelManager {
    private GUI gui;
    private File levelFile;
    private FileConfiguration levelFileConfig;

    public GUILevelManager(GUI gui) {
        this.gui = gui;
        System.out.println("prueba rutas a ficheros");
        levelFile= new File(RPGFinals.LevelingConfigFile);
        if(!levelFile.exists()){
            try {
                levelFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUILevelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadConfig();
    }

    private void loadConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
