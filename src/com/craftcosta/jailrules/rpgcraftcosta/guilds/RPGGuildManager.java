/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGGuildManager {
    private RPGCraftCosta plugin;
    private RPGChatManager rpgCMan;
    private RPGPlayerManager rpgPMan;
    private File guildsFile;
    private File guildsFileConfig;
    private FileConfiguration gConfig;
    private FileConfiguration gCConfig;
    private Map<String, RPGGuild> listGuilds;
    public static Map<String, String> peticiones;
    public static Map<String, String> invitaciones;

    private boolean enabled;
    private int maxslots;
    private int minlevelcreate;
    private int minleveljoin;
    private double a;
    private double b;
    private int maxlevel;
    private Map<Double, Integer> levels;
    private int maxcontribution;

    /**
     *
     * @param plugin
     */
    public RPGGuildManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading guilds config...");
        this.rpgCMan = plugin.getRPGChatManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.listGuilds = new HashMap<>();
        this.peticiones= new HashMap<>();
        this.invitaciones= new HashMap<>();
        guildsFile = new File(RPGFinals.guildsFilePath);
        guildsFileConfig = new File(RPGFinals.guildsConfigPath);
        if (!guildsFile.exists()) {
            plugin.getLogger().info("Loading default guilds...");
            guildsFile.getParentFile().mkdirs();
            copy(plugin.getResource("guilds.yml"), guildsFile);
        }
        if (!guildsFileConfig.exists()) {
            plugin.getLogger().info("Creating default guilds config...");
            guildsFileConfig.getParentFile().mkdirs();
            copy(plugin.getResource("guildsConfig.yml"), guildsFileConfig);
        }
        loadGuildsConfig();
        loadGuilds();
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGuildsConfig() {
        gCConfig = YamlConfiguration.loadConfiguration(guildsFileConfig);
        this.enabled = gCConfig.getBoolean("enabled");
        this.maxslots = gCConfig.getInt("maxslots");
        this.minlevelcreate = gCConfig.getInt("minlevelcreate");
        this.minleveljoin = gCConfig.getInt("minleveljoin");
        this.a = gCConfig.getDouble("a");
        this.b = gCConfig.getDouble("b");
        this.maxlevel = gCConfig.getInt("maxlevel");
        this.maxcontribution = gCConfig.getInt("maxcontribution");
        for (int i = 1; i <= this.maxlevel; i++) {
            levels.put((Math.pow(a, 2) * i + b * i), i);
        }
    }

    private void loadGuilds() {
        gConfig = YamlConfiguration.loadConfiguration(guildsFileConfig);
        plugin.getLogger().info("Loading guilds...");
        String name;
        String acronym;
        int level;
        double money;
        String owner;
        List <String> moderators;
        List <String> members;
        
        Set<String> guilds= gConfig.getKeys(false);
        for(String guildname:guilds){
            ConfigurationSection section= gConfig.getConfigurationSection(guildname);
            name=guildname;
            acronym= section.getString("acronym");
            level= section.getInt("level");
            money= section.getDouble("money");
            owner= section.getString("owner");
            moderators= section.getStringList("moderators");
            members= section.getStringList("members");
            this.listGuilds.put(guildname, new RPGGuild(name, acronym, owner,level,money, moderators, members));
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public RPGGuild getGuildByName(String name) {
        return this.listGuilds.get(name);
    }

    /**
     * Send message to all online players connected to the server of that guild
     * name
     *
     * @param guild name
     * @param message to send
     */
    public void sendMessageToGuild(String name, String message) {
        RPGGuild rpgG = this.getGuildByName(name);
        rpgG.sendMessageToGuild(rpgCMan.getPrefixForGuild() + message);
    }

    Set<String> getAllAvailableGuilds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void removeGuild(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void leavePlayerFromGuild(Player p, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addPlayerToGuild(Player p, RPGGuild guild) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void kickPlayerFromGuild(RPGGuild guild, Player kickplayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void makeOwnerPlayerFromGuild(RPGGuild guild, Player newleaderplayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addNewGuild(RPGGuild rpgGuild) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
