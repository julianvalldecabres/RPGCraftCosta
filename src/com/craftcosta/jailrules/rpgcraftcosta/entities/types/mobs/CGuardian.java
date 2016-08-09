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
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.GuardianType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.PathFinderGoalGoHome;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ControllerLook;
import net.minecraft.server.v1_8_R3.ControllerMove;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
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
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

/**
 *
 * @author jail
 */
public class CGuardian extends EntityMonster implements IRangedEntity {

    //VARIABLES AÑADIDAS QUE DEFINEN EL TIPO DE MOB
    private AttackType aType;
    private MobBehaviour mType;
    //VARIBLES ATRIBUTOS DEL MOB

    /**
     *
     */
        public Location spawnLoc;
    private GuardianType gtype;
    private String name;
    private int level;
    private double movementspeed;
    private double knockback;
    private double attackdamage;
    private double attackSpeed;
    private double rangedDamage;
    private float rangedStrength;
    private double followrange;
    private double maxhealth;

    //VARIABLES PROPIAS DEL ENTITYGUARDIAN
    private float a;
    private float b;
    private float c;
    private float bm;
    private float bn;
    private EntityLiving bo;
    private int bp;
    private boolean bq;

    /**
     *
     */
    public PathfinderGoalRandomStroll goalRandomStroll;

    /**
     *
     * @param world
     */
    public CGuardian(World world) {
        super(world);
        this.b_ = 10;
        this.setSize(0.85F, 0.85F);
        this.spawnLoc = new Location(world.getWorld(), 0, 0, 0);
        this.gtype = GuardianType.NORMAL;
        //tipos por defecto
        this.attackSpeed = 1.0D;
        this.rangedDamage = 3.0D;
        this.rangedStrength = 1.0F;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.NORMAL;
        //set Guardian Type

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
    public CGuardian(World world, Location spawnLoc) {
        super(world);
        this.b_ = 10;
        this.setSize(0.85F, 0.85F);
        this.spawnLoc = spawnLoc;
        this.gtype = GuardianType.ELDER;
        //tipos por defecto
        this.attackSpeed = 1.0D;
        this.rangedDamage = 3.0D;
        this.rangedStrength = 1.0F;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.AGGRESSIVE;
        //set Guardian Type
        switch (gtype) {
            case ELDER:
                this.setElder(true);
                break;
            case NORMAL:
                this.setElder(false);
                break;
        }

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
        this.moveController= new ControllerMoveGuardian(this);
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
            case MAGIC:
                this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, attackSpeed, 60, 10.0F));
                break;
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
        EntityArrow entityarrow = new EntityArrow(this.world, this, el, 1.6F, 14 - this.world.getDifficulty().a() * 4);
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
            case MAGIC:
            case MELEE:
                return 1;
            default:
                return 0;
        }
    }

    //METODOS PROPIOS DEL ENTITYGUARDIAN

    /**
     *
     * @param f
     * @param f1
     */
        public void e(float f, float f1) {
    }

    /**
     *
     * @param nbttagcompound
     */
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    /**
     *
     * @param nbttagcompound
     */
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

//    protected NavigationAbstract b(World world) {
//        return new Navigation(this, world);
//    }

    /**
     *
     */
        protected void h() {
        super.h();
        this.datawatcher.a(16, Integer.valueOf(0));
        this.datawatcher.a(17, Integer.valueOf(0));
    }

    private boolean a(int i) {
        return (this.datawatcher.getInt(16) & i) != 0;
    }

    //metodo para cambiar la apariencia del guardian
    private void a(int i, boolean flag) {
        int j = this.datawatcher.getInt(16);

        if (flag) {
            this.datawatcher.watch(16, Integer.valueOf(j | i));
        } else {
            this.datawatcher.watch(16, Integer.valueOf(j & ~i));
        }

    }

    //devuelve true si el guardian es normal

    /**
     *
     * @return
     */
        public boolean n() {
        return this.a(2);
    }

    private void l(boolean flag) {
        this.a(2, flag);
    }

    /**
     *
     * @return
     */
    public int cm() {
        return this.isElder() ? 60 : 80;
    }

    //devuelve true si el guardian es elder

    /**
     *
     * @return
     */
        public boolean isElder() {
        return this.a(4);
    }

    //modifica la entidad para que tenga aspecto de elder

    /**
     *
     * @param flag
     */
        public void setElder(boolean flag) {
        this.a(4, flag);
    }

    private void b(int i) {
        this.datawatcher.watch(17, Integer.valueOf(i));
    }

    //devuelve true si el guardian tiene un objetivo

    /**
     *
     * @return
     */
        public boolean cp() {
        return this.datawatcher.getInt(17) != 0;
    }

    //devuelve si el guardian tiene un objetivo

    /**
     *
     * @return
     */
        public EntityLiving cq() {
        if (!this.cp()) {
            return null;
        } else {
            return this.getGoalTarget();
        }
    }

    /**
     *
     * @param i
     */
    public void i(int i) {
        super.i(i);
        if (i == 16) {
            if (this.isElder() && this.width < 1.0F) {
                this.setSize(1.9975F, 1.9975F);
            }
        } else if (i == 17) {
            this.bp = 0;
            this.bo = null;
        }

    }

    /**
     *
     * @return
     */
    public int w() {
        return 160;
    }

    /**
     *
     * @return
     */
    protected String z() {
        return !this.V() ? "mob.guardian.land.idle" : (this.isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
    }

    /**
     *
     * @return
     */
    protected String bo() {
        return !this.V() ? "mob.guardian.land.hit" : (this.isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
    }

    /**
     *
     * @return
     */
    protected String bp() {
        return !this.V() ? "mob.guardian.land.death" : (this.isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
    }

    /**
     *
     * @return
     */
    protected boolean s_() {
        return false;
    }

    /**
     *
     * @return
     */
    public float getHeadHeight() {
        return this.length * 0.5F;
    }

    /**
     *
     * @param blockposition
     * @return
     */
    public float a(BlockPosition blockposition) {
        return this.world.getType(blockposition).getBlock().getMaterial() == Material.WATER ? 10.0F + this.world.o(blockposition) - 0.5F : super.a(blockposition);
    }

    /**
     *
     */
    public void m() {

        if (this.world.isClientSide) {
            this.b = this.a;
            if (!this.V()) {
                this.c = 2.0F;
                if (this.motY > 0.0D && this.bq && !this.R()) {
                    this.world.a(this.locX, this.locY, this.locZ, "mob.guardian.flop", 1.0F, 1.0F, false);
                }

                this.bq = this.motY < 0.0D && this.world.d((new BlockPosition(this)).down(), false);
            } else if (this.n()) {
                if (this.c < 0.5F) {
                    this.c = 4.0F;
                } else {
                    this.c += (0.5F - this.c) * 0.1F;
                }
            } else {
                this.c += (0.125F - this.c) * 0.2F;
            }

            this.a += this.c;
            this.bn = this.bm;
            if (!this.V()) {
                this.bm = this.random.nextFloat();
            } else if (this.n()) {
                this.bm += (0.0F - this.bm) * 0.25F;
            } else {
                this.bm += (1.0F - this.bm) * 0.06F;
            }

            if (this.n() && this.V()) {
                Vec3D vec3d = this.d(0.0F);

                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width - vec3d.a * 1.5D, this.locY + this.random.nextDouble() * (double) this.length - vec3d.b * 1.5D, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width - vec3d.c * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
                }
            }

            if (this.cp()) {
                if (this.bp < this.cm()) {
                    ++this.bp;
                }

                EntityLiving entityliving = this.cq();

                if (entityliving != null) {
                    this.getControllerLook().a(entityliving, 90.0F, 90.0F);
                    this.getControllerLook().a();
                    double d0 = (double) this.q(0.0F);
                    double d1 = entityliving.locX - this.locX;
                    double d2 = entityliving.locY + (double) (entityliving.length * 0.5F) - (this.locY + (double) this.getHeadHeight());
                    double d3 = entityliving.locZ - this.locZ;
                    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

                    d1 /= d4;
                    d2 /= d4;
                    d3 /= d4;
                    double d5 = this.random.nextDouble();

                    while (d5 < d4) {
                        d5 += 1.8D - d0 + this.random.nextDouble() * (1.7D - d0);
                        this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + d1 * d5, this.locY + d2 * d5 + (double) this.getHeadHeight(), this.locZ + d3 * d5, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
            }
        }

//        if (this.cp()) {
//            this.yaw = this.aK;
//        } else {
//            if (this.inWater) {
//                this.setAirTicks(300);
//            } else if (this.onGround) {
//                this.motY += 0.5D;
//                this.yaw = this.random.nextFloat() * 360.0F;
//                this.onGround = false;
//                this.ai = true;
//            }
//
//        }

        super.m();
    }

    /**
     *
     * @param f
     * @return
     */
    public float q(float f) {
        return ((float) this.bp + f) / (float) this.cm();
    }

    /**
     *
     */
    protected void E() {
        super.E();
    }

    /**
     *
     * @return
     */
    protected boolean n_() {
        return true;
    }

    /**
     *
     * @return
     */
    public boolean canSpawn() {
        return this.world.a(this.getBoundingBox(), (Entity) this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean bR() {
        return (this.random.nextInt(20) == 0 || !this.world.j(new BlockPosition(this))) && super.bR();
    }

    /**
     *
     * @param damagesource
     * @param f
     * @return
     */
    public boolean damageEntity(DamageSource damagesource, float f) {
        return super.damageEntity(damagesource, f);
    }

    /**
     *
     * @return
     */
    public int bQ() {
        return 180;
    }

    /**
     *
     * @param f
     * @param f1
     */
    public void g(float f, float f1) {
        if (this.bM()) {
            if (this.V()) {
                this.a(f, f1, 0.1F);
                this.move(this.motX, this.motY, this.motZ);
                this.motX *= 0.8999999761581421D;
                this.motY *= 0.8999999761581421D;
                this.motZ *= 0.8999999761581421D;
                if (!this.n() && this.getGoalTarget() == null) {
                    this.motY -= 0.005D;
                }
            } else {
                super.g(f, f1);
            }
        } else {
            super.g(f, f1);
        }

    }

    static class ControllerMoveGuardian extends ControllerMove {

        private CGuardian g;

        public ControllerMoveGuardian(CGuardian entityguardian) {
            super(entityguardian);
            this.g = entityguardian;
        }

        public void c() {
            if (this.f && !this.g.getNavigation().m()) {
                double d0 = this.b - this.g.locX;
                double d1 = this.c - this.g.locY;
                double d2 = this.d - this.g.locZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                d3 = (double) MathHelper.sqrt(d3);
                d1 /= d3;
                float f = (float) (MathHelper.b(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;

                this.g.yaw = this.a(this.g.yaw, f, 30.0F);
                this.g.aI = this.g.yaw;
                float f1 = (float) (this.e * this.g.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue());

                this.g.k(this.g.bI() + (f1 - this.g.bI()) * 0.125F);
                double d4 = Math.sin((double) (this.g.ticksLived + this.g.getId()) * 0.5D) * 0.05D;
                double d5 = Math.cos((double) (this.g.yaw * 3.1415927F / 180.0F));
                double d6 = Math.sin((double) (this.g.yaw * 3.1415927F / 180.0F));

                this.g.motX += d4 * d5;
                this.g.motZ += d4 * d6;
                d4 = Math.sin((double) (this.g.ticksLived + this.g.getId()) * 0.75D) * 0.05D;
                this.g.motY += d4 * (d6 + d5) * 0.25D;
                this.g.motY += (double) this.g.bI() * d1 * 0.1D;
                ControllerLook controllerlook = this.g.getControllerLook();
                double d7 = this.g.locX + d0 / d3 * 2.0D;
                double d8 = (double) this.g.getHeadHeight() + this.g.locY + d1 / d3 * 1.0D;
                double d9 = this.g.locZ + d2 / d3 * 2.0D;
                double d10 = controllerlook.e();
                double d11 = controllerlook.f();
                double d12 = controllerlook.g();

                if (!controllerlook.b()) {
                    d10 = d7;
                    d11 = d8;
                    d12 = d9;
                }

                this.g.getControllerLook().a(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
                this.g.l(true);
            } else {
                this.g.k(0.0F);
                this.g.l(false);
            }
        }
    }
}
