/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import java.util.HashMap;

/**
 *
 * @author jail
 */
public class RPGGuildManager {

    /**
     *
     * @param guildname
     * @return 
     */
    private RPGCraftCosta plugin;
    private RPGChatManager rpgCMan;
    private HashMap<String, RPGGuild> listGuilds;

    /**
     *
     * @param plugin
     */
    public RPGGuildManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        //loadGuilds();
    }

    private void loadGuilds() {

    }

    /**
     *
     * @param name
     * @return
     */
    public RPGGuild getGuildByName(String name) {
        return this.listGuilds.get(name);
    }

    /** Send message to all online players connected to the server of that guild name
     *
     * @param guild name
     * @param message to send
     */
    public void sendMessageToGuild(String name, String message) {
        RPGGuild rpgG= this.getGuildByName(name);
        rpgG.sendMessageToGuild(rpgCMan.getPrefixForGuild()+message);
    }
}
