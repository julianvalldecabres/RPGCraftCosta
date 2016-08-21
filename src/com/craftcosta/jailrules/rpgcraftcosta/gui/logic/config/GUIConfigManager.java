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

/**
 *
 * @author jail
 */
public class GUIConfigManager {
    GUI gui;
    private boolean daycicle;
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
        
    }

    public boolean isDaycicle() {
        return daycicle;
    }

    public void setDaycicle(boolean daycicle) {
        this.daycicle = daycicle;
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
    
    
}
