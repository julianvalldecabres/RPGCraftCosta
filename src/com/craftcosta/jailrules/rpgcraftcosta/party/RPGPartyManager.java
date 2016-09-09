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
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.economy.NegativeMoneyException;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPartyManager {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;
    private RPGChatManager rpgCMan;
    private File partyFileConfig;
    private FileConfiguration pConfig;
    private Map<String, RPGParty> partylist;
    private boolean enabled;
    private int playerLimit;
    private String shareOptions;// none,money,experience,both
    private boolean pvpEnabled;
    private String ShareOptionsDistribution;
    private String shareDistributionReason;
    private double bonusProportion;

    /**
     *
     */
    public static Map<String, String> peticiones;

    /**
     *
     * @param string
     * @return
     */
    public RPGParty getParty(String name) {
        return this.partylist.get(name);
    }

    /**
     *
     * @param plugin
     */
    public RPGPartyManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.plugin.getLogger().info("Loading party module....");
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgCMan = plugin.getRPGChatManager();
        this.peticiones = new HashMap<>();
        this.partylist = new HashMap<>();
        this.partyFileConfig = new File(RPGFinals.partyFilePath);
        if (!partyFileConfig.exists()) {
            plugin.getLogger().info("Creating default party config....");
            partyFileConfig.getParentFile().mkdirs();
            copy(plugin.getResource("partyConfig.yml"), partyFileConfig);
        }
        loadPartyConfig();
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

    private void loadPartyConfig() {

        pConfig = YamlConfiguration.loadConfiguration(partyFileConfig);

        plugin.getLogger().info("Loading party config...");
        this.enabled = pConfig.getBoolean("enabled");
        this.playerLimit = pConfig.getInt("playerlimit");
        this.shareOptions = pConfig.getString("share");
        this.pvpEnabled = pConfig.getBoolean("pvpenabled");
        this.ShareOptionsDistribution = pConfig.getString("shareoption");
        this.shareDistributionReason = pConfig.getString("proportionalreason");
        this.bonusProportion = pConfig.getDouble("bonusproportion");
    }

    /**
     *
     * @param p
     * @param party
     */
    public void leavePlayerFromParty(Player p, String party) {
        RPGParty rpgParty = partylist.get(party);
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        rpgP.setParty("");
        rpgParty.leavePlayerFromParty(p);
        if (rpgParty.getPlayers().isEmpty()) {
            disbandParty(party);
        } else {
            if (rpgParty.getLeader().equals(p.getName())) {
                Player newOwner = rpgParty.getPlayers().get(0);
                rpgParty.setLeader(newOwner.getName());
                sendMessagePlayerLeaveParty(p, rpgParty);
                sendMessageOwnerChangedToParty(p, rpgParty);
            } else {
                sendMessagePlayerLeaveParty(p, rpgParty);
            }
        }
    }

    /**
     *
     * @param p
     * @param rpgParty
     */
    public void sendMessageOwnerChangedToParty(Player p, RPGParty party) {
        sendPartyMessage(party.getName(), " El compañero " + p.getName() + " es el nuevo lider del grupo");
    }

    /**
     *
     * @param party
     */
    public void disbandParty(String party) {
        sendPartyMessage(party, "El grupo " + party + " se ha disuelto");
        RPGParty rpgParty = getParty(party);
        for (Player p : getParty(party).getPlayers()) {
            RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
            rpgP.setParty("");
        }
        this.partylist.remove(party);
    }

    /**
     *
     * @param p
     * @param rpgParty
     */
    public void sendMessagePlayerLeaveParty(Player p, RPGParty rpgParty) {
        sendPartyMessage(rpgParty.getName(), " El compañero " + p.getName() + " ha abandonado el grupo");
    }


    /**
     *
     * @return
     */
    public Set<String> getAllAvailableParties() {
        return this.partylist.keySet();
    }

    /**
     *
     * @param rpgParty
     */
    public void addNewParty(RPGParty rpgParty) {
        this.partylist.put(rpgParty.getName(), rpgParty);
    }

    /**
     *
     * @param partyname
     */
    public void remParty(String partyname) {
        this.partylist.remove(partyname);
    }

    /**
     *
     * @param party
     * @param kickplayer
     */
    public void kickPlayerFromParty(RPGParty party, Player kickplayer) {
        party.kickplayer(kickplayer);
        kickplayer.sendMessage(rpgCMan.getPrefixForParty() + ChatColor.RED + " Has sido kickeado de la party");
        sendPartyMessage(party.getName(), "El jugador " + kickplayer.getName() + " ha sido kickeado del grupo");
    }

    /**
     *
     * @param party
     * @param newleaderplayer
     */
    public void makeLeaderPlayerFromParty(RPGParty party, Player newleaderplayer) {
        party.setLeader(newleaderplayer.getName());
        sendMessageOwnerChangedToParty(newleaderplayer, party);
    }

    /**
     *
     * @param p
     * @param party
     */
    public void addPlayerToParty(Player p, RPGParty party) {
        party.addPlayerToParty(p);
    }

    public RPGCraftCosta getPlugin() {
        return plugin;
    }

    public void setPlugin(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    public RPGPlayerManager getRpgPMan() {
        return rpgPMan;
    }

    public void setRpgPMan(RPGPlayerManager rpgPMan) {
        this.rpgPMan = rpgPMan;
    }

    public RPGChatManager getRpgCMan() {
        return rpgCMan;
    }

    public void setRpgCMan(RPGChatManager rpgCMan) {
        this.rpgCMan = rpgCMan;
    }

    public File getPartyFileConfig() {
        return partyFileConfig;
    }

    public void setPartyFileConfig(File partyFileConfig) {
        this.partyFileConfig = partyFileConfig;
    }

    public FileConfiguration getpConfig() {
        return pConfig;
    }

    public void setpConfig(FileConfiguration pConfig) {
        this.pConfig = pConfig;
    }

    public Map<String, RPGParty> getPartylist() {
        return partylist;
    }

    public void setPartylist(Map<String, RPGParty> partylist) {
        this.partylist = partylist;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPlayerLimit() {
        return playerLimit;
    }

    public void setPlayerLimit(int playerLimit) {
        this.playerLimit = playerLimit;
    }

    public String getShareOptions() {
        return shareOptions;
    }

    public void setShareOptions(String shareOptions) {
        this.shareOptions = shareOptions;
    }

    public boolean isPvpEnabled() {
        return pvpEnabled;
    }

    public void setPvpEnabled(boolean pvpEnabled) {
        this.pvpEnabled = pvpEnabled;
    }

    public String getShareOptionsDistribution() {
        return ShareOptionsDistribution;
    }

    public void setShareOptionsDistribution(String ShareOptionsDistribution) {
        this.ShareOptionsDistribution = ShareOptionsDistribution;
    }

    public String getShareDistributionReason() {
        return shareDistributionReason;
    }

    public void setShareDistributionReason(String shareDistributionReason) {
        this.shareDistributionReason = shareDistributionReason;
    }

    public double getBonusProportion() {
        return bonusProportion;
    }

    public void setBonusProportion(double bonusProportion) {
        this.bonusProportion = bonusProportion;
    }

    public static Map<String, String> getPeticiones() {
        return peticiones;
    }

    public static void setPeticiones(Map<String, String> peticiones) {
        RPGPartyManager.peticiones = peticiones;
    }

    public void shareWithParty(RPGParty rpgparty, long exp, long money) {
        if (exp != 0) {
            if (money != 0) {
                shareBothWithParty(rpgparty, exp, money);
            } else {
                shareExpWithParty(rpgparty, exp);
            }
        } else {
            if (money != 0) {
                shareMoneyWithParty(rpgparty, money);
            }
        }
    }

    public void shareBothWithParty(RPGParty rpgparty, long exp, long money) {
        switch (ShareOptionsDistribution) {
            case "equal":
                int numtotal = rpgparty.getPlayers().size();
                long moneytoshare = money / numtotal;
                long exptoshare = exp / numtotal;
                for (Player p : rpgparty.getPlayers()) {
                    try {
                        RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                        if (plugin.getRPGLevelManager().checkLevelUp(rpgp.getActualExp(), exptoshare)) {
                            rpgp.setActualExp(rpgp.getActualExp() + exptoshare);
                            plugin.getRPGClassManager().levelUP(rpgp);
                        } else {
                            rpgp.setActualExp(rpgp.getActualExp() + exptoshare);
                        }
                        rpgp.getEcon().addMoney(moneytoshare);
                    } catch (NegativeMoneyException ex) {
                        Logger.getLogger(RPGPartyManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;
            case "proportional":
                switch (shareDistributionReason) {
                    case "kills":
                        int totalkills = rpgparty.getTotalKills();
                        int count = 0;
                        for (Player p : rpgparty.getPlayers()) {
                            try {
                                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                                long moneytogive = rpgparty.getKills().get(count) / totalkills * money;
                                long exptogive = rpgparty.getKills().get(count) / totalkills * exp;
                                if (plugin.getRPGLevelManager().checkLevelUp(rpgp.getActualExp(), exptogive)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                    plugin.getRPGClassManager().levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                }
                                rpgp.getEcon().addMoney(moneytogive);
                                count++;
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGPartyManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case "level":
                        int totallevel = getTotalLevel(rpgparty);
                        int count1 = 0;
                        for (Player p : rpgparty.getPlayers()) {
                            try {
                                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                                long moneytogive = rpgp.getActualLevel() / totallevel * money;
                                long exptogive = rpgp.getActualLevel() / totallevel * exp;
                                if (plugin.getRPGLevelManager().checkLevelUp(rpgp.getActualExp(), exptogive)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                    plugin.getRPGClassManager().levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                }
                                rpgp.getEcon().addMoney(moneytogive);
                                count1++;
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGPartyManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                }
                break;
        }
    }

    public void shareExpWithParty(RPGParty rpgparty, long exp) {
        switch (ShareOptionsDistribution) {
            case "equal":
                int numtotal = rpgparty.getPlayers().size();
                long exptoshare = exp / numtotal;
                for (Player p : rpgparty.getPlayers()) {
                    RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                    if (plugin.getRPGLevelManager().checkLevelUp(rpgp.getActualExp(), exptoshare)) {
                        rpgp.setActualExp(rpgp.getActualExp() + exptoshare);
                        plugin.getRPGClassManager().levelUP(rpgp);
                    } else {
                        rpgp.setActualExp(rpgp.getActualExp() + exptoshare);
                    }
                }
                break;
            case "proportional":
                switch (shareDistributionReason) {
                    case "kills":
                        int totalkills = rpgparty.getTotalKills();
                        int count = 0;
                        for (Player p : rpgparty.getPlayers()) {
                                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                                long exptogive = rpgparty.getKills().get(count) / totalkills * exp;
                                if (plugin.getRPGLevelManager().checkLevelUp(rpgp.getActualExp(), exptogive)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                    plugin.getRPGClassManager().levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                }
                                count++;
                        }
                        break;
                    case "level":
                        int totallevel = getTotalLevel(rpgparty);
                        int count1 = 0;
                        for (Player p : rpgparty.getPlayers()) {
                                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                                long exptogive = rpgp.getActualLevel() / totallevel * exp;
                                if (plugin.getRPGLevelManager().checkLevelUp(rpgp.getActualExp(), exptogive)) {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                    plugin.getRPGClassManager().levelUP(rpgp);
                                } else {
                                    rpgp.setActualExp(rpgp.getActualExp() + exptogive);
                                }
                                count1++;
                        }
                        break;
                }
                break;
        }
    }

    public void shareMoneyWithParty(RPGParty rpgparty, long money) {
        switch (ShareOptionsDistribution) {
            case "equal":
                int numtotal = rpgparty.getPlayers().size();
                long moneytoshare = money / numtotal;
                for (Player p : rpgparty.getPlayers()) {
                    try {
                        RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                        rpgp.getEcon().addMoney(moneytoshare);
                    } catch (NegativeMoneyException ex) {
                        Logger.getLogger(RPGPartyManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;
            case "proportional":
                switch (shareDistributionReason) {
                    case "kills":
                        int totalkills = rpgparty.getTotalKills();
                        int count = 0;
                        for (Player p : rpgparty.getPlayers()) {
                            try {
                                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                                long moneytogive = rpgparty.getKills().get(count) / totalkills * money;
                                rpgp.getEcon().addMoney(moneytogive);
                                count++;
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGPartyManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case "level":
                        int totallevel = getTotalLevel(rpgparty);
                        int count1 = 0;
                        for (Player p : rpgparty.getPlayers()) {
                            try {
                                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                                long moneytogive = rpgp.getActualLevel() / totallevel * money;
                                rpgp.getEcon().addMoney(moneytogive);
                                count1++;
                            } catch (NegativeMoneyException ex) {
                                Logger.getLogger(RPGPartyManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                }
                break;
        }
    }

    public Integer getTotalLevel(RPGParty rpgparty) {
        int res = 0;
        for (Player p : rpgparty.getPlayers()) {
            RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
            res += rpgp.getActualLevel();
        }
        return res;
    }

    public void sendPartyMessage(String party, String message) {
        for(Player p: getParty(party).getPlayers()){
            p.sendMessage(message);
        }
    }
}
