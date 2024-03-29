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
package com.craftcosta.jailrules.rpgcraftcosta.party;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.chat.RPGChatManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author jail
 */
public class RPGPartyListener implements Listener {

    RPGCraftCosta plugin;
    RPGPartyManager rpgPaMan;
    RPGPlayerManager rpgPMan;
    RPGChatManager rpgCMan;

    /**
     *
     * @param plugin
     */
    public RPGPartyListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        rpgPaMan = plugin.getRPGPartyManager();
        rpgPMan = plugin.getRPGPlayerManager();
        rpgCMan = plugin.getRPGChatManager();
    }
}
