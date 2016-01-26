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
public class PathfinderGoalExplodeLikeCreeper extends PathfinderGoal{

    private EntityMonster entity;
    private float prob;
    private float radius;

    public PathfinderGoalExplodeLikeCreeper(EntityMonster entity, float prob, float radius) {
        this.entity = entity;
        this.prob = prob;
        this.radius = radius;
    }
    
    @Override
    public boolean a() {
        EntityLiving entityliving = this.entity.getGoalTarget();
        double aHealth= this.entity.getHealth();
        double mHealth= this.entity.getMaxHealth();
        double pHealth= aHealth/mHealth;
        if(entityliving != null && entityliving.isAlive() && pHealth<this.prob){
            return true;
        }
        if(entityliving != null && entityliving.isAlive() && pHealth<(this.prob*2)){
            //Seria interesante invocar un metodo que llegado a este punto muestre una animacion
            //que ayude a preveer una explosion
//            Packet packet = new PacketPlayOutWorldParticles(EnumParticle.HEART, true,(float)this.entity.locX,(float) this.entity.locY,(float) this.entity.locZ, 1.0F, 1.0F, 1.0F, 1.0F, 1, 1);
//                ((CraftPlayer) entityliving.getBukkitEntity()).getHandle().playerConnection.sendPacket(packet);
            return false;
        }
        
        return false;
    }
    
    public void c(){
        ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.entity.getBukkitEntity(),(float)this.entity.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).getValue() , false);
            this.entity.getWorld().getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.entity.getWorld().createExplosion(this.entity, this.entity.locX, this.entity.locY, this.entity.locZ, event.getRadius(), event.getFire(), true);
                this.entity.die();
            }
    }
    
}
