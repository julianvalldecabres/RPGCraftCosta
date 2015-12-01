/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities;

import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.BehaviourType;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
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
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.AttackType;

/**
 *
 * @author jail
 */
public class CBat extends EntityMonster implements IRangedEntity {

    //VARIABLES AÑADIDAS QUE DEFINEN EL TIPO DE MOB
    private Location spawnLoc;
    private AttackType aType;
    private BehaviourType mType;
    private boolean baby;
    //VARIBLES ATRIBUTOS DEL MOB
    private String displayName;
    private int level;
    private double movSpeed;
    private double knockback;
    private double damage;
    private double attackSpeed;
    private double rangedDamage;
    private double followRange;
    private double maxHealth;
    //VARIABLES AÑADIDAS QUE DEFINEN LO QUE SE OBTIENE DE ELLOS
    private ItemStack[] drops;
    private int experience;
    private int ap;
    private int money;
    
    //VARIABLES PROPIAS DEL ENTITYCHICKEN
    public float bm;
    public float bo;
    public float bp;
    public float bq;
    public float br = 1.0F;
    public int bs;
    public boolean bt;

    public CBat(World world) {
        super(world);
        
        setSize(0.5F, 0.9F);
        setAsleep(true);
        System.out.println("mundo: " + world.getWorld().getName());
        this.spawnLoc = new Location(world.getWorld(), this.lastX, this.lastY, this.lastZ);
        //this.baby = true;
        //tipos por defecto
        this.aType = AttackType.RANGED;
        this.mType = BehaviourType.NORMAL;
        this.setBaby(baby);
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
        //Añadir equipamiento
        initEquipment();
        System.out.println("comprobacion world que no sea cliente");
    }

    //Dependiente de la naturaleza del mob
    private void initEquipment() {
        switch (aType) {
            case RANGED:
                System.out.println("equipo ranged");
                setEquipment(0, new ItemStack(Items.BOW));
                break;
            case MAGIC:
                System.out.println("equipo magic");
                setEquipment(0, new ItemStack(Items.POISONOUS_POTATO));
                break;
            default:
                System.out.println("equipo melee");
                setEquipment(0, new ItemStack(Items.STONE_SWORD));
                break;
        }
    }

    private void initPathfinderGoals() {
        //default PfG
        System.out.println("Iniciado pathfindergoals");
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        //mobType PfG
        switch (mType) {
            case AGGREAGGRO:
                System.out.println("Iniciado pathfindergoals aggressive aggro");
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, true));
                this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
                break;
            case AGGRESSIVE:
                System.out.println("Iniciado pathfindergoals aggressive");
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, false));
                this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, false));
                break;
            case NORMAGGRO:
                System.out.println("Iniciado pathfindergoals normal aggro");
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, true));
                break;
            case NORMAL:
                System.out.println("Iniciado pathfindergoals normal");
                this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
                this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, true));
                this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));

                this.targetSelector.a(0, new PathfinderGoalHurtByTarget(this, false));
                break;
            default:
                //peaceful
                System.out.println("Iniciado pathfindergoals peaceful");
                this.goalSelector.a(5, new PathfinderGoalPanic(this, 2.0D));

                this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
                break;
        }
        //attackType PfG
        switch (aType) {
            case MAGIC:
                System.out.println("Iniciado pathfindergoals magic");
                this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 60, 10.0F));
                break;
            case RANGED:
                System.out.println("Iniciado pathfindergoals ranged");
                this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F));
                break;
            default:
                //MELEE
                System.out.println("Iniciado pathfindergoals melee");
                this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false));
                break;
        }
    }

    //Override de IRangedEntity
    @Override
    public void a(EntityLiving el, float f) {
        //Para que nuestras entidades puedan realizar ataques a distancia hay que reescribir el metodo a
        //que define que tipo de ataque a distancia realiza
        //Por simplicidad lo hemos dejado tal cual esta definido en el mob skeleton
        //Solo se modificará el daño realizado por la entidad arrow
        //Se puede modificar la fuerza con la que es disparada
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

    public int getType() {
        System.out.println("gettype");
        switch (aType) {
            case MAGIC:
            case MELEE:
                return 1;
            default:
                return 0;
        }
    }

    private void setBaby(boolean baby) {
        if (baby) {
            this.datawatcher.a(12, new Byte((byte) -1));
        } else {
            this.datawatcher.a(12, new Byte((byte) 1));
        }
    }

    //METODOS PROPIOS DE ENTITYBAT
    protected void h() {
        super.h();

        this.datawatcher.a(16, new Byte((byte) 0));
    }

    protected float bB() {
        return 0.1F;
    }

    protected float bC() {
        return super.bC() * 0.95F;
    }

    protected String z() {
        if ((isAsleep()) && (this.random.nextInt(4) != 0)) {
            return null;
        }
        return "mob.bat.idle";
    }

    protected String bo() {
        return "mob.bat.hurt";
    }

    protected String bp() {
        return "mob.bat.death";
    }

    public boolean isAsleep() {
        return (this.datawatcher.getByte(16) & 0x1) != 0;
    }

    public void setAsleep(boolean flag) {
        byte bit = this.datawatcher.getByte(16);
        if (flag) {
            this.datawatcher.watch(16, Byte.valueOf((byte) (bit | 0x1)));
        } else {
            this.datawatcher.watch(16, Byte.valueOf((byte) (bit & 0xFFFFFFFE)));
        }
    }

    public void t_() {
        super.t_();
        if (isAsleep()) {
            this.motX = (this.motY = this.motZ = 0.0D);
            this.locY = (MathHelper.floor(this.locY) + 1.0D - this.length);
        } else {
            this.motY *= 0.6000000238418579D;
        }
    }

    public void a(NBTTagCompound nbt) {
        super.a(nbt);

        this.datawatcher.watch(16, Byte.valueOf(nbt.getByte("BatFlags")));
    }

    public void b(NBTTagCompound nbt) {
        super.b(nbt);

        nbt.setByte("BatFlags", this.datawatcher.getByte(16));
    }

    public float getHeadHeight() {
        return this.length / 2.0F;
    }
}
