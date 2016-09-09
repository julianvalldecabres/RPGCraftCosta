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

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * RPGChatCommands clase que se encarga de los comandos relacionados con el chat
 *
 * @author jail
 */
public class RPGChatCommands implements CommandExecutor, TabCompleter {

    //Campos de la clase
    RPGCraftCosta plugin;
    RPGChatManager rpgCMan;
    RPGPlayerManager rpgPMan;

    /**
     * Constructor de la clase RPGChatCommands
     *
     * @param plugin clase RPGCraftCosta
     */
    public RPGChatCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgCMan = plugin.getRPGChatManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
    }

    /**
     * onCommand se encarga de los comandos lanzados al servidor por parte del
     * usuario
     *
     * @param sender es el usuario o consola que envia el mensaje
     * @param cmd comando escrito
     * @param label etiqueta del comando
     * @param args argumentos del comando
     * @return true/false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
        if (label.equals("chat")) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.YELLOW + "----" + ChatColor.RED + "CHAT HELP" + ChatColor.YELLOW + "----");
                p.sendMessage(ChatColor.YELLOW + "To activate or deactivate individual chat types use " + ChatColor.RED + "/chat <chat type> <on/off>");
                p.sendMessage(ChatColor.YELLOW + "To receive only one chat type use " + ChatColor.RED + "/chat <chat type>");
                p.sendMessage(ChatColor.YELLOW + "To restore or deactivate all chat types use " + ChatColor.RED + "/chat <all/none>");
                return true;
            }
            if (args.length > 0) {
                String caso = args[0].toLowerCase();
                switch (caso) {
                    case "party":
                        if (args.length == 1) {
                            rpgp.setPartyChat(true);
                            rpgp.setGuildChat(false);
                            rpgp.setPrivateChat(false);
                            rpgp.setLocalChat(false);
                            rpgp.setMarketChat(false);
                            rpgp.setGlobalChat(false);
                        } else if (args.length == 2) {
                            String option = args[1].toLowerCase();
                            switch (option) {
                                case "on":
                                    rpgp.setPartyChat(true);
                                    break;
                                case "off":
                                    rpgp.setPartyChat(false);
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Las opciones disponibles para activar o desactivar un chat son: " + ChatColor.YELLOW + " on (para activar) y off (para desactivar)");
                                    break;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Demasiados argumentos");
                            return true;
                        }
                        break;
                    case "private":
                        if (args.length == 1) {
                            rpgp.setPartyChat(false);
                            rpgp.setGuildChat(false);
                            rpgp.setPrivateChat(true);
                            rpgp.setLocalChat(false);
                            rpgp.setMarketChat(false);
                            rpgp.setGlobalChat(false);
                        } else if (args.length == 2) {
                            String option = args[1].toLowerCase();
                            switch (option) {
                                case "on":
                                    rpgp.setPrivateChat(true);
                                    break;
                                case "off":
                                    rpgp.setPrivateChat(false);
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Las opciones disponibles para activar o desactivar un chat son: " + ChatColor.YELLOW + " on (para activar) y off (para desactivar)");
                                    break;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Demasiados argumentos");
                            return true;
                        }
                        break;
                    case "guild":
                        if (args.length == 1) {
                            rpgp.setPartyChat(false);
                            rpgp.setGuildChat(true);
                            rpgp.setPrivateChat(false);
                            rpgp.setLocalChat(false);
                            rpgp.setMarketChat(false);
                            rpgp.setGlobalChat(false);
                        } else if (args.length == 2) {
                            String option = args[1].toLowerCase();
                            switch (option) {
                                case "on":
                                    rpgp.setGuildChat(true);
                                    break;
                                case "off":
                                    rpgp.setGuildChat(false);
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Las opciones disponibles para activar o desactivar un chat son: " + ChatColor.YELLOW + " on (para activar) y off (para desactivar)");
                                    break;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Demasiados argumentos");
                            return true;
                        }
                        break;
                    case "market":
                        if (args.length == 1) {
                            rpgp.setPartyChat(false);
                            rpgp.setGuildChat(false);
                            rpgp.setPrivateChat(false);
                            rpgp.setLocalChat(false);
                            rpgp.setMarketChat(true);
                            rpgp.setGlobalChat(false);
                        } else if (args.length == 2) {
                            String option = args[1].toLowerCase();
                            switch (option) {
                                case "on":
                                    rpgp.setMarketChat(true);
                                    break;
                                case "off":
                                    rpgp.setMarketChat(false);
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Las opciones disponibles para activar o desactivar un chat son: " + ChatColor.YELLOW + " on (para activar) y off (para desactivar)");
                                    break;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Demasiados argumentos");
                            return true;
                        }
                        break;
                    case "global":
                        if (args.length == 1) {
                            rpgp.setPartyChat(false);
                            rpgp.setGuildChat(false);
                            rpgp.setPrivateChat(false);
                            rpgp.setLocalChat(false);
                            rpgp.setMarketChat(false);
                            rpgp.setGlobalChat(true);
                        } else if (args.length == 2) {
                            String option = args[1].toLowerCase();
                            switch (option) {
                                case "on":
                                    rpgp.setGlobalChat(true);
                                    break;
                                case "off":
                                    rpgp.setGlobalChat(false);
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Las opciones disponibles para activar o desactivar un chat son: " + ChatColor.YELLOW + " on (para activar) y off (para desactivar)");
                                    break;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Demasiados argumentos");
                            return true;
                        }
                        break;
                    case "local":
                        if (args.length == 1) {
                            rpgp.setPartyChat(false);
                            rpgp.setGuildChat(false);
                            rpgp.setPrivateChat(false);
                            rpgp.setLocalChat(true);
                            rpgp.setMarketChat(false);
                            rpgp.setGlobalChat(false);
                        } else if (args.length == 2) {
                            String option = args[1].toLowerCase();
                            switch (option) {
                                case "on":
                                    rpgp.setLocalChat(true);
                                    break;
                                case "off":
                                    rpgp.setLocalChat(false);
                                    break;
                                default:
                                    p.sendMessage(ChatColor.RED + "Las opciones disponibles para activar o desactivar un chat son: " + ChatColor.YELLOW + " on (para activar) y off (para desactivar)");
                                    break;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Demasiados argumentos");
                            return true;
                        }
                        break;
                    case "none":
                        if (args.length == 1) {
                            rpgp.setPartyChat(false);
                            rpgp.setGuildChat(false);
                            rpgp.setPrivateChat(false);
                            rpgp.setLocalChat(false);
                            rpgp.setMarketChat(false);
                            rpgp.setGlobalChat(false);
                            p.sendMessage(ChatColor.RED + "Desactivados todos los chats");
                        } else {
                            p.sendMessage(ChatColor.RED + " Demasiados argumentos");
                        }
                        break;
                    case "all":
                        if (args.length == 1) {
                            rpgp.setPartyChat(true);
                            rpgp.setGuildChat(true);
                            rpgp.setPrivateChat(true);
                            rpgp.setLocalChat(true);
                            rpgp.setMarketChat(true);
                            rpgp.setGlobalChat(true);
                            p.sendMessage(ChatColor.RED + "Reactivados todos los chats");
                        } else {
                            p.sendMessage(ChatColor.RED + " Demasiados argumentos");
                            return true;
                        }
                        break;
                    default:
                        p.sendMessage(ChatColor.RED + "No existe un tipo de chat llamado " + ChatColor.YELLOW + args[0]);
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmnd, String label, String[] args) {
        List<String> lista = new ArrayList<>();
        Player p = (Player) cs;
        RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
        if (label.equalsIgnoreCase("chat")) {
            if (args.length == 0 || args.length == 1) {
                String[] list1 = new String[]{"party", "global", "private", "guild", "market", "local", "none", "all"};
                for (String elem : list1) {
                    lista.add(elem);
                }
                return lista;
            } else if (args.length == 2) {
                String tipo = args[0].toLowerCase();
                switch (tipo) {
                    case "local":
                    case "global":
                    case "market":
                    case "private":
                    case "guild":
                    case "party":
                        lista.add("on");
                        lista.add("off");
                        return lista;
                }
            } else {
                return null;
            }
        }
        return null;
    }
}
