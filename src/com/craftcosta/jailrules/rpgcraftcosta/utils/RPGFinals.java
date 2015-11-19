/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.io.File;

/**
 *
 * @author jail
 */
public abstract class RPGFinals {

    // FINALS
    /**
     *
     */
    public final static String pluginName = "RPGCraftCosta";

    // FOLDERS
    /**
     *
     */
    public final static String pluginsFolder = "plugins" + File.separator;

    /**
     *
     */
    public final static String mainFolder = pluginsFolder + "RPGCraftCosta" + File.separator;

    /**
     *
     */
    public final static String dataFolder = mainFolder + "data" + File.separator;

    /**
     *
     */
    public final static String configFolder = mainFolder + "config" + File.separator;

    /**
     *
     */
    public final static String langFolder = configFolder + "lang" + File.separator;

    /**
     *
     */
    public final static String playerData = dataFolder + "playerdata" + File.separator;

    // FILES
    // data
    /**
     *
     */
    public final static String playerFilePath = playerData + "%player%.yml";

    /**
     *
     */
    public final static String guildsFilePath = dataFolder + "guilds.yml";

    /**
     *
     */
    public final static String classFilePath = dataFolder + "classes.yml";

    /**
     *
     */
    public final static String partyFilePath = dataFolder + "partyConfig.yml";
    /**
     *
     */
    public final static String weaponFilePath = dataFolder + "weapons.yml";
    /**
     *
     */
    public final static String recipeFilePath = dataFolder + "recipes.yml";
    /**
     *
     */
    public final static String recipeConfigPath = dataFolder + "recipesConfig.yml";
    /**
     *
     */
    public final static String itemsFilePath = configFolder + "items.yml";

    /**
     *
     */
    public final static String questFilePath = configFolder + "quests.yml";
    // config

    /**
     *
     */
    public final static String LevelingConfigFile = configFolder + "leveling.yml";

    /**
     *
     */
    public final static String MainConfigFile = configFolder + "config.yml";

    /**
     *
     */
    public final static String messagesFilePath = langFolder + "%lang%.yml";
    public final static String jewelFilePath = dataFolder + "jewels.yml";
    public final static String jewelsConfigPath = dataFolder + "jewelsConfig.yml";
    public static String weaponsConfigPath = dataFolder + "weaponsConfig.yml";
    public static String questItemFilePath = dataFolder + "questItems.yml";
    public static String questItemConfigPath = dataFolder + "questItemsConfig.yml";
    public static String armorFilePath = dataFolder + "armor.yml";
    public static String armorConfigFilePath = dataFolder + "armorConfig.yml";
    public static String chatFileConfig = dataFolder + "chatConfig.yml";
    public static String chatTasksFilePath = dataFolder + "chatTasks.yml";
    public static String loresFilePath = dataFolder + "lores.yml";
}
