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
package com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs;

import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.PathFinderGoalGoHome;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityDamageSource;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IComplex;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 *
 * @author jail
 */
public class CEnderDragon extends EntityMonster implements IComplex, IRangedEntity {

    //VARIABLES AÑADIDAS
    private Location spawnLoc;
    private AttackType aType;
    private MobBehaviour mType;
    private boolean baby;
    private String name;
    private int level;
    private double movementspeed;
    private double knockback;
    private double attackdamage;
    private double attackSpeed;
    private double rangedDamage;
    private float rangedStrenght;
    private double followrange;
    //VARIABLES PROPIAS DE ENTITYENDERDRAGON

    /**
     *
     */
    public double a;

    /**
     *
     */
    public double b;

    /**
     *
     */
    public double c;

    /**
     *
     */
    public double[][] bk = new double[64][3];

    /**
     *
     */
    public int bl = -1;

    /**
     *
     */
    public EntityComplexPart[] children;

    /**
     *
     */
    public EntityComplexPart bn;

    /**
     *
     */
    public EntityComplexPart bo;

    /**
     *
     */
    public EntityComplexPart bp;

    /**
     *
     */
    public EntityComplexPart bq;

    /**
     *
     */
    public EntityComplexPart br;

    /**
     *
     */
    public EntityComplexPart bs;

    /**
     *
     */
    public EntityComplexPart bt;

    /**
     *
     */
    public float bu;

    /**
     *
     */
    public float bv;

    /**
     *
     */
    public boolean bw;

    /**
     *
     */
    public boolean bx;

    /**
     *
     */
    public Entity bA; // CraftBukkit - public // PAIL: Target

    /**
     *
     */
    public int by;

    /**
     *
     * @param world
     */
    public CEnderDragon(World world) {
        super(world);
        this.a(16.0F, 8.0F);
        this.children = new EntityComplexPart[]{this.bn = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.bo = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.bp = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bq = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.br = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bs = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bt = new EntityComplexPart(this, "wing", 4.0F, 4.0F)};
        this.spawnLoc = new Location(world.getWorld(), 0, 0, 0);
        //tipos por defecto
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.AGGRESSIVE;
        this.attackSpeed = 1.0D;
        this.rangedStrenght = 1.0F;
//reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(6.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3D);
        getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(10.0D);
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception exc) {
            System.out.println("Ojo alguna variable ha cambiado y hay que revisarlas");
            exc.printStackTrace();
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    /**
     *
     * @param world
     * @param spawnLoc
     */
    public CEnderDragon(World world, Location spawnLoc) {
        super(world);
        this.a(16.0F, 8.0F);
        this.children = new EntityComplexPart[]{this.bn = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.bo = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.bp = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bq = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.br = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bs = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bt = new EntityComplexPart(this, "wing", 4.0F, 4.0F)};
        this.spawnLoc = spawnLoc;

        //tipos por defecto
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.NORMAL;
        this.attackSpeed = 1.0D;
        this.rangedStrenght = 1.0F;
//reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(6.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3D);
        getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(10.0D);
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception exc) {
            System.out.println("Ojo alguna variable ha cambiado y hay que revisarlas");
            exc.printStackTrace();
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    private void initPathfinderGoals() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(9, new PathFinderGoalGoHome(1.0D, this, spawnLoc));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        //mobType PfG
        switch (mType) {
            case AGGREAGGRO:
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, true));
                this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
                break;
            case AGGRESSIVE:
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, false));
                this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, false));
                break;
            case NORMAGGRO:
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, true));
                break;
            case NORMAL:
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, false));
                break;
            default:
                //peaceful
                this.goalSelector.a(5, new PathfinderGoalPanic(this, 2.0D));
                break;
        }
        //attackType PfG
        switch (aType) {
            case RANGED:
                this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, attackSpeed, 20, 60, 15.0F));
                break;
            default:
                //MELEE
                this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, attackSpeed, true));
                break;
        }
    }

    //Override de IRangedEntity
    /**
     *
     * @param el
     * @param f
     */
    @Override
    public void a(EntityLiving el, float f) {
        //Para que nuestras entidades puedan realizar ataques a distancia hay que reescribir el metodo avoid a(EntityLiving el, float f)
        //que define que tipo de ataque a distancia realiza
        //Por simplicidad lo hemos dejado tal cual esta definido en el mob skeleton
        //Solo se modificará el daño realizado por la entidad arrow
        //Se puede modificar la fuerza con la que es disparada
        EntityArrow entityarrow = new EntityArrow(this.world, this, el, rangedStrenght, 14 - this.world.getDifficulty().a() * 4);
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bA());
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bA());

        entityarrow.b(f * 2.0F + this.random.nextGaussian() * 0.25D + this.world.getDifficulty().a() * 0.11F);
        if (i > 0) {
            entityarrow.b(entityarrow.j() + i * 0.5D + 0.5D);
        }
        if (j > 0) {
            entityarrow.setKnockbackStrength(j);
        }
        if ((EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, bA()) > 0) || (getType() == 1)) {
            EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                entityarrow.setOnFire(event.getDuration());
            }
        }
        EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, bA(), entityarrow, 0.8F);
        if (event.isCancelled()) {
            event.getProjectile().remove();
            return;
        }
        if (event.getProjectile() == entityarrow.getBukkitEntity()) {
            this.world.addEntity(entityarrow);
        }
        makeSound("random.bow", 1.0F, 1.0F / (bc().nextFloat() * 0.4F + 0.8F));
    }

    /**
     *
     * @return
     */
    public int getType() {
        switch (aType) {
            case MELEE:
                return 1;
            default:
                return 0;
        }
    }

    /**
     *
     */
    protected void h() {
        super.h();
    }

    /**
     *
     * @return
     */
    @Override
    public World a() {
        return this.world;
    }

    /**
     *
     * @return
     */
    protected String z() {
        return "mob.enderdragon.growl";
    }

    /**
     *
     * @return
     */
    protected String bo() {
        return "mob.enderdragon.hit";
    }

    /**
     *
     * @return
     */
    protected float bB() {
        return 5.0F;
    }

    /**
     *
     */
    protected void D() {
    }

    /**
     *
     * @return
     */
    public Entity[] aB() {
        return this.children;
    }

    /**
     *
     * @return
     */
    public boolean ad() {
        return false;
    }

    /**
     *
     */
    public void m() {
        float f;
        float f1;

        if (this.world.isClientSide) {
            f = MathHelper.cos(this.bv * 3.1415927F * 2.0F);
            f1 = MathHelper.cos(this.bu * 3.1415927F * 2.0F);
            if (f1 <= -0.3F && f >= -0.3F && !this.R()) {
                this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
            }
        }

        this.bu = this.bv;
        float f2;

        if (this.getHealth() <= 0.0F) {
            f = (this.random.nextFloat() - 0.5F) * 8.0F;
            f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX + (double) f, this.locY + 2.0D + (double) f1, this.locZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
        } else {
            this.n();
            f = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
            f *= (float) Math.pow(2.0D, this.motY);
            if (this.bx) {
                this.bv += f * 0.5F;
            } else {
                this.bv += f;
            }

            this.yaw = MathHelper.g(this.yaw);
            if (this.ce()) {
                this.bv = 0.5F;
            } else {
                if (this.bl < 0) {
                    for (int i = 0; i < this.bk.length; ++i) {
                        this.bk[i][0] = (double) this.yaw;
                        this.bk[i][1] = this.locY;
                    }
                }

                if (++this.bl == this.bk.length) {
                    this.bl = 0;
                }

                this.bk[this.bl][0] = (double) this.yaw;
                this.bk[this.bl][1] = this.locY;
                double d0;
                double d1;
                double d2;
                double d3;
                float f3;

                if (this.world.isClientSide) {
                    if (this.bc > 0) {
                        d3 = this.locX + (this.bd - this.locX) / (double) this.bc;
                        d0 = this.locY + (this.be - this.locY) / (double) this.bc;
                        d1 = this.locZ + (this.bf - this.locZ) / (double) this.bc;
                        d2 = MathHelper.g(this.bg - (double) this.yaw);
                        this.yaw = (float) ((double) this.yaw + d2 / (double) this.bc);
                        this.pitch = (float) ((double) this.pitch + (this.bh - (double) this.pitch) / (double) this.bc);
                        --this.bc;
                        this.setPosition(d3, d0, d1);
                        this.setYawPitch(this.yaw, this.pitch);
                    }
                } else {
                    d3 = this.a - this.locX;
                    d0 = this.b - this.locY;
                    d1 = this.c - this.locZ;
                    d2 = d3 * d3 + d0 * d0 + d1 * d1;
                    double d4;

                    if (this.bA != null) {
                        this.a = this.bA.locX;
                        this.c = this.bA.locZ;
                        double d5 = this.a - this.locX;
                        double d6 = this.c - this.locZ;
                        double d7 = Math.sqrt(d5 * d5 + d6 * d6);

                        d4 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
                        if (d4 > 10.0D) {
                            d4 = 10.0D;
                        }

                        this.b = this.bA.getBoundingBox().b + d4;
                    } else {
                        this.a += this.random.nextGaussian() * 2.0D;
                        this.c += this.random.nextGaussian() * 2.0D;
                    }

                    if (this.bw || d2 < 100.0D || d2 > 22500.0D || this.positionChanged || this.E) {
                        this.cf();
                    }

                    d0 /= (double) MathHelper.sqrt(d3 * d3 + d1 * d1);
                    f3 = 0.6F;
                    d0 = MathHelper.a(d0, (double) (-f3), (double) f3);
                    this.motY += d0 * 0.10000000149011612D;
                    this.yaw = MathHelper.g(this.yaw);
                    double d8 = 180.0D - MathHelper.b(d3, d1) * 180.0D / 3.1415927410125732D;
                    double d9 = MathHelper.g(d8 - (double) this.yaw);

                    if (d9 > 50.0D) {
                        d9 = 50.0D;
                    }

                    if (d9 < -50.0D) {
                        d9 = -50.0D;
                    }

                    Vec3D vec3d = (new Vec3D(this.a - this.locX, this.b - this.locY, this.c - this.locZ)).a();

                    d4 = (double) (-MathHelper.cos(this.yaw * 3.1415927F / 180.0F));
                    Vec3D vec3d1 = (new Vec3D((double) MathHelper.sin(this.yaw * 3.1415927F / 180.0F), this.motY, d4)).a();
                    float f4 = ((float) vec3d1.b(vec3d) + 0.5F) / 1.5F;

                    if (f4 < 0.0F) {
                        f4 = 0.0F;
                    }

                    this.bb *= 0.8F;
                    float f5 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0F + 1.0F;
                    double d10 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0D + 1.0D;

                    if (d10 > 40.0D) {
                        d10 = 40.0D;
                    }

                    this.bb = (float) ((double) this.bb + d9 * (0.699999988079071D / d10 / (double) f5));
                    this.yaw += this.bb * 0.1F;
                    float f6 = (float) (2.0D / (d10 + 1.0D));
                    float f7 = 0.06F;

                    this.a(0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));
                    if (this.bx) {
                        this.move(this.motX * 0.800000011920929D, this.motY * 0.800000011920929D, this.motZ * 0.800000011920929D);
                    } else {
                        this.move(this.motX, this.motY, this.motZ);
                    }

                    Vec3D vec3d2 = (new Vec3D(this.motX, this.motY, this.motZ)).a();
                    float f8 = ((float) vec3d2.b(vec3d1) + 1.0F) / 2.0F;

                    f8 = 0.8F + 0.15F * f8;
                    this.motX *= (double) f8;
                    this.motZ *= (double) f8;
                    this.motY *= 0.9100000262260437D;
                }

                this.aI = this.yaw;
                this.bn.width = this.bn.length = 3.0F;
                this.bp.width = this.bp.length = 2.0F;
                this.bq.width = this.bq.length = 2.0F;
                this.br.width = this.br.length = 2.0F;
                this.bo.length = 3.0F;
                this.bo.width = 5.0F;
                this.bs.length = 2.0F;
                this.bs.width = 4.0F;
                this.bt.length = 3.0F;
                this.bt.width = 4.0F;
                f1 = (float) (this.b(5, 1.0F)[1] - this.b(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
                f2 = MathHelper.cos(f1);
                float f9 = -MathHelper.sin(f1);
                float f10 = this.yaw * 3.1415927F / 180.0F;
                float f11 = MathHelper.sin(f10);
                float f12 = MathHelper.cos(f10);

                this.bo.t_();
                this.bo.setPositionRotation(this.locX + (double) (f11 * 0.5F), this.locY, this.locZ - (double) (f12 * 0.5F), 0.0F, 0.0F);
                this.bs.t_();
                this.bs.setPositionRotation(this.locX + (double) (f12 * 4.5F), this.locY + 2.0D, this.locZ + (double) (f11 * 4.5F), 0.0F, 0.0F);
                this.bt.t_();
                this.bt.setPositionRotation(this.locX - (double) (f12 * 4.5F), this.locY + 2.0D, this.locZ - (double) (f11 * 4.5F), 0.0F, 0.0F);
                if (!this.world.isClientSide && this.hurtTicks == 0) {
                    this.a(this.world.getEntities(this, this.bs.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
                    this.a(this.world.getEntities(this, this.bt.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
                    this.b(this.world.getEntities(this, this.bn.getBoundingBox().grow(1.0D, 1.0D, 1.0D)));
                }

                double[] adouble = this.b(5, 1.0F);
                double[] adouble1 = this.b(0, 1.0F);

                f3 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F - this.bb * 0.01F);
                float f13 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F - this.bb * 0.01F);

                this.bn.t_();
                this.bn.setPositionRotation(this.locX + (double) (f3 * 5.5F * f2), this.locY + (adouble1[1] - adouble[1]) * 1.0D + (double) (f9 * 5.5F), this.locZ - (double) (f13 * 5.5F * f2), 0.0F, 0.0F);

                for (int j = 0; j < 3; ++j) {
                    EntityComplexPart entitycomplexpart = null;

                    if (j == 0) {
                        entitycomplexpart = this.bp;
                    }

                    if (j == 1) {
                        entitycomplexpart = this.bq;
                    }

                    if (j == 2) {
                        entitycomplexpart = this.br;
                    }

                    double[] adouble2 = this.b(12 + j * 2, 1.0F);
                    float f14 = this.yaw * 3.1415927F / 180.0F + this.b(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
                    float f15 = MathHelper.sin(f14);
                    float f16 = MathHelper.cos(f14);
                    float f17 = 1.5F;
                    float f18 = (float) (j + 1) * 2.0F;

                    entitycomplexpart.t_();
                    entitycomplexpart.setPositionRotation(this.locX - (double) ((f11 * f17 + f15 * f18) * f2), this.locY + (adouble2[1] - adouble[1]) * 1.0D - (double) ((f18 + f17) * f9) + 1.5D, this.locZ + (double) ((f12 * f17 + f16 * f18) * f2), 0.0F, 0.0F);
                }

                if (!this.world.isClientSide) {
                    this.bx = this.b(this.bn.getBoundingBox()) | this.b(this.bo.getBoundingBox());
                }

            }
        }
    }

    private void b(List<Entity> list) {
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);

            if (entity instanceof EntityHuman) {
                entity.damageEntity(DamageSource.mobAttack(this), 10.0F);
                this.a((EntityLiving) this, entity);
            }
        }

    }

    private void a(List<Entity> list) {
        double d0 = (this.bo.getBoundingBox().a + this.bo.getBoundingBox().d) / 2.0D;
        double d1 = (this.bo.getBoundingBox().c + this.bo.getBoundingBox().f) / 2.0D;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (entity instanceof EntityHuman) {
                double d2 = entity.locX - d0;
                double d3 = entity.locZ - d1;
                double d4 = d2 * d2 + d3 * d3;

                entity.g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
            }
        }

    }

    private float b(double d0) {
        return (float) MathHelper.g(d0);
    }

    /**
     *
     * @param i
     * @param f
     * @return
     */
    public double[] b(int i, float f) {
        if (this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        f = 1.0F - f;
        int j = this.bl - i * 1 & 63;
        int k = this.bl - i * 1 - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.bk[j][0];
        double d1 = MathHelper.g(this.bk[k][0] - d0);

        adouble[0] = d0 + d1 * (double) f;
        d0 = this.bk[j][1];
        d1 = this.bk[k][1] - d0;
        adouble[1] = d0 + d1 * (double) f;
        adouble[2] = this.bk[j][2] + (this.bk[k][2] - this.bk[j][2]) * (double) f;
        return adouble;
    }

    private void n() {
    }

    /**
     *
     * @return
     */
    public boolean cf() {

        this.bw = false;
        ArrayList arraylist = Lists.newArrayList(this.world.players);
        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            if (((EntityHuman) iterator.next()).isSpectator()) {
                iterator.remove();
            }
        }

        if (this.random.nextInt(2) == 0 && !arraylist.isEmpty()) {
            // CraftBukkit start
            Entity target = (Entity) this.world.players.get(this.random.nextInt(this.world.players.size()));
            EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.RANDOM_TARGET);
            this.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (event.getTarget() == null) {
                    this.bA = null;
                } else {
                    this.bA = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity) event.getTarget()).getHandle();
                }
            }
            // CraftBukkit end
        } else {
            boolean flag;

            do {
                this.a = 0.0D;
                this.b = (double) (70.0F + this.random.nextFloat() * 50.0F);
                this.c = 0.0D;
                this.a += (double) (this.random.nextFloat() * 120.0F - 60.0F);
                this.c += (double) (this.random.nextFloat() * 120.0F - 60.0F);
                double d0 = this.locX - this.a;
                double d1 = this.locY - this.b;
                double d2 = this.locZ - this.c;

                flag = d0 * d0 + d1 * d1 + d2 * d2 > 100.0D;
            } while (!flag);

            this.bA = null;
        }
        return !this.navigation.m();

    }

    /**
     *
     * @param entitycomplexpart
     * @param damagesource
     * @param f
     * @return
     */
    @Override
    public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
        if (entitycomplexpart != this.bn) {
            f = f / 4.0F + 1.0F;
        }

        float f1 = this.yaw * 3.1415927F / 180.0F;
        float f2 = MathHelper.sin(f1);
        float f3 = MathHelper.cos(f1);

        this.a = this.locX + (double) (f2 * 5.0F) + (double) ((this.random.nextFloat() - 0.5F) * 2.0F);
        this.b = this.locY + (double) (this.random.nextFloat() * 3.0F) + 1.0D;
        this.c = this.locZ - (double) (f3 * 5.0F) + (double) ((this.random.nextFloat() - 0.5F) * 2.0F);
        this.bA = null;
        if (damagesource.getEntity() instanceof EntityHuman || damagesource.isExplosion()) {
            this.dealDamage(damagesource, f);
        }

        return true;
    }

    /**
     *
     */
    public void G() {
        this.die();
    }

    private boolean b(AxisAlignedBB axisalignedbb) {
        return false;
    }

    /**
     *
     * @param damagesource
     * @param f
     * @return
     */
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource instanceof EntityDamageSource && ((EntityDamageSource) damagesource).w()) {
            this.dealDamage(damagesource, f);
        }

        return false;
    }

    /**
     *
     * @param damagesource
     * @param f
     * @return
     */
    protected boolean dealDamage(DamageSource damagesource, float f) {
        return super.damageEntity(damagesource, f);
    }

    /**
     *
     */
    protected void aZ() {
        if (this.dead) {
            return; // CraftBukkit - can't kill what's already dead
        }
        ++this.by;
        if (this.by >= 180 && this.by <= 200) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;

            this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.locX + (double) f, this.locY + 2.0D + (double) f1, this.locZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
        int i;
        int j;

        if (!this.world.isClientSide) {
            if (this.by > 150 && this.by % 5 == 0 && flag) {
                i = this.expToDrop / 12; // CraftBukkit - drop experience as dragon falls from sky. use experience drop from death event. This is now set in getExpReward()

                while (i > 0) {
                    j = EntityExperienceOrb.getOrbValue(i);
                    i -= j;
                    this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
                }
            }

            if (this.by == 1) {
                // CraftBukkit start - Use relative location for far away sounds
                // this.world.a(1018, new BlockPosition(this), 0);
                int viewDistance = ((WorldServer) this.world).getServer().getViewDistance() * 16;
                for (EntityPlayer player : (List<EntityPlayer>) MinecraftServer.getServer().getPlayerList().players) {
                    double deltaX = this.locX - player.locX;
                    double deltaZ = this.locZ - player.locZ;
                    double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
                    if (distanceSquared > viewDistance * viewDistance) {
                        double deltaLength = Math.sqrt(distanceSquared);
                        double relativeX = player.locX + (deltaX / deltaLength) * viewDistance;
                        double relativeZ = player.locZ + (deltaZ / deltaLength) * viewDistance;
                        player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, new BlockPosition((int) relativeX, (int) this.locY, (int) relativeZ), 0, true));
                    } else {
                        player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, new BlockPosition((int) this.locX, (int) this.locY, (int) this.locZ), 0, true));
                    }
                }
                // CraftBukkit end
            }
        }
        this.move(0.0D, 0.10000000149011612D, 0.0D);
        this.aI = this.yaw += 20.0F;
        if (this.by == 200 && !this.world.isClientSide) {
            if (flag) {
                i = this.expToDrop - (10 * this.expToDrop / 12); // CraftBukkit - drop the remaining experience

                while (i > 0) {
                    j = EntityExperienceOrb.getOrbValue(i);
                    i -= j;
                    this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
                }
            }

            this.a(new BlockPosition(this.locX, 64.0D, this.locZ));
            this.die();
        }

    }

}
