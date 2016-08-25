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
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.io.File;

/**
 *
 * @author jail
 */
public abstract class RPGFinals {

    /**
     *
     */
    public final static String itemImagePackage = "/com/craftcosta/jailrules/rpgcraftcosta/gui/logic/resources/items/";

    /**
     *
     */
    public final static String pluginName = "RPGCraftCosta";

    /**
     *
     */
    public final static String pluginNameFolder = "RPGCraftCosta" + File.separator;
    public final static String pluginDataFolder = pluginNameFolder + File.separator + "data" + File.separator;
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
    public final static String dataFolderA = pluginNameFolder + "data" + File.separator;

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

    /**
     *
     */
    public final static String playerFilePath = playerData + "%player%.yml";

    /**
     *
     */
    public final static String guildsFilePath = dataFolder + "guilds.yml";
    public final static String guildsFile = pluginDataFolder + "guilds.yml";
    /**
     *
     */
    public final static String classFilePath = dataFolder + "classes.yml";
    public final static String classFile = pluginDataFolder + "classes.yml";
    /**
     *
     */
    public final static String partyFilePath = dataFolder + "partyConfig.yml";
    public final static String partyFile = pluginDataFolder + "partyConfig.yml";
    /**
     *
     */
    public final static String weaponFilePath = dataFolder + "weapons.yml";
    public final static String weaponFile = pluginDataFolder + "weapons.yml";
    /**
     *
     */
    public final static String LevelingConfigFile = dataFolder + "levelsConfig.yml";
    public final static String LevelingConfig = pluginDataFolder + "levelsConfig.yml";
    /**
     *
     */
    public final static String MainConfigFile = configFolder + "config.yml";
    public final static String MainConfig = pluginDataFolder + "config.yml";

    /**
     *
     */
    public final static String jewelFilePath = dataFolder + "jewels.yml";
    public final static String jewelFile = pluginDataFolder + "jewels.yml";
    /**
     *
     */
    public final static String jewelsConfigPath = dataFolder + "jewelsConfig.yml";
    public final static String jewelsConfig = pluginDataFolder + "jewelsConfig.yml";
    /**
     *
     */
    public final static String weaponsConfigPath = dataFolder + "weaponsConfig.yml";
    public final static String weaponsConfig = pluginDataFolder + "weaponsConfig.yml";

    /**
     *
     */
    public final static String questItemFilePath = dataFolder + "questItems.yml";
    public final static String questItemFile = pluginDataFolder + "questItems.yml";
    /**
     *
     */
    public final static String armorFilePath = dataFolder + "armor.yml";
    public final static String armorFile = pluginDataFolder + "armor.yml";
    /**
     *
     */
    public final static String armorConfigFilePath = dataFolder + "armorConfig.yml";
    public final static String armorConfigFile = pluginDataFolder + "armorConfig.yml";
    /**
     *
     */
    public final static String chatFileConfig = dataFolder + "chatConfig.yml";
    public final static String chatFile = pluginDataFolder + "chatConfig.yml";

    /**
     *
     */
    public final static String loresFilePath = dataFolder + "lores.yml";
    public final static String loresFile = pluginDataFolder + "lores.yml";
    /**
     *
     */
    public final static String guildsConfigPath = dataFolder + "guildsConfig.yml";
    public final static String guildsConfig = pluginDataFolder + "guildsConfig.yml";

    /**
     *
     */
    public static String mobsFilePath = dataFolder + "mobs.yml";
    public static String mobsFile = pluginDataFolder + "mobs.yml";
    /**
     *
     */
    public static String spawnersFilePath = dataFolder + "spawners.yml";
    public static String spawnersFile = pluginDataFolder + "spawners.yml";

    /**
     *
     */
    public static String entitiesImagePackage = "/com/craftcosta/jailrules/rpgcraftcosta/gui/logic/resources/entities/";
    public static String globalConfigFilePath = dataFolder + "globalConfig.yml";
    public static String globalConfigFile = pluginDataFolder + "globalConfig.yml";
    public static String configFile = pluginDataFolder + "config.yml";
}
