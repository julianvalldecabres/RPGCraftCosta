/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta;

import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatCommands;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatListener;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassCommands;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.commands.RPGCommandManager;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildCommands;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItemManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorListener;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelListener;
import com.craftcosta.jailrules.rpgcraftcosta.items.questitems.RPGQuestItemListener;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponListener;
import com.craftcosta.jailrules.rpgcraftcosta.listeners.RPGCreatureListener;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.CustomEntityType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.RPGMobCommands;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.RPGMobManager;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.RPGMobsListener;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyCommands;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyListener;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerCommands;
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
    private final FileConfiguration config;
    private RPGChatManager rpgChatManager;
    private RPGPlayerManager rpgPlayerManager;
    private RPGClassManager rpgClassManager;
    private RPGGuildManager rpgGuildManager;
    private RPGItemManager rpgItemManager;
    private RPGCommandManager myExecutor;
    private RPGClassCommands myClassCommands;
    private RPGChatCommands myChatCommands;
    private RPGPartyCommands myPartyCommands;
    private RPGPartyManager rpgPartyManager;
    private RPGGuildCommands myGuildCommands;
    private RPGPlayerCommands myPlayerCommands;
    private RPGMobManager rpgMobManager;
    private RPGMobCommands myMobCommands;

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
        CustomEntityType.registerEntities();

        this.rpgMobManager = new RPGMobManager(this);
        this.rpgChatManager = new RPGChatManager(this);
        this.rpgItemManager = new RPGItemManager(this);
        this.rpgClassManager = new RPGClassManager(this);
        this.rpgPlayerManager = new RPGPlayerManager(this);
        this.rpgPartyManager = new RPGPartyManager(this);
        this.rpgGuildManager = new RPGGuildManager(this);
        myExecutor = new RPGCommandManager(this);
        myChatCommands = new RPGChatCommands(this);
        myClassCommands = new RPGClassCommands(this);
        myPartyCommands = new RPGPartyCommands(this);
        myGuildCommands = new RPGGuildCommands(this);
        myPlayerCommands = new RPGPlayerCommands(this);

        getCommand("task").setExecutor(myChatCommands);
        getCommand("class").setExecutor(myClassCommands);
        getCommand("class").setTabCompleter(myClassCommands);
        getCommand("party").setExecutor(myPartyCommands);
        getCommand("party").setTabCompleter(myPartyCommands);
        getCommand("guild").setExecutor(myGuildCommands);
        getCommand("guild").setTabCompleter(myGuildCommands);
        getCommand("player").setExecutor(myPlayerCommands);
        getLogger().info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new RPGQuestItemListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGJewelListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGWeaponListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGArmorListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGChatListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGPlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGPartyListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGCreatureListener(this), this);
        getServer().getPluginManager().registerEvents(new RPGMobsListener(this), this);
    }

    /**
     *
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

    }

    public RPGItemManager getRPGItemManager() {
        return this.rpgItemManager;
    }

    public RPGChatManager getRPGChatManager() {
        return this.rpgChatManager;
    }

    public RPGPartyManager getRPGPartyManager() {
        return this.rpgPartyManager;
    }

}
