/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGBat;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGBlaze;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGCaveSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGChicken;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGCreeper;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGEnderDragon;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGEnderman;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGEndermite;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGGhast;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGGiant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGGuardian;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGHorse;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGIronGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGMagmaCube;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGMushroomCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGOcelot;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGPig;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGPigZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGRabbit;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSheep;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSilverfish;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSkeleton;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSlime;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSnowGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGSquid;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGVillager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGWitch;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGWither;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.GuardianType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.HorseType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.HorseVariant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.MobBehaviour;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.OcelotType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.RabbitType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.SheepColor;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.SkeletonType;
import com.craftcosta.jailrules.rpgcraftcosta.entities.utils.VillagerType;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jail
 */
public class RPGMobManager {

    private RPGCraftCosta plugin;
    private File mobsFile;
    private File spawnersFile;
    private FileConfiguration mobFileConfig;
    private FileConfiguration spawnerFileConfig;
    public HashMap<String, RPGMob> mobList;
    public Map<RPGChunk, Map<String, RPGSpawner>> spawnerList;

    public RPGMobManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading mobs and spawners module....");
        this.mobList = new HashMap<>();
        this.mobsFile = new File(RPGFinals.mobsFilePath);
        this.spawnersFile = new File(RPGFinals.spawnersFilePath);
        if (!mobsFile.exists()) {
            plugin.getLogger().info("Loading default mobs...");
            mobsFile.getParentFile().mkdirs();
            copy(plugin.getResource("mobs.yml"), mobsFile);
        }
        if (!spawnersFile.exists()) {
            plugin.getLogger().info("Loading default spawners...");
            spawnersFile.getParentFile().mkdirs();
            copy(plugin.getResource("spawners.yml"), spawnersFile);
        }
        loadMobs();
        loadMS();
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMobs() {
        mobFileConfig = YamlConfiguration.loadConfiguration(mobsFile);
        plugin.getLogger().info("Loading mobs...");
        String name;
        CustomEntityType type;
        int level;
        AttackType aType;
        MobBehaviour bType;
        double dammageattack;
        double movementspeed;
        double knockback;
        double followrange;
        double maxhealth;
        double attackspeed;
        double rangeddamage;
        double rangedstrength;
        int money;
        int exp;
        int ap;
        Set<String> mobs = mobFileConfig.getKeys(false);
        for (String id : mobs) {
            ConfigurationSection section = mobFileConfig.getConfigurationSection(id);
            name = section.getString("name");
            level = section.getInt("level");
            type = CustomEntityType.valueOf(section.getString("entitytype"));
            aType = AttackType.valueOf("attacktype");
            bType = MobBehaviour.valueOf("behaviour");
            dammageattack = section.getDouble("damageattack");
            movementspeed = section.getDouble("movementspeed");
            knockback = section.getDouble("knockback");
            followrange = section.getDouble("followrange");
            maxhealth = section.getDouble("maxhealth");
            attackspeed = section.getDouble("attackspeed");
            rangeddamage = section.getDouble("rangeddamage");
            rangedstrength = section.getDouble("rangedstrength");
            money = section.getInt("money");
            ap = section.getInt("ap");
            exp = section.getInt("exp");
            RPGMob rpgmob = null;
            boolean baby = false;
            boolean villager = false;
            boolean asleep = false;
            GuardianType gtype = null;
            HorseType htype = null;
            HorseVariant hvariant = null;
            OcelotType otype = null;
            RabbitType rtype = null;
            SheepColor sColor = null;
            SkeletonType stype = null;
            VillagerType vType = null;
            switch (type) {
                case BATX:
                    asleep = section.getBoolean("asleep");
                    rpgmob = new RPGBat(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, asleep);
                    break;
                case BLAZEX:
                    rpgmob = new RPGBlaze(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case CAVESPIDERX:
                    rpgmob = new RPGCaveSpider(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case CHICKENX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGChicken(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby);
                    break;
                case COWX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGCow(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby);
                    break;
                case CREEPERX:
                    rpgmob = new RPGCreeper(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case ENDERDRAGONX:
                    rpgmob = new RPGEnderDragon(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case ENDERMANX:
                    rpgmob = new RPGEnderman(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case ENDERMITEX:
                    rpgmob = new RPGEndermite(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case GHASTX:
                    rpgmob = new RPGGhast(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case GIANTX:
                    rpgmob = new RPGGiant(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case GUARDIANX:
                    gtype = GuardianType.valueOf(section.getString("guardiantype"));
                    rpgmob = new RPGGuardian(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, gtype);
                    break;
                case HORSEX:
                    hvariant = HorseVariant.valueOf(section.getString("horsevariant"));
                    htype = HorseType.valueOf(section.getString("horsetype"));
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGHorse(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby, htype, hvariant);
                    break;
                case IRONGOLEMX:
                    rpgmob = new RPGIronGolem(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case MAGMACUBEX:
                    rpgmob = new RPGMagmaCube(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case MUSHROOMCOWX:
                    rpgmob = new RPGMushroomCow(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby);
                    break;
                case OCELOTX:
                    baby = section.getBoolean("baby");
                    otype = OcelotType.valueOf(section.getString("otype"));
                    rpgmob = new RPGOcelot(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby, otype);
                    break;
                case PIGX:
                    rpgmob = new RPGPig(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby);
                    break;
                case PIGZOMBIEX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGPigZombie(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby);
                    break;
                case RABBITX:
                    baby = section.getBoolean("baby");
                    rtype = RabbitType.valueOf(section.getString("rtype"));
                    rpgmob = new RPGRabbit(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, rtype, baby);
                    break;
                case SHEEPX:
                    baby = section.getBoolean("baby");
                    sColor = SheepColor.valueOf(section.getString("scolor"));
                    rpgmob = new RPGSheep(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, sColor, baby);
                    break;
                case SILVERFISHX:
                    rpgmob = new RPGSilverfish(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case SKELETONX:
                    stype = SkeletonType.valueOf(section.getString("stype"));
                    rpgmob = new RPGSkeleton(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, stype);
                    break;
                case SLIMEX:
                    rpgmob = new RPGSlime(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case SNOWGOLEMX:
                    rpgmob = new RPGSnowGolem(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case SPIDERX:
                    rpgmob = new RPGSpider(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case SQUIDX:
                    rpgmob = new RPGSquid(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case VILLAGERX:
                    baby = section.getBoolean("baby");
                    vType = VillagerType.valueOf(section.getString("vtype"));
                    rpgmob = new RPGVillager(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, vType, baby);
                    break;
                case WITCHX:
                    rpgmob = new RPGWitch(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case WITHERX:
                    rpgmob = new RPGWither(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth);
                    break;
                case ZOMBIEX:
                    baby = section.getBoolean("baby");
                    villager = section.getBoolean("villager");
                    rpgmob = new RPGZombie(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, money, maxhealth, maxhealth, maxhealth, baby, villager);
                    break;
            }
            mobList.put(name, rpgmob);
        }
    }

    private void loadMS() {
        spawnerFileConfig = YamlConfiguration.loadConfiguration(spawnersFile);
        plugin.getLogger().info("Loading spawners...");
        World world;
        RPGChunk rpgchunk;
        RPGSpawner rpgspawner;
        int spawnerid;
        int maxmobs;
        int chunkX;
        int chunkZ;
        int posX;
        int posY;
        int posZ;
        Set<String> worlds = spawnerFileConfig.getKeys(false);
        for (String stringworld : worlds) {
            world = plugin.getServer().getWorld(stringworld);
            ConfigurationSection wsection = spawnerFileConfig.getConfigurationSection(stringworld);
            Set<String> chunks = wsection.getKeys(false);
            for (String chunk : chunks) {
                chunkX = Integer.parseInt(chunk.split("x")[0]);
                chunkZ = Integer.parseInt(chunk.split("x")[1]);
                rpgchunk = new RPGChunk(chunkX, chunkZ, world);
                ConfigurationSection csection = wsection.getConfigurationSection(chunk);
                Set<String> ids = csection.getKeys(false);
                for (String id : ids) {
                    plugin.getLogger().info(world.getName() + "." + chunk + "." + id);
                    ConfigurationSection ssection = csection.getConfigurationSection(id);
                }
            }
        }
    }

    public boolean chunkHasSpawners(Chunk chunk) {
        return spawnerList.containsKey(new RPGChunk(chunk.getX(), chunk.getZ(), chunk.getWorld()));
    }

}
