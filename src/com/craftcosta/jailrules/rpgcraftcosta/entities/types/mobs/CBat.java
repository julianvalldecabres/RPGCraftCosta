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

import com.craftcosta.jailrules.rpgcraftcosta.entities.RPGMob;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGBat;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.PathFinderGoalGoHome;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
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
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import net.minecraft.server.v1_8_R3.EntityMonster;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public final class CBat extends EntityMonster implements IRangedEntity {

    //VARIABLES AÑADIDAS QUE DEFINEN EL TIPO DE MOB
    private AttackType aType;
    private MobBehaviour mType;
    //VARIBLES ATRIBUTOS DEL MOB

    /**
     *
     */
    public Location spawnLoc;
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

    //VARIABLES PROPIAS DEL ENTITYCHICKEN
    /**
     *
     */
    public float bm;

    /**
     *
     */
    public float bo;

    /**
     *
     */
    public float bp;

    /**
     *
     */
    public float bq;

    /**
     *
     */
    public float br = 1.0F;

    /**
     *
     */
    public int bs;

    /**
     *
     */
    public boolean bt;

    /**
     *
     * @param world
     */
    public CBat(World world) {
        super(world);

        setSize(0.5F, 0.9F);
        setAsleep(false);
        setCustomName("Bat");

        this.spawnLoc = new Location(world.getWorld(), 0, 0, 0);
        //tipos por defecto
        this.attackSpeed = 1.0D;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.NORMAL;

        //reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(20.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
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
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exc) {
            System.out.println("Ojo alguna variable ha cambiado y hay que revisarlas");
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    /**
     *
     * @param world
     * @param loc
     */
    public CBat(World world, Location loc) {
        super(world);
        setSize(0.5F, 0.9F);
        this.setAsleep(false);
        setCustomName("BatLoc");
        this.attackSpeed = 1.0D;
        this.spawnLoc = loc;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.AGGRESSIVE;

        //reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(20.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
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
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exc) {
            System.out.println("Ojo alguna variable ha cambiado y hay que revisarlas");
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    /**
     *
     * @param aType
     * @param mType
     * @param name
     * @param level
     * @param movSpeed
     * @param knockback
     * @param attack_damage
     * @param attackSpeed
     * @param rangedDamage
     * @param followRange
     * @param maxHealth
     * @param rangedStrength
     * @param world
     */
    public CBat(AttackType aType, MobBehaviour mType, String name, int level, double movSpeed, double knockback, double attack_damage, double attackSpeed, double rangedDamage, double followRange, double maxHealth, float rangedStrength, World world) {
        super(world);
        setSize(0.5F, 0.9F);
        setAsleep(false);
        this.aType = aType;
        this.mType = mType;
        this.name = name;
        this.level = level;
        setCustomName("[LVL" + level + "] " + name);
        this.movementspeed = movSpeed;
        this.knockback = knockback;
        this.attackdamage = attack_damage;
        this.attackSpeed = attackSpeed;
        this.rangedDamage = rangedDamage;
        this.rangedStrength = rangedStrength;
        this.followrange = followRange;
        this.maxhealth = maxHealth;

        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(followrange);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(maxhealth);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(movementspeed);
        getAttributeInstance(GenericAttributes.c).setValue(knockback);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(attackdamage);

        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exc) {
            System.out.println("Ojo alguna variable ha cambiado y hay que revisarlas");
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    public CBat(RPGMob rpgm, World world, Location loc) {
        super(world);
        setSize(0.5F, 0.9F);
        setAsleep(((RPGBat) rpgm).isAsleep());
        this.aType = rpgm.getaType();
        this.mType = rpgm.getmType();
        this.name = rpgm.getName();
        this.level = rpgm.getLevel();
        this.spawnLoc = loc;
        setCustomName("[LVL" + level + "] " + name);
        this.movementspeed = rpgm.getMovementspeed();
        this.knockback = rpgm.getKnockback();
        this.attackdamage = rpgm.getDamageattack();
        this.attackSpeed = rpgm.getAttackspeed();
        this.rangedDamage = rpgm.getRangeddamage();
        this.rangedStrength = rpgm.getRangedstrength();
        this.followrange = rpgm.getFollowrange();
        this.maxhealth = rpgm.getMaxhealth();

        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(followrange);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(maxhealth);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(movementspeed);
        getAttributeInstance(GenericAttributes.c).setValue(knockback);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(attackdamage);

        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exc) {
            System.out.println("Ojo alguna variable ha cambiado y hay que revisarlas");
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
        EntityArrow entityarrow = new EntityArrow(this.world, this, el, rangedStrength, 14 - this.world.getDifficulty().a() * 4);
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

    //METODOS PROPIOS DE ENTITYBAT
    /**
     *
     */
    protected void h() {
        super.h();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    /**
     *
     * @return
     */
    protected float bB() {
        return 0.1F;
    }

    /**
     *
     * @return
     */
    protected float bC() {
        return super.bC() * 0.95F;
    }

    /**
     *
     * @return
     */
    protected String z() {
        if ((isAsleep()) && (this.random.nextInt(4) != 0)) {
            return null;
        }
        return "mob.bat.idle";
    }

    /**
     *
     * @return
     */
    protected String bo() {
        return "mob.bat.hurt";
    }

    /**
     *
     * @return
     */
    protected String bp() {
        return "mob.bat.death";
    }

    /**
     *
     * @return
     */
    public boolean isAsleep() {
        return (this.datawatcher.getByte(16) & 0x1) != 0;
    }

    /**
     *
     * @param flag
     */
    public void setAsleep(boolean flag) {
        byte bit = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, Byte.valueOf((byte) (bit | 0x1)));
        } else {
            this.datawatcher.watch(16, Byte.valueOf((byte) (bit & 0xFFFFFFFE)));
        }
    }

    /**
     *
     */
    public void t_() {
        super.t_();
        if (isAsleep()) {
            this.motX = (this.motY = this.motZ = 0.0D);
            this.locY = (MathHelper.floor(this.locY) + 1.0D - this.length);
        } else {
            this.motY *= 0.6000000238418579D;
        }
    }

    /**
     *
     * @param nbt
     */
    @Override
    public void a(NBTTagCompound nbt) {
        super.a(nbt);
        this.datawatcher.watch(16, Byte.valueOf(nbt.getByte("BatFlags")));
    }

    /**
     *
     * @param nbt
     */
    @Override
    public void b(NBTTagCompound nbt) {
        super.b(nbt);

        nbt.setByte("BatFlags", this.datawatcher.getByte(16));
    }

    /**
     *
     * @return
     */
    @Override
    public float getHeadHeight() {
        return this.length / 2.0F;
    }
}
