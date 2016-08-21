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
    public final static String itemImagePackage= "/com/craftcosta/jailrules/rpgcraftcosta/gui/logic/resources/items/";

    /**
     *
     */
    public final static String pluginName = "RPGCraftCosta";

    /**
     *
     */
    public final static String pluginNameFolder= "RPGCraftCosta"+File.separator;

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
    public final static String dataFolderA = pluginNameFolder+"data"+File.separator;

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

    /**
     *
     */
    public final static String LevelingConfigFile = dataFolder + "levelsConfig.yml";

    /**
     *
     */
    public final static String MainConfigFile = configFolder + "config.yml";

    /**
     *
     */
    public final static String messagesFilePath = langFolder + "%lang%.yml";

    /**
     *
     */
    public final static String jewelFilePath = dataFolder + "jewels.yml";

    /**
     *
     */
    public final static String jewelsConfigPath = dataFolder + "jewelsConfig.yml";

    /**
     *
     */
    public final static String weaponsConfigPath = dataFolder + "weaponsConfig.yml";

    /**
     *
     */
    public final static String questItemFilePath = dataFolder + "questItems.yml";

    /**
     *
     */

    public final static String questItemConfigPath = dataFolder + "questItemsConfig.yml";

    /**
     *
     */
    public final static String armorFilePath = dataFolder + "armor.yml";

    /**
     *
     */
    public final static String armorConfigFilePath = dataFolder + "armorConfig.yml";

    /**
     *
     */
    public final static String chatFileConfig = dataFolder + "chatConfig.yml";

    /**
     *
     */
    public final static String chatTasksFilePath = dataFolder + "chatTasks.yml";

    /**
     *
     */
    public final static String loresFilePath = dataFolder + "lores.yml";

    /**
     *
     */
    public final static String guildsConfigPath = dataFolder + "guildConfig.yml";

    /**
     *
     */
    public static String questFileConfig = dataFolder + "questsConfig.yml";

    /**
     *
     */
    public static String mobsFilePath = dataFolder + "mobs.yml";

    /**
     *
     */
    public static String spawnersFilePath= dataFolder + "spawners.yml";

    /**
     *
     */
    public static String entitiesImagePackage= "/com/craftcosta/jailrules/rpgcraftcosta/gui/logic/resources/entities/";
    public static String globalConfigFilePath= dataFolder + "globalConfig.yml";
    
}
