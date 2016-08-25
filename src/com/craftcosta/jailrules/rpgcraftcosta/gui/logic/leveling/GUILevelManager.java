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
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUILevelManager {

    private double a;
    private double b;
    private double c;
    private int max;
    private int ph;
    private GUI gui;
    private File file;
    private FileConfiguration fileConfig;

    public GUILevelManager(GUI gui) {
        this.gui = gui;
        file = new File(RPGFinals.LevelingConfig);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUILevelManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadConfig();
    }

    private void loadConfig() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        this.a = fileConfig.getDouble("a");
        gui.getSpinnerX3Nivel().setValue(this.a);
        this.b = fileConfig.getDouble("b");
        gui.getSpinnerX2Nivel().setValue(this.b);
        this.c = fileConfig.getDouble("c");
        gui.getSpinnerXNivel().setValue(this.c);
        this.max = fileConfig.getInt("max");
        gui.getSpinnerMaxNivel().setValue(this.max);
        this.ph = fileConfig.getInt("ph");
        gui.getSpinnerPHxNivel().setValue(this.ph);
    }

    public void saveConfig() {
        file = new File(RPGFinals.LevelingConfig);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
        fileConfig.set("a", this.a);
        fileConfig.set("b", this.b);
        fileConfig.set("c", this.c);
        fileConfig.set("max", this.max);
        fileConfig.set("ph", this.ph);
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

}
