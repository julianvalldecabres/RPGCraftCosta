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
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 *
 * @author jail
 */
public class RPGMobListener implements Listener {

    private RPGCraftCosta plugin;
    private RPGMobManager RPGMMan;

    /**
     *
     * @param plugin
     */
    public RPGMobListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGMMan = plugin.getRPGMobManager();
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

        plugin.getLogger().info("///////////////////////////////");
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Packet packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 1.0F, 1.0F, 1.0F, 1.0F, 1, 1);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            plugin.getLogger().info("El que realiza el daño es un player");
            plugin.getLogger().info(p.getLocation().getChunk().getWorld().getName() + "_" + p.getLocation().getChunk().getX() + "_" + p.getLocation().getChunk().getZ());
        } else {
            Entity ent = e.getEntity();
            ent.setCustomName("picha");
            plugin.getLogger().info("El que realiza el daño es: " + e.getDamager().toString());
            plugin.getLogger().info("El que realiza el daño es: " + e.getDamager().getType());
            plugin.getLogger().info("El que realiza el daño es: " + e.getDamager().getClass().toString());
        }
        if (e.getEntity() instanceof Player) {
            plugin.getLogger().info("El que recibe el daño es un player");
        } else {
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().toString());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntityType().toString());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getType());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().toString());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().getCanonicalName());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().getName());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getClass().getSimpleName());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getCustomName());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getName());
            plugin.getLogger().info("El que recibe el daño es: " + e.getEntity().getEntityId());
            plugin.getLogger().info("El que recibe el daño es: " + CustomEntityType.CHICKENX.getName());
        }
        if (e.getEntity() instanceof EntityChicken) {
            EntityChicken ent = (EntityChicken) e.getEntity();
            CraftEntity entchi = ent.getBukkitEntity();
            plugin.getLogger().info("bukkitentity: " + entchi.getCustomName().toString());
        }
        plugin.getLogger().info("///////////////////////////////");

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
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CHUNK_GEN)) {
            e.setCancelled(true);
        }
        //System.out.println("entity: "+e.getEntity()+"\ntype: "+e.getEntityType());
        Entity entity = e.getEntity();
        EntityLiving living = (EntityLiving) ((CraftEntity) entity).getHandle();
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
        //quitar las entidades de los spawnere del chunk descargado
        if (RPGMMan.chunkHasSpawners(e.getChunk())) {
            for (Entity ent : plugin.getServer().getWorld("").getEntities()) {
                ent.getUniqueId();
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
