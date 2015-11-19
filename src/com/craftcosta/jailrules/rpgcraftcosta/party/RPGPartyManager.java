/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPartyManager {

    private RPGCraftCosta plugin;
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
        this.partylist = new HashMap<>();
        this.partyFileConfig = new File(RPGFinals.partyFilePath);
        if (!partyFileConfig.exists()) {
            plugin.getLogger().info("Loading parties module....");
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
        if (!partyFileConfig.exists()) {
            pConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.partyFilePath));
        } else {
            pConfig = YamlConfiguration.loadConfiguration(partyFileConfig);
        }
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
        rpgParty.leavePlayerFromParty(p.getName());
        if (rpgParty.getPlayers().size() == 0) {
            disbandParty(party);
        } else {
            if (rpgParty.getCreator().equals(p.getName())) {
                Player newOwner = plugin.getServer().getPlayer(rpgParty.getPlayers().get(0));
                rpgParty.setCreator(rpgParty.getPlayers().get(0));
                sendMessagePlayerLeaveParty(p, rpgParty);
                sendMessageOwnerChangedToParty(p, rpgParty);
            } else {
                sendMessagePlayerLeaveParty(p, rpgParty);
            }
        }
    }

    public void sendMessageOwnerChangedToParty(Player p, RPGParty rpgParty) {
        String prefix = rpgCMan.getPrefixForParty();
        for (String player : rpgParty.getPlayers()) {
            if (player == p.getName()) {
                p.sendMessage(prefix + " Eres el nuevo lider del grupo");
            } else {
                plugin.getServer().getPlayer(player).sendMessage(prefix + " " + p.getName() + " es el nuevo lider del grupo");
            }
        }
    }

    public void disbandParty(String party) {
        String prefix = rpgCMan.getPrefixForParty();
        plugin.getLogger().info(prefix + " El grupo " + party + " se ha disuelto");
        this.partylist.remove(party);
    }

    public void sendMessagePlayerLeaveParty(Player p, RPGParty rpgParty) {
        String prefix = rpgCMan.getPrefixForParty();
        for (String player : rpgParty.getPlayers()) {
            plugin.getServer().getPlayer(player).sendMessage(prefix + " " + p.getName() + " ha abandonado el grupo");
        }
    }

    public void sendMessageToParty(String party, String message) {
        for (String player : this.partylist.get(party).getPlayers()) {
            Player receiver = plugin.getServer().getPlayer(player);
            receiver.sendMessage(message);
        }
    }
}
