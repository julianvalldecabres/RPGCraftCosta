/*
 * Copyright (C) 2015 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGChatCommands implements CommandExecutor {

    RPGCraftCosta plugin;
    RPGChatManager rpgCMan;

    public RPGChatCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgCMan = plugin.getRPGChatManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (label.equals("task")) {
            if (p.isOp()) {
                if (args.length == 1) {
                    if (args[0].equals("list")) {
                        for (RPGChatBukkitRunnable runable : rpgCMan.getTasklist().values()) {
                            p.sendMessage(runable.TaskId + " " + runable.rpgChatTask.getName());
                        }
                    }
                }
                if (args.length == 2) {
                    if (args[0].equals("disable")) {
                        if (rpgCMan.getTasklist().containsKey(args[1])) {
                            rpgCMan.getTasklist().get(args[1]).getRPGChatTask().setEnabled(false);
                            return true;
                        } else {
                            p.sendMessage("Tarea no encontrada");
                            return true;
                        }
                    } else if (args[0].equals("enable")) {
                        if (rpgCMan.getTasklist().containsKey(args[1])) {
                            rpgCMan.getTasklist().get(args[1]).getRPGChatTask().setEnabled(true);
                            return true;
                        } else {
                            p.sendMessage("Tarea no encontrada");
                            return true;
                        }
                    } else {
                        p.sendMessage("El comando " + args[0] + " no existe");
                        return true;
                    }
                } else {
                    p.sendMessage("No hay mas opciones");
                    return true;
                }
            } else {
                p.sendMessage("No tienes permiso");
                return true;
            }
        }

        return true;
    }

}
