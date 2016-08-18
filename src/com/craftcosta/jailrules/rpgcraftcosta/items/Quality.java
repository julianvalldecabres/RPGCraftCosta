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
package com.craftcosta.jailrules.rpgcraftcosta.items;

import org.bukkit.ChatColor;

/**
 *
 * @author jail
 */
public enum Quality {

    /**
     *
     */
    TRASH(0, ChatColor.GRAY),
    /**
     *
     */
    COMMON(1, ChatColor.WHITE),
    /**
     *
     */
    UNCOMMON(2, ChatColor.GREEN),
    /**
     *
     */
    RARE(3, ChatColor.LIGHT_PURPLE),
    /**
     *
     */
    EPIC(3, ChatColor.AQUA);
    private int num;
    private ChatColor color;

    private Quality(int num, ChatColor color) {
        this.num = num;
        this.color = color;
    }

    /**
     *
     * @return
     */
    public ChatColor getColor() {
        return this.color;
    }

    /**
     *
     * @return
     */
    public Quality getValues() {
        return this.getValues();
    }

}
