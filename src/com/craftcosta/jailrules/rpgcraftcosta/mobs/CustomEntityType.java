package com.craftcosta.jailrules.rpgcraftcosta.mobs;

/**
 *
 * @author jail
 */
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CChicken;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CGiant;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CSkeleton;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CZombie;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CPig;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CCow;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CSkeletonWither;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CHorse;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CSpider;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CBat;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.customentities.CRabbit;
import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntityZombie;

import org.bukkit.entity.EntityType;

public enum CustomEntityType {

    BATX("BatX", 65, EntityType.BAT, EntityBat.class, CBat.class),
    HORSEX("HorseX", 100, EntityType.HORSE, EntityHorse.class, CHorse.class),
    COWX("CowX", 92, EntityType.COW, EntityCow.class, CCow.class),
    RABBITX("RabbitX", 101, EntityType.RABBIT, EntityRabbit.class, CRabbit.class),
    PIGX("PigX", 90, EntityType.PIG, EntityPig.class, CPig.class),
    GIANTX("GiantX", 53, EntityType.GIANT, EntityGiantZombie.class, CGiant.class),
    CHICKENX("ChickenX", 93, EntityType.CHICKEN, EntityChicken.class, CChicken.class),
    ZOMBIEX("ZombieX", 54, EntityType.ZOMBIE, EntityZombie.class, CZombie.class),
    SKELETONX("SkeletonX", 51, EntityType.SKELETON, EntitySkeleton.class, CSkeleton.class),
    SKELETONWX("SkeletonWX", 51, EntityType.SKELETON, EntitySkeleton.class, CSkeletonWither.class),
    SPIDERX("SpiderX", 52, EntityType.SPIDER, EntitySpider.class, CSpider.class);

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
    /*A little Util for getting a private field*/

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

}
