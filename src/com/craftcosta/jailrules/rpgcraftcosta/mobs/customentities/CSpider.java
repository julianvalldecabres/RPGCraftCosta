/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities;

import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.AttackType;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySpider.GroupDataSpider;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
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
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.BehaviourType;

/**
 *
 * @author jail
 */
public class CSpider extends EntityMonster implements IRangedEntity {

    private Location spawnLoc;
    private AttackType aType;
    private BehaviourType mType;

    public CSpider(World world) {
        super(world);
        System.out.println("mundo: " + world.getWorld().getName());
        this.spawnLoc = new Location(world.getWorld(), this.lastX, this.lastY, this.lastZ);
        //tipos por defecto
        this.aType = AttackType.MELEE;
        this.mType = BehaviourType.NORMAL;
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
        //añadimos atributos (POR REVISAR)
        //initAttributes();
        //añadimos los pathfindergoals
        initPathfinderGoals();
        //Añadir equipamiento
        initEquipment();
        System.out.println("comprobacion world que no sea cliente");
        if ((world != null) && (!world.isClientSide)) {
            System.out.println("Invocando n()");
            n();
        }
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
                this.targetSelector.a(1, new PathfinderGoalSpiderNearestAttackableTarget(this, EntityHuman.class));
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
                this.goalSelector.a(2, new PathfinderGoalSpiderMeleeAttack(this, EntityHuman.class, 1.2D, false));
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

    //METODOS PROPIOS DE ENTITYSPIDER
    protected NavigationAbstract b(World world) {
        return new NavigationSpider(this, world);
    }

    protected void h() {
        super.h();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    public void t_() {
        super.t_();
        if (!this.world.isClientSide) {
            a(this.positionChanged);
        }
    }

    protected String z() {
        return "mob.spider.say";
    }

    protected String bo() {
        return "mob.spider.say";
    }

    protected String bp() {
        return "mob.spider.death";
    }

    protected void a(BlockPosition blockposition, Block block) {
        makeSound("mob.spider.step", 0.15F, 1.0F);
    }

    public boolean k_() {
        return n();
    }

    public void aA() {
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }

    public boolean d(MobEffect mobeffect) {
        return mobeffect.getEffectId() == MobEffectList.POISON.id ? false : super.d(mobeffect);
    }

    public boolean n() {
        return (this.datawatcher.getByte(16) & 0x1) != 0;
    }

    public void a(boolean flag) {
        byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            b0 = (byte) (b0 | 0x1);
        } else {
            b0 = (byte) (b0 & 0xFFFFFFFE);
        }
        this.datawatcher.watch(16, Byte.valueOf(b0));
    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        Object object = super.prepare(difficultydamagescaler, groupdataentity);
        if (this.world.random.nextInt(100) == 0) {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.world);

            entityskeleton.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            entityskeleton.prepare(difficultydamagescaler, null);
            this.world.addEntity(entityskeleton, CreatureSpawnEvent.SpawnReason.JOCKEY);
            entityskeleton.mount(this);
        }
        if (object == null) {
            object = new GroupDataSpider();
            if ((this.world.getDifficulty() == EnumDifficulty.HARD) && (this.world.random.nextFloat() < 0.1F * difficultydamagescaler.c())) {
                ((GroupDataSpider) object).a(this.world.random);
            }
        }
        if ((object instanceof GroupDataSpider)) {
            int i = ((GroupDataSpider) object).a;
            if ((i > 0) && (MobEffectList.byId[i] != null)) {
                addEffect(new MobEffect(i, 2147483647));
            }
        }
        return (GroupDataEntity) object;
    }

    public float getHeadHeight() {
        return 0.65F;
    }

    static class PathfinderGoalSpiderNearestAttackableTarget<T extends EntityLiving>
            extends PathfinderGoalNearestAttackableTarget {

        public PathfinderGoalSpiderNearestAttackableTarget(CSpider entityspider, Class<T> oclass) {
            super(entityspider, oclass, true);
        }

        public boolean a() {
            float f = this.e.c(1.0F);

            return f >= 0.5F ? false : super.a();
        }
    }

    static class PathfinderGoalSpiderMeleeAttack
            extends PathfinderGoalMeleeAttack {

        public PathfinderGoalSpiderMeleeAttack(CSpider entityspider, Class<? extends Entity> oclass, double d, boolean flag) {
            super(entityspider, oclass, d, flag);
        }

        public boolean b() {
            float f = this.b.c(1.0F);
            if ((f >= 0.5F) && (this.b.bc().nextInt(100) == 0)) {
                this.b.setGoalTarget(null);
                return false;
            }
            return super.b();
        }

        protected double a(EntityLiving entityliving) {
            return 4.0F + entityliving.width;
        }
    }
}
