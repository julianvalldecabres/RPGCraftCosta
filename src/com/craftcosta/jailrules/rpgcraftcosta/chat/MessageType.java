/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;

/**
 *
 * @author jail
 */
public enum MessageType {

    WARNING("|", "WARNING", ChatColor.RED, ChatColor.YELLOW),
    NEWS("^", "NEWS", ChatColor.RED, ChatColor.YELLOW),
    PRIVATE("&", "PRIVATE", ChatColor.RED, ChatColor.YELLOW),
    GUILD("@", "GUILD", ChatColor.RED, ChatColor.YELLOW),
    PARTY("#", "PARTY", ChatColor.RED, ChatColor.YELLOW),
    MARKET("$", "MARKET", ChatColor.RED, ChatColor.YELLOW),
    GLOBAL("!", "GLOBAL", ChatColor.RED, ChatColor.YELLOW),
    LOCAL("", "LOCAL", ChatColor.RED, ChatColor.YELLOW);

    private String shortcut;
    private String prefix;
    private ChatColor messageColor;
    private ChatColor prefixColor;

    private MessageType(String shortcut, String prefix, ChatColor messageColor, ChatColor prefixColor) {
        this.shortcut = shortcut;
        this.prefix = prefix;
        this.messageColor = messageColor;
        this.prefixColor = prefixColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ChatColor getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(ChatColor messageColor) {
        this.messageColor = messageColor;
    }

    public ChatColor getPrefixColor() {
        return prefixColor;
    }

    public void setPrefixColor(ChatColor prefixColor) {
        this.prefixColor = prefixColor;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public String toString() {
        return "MessageType{" + "shortcut=" + shortcut + ", prefix=" + prefix + ", messageColor=" + messageColor + ", prefixColor=" + prefixColor + '}';
    }

    public void setPrefixColorByName(String nameColor) {
        this.prefixColor = ChatColor.valueOf(nameColor.toUpperCase());
    }

    public void setMessageColorByName(String nameColor) {
        this.messageColor = ChatColor.valueOf(nameColor.toUpperCase());
    }

    public MessageType getMessageTypeByName(String name) {
        return valueOf(name);
    }

}
