/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta;

import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.commands.RPGCommandManager;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
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
    private RPGCommandManager myExecutor;

    public RPGCraftCosta() {
        rpgCraftCosta = this;
        this.config = this.getConfig();
    }
    
    public RPGCraftCosta getPlugin(){
        return this;
    }

    public RPGPlayerManager getRPGPlayerManager() {
        return rpgPlayerManager;
    }

    public RPGClassManager getRPGClassManager() {
        return rpgClassManager;
    }

    public RPGGuildManager getRPGGuildManager() {
        return rpgGuildManager;
    }

    public boolean mkdirs() {
        return true;
    }

    /**
     */
    @Override
    public void onEnable() {
        getLogger().info("Habilitando RPGCC");
        getServer().getPluginManager().registerEvents(new RPGPlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGCreatureListener(), this);
        this.rpgPlayerManager = new RPGPlayerManager(this);
        myExecutor= new RPGCommandManager(getPlugin());
        getCommand("login").setExecutor(myExecutor);
        getCommand("register").setExecutor(myExecutor);
        //this.rpgGuildManager = new RPGGuildManager(this);
        //this.rpgClassManager = new RPGClassManager(this);
        //ScoreBoardManager sbManager= new ScoreBoardManager();
    }

    @Override
    public void onDisable() {
        this.rpgPlayerManager.saveRpgPlayers();
        getLogger().info("Shutting down I'll be back!!");
    }

}
