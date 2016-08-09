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
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
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
public class CEndermite extends EntityMonster implements IRangedEntity {

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

    //VARIABLES PROPIAS DEL ENTITYENDERMITE
    private int a = 0;
    private boolean b = false;

    /**
     *
     * @param world
     */
    public CEndermite(World world) {
        super(world);
        this.b_ = 3;
        setSize(0.4F, 0.3F);
        this.spawnLoc = new Location(world.getWorld(), 0, 0, 0);
        //tipos por defecto
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.AGGRESSIVE;
        this.attackSpeed = 1.0D;
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
    public CEndermite(World world, Location spawnLoc) {
        super(world);
        this.spawnLoc = spawnLoc;
        this.b_ = 3;
        setSize(0.4F, 0.3F);
        //tipos por defecto
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.AGGRESSIVE;
        this.attackSpeed = 1.0D;
        this.rangedDamage = 10.0D;
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
        //Para que nuestras entidades puedan realizar ataques a distancia hay que reescribir el metodo avoid a(EntityLiving el, float f)
        //que define que tipo de ataque a distancia realiza
        //Por simplicidad lo hemos dejado tal cual esta definido en el mob skeleton
        //Solo se modificará el daño realizado por la entidad arrow
        //Se puede modificar la fuerza con la que es disparada
        EntityArrow entityarrow = new EntityArrow(this.world, this, el, rangedStrenght, 14 - this.world.getDifficulty().a() * 4);
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bA());
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bA());

        entityarrow.b(rangedDamage);
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

    //METODOS PROPIOS DEL ENTITYENDERMITE

    /**
     *
     * @return
     */
        protected String z() {
        return "mob.silverfish.say";
    }

    /**
     *
     * @return
     */
    protected String bn() {
        return "mob.silverfish.hit";
    }

    /**
     *
     * @return
     */
    protected String bo() {
        return "mob.silverfish.kill";
    }

    /**
     *
     * @param blockposition
     * @param block
     */
    protected void a(BlockPosition blockposition, Block block) {
        this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
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
        return 0.5F;
    }

    /**
     *
     */
    public void t_() {
        this.aI = this.yaw;
        super.t_();
    }

    /**
     *
     * @return
     */
    public boolean n() {
        return this.b;
    }

    /**
     *
     * @param flag
     */
    public void a(boolean flag) {
        this.b = flag;
    }

    /**
     *
     */
    public void m() {
        super.m();
        if (this.world.isClientSide) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(EnumParticle.PORTAL, this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        } else {
            if (!this.isPersistent()) {
                ++this.a;
            }

            if (this.a >= 2400) {
                this.die();
            }
        }

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
    public boolean bR() {
        if (super.bR()) {
            EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0D);

            return entityhuman == null;
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }
}
