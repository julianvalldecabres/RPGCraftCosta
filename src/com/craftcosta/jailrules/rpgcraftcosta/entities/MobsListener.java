/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftRabbit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 *
 * @author jail
 */
public class MobsListener implements Listener {

    private RPGCraftCosta plugin;

    public MobsListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

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

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof CraftRabbit) {
            EntityRabbit rabbit = ((CraftRabbit) e.getDamager()).getHandle();
//            if (rabbit instanceof CustomEntityRabbitNormal) {
//                e.setDamage(1.0D);
//            }
        }
//        if(e.getEntityType().equals(CustomEntityType.RABBITLVL1)){
//            e.setDamage(40.0D);
//            System.out.println(e.getDamage());
//        }

    }
}
