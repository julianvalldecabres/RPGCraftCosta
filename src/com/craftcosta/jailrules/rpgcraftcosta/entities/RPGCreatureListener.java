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
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

/**
 *
 * @author jail
 */
public class RPGCreatureListener implements Listener {

    RPGCraftCosta plugin;
    private final Random random;

    /**
     *
     * @param plugin
     */
    public RPGCreatureListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
        random = new Random();
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
                || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.MOUNT)) {
            e.getEntity().remove();
            e.setCancelled(true);
            return;
        }
        LivingEntity ent = e.getEntity();
        Location loc = ent.getLocation();
        String name = ((LivingEntity) e.getEntity()).getName();
        plugin.getLogger().info("nombre:" + name
                + "\n reason: " + e.getSpawnReason().name()
                + "\n loc: " + loc.toString()
                + "\n id: " + e.getEntity().getEntityId()
                + "\n UUID: "+e.getEntity().getUniqueId().toString());
        e.getEntity().setCustomName(name);
        e.getEntity().setCustomNameVisible(true);

    }

    /**
     *
     * @param e
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        //If the damager contains the metadata, we are sure it's one of our snowballs
        if (damager.hasMetadata("snowarrow")) {
            boolean isCritical = damager.getMetadata("critical").get(0).asBoolean();
            int knockback = damager.getMetadata("knockback").get(0).asInt();
            double damage = damager.getMetadata("damage").get(0).asDouble();
            //This is the damage calculation, taken from the EntityArrow class
            double velocityLength = damager.getVelocity().length();
            double realDamage = Math.ceil(velocityLength * damage);
            if (isCritical) {
                realDamage += random.nextInt(((int) realDamage) / 2 + 2);
            }
            e.setDamage(realDamage);
      //Entities always burn "5" seconds for Flame enchantments
            //Since this is hard coded I might change it later to use the EntityCombustByEntityEvent
            if (damager.getFireTicks() > 0) {
                e.getEntity().setFireTicks(100);
            }
            //Now calculate knockback if necessary, taken from EntityArrow aswell
            if (knockback > 0) {
                Vector vector = damager.getVelocity();
                double horizontalSpeed = Math.sqrt(vector.getX() * vector.getX() + vector.getZ() * vector.getZ());
                Vector velocity = e.getEntity().getVelocity();
                velocity.setX(velocity.getX() + vector.getX() * knockback * 0.6 / horizontalSpeed);
                velocity.setY(velocity.getY() + 0.1);
                velocity.setZ(velocity.getZ() + vector.getZ() * knockback * 0.6 / horizontalSpeed);
                e.getEntity().setVelocity(velocity);
            }
            //Play the "ding" sound like normal arrows
            if (((Projectile) damager).getShooter() instanceof Player) {
                Player shooter = (Player) ((Projectile) e.getDamager()).getShooter();
                shooter.playSound(shooter.getLocation(), Sound.SUCCESSFUL_HIT, 0.5f, 0.5f); //Sound volume pitch
            }
        }
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
}
