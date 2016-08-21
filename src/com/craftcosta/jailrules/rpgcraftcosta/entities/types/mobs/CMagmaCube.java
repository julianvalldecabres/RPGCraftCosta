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
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IRangedEntity;
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
public class CMagmaCube extends EntityMonster implements IRangedEntity {

    /**
     *
     */
    public float a;

    /**
     *
     */
    public float b;

    /**
     *
     */
    public float c;
    private boolean bi;
    //VARIABLES AÑADIDAS
    private Location spawnLoc;
    private AttackType aType;
    private MobBehaviour mType;
    private String name;
    private int level;
    private double movementspeed;
    private double knockback;
    private double attackdamage;
    private double attackSpeed;
    private double rangedDamage;
    private float rangedStrenght;
    private double followrange;
    private double maxhealth;

    /**
     *
     * @param world
     */
    public CMagmaCube(World world) {
        super(world);

        this.spawnLoc = new Location(world.getWorld(), 0, 0, 0);
        this.rangedStrenght = 1.6F;
        this.rangedDamage = 20.0D;
        this.attackSpeed = 1.0D;
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.NORMAL;
        //reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(6.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.300000011920929D);
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
     * @param spawnloc
     */
    public CMagmaCube(World world,Location spawnloc) {
        super(world);

        this.spawnLoc = spawnloc;
        this.attackSpeed = 1.0D;
        this.rangedStrenght = 1.6F;
        this.rangedDamage = 1.0D;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.AGGRESSIVE;
        //reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(6.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.300000011920929D);
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
        //default PfG
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathFinderGoalGoHome(1.0D, this, spawnLoc));
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
                this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.attackSpeed, 20, 60, 15.0F));
                break;
            default:
                //MELEE
                this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, this.attackSpeed, false));
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
        EntityArrow entityarrow = new EntityArrow(this.world, this, el, this.rangedStrenght, 14 - this.world.getDifficulty().a() * 4);
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bA());
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bA());

        entityarrow.b(rangedDamage);
        if (i > 0) {
            entityarrow.b(entityarrow.j() + i * 0.5D + 0.5D);
        }
        if (j > 0) {
            entityarrow.setKnockbackStrength(j);
        }
        if ((EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, bA()) > 0) || (getAttackType() == 1)) {
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
    public int getAttackType() {
        switch (aType) {
            case RANGED:
                return 1;
            default:
                return 0;
        }
    }
    
    //METODOS PROPIOS DEL ENTITYMAGMACUBE

    /**
     *
     * @return
     */
    
    public int br() {
        return this.getSize() * 3;
    }

    /**
     *
     * @param f
     * @return
     */
    public float c(float f) {
        return 1.0F;
    }

    /**
     *
     * @return
     */
    protected EnumParticle n() {
        return EnumParticle.FLAME;
    }

    /**
     *
     */
    protected void bF() {
        this.motY = (double) (0.42F + (float) this.getSize() * 0.1F);
        this.ai = true;
    }

    /**
     *
     */
    protected void bH() {
        this.motY = (double) (0.22F + (float) this.getSize() * 0.05F);
        this.ai = true;
    }

    /**
     *
     * @param f
     * @param f1
     */
    public void e(float f, float f1) {}

    /**
     *
     * @return
     */
    protected boolean cl() {
        return true;
    }
    
    //METODOS DEL ENTITY SLIME

    /**
     *
     */
        protected void h() {
        super.h();
        this.datawatcher.a(16, Byte.valueOf((byte) 1));
    }

    /**
     *
     * @param i
     */
    public void setSize(int i) {
        this.datawatcher.watch(16, Byte.valueOf((byte) i));
        this.a(0.51000005F * (float) i, 0.51000005F * (float) i);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.b_ = i;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return this.datawatcher.getByte(16);
    }

    /**
     *
     * @param nbttagcompound
     */
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Size", this.getSize() - 1);
    }

    /**
     *
     * @param nbttagcompound
     */
    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        int i = nbttagcompound.getInt("Size");

        if (i < 0) {
            i = 0;
        }

        this.setSize(i + 1);
    }

    /**
     *
     * @return
     */
    protected CSlime cd() {
        return new CSlime(this.world);
    }

    /**
     *
     * @param i
     */
    public void i(int i) {
        if (i == 16) {
            int j = this.getSize();

            this.a(0.51000005F * (float) j, 0.51000005F * (float) j);
            this.yaw = this.aI;
            this.aG = this.aI;
            if (this.V() && this.random.nextInt(20) == 0) {
                this.X();
            }
        }

        super.i(i);
    }

    /**
     *
     */
    public void die() {
        int i = this.getSize();

        if (!this.world.isClientSide && i > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.random.nextInt(3);

            for (int k = 0; k < j; ++k) {
                float f = ((float) (k % 2) - 0.5F) * (float) i / 4.0F;
                float f1 = ((float) (k / 2) - 0.5F) * (float) i / 4.0F;
                CSlime entityslime = this.cd();

                if (this.hasCustomName()) {
                    setCustomName(this.getCustomName());
                }

                setSize(i / 2);
                setPositionRotation(this.locX + (double) f, this.locY + 0.5D, this.locZ + (double) f1, this.random.nextFloat() * 360.0F, 0.0F);
                this.world.addEntity(this);
            }
        }

        super.die();
    }

    /**
     *
     * @param entity
     */
    public void collide(Entity entity) {
        super.collide(entity);
    }

    /**
     *
     * @param entityliving
     */
    protected void e(EntityLiving entityliving) {
        int i = this.getSize();

        
            this.makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.a((EntityLiving) this, (Entity) entityliving);
        

    }

    /**
     *
     * @return
     */
    public float getHeadHeight() {
        return 0.625F * this.length;
    }

    /**
     *
     * @return
     */
    protected String bn() {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    /**
     *
     * @return
     */
    protected String bo() {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    /**
     *
     * @param difficultydamagescaler
     * @param groupdataentity
     * @return
     */
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        int i = this.random.nextInt(3);

        if (i < 2 && this.random.nextFloat() < 0.5F * difficultydamagescaler.c()) {
            ++i;
        }

        int j = 1 << i;

        this.setSize(j);
        return super.prepare(difficultydamagescaler, groupdataentity);
    }

}
