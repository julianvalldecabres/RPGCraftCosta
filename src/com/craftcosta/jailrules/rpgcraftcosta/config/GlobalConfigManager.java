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
package com.craftcosta.jailrules.rpgcraftcosta.config;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class GlobalConfigManager {
    
    private RPGCraftCosta plugin;
    
    private boolean hunger;
    private boolean falldamage;
    private boolean drowndamage;
    private boolean enablePvP;
    private double spawnx;
    private double spawny;
    private double spawnz;
    private String world;
    private boolean enableGuilds;
    private boolean enableParties;
    private boolean allowBlockPlace;
    private boolean allowBlockDestroy;
    private boolean daycycle;
    private File configFile;
    private FileConfiguration configFileConfig;

    public GlobalConfigManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading global config module....");
        this.configFile= new File(RPGFinals.globalConfigFilePath);
        if(!configFile.exists()){
            plugin.getLogger().info("Loading default global config...");
            configFile.getParentFile().mkdirs();
            copy(plugin.getResource("globalConfig.yml"),configFile);
        }
        loadGlobalConfig();
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

    private void loadGlobalConfig() {
        configFileConfig= YamlConfiguration.loadConfiguration(configFile);
        plugin.getLogger().info("Loading global config...");
        this.spawnx=configFileConfig.getDouble("spawnx");
        this.spawny=configFileConfig.getDouble("spawny");
        this.spawnz=configFileConfig.getDouble("spawnz");
        this.daycycle= configFileConfig.getBoolean("daycicle");
        for(World w:plugin.getServer().getWorlds()){
            if(daycycle){
                w.setGameRuleValue("doDaylightCycle", "true");
            }else{
                w.setGameRuleValue("doDaylightCycle", "false");
            }
        }
        this.world=configFileConfig.getString("world");
        this.allowBlockDestroy= configFileConfig.getBoolean("allowBlockDestroy");
        this.allowBlockPlace= configFileConfig.getBoolean("allowBlockPlace");
        this.falldamage= configFileConfig.getBoolean("falldamage");
        this.drowndamage= configFileConfig.getBoolean("drowndamage");
        this.enablePvP= configFileConfig.getBoolean("enablePvP");
        this.hunger= configFileConfig.getBoolean("hunger");
        this.enableGuilds= configFileConfig.getBoolean("enableGuilds");
        this.enableParties= configFileConfig.getBoolean("enableParties");        
    }
    
    public void saveConfig(){
        configFile = new File(RPGFinals.globalConfigFilePath);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        configFileConfig = YamlConfiguration.loadConfiguration(configFile);
        configFileConfig.set("hunger",this.hunger);
        configFileConfig.set("falldamage",this.falldamage);
        configFileConfig.set("drowndamage",this.drowndamage);
        configFileConfig.set("daycycle",this.daycycle);
        configFileConfig.set("enablePvP",this.enablePvP);
        configFileConfig.set("spawnx",this.spawnx);
        configFileConfig.set("spawny",this.spawny);
        configFileConfig.set("spawnz",this.spawnz);
        configFileConfig.set("world",this.world);
        configFileConfig.set("enableGuilds",this.enableGuilds);
        configFileConfig.set("enableParties",this.enableParties);
        configFileConfig.set("allowBlockPlace",this.allowBlockPlace);
        configFileConfig.set("allowBlockDestroy",this.allowBlockDestroy);
        try {
            configFileConfig.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDaycycle() {
        return daycycle;
    }

    public void setDaycycle(boolean daycycle) {
        this.daycycle = daycycle;
        for(World w:plugin.getServer().getWorlds()){
            if(daycycle){
                w.setGameRuleValue("doDaylightCycle", "true");
            }else{
                w.setGameRuleValue("doDaylightCycle", "false");
            }
        }
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

    public boolean isEnablePvP() {
        return enablePvP;
    }

    public void setEnablePvP(boolean enablePvP) {
        this.enablePvP = enablePvP;
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
    
    
}
