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
package com.craftcosta.jailrules.rpgcraftcosta.config;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class GlobalConfigCommands implements CommandExecutor, TabCompleter {

    RPGCraftCosta plugin;
    GlobalConfigManager rpgGMan;

    public GlobalConfigCommands(RPGCraftCosta plugin) {
        this.plugin = plugin;
        rpgGMan = plugin.getRpgConfig();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] strings) {
        if (cs.isOp()) {
            if (label.equalsIgnoreCase("config")) {
                if (strings.length == 0) {
                    cs.sendMessage(ChatColor.YELLOW + "---- CONFIGURACION GLOBAL ----");
                    cs.sendMessage(ChatColor.YELLOW + "Spawn global: " + rpgGMan.getWorld() + " X: " + rpgGMan.getSpawnx() + " Y: " + rpgGMan.getSpawny() + " Z: " + rpgGMan.getSpawnz());
                    cs.sendMessage(ChatColor.YELLOW + "Sistema de hambre: " + rpgGMan.isHunger());
                    cs.sendMessage(ChatColor.YELLOW + "Daño por caida: " + rpgGMan.isFalldamage());
                    cs.sendMessage(ChatColor.YELLOW + "Daño por ahogamiento: " + rpgGMan.isDrowndamage());
                    cs.sendMessage(ChatColor.YELLOW + "PvP: " + rpgGMan.isEnablePvP());
                    cs.sendMessage(ChatColor.YELLOW + "Clanes: " + rpgGMan.isEnableGuilds());
                    cs.sendMessage(ChatColor.YELLOW + "Grupos: " + rpgGMan.isEnableParties());
                    cs.sendMessage(ChatColor.YELLOW + "Poner bloques: " + rpgGMan.isAllowBlockPlace());
                    cs.sendMessage(ChatColor.YELLOW + "Quitar bloques: " + rpgGMan.isAllowBlockDestroy());
                    cs.sendMessage(ChatColor.YELLOW + "Ciclo de dia: " + rpgGMan.isDaycycle());
                } else if (strings.length == 1) {
                    if (strings[0].equalsIgnoreCase("setspawnglobal")) {
                        if (cs instanceof Player) {
                            Location loc = ((Player) cs).getLocation();
                            rpgGMan.setSpawnx(loc.getX());
                            rpgGMan.setSpawny(loc.getY());
                            rpgGMan.setSpawnz(loc.getZ());
                            rpgGMan.setWorld(loc.getWorld().getName());
                            rpgGMan.saveConfig();
                            loc.getWorld().setSpawnLocation((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
                        } else {
                            plugin.getLogger().info("Comando no disponible en consola");
                        }
                    } else if (strings[0].equalsIgnoreCase("hambre")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Sistema de hambre: " + rpgGMan.isHunger());
                        } else {
                            plugin.getLogger().info("Sistema de hambre: " + rpgGMan.isHunger());
                        }
                    } else if (strings[0].equalsIgnoreCase("dañocaida")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Daño por caida: " + rpgGMan.isFalldamage());
                        } else {
                            plugin.getLogger().info("Daño por caida: " + rpgGMan.isFalldamage());
                        }
                    } else if (strings[0].equalsIgnoreCase("ahogamiento")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Daño por ahogamiento: " + rpgGMan.isDrowndamage());
                        } else {
                            plugin.getLogger().info("Daño por ahogamiento: " + rpgGMan.isDrowndamage());
                        }
                    } else if (strings[0].equalsIgnoreCase("pvp")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("PvP: " + rpgGMan.isEnablePvP());
                        } else {
                            plugin.getLogger().info("PvP: " + rpgGMan.isEnablePvP());
                        }
                    } else if (strings[0].equalsIgnoreCase("ciclodia")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Ciclo de dia: " + rpgGMan.isDaycycle());
                        } else {
                            plugin.getLogger().info("Ciclo de dia: " + rpgGMan.isDaycycle());
                        }
                    } else if (strings[0].equalsIgnoreCase("blockplace")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Colocar bloques: " + rpgGMan.isAllowBlockPlace());
                        } else {
                            plugin.getLogger().info("Colocar bloques: " + rpgGMan.isAllowBlockPlace());
                        }
                    } else if (strings[0].equalsIgnoreCase("blockdestroy")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Romper bloques: " + rpgGMan.isAllowBlockDestroy());
                        } else {
                            plugin.getLogger().info("Romper bloques: " + rpgGMan.isAllowBlockDestroy());
                        }
                    } else if (strings[0].equalsIgnoreCase("clanes")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Clanes habilitados: " + rpgGMan.isEnableGuilds());
                        } else {
                            plugin.getLogger().info("Clanes habilitados: " + rpgGMan.isEnableGuilds());
                        }
                    } else if (strings[0].equalsIgnoreCase("grupos")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Grupos habilitados: " + rpgGMan.isEnableParties());
                        } else {
                            plugin.getLogger().info("Grupos habilitados: " + rpgGMan.isEnableParties());
                        }
                    } else if (strings[0].equalsIgnoreCase("spawnglobal")) {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage("Spawn global: " + rpgGMan.getWorld() + " X: " + rpgGMan.getSpawnx() + " Y: " + rpgGMan.getSpawny() + " Z: " + rpgGMan.getSpawnz());
                        } else {
                            plugin.getLogger().info("Spawn global: " + rpgGMan.getWorld() + " X: " + rpgGMan.getSpawnx() + " Y: " + rpgGMan.getSpawny() + " Z: " + rpgGMan.getSpawnz());
                        }
                    } else {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                        } else {
                            plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                        }
                    }
                } else if (strings.length == 2) {
                    if (strings[0].equalsIgnoreCase("hambre")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setHunger(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setHunger(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }

                    } else if (strings[0].equalsIgnoreCase("dañocaida")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setFalldamage(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setFalldamage(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("ahogamiento")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setDrowndamage(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setDrowndamage(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("pvp")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setEnablePvP(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setEnablePvP(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("ciclodia")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setDaycycle(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setDaycycle(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("blockplace")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setAllowBlockPlace(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setAllowBlockPlace(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("blockdestroy")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setAllowBlockDestroy(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setAllowBlockDestroy(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("clanes")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setEnableGuilds(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setEnableGuilds(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else if (strings[0].equalsIgnoreCase("grupos")) {
                        if (strings[1].equalsIgnoreCase("on")) {
                            rpgGMan.setEnableParties(true);
                        } else if (strings[1].equalsIgnoreCase("off")) {
                            rpgGMan.setEnableParties(false);
                        } else {
                            if (cs instanceof Player) {
                                ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                            } else {
                                plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                            }
                        }
                    } else {
                        if (cs instanceof Player) {
                            ((Player) cs).sendMessage(ChatColor.RED + "Argumento no reconocido");
                        } else {
                            plugin.getLogger().info(ChatColor.RED + "Argumento no reconocido");
                        }
                    }
                    rpgGMan.saveConfig();
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmnd, String label, String[] strings) {
        List<String> lista = new ArrayList<>();
        if (label.equalsIgnoreCase("config")) {
            if (strings.length == 0 || strings.length == 1) {
                String[] list = new String[]{"hambre", "dañocaida", "ahogamiento", "clanes", "grupos", "ciclodia", "blockplace", "blockdestroy", "pvp", "spawnglobal", "setspawnglobal"};
                for (String l : list) {
                    lista.add(l);
                }
                return lista;
            } else if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("hambre")
                        || strings[0].equalsIgnoreCase("dañocaida")
                        || strings[0].equalsIgnoreCase("ahogamiento")
                        || strings[0].equalsIgnoreCase("clanes")
                        || strings[0].equalsIgnoreCase("grupos")
                        || strings[0].equalsIgnoreCase("pvp")
                        || strings[0].equalsIgnoreCase("blockplace")
                        || strings[0].equalsIgnoreCase("blockdestroy")
                        || strings[0].equalsIgnoreCase("ciclodia")) {
                    lista.add("on");
                    lista.add("off");
                    return lista;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

}
