/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
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
    private boolean canSetPlayerLimits;
    private int playerLimit;
    private boolean canSetShareOptions;
    private String shareOptions;// none,money,experience,both
    private boolean canSetPvPEnabled;
    private boolean pvpEnabled;
    private boolean canSetSOD;
    private String ShareOptionsDistribution;
    private boolean canSetSDR;
    private String shareDistributionReason;
    private double bonusProportion;
    public static Map<String, String> peticiones;

    /**
     *
     * @param string
     * @return
     */
    public RPGParty getParty(String name) {
        return this.partylist.get(name);
    }

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
        this.canSetPlayerLimits = pConfig.getBoolean("cansetplayerlimits");
        this.playerLimit = pConfig.getInt("playerlimit");
        this.shareOptions = pConfig.getString("share");
        this.canSetShareOptions = pConfig.getBoolean("cansetshareoptions");
        this.pvpEnabled = pConfig.getBoolean("pvpenabled");
        this.canSetPvPEnabled = pConfig.getBoolean("cansetpvp");
        this.canSetSOD = pConfig.getBoolean("cansetsod");
        this.ShareOptionsDistribution = pConfig.getString("shareoption");
        this.canSetSDR = pConfig.getBoolean("cansetsdr");
        this.shareDistributionReason = pConfig.getString("proportionalreason");
        this.bonusProportion = pConfig.getDouble("bonusproportion");
    }

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

    public void sendMessageOwnerChangedToParty(Player p, RPGParty rpgParty) {
        String prefix = rpgCMan.getPrefixForParty();
        for (Player player : rpgParty.getPlayers()) {
            if (player == p) {
                p.sendMessage(prefix + " Eres el nuevo lider del grupo " + rpgParty.getName());
            } else {
                player.sendMessage(prefix + " " + p.getName() + " es el nuevo lider del grupo " + rpgParty.getName());
            }
        }
    }

    public void disbandParty(String party) {
        String prefix = rpgCMan.getPrefixForParty();
        RPGParty rpgParty = getParty(party);
        for (Player p : rpgParty.getPlayers()) {
            RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
            rpgP.setParty("");
            p.sendMessage(prefix + " El grupo " + party + " se ha disuelto");
        }
        this.partylist.remove(party);
    }

    public void sendMessagePlayerLeaveParty(Player p, RPGParty rpgParty) {
        String prefix = rpgCMan.getPrefixForParty();
        for (Player player : rpgParty.getPlayers()) {
            player.sendMessage(prefix + " " + p.getName() + " ha abandonado el grupo");
        }
    }

    public void sendMessageToParty(String party, String message) {
        String prefix = rpgCMan.getPrefixForParty();
        for (Player player : this.partylist.get(party).getPlayers()) {
            player.sendMessage(prefix + " " + message);
        }
    }

    public Set<String> getAllAvailableParties() {
        return this.partylist.keySet();
    }

    public void addNewParty(RPGParty rpgParty) {
        this.partylist.put(rpgParty.getName(), rpgParty);
    }

    public void remParty(String partyname) {
        this.partylist.remove(partyname);
    }

    public void kickPlayerFromParty(RPGParty party, Player kickplayer) {
        party.kickplayer(kickplayer);
        kickplayer.sendMessage(rpgCMan.getPrefixForParty() + ChatColor.RED + " Has sido kickeado de la party");
        sendMessageToParty(party.getName(), "El jugador " + kickplayer.getName() + " ha sido kickeado del grupo");
    }

    public void makeLeaderPlayerFromParty(RPGParty party, Player newleaderplayer) {
        party.setLeader(newleaderplayer.getName());
        sendMessageOwnerChangedToParty(newleaderplayer, party);
    }

    public void addPlayerToParty(Player p, RPGParty party) {
        party.addPlayerToParty(p);
    }

    void setPartyPvPOff(RPGParty party) {
        party.setPvpEnabled(false);
    }

    void setPartyPvPOn(RPGParty party) {
        party.setPvpEnabled(true);
    }
}
