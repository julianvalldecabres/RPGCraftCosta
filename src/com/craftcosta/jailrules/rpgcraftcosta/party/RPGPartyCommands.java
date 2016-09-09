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
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPartyCommands implements CommandExecutor, TabCompleter {

    RPGCraftCosta plugin;
    RPGPartyManager rpgPaMan;
    RPGPlayerManager rpgPMan;
    RPGChatManager rpgCMan;

    /**
     *
     */
    public static Map<String, String> peticiones = RPGPartyManager.peticiones;

    /**
     *
     * @param plugin
     */
    public RPGPartyCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgPaMan = plugin.getRPGPartyManager();
        this.rpgCMan = plugin.getRPGChatManager();
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
        String prefix = rpgCMan.getPrefixForParty();
        Player p = (Player) sender;
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (label.equalsIgnoreCase("party")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list") || (args[0].equalsIgnoreCase("ls"))) {
                    //muetra una lista de grupos creados en la partida actual
                    p.sendMessage(prefix + "Los grupos disponibles son las siguientes: " + rpgPaMan.getAllAvailableParties());
                    return true;
                } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                    p.sendMessage(ChatColor.YELLOW + "---- Ayuda: party ----");
                    p.sendMessage(ChatColor.GOLD + "Comandos de Grupo:");
                    p.sendMessage(ChatColor.GOLD + "/party list: " + ChatColor.WHITE + "Muestra la lista de grupos disponibles");
                    p.sendMessage(ChatColor.GOLD + "/party help: " + ChatColor.WHITE + "Muestra esta ayuda");
                    p.sendMessage(ChatColor.GOLD + "/party accept/decline: " + ChatColor.WHITE + "Acepta/Declina la invitacion al grupo");
                    p.sendMessage(ChatColor.GOLD + "/party disband: " + ChatColor.WHITE + "Disuelve el grupo actual");
                    p.sendMessage(ChatColor.GOLD + "/party leave: " + ChatColor.WHITE + "Abandona el grupo actual");
                    p.sendMessage(ChatColor.GOLD + "/party info [grupo]: " + ChatColor.WHITE + "Muestra informacion del grupo actual o el indicado");
                    p.sendMessage(ChatColor.GOLD + "/party create <nombre>: " + ChatColor.WHITE + "Crea un nuevo grupo");
                    p.sendMessage(ChatColor.GOLD + "/party invite <nombre>: " + ChatColor.WHITE + "Envia una peticion al jugador indicado");
                    p.sendMessage(ChatColor.GOLD + "/party kick <nombre>: " + ChatColor.WHITE + "Kickea al jugador indicado del grupo");
                    p.sendMessage(ChatColor.GOLD + "/party makeleader <nombre>: " + ChatColor.WHITE + "Cambia el liderazgo al jugador indicado");
                    return true;
                } else if (args[0].equalsIgnoreCase("disband") || args[0].equalsIgnoreCase("d")) {
                    //si es el lider puede disolver el grupo
                    if (rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " No perteneces a ningún grupo");
                        return true;
                    } else {
                        RPGParty party = rpgPaMan.getParty(rpgP.getParty());
                        if (party.getLeader().equals(p.getName())) {
                            rpgPaMan.disbandParty(party.getName());
                            p.sendMessage(prefix + " Has disuelto el grupo " + party.getName());
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Debes ser el lider del grupo para realizar esta accion");
                            return true;
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    //muestra la informacion de la party a la que pertenece el player
                    if (rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " No perteneces a ningún grupo");
                        return true;
                    } else {
                        RPGParty party = rpgPaMan.getParty(rpgP.getParty());
                        p.sendMessage(ChatColor.YELLOW + "Nombre del grupo: " + ChatColor.GOLD + party.getName());
                        p.sendMessage(ChatColor.YELLOW + "Lider: " + ChatColor.GOLD + party.getLeader());
                        p.sendMessage(ChatColor.YELLOW + "Jugadores: " + ChatColor.GOLD + party.getPlayers().toString());
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("quit") || args[0].equalsIgnoreCase("exit") || args[0].equalsIgnoreCase("leave")) {
                    //Para que el jugador abandone el grupo actual
                    if (rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " No perteneces a ningún grupo");
                        return true;
                    } else {
                        RPGParty party = rpgPaMan.getParty(rpgP.getParty());
                        rpgPaMan.leavePlayerFromParty(p, party.getName());
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("a")) {
                    //Para que el jugador abandone el grupo actual
                    if (rpgP.getParty().isEmpty()) {
                        if (playerEnPeticion(p.getName())) {
                            RPGParty party = rpgPaMan.getParty(peticiones.get(p.getName()));
                            p.sendMessage(prefix + " Te has unido al grupo " + party.getName());
                            rpgPaMan.sendPartyMessage(party.getName(), "El jugador " + p.getName() + " se ha unido al grupo");
                            rpgPaMan.addPlayerToParty(p, party);
                            rpgP.setParty(party.getName());
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " No tienes invitación a ningun grupo");
                            return true;
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.RED + " Ya perteneces al grupo " + rpgP.getParty());
                        return true;
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("de")) {
                    //Para que el jugador decline la invitacion
                    if (!rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " Ya perteneces al grupo " + rpgP.getParty());
                        return true;
                    } else {
                        if (playerEnPeticion(p.getName())) {
                            String party = peticiones.get(p.getName());
                            p.sendMessage(prefix + " Has declinado la invitación al grupo " + party);
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " No tienes ninguna invitación a ningún grupo");
                        }
                    }
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "Usa /party help para mostrar la ayuda");
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
                    if (rpgP.getParty().isEmpty()) {
                        //el jugador crea un grupo del cual es lider
                        rpgPaMan.addNewParty(new RPGParty(args[1], p));
                        p.sendMessage(prefix + " El grupo se ha creado correctamente");
                        rpgP.setParty(args[1]);
                        return true;
                    } else {
                        p.sendMessage(prefix + ChatColor.RED + " Ya perteneces al grupo " + rpgP.getParty());
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("i")) {
                    if (rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder invitar debes pertenecer a un grupo");
                    } else {
                        String partyName = rpgP.getParty();
                        if (plugin.getServer().getPlayerExact(args[1]) == null) {
                            p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + "no ha sido encontrado");
                        } else {
                            final Player invited = plugin.getServer().getPlayerExact(args[1]);
                            RPGPlayer rpgPI = rpgPMan.getRPGPlayerByName(invited.getName());
                            if (rpgPI.getParty().isEmpty()) {
                                invited.sendMessage(prefix + " Has sido invitado a unirte al grupo " + partyName);
                                invited.sendMessage(prefix + " Usa /party accept para unirte");
                                if (peticiones.containsKey(invited.getName())) {
                                    peticiones.remove(invited.getName());
                                }
                                peticiones.put(invited.getName(), partyName);
                                Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        if (playerEnPeticion(invited.getName())) {
                                            peticiones.remove(invited.getName());
                                        }
                                    }
                                }, 400);
                            } else {
                                p.sendMessage(prefix + ChatColor.RED + " El jugador " + invited.getName() + " ya pertenece a otro grupo");
                            }
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("k")) {
                    //el jugador si es lider de un grupo puede echar a otro jugador
                    if (p.getName().equals(args[1])) {
                        p.sendMessage(prefix + ChatColor.RED + " No puedes kickearte del grupo a ti mismo");
                        return true;
                    }
                    if (rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes pertencer y ser el lider de un grupo");
                        return true;
                    } else {
                        RPGParty party = rpgPaMan.getParty(rpgP.getParty());
                        if (p.getName().equals(party.getLeader())) {
                            Player kickplayer = plugin.getServer().getPlayerExact(args[1]);

                            if (kickplayer == null) {
                                p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no se ha encontrado");
                                return true;
                            } else {
                                RPGPlayer rpgkplayer = rpgPMan.getRPGPlayerByName(kickplayer.getName());
                                if (party.getPlayers().contains(kickplayer)) {
                                    rpgPaMan.kickPlayerFromParty(party, kickplayer);
                                    rpgkplayer.setParty("");
                                    p.sendMessage(prefix + " Has kickeado a " + args[1] + " del grupo");
                                    return true;
                                } else {
                                    p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no pertenece al grupo");
                                    return true;
                                }
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes ser el lider del grupo");
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("makeleader") || args[0].equalsIgnoreCase("mkl")) {
                    //el jugador si es lider de un grupo puede hacer lider a otro jugador
                    if (p.getName().equals(args[1])) {
                        p.sendMessage(prefix + ChatColor.RED + " No puedes hacerte lider del grupo a ti mismo");
                        return true;
                    }
                    if (rpgP.getParty().isEmpty()) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes pertencer y ser el lider de un grupo");
                        return true;
                    } else {
                        RPGParty party = rpgPaMan.getParty(rpgP.getParty());
                        if (p.getName().equals(party.getLeader())) {
                            Player newleaderplayer = plugin.getServer().getPlayerExact(args[1]);
                            if (newleaderplayer == null) {
                                p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no se ha encontrado");
                                return true;
                            } else {
                                if (party.getPlayers().contains(newleaderplayer)) {
                                    rpgPaMan.makeLeaderPlayerFromParty(party, newleaderplayer);
                                    p.sendMessage(prefix + " Has nombrado lider del grupo a " + args[1]);
                                    return true;
                                } else {
                                    p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no pertenece al grupo");
                                    return true;
                                }
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes ser el lider del grupo");
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("info")) {
                    //muestra la informacion de un grupo en particular
                    RPGParty rpgparty = rpgPaMan.getParty(args[1]);
                    if (rpgparty == null) {
                        p.sendMessage(prefix + ChatColor.RED + " El grupo " + args[1] + " no encontrado");
                        return true;
                    } else {
                        p.sendMessage(ChatColor.YELLOW + "Nombre del grupo: " + ChatColor.GOLD + rpgparty.getName());
                        p.sendMessage(ChatColor.YELLOW + "Lider: " + ChatColor.GOLD + rpgparty.getLeader());
                        p.sendMessage(ChatColor.YELLOW + "Jugadores: " + ChatColor.GOLD + rpgparty.getPlayers().toString());
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private boolean playerEnPeticion(String name) {
        return peticiones.containsKey(name);
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
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
            if (label.equalsIgnoreCase("party")) {
                if (args.length == 0 || args.length == 1) {
                    String[] list1 = new String[]{"list", "info", "help", "pvpon", "pvpoff", "leave", "disband", "accept", "decline", "makeleader", "create", "invite", "kick"};
                    for (String elem : list1) {
                        list.add(elem);
                    }
                    Collections.sort(list);
                    return list;
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list")
                            || args[0].equalsIgnoreCase("help")
                            || args[0].equalsIgnoreCase("pvpon")
                            || args[0].equalsIgnoreCase("pvpoff")
                            || args[0].equalsIgnoreCase("leave")
                            || args[0].equalsIgnoreCase("disband")
                            || args[0].equalsIgnoreCase("accept")
                            || args[0].equalsIgnoreCase("decline")) {
                        return null;
                    } else if (args[0].equalsIgnoreCase("info")) {
                        list.addAll(rpgPaMan.getAllAvailableParties());
                        Collections.sort(list);
                        return list;
                    } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("makeleader") || args[0].equalsIgnoreCase("k") || args[0].equalsIgnoreCase("mkl")) {
                        if (!rpgP.getParty().isEmpty()) {
                            for (Player elem : rpgPaMan.getParty(rpgP.getParty()).getPlayers()) {
                                list.add(elem.getName());
                            }
                            Collections.sort(list);
                            return list;
                        }
                    } else if (args[0].equalsIgnoreCase("invite")) {
                        if (!rpgP.getParty().isEmpty()) {
                            for (Player elem : plugin.getServer().getOnlinePlayers()) {
                                list.add(elem.getName());
                            }
                            Collections.sort(list);
                            return list;
                        }
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
