/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

/**
 *
 * @author jail
 */
public class RPGParty {

    private String name;
    private String leader;
    private List<String> players;
    private boolean shareExp;
    private boolean shareMoney;

    /**
     *
     * @param name
     * @param creator
     * @param shareExp
     * @param shareMoney
     */
    public RPGParty(String name, String creator, boolean shareExp, boolean shareMoney) {
        this.players = new ArrayList<>();
        this.name = name;
        this.leader = creator;
        this.shareExp = shareExp;
        this.shareMoney = shareMoney;
        this.players.add(creator);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getCreator() {
        return leader;
    }

    /**
     *
     * @param creator
     */
    public void setCreator(String creator) {
        this.leader = creator;
    }

    /**
     *
     * @return
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    /**
     *
     * @return
     */
    public boolean isShareExp() {
        return shareExp;
    }

    /**
     *
     * @param shareExp
     */
    public void setShareExp(boolean shareExp) {
        this.shareExp = shareExp;
    }

    /**
     *
     * @return
     */
    public boolean isShareMoney() {
        return shareMoney;
    }

    /**
     *
     * @param shareMoney
     */
    public void setShareMoney(boolean shareMoney) {
        this.shareMoney = shareMoney;
    }

    /**
     *
     * @param player
     */
    public void invitePlayerToParty(String player) {
        this.players.add(player);
    }

    /**
     *
     * @param player
     */
    public void leavePlayerFromParty(String player) {
        this.players.remove(player);
    }

    /**
     *
     * @param sender
     * @param player
     */
    public void makePlayerLeader(String sender, String player) {
        String message = ChatColor.AQUA + "El lider de la party " + name + " ahora es: " + player;
        String message2 = ChatColor.AQUA + "Eres el nuevo lider de la party" + name;
        if (sender.equals(leader)) {
            if (RPGPlayerUtils.isPlayerOnline(player)) {
                setCreator(player);
                RPGPlayerUtils.sendMessageToPlayer(sender, message);
                RPGPlayerUtils.sendMessageToPlayer(sender, message2);
            } else {
                message = ChatColor.RED + "[PARTY] El jugador no esta en conectado en este momento";
                RPGPlayerUtils.sendMessageToPlayer(sender, message);
            }
        } else {
            message2 = ChatColor.RED + "[PARTY] Solo el lider puede cambiar el liderazgo";
            RPGPlayerUtils.sendMessageToPlayer(sender, message2);
        }

    }

    /**
     *
     * @param shareXP
     * @param shareMoney
     */
    public void changeAllConfig(boolean shareXP, boolean shareMoney) {
        setShareExp(shareXP);
        setShareMoney(shareMoney);
    }
}
