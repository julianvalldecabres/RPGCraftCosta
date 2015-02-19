/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta;

import com.craftcosta.jailrules.rpgcraftcosta.listeners.RPGCreatureListener;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerListener;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.scoreboard.ScoreBoardManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jail
 */
public class RPGCraftCosta extends JavaPlugin {
    static RPGCraftCosta rpgCraftCosta;
    private RPGPlayerManager rpgPlayerManager;

    public RPGCraftCosta() {
        rpgCraftCosta = this;
    }

    public RPGPlayerManager getRPGPlayerManager(){
        return this.rpgPlayerManager;
    }
    /**
     */
    @Override
    public void onEnable() {
        getLogger().info("Habilitando RPGCC");
        getServer().getPluginManager().registerEvents(new RPGPlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGCreatureListener(), this);
        this.rpgPlayerManager= new RPGPlayerManager(rpgCraftCosta);
        ScoreBoardManager sbManager= new ScoreBoardManager();
    }

    @Override
    public void onDisable() {
        getLogger().info("byebye");
    }

    

}
