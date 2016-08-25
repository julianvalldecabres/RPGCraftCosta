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
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGMobDrop {

    private ItemStack item;
    private float dropChance;

    /**
     *
     * @param item
     * @param dropChance
     */
    public RPGMobDrop(ItemStack item, float dropChance) {
        this.item = item;
        this.dropChance = dropChance;

    }

    /**
     *
     * @param item
     * @param dropChance
     * @param owner
     */
    public RPGMobDrop(ItemStack item, float dropChance, Player owner) {
        this.item = item;
        this.dropChance = dropChance;

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
    public float getDropChance() {
        return dropChance;
    }

    /**
     *
     * @param dropChance
     */
    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }
}
