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

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author jail
 */
public class RPGChatBukkitRunnable extends BukkitRunnable {

    RPGChatTask rpgChatTask;

    int TaskId;

    public RPGChatBukkitRunnable(RPGChatTask rpgChatTask) {
        this.rpgChatTask = rpgChatTask;

    }

    @Override
    public void run() {
        if (rpgChatTask.isEnabled()) {
            for (String cadena : this.rpgChatTask.getMessages()) {
                Bukkit.getServer().broadcastMessage(this.rpgChatTask.getType().getPrefixColor()
                        + "[" + this.rpgChatTask.getType().getPrefix() + "] "
                        + this.rpgChatTask.getType().getMessageColor() + cadena);
            }
        }
    }

    public void setTaskId(int TaskId) {
        this.TaskId = TaskId;
    }

    public RPGChatTask getRPGChatTask() {
        return this.rpgChatTask;
    }

}
