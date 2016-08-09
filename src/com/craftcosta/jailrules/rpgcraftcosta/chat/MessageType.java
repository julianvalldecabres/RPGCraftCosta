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

import org.bukkit.ChatColor;

/**
 * Aqui se documentan los tipos de chat existentes
 *
 * @author jail
 */
public enum MessageType {

    /**
     * Chat Warning es de uso de administradores para informar de problemas o
     * incidencias del servidor
     */
    WARNING("|", "WARNING", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat News es de uso de administradoresanunciar para eventos
     */
    NEWS("^", "NEWS", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat Private es para conversaciones privadas entre dos usuarios
     */
    PRIVATE("&", "PRIVATE", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat Guild es para conversaciones entre miembros de un mismo clan
     */
    GUILD("@", "GUILD", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat Party es para conversaciones entre miembros de un mismo grupo
     */
    PARTY("#", "PARTY", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat Market es para conversaciones de tipo comercial
     */
    MARKET("$", "MARKET", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat Global es para mensajes de cualquier tipo de usuario a todo el
     * servidor
     */
    GLOBAL("!", "GLOBAL", ChatColor.RED, ChatColor.YELLOW),
    /**
     * Chat Local es para mantener conversaciones para cualquier tipo de
     * usuarios en el mismo mundo a poca distancia
     */
    LOCAL("", "LOCAL", ChatColor.RED, ChatColor.YELLOW);

    //campos de la clase
    private String shortcut;
    private String prefix;
    private ChatColor messageColor;
    private ChatColor prefixColor;

    //constructor del enumerado
    private MessageType(String shortcut, String prefix, ChatColor messageColor, ChatColor prefixColor) {
        this.shortcut = shortcut;
        this.prefix = prefix;
        this.messageColor = messageColor;
        this.prefixColor = prefixColor;
    }

    /**
     * Metodo que obtiene el prefijo de un tipo de Chat
     *
     * @return el prefijo del enumerado en cuestion
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Metodo que establece el prefijo de un tipo de Chat
     *
     * @param prefix es el prefijo que se establecerá
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Metodo que obtiene el ChatColor del tipo de Chat
     *
     * @return el ChatColor del tipo de Chat
     */
    public ChatColor getMessageColor() {
        return messageColor;
    }

    /**
     * Metodo que establece el ChatColor del tipo de Chat
     *
     * @param messageColor es el ChatColor que se establecera
     */
    public void setMessageColor(ChatColor messageColor) {
        this.messageColor = messageColor;
    }

    /**
     * Metodo que obtiene el color del prefijo del tipo de Chat
     *
     * @return el ChatColor del prefijo del tipo de Chat
     */
    public ChatColor getPrefixColor() {
        return prefixColor;
    }

    /**
     * Metodo que establece el ChatColor del prefijo del tipo de Chat
     *
     * @param prefixColor el ChatColor que se establecera
     */
    public void setPrefixColor(ChatColor prefixColor) {
        this.prefixColor = prefixColor;
    }

    /**
     * Metodo que obtiene el Shortcut o nombre abreviado del tipo de Chat
     *
     * @return el Shortcut del tipo de Chat
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * Metodo que establece el Short ut del tipo de Chat
     *
     * @param shortcut el Shortcut que se establecera
     */
    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public String toString() {
        return "MessageType{" + "shortcut=" + shortcut + ", prefix=" + prefix + ", messageColor=" + messageColor + ", prefixColor=" + prefixColor + '}';
    }

    /**
     * Metodo que establece el ChatColor del prefijo del tipo de Chat desde una
     * cadena de texto
     *
     * @param nameColor cadena de texto que presumiblemente es un color
     */
    public void setPrefixColorByName(String nameColor) {
        this.prefixColor = ChatColor.valueOf(nameColor.toUpperCase());
    }

    /**
     * Metodo que establece el ChatColor del tipo de Chat desde una cadena de
     * texto
     *
     * @param nameColor cadena de texto que presumiblemente es un color
     */
    public void setMessageColorByName(String nameColor) {
        this.messageColor = ChatColor.valueOf(nameColor.toUpperCase());
    }

    /**
     * Metodo que obtiene un tipo de Chat a partir de una cadena de texto
     *
     * @param name es la cadena de texto que presumiblemente es un tipo de chat
     * @return obtiene el tipo de chat
     */
    public MessageType getMessageTypeByName(String name) {
        return valueOf(name);
    }

}
