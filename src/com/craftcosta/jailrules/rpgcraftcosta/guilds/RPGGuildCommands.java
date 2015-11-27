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
package com.craftcosta.jailrules.rpgcraftcosta.guilds;

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
public class RPGGuildCommands implements CommandExecutor, TabCompleter {

    RPGCraftCosta plugin;
    RPGGuildManager rpgGMan;
    RPGPlayerManager rpgPMan;
    RPGChatManager rpgCMan;
    public static Map<String, String> peticiones = RPGGuildManager.peticiones;
    public static Map<String, String> invitaciones = RPGGuildManager.invitaciones;

    public RPGGuildCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.rpgPMan = plugin.getRPGPlayerManager();
        this.rpgGMan = plugin.getRPGGuildManager();
        this.rpgCMan = plugin.getRPGChatManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = rpgCMan.getPrefixForGuild();
        final Player p = (Player) sender;
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
        if (label.equalsIgnoreCase("guilds")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list") || (args[0].equalsIgnoreCase("ls"))) {
                    //muetra una lista de clanes creados en la partida actual
                    p.sendMessage(prefix + "Los clanes disponibles son las siguientes: " + rpgGMan.getAllAvailableGuilds());
                    return true;
                } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                    p.sendMessage(ChatColor.YELLOW + "---- Ayuda: guilds ----");
                    p.sendMessage(ChatColor.GOLD + "Comandos de Clan:");
                    p.sendMessage(ChatColor.GOLD + "/guild list: " + ChatColor.WHITE + "Muestra la lista de clanes disponibles");
                    p.sendMessage(ChatColor.GOLD + "/guild help: " + ChatColor.WHITE + "Muestra esta ayuda");
                    p.sendMessage(ChatColor.GOLD + "/guild accept/decline: " + ChatColor.WHITE + "Acepta/Declina la invitacion al clan");
                    p.sendMessage(ChatColor.GOLD + "/guild disband: " + ChatColor.WHITE + "Disuelve el clan actual");
                    p.sendMessage(ChatColor.GOLD + "/guild leave: " + ChatColor.WHITE + "Abandona el clan actual");
                    p.sendMessage(ChatColor.GOLD + "/guild info [clan]: " + ChatColor.WHITE + "Muestra informacion del clan actual o el indicado");
                    p.sendMessage(ChatColor.GOLD + "/guild create <nombre> <acronimo>: " + ChatColor.WHITE + "Crea un nuevo clan");
                    p.sendMessage(ChatColor.GOLD + "/guild invite <nombre>: " + ChatColor.WHITE + "Envia una invitacion al jugador indicado");
                    p.sendMessage(ChatColor.GOLD + "/guild join <nombre>: " + ChatColor.WHITE + "Envia una peticion de union al clan indicado");
                    p.sendMessage(ChatColor.GOLD + "/guild kick <nombre>: " + ChatColor.WHITE + "Kickea al jugador indicado del clan");
                    p.sendMessage(ChatColor.GOLD + "/guild setowner <nombre>: " + ChatColor.WHITE + "Cambia el dueño del clan al jugador indicado");
                    return true;
                } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
                    //si es el lider puede disolver el grupo
                    if (rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " No perteneces a ningún clan");
                        return true;
                    } else {
                        RPGGuild guild = rpgGMan.getGuildByName(rpgP.getGuild());
                        if (guild.getOwner().equals(p.getName())) {
                            rpgGMan.removeGuild(guild.getName());
                            p.sendMessage(prefix + " Has eliminado el clan " + guild.getName());
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Debes ser el propietario del clan para realizar esta accion");
                            return true;
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    //muestra la informacion del clan al que pertenece el jugador
                    if (rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " No perteneces a ningún clan");
                        return true;
                    } else {
                        RPGGuild guild = rpgGMan.getGuildByName(rpgP.getGuild());
                        p.sendMessage(ChatColor.YELLOW + "Nombre del clan: " + ChatColor.GOLD + guild.getName());
                        p.sendMessage(ChatColor.YELLOW + "Propietario: " + ChatColor.GOLD + guild.getOwner());
                        p.sendMessage(ChatColor.YELLOW + "Moderadores: " + ChatColor.GOLD + guild.getModerators().toString());
                        p.sendMessage(ChatColor.YELLOW + "Miembros: " + ChatColor.GOLD + guild.getMembers().toString());
                        p.sendMessage(ChatColor.YELLOW + "Dinero: " + ChatColor.GOLD + guild.getMoney());
                        p.sendMessage(ChatColor.YELLOW + "Nivel: " + ChatColor.GOLD + guild.getLevel());
                        p.sendMessage(ChatColor.YELLOW + "Usuarios online: " + ChatColor.GOLD + guild.getOnlineMembers().toString());
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("quit") || args[0].equalsIgnoreCase("exit") || args[0].equalsIgnoreCase("leave")) {
                    //Para que el jugador abandone el clan actual
                    if (rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " No perteneces a ningún clan");
                        return true;
                    } else {
                        RPGGuild guild = rpgGMan.getGuildByName(rpgP.getGuild());
                        rpgGMan.leavePlayerFromGuild(p, guild.getName());
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("a")) {
                    //Para que el jugador acepte una invitacion al clan
                    if (rpgP.getGuild().equals("")) {
                        if (playerEnPeticion(p.getName())) {
                            RPGGuild guild = rpgGMan.getGuildByName(peticiones.get(p.getName()));
                            p.sendMessage(prefix + " Te has unido al clan " + guild.getName());
                            rpgGMan.sendMessageToGuild(guild.getName(), "El jugador " + p.getName() + " se ha unido al clan");
                            rpgGMan.addPlayerToGuild(p, guild);
                            rpgP.setGuild(guild.getName());
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " No tienes invitación a ningun clan");
                            return true;
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.RED + " Ya perteneces al clan " + rpgP.getGuild());
                        return true;
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("de")) {
                    //Para que el jugador decline la invitacion al clan
                    if (!rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " Ya perteneces al clan " + rpgP.getGuild());
                        return true;
                    } else {
                        if (playerEnPeticion(p.getName())) {
                            String guild = peticiones.get(p.getName());
                            p.sendMessage(prefix + " Has declinado la invitación al clan " + guild);
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " No tienes ninguna invitación a ningún clan");
                        }
                    }
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "Usa /clan help para mostrar la ayuda");
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("i")) {
                    if (rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder invitar debes pertenecer a un clan");
                    } else {
                        String guildName = rpgP.getGuild();
                        if (plugin.getServer().getPlayerExact(args[1]) == null) {
                            p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + "no ha sido encontrado");
                        } else {
                            final Player invited = plugin.getServer().getPlayerExact(args[1]);
                            RPGPlayer rpgPI = rpgPMan.getRPGPlayerByName(invited.getName());
                            if (rpgPI.getGuild().equals("")) {
                                invited.sendMessage(prefix + " Has sido invitado a unirte al clan " + guildName);
                                invited.sendMessage(prefix + " Usa /clan accept/decline para unirte o declinar la oferta");
                                if (peticiones.containsKey(invited.getName())) {
                                    peticiones.remove(invited.getName());
                                }
                                peticiones.put(invited.getName(), guildName);
                                Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
                                    public void run() {
                                        if (playerEnPeticion(invited.getName())) {
                                            peticiones.remove(invited.getName());
                                        }
                                    }
                                }, 400);
                            } else {
                                p.sendMessage(prefix + ChatColor.RED + " El jugador " + invited.getName() + " ya pertenece a otro clan");
                            }
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) {
                    if (!rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder solicitar la union a un clan no debes pertenecer a uno");
                    } else {
                        RPGGuild guild = rpgGMan.getGuildByName(args[1]);
                        if (guild == null) {
                            p.sendMessage(prefix + ChatColor.RED + " El clan " + args[1] + "no ha sido encontrado");
                            return true;
                        } else {
                            if (invitaciones.containsKey(p.getName())) {
                                invitaciones.remove(p.getName());
                            }
                            peticiones.put(p.getName(), guild.getName());
                            Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (playerEnInvitacion(p.getName())) {
                                        invitaciones.remove(p.getName());
                                    }
                                }
                            }, 400);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("k")) {
                    //el jugador si es lider o moderador de un clan puede echar a otro jugador
                    if (p.getName().equals(args[1])) {
                        p.sendMessage(prefix + ChatColor.RED + " No puedes kickearte del clan a ti mismo");
                        return true;
                    }
                    if (rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes pertencer y ser el lider o moderador de un clan");
                        return true;
                    } else {
                        RPGGuild guild = rpgGMan.getGuildByName(rpgP.getGuild());
                        if (p.getName().equals(guild.getOwner()) || guild.getMembers().contains(p.getName())) {
                            Player kickplayer = plugin.getServer().getPlayerExact(args[1]);

                            if (kickplayer == null) {
                                p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no se ha encontrado");
                                return true;
                            } else {
                                RPGPlayer rpgkplayer = rpgPMan.getRPGPlayerByName(kickplayer.getName());
                                if (guild.getMembers().contains(kickplayer.getName())) {
                                    rpgGMan.kickPlayerFromGuild(guild, kickplayer);
                                    rpgkplayer.setGuild("");
                                    p.sendMessage(prefix + " Has kickeado a " + args[1] + " del clan");
                                    return true;
                                } else {
                                    p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no pertenece al clan");
                                    return true;
                                }
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes ser el lider o moderador del clan");
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("setowner") || args[0].equalsIgnoreCase("so")) {
                    //el jugador si es lider de un clan puede hacer lider a otro jugador
                    if (p.getName().equals(args[1])) {
                        p.sendMessage(prefix + ChatColor.RED + " No puedes hacerte lider del clan a ti mismo");
                        return true;
                    }
                    if (rpgP.getGuild().equals("")) {
                        p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes pertencer y ser el lider de un clan");
                        return true;
                    } else {
                        RPGGuild guild = rpgGMan.getGuildByName(rpgP.getGuild());
                        if (p.getName().equals(guild.getOwner())) {
                            Player newleaderplayer = plugin.getServer().getPlayerExact(args[1]);
                            if (newleaderplayer == null) {
                                p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no se ha encontrado");
                                return true;
                            } else {
                                if (guild.getMembers().contains(newleaderplayer.getName())) {
                                    rpgGMan.makeOwnerPlayerFromGuild(guild, newleaderplayer);
                                    p.sendMessage(prefix + " Has nombrado lider del clan a " + args[1]);
                                    return true;
                                } else {
                                    p.sendMessage(prefix + ChatColor.RED + " El jugador " + args[1] + " no pertenece al clan");
                                    return true;
                                }
                            }
                        } else {
                            p.sendMessage(prefix + ChatColor.RED + " Para poder usar este comando debes ser el lider del clan");
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("info")) {
                    //muestra la informacion de un clan en particular
                    RPGGuild guild = rpgGMan.getGuildByName(args[1]);
                    if (guild == null) {
                        p.sendMessage(prefix + ChatColor.RED + " El clan " + args[1] + " no encontrado");
                        return true;
                    } else {
                        p.sendMessage(ChatColor.YELLOW + "Nombre del clan: " + ChatColor.GOLD + guild.getName());
                        p.sendMessage(ChatColor.YELLOW + "Propietario: " + ChatColor.GOLD + guild.getOwner());
                        p.sendMessage(ChatColor.YELLOW + "Moderadores: " + ChatColor.GOLD + guild.getModerators().toString());
                        p.sendMessage(ChatColor.YELLOW + "Miembros: " + ChatColor.GOLD + guild.getMembers().toString());
                        p.sendMessage(ChatColor.YELLOW + "Dinero: " + ChatColor.GOLD + guild.getMoney());
                        p.sendMessage(ChatColor.YELLOW + "Nivel: " + ChatColor.GOLD + guild.getLevel());
                        p.sendMessage(ChatColor.YELLOW + "Usuarios online: " + ChatColor.GOLD + guild.getOnlineMembers().toString());
                        return true;
                    }
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
                    if (rpgP.getGuild().equals("")) {
                        if (rpgGMan.getAllAvailableGuilds().contains(args[1])) {
                            p.sendMessage(prefix + ChatColor.RED + " Ya existe un clan con ese nombre");
                            return true;
                        } else {
                            if (args[2].length() == 3) {
                                //el jugador crea un clan del cual es propietario
                                rpgGMan.addNewGuild(new RPGGuild(args[1], args[2].toUpperCase(), p));
                                p.sendMessage(prefix + " El clan se ha creado correctamente");
                                rpgP.setGuild(args[1]);
                                return true;
                            } else {
                                p.sendMessage(prefix + ChatColor.RED + " Las siglas del clan deben tener 3 letras");
                                return true;
                            }
                        }
                    } else {
                        p.sendMessage(prefix + ChatColor.RED + " Ya perteneces al clan " + rpgP.getParty());
                        return true;
                    }
                } else {

                }
            } else {

            }
        }

        return false;

    }

    private boolean playerEnPeticion(String name) {
        return peticiones.containsKey(name);
    }

    private boolean playerEnInvitacion(String name) {
        return invitaciones.containsKey(name);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(p.getName());
            if (label.equalsIgnoreCase("guild")) {
                if (args.length == 0 || args.length == 1) {
                    String[] list1 = new String[]{"list", "info", "help", "join", "leave", "remove", "accept", "decline", "setowner", "create", "invite", "kick"};
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
                            || args[0].equalsIgnoreCase("remove")
                            || args[0].equalsIgnoreCase("accept")
                            || args[0].equalsIgnoreCase("decline")) {
                        return null;
                    } else if (args[0].equalsIgnoreCase("info")) {
                        list.addAll(rpgGMan.getAllAvailableGuilds());
                        Collections.sort(list);
                        return list;
                    } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("makeleader") || args[0].equalsIgnoreCase("k") || args[0].equalsIgnoreCase("mkl")) {
                        if (!rpgP.getParty().equals("")) {
                            for (Player elem : rpgGMan.getGuildByName(rpgP.getGuild()).getOnlineMembers()) {
                                list.add(elem.getName());
                            }
                            Collections.sort(list);
                            return list;
                        }
                    } else if (args[0].equalsIgnoreCase("invite")) {
                        if (!rpgP.getParty().equals("")) {
                            for (Player elem : plugin.getServer().getOnlinePlayers()) {
                                list.add(elem.getName());
                            }
                            Collections.sort(list);
                            return list;
                        }
                    } else if (args[0].equalsIgnoreCase("join")) {
                        if (rpgP.getGuild().equals("")) {
                            for (String guild : rpgGMan.getAllAvailableGuilds()) {
                                list.add(guild);
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
