/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.util.Calendar;
import java.util.List;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author jail
 */
public class RPGChatTask {

    private String name;
    private MessageType type;
    private boolean enabled;
    private String description;
    private List<String> messages;
    private long interval;

    public RPGChatTask(String name, MessageType type, final boolean enabled, String description, final List<String> messages, final long interval) {
        this.name = name;
        this.type = type;
        this.enabled = enabled;
        this.description = description;
        this.messages = messages;
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public long getCooldown() {
        return interval;
    }

    public void setCooldown(int cooldown) {
        this.interval = cooldown;
    }

}
