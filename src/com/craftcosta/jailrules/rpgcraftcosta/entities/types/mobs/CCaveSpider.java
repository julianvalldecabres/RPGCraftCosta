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
package com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs;

import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.PathFinderGoalGoHome;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.NavigationSpider;
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
public class CCaveSpider extends EntityMonster implements IRangedEntity {

    private AttackType aType;
    private MobBehaviour mType;
    //VARIBLES ATRIBUTOS DEL MOB
    public Location spawnLoc;
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

    public CCaveSpider(World world) {
        super(world);
        this.setSize(0.7F, 0.5F);
        this.spawnLoc = new Location(world.getWorld(), 0, 0, 0);
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
            exc.printStackTrace();
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    public CCaveSpider(World world, Location spawnLoc) {
        super(world);
        this.spawnLoc = spawnLoc;
        this.setSize(0.7F, 0.5F);

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
            exc.printStackTrace();
        }
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    public CCaveSpider(AttackType aType, MobBehaviour mType, Location spawnLoc, String name, int level, double movementspeed, double knockback, double attackdamage, double attackSpeed, double rangedDamage, float rangedStrenght, double followrange, double maxhealth, World world) {
        super(world);
        this.setSize(0.7F, 0.5F);
        this.aType = aType;
        this.mType = mType;
        this.spawnLoc = spawnLoc;
        this.name = name;
        this.level = level;
        this.movementspeed = movementspeed;
        this.knockback = knockback;
        this.attackdamage = attackdamage;
        this.attackSpeed = attackSpeed;
        this.rangedDamage = rangedDamage;
        this.rangedStrenght = rangedStrenght;
        this.followrange = followrange;
        this.maxhealth = maxhealth;

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

    public int getType() {
        switch (aType) {
            case MAGIC:
            case MELEE:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        return groupdataentity;
    }

    @Override
    public float getHeadHeight() {
        return 0.45F;
    }

    @Override
    public double an() {
        return (double) (this.length * 0.5F);
    }

    @Override
    protected NavigationAbstract b(World world) {
        return new NavigationSpider(this, world);
    }

    @Override
    protected void h() {
        super.h();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    @Override
    public void t_() {
        super.t_();
        if (!this.world.isClientSide) {
            this.a(this.positionChanged);
        }

    }

    @Override
    protected String z() {
        return "mob.spider.say";
    }

    @Override
    protected String bo() {
        return "mob.spider.say";
    }

    @Override
    protected String bp() {
        return "mob.spider.death";
    }

    @Override
    protected void a(BlockPosition blockposition, Block block) {
        this.makeSound("mob.spider.step", 0.15F, 1.0F);
    }

    @Override
    public boolean k_() {
        return this.n();
    }

    @Override
    public void aA() {
    }

    @Override
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }

    @Override
    public boolean d(MobEffect mobeffect) {
        return mobeffect.getEffectId() == MobEffectList.POISON.id ? false : super.d(mobeffect);
    }

    public boolean n() {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void a(boolean flag) {
        byte b0 = this.datawatcher.getByte(16);

        if (flag) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 &= -2;
        }

        this.datawatcher.watch(16, Byte.valueOf(b0));
    }

}
