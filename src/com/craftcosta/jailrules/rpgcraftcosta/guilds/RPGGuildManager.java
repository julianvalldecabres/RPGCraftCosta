/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.HashMap;

/**
 *
 * @author jail
 */
public class RPGGuildManager {

    public static RPGGuild getGuild(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private RPGCraftCosta plugin;
    private HashMap<String, RPGGuild> listGuilds;

    public RPGGuildManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        //loadGuilds();
    }

    private void loadGuilds() {

    }

    public RPGGuild getGuildByName(String name) {
        return this.listGuilds.get(name);
    }

    public void sendMessageToGuild(String name, String message) {

    }
}
