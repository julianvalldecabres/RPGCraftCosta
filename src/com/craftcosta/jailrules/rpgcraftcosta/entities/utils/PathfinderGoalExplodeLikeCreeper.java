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
package com.craftcosta.jailrules.rpgcraftcosta.entities.utils;

import static net.minecraft.server.v1_8_R3.Block.f;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author jail
 */
public class PathfinderGoalExplodeLikeCreeper extends PathfinderGoal {

    private EntityMonster entity;
    private float prob;
    private float radius;

    /**
     *
     * @param entity
     * @param prob
     * @param radius
     */
    public PathfinderGoalExplodeLikeCreeper(EntityMonster entity, float prob, float radius) {
        this.entity = entity;
        this.prob = prob;
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean a() {
        EntityLiving entityliving = this.entity.getGoalTarget();
        double aHealth = this.entity.getHealth();
        double mHealth = this.entity.getMaxHealth();
        double pHealth = aHealth / mHealth;
        if (entityliving != null && entityliving.isAlive() && pHealth < this.prob) {
            return true;
        }
        if (entityliving != null && entityliving.isAlive() && pHealth < (this.prob * 2)) {
            //Seria interesante invocar un metodo que llegado a este punto muestre una animacion
            //que ayude a preveer una explosion
//            Packet packet = new PacketPlayOutWorldParticles(EnumParticle.HEART, true,(float)this.entity.locX,(float) this.entity.locY,(float) this.entity.locZ, 1.0F, 1.0F, 1.0F, 1.0F, 1, 1);
//                ((CraftPlayer) entityliving.getBukkitEntity()).getHandle().playerConnection.sendPacket(packet);
            return false;
        }

        return false;
    }

    /**
     *
     */
    public void c() {
        ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.entity.getBukkitEntity(), (float) this.entity.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).getValue(), false);
        this.entity.getWorld().getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            this.entity.getWorld().createExplosion(this.entity, this.entity.locX, this.entity.locY, this.entity.locZ, event.getRadius(), event.getFire(), true);
            this.entity.die();
        }
    }

}
