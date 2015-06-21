/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.util.ArrayList;
import org.bukkit.ChatColor;

/**
 *
 * @author jail
 */
public class RPGParty {

    String name;
    String leader;
    ArrayList<String> players;
    boolean shareExp;
    boolean shareMoney;
    boolean shareAP;

    public RPGParty(String name, String creator, boolean shareExp, boolean shareMoney, boolean shareAP) {
        this.name = name;
        this.leader = creator;
        this.shareExp = shareExp;
        this.shareMoney = shareMoney;
        this.shareAP = shareAP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return leader;
    }

    public void setCreator(String creator) {
        this.leader = creator;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public boolean isShareExp() {
        return shareExp;
    }

    public void setShareExp(boolean shareExp) {
        this.shareExp = shareExp;
    }

    public boolean isShareMoney() {
        return shareMoney;
    }

    public void setShareMoney(boolean shareMoney) {
        this.shareMoney = shareMoney;
    }

    public void invitePlayerToParty(String player) {
        this.players.add(player);
    }

    public void leavePlayerFromParty(String player) {
        this.players.remove(player);
    }

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

    public void changeAllConfig(boolean shareXP, boolean shareAP, boolean shareMoney) {
        setShareExp(shareXP);
        setShareMoney(shareMoney);
        setShareAP(shareAP);
    }

    public void setShareAP(boolean shareAP) {
        this.shareAP = shareAP;
    }

}
