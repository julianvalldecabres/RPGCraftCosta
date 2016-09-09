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
import com.craftcosta.jailrules.rpgcraftcosta.config.GlobalConfigManager;
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
    private GlobalConfigManager rpgGCMan;
    private File guildsFile;
    private File guildsFileConfig;
    private FileConfiguration gConfig;
    private FileConfiguration gCConfig;
    private Map<String, RPGGuild> listGuilds;
    private int minlevelcreate;
    private boolean ilimitedPlayers;
    private boolean fixedPlayers;//true-numero false-niveles
    private int numplayers;
    private boolean formulabased;//true-con formula crear niveles false-leer del fichero lso niveles
    private double a;
    private double b;
    private int maxlevel;
    private int playersxlevel;
    private int initialplayers;
    private boolean donation;
    private boolean contribution;
    private double contributionkills;

    private TreeMap<Long, RPGGuildLevel> guildLevels;
    public static Map<String, String> peticiones;
    public static Map<String, String> invitaciones;

    /**
     *
     * @param plugin
     */
    public RPGGuildManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading guilds config...");
        this.rpgCMan = plugin.getRPGChatManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.guildLevels = new TreeMap<>();
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
        
        this.minlevelcreate = gCConfig.getInt("minlevelcreate");
        this.numplayers = gCConfig.getInt("numplayers");
        this.fixedPlayers = gCConfig.getBoolean("fixedplayers");
        this.formulabased = gCConfig.getBoolean("formulabased");
        this.a = gCConfig.getInt("a");
        this.b = gCConfig.getInt("b");
        this.maxlevel = gCConfig.getInt("maxlevel");
        this.playersxlevel = gCConfig.getInt("playersxlevel");
        this.initialplayers = gCConfig.getInt("initialplayers");
        this.donation = gCConfig.getBoolean("donation");
        this.contribution = gCConfig.getBoolean("contribution");
        this.contributionkills = gCConfig.getDouble("contributionkills");
        if (this.formulabased) {
            for (int i = 1; i <= this.maxlevel; i++) {
                long money=(long) (Math.pow(a, 2) * i + b * i);
                int nplayers= (int) (initialplayers+i*playersxlevel);
                int nivel= i;
                guildLevels.put(money, new RPGGuildLevel(nivel, numplayers));
                
            }
        }else{
            ConfigurationSection s=gCConfig.getConfigurationSection("levels");
            Set<String> levels=s.getKeys(false);
            for(String level:levels){
                ConfigurationSection s2=s.getConfigurationSection(level);
                int nivel=Integer.parseInt(level);
                long money=s2.getLong("dinero");
                int nplayers= s2.getInt("numplayers");
                guildLevels.put(money, new RPGGuildLevel(nivel, nplayers));
            }
        }

    }

    private void loadGuilds() {
        gConfig = YamlConfiguration.loadConfiguration(guildsFile);
        plugin.getLogger().info("Loading guilds...");
        String name;
        String acronym;
        int level;
        int maxplayers;
        long money;
        String owner;
        List<String> members;

        Set<String> guilds = gConfig.getKeys(false);
        for (String guildname : guilds) {
            ConfigurationSection section = gConfig.getConfigurationSection(guildname);
            name = guildname;
            level = section.getInt("level");
            maxplayers = section.getInt("maxplayers");
            money = section.getLong("money");
            owner = section.getString("owner");
            members = section.getStringList("members");
            this.listGuilds.put(guildname, new RPGGuild(name, owner, level, money, maxplayers, members));
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
     *
     * @return
     */
    public int getMinlevelcreate() {
        return minlevelcreate;
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
        rpgCMan.sendGuildMessageToPlayer(rpgPMan.getRPGPlayerByName(guild.getOwner()), " El jugador " + p.getName() + " quiere unirse al clan");
    }

    private void sendMessageOwnerChangedToGuild(Player newleaderplayer, RPGGuild guild) {
        sendMessageToGuild(guild.getName(), " El miembro " + newleaderplayer.getName() + " es el nuevo owner del clan");
        saveRPGGuild(guild);
    }

    private void sendMessagePlayerLeaveGuild(Player p, RPGGuild guild) {
        sendMessageToGuild(guild.getName(), "El camarada " + p.getName() + " ha abandonado el clan");
    }

    private void saveRPGGuild(RPGGuild rpgGuild) {
        gConfig = YamlConfiguration.loadConfiguration(guildsFile);
        ConfigurationSection section = gConfig.createSection(rpgGuild.getName());
        section.set("money", rpgGuild.getMoney());
        section.set("level", rpgGuild.getLevel());
        section.set("owner", rpgGuild.getOwner());
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
            section.set("members", guild.getMembers());
            try {
                gConfig.save(guildsFile);
            } catch (IOException ex) {
                Logger.getLogger(RPGGuildManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void sendMessageToGuild(String guildname, String message) {
        for (Player p : getGuildByName(guildname).getOnlineMembers()) {
            p.sendMessage(message);
        }
    }

    public Map<String, RPGGuild> getListGuilds() {
        return listGuilds;
    }

    public void setListGuilds(Map<String, RPGGuild> listGuilds) {
        this.listGuilds = listGuilds;
    }

    public boolean isIlimitedPlayers() {
        return ilimitedPlayers;
    }

    public void setIlimitedPlayers(boolean ilimitedPlayers) {
        this.ilimitedPlayers = ilimitedPlayers;
    }

    public boolean isFixedPlayers() {
        return fixedPlayers;
    }

    public void setFixedPlayers(boolean fixedPlayers) {
        this.fixedPlayers = fixedPlayers;
    }
    
    public RPGCraftCosta getPlugin() {
        return plugin;
    }

    public void setPlugin(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    public RPGChatManager getRpgCMan() {
        return rpgCMan;
    }

    public void setRpgCMan(RPGChatManager rpgCMan) {
        this.rpgCMan = rpgCMan;
    }

    public RPGPlayerManager getRpgPMan() {
        return rpgPMan;
    }

    public void setRpgPMan(RPGPlayerManager rpgPMan) {
        this.rpgPMan = rpgPMan;
    }

    public GlobalConfigManager getRpgGCMan() {
        return rpgGCMan;
    }

    public void setRpgGCMan(GlobalConfigManager rpgGCMan) {
        this.rpgGCMan = rpgGCMan;
    }

    public File getGuildsFile() {
        return guildsFile;
    }

    public void setGuildsFile(File guildsFile) {
        this.guildsFile = guildsFile;
    }

    public File getGuildsFileConfig() {
        return guildsFileConfig;
    }

    public void setGuildsFileConfig(File guildsFileConfig) {
        this.guildsFileConfig = guildsFileConfig;
    }

    public FileConfiguration getgConfig() {
        return gConfig;
    }

    public void setgConfig(FileConfiguration gConfig) {
        this.gConfig = gConfig;
    }

    public FileConfiguration getgCConfig() {
        return gCConfig;
    }

    public void setgCConfig(FileConfiguration gCConfig) {
        this.gCConfig = gCConfig;
    }

    public int getNumplayers() {
        return numplayers;
    }

    public void setNumplayers(int numplayers) {
        this.numplayers = numplayers;
    }

    public boolean isFormulabased() {
        return formulabased;
    }

    public void setFormulabased(boolean formulabased) {
        this.formulabased = formulabased;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public int getMaxlevel() {
        return maxlevel;
    }

    public void setMaxlevel(int maxlevel) {
        this.maxlevel = maxlevel;
    }

    public double getPlayersxlevel() {
        return playersxlevel;
    }

    public void setPlayersxlevel(int playersxlevel) {
        this.playersxlevel = playersxlevel;
    }

    public int getInitialplayers() {
        return initialplayers;
    }

    public void setInitialplayers(int initialplayers) {
        this.initialplayers = initialplayers;
    }

    public boolean isDonation() {
        return donation;
    }

    public void setDonation(boolean donation) {
        this.donation = donation;
    }

    public boolean isContribution() {
        return contribution;
    }

    public void setContribution(boolean contribution) {
        this.contribution = contribution;
    }

    public double getContributionkills() {
        return contributionkills;
    }

    public void setContributionkills(double contributionkills) {
        this.contributionkills = contributionkills;
    }

    public TreeMap<Long, RPGGuildLevel> getGuildLevels() {
        return guildLevels;
    }

    public void setGuildLevels(TreeMap<Long, RPGGuildLevel> guildLevels) {
        this.guildLevels = guildLevels;
    }

    public static Map<String, String> getPeticiones() {
        return peticiones;
    }

    public static void setPeticiones(Map<String, String> peticiones) {
        RPGGuildManager.peticiones = peticiones;
    }

    public static Map<String, String> getInvitaciones() {
        return invitaciones;
    }

    public static void setInvitaciones(Map<String, String> invitaciones) {
        RPGGuildManager.invitaciones = invitaciones;
    }
    
    

    public void addMoneyToGuild(RPGGuild rpgg, long money) {
        if (checkLevelUp((long) rpgg.getMoney(), money)) {
            rpgg.setMoney((long) (rpgg.getMoney() + money));
            lvlUp(rpgg);
        } else {
            rpgg.setMoney((long) (rpgg.getMoney() + money));
        }
    }

    public boolean checkLevelUp(long money, long inc) {
        double actualmoney = money;
        long newmoney = 0;
        if (inc > 0) {
            newmoney = money + inc;
            return guildLevels.lowerEntry(money).getValue() != guildLevels.lowerEntry(newmoney).getValue();
        }
        return false;
    }

    private void lvlUp(RPGGuild rpgg) {
        rpgg.setLevel(rpgg.getLevel() + 1);
        rpgg.setMaxplayers(getGuildLevels().lowerEntry(rpgg.getMoney()).getValue().getNumplayers());
        sendMessageToGuild(rpgg.getName(), " El clan ha alcanzado el nivel "+rpgg.getLevel());
    }

    public void sendGuildMessage(String guild, String message) {
        for (Player p : getGuildByName(guild).getOnlineMembers()) {
            p.sendMessage(message);            
        }
    }
}
