/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities;

import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.BehaviourType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.AttackType;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ControllerJump;
import net.minecraft.server.v1_8_R3.ControllerMove;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
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
public class CRabbit extends EntityMonster implements IRangedEntity {

    //VARIABLES AÑADIDAS
    private Location spawnLoc;
    private AttackType aType;
    private BehaviourType mType;
    private boolean baby;
    //VARIABLES PROPIAS DEL ENTITYRABBIT
    private int bo = 0;
    private int bp = 0;
    private boolean bq = false;
    private boolean br = false;
    private int bs = 0;
    private EnumRabbitState bt;
    private int bu;
    private EntityHuman bv;

    public CRabbit(World world) {
        super(world);
        
        this.bt = EnumRabbitState.HOP;
        this.bu = 0;
        this.bv = null;
        setSize(0.6F, 0.7F);
        this.g = new ControllerJumpRabbit(this);
        this.moveController = new ControllerMoveRabbit(this);
        ((Navigation) getNavigation()).a(true);

        System.out.println("mundo: " + world.getWorld().getName());
        this.spawnLoc = new Location(world.getWorld(), this.lastX, this.lastY, this.lastZ);
        this.baby = true;
        //tipos por defecto
        this.aType = AttackType.MELEE;
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

    //METODOS PROPIOS DE ENTITYRABBIT
    protected float bE() {
        return (this.moveController.a()) && (this.moveController.e() > this.locY + 0.5D) ? 0.5F : this.bt.b();
    }

    public void a(EnumRabbitState entityrabbit_enumrabbitstate) {
        this.bt = entityrabbit_enumrabbitstate;
    }

    public void b(double d0) {
        getNavigation().a(d0);
        this.moveController.a(this.moveController.d(), this.moveController.e(), this.moveController.f(), d0);
    }

    public void a(boolean flag, EnumRabbitState entityrabbit_enumrabbitstate) {
        super.i(flag);
        if (!flag) {
            if (this.bt == EnumRabbitState.ATTACK) {
                this.bt = EnumRabbitState.HOP;
            }
        } else {
            b(1.5D * entityrabbit_enumrabbitstate.a());
            makeSound(cm(), bB(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
        this.bq = flag;
    }

    public void b(EnumRabbitState entityrabbit_enumrabbitstate) {
        a(true, entityrabbit_enumrabbitstate);
        this.bp = entityrabbit_enumrabbitstate.d();
        this.bo = 0;
    }

    public boolean cl() {
        return this.bq;
    }

    protected void h() {
        super.h();
        this.datawatcher.a(18, Byte.valueOf((byte) 0));
    }

    public void E() {
        if (this.moveController.b() > 0.8D) {
            a(EnumRabbitState.SPRINT);
        } else if (this.bt != EnumRabbitState.ATTACK) {
            a(EnumRabbitState.HOP);
        }
        if (this.bs > 0) {
            this.bs -= 1;
        }
        if (this.bu > 0) {
            this.bu -= this.random.nextInt(3);
            if (this.bu < 0) {
                this.bu = 0;
            }
        }
        if (this.onGround) {
            if (!this.br) {
                a(false, EnumRabbitState.NONE);
                cw();
            }
            if ((getRabbitType() == 99) && (this.bs == 0)) {
                EntityLiving entityliving = getGoalTarget();
                if ((entityliving != null) && (h(entityliving) < 16.0D)) {
                    a(entityliving.locX, entityliving.locZ);
                    this.moveController.a(entityliving.locX, entityliving.locY, entityliving.locZ, this.moveController.b());
                    b(EnumRabbitState.ATTACK);
                    this.br = true;
                }
            }
            ControllerJumpRabbit entityrabbit_controllerjumprabbit = (ControllerJumpRabbit) this.g;
            if (!entityrabbit_controllerjumprabbit.c()) {
                if ((this.moveController.a()) && (this.bs == 0)) {
                    PathEntity pathentity = this.navigation.j();
                    Vec3D vec3d = new Vec3D(this.moveController.d(), this.moveController.e(), this.moveController.f());
                    if ((pathentity != null) && (pathentity.e() < pathentity.d())) {
                        vec3d = pathentity.a(this);
                    }
                    a(vec3d.a, vec3d.c);
                    b(this.bt);
                }
            } else if (!entityrabbit_controllerjumprabbit.d()) {
                ct();
            }
        }
        this.br = this.onGround;
    }

    public void Y() {
    }

    private void a(double d0, double d1) {
        this.yaw = ((float) (MathHelper.b(d1 - this.locZ, d0 - this.locX) * 180.0D / 3.141592741012573D) - 90.0F);
    }

    private void ct() {
        ((ControllerJumpRabbit) this.g).a(true);
    }

    private void cu() {
        ((ControllerJumpRabbit) this.g).a(false);
    }

    private void cv() {
        this.bs = co();
    }

    private void cw() {
        cv();
        cu();
    }

    protected String cm() {
        return "mob.rabbit.hop";
    }

    protected String z() {
        return "mob.rabbit.idle";
    }

    protected String bo() {
        return "mob.rabbit.hurt";
    }

    protected String bp() {
        return "mob.rabbit.death";
    }

    public boolean r(Entity entity) {
        makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
    }

    public int br() {
        return getRabbitType() == 99 ? 8 : super.br();
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        return isInvulnerable(damagesource) ? false : super.damageEntity(damagesource, f);
    }

    public void m() {
        super.m();
        if (this.bo != this.bp) {
            if ((this.bo == 0) && (!this.world.isClientSide)) {
                this.world.broadcastEntityEffect(this, (byte) 1);
            }
            this.bo += 1;
        } else if (this.bp != 0) {
            this.bo = 0;
            this.bp = 0;
        }
    }

    public int getRabbitType() {
        return this.datawatcher.getByte(18);
    }

    public void setRabbitType(int i) {
        this.datawatcher.watch(18, Byte.valueOf((byte) i));
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEFINED;
    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        Object object = super.prepare(difficultydamagescaler, groupdataentity);
        int i = this.random.nextInt(6);
        boolean flag = false;
        if ((object instanceof GroupDataRabbit)) {
            i = ((GroupDataRabbit) object).a;
            flag = true;
        } else {
            object = new GroupDataRabbit(i);
        }
        setRabbitType(i);
        if (flag) {
            this.datawatcher.watch(12, Byte.valueOf((byte) MathHelper.clamp(i, -1, 1)));
        }
        return (GroupDataEntity) object;
    }

    private boolean cx() {
        return this.bu == 0;
    }

    protected int co() {
        return this.bt.c();
    }

    protected void cp() {
        this.world.addParticle(EnumParticle.BLOCK_DUST, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getCombinedId(Blocks.CARROTS.fromLegacyData(7))});
        this.bu = 100;
    }

    public float getHeadHeight() {
        return this.length;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        setRabbitType(nbttagcompound.getInt("RabbitType"));
        this.bu = nbttagcompound.getInt("MoreCarrotTicks");
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("RabbitType", getRabbitType());
        nbttagcompound.setInt("MoreCarrotTicks", this.bu);
    }

    public void e(float f, float f1) {
    }

    public void al() {
        super.al();
        float f = MathHelper.sin(this.aI * 3.141593F / 180.0F);
        float f1 = MathHelper.cos(this.aI * 3.141593F / 180.0F);
        float f2 = 0.1F;
        float f3 = 0.0F;

        this.passenger.setPosition(this.locX + f2 * f, this.locY + this.length * 0.5F + this.passenger.am() + f3, this.locZ - f2 * f1);
        if ((this.passenger instanceof EntityLiving)) {
            ((EntityLiving) this.passenger).aI = this.aI;
        }
    }

    static enum EnumRabbitState {

        NONE(0.0F, 0.0F, 30, 1), HOP(0.8F, 0.2F, 20, 10), STEP(1.0F, 0.45F, 14, 14), SPRINT(1.75F, 0.4F, 1, 8), ATTACK(2.0F, 0.7F, 7, 8);

        private final float f;
        private final float g;
        private final int h;
        private final int i;

        private EnumRabbitState(float f, float f1, int i, int j) {
            this.f = f;
            this.g = f1;
            this.h = i;
            this.i = j;
        }

        public float a() {
            return this.f;
        }

        public float b() {
            return this.g;
        }

        public int c() {
            return this.h;
        }

        public int d() {
            return this.i;
        }
    }

    static class PathfinderGoalKillerRabbitMeleeAttack
            extends PathfinderGoalMeleeAttack {

        public PathfinderGoalKillerRabbitMeleeAttack(CRabbit entityrabbit) {
            super(entityrabbit, EntityLiving.class, 1.4D, true);
        }

        protected double a(EntityLiving entityliving) {
            return 4.0F + entityliving.width;
        }
    }

    static class ControllerMoveRabbit
            extends ControllerMove {

        private CRabbit g;

        public ControllerMoveRabbit(CRabbit entityrabbit) {
            super(entityrabbit);
            this.g = entityrabbit;
        }

        public void c() {
            if ((this.g.onGround) && (!this.g.cl())) {
                this.g.b(0.0D);
            }
            super.c();
        }
    }

    public class ControllerJumpRabbit
            extends ControllerJump {

        private CRabbit c;
        private boolean d = false;

        public ControllerJumpRabbit(CRabbit entityrabbit) {
            super(entityrabbit);
            this.c = entityrabbit;
        }

        public boolean c() {
            return this.a;
        }

        public boolean d() {
            return this.d;
        }

        public void a(boolean flag) {
            this.d = flag;
        }

        public void b() {
            if (this.a) {
                this.c.b(CRabbit.EnumRabbitState.STEP);
                this.a = false;
            }
        }
    }

    public static class GroupDataRabbit
            implements GroupDataEntity {

        public int a;

        public GroupDataRabbit(int i) {
            this.a = i;
        }
    }

}
