/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.classes;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGClassManager {

    private final RPGCraftCosta plugin;
    private static HashMap<String, RPGClass> listClasses;

    /**
     *
     * @param plugin
     */
    public RPGClassManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        makeDefaults();
    }

    /**
     *
     * @return
     */
    public static String getListAvailableClasses() {
        return listClasses.keySet().toString();
    }

    /**
     *
     * @param name
     * @return
     */
    public static RPGClass getRPGClass(String name) {
        return listClasses.get(name);
    }

    private void makeDefaults() {
        File config = new File(RPGFinals.classFilePath);
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(RPGClassManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            createDefaultConfig(config);
        } else {
            loadConfig(config);
        }
    }

    private void loadConfig(File config) {
        FileConfiguration classes = YamlConfiguration.loadConfiguration(config);
        for (String entry : classes.getKeys(false)) {
            ConfigurationSection section = classes.getConfigurationSection(entry);
            if (section.getBoolean("enabled")) {
                RPGClass clase;
                clase = new RPGClass(config, entry);
                addRPGClassToList(clase.getNameClass(), clase);
            }
        }
    }

    private void createDefaultConfig(File config) {
        RPGClass defaults = new RPGClass();
        listClasses.put("Default", defaults);
    }

    private void addRPGClassToList(String nameClass, RPGClass clase) {
        listClasses.put(nameClass, clase);
    }

}
