/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuildManager;
import com.craftcosta.jailrules.rpgcraftcosta.party.RPGPartyManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author jail
 */
public class RPGChatListener implements Listener {

    RPGCraftCosta plugin;
    RPGPlayerManager rpgPMan;
    RPGChatManager rpgCMan;
    RPGGuildManager rpgGMan;
    RPGPartyManager rpgTMan;

    public RPGChatListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgTMan = plugin.getRPGPartyManager();
        this.rpgGMan = plugin.getRPGGuildManager();
        this.rpgCMan = plugin.getRPGChatManager();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        RPGPlayer rpgSender = rpgPMan.getRPGPlayerByName(sender.getName());
        String shortcut = event.getMessage().split(" ")[0];
        sender.sendMessage(shortcut);
        String playerClass = "";
        String guild = "";
        String message = "";
        MessageType type = rpgCMan.getMessageTypeByPrefix(shortcut);
        if (type == null) {
            type = MessageType.LOCAL;
        }
        String location = rpgCMan.getLocationColor() + "[" + event.getPlayer().getWorld().getName().substring(0, 1).toUpperCase() + "]";
        event.setCancelled(true);
        if (!rpgSender.getGuild().isEmpty()) {
            guild = rpgSender.getGuild();
        }
        String messagePrefix = "";
        if (playerClass.isEmpty()) {
            playerClass = "[Newbie]";
        } else {
            playerClass = "[" + rpgSender.getPlayerClass() + "]";
        }

        String[] messageParts = event.getMessage().split(" ");
        String message1 = "";
        for (int i = 1; i < messageParts.length; i++) {
            message1 += " " + messageParts[i];
        }
        switch (type) {
            //ChatGlobal prefijo+location+clase+ nombre :+ mensaje
            case GLOBAL:
                if (rpgCMan.isGlobalChatEnabled()) {
                    messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]"
                            + rpgCMan.getLocationColor() + location
                            + rpgCMan.getPlayerClassColor() + playerClass
                            + rpgCMan.getPlayerNameColor() + sender.getName()
                            + type.getPrefixColor() + " : " + type.getMessageColor();
                    message = messagePrefix + message1;
                    plugin.getServer().broadcastMessage(message);
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " Chat global deshabilitado");
                }
                break;
            case GUILD:
                //ChatClan prefijo+ clan +nombre :+mensaje
                if (!rpgSender.getGuild().isEmpty()) {
                    if (rpgCMan.isGuildChatEnabled()) {
                        messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]" + "[" + guild + "]"
                                + rpgCMan.getPlayerNameColor() + sender.getName() + " :";
                        message = messagePrefix + message1;
                        rpgGMan.getGuildByName(guild).sendMessageToGuild(message);
                    } else {
                        sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " Chat de clan deshabilitado");
                    }
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " No perteneces a ningun clan");
                }
                break;
            case MARKET:
                //ChatMercado prefijo+ location+ nombre :+mensaje
                if (rpgCMan.isMarketChatEnabled()) {
                    messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]"
                            + location
                            + rpgCMan.getPlayerNameColor() + sender.getName()
                            + " :" + type.getMessageColor();
                    message = messagePrefix + message1;
                    plugin.getServer().broadcastMessage(message);
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " Chat de mercado deshabilitado");
                }
                break;
            case PRIVATE:
                //ChatPrivado prefijo+ nombre :+mensaje
                if (rpgCMan.isPrivateChatEnabled()) {
                    Player receiver = Bukkit.getServer().getPlayer(event.getMessage().split(" ")[1]);
                    messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]"
                            + rpgCMan.getPlayerNameColor() + sender.getName() + " :"
                            + type.getMessageColor();
                    message1 = "";
                    for (int i = 2; i < messageParts.length; i++) {
                        message1 += " " + messageParts[i];
                    }
                    message = messagePrefix + message1;
                    if (receiver == null) {
                        sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "] " + ChatColor.RED + event.getMessage().split(" ")[1] + " no encontrado");
                    } else {
                        receiver.sendMessage(message);
                    }
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " Chat privado deshabilitado");
                }
                break;
            case PARTY:
                //ChatGrupo prefijo+ nombre: +mensaje
                if (rpgCMan.isPartyChatEnabled()) {
                    if (!rpgSender.getParty().isEmpty()) {
                        messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]"
                                + rpgCMan.getLocationColor() + location
                                + rpgCMan.getPlayerClassColor() + "[" + playerClass + "]"
                                + rpgCMan.getPlayerNameColor() + sender.getName() + " :";
                        message = messagePrefix + message1;
                        rpgTMan.sendMessageToParty(rpgSender.getParty(), message);
                    } else {
                        sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " No perteneces a ninguna party");
                    }
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " Chat de grupo deshabilitado");
                }
                break;
            case NEWS:
                //ChatNews prefijo + mensaje
                if (sender.isOp() || sender.hasPermission("Chat.news")) {

                    messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]";
                    message = messagePrefix + message1;
                    plugin.getServer().broadcastMessage(message);
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " No tienes permiso para usar este chat");
                }
                break;
            case WARNING:
                //ChatWarning prefijo + mensaje
                if (sender.isOp() || sender.hasPermission("")) {
                    messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]";
                    message = messagePrefix + message1;
                    plugin.getServer().broadcastMessage(message);
                } else {
                    sender.sendMessage(type.getPrefixColor() + "[" + type.getPrefix() + "]" + ChatColor.RED + " No tienes permiso para usar este chat");
                }
                break;
            default:
                //ChatLocal prefijo+clase+ nombre :+ mensaje
                messagePrefix = type.getPrefixColor() + "[" + type.getPrefix() + "]"
                        + rpgCMan.getPlayerClassColor() + playerClass
                        + rpgCMan.getPlayerNameColor() + sender.getName()
                        + type.getPrefixColor() + " : " + type.getMessageColor();
                message = messagePrefix + event.getMessage();
                for (Player receiver : plugin.getServer().getOnlinePlayers()) {
                    if (rpgCMan.isLocalChatDistanceEnabled()) {
                        if (rpgCMan.canDistance(sender, receiver)) {
                            receiver.sendMessage(message);
                        }
                    } else {
                        if (rpgCMan.playersInTheSameWorld(sender, receiver)) {
                            receiver.sendMessage(message);
                        }
                    }
                }
                sender.sendMessage(message);
                break;
        }

    }
}