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
package com.craftcosta.jailrules.rpgcraftcosta;

import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatCommands;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatListener;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassCommands;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.config.GlobalConfigManager;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGTradeCommands;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGTradeListeners;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGTradeManager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.CustomEntityType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMobManager;

import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildCommands;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItemManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorListener;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelListener;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponListener;
import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMobListener;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildListener;
import com.craftcosta.jailrules.rpgcraftcosta.leveling.RPGLevelManager;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyCommands;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyListener;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerCommands;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerListener;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * La clase RPGCraftCosta es la principal de todo el plugin
 *
 * @author Jail
 */
public class RPGCraftCosta extends JavaPlugin {

    //Campos de la clase
    static RPGCraftCosta rpgCraftCosta;
    private final FileConfiguration config;
    private GlobalConfigManager rpgConfig;
    private RPGChatManager rpgChatManager;
    private RPGPlayerManager rpgPlayerManager;
    private RPGLevelManager rpgLevelManager;
    private RPGClassManager rpgClassManager;
    private RPGGuildManager rpgGuildManager;
    private RPGItemManager rpgItemManager;
    private RPGMobManager rpgMobManager;
    private RPGTradeManager rpgTradeManager;
    private RPGClassCommands myClassCommands;
    private RPGChatCommands myChatCommands;
    private RPGPartyCommands myPartyCommands;
    private RPGPartyManager rpgPartyManager;
    private RPGGuildCommands myGuildCommands;
    private RPGPlayerCommands myPlayerCommands;
    private RPGTradeCommands myTradeCommands;

    /**
     * Constructor de la clase RPGCraftCosta Se hace uso del patron singleton
     * para evitar la creacion de mas de 1 instancia
     */
    public RPGCraftCosta() {
        rpgCraftCosta = this;
        this.config = this.getConfig();
    }//Cierre del constructor

    /**
     * Metodo que devuelve el objeto RPGCraftCosta
     *
     * @return el objeto RPGCraftCosta
     */
    public RPGCraftCosta getPlugin() {
        return this;
    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGPlayerManager
     *
     * @return el objeto RPGPlayerManager
     */
    public RPGPlayerManager getRPGPlayerManager() {
        return rpgPlayerManager;
    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGClassManager
     *
     * @return el objeto RPGClassManager
     */
    public RPGClassManager getRPGClassManager() {
        return rpgClassManager;
    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGGuildManager
     *
     * @return el objeto RPGGuildManager
     */
    public RPGGuildManager getRPGGuildManager() {
        return rpgGuildManager;
    }//Cierre del metodo

    /**
     * Metodo necesario para que el plugin se inicie. Al iniciar el servidor
     * este metodo se encarga de inicializar todos los objetos necesarios para
     * la ejecucion del plugin en el servidor
     */
    @Override
    public void onEnable() {
        getLogger().info("Loading modules...");

        CustomEntityType.registerEntities();
        //Managers
        this.rpgConfig = new GlobalConfigManager(this);
        this.rpgChatManager = new RPGChatManager(this);
        this.rpgItemManager = new RPGItemManager(this);
        this.rpgClassManager = new RPGClassManager(this);
        this.rpgLevelManager = new RPGLevelManager(this);
        this.rpgPlayerManager = new RPGPlayerManager(this);
        if (getRpgConfig().isEnableParties()) {
            this.rpgPartyManager = new RPGPartyManager(this);
        }
        if (getRpgConfig().isEnableGuilds()) {
            this.rpgGuildManager = new RPGGuildManager(this);
        }
        this.rpgMobManager = new RPGMobManager(this);
        this.rpgTradeManager = new RPGTradeManager(this);
        //Commands
        myChatCommands = new RPGChatCommands(this);
        myClassCommands = new RPGClassCommands(this);
        if (getRpgConfig().isEnableParties()) {
            myPartyCommands = new RPGPartyCommands(this);
        }
        if (getRpgConfig().isEnableGuilds()) {
            myGuildCommands = new RPGGuildCommands(this);
        }
        myPlayerCommands = new RPGPlayerCommands(this);
        myTradeCommands = new RPGTradeCommands(this);
        getCommand("task").setExecutor(myChatCommands);
        getCommand("class").setExecutor(myClassCommands);
        getCommand("class").setTabCompleter(myClassCommands);
        if (getRpgConfig().isEnableParties()) {
            getCommand("party").setExecutor(myPartyCommands);
            getCommand("party").setTabCompleter(myPartyCommands);
        }
        if (getRpgConfig().isEnableGuilds()) {
            getCommand("guild").setExecutor(myGuildCommands);
            getCommand("guild").setTabCompleter(myGuildCommands);
        }
        getCommand("player").setExecutor(myPlayerCommands);
        getCommand("trato").setExecutor(myTradeCommands);
        //Listeners
        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new RPGJewelListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGWeaponListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGArmorListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGChatListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGPlayerListener(this), this);
        if (getRpgConfig().isEnableGuilds()) {
            getServer().getPluginManager().registerEvents(new RPGGuildListener(this), this);
        }
        if (getRpgConfig().isEnableParties()) {
            getServer().getPluginManager().registerEvents(new RPGPartyListener(this), this);
        }
        getServer().getPluginManager().registerEvents(new RPGMobListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGTradeListeners(this), this);
    }//Cierre del metodo

    /**
     * Metodo necesario para que el plugin se detenga al parar o reiniciar el
     * servidor Este metodo se encargara de guardar los cambios de jugadores
     * configuraciones y datos.
     */
    @Override
    public void onDisable() {
        CustomEntityType.unregisterEntities();
        rpgCraftCosta.getLogger().info("Saving players...");
        this.rpgPlayerManager.saveRpgPlayers();
        rpgCraftCosta.getLogger().info("Saving guilds...");
        this.rpgGuildManager.saveGuilds();
        getLogger().info("Shutting down I'll be back!!");
        this.rpgCraftCosta = null;

    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGItemManager
     *
     * @return el objeto RPGItemManager
     */
    public RPGItemManager getRPGItemManager() {
        return this.rpgItemManager;
    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGChatManager
     *
     * @return el objeto RPGChatManager
     */
    public RPGChatManager getRPGChatManager() {
        return this.rpgChatManager;
    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGPartyManager
     *
     * @return el objeto RPGPartyManager
     */
    public RPGPartyManager getRPGPartyManager() {
        return this.rpgPartyManager;
    }//Cierre del metodo

    /**
     * Metodo que devuelve el objeto RPGMobManager
     *
     * @return el objeto RPGMobManager
     */
    public RPGMobManager getRPGMobManager() {
        return this.rpgMobManager;
    }//Cierre del metodo

    public RPGLevelManager getRPGLevelManager() {
        return this.rpgLevelManager;
    }

    public RPGTradeManager getTradeManager() {
        return this.rpgTradeManager;
    }

    public GlobalConfigManager getRpgConfig() {
        return rpgConfig;
    }

}
