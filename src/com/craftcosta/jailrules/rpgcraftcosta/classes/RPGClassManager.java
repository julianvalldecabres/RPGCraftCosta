/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.classes;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.HashMap;

/**
 *
 * @author jail
 */
public class RPGClassManager {
    HashMap<String,RPGClass> listClasses;
    RPGCraftCosta plugin;

    public RPGClassManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }
    
    public void loadRPGClasses(){
        //TODO
    }
    
    public RPGClass getRPGClass(String name){
        return listClasses.get(name);
    }
    
}
