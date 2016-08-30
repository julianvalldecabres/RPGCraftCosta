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
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.PathFinderGoalGoHome;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
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
public class CSquid extends EntityMonster implements IRangedEntity {

    //VARIABLES AÑADIDAS QUE DEFINEN EL TIPO DE MOB
    private AttackType aType;
    private MobBehaviour mType;
    //VARIBLES ATRIBUTOS DEL MOB

    /**
     *
     */
    public Location spawnLoc;
    private boolean baby = false;
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

    /**
     *
     * @param world
     */
    public CSquid(World world) {
        super(world);
        this.setSize(0.95F, 0.95F);
        this.spawnLoc = new Location(world.getWorld(), 0, 4, 0);
        //tipos por defecto
        this.attackSpeed = 1.0D;
        this.rangedDamage = 1.0D;
        this.rangedStrength = 1.0F;
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.AGGRESSIVE;
        //reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(6.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.300000011920929D);
        getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(1.0D);
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
        //añadimos atributos (POR REVISAR)
        //initAttributes();
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    /**
     *
     * @param world
     * @param spawnLoc
     */
    public CSquid(World world, Location spawnLoc) {
        super(world);
        this.setSize(0.95F, 0.95F);
        this.spawnLoc = spawnLoc;
        //tipos por defecto
        this.attackSpeed = 1.0D;
        this.rangedDamage = 1.0D;
        this.rangedStrength = 1.0F;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.NORMAGGRO;
        //reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(6.0D);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.300000011920929D);
        getAttributeInstance(GenericAttributes.c).setValue(1.0D);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(1.0D);
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
        //añadimos atributos (POR REVISAR)
        //initAttributes();
        //añadimos los pathfindergoals
        initPathfinderGoals();
    }

    public CSquid(RPGMob rpgm, World world, Location loc) {
        super(world);
        this.setSize(0.95F, 0.95F);
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
        EntityArrow entityarrow = new EntityArrow(this.world, this, el, 1.6F, 14 - this.world.getDifficulty().a() * 4);
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bA());
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bA());

        entityarrow.b(this.rangedDamage);

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

    //METODOS PROPIOS DEL ENTITYSQUID
    /**
     *
     * @return
     */
    public float getHeadHeight() {
        return this.length * 0.5F;
    }

    /**
     *
     * @return
     */
    protected String z() {
        return null;
    }

    /**
     *
     * @return
     */
    protected String bo() {
        return null;
    }

    /**
     *
     * @return
     */
    protected String bp() {
        return null;
    }

    /**
     *
     * @return
     */
    protected boolean s_() {
        return false;
    }

    /* CraftBukkit start - Delegate to Entity to use existing inWater value
     public boolean V() {
     return this.world.a(this.getBoundingBox().grow(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, (Entity) this);
     }
     // CraftBukkit end */
    /**
     *
     */
    public void m() {
        super.m();
    }

    /**
     *
     * @param f
     * @param f1
     */
    public void g(float f, float f1) {
        super.g(f, f1);
    }
}
