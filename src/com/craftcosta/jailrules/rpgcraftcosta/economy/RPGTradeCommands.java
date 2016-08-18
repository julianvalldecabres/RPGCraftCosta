/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.economy;

import java.util.HashMap;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGTradeCommands implements CommandExecutor, InventoryHolder, Listener {

    public static HashMap<Player, Player> peticiones = new HashMap<>();//<creador,participante>
    public static Map<Player, Player> tratos = new HashMap<>();
    public static HashMap<String, Inventory> listaInventarios = new HashMap<>();

    private RPGTradeManager tman;
    private RPGCraftCosta plugin;
    public Inventory inv1;
    public Inventory inv2;

    public RPGTradeCommands(RPGCraftCosta plugin, RPGTradeManager tM) {
        this.plugin = plugin;
        this.tman = tM;
        this.plugin.getCommand("trato").setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    public RPGTradeCommands getTradeCommands() {
        return this;
    }

    @Override
    public boolean onCommand(final CommandSender cs, Command cmnd, String string, final String[] strings) {
        
        RPGTradeInventory tinv = this.tman.getTradeInventory();
        if (cs instanceof Player) {
            Player p = (Player) cs;

            this.inv1 = Bukkit.createInventory(this, 45, ChatColor.GREEN + "Intercambio de objetos");
            this.inv2 = Bukkit.createInventory(this, 45, ChatColor.GREEN + "Intercambio de objetos");

            if (strings.length == 0) {
                cs.sendMessage(ChatColor.GREEN + "~~~~TRATOS~~~~");
                cs.sendMessage(ChatColor.GREEN + "/trato <jugador>");
                cs.sendMessage(ChatColor.GREEN + "/trato aceptar");
                cs.sendMessage(ChatColor.GREEN + "/trato cancelar");
                return true;
            }
            if (strings.length == 1) {
                //aceptar petición e inicializar trato
                if (strings[0].equalsIgnoreCase("aceptar")) {
                    //Aqui solo aceptará el participante
                    if (peticiones.containsValue(p)) {
                        for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                            Player creador = entrySet.getKey();
                            Player participante = entrySet.getValue();
                            if (participante == p) {
                                peticiones.remove(creador);
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Intercambio con " + creador.getName() + " iniciado");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Intercambio con " + participante.getName() + " iniciado");
                                tratos.put(creador, participante);
                                participante.openInventory(this.inv1);
                                tinv.rellenarInventarioplayer1(this.inv1);
                                creador.openInventory(this.inv2);
                                tinv.rellenarInventarioplayer2(this.inv2);
                                participante.updateInventory();
                                creador.updateInventory();
                                listaInventarios.put(creador.getName(), inv2);
                                listaInventarios.put(participante.getName(), inv1);
                                return true;
                            }
                        }
                    }else{
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " No tienes ninguna peticion de intercambio actualmente");
                        return true;
                    }
                }
                //eliminar petición
                if (strings[0].equalsIgnoreCase("cancelar")) {
                    if (playerEnPeticion(p)) {
                        for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                            Player key = entrySet.getKey();
                            Player value = entrySet.getValue();
                            if (key == p) {
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Has cancelado una petición anterior de intercambio con " + value.getName());
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + key.getName() + " Ha cancelado la peticion de intercambio contigo");
                                peticiones.remove(key);
                            }
                            if (value == p) {
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + value.getName() + " Ha cancelado la peticion de intercambio contigo");
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Has cancelado una petición anterior de intercambio con " + key.getName());
                                peticiones.remove(key);
                            }
                        }
                    }else{
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " No tienes ninguna peticion de intercambio actualmente");
                        return true;
                    }
                }
                //Si el argumento se corresponde con un nombre de usuario
                final Player creador = p;
                Player participante = this.plugin.getServer().getPlayer(strings[0]);
                //si no se corresponde a un nombre de Jugador cancelar accion
                if (participante == null) {
                    p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Jugador no encontrado");
                    return true;
                    //En caso de que si sea un jugador real
                } else {
                    //Si el player solicitado es el propio jugador cancelar
                    if (p.getName() == participante.getName()) {
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " No puedes realizar un trato contigo mismo");
                        return true;
                    }
                    //Si los players estan en distintos mundos cancelar
                    if (!p.getWorld().equals(participante.getWorld())) {
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " No puedes realizar un trato entre mundos distintos");
                        return true;
                    }
                    //Si el player concertado esta en mitad de un trato cancelar peticion
                    if (playerEnTrato(participante)) {
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + participante.getName() + " se encuentra en mitad de un trato, intentalo mas tarde");
                        return true;
                    }
                    if (tratos.containsKey(p)) {
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " El jugador esta en mitad de un trato");
                        return true;
                    }
                    //Si el creador ya creo o recibio una peticion anterior se cancela
                    if (playerEnPeticion(creador)) {
                        for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                            Player key = entrySet.getKey();
                            Player value = entrySet.getValue();
                            if (key == creador) {
                                //si el creador es el que realizo la peticion anterior enviamos 2 mensajes uno para el propio creador y otro para el antiguo participante
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Has cancelado una petición anterior de intercambio con " + value.getName());
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + key.getName() + " Ha cancelado la peticion de intercambio contigo");
                                peticiones.remove(key);
                            }
                            if (value == creador) {
                                //si el creador es el que realizo la peticion anterior enviamos 2 mensajes uno para el propio creador y otro para el antiguo participante
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + value.getName() + " Ha cancelado la peticion de intercambio contigo");
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Has cancelado una petición anterior de intercambio con " + key.getName());
                                peticiones.remove(key);
                            }
                        }
                    }
                    if (playerEnPeticion(participante)) {
                        for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                            Player key = entrySet.getKey();
                            Player value = entrySet.getValue();
                            if (key == participante) {
                                //si el creador es el que realizo la peticion anterior enviamos 2 mensajes uno para el propio creador y otro para el antiguo participante
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Has cancelado una petición anterior de intercambio con " + value.getName());
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + key.getName() + " Ha cancelado la peticion de intercambio contigo");
                                peticiones.remove(key);
                            }
                            if (value == participante) {
                                //si el creador es el que realizo la peticion anterior enviamos 2 mensajes uno para el propio creador y otro para el antiguo participante
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + value.getName() + " Ha cancelado la peticion de intercambio contigo");
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Has cancelado una petición anterior de intercambio con " + key.getName());
                                peticiones.remove(key);
                            }
                        }
                    }

                    //crear la nueva peticion
                    peticiones.put(creador, participante);
                    creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Peticion de trato enviada a " + participante.getName());
                    participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " Te ha enviado una peticion de intercambio");
                    participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Usa " + ChatColor.WHITE + "/trato aceptar " + ChatColor.GREEN + "para aceptar el intercambio o " + ChatColor.WHITE + "/trato cancelar " + ChatColor.GREEN + "para cancelarlo");

                    //Inicializar reloj para eliminar las peticiones desatendidas
                    //quitar peticiones y tiempoespera
                    Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
                        public void run() {
                            Player p = (Player) cs;
                            Player participante = RPGTradeCommands.this.plugin.getServer().getPlayer(strings[0]);
                            if (playerEnPeticion(creador)) {
                                for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                                    Player key = entrySet.getKey();
                                    Player value = entrySet.getValue();
                                    if (key == creador) {
                                        key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado la peticion de intercambio con " + value.getName() + "");
                                        value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado la petición de intercambio con " + key.getName() + "");
                                        peticiones.remove(key);
                                    }
                                }
                            }
                        }
                    }, 400);

                }
                return true;
            }
        }
        return true;
    }

    public Inventory getInventory() {
        return inv1;
    }

    public Inventory getInventory2() {
        return inv1;
    }

    public boolean playerEnTrato(Player p) {
        if (tratos.containsKey(p) || tratos.containsValue(p)) {
            return true;
        }
        return false;
    }

    public boolean playerEnPeticion(Player p) {
        if (peticiones.containsKey(p) || peticiones.containsValue(p)) {
            return true;
        }
        return false;
    }

}
