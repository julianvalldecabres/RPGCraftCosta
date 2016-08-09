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
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGChatManager {

    private RPGCraftCosta plugin;
    private File chatFileConfig;
    private FileConfiguration cfcConfig;
    private boolean globalChatEnabled;
    private boolean partyChatEnabled;
    private boolean guildChatEnabled;
    private boolean marketChatEnabled;
    private boolean privateChatEnabled;
    private boolean localChatDistanceEnabled;
    private double maxDistance;
    private ChatColor playerClassColor;
    private ChatColor playerNameColor;
    private ChatColor locationColor;
    private File chatTasksFile;
    private FileConfiguration ctConfig;

    private Map<String, RPGChatBukkitRunnable> Tasklist;

    /**
     *
     * @param plugin
     */
    public RPGChatManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading chat module....");
        this.Tasklist = new HashMap<>();
        this.chatFileConfig = new File(RPGFinals.chatFileConfig);
        if (!chatFileConfig.exists()) {
            plugin.getLogger().info("Loading default chat config...");
            chatFileConfig.getParentFile().mkdirs();
            copy(plugin.getResource("chatConfig.yml"), chatFileConfig);
        }
        this.chatTasksFile = new File(RPGFinals.chatTasksFilePath);
        if (!chatTasksFile.exists()) {
            plugin.getLogger().info("Loading default chat tasks...");
            chatTasksFile.getParentFile().mkdirs();
            copy(plugin.getResource("chatTasks.yml"), chatTasksFile);
        }
        loadChatConfig();
        loadChatTasks();
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

    private void loadChatConfig() {
        if (!chatFileConfig.exists()) {
            cfcConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.chatFileConfig));
        } else {
            cfcConfig = YamlConfiguration.loadConfiguration(chatFileConfig);
        }
        plugin.getLogger().info("Loading chats config...");
        this.playerClassColor = ChatColor.valueOf(cfcConfig.getString("playerclasscolor").toUpperCase());
        this.playerNameColor = ChatColor.valueOf(cfcConfig.getString("playernamecolor").toUpperCase());
        this.locationColor = ChatColor.valueOf(cfcConfig.getString("locationcolor").toUpperCase());
        this.globalChatEnabled = cfcConfig.getBoolean("globalenabled");
        this.guildChatEnabled = cfcConfig.getBoolean("guildenabled");
        this.marketChatEnabled = cfcConfig.getBoolean("marketenabled");
        this.partyChatEnabled = cfcConfig.getBoolean("partyenabled");
        this.privateChatEnabled = cfcConfig.getBoolean("privateenabled");
        this.localChatDistanceEnabled = cfcConfig.getBoolean("localchatdistanceenabled");
        this.maxDistance = cfcConfig.getDouble("maxdistance");
        Set<String> messageTypes = cfcConfig.getKeys(false);
        for (String type : messageTypes) {
            if (type.equals("WARNING")
                    || type.equals("NEWS")
                    || type.equals("GLOBAL")
                    || type.equals("MARKET")
                    || type.equals("PARTY")
                    || type.equals("LOCAL")
                    || type.equals("PRIVATE")
                    || type.equals("GUILD")) {
                ConfigurationSection section = cfcConfig.getConfigurationSection(type);
                MessageType.valueOf(type).setMessageColorByName(section.getString("messagecolor"));
                MessageType.valueOf(type).setPrefixColorByName(section.getString("prefixcolor"));
                MessageType.valueOf(type).setPrefix(section.getString("prefix"));
                MessageType.valueOf(type).setShortcut(section.getString("shortcut"));
            }
        }

    }

    /**
     *
     * @param shortcut
     * @return
     */
    public MessageType getMessageTypeByPrefix(String shortcut) {
        for (MessageType type : MessageType.values()) {
            if (type.getShortcut().equals(shortcut)) {
                return type;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public boolean isGlobalChatEnabled() {
        return globalChatEnabled;
    }

    /**
     *
     * @return
     */
    public boolean isPartyChatEnabled() {
        return partyChatEnabled;
    }

    /**
     *
     * @return
     */
    public boolean isGuildChatEnabled() {
        return guildChatEnabled;
    }

    /**
     *
     * @return
     */
    public boolean isMarketChatEnabled() {
        return marketChatEnabled;
    }

    /**
     *
     * @return
     */
    public boolean isPrivateChatEnabled() {
        return privateChatEnabled;
    }

    /**
     *
     * @return
     */
    public boolean isLocalChatDistanceEnabled() {
        return localChatDistanceEnabled;
    }

    /**
     *
     * @return
     */
    public double getMaxDistance() {
        return maxDistance;
    }

    /**
     *
     * @return
     */
    public ChatColor getPlayerClassColor() {
        return playerClassColor;
    }

    /**
     *
     * @return
     */
    public ChatColor getPlayerNameColor() {
        return playerNameColor;
    }

    /**
     *
     * @return
     */
    public ChatColor getLocationColor() {
        return locationColor;
    }

    /**
     *
     * @return
     */
    public String getPrefixForParty() {
        return MessageType.PARTY.getPrefixColor()
                + "[" + MessageType.PARTY.getPrefix()
                + "]" + MessageType.PARTY.getMessageColor();
    }

    /**
     *
     * @return
     */
    public String getPrefixForGuild() {
        return MessageType.GUILD.getPrefixColor()
                + "[" + MessageType.GUILD.getPrefix()
                + "]" + MessageType.GUILD.getMessageColor();
    }

    /**
     *
     * @param sender
     * @param receiver
     * @return
     */
    public boolean canDistance(Player sender, Player receiver) {
        if (sender.getLocation().getWorld().equals(receiver.getLocation().getWorld())) {
            if (sender.getLocation().distance(receiver.getLocation()) < maxDistance) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    boolean playersInTheSameWorld(Player sender, Player receiver) {
        return sender.getLocation().getWorld().equals(receiver.getLocation().getWorld());
    }

    private void loadChatTasks() {
        if (!chatTasksFile.exists()) {
            ctConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.chatTasksFilePath));
        } else {
            ctConfig = YamlConfiguration.loadConfiguration(chatTasksFile);
        }
        plugin.getLogger().info("Loading chat tasks...");
        String nametask = "";
        boolean enabled;
        String description = "";
        MessageType type = null;
        List<String> messages;
        long interval;
        Set<String> tasks = ctConfig.getKeys(false);

        for (String task : tasks) {
            ConfigurationSection section = ctConfig.getConfigurationSection(task);
            nametask = task;
            enabled = section.getBoolean("enabled");
            description = section.getString("description");
            type = MessageType.valueOf(section.getString("type"));
            messages = section.getStringList("message");
            interval = section.getLong("interval");
            this.Tasklist.put(task, new RPGChatBukkitRunnable(new RPGChatTask(task, type, enabled, description, messages, interval)));
            this.Tasklist.get(task).setTaskId(this.Tasklist.get(task).runTaskTimer(plugin, 0, interval).getTaskId());
        }
    }

    /**
     *
     * @return
     */
    public Map<String, RPGChatBukkitRunnable> getTasklist() {
        return Tasklist;
    }

}
