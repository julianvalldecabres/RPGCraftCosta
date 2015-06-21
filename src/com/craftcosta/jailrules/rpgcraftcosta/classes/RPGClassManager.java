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

    private RPGCraftCosta plugin;
    static HashMap<String, RPGClass> listClasses;

    public RPGClassManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    public static String getListAvailableClasses() {
        return "";
    }

    public static void loadRPGClasses() {
        //TODO
    }

    public static void saveRPGClass(RPGClass rpgclass) {

    }

    public static RPGClass getRPGClass(String name) {
        return (RPGClass) listClasses.get(name);
    }

}
