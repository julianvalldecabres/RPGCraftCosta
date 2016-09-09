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
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 *
 * @author jail
 */
public class RPGMobListener implements Listener {
    
    private RPGCraftCosta plugin;
    private RPGMobManager RPGMMan;
    private RPGPlayerManager rpgPMan;
    private List<RPGChunk> loadedchunks;

    /**
     *
     * @param plugin
     */
    public RPGMobListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGMMan = plugin.getRPGMobManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        loadedchunks = new ArrayList<>();
        for (World world : this.plugin.getServer().getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                loadedchunks.add(this.RPGMMan.getRPGChunkfromChunk(chunk));
            }
            for (Entity ent : world.getEntities()) {
                ent.remove();
            }
        }
        startSpawners();
        plugin.getLogger().info("Cargado Listener de Mobs");
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onCreatureDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Creature) {
            e.setDroppedExp(0);
            //e.getDrops().clear();
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onSpawnCreature(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)
                || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.JOCKEY)
                || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.MOUNT)
                || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CHUNK_GEN)
                || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)
                || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            e.getEntity().remove();
            e.setCancelled(true);
            return;
        }
        LivingEntity ent = e.getEntity();
        Location loc = ent.getLocation();
        String name = ((LivingEntity) e.getEntity()).getName();
        e.getEntity().setCustomName(name);
        e.getEntity().setCustomNameVisible(true);
        
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onCreatureExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    /**
     *
     * @param e
     */
    public void onCreatureChangeBlocks(EntityChangeBlockEvent e) {
        e.setCancelled(true);
    }

    /**
     *
     * @param e
     */
    public void onCreatureCombustEvent(EntityCombustEvent e) {
        e.setCancelled(true);
    }

    /**
     * onEntityDamage captura el evento EntityDamageEvent
     *
     * @param e evento que captura cuando un usuario recibe daño que no es una
     * entidad
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
            }
            if (e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
                e.setCancelled(true);
            }
            if (e.getCause().equals(EntityDamageEvent.DamageCause.STARVATION)) {
                e.setCancelled(true);
            }
        }
    }

    /**
     * Metodo para testear daño hecho y recibido de/por un jugador a otra
     * entidad
     *
     * @param e evento que captura la accion de dañar de un usuario
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        LivingEntity l = (LivingEntity) e.getEntity();
        Location loc = e.getEntity().getLocation();
        boolean hitrate;
        boolean evasion;
        boolean critical;
        boolean mortal;
        plugin.getLogger().info("///////////////////////////////");
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
            Packet packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 1.0F, 1.0F, 1.0F, 1.0F, 1, 1);
            hitrate = RPGPlayerUtils.checkProbability(rpgp.getFinalphysicalHitRate());
            critical = RPGPlayerUtils.checkProbability(rpgp.getFinalcritical());
            plugin.getLogger().info("critical :" + critical + " hitrate: " + hitrate + " damage: " + e.getDamage());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            if (e.getEntity() instanceof Player) {
                plugin.getLogger().info("Player vs Player");
                Player p2 = (Player) e.getEntity();
                RPGPlayer rpgp2 = rpgPMan.getRPGPlayerByName(p2.getName());
                evasion = RPGPlayerUtils.checkProbability(rpgp2.getFinalphysicalEvasion());
                plugin.getLogger().info("p2 evasion: " + evasion);
                ((CraftPlayer) p2).getHandle().playerConnection.sendPacket(packet);
                if (hitrate) {
                    if (evasion) {
                        if (critical) {
                            double damage = rpgp.getFinalphysicalAttack() * 1.5;
                            plugin.getLogger().info("p1 daño : " + damage);
                            if (damage - rpgp2.getFinalphysicalDefense() <= 0) {
                                plugin.getLogger().info("Golpe fallido");
                                e.setDamage(0.0);
                                p.sendMessage(ChatColor.GREEN + "Golpe esquivado");
                                p2.sendMessage(ChatColor.RED + "Has esquivado el golpe");
                            } else {
                                e.setDamage(rpgp2.getNormalizedDamageToPlayer(damage - rpgp2.getFinalphysicalDefense()));
                                rpgp2.setActualHealth(rpgp2.getActualHealth() - (damage - rpgp2.getFinalphysicalDefense()));
                            }
                        } else {
                            if (rpgp.getFinalphysicalAttack() - rpgp2.getFinalphysicalDefense() <= 0) {
                                e.setDamage(0.0);
                                p.sendMessage(ChatColor.GREEN + "Golpe esquivado");
                                p2.sendMessage(ChatColor.RED + "Has esquivado el golpe");
                            } else {
                                plugin.getLogger().info("p1 daño : " + rpgp.getFinalphysicalAttack());
                                e.setDamage(rpgp2.getNormalizedDamageToPlayer(rpgp.getFinalphysicalAttack() - rpgp2.getFinalphysicalDefense()));
                                rpgp2.setActualHealth(rpgp2.getActualHealth() - (rpgp.getFinalphysicalAttack() - rpgp2.getFinalphysicalDefense()));
                            }
                        }
                    } else {
                        if (critical) {
                            double damage = rpgp.getFinalphysicalAttack() * 1.5;
                            plugin.getLogger().info("p1 daño : " + damage);
                            e.setDamage(rpgp2.getNormalizedDamageToPlayer(damage));
                            rpgp2.setActualHealth(rpgp2.getActualHealth() - damage);
                            p.sendMessage("Golpe crítico");
                        } else {
                            plugin.getLogger().info("p1 daño : " + rpgp.getFinalphysicalAttack());
                            e.setDamage(rpgp2.getNormalizedDamageToPlayer(rpgp.getFinalphysicalAttack()));
                            rpgp2.setActualHealth(rpgp2.getActualHealth() - rpgp.getFinalphysicalAttack());
                        }
                    }
                } else {
                    plugin.getLogger().info("Golpe fallido");
                    e.setDamage(0.0);
                    p.sendMessage(ChatColor.RED + "Golpe fallido");
                }
            } else {//player contra mob
                plugin.getLogger().info("Player contra mob");
                if (critical) {
                    double damage = rpgp.getFinalphysicalAttack() * 1.5;
                    plugin.getLogger().info("p1 daño : " + damage);
                    p.sendMessage("Golpe crítico");
                    e.setDamage(damage);
                } else {
                    plugin.getLogger().info("p1 daño : " + rpgp.getFinalphysicalAttack());
                    e.setDamage(rpgp.getFinalphysicalAttack());
                }
            }
        } else {//mob contra player            
            if (e.getEntity() instanceof Player) {
                plugin.getLogger().info("mob contra player");
                Player p = (Player) e.getEntity();
                RPGPlayer rpgp = rpgPMan.getRPGPlayerByName(p.getName());
                evasion = RPGPlayerUtils.checkProbability(rpgp.getFinalphysicalEvasion());
                plugin.getLogger().info("mob damage : " + e.getDamage() + " p1 evasion : " + evasion + " defensa : " + rpgp.getFinalphysicalDefense());
                if (evasion) {
                    double damage = e.getDamage();
                    if (damage - rpgp.getFinalphysicalDefense() <= 0) {
                        plugin.getLogger().info("Golpe fallido");
                        p.sendMessage(ChatColor.GREEN + "Golpe esquivado");
                    } else {
                        e.setDamage(rpgp.getNormalizedDamageToPlayer(damage - rpgp.getFinalphysicalDefense()));
                        rpgp.setActualHealth(rpgp.getActualHealth() - (damage - rpgp.getFinalphysicalDefense()));
                    }
                } else {
                    e.setDamage(rpgp.getNormalizedDamageToPlayer(e.getDamage()));
                    rpgp.setActualHealth(rpgp.getActualHealth() - e.getDamage());
                }
            } else {//Mob contra mob
                plugin.getLogger().info("mob contra mob");
                e.setDamage(0.0);
            }
        }        
        plugin.getLogger().info("///////////////////////////////");
    }

    /**
     * If chunk constains spawnpoints remove entities
     *
     * @param e event
     */
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        List<Entity> entitiesinchunk = Arrays.asList(e.getChunk().getEntities());
        RPGChunk chunk = RPGMMan.getRPGChunkfromChunk(e.getChunk());
        loadedchunks.remove(chunk);
        for (Entity ent : entitiesinchunk) {
            if (RPGMMan.getEntities().containsKey(ent.getUniqueId())) {
                String spawnerName = RPGMMan.getEntities().get(ent.getUniqueId());
                RPGMMan.getEntities().remove(ent.getUniqueId());
                RPGMMan.removeUUIDfromSpawner(ent.getUniqueId(), spawnerName);
            }
        }
        for (Entity ent : e.getChunk().getEntities()) {
            RPGMMan.removeUUIDfromSpawner(ent);
        }
        for (Map.Entry<RPGChunk, Map<String, RPGSpawner>> entrySet : this.RPGMMan.getSpawnerList().entrySet()) {
            if (chunk.equals(entrySet.getKey())) {
                Map<String, RPGSpawner> spawnerslist = entrySet.getValue();
                for (Map.Entry<String, RPGSpawner> entrySet1 : spawnerslist.entrySet()) {
                    RPGSpawner value = entrySet1.getValue();
                    try {
                        RPGMMan.taskId.get(value.getId()).cancel();
                        RPGMMan.taskId.remove(value.getId());
                    } catch (IllegalStateException ex) {
                        ex.printStackTrace();
                    }
                    
                }
            }
        }
    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        RPGChunk chunk = RPGMMan.getRPGChunkfromChunk(e.getChunk());
        loadedchunks.add(chunk);
        for (Map.Entry<RPGChunk, Map<String, RPGSpawner>> entrySet : this.RPGMMan.getSpawnerList().entrySet()) {
            if (chunk.equals(entrySet.getKey())) {
                Map<String, RPGSpawner> spawnerslist = entrySet.getValue();
                for (Map.Entry<String, RPGSpawner> entrySet1 : spawnerslist.entrySet()) {
                    RPGSpawner value = entrySet1.getValue();
                    RPGMMan.taskId.put(value.getId(), new RPGSpawnerBukkitRunnable(value, plugin));
                    try {
                        RPGMMan.taskId.get(value.getId()).runTaskTimer(plugin, 0, (int) value.getCooldown());
                    } catch (IllegalStateException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    private void startSpawners() {
        for (Map.Entry<RPGChunk, Map<String, RPGSpawner>> entrySet : this.RPGMMan.getSpawnerList().entrySet()) {
            for (RPGChunk chunk : loadedchunks) {
                if (chunk.equals(entrySet.getKey())) {
                    Map<String, RPGSpawner> spawnerslist = entrySet.getValue();
                    for (Map.Entry<String, RPGSpawner> entrySet1 : spawnerslist.entrySet()) {
                        RPGSpawner value = entrySet1.getValue();
                        RPGMMan.taskId.put(value.getId(), new RPGSpawnerBukkitRunnable(value, plugin));
                        try {
                            RPGMMan.taskId.get(value.getId()).runTaskTimer(plugin, 0, (int) value.getCooldown());
                        } catch (IllegalStateException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
