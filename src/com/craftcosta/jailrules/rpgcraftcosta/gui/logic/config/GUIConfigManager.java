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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.config;

import com.craftcosta.jailrules.rpgcraftcosta.gui.GUI;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GUIConfigManager {

    GUI gui;
    File file;
    FileConfiguration fileConfig;
    private boolean daycycle;
    private boolean enablePvP;
    private boolean enableGuilds;
    private boolean enableParties;
    private boolean allowBlockPlace;
    private boolean allowBlockDestroy;
    private boolean hunger;
    private boolean falldamage;
    private boolean drowndamage;
    private double spawnx;
    private double spawny;
    private double spawnz;
    private String world;

    public GUIConfigManager(GUI gui) {
        this.gui = gui;
        file = new File(RPGFinals.globalConfigFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GUIConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadConfigFromFile();

    }

    public boolean isDaycycle() {
        return daycycle;
    }

    public void setDaycicle(boolean daycycle) {
        this.daycycle = daycycle;
    }

    public boolean isEnablePvP() {
        return enablePvP;
    }

    public void setEnablePvP(boolean enablePvP) {
        this.enablePvP = enablePvP;
    }

    public boolean isEnableGuilds() {
        return enableGuilds;
    }

    public void setEnableGuilds(boolean enableGuilds) {
        this.enableGuilds = enableGuilds;
    }

    public boolean isEnableParties() {
        return enableParties;
    }

    public void setEnableParties(boolean enableParties) {
        this.enableParties = enableParties;
    }

    public boolean isAllowBlockPlace() {
        return allowBlockPlace;
    }

    public void setAllowBlockPlace(boolean allowBlockPlace) {
        this.allowBlockPlace = allowBlockPlace;
    }

    public boolean isAllowBlockDestroy() {
        return allowBlockDestroy;
    }

    public void setAllowBlockDestroy(boolean allowBlockDestroy) {
        this.allowBlockDestroy = allowBlockDestroy;
    }

    public boolean isHunger() {
        return hunger;
    }

    public void setHunger(boolean hunger) {
        this.hunger = hunger;
    }

    public boolean isFalldamage() {
        return falldamage;
    }

    public void setFalldamage(boolean falldamage) {
        this.falldamage = falldamage;
    }

    public boolean isDrowndamage() {
        return drowndamage;
    }

    public void setDrowndamage(boolean drowndamage) {
        this.drowndamage = drowndamage;
    }

    public double getSpawnx() {
        return spawnx;
    }

    public void setSpawnx(double spawnx) {
        this.spawnx = spawnx;
    }

    public double getSpawny() {
        return spawny;
    }

    public void setSpawny(double spawny) {
        this.spawny = spawny;
    }

    public double getSpawnz() {
        return spawnz;
    }

    public void setSpawnz(double spawnz) {
        this.spawnz = spawnz;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    private void loadConfigFromFile() {
        fileConfig = YamlConfiguration.loadConfiguration(file);
        this.spawnx = fileConfig.getDouble("spawnx");
        gui.getSpinnerXInicio().setValue(this.spawnx);
        this.spawny = fileConfig.getDouble("spawny");
        gui.getSpinnerYInicio().setValue(this.spawny);
        this.spawnz = fileConfig.getDouble("spawnz");
        gui.getSpinnerZInicio().setValue(this.spawnz);
        this.daycycle = fileConfig.getBoolean("daycycle");
        gui.getCheckDayCycle().setSelected(this.daycycle);
        this.world = fileConfig.getString("world");
        gui.getTxtMundoInicio().setText(this.world);
        this.allowBlockDestroy = fileConfig.getBoolean("allowBlockDestroy");
        gui.getCheckDestruirBloques().setSelected(this.allowBlockDestroy);
        this.allowBlockPlace = fileConfig.getBoolean("allowBlockPlace");
        gui.getCheckColocarBloques().setSelected(this.allowBlockPlace);
        this.falldamage = fileConfig.getBoolean("falldamage");
        gui.getCheckDanioCaida().setSelected(this.falldamage);
        this.drowndamage = fileConfig.getBoolean("drowndamage");
        gui.getCheckDanioAhogo().setSelected(this.drowndamage);
        this.enablePvP = fileConfig.getBoolean("enablePvP");
        gui.getCheckDanioPvp().setSelected(this.enablePvP);
        this.hunger = fileConfig.getBoolean("hunger");
        gui.getCheckSistHambre().setSelected(this.hunger);
        this.enableGuilds = fileConfig.getBoolean("enableGuilds");
        gui.getCheckClanes().setSelected(this.enableGuilds);
        this.enableParties = fileConfig.getBoolean("enableParties");
        gui.getCheckGrupos().setSelected(this.enableParties);
        gui.repaint();
    }

    public void saveConfig() {
        file = new File(RPGFinals.globalConfigFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
        fileConfig.set("hunger", gui.getCheckSistHambre().isSelected());
        fileConfig.set("falldamage", gui.getCheckDanioCaida().isSelected());
        fileConfig.set("drowndamage", gui.getCheckDanioAhogo().isSelected());
        fileConfig.set("daycycle", gui.getCheckDayCycle().isSelected());
        fileConfig.set("enablePvP", gui.getCheckDanioPvp().isSelected());
        fileConfig.set("spawnx", gui.getSpinnerXInicio().getValue());
        fileConfig.set("spawny", gui.getSpinnerYInicio().getValue());
        fileConfig.set("spawnz", gui.getSpinnerZInicio().getValue());
        fileConfig.set("world", gui.getTxtMundoInicio().getText());
        fileConfig.set("enableGuilds", gui.getCheckClanes().isSelected());
        fileConfig.set("enableParties", gui.getCheckGrupos().isSelected());
        fileConfig.set("allowBlockPlace", gui.getCheckColocarBloques().isSelected());
        fileConfig.set("allowBlockDestroy", gui.getCheckDestruirBloques().isSelected());
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
