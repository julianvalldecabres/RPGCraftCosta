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

    /**
     *
     * @return
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     *
     * @param item
     */
    public void setItem(ItemStack item) {
        this.item = item;
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
    public Quality getQuality() {
        return quality;
    }

    /**
     *
     * @param quality
     */
    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    /**
     *
     * @return
     */
    public List<String> getLores() {
        return lores;
    }

    /**
     *
     * @param lores
     */
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
        return Objects.equals(this.lores, other.lores);
    }

    @Override
    public String toString() {
        return "RPGQuestItem{" + "item=" + item + ", name=" + name + ", quality=" + quality + ", lores=" + lores + '}';
    }

}
