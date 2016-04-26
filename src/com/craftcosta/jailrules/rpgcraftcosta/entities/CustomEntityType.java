package com.craftcosta.jailrules.rpgcraftcosta.entities;

/**
 *
 * @author jail
 */
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CChicken;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CGiant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSkeleton;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CPig;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CHorse;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CBat;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CBlaze;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CCaveSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CCreeper;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CEnderDragon;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CEnderman;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CEndermite;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CGhast;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CGuardian;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CIronGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CMagmaCube;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CMushroomCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.COcelot;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CPigZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CRabbit;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSheep;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSilverfish;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSlime;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSnowGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSquid;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CVillager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CWitch;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CWither;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CWolf;
import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntitySquid;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import org.bukkit.entity.EntityType;

public enum CustomEntityType {

    BATX("BatX", 65, EntityType.BAT, EntityBat.class, CBat.class),
    BLAZEX("BlazeX", 61, EntityType.BLAZE, EntityBlaze.class, CBlaze.class),
    CAVESPIDERX("CaveSpiderX", 59, EntityType.CAVE_SPIDER, EntityCaveSpider.class, CCaveSpider.class),
    CHICKENX("ChickenX", 93, EntityType.CHICKEN, EntityChicken.class, CChicken.class),
    COWX("CowX", 92, EntityType.COW, EntityCow.class, CCow.class),
    CREEPERX("CreeperX", 50, EntityType.CREEPER, EntityCreeper.class, CCreeper.class),
    /*MODIFICAR SISTEMA DE MOVIMIENTO PARA EL ENDERDRAGON*/
    ENDERDRAGONX("EnderDragonX", 63, EntityType.ENDER_DRAGON, EntityEnderDragon.class, CEnderDragon.class),
    ENDERMANX("EndermanX", 58, EntityType.ENDERMAN, EntityEnderman.class, CEnderman.class),
    ENDERMITEX("EndermiteX", 67, EntityType.ENDERMITE, EntityEndermite.class, CEndermite.class),
    /*MODIFICAR SISTEMA DE MOVIMIENTO PARA EL GHAST*/
    GHASTX("GhastX", 56, EntityType.GHAST, EntityGhast.class, CGhast.class),
    GIANTX("GiantX", 53, EntityType.GIANT, EntityGiantZombie.class, CGiant.class),
    /*MODIFICAR SISTEMA DE MOVIMIENTO PARA EL GUARDIAN*/
    GUARDIANX("GuardianX",68,EntityType.GUARDIAN,EntityGuardian.class,CGuardian.class),
    HORSEX("HorseX", 100, EntityType.HORSE, EntityHorse.class, CHorse.class),
    IRONGOLEMX("IronGolemX",99,EntityType.IRON_GOLEM,EntityIronGolem.class,CIronGolem.class),
    /*MODIFICAR SISTEMA DE MOVIMIENTO PARA EL MAGMACUBE*/
    MAGMACUBEX("MagmaCubeX",62,EntityType.MAGMA_CUBE,EntityMagmaCube.class,CMagmaCube.class),
    MUSHROOMCOWX("MushroomCowX",96,EntityType.MUSHROOM_COW,EntityMushroomCow.class,CMushroomCow.class),
    OCELOTX("OcelotX",98,EntityType.OCELOT,EntityOcelot.class,COcelot.class),
    PIGX("PigX", 90, EntityType.PIG, EntityPig.class, CPig.class),
    PIGZOMBIEX("PigZombieX",57,EntityType.PIG_ZOMBIE,EntityPigZombie.class,CPigZombie.class),
    RABBITX("RabbitX", 101, EntityType.RABBIT, EntityRabbit.class, CRabbit.class),
    SHEEPX("SheepX",91,EntityType.SHEEP,EntitySheep.class,CSheep.class),
    SILVERFISHX("SilverFishX",60,EntityType.SILVERFISH,EntitySilverfish.class,CSilverfish.class),
    SKELETONX("SkeletonX", 51, EntityType.SKELETON, EntitySkeleton.class, CSkeleton.class),
    /*MODIFICAR SISTEMA DE MOVIMIENTO PARA EL SLIME*/
    SLIMEX("SlimeX",55,EntityType.SLIME,EntitySlime.class,CSlime.class),
    SNOWGOLEMX("SnowGolemX",97,EntityType.SNOWMAN,EntitySnowman.class,CSnowGolem.class),
    SPIDERX("SpiderX", 52, EntityType.SPIDER, EntitySpider.class, CSpider.class),
    /*MODIFICAR SISTEMA DE MOVIMIENTO PARA EL SQUID*/
    SQUIDX("SquidX",94,EntityType.SQUID,EntitySquid.class,CSquid.class),
    VILLAGERX("VillagerX",120,EntityType.VILLAGER,EntityVillager.class,CVillager.class),
    WITCHX("WitchX",66,EntityType.WITCH,EntityWitch.class,CWitch.class),
    WITHERX("WitherX",64,EntityType.WITHER,EntityWither.class,CWither.class),
    WOLFX("WolfX",95,EntityType.WOLF,EntityWolf.class,CWolf.class),
    ZOMBIEX("ZombieX", 54, EntityType.ZOMBIE, EntityZombie.class, CZombie.class);
    

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;

    private CustomEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass,
            Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }

    public String getName() {
        return name;
    }

    public static Entity spawnEntity(Entity entity, Location loc) {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity);
        return entity;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass() {
        return nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass() {
        return customClass;
    }

    /**
     * Register our entities.
     */
    /*Register our entities to the server, add to onEnable()*/
    public static void registerEntities() {
        for (CustomEntityType entity : values()) /*Get our entities*/ {
            a(entity.getCustomClass(), entity.getName(), entity.getID());
        }
    }
    /*Method(add to onDisable()) to prevent server leaks when the plugin gets disabled*/

    @SuppressWarnings("rawtypes")
    public static void unregisterEntities() {
        for (CustomEntityType entity : values()) {
            try {
                ((Map) getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((Map) getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (CustomEntityType entity : values()) {
            try {
                a(entity.getNMSClass(), entity.getName(), entity.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }
    /*Set data into the entitytypes class from NMS*/

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void a(Class paramClass, String paramString, int paramInt) {
        try {
            ((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
            ((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<? extends EntityInsentient> getNmsClass() {
        return nmsClass;
    }

    public void setNmsClass(Class<? extends EntityInsentient> nmsClass) {
        this.nmsClass = nmsClass;
    }

}
