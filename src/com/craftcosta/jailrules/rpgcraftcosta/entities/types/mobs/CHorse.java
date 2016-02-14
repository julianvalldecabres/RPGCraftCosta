/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs;

import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.HorseType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.HorseVariant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.PathFinderGoalGoHome;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
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

/**
 *
 * @author jail
 */
public class CHorse extends EntityMonster implements IRangedEntity {

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
    private double maxhealth;
    private HorseVariant hVariant;
    private HorseType hType;
    //VARIABLES PROPIAS DEL ENTITYHORSE
    

    public CHorse(World world) {
        super(world);
        setSize(1.4F, 1.6F);
        ((Navigation) getNavigation()).a(true);
        this.spawnLoc = new Location(world.getWorld(),0, 0, 0);
        this.baby = true;
        //tipos por defecto
        this.hVariant=HorseVariant.WHITE;
        this.hType=HorseType.ZOMBIE;
                
        this.setVariant(0);
        this.setType(3);
        this.rangedStrenght = 1.6F;
        this.rangedDamage=20.0D;
        this.aType = AttackType.RANGED;
        this.mType = MobBehaviour.NORMAL;
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
    }
    
    public CHorse(World world,Location spawnloc) {
        super(world);
        setSize(1.4F, 1.6F);
        ((Navigation) getNavigation()).a(true);
        this.spawnLoc = spawnloc;
        this.baby = false;
        //tipos por defecto
        this.hVariant=HorseVariant.WHITE;
        this.hType=HorseType.ZOMBIE;
                
        this.setVariant(0);
        this.setType(3);
        this.attackSpeed=1.0D;
        this.rangedStrenght = 1.6F;
        this.rangedDamage=20.0D;
        this.aType = AttackType.MELEE;
        this.mType = MobBehaviour.NORMAL;
        this.setBaby(baby);
//reseteamos los pathfinders
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(12.0D);
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

    public CHorse(AttackType aType, MobBehaviour mType, boolean baby, String name, int level, double movementspeed, double knockback, double attackdamage, double attackSpeed, double rangedDamage, float rangedStrenght, double followrange, double maxhealth, World world) {
        super(world);
        this.aType = aType;
        this.mType = mType;
        this.baby = baby;
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

        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(this.followrange);
        getAttributeInstance(GenericAttributes.maxHealth).setValue(this.maxhealth);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.movementspeed);
        getAttributeInstance(GenericAttributes.c).setValue(this.knockback);
        getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(this.attackdamage);
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
        this.goalSelector.a(5,new PathFinderGoalGoHome(1.0D, this, spawnLoc));
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
                this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.attackSpeed, 60, 10.0F));
                break;
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

    public int getAttackType() {
        switch (aType) {
            case MAGIC:
            case RANGED:
                return 1;
            default:
                return 0;
        }
    }

    private void setBaby(boolean baby) {
        if (this.baby) {
            this.datawatcher.a(12, new Byte((byte) -1));
        } else {
            this.datawatcher.a(12, new Byte((byte) 1));
        }
    }

    //METODOS PROPIOS DE ENTITYHORSE
    protected String z() {
        int i = getType();

        return (i != 1) && (i != 2) ? "mob.horse.idle" : i == 4 ? "mob.horse.skeleton.idle" : i == 3 ? "mob.horse.zombie.idle" : "mob.horse.donkey.idle";
    }

    protected String bo() {
        int i = getType();

        return (i != 1) && (i != 2) ? "mob.horse.hit" : i == 4 ? "mob.horse.skeleton.hit" : i == 3 ? "mob.horse.zombie.hit" : "mob.horse.donkey.hit";
    }

    protected String bp() {
        int i = getType();

        return (i != 1) && (i != 2) ? "mob.horse.death" : i == 4 ? "mob.horse.skeleton.death" : i == 3 ? "mob.horse.zombie.death" : "mob.horse.donkey.death";
    }

    public boolean cy() {
        return w(32);
    }

    public void t_() {
        super.t_();
    }

    private void df() {
            c(128, true);
    }

    //devuelve true si es un caballo zombie esqueleto o mula
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
            s(true);
    }

    public void cW() {
        String s = cH();
        if (s != null) {
            makeSound(s, bB(), bC());
        }
    }

    //Devuelve el string sonido del tipo de caballo enfadado
    protected String cH() {
        int i = getType();
        return (i != 3) && (i != 4) ? "mob.horse.donkey.angry" : (i != 1) && (i != 2) ? "mob.horse.angry" : null;
    }

    //CONTROLA EL MOVIMIENTO
    public void g(float f, float f1) {
        super.g(f,f1);
    }

    
    public boolean cz() {
        return w(64);
    }

    //Sonidos del caballo al andar
    protected void a(BlockPosition blockposition, Block block) {
        Block.StepSound block_stepsound = block.stepSound;
        if (this.world.getType(blockposition.up()).getBlock() == Blocks.SNOW_LAYER) {
            block_stepsound = Blocks.SNOW_LAYER.stepSound;
        }
        if (!block.getMaterial().isLiquid()) {
            makeSound("mob.horse.soft", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
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
    
    //metodo que acciona el salto del caballo dependiendo de la fuerza que imprima el jugador
    public void v(int i) {
    }

    public float getHeadHeight() {
        return this.length;
    }

    //inicializa el data de la entityhorse
    protected void h() {
        super.h();
        this.datawatcher.a(16, Integer.valueOf(0));
        this.datawatcher.a(19, Byte.valueOf((byte) 0));
        this.datawatcher.a(20, Integer.valueOf(0));
        this.datawatcher.a(21, String.valueOf(""));
        this.datawatcher.a(22, Integer.valueOf(0));
    }

    //establece el tipo de caballo del tipo i
    public void setType(int i) {
        this.datawatcher.watch(19, Byte.valueOf((byte) hType.getNumber()));
    }
    
    //consulta el tipo de caballo
    public int getType() {
        return this.datawatcher.getByte(19);
    }

    //establece la variante dentro del tipo del caballo
    public void setVariant(int i) {
        this.datawatcher.watch(20, Integer.valueOf(hVariant.getNumber()));
    }

    //consulta la variante de caballo
    public int getVariant() {
        return this.datawatcher.getInt(20);
    }

    //obtiene el nombre si tiene uno propio o el heredado del tipo de caballo
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

    //true si el caballo es un potro
    private boolean w(int i) {
        return (this.datawatcher.getInt(16) & i) != 0;
    }

    //establece la edad del caballo 
    private void c(int i, boolean flag) {
        int j = this.datawatcher.getInt(16);
        if (flag) {
            this.datawatcher.watch(16, Integer.valueOf(j | i));
        } else {
            this.datawatcher.watch(16, Integer.valueOf(j & (i ^ 0xFFFFFFFF)));
        }
    }

    //true si el caballo no es un potro
    public boolean cp() {
        return !isBaby();
    }

    //aun por determinar puede ser la altura de la cabeza suponiendo que sea un potro
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

    
    //metodo que añade sonido al salto del caballo, dependiendo del tipo de bloque que pise
    public void e(float f, float f1) {
        if (f > 1.0F) {
            makeSound("mob.horse.land", 0.4F, 1.0F);
        }
        int i = MathHelper.f((f * 0.5F - 3.0F) * f1);
        if (i > 0) {            
            Block block = this.world.getType(new BlockPosition(this.locX, this.locY - 0.2D - this.lastYaw, this.locZ)).getBlock();
            if ((block.getMaterial() != Material.AIR) && (!R())) {
                Block.StepSound block_stepsound = block.stepSound;
                this.world.makeSound(this, block_stepsound.getStepSound(), block_stepsound.getVolume1() * 0.5F, block_stepsound.getVolume2() * 0.75F);
            }
        }
    }

    //devuelve true si el caballo es de tipo zombie o esqueleto
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
    
    //devuelve true si es un caballo normal
    public boolean cO() {
        return getType() == 0;
    }

    //devuelve true si es un burro o asno
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
