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
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * RPGChatBukkitRunnable se encarga de inicializar mensajes automaticos al servidor
 * @author jail
 */
public class RPGChatBukkitRunnable extends BukkitRunnable {

    //Campos de la clase
    RPGChatTask rpgChatTask;

    int TaskId;

    /**
     * Constructor de la clase RPGChatBukkitRunnable
     * @param rpgChatTask es un mensaje
     */
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

    /**
     * setTaskId establece el Id de la tarea
     * @param TaskId Id nueva de la tarea
     */
    public void setTaskId(int TaskId) {
        this.TaskId = TaskId;
    }

    /**
     * getRPGChatTask devuelve la instancia de la tarea 
     * @return RPGChatTask
     */
    public RPGChatTask getRPGChatTask() {
        return this.rpgChatTask;
    }

}
