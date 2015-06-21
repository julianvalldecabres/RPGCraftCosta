/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

/**
 *
 * @author jail
 */
public abstract class RPGFinals {

    // FOLDERS
    public final static String mainFolder = "plugins/RPGCraftCosta/";
    public final static String dataFolder = "plugins/RPGCraftCosta/data/";
    public final static String configFolder = "plugins/RPGCraftCosta/myConfig/";
    public final static String messagesFolder = configFolder + "Messages/";
    public final static String playerData = dataFolder + "playerdata/";
    public final static String uuidPlayerData = dataFolder + "uuidPlayerdata/";

    // FILES
    // data
    public final static String playerFilePath = playerData + "%player%.yml";
    public final static String uuidplayerFilePath = dataFolder + "uuids.yml"; 
    public final static String guildsFilePath = dataFolder + "guilds.yml";
    public final static String partyFilePath = dataFolder + "partys.yml";
    // config
    public final static String configFilePath = configFolder + "config.yml";
    public final static String expFilePath = configFolder + "values.yml";
    public final static String mysqlFilePath = configFolder + "mysql.yml";
    public final static String messagesFilePath = messagesFolder + "messages.yml";
    public final static String classFilePath = configFolder + "classes.yml";
    public final static String itemsFilePath = configFolder + "items.yml";
    public final static String questFilePath = configFolder + "quests.yml";

    // FINALS
    public final static String pluginName = "RPGCraftCosta";

}
