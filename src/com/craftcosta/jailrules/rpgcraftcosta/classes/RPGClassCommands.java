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
package com.craftcosta.jailrules.rpgcraftcosta.classes;

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
 *
 * @author jail
 */
public class RPGClassCommands implements CommandExecutor ,TabCompleter{

    RPGCraftCosta plugin;
    RPGPlayerManager rpgPMan;
    RPGClassManager rpgCMan;

    public RPGClassCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        rpgPMan = plugin.getRPGPlayerManager();
        rpgCMan = plugin.getRPGClassManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        RPGPlayer rpgP = rpgPMan.getRPGPlayerByName(sender.getName());
        if (label.equalsIgnoreCase("class")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("ls")) {
                    p.sendMessage(rpgCMan.getAllClassesNames().toString());
                    return true;
                } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                    p.sendMessage(ChatColor.YELLOW + "---- Ayuda: class ----");
                    p.sendMessage(ChatColor.GOLD + "Comandos de Class:");
                    p.sendMessage(ChatColor.GOLD + "/class list: " + ChatColor.WHITE + "Muestra la lista de clases disponibles");
                    p.sendMessage(ChatColor.GOLD + "/class help: " + ChatColor.WHITE + "Muestra esta ayuda");
                    p.sendMessage(ChatColor.GOLD + "/class choose <rpgclass>: " + ChatColor.WHITE + "Asigna la <rpgclass> al jugador que la invoca");
                    p.sendMessage(ChatColor.GOLD + "/class info <rpgclass>: " + ChatColor.WHITE + "Muestra la información de la clase <rpgclass>");
                    return true;
                } else if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("r")) {
                    if (rpgP.isSetResetRequested()) {
                        //RESETEAR LA CUENTA
                        p.sendMessage("El proceso de reseteado de tu cuenta ha sido finalizado con exito");
                        return true;
                    } else {
                        p.sendMessage(ChatColor.RED + "El proceso de reseteo de tu cuenta eliminará todos tus objetos y tus avances");
                        p.sendMessage(ChatColor.RED + "Repite el comando para proceder con el reseteo de tu cuenta");
                        rpgP.setSetResetRequested(true);
                        return true;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Usa /class help para mostrar la ayuda");
                    return true;
                }
            } else if (args.length == 2) {
                //info,choose
                if (args[0].equalsIgnoreCase("choose")) {
                    if (!rpgP.getPlayerClass().isEmpty()) {
                        p.sendMessage(ChatColor.RED + "No puedes cambiar de clase");
                        return true;
                    } else {
                        if (rpgCMan.getAllClassesNames().contains(args[1])) {
                            rpgPMan.setRPGClassToPlayer(p, rpgCMan.getRPGClassByName(args[1]));
                            p.sendMessage(ChatColor.RED + "Enhorabuena ahora eres " + args[1] + " del reino");
                            rpgP.setMove(true);
                            return true;
                        } else {
                            p.sendMessage(ChatColor.RED + "Las clases disponibles son las siguientes: " + rpgCMan.getAllClassesNames());
                            return true;
                        }
                    }
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (rpgCMan.getAllClassesNames().contains(args[1])) {
                        RPGClass rpgC = rpgCMan.getRPGClassByName(args[1]);
                        p.sendMessage(ChatColor.YELLOW + "---- Clase: " + args[1] + " ----");
                        p.sendMessage(ChatColor.GOLD + "Base health: " + ChatColor.WHITE + rpgC.getBaseHealth());
                        p.sendMessage(ChatColor.GOLD + "Base mana: " + ChatColor.WHITE + rpgC.getBaseMana());

                        p.sendMessage(ChatColor.GOLD + "Base physical attack: " + ChatColor.WHITE + rpgC.getBasePhysicalAttack());
                        p.sendMessage(ChatColor.GOLD + "Base physical defense: " + ChatColor.WHITE + rpgC.getBasePhysicalDefense());
                        p.sendMessage(ChatColor.GOLD + "Base physical evasion: " + ChatColor.WHITE + rpgC.getBasePhysicalEvasion());
                        p.sendMessage(ChatColor.GOLD + "Base physical hit rate: " + ChatColor.WHITE + rpgC.getBasePhysicalHitRate());
                        p.sendMessage(ChatColor.GOLD + "Base critical: " + ChatColor.WHITE + rpgC.getBaseCritical());
                        p.sendMessage(ChatColor.GOLD + "Base magical attack: " + ChatColor.WHITE + rpgC.getBaseMagicalAttack());
                        p.sendMessage(ChatColor.GOLD + "Base magical defense: " + ChatColor.WHITE + rpgC.getBaseMagicalDefense());
                        p.sendMessage(ChatColor.GOLD + "Base magical evasion: " + ChatColor.WHITE + rpgC.getBaseMagicalEvasion());
                        p.sendMessage(ChatColor.GOLD + "Base magical hit rate: " + ChatColor.WHITE + rpgC.getBaseMagicalHitRate());
                        return true;
                    } else {
                        p.sendMessage(ChatColor.RED + "Las clases disponibles son las siguientes: " + rpgCMan.getAllClassesNames());
                        return true;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "No existe la opcion " + args[0]+". Para mas ayuda use /class help");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list= new ArrayList();
        if(sender instanceof Player){
            if(label.equalsIgnoreCase("class")){
                if(args.length==0 || args.length==1){
                    String [] list1= new String[]{"list","choose","help","reset","info"};
                    for (String elem : list1) {
                        list.add(elem);
                    }
                    return list;
                }else if(args.length==2){
                     if(args[0].equalsIgnoreCase("list")
                            ||args[0].equalsIgnoreCase("help")
                            ||args[0].equalsIgnoreCase("reset")){
                    }else if(args[0].equalsIgnoreCase("choose")){
                        list.addAll(rpgCMan.getAllClassesNames());
                        return list;
                    }else if(args[0].equalsIgnoreCase("info")) {
                        list.addAll(rpgCMan.getAllClassesNames());
                        return list;
                    }
                }else{
                    return null;
                }
            }
        }
        return null;
    }
}
