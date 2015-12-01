/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities;

import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.BehaviourType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.AttackType;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.AttributeRanged;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
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
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Navigation;
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
import org.bukkit.event.entity.HorseJumpEvent;

/**
 *
 * @author jail
 */
public class CHorse extends EntityMonster implements IRangedEntity {

    //VARIABLES AÑADIDAS
    private Location spawnLoc;
    private AttackType aType;
    private BehaviourType mType;
    private boolean baby;
    //VARIABLES PROPIAS DEL ENTITYCOW
    public static final IAttribute attributeJumpStrength = new AttributeRanged(null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D).a("Jump Strength").a(true);
    private int bC;
    private int bD;
    public int bm;
    public int bo;
    protected boolean bp;
    protected int bq;
    protected float br;
    private boolean bG;
    private float bH;
    private float bI;
    private float bJ;
    private float bK;
    private float bL;
    private float bM;
    private int bN;
    private String bO;

    public CHorse(World world) {
        super(world);
        setSize(1.4F, 1.6F);
        ((Navigation) getNavigation()).a(true);
        System.out.println("mundo: " + world.getWorld().getName());
        this.spawnLoc = new Location(world.getWorld(), this.lastX, this.lastY, this.lastZ);
        this.baby = true;
        //tipos por defecto
        this.setVariant(0);
        this.setType(3);
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
        System.out.println("metodo a el f");
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

    public int getAttackType() {
        System.out.println("gettype");
        switch (aType) {
            case MAGIC:
            case RANGED:
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

    //METODOS PROPIOS DE ENTITYPIG
    protected String z() {
        df();
        if ((this.random.nextInt(10) == 0) && (!bD())) {
            dh();
        }
        int i = getType();

        return (i != 1) && (i != 2) ? "mob.horse.idle" : i == 4 ? "mob.horse.skeleton.idle" : i == 3 ? "mob.horse.zombie.idle" : "mob.horse.donkey.idle";
    }

    protected String bo() {
        df();
        if (this.random.nextInt(3) == 0) {
            dh();
        }
        int i = getType();

        return (i != 1) && (i != 2) ? "mob.horse.hit" : i == 4 ? "mob.horse.skeleton.hit" : i == 3 ? "mob.horse.zombie.hit" : "mob.horse.donkey.hit";
    }

    protected String bp() {
        df();
        int i = getType();

        return (i != 1) && (i != 2) ? "mob.horse.death" : i == 4 ? "mob.horse.skeleton.death" : i == 3 ? "mob.horse.zombie.death" : "mob.horse.donkey.death";
    }

    public boolean cy() {
        return w(32);
    }

    public void t_() {
        super.t_();
        if ((this.world.isClientSide) && (this.datawatcher.a())) {
            this.datawatcher.e();
            dc();
        }
        if ((this.bC > 0) && (++this.bC > 30)) {
            this.bC = 0;
            c(128, false);
        }
        if ((!this.world.isClientSide) && (this.bD > 0) && (++this.bD > 20)) {
            this.bD = 0;
            s(false);
        }
        if ((this.bm > 0) && (++this.bm > 8)) {
            this.bm = 0;
        }
        if (this.bo > 0) {
            this.bo += 1;
            if (this.bo > 300) {
                this.bo = 0;
            }
        }
        this.bI = this.bH;
        if (cy()) {
            this.bH += (1.0F - this.bH) * 0.4F + 0.05F;
            if (this.bH > 1.0F) {
                this.bH = 1.0F;
            }
        } else {
            this.bH += (0.0F - this.bH) * 0.4F - 0.05F;
            if (this.bH < 0.0F) {
                this.bH = 0.0F;
            }
        }
        this.bK = this.bJ;
        if (cz()) {
            this.bI = (this.bH = 0.0F);
            this.bJ += (1.0F - this.bJ) * 0.4F + 0.05F;
            if (this.bJ > 1.0F) {
                this.bJ = 1.0F;
            }
        } else {
            this.bG = false;
            this.bJ += (0.8F * this.bJ * this.bJ * this.bJ - this.bJ) * 0.6F - 0.05F;
            if (this.bJ < 0.0F) {
                this.bJ = 0.0F;
            }
        }
        this.bM = this.bL;
        if (w(128)) {
            this.bL += (1.0F - this.bL) * 0.7F + 0.05F;
            if (this.bL > 1.0F) {
                this.bL = 1.0F;
            }
        } else {
            this.bL += (0.0F - this.bL) * 0.7F - 0.05F;
            if (this.bL < 0.0F) {
                this.bL = 0.0F;
            }
        }
    }

    private void df() {
        if (!this.world.isClientSide) {
            this.bC = 1;
            c(128, true);
        }
    }

    public boolean cS() {
        return (cR()) || (getType() == 2);
    }

    public void f(boolean flag) {
        c(32, flag);
    }

    public void r(boolean flag) {
        f(flag);
    }

    public void s(boolean flag) {
        if (flag) {
            r(false);
        }
        c(64, flag);
    }

    private void dh() {
        if (!this.world.isClientSide) {
            this.bD = 1;
            s(true);
        }
    }

    public void cW() {
        dh();
        String s = cH();
        if (s != null) {
            makeSound(s, bB(), bC());
        }
    }

    protected String cH() {
        df();
        dh();
        int i = getType();

        return (i != 3) && (i != 4) ? "mob.horse.donkey.angry" : (i != 1) && (i != 2) ? "mob.horse.angry" : null;
    }

    public void g(float f, float f1) {
    }

    public boolean cz() {
        return w(64);
    }

    public boolean cv() {
        return this.bp;
    }

    public double getJumpStrength() {
        return getAttributeInstance(attributeJumpStrength).getValue();
    }

    public void m(boolean flag) {
        this.bp = flag;
    }

    protected void a(BlockPosition blockposition, Block block) {
        Block.StepSound block_stepsound = block.stepSound;
        if (this.world.getType(blockposition.up()).getBlock() == Blocks.SNOW_LAYER) {
            block_stepsound = Blocks.SNOW_LAYER.stepSound;
        }
        if (!block.getMaterial().isLiquid()) {
            int i = getType();
            if ((this.passenger != null) && (i != 1) && (i != 2)) {
                this.bN += 1;
                if ((this.bN > 5) && (this.bN % 3 == 0)) {
                    makeSound("mob.horse.gallop", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
                    if ((i == 0) && (this.random.nextInt(10) == 0)) {
                        makeSound("mob.horse.breathe", block_stepsound.getVolume1() * 0.6F, block_stepsound.getVolume2());
                    }
                } else if (this.bN <= 5) {
                    makeSound("mob.horse.wood", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
                }
            } else if (block_stepsound == Block.f) {
                makeSound("mob.horse.wood", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
            } else {
                makeSound("mob.horse.soft", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
            }
        }
    }

    public int bV() {
        return 6;
    }

    protected float bB() {
        return 0.8F;
    }

    public int w() {
        return 400;
    }

    private void dc() {
        this.bO = null;
    }

    public void m() {
        super.m();
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEFINED;
    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
        return groupdataentity;
    }

    private float di() {
        return 15.0F + this.random.nextInt(8) + this.random.nextInt(9);
    }

    private double dj() {
        return 0.4000000059604645D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
    }

    private double dk() {
        return (0.449999988079071D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
    }

    public static boolean a(Item item) {
        return (item == Items.IRON_HORSE_ARMOR) || (item == Items.GOLDEN_HORSE_ARMOR) || (item == Items.DIAMOND_HORSE_ARMOR);
    }

    public boolean cG() {
        return w(4);
    }

    public void v(int i) {
        if (cG()) {
            if (i < 0) {
                i = 0;
            }
            float power;
            if (i >= 90) {
                power = 1.0F;
            } else {
                power = 0.4F + 0.4F * i / 90.0F;
            }
            HorseJumpEvent event = CraftEventFactory.callHorseJumpEvent(this, power);
            if (!event.isCancelled()) {
                this.bG = true;
                dh();
                this.br = power;
            }
        }
    }

    public float getHeadHeight() {
        return this.length;
    }

    protected void h() {
        super.h();
        this.datawatcher.a(16, Integer.valueOf(0));
        this.datawatcher.a(19, Byte.valueOf((byte) 0));
        this.datawatcher.a(20, Integer.valueOf(0));
        this.datawatcher.a(21, String.valueOf(""));
        this.datawatcher.a(22, Integer.valueOf(0));
    }

    public void setType(int i) {
        this.datawatcher.watch(19, Byte.valueOf((byte) i));
        dc();
    }

    public int getType() {
        return this.datawatcher.getByte(19);
    }

    public void setVariant(int i) {
        this.datawatcher.watch(20, Integer.valueOf(i));
        dc();
    }

    public int getVariant() {
        return this.datawatcher.getInt(20);
    }

    public String getName() {
        if (hasCustomName()) {
            return getCustomName();
        }
        int i = getType();
        switch (i) {
            case 0:
                return LocaleI18n.get("entity.horse.name");
            case 1:
                return LocaleI18n.get("entity.donkey.name");
            case 2:
                return LocaleI18n.get("entity.mule.name");
            case 3:
                return LocaleI18n.get("entity.zombiehorse.name");
            default:
                return LocaleI18n.get("entity.skeletonhorse.name");
        }

    }

    private boolean w(int i) {
        System.out.println(this.datawatcher.getInt(16));
        return (this.datawatcher.getInt(16) & i) != 0;
    }

    private void c(int i, boolean flag) {
        int j = this.datawatcher.getInt(16);
        if (flag) {
            this.datawatcher.watch(16, Integer.valueOf(j | i));
        } else {
            this.datawatcher.watch(16, Integer.valueOf(j & (i ^ 0xFFFFFFFF)));
        }
    }

    public boolean cp() {
        return !isBaby();
    }

    public float cu() {
        return 0.5F;
    }

    public void a(boolean flag) {
        if (flag) {
            a(cu());
        } else {
            a(1.0F);
        }
    }

    public int getTemper() {
        return this.bq;
    }

    public void setTemper(int i) {
        this.bq = i;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
//        setType(nbttagcompound.getInt("Type"));
//        setVariant(nbttagcompound.getInt("Variant"));
//        setTemper(nbttagcompound.getInt("Temper"));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
//        nbttagcompound.setInt("Type", getType());
//        nbttagcompound.setInt("Variant", getVariant());
//        nbttagcompound.setInt("Temper", getTemper());
    }

    public void e(float f, float f1) {
        if (f > 1.0F) {
            makeSound("mob.horse.land", 0.4F, 1.0F);
        }
        int i = MathHelper.f((f * 0.5F - 3.0F) * f1);
        if (i > 0) {
            damageEntity(DamageSource.FALL, i);
            Block block = this.world.getType(new BlockPosition(this.locX, this.locY - 0.2D - this.lastYaw, this.locZ)).getBlock();
            if ((block.getMaterial() != Material.AIR) && (!R())) {
                Block.StepSound block_stepsound = block.stepSound;

                this.world.makeSound(this, block_stepsound.getStepSound(), block_stepsound.getVolume1() * 0.5F, block_stepsound.getVolume2() * 0.75F);
            }
        }
    }

    public boolean cR() {
        int i = getType();

        return (i == 3) || (i == 4);
    }

    public boolean a(EntityHuman entityhuman) {
        return super.a(entityhuman);
    }

    //MONTAR
//    private void i(EntityHuman entityhuman) {
//        entityhuman.yaw = this.yaw;
//        entityhuman.pitch = this.pitch;
//        r(false);
//        s(false);
//        if (!this.world.isClientSide) {
//            entityhuman.mount(this);
//        }
//    }
    public boolean cO() {
        return getType() == 0;
    }

    public boolean cP() {
        int i = getType();

        return (i == 2) || (i == 1);
    }

    public static class GroupDataHorseX
            implements GroupDataEntity {

        public int a;
        public int b;

        public GroupDataHorseX(int i, int j) {
            this.a = i;
            this.b = j;
        }
    }
}
