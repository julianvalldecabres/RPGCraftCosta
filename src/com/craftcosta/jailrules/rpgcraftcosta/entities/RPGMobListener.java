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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
    private List<RPGChunk> loadedchunks;

    /**
     *
     * @param plugin
     */
    public RPGMobListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGMMan = plugin.getRPGMobManager();
        loadedchunks = new ArrayList<>();
        for (World world : this.plugin.getServer().getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                
                loadedchunks.add(this.RPGMMan.getRPGChunkfromChunk(chunk));
                
            }
            for(Entity ent: world.getEntities()){
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
            plugin.getLogger().info("QUITADA entidad con UUID " +e.getEntity().getUniqueId().toString());
            e.getEntity().remove();
            e.setCancelled(true);
            return;
        }
        LivingEntity ent = e.getEntity();
        Location loc = ent.getLocation();
        String name = ((LivingEntity) e.getEntity()).getName();
//        plugin.getLogger().info("nombre:" + name
//                + "\n reason: " + e.getSpawnReason().name()
//                + "\n loc: " + loc.toString()
//                + "\n id: " + e.getEntity().getEntityId()
//                + "\n UUID: " + e.getEntity().getUniqueId().toString());
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
//        LivingEntity l = (LivingEntity) e.getEntity();
//        Location loc = e.getEntity().getLocation();
//
//        plugin.getLogger().info("///////////////////////////////");
//        if (e.getDamager() instanceof Player) {
//            Player p = (Player) e.getDamager();
//            Packet packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 1.0F, 1.0F, 1.0F, 1.0F, 1, 1);
//            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
//            plugin.getLogger().info("El que realiza el daño es un player");
//            plugin.getLogger().info(p.getLocation().getChunk().getWorld().getName() + "_" + p.getLocation().getChunk().getX() + "_" + p.getLocation().getChunk().getZ());
//        } else {
//            Entity ent = e.getEntity();
//            ent.setCustomName("picha");
//            plugin.getLogger().info("El que realiza el daño es: " + e.getDamager().toString());
//            plugin.getLogger().info("El que realiza el daño es: " + e.getDamager().getType());
//            plugin.getLogger().info("El que realiza el daño es: " + e.getDamager().getClass().toString());
//        }
//        if (e.getEntity() instanceof Player) {
//            plugin.getLogger().info("El que recibe el daño es un player");
//        } else {
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().toString());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntityType().toString());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getType());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().toString());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().getCanonicalName());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().getName());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().getSimpleName());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getCustomName());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getName());
//            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getEntityId());
//            plugin.getLogger().info("El que recibe el daño es: " + CustomEntityType.CHICKENX.getName());
//        }
//        plugin.getLogger().info("///////////////////////////////");

        //plugin.getLogger().info("Quien realiza el daño: "+e.getDamager().getType().toString()+" con nombre: " +e.getDamager().getName());
        //plugin.getLogger().info("Quien recibe el daño: "+e.getEntity().getType().toString()+" con nombre: "+e.getEntity().getName());
    }

    /**
     *
     * @param e
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntitySpawn(CreatureSpawnEvent e) {
        //System.out.println(e.getEntity().toString());
        //System.out.println(e.getLocation().toString());
//        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CHUNK_GEN)) {
//            e.setCancelled(false);
//        }
        //System.out.println("entity: "+e.getEntity()+"\ntype: "+e.getEntityType());
//        Entity entity = e.getEntity();
//        EntityLiving living = (EntityLiving) ((CraftEntity) entity).getHandle();
        //CASTING A CUSTOM ENTITY
        //if (living instanceof CustomEntityChickenAggressive){
        //ACCESO A METODOS Y ATRIBUTOS
        //CustomEntityChickenAggressive ca= (CustomEntityChickenAggressive) living;
        //System.out.println("distancia "+ca.DistanceFromSpawnLimit);
        //System.out.println("Entre!");
//            EntityInsentient nmsEntity = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();
//            AttributeInstance attributes = nmsEntity.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE);
//     
//            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "ATTACK_DAMAGE", 20.0d, 10);
//                 attributes.b(modifier);
//            attributes.a(modifier);
        //}
//        Area a = aMan.getAreaByLocation(e.getEntity().getLocation());
//        if (a != null) {
//            if (!a.isSpawnAllowed()) {
//                //System.out.println("No permitido spawn");
//                e.setCancelled(true);
//            } else {
//                //System.out.println("Permitido el spawn");
//            }
//        }

    }

    /**
     * If chunk constains spawnpoints remove entities
     *
     * @param e event
     */
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        //quitar las entidades de los spawners del chunk descargado
        List<Entity> entitiesinchunk=Arrays.asList(e.getChunk().getEntities());
        RPGChunk chunk = RPGMMan.getRPGChunkfromChunk(e.getChunk());
        loadedchunks.remove(chunk);
        //plugin.getLogger().info("Chunk "+chunk.toString()+"\n----------------------------");
        for (Entity ent : entitiesinchunk) {
            //plugin.getLogger().info("entity uuid: "+ent.getUniqueId());
            if(RPGMMan.getEntities().containsKey(ent.getUniqueId())){
                plugin.getLogger().info("vamos por el buen camino");
                String spawnerName=RPGMMan.getEntities().get(ent.getUniqueId());
                RPGMMan.getEntities().remove(ent.getUniqueId());
                RPGMMan.removeUUIDfromSpawner(ent.getUniqueId(), spawnerName);
            }
        }
        
        for(Entity ent:e.getChunk().getEntities()){
            //plugin.getLogger().info(chunk.toString()+" "+ent.getUniqueId().toString());
            RPGMMan.removeUUIDfromSpawner(ent);
        }
        
        for (Map.Entry<RPGChunk, Map<String, RPGSpawner>> entrySet : this.RPGMMan.getSpawnerList().entrySet()) {
            if (chunk.equals(entrySet.getKey())) {
                //plugin.getLogger().info("Al fin uno -.-");
                //plugin.getLogger().info("obj"+chunk+" el nuestro "+entrySet.getKey());
                Map<String, RPGSpawner> spawnerslist = entrySet.getValue();
                for (Map.Entry<String, RPGSpawner> entrySet1 : spawnerslist.entrySet()) {
                    RPGSpawner value = entrySet1.getValue();
                    //value.run();
                    plugin.getLogger().info("Id del spawner a quitar "+value.getId());
                    plugin.getLogger().info("entidades total: "+value.getEntitiesUUIDS().size());
                    try{
                    RPGMMan.taskId.get(value.getId()).cancel();
                    RPGMMan.taskId.remove(value.getId());
                    }catch(IllegalStateException ex){
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
        //activar spawners del chunk que se ha cargado
        RPGChunk chunk = RPGMMan.getRPGChunkfromChunk(e.getChunk());
        loadedchunks.add(chunk);
        //plugin.getLogger().info(chunk.toString());
        for (Map.Entry<RPGChunk, Map<String, RPGSpawner>> entrySet : this.RPGMMan.getSpawnerList().entrySet()) {
            if (chunk.equals(entrySet.getKey())) {
                //plugin.getLogger().info("Al fin uno -.-");
                //plugin.getLogger().info("obj"+chunk+" el nuestro "+entrySet.getKey());
                Map<String, RPGSpawner> spawnerslist = entrySet.getValue();
                for (Map.Entry<String, RPGSpawner> entrySet1 : spawnerslist.entrySet()) {
                    RPGSpawner value = entrySet1.getValue();
                    plugin.getLogger().info("Id del spawner a cargar "+value.getId());
                    plugin.getLogger().info("entidades total: "+value.getEntitiesUUIDS().size());
                    RPGMMan.taskId.put(value.getId(), new RPGSpawnerBukkitRunnable(value, plugin));
                    try{
                        RPGMMan.taskId.get(value.getId()).runTaskTimer(plugin, 0, (int) value.getCooldown());
                    }catch(IllegalStateException ex){
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
                    //plugin.getLogger().info("Al fin uno -.-");
                    //plugin.getLogger().info("obj"+chunk+" el nuestro "+entrySet.getKey());
                    Map<String, RPGSpawner> spawnerslist = entrySet.getValue();
                    for (Map.Entry<String, RPGSpawner> entrySet1 : spawnerslist.entrySet()) {
                        RPGSpawner value = entrySet1.getValue();
                        //value.run();
                        plugin.getLogger().info("Id del spawner a cargar "+value.getId());
                        plugin.getLogger().info("entidades total: "+value.getEntitiesUUIDS().size());                        
                        RPGMMan.taskId.put(value.getId(), new RPGSpawnerBukkitRunnable(value, plugin));
                        try{
                        RPGMMan.taskId.get(value.getId()).runTaskTimer(plugin, 0, (int) value.getCooldown());
                        }catch(IllegalStateException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param e
     */
//    @EventHandler
//    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
//        if (e.getDamager() instanceof CraftRabbit) {
//            EntityRabbit rabbit = ((CraftRabbit) e.getDamager()).getHandle();
//            if (rabbit instanceof CustomEntityRabbitNormal) {
//               e.setDamage(1.0D);
//            }
//        }
//       if(e.getEntityType().equals(CustomEntityType.RABBITLVL1)){
//            e.setDamage(40.0D);
//            System.out.println(e.getDamage());
//        }
//
//    }
}
