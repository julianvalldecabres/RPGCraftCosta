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
package com.craftcosta.jailrules.rpgcraftcosta.commands;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClassManager;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGCommandManager implements CommandExecutor {

    /**
     *
     */
    public RPGCraftCosta plugin;

    /**
     *
     * @param aThis
     */
    public RPGCommandManager(RPGCraftCosta aThis) {
        plugin = aThis;
    }

    /**
     *
     * @param sender
     * @param command
     * @param commandLabel
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = (Player) sender;
        RPGPlayer rpgplayer = plugin.getRPGPlayerManager().getRPGPlayerByName(player.getName());
        if (commandLabel.equalsIgnoreCase("classlist")) {
            rpgplayer.getPlayer().sendMessage(RPGClassManager.getListAvailableClasses());
            return true;
        }
        if (commandLabel.equalsIgnoreCase("chooseclass")) {
            if (args.length == 1) {
                RPGClass rpgclass = plugin.getRPGClassManager().getRPGClass(args[0]);
                if (rpgclass != null) {
                    plugin.getRPGPlayerManager().setRPGClassToPlayer(player, rpgclass);
                } else {
                    rpgplayer.getPlayer().sendMessage("That class doesn't exists!! Choose one from following:");
                    rpgplayer.getPlayer().sendMessage(RPGClassManager.getListAvailableClasses());
                }
            } else {

                return false;
            }
        }
        return false;
    }

}
