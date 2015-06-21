/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.commands;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
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

    public RPGCraftCosta plugin;

    public RPGCommandManager(RPGCraftCosta aThis) {
        plugin = aThis;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Player player = (Player) sender;
        RPGPlayer rpgplayer = plugin.getRPGPlayerManager().getRPGPlayerByName(player.getName());
        if (commandLabel.equalsIgnoreCase("login")) {
            if (args.length == 1) {
                if (rpgplayer.passCheck(args[0])) {
                    
                    player.sendMessage("login correcto");
                    rpgplayer.allowMove();
                    player.sendMessage("habilitado movimiento");
                    rpgplayer.saveRPGPlayer();
                    player.sendMessage("guardado del player en fichero");
                    player.sendMessage("Login Correcto!");
                    return true;
                } else {
                    player.sendMessage("Login Incorrecto!");
                    return false;
                }
            } else {
                player.sendMessage("Numero incorrecto de argumentos!");
                return false;
            }
        }
        if (commandLabel.equalsIgnoreCase("register")) {
            if (args.length == 2) {
                if (args[0].equals(args[1])) {
                    player.sendMessage("registro correcto ambas contraseñas coinciden");
                    rpgplayer.setPassword(args[0]);
                    player.sendMessage("tu contrasenya es: "+rpgplayer.getPassword());
                    rpgplayer.setFirstLogin(false);
                    player.sendMessage("firstlogin false");
                    rpgplayer.saveRPGPlayer();
                    player.sendMessage("Registro realizado correctamente!");
                    return true;
                } else {
                    player.sendMessage("las contraseñas deben coincidir!");
                    return false;
                }
            } else {
                player.sendMessage("Numero incorrecto de argumentos!");
                return false;
            }
        }
        if (commandLabel.equalsIgnoreCase("classlist")) {
            //rpgplayer.getPlayer().sendMessage("lista de clases");
            return true;
        }
        if (commandLabel.equalsIgnoreCase("chooseclass")) {
            if (args.length == 1) {
                if (plugin.getRPGClassManager().getRPGClass(args[0]) != null) {
                    //
                } else {

                }
            } else {

                return false;
            }
        }
        return false;
    }

}
