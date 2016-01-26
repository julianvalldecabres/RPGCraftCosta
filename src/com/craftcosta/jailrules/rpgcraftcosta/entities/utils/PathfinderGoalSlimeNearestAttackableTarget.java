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

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.PathfinderGoalTarget;

/**
 *
 * @author jail
 */
public class PathfinderGoalSlimeNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalTarget {

    protected final Class<T> a;
    private final int g;
    private int b;
    protected final PathfinderGoalSlimeNearestAttackableTarget.DistanceComparator path;
    protected Predicate<? super T> c;
    protected EntityLiving d;

    public PathfinderGoalSlimeNearestAttackableTarget(EntityCreature entitycreature, Class<T> oclass, boolean flag) {
        
        this(entitycreature, oclass, flag, false);
    }

    public PathfinderGoalSlimeNearestAttackableTarget(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
        this(entitycreature, oclass, 10, flag, flag1, (Predicate) null);
    }

    public PathfinderGoalSlimeNearestAttackableTarget(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, final Predicate<? super T> predicate) {
        super(entitycreature, flag, flag1);
        this.a = oclass;
        this.g = i;
        this.path = new PathfinderGoalSlimeNearestAttackableTarget.DistanceComparator(entitycreature);
        this.a(1);
        this.c = new Predicate() {
            public boolean a(T t0) {
                if (predicate != null && !predicate.apply(t0)) {
                    return false;
                } else {
                    if (t0 instanceof EntityHuman) {
                        double d0 = PathfinderGoalSlimeNearestAttackableTarget.this.f();

                        if (t0.isSneaking()) {
                            d0 *= 0.800000011920929D;
                        }

                        if (t0.isInvisible()) {
                            float f = ((EntityHuman) t0).bY();

                            if (f < 0.1F) {
                                f = 0.1F;
                            }

                            d0 *= (double) (0.7F * f);
                        }

                        if ((double) t0.g(PathfinderGoalSlimeNearestAttackableTarget.this.e) > d0) {
                            return false;
                        }
                    }

                    return PathfinderGoalSlimeNearestAttackableTarget.this.a(t0, false);
                }
            }

            public boolean apply(Object object) {
                return this.a((T) object); // CraftBukkit - fix decompile error
            }
        };
    }

    public boolean a() {
        if (this.g > 0 && this.e.bc().nextInt(this.g) != 0) {
            return false;
        } else {
            double d0 = this.f();
            List list = this.e.world.a(this.a, this.e.getBoundingBox().grow(d0, 4.0D, d0), Predicates.and(this.c, IEntitySelector.d));

            Collections.sort(list, this.path);
            if (list.isEmpty()) {
                return false;
            } else {
                this.d = (EntityLiving) list.get(0);
                return true;
            }
        }
    }

    public void c() {
        this.b=300;
        this.e.setGoalTarget(this.d, d instanceof EntityPlayer ? org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_PLAYER : org.bukkit.event.entity.EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true); // Craftbukkit - reason
        super.c();
    }

    public static class DistanceComparator implements Comparator<Entity> {

        private final Entity a;

        public DistanceComparator(Entity entity) {
            this.a = entity;
        }

        public int a(Entity entity, Entity entity1) {
            double d0 = this.a.h(entity);
            double d1 = this.a.h(entity1);

            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Entity object, Entity object1) { // CraftBukkit - fix decompile error
            return this.a((Entity) object, (Entity) object1);
        }
    }
}
