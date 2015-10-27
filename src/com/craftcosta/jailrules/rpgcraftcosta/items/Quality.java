/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items;

import org.bukkit.ChatColor;

/**
 *
 * @author jail
 */
public enum Quality {

    TRASH(0, ChatColor.GRAY), COMMON(1, ChatColor.WHITE), UNCOMMON(2, ChatColor.GREEN), RARE(3, ChatColor.LIGHT_PURPLE), EPIC(3, ChatColor.AQUA);
    private int num;
    private ChatColor color;

    private Quality(int num, ChatColor color) {
        this.num = num;
        this.color = color;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public Quality getValues() {
        return this.getValues();
    }

}
