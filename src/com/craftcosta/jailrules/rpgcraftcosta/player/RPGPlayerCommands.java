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
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayerCommands implements CommandExecutor {

    private RPGCraftCosta plugin;
    private RPGPlayerManager rpgPMan;

    /**
     *
     * @param plugin
     */
    public RPGPlayerCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    /**
     *
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        RPGPlayer rpgP = this.rpgPMan.getRPGPlayerByName(p.getName());
        if (label.equalsIgnoreCase("player")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Use /player help para mostrar la ayuda");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("info")) {
                    p.sendMessage("class: " + rpgP.getPlayerClass());
                    p.sendMessage("guild: " + rpgP.getGuild());
                    p.sendMessage("party: " + rpgP.getParty());
                }
            }
        }
        return true;
    }

}
