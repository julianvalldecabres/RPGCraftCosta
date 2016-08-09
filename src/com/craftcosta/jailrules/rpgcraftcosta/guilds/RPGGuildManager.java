/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
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

    /**
     *
     */
    public static Map<String, String> peticiones;

    /**
     *
     */
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
        this.levels = new TreeMap<>();
        this.listGuilds = new HashMap<>();
        this.peticiones = new HashMap<>();
        this.invitaciones = new HashMap<>();
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
        gConfig = YamlConfiguration.loadConfiguration(guildsFile);
        plugin.getLogger().info("Loading guilds...");
        String name;
        String acronym;
        int level;
        double money;
        String owner;
        List<String> moderators;
        List<String> members;

        Set<String> guilds = gConfig.getKeys(false);
        for (String guildname : guilds) {
            ConfigurationSection section = gConfig.getConfigurationSection(guildname);
            name = guildname;
            level = section.getInt("level");
            money = section.getDouble("money");
            owner = section.getString("owner");
            moderators = section.getStringList("moderators");
            members = section.getStringList("members");
            this.listGuilds.put(guildname, new RPGGuild(name, owner, level, money, moderators, members));
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public RPGGuild getGuildByName(String name) {
        if (name.isEmpty()) {
            return null;
        }
        return this.listGuilds.get(name);
    }

    
    

    /**
     *
     * @return
     */
    public Set<String> getAllAvailableGuilds() {
        return this.listGuilds.keySet();
    }

    /**
     *
     * @param name
     */
    public void removeGuild(String name) {
        this.listGuilds.remove(name);
        gConfig.set(name, null);
        try {
            gConfig.save(guildsFile);
        } catch (IOException ex) {
            Logger.getLogger(RPGGuildManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param p
     * @param name
     */
    public void leavePlayerFromGuild(Player p, String name) {
        RPGGuild guild = listGuilds.get(name);
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        rpgP.setGuild("");
        guild.leavePlayerFromGuild(p);
        if (guild.getMembers().isEmpty()) {
            removeGuild(name);
        } else {
            if (guild.getOwner().equals(p.getName())) {
                String newOwner = guild.getMembers().get(0);
                guild.setOwner(newOwner);
                sendMessagePlayerLeaveGuild(p, guild);
            }
        }
    }
    
    /**
     * Send message to all online players connected to the server of that guild
     * name
     *
     * @param name
     * @param message to send
     */
    public void sendMessageToGuildByName(String name,String message){
        RPGGuild guild = this.getGuildByName(name);
        for(Player p: guild.getOnlineMembers()){
            RPGPlayer rpgp= rpgPMan.getRPGPlayerByName(p.getName());
            if(rpgp.isGuildChat()){
                guild.sendMessageToGuildPlayer(p,message);
            }
        }
    }
    /**
     *
     * @return
     */
    public int getMaxslots() {
        return maxslots;
    }

    /**
     *
     * @return
     */
    public int getMinlevelcreate() {
        return minlevelcreate;
    }

    /**
     *
     * @return
     */
    public int getMinleveljoin() {
        return minleveljoin;
    }

    /**
     *
     * @return
     */
    public int getMaxlevel() {
        return maxlevel;
    }

    /**
     *
     * @return
     */
    public int getMaxcontribution() {
        return maxcontribution;
    }

    /**
     *
     * @param p
     * @param guild
     */
    public void addPlayerToGuild(Player p, RPGGuild guild) {
        guild.addMember(p);
        guild.getOnlineMembers().add(p);
        saveRPGGuild(guild);
    }

    void kickPlayerFromGuild(RPGGuild guild, Player kickplayer) {
        guild.delFromGuild(kickplayer);
    }

    /**
     *
     * @param guild
     * @param newleaderplayer
     */
    public void makeOwnerPlayerFromGuild(RPGGuild guild, Player newleaderplayer) {
        guild.setOwner(newleaderplayer.getName());
        sendMessageOwnerChangedToGuild(newleaderplayer, guild);
        saveRPGGuild(guild);
    }

    /**
     *
     * @param rpgGuild
     */
    public void addNewGuild(RPGGuild rpgGuild) {
        this.listGuilds.put(rpgGuild.getName(), rpgGuild);
        saveRPGGuild(rpgGuild);
    }

    /**
     *
     * @param p
     * @param guild
     */
    public void sendJoinRequestGuild(Player p, RPGGuild guild) {
        for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            if (guild.getOwner().equals(p1.getName()) || guild.getModerators().contains(p1.getName())) {
                p1.sendMessage(rpgCMan.getPrefixForGuild() + " El jugador " + p.getName() + " quiere unirse al clan");
            }
        }
    }

    private void sendMessageOwnerChangedToGuild(Player newleaderplayer, RPGGuild guild) {
        for (Player p : guild.getOnlineMembers()) {
            if (newleaderplayer.equals(p)) {
                p.sendMessage(rpgCMan.getPrefixForGuild() + " Eres el nuevo propietario del clan " + guild.getName());
            } else {
                p.sendMessage(rpgCMan.getPrefixForGuild() + " El miembro " + newleaderplayer.getName() + " es el nuevo owner del clan");
            }
        }

        saveRPGGuild(guild);
    }

    private void sendMessagePlayerLeaveGuild(Player p, RPGGuild guild) {
        guild.sendMessageToGuild(rpgCMan.getPrefixForGuild() + " El jugador " + p.getName() + " ha abandonado el clan");
    }

    private void saveRPGGuild(RPGGuild rpgGuild) {
        gConfig = YamlConfiguration.loadConfiguration(guildsFile);
        ConfigurationSection section = gConfig.createSection(rpgGuild.getName());
        section.set("money", rpgGuild.getMoney());
        section.set("level", rpgGuild.getLevel());
        section.set("owner", rpgGuild.getOwner());
        section.set("moderators", rpgGuild.getModerators());
        section.set("members", rpgGuild.getMembers());
        try {
            gConfig.save(guildsFile);
        } catch (IOException ex) {
            Logger.getLogger(RPGGuildManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addDonationToGuild(RPGGuild guild, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void promotePlayerFromGuild(RPGGuild guild, Player newmodplayer) {
        if (guild.getModerators().contains(newmodplayer)) {
            return;
        } else {
            guild.getModerators().add(newmodplayer.getName());
        }
        saveRPGGuild(guild);
    }

    void depromotePlayerFromGuild(RPGGuild guild, Player newmodplayer) {
        if (!guild.getModerators().contains(newmodplayer)) {
            return;
        } else {
            guild.getModerators().remove(newmodplayer.getName());
        }
        saveRPGGuild(guild);
    }

    /**
     *
     */
    public void saveGuilds() {
        for (Map.Entry<String, RPGGuild> entrySet : listGuilds.entrySet()) {
            RPGGuild guild = entrySet.getValue();
            gConfig = YamlConfiguration.loadConfiguration(guildsFile);
            ConfigurationSection section = gConfig.createSection(guild.getName());
            section.set("money", guild.getMoney());
            section.set("level", guild.getLevel());
            section.set("owner", guild.getOwner());
            section.set("moderators", guild.getModerators());
            section.set("members", guild.getMembers());
            try {
                gConfig.save(guildsFile);
            } catch (IOException ex) {
                Logger.getLogger(RPGGuildManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     *
     * @param p
     * @param guild
     */
    public void playerConnectedToGuild(Player p, RPGGuild guild) {
        guild.addOnlinePlayer(p);
        guild.sendMessageToGuild(rpgCMan.getPrefixForGuild() + " El camarada " + p.getName() + " se ha conectado");
    }

    public void sendMessageToGuild(String name, String string) {
        getGuildByName(name).sendMessageToGuild(name);
    }

}
