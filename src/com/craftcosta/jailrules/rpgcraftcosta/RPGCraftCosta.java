/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta;

import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.commands.RPGCommandManager;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItemManager;
import com.craftcosta.jailrules.rpgcraftcosta.listeners.RPGCreatureListener;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerListener;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jail
 */
public class RPGCraftCosta extends JavaPlugin {

    static RPGCraftCosta rpgCraftCosta;
    private FileConfiguration config;
    private RPGPlayerManager rpgPlayerManager;
    private RPGClassManager rpgClassManager;
    private RPGGuildManager rpgGuildManager;
    private RPGItemManager rpgItemManager;
    private RPGCommandManager myExecutor;

    /**
     *
     */
    public RPGCraftCosta() {
        rpgCraftCosta = this;
        this.config = this.getConfig();

    }

    /**
     *
     * @return
     */
    public RPGCraftCosta getPlugin() {
        return this;
    }

    /**
     *
     * @return
     */
    public RPGPlayerManager getRPGPlayerManager() {
        return rpgPlayerManager;
    }

    /**
     *
     * @return
     */
    public RPGClassManager getRPGClassManager() {
        return rpgClassManager;
    }

    /**
     *
     * @return
     */
    public RPGGuildManager getRPGGuildManager() {
        return rpgGuildManager;
    }

    /**
     *
     * @return
     */
    public boolean mkdirs() {
        return true;
    }

    /**
     */
    @Override
    public void onEnable() {
        getLogger().info("Checking config");
        getLogger().info("Cargando Items");
        this.rpgItemManager = new RPGItemManager(this);
        getLogger().info("Cargando players");

        this.rpgPlayerManager = new RPGPlayerManager(this);
        myExecutor = new RPGCommandManager(getPlugin());
        getCommand("login").setExecutor(myExecutor);
        getCommand("register").setExecutor(myExecutor);
        //this.rpgGuildManager = new RPGGuildManager(this);
        //this.rpgClassManager = new RPGClassManager(this);
        //ScoreBoardManager sbManager= new ScoreBoardManager();

        getLogger().info("Registrando player listeners");

        getServer().getPluginManager().registerEvents(new RPGPlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGCreatureListener(), this);

    }

    /**
     *
     */
    @Override
    public void onDisable() {
        this.rpgPlayerManager.saveRpgPlayers();
        getLogger().info("Shutting down I'll be back!!");
    }

    public RPGItemManager getRPGItemManager() {
        return this.rpgItemManager;
    }

}
