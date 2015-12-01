/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs;

import com.craftcosta.jailrules.rpgcraftcosta.mobs.spawner.RPGMobSpawner;
import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Entity;

/**
 *
 * @author jail
 */
public class RPGMobManager {
    private RPGCraftCosta plugin;
    private RPGMobCommands mobC;
    private File Monsters;
    public HashMap<String, Entity> listaEntidades;
    public HashMap<Integer,RPGMobSpawner> listMS;

    public RPGMobManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.mobC = new RPGMobCommands(plugin);
        loadMS();
    }

    private void loadMS() {
        this.listaEntidades=new HashMap<>();
        //loadEntities();
        this.listMS=new HashMap<>();
        
    }

    private void loadEntities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
