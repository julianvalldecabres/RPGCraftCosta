/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.questitems;

import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import java.util.List;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGQuestItem {

    ItemStack item;
    String name;
    Quality quality;
    List<String> lores;

    RPGQuestItem(ItemStack item, String name, Quality quality, List<String> lores) {
        this.item = item;
        this.name = name;
        this.quality = quality;
        this.lores = lores;
        ItemMeta iMeta = item.getItemMeta();
        iMeta.setDisplayName(quality.getColor() + name);
        iMeta.setLore(this.lores);
        item.setItemMeta(iMeta);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public List<String> getLores() {
        return lores;
    }

    public void setLores(List<String> lores) {
        this.lores = lores;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RPGQuestItem other = (RPGQuestItem) obj;
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.quality != other.quality) {
            return false;
        }
        if (!Objects.equals(this.lores, other.lores)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RPGQuestItem{" + "item=" + item + ", name=" + name + ", quality=" + quality + ", lores=" + lores + '}';
    }

}
