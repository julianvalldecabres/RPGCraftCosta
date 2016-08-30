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
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGWolf;
import com.craftcosta.jailrules.rpgcraftcosta.entities.rpgentities.RPGZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CBat;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CBlaze;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CCaveSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CChicken;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CCreeper;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CEnderDragon;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CEnderman;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CEndermite;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CGhast;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CGiant;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CGuardian;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CHorse;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CIronGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CMagmaCube;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CMushroomCow;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.COcelot;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CPig;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CPigZombie;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CRabbit;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSheep;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSilverfish;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSkeleton;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSlime;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSnowGolem;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSpider;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CSquid;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CVillager;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CWitch;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CWither;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CWolf;
import com.craftcosta.jailrules.rpgcraftcosta.entities.types.mobs.CZombie;
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
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.entities.EnumTypeDrop;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.entities.MobDrop;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Entity;

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
    public HashMap<Integer, String> mobIdList;
    public Map<RPGChunk, Map<String, RPGSpawner>> spawnerList;

    /**
     *
     * @param plugin
     */
    public RPGMobManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Loading mobs and spawners module....");
        this.mobList = new HashMap<>();
        this.mobIdList = new HashMap<>();
        this.spawnerList= new HashMap<>();
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
        long money;
        long exp;
        int ap;
        Set<String> mobs = mobFileConfig.getKeys(false);
        for (String id : mobs) {
            plugin.getLogger().info(id);
            ConfigurationSection section = mobFileConfig.getConfigurationSection(id);
            name = section.getString("name");
            level = section.getInt("level");
            plugin.getLogger().info(section.getString("entitytype"));
            type = CustomEntityType.valueOf(section.getString("entitytype"));
            aType = AttackType.valueOf(section.getString("attacktype"));
            bType = MobBehaviour.valueOf(section.getString("behaviour"));
            dammageattack = section.getDouble("damageattack");
            movementspeed = section.getDouble("movementspeed");
            knockback = section.getDouble("knockback");
            followrange = section.getDouble("followrange");
            maxhealth = section.getDouble("maxhealth");
            attackspeed = section.getDouble("attackspeed");
            rangeddamage = section.getDouble("rangeddamage");
            rangedstrength = section.getDouble("rangedstrength");
            money = section.getLong("money");
            exp = section.getLong("exp");
            String dropname;
            int dropquantity;
            EnumTypeDrop droptype;
            double dropprob;
            List<MobDrop> drops = new ArrayList<>();
            if (section.getConfigurationSection("drops") != null) {
                ConfigurationSection s2 = section.getConfigurationSection("drops");
                Set<String> dropids = s2.getKeys(false);
                for (String s : dropids) {
                    ConfigurationSection s3 = s2.getConfigurationSection(s);
                    dropname = s3.getString("name");
                    droptype = EnumTypeDrop.valueOf(s3.getString("type"));
                    dropquantity = s3.getInt("quantity");
                    dropprob = s3.getDouble("probability");
                    drops.add(new MobDrop(dropname, droptype, dropquantity, dropprob));
                }
            }
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
                    rpgmob = new RPGBat(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, asleep, drops);
                    break;
                case BLAZEX:
                    rpgmob = new RPGBlaze(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case CAVESPIDERX:
                    rpgmob = new RPGCaveSpider(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case CHICKENX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGChicken(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, drops);
                    break;
                case COWX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGCow(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, drops);
                    break;
                case CREEPERX:
                    rpgmob = new RPGCreeper(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case ENDERDRAGONX:
                    rpgmob = new RPGEnderDragon(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case ENDERMANX:
                    rpgmob = new RPGEnderman(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case ENDERMITEX:
                    rpgmob = new RPGEndermite(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case GHASTX:
                    rpgmob = new RPGGhast(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case GIANTX:
                    rpgmob = new RPGGiant(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case GUARDIANX:
                    gtype = GuardianType.valueOf(section.getString("guardiantype"));
                    rpgmob = new RPGGuardian(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, gtype, drops);
                    break;
                case HORSEX:
                    hvariant = HorseVariant.valueOf(section.getString("horsevariant"));
                    htype = HorseType.valueOf(section.getString("horsetype"));
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGHorse(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, htype, hvariant, drops);
                    break;
                case IRONGOLEMX:
                    rpgmob = new RPGIronGolem(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case MAGMACUBEX:
                    rpgmob = new RPGMagmaCube(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case MUSHROOMCOWX:
                    rpgmob = new RPGMushroomCow(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, drops);
                    break;
                case OCELOTX:
                    baby = section.getBoolean("baby");
                    otype = OcelotType.valueOf(section.getString("otype"));
                    rpgmob = new RPGOcelot(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, otype, drops);
                    break;
                case PIGX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGPig(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, drops);
                    break;
                case PIGZOMBIEX:
                    baby = section.getBoolean("baby");
                    rpgmob = new RPGPigZombie(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, drops);
                    break;
                case RABBITX:
                    baby = section.getBoolean("baby");
                    rtype = RabbitType.valueOf(section.getString("rtype"));
                    rpgmob = new RPGRabbit(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, rtype, baby, drops);
                    break;
                case SHEEPX:
                    baby = section.getBoolean("baby");
                    sColor = SheepColor.valueOf(section.getString("scolor"));
                    rpgmob = new RPGSheep(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, sColor, baby, drops);
                    break;
                case SILVERFISHX:
                    rpgmob = new RPGSilverfish(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case SKELETONX:
                    stype = SkeletonType.valueOf(section.getString("stype"));
                    rpgmob = new RPGSkeleton(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, stype, drops);
                    break;
                case SLIMEX:
                    rpgmob = new RPGSlime(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case SNOWGOLEMX:
                    rpgmob = new RPGSnowGolem(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case SPIDERX:
                    rpgmob = new RPGSpider(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case SQUIDX:
                    rpgmob = new RPGSquid(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case VILLAGERX:
                    baby = section.getBoolean("baby");
                    vType = VillagerType.valueOf(section.getString("vtype"));
                    rpgmob = new RPGVillager(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, vType, baby, drops);
                    break;
                case WITCHX:
                    rpgmob = new RPGWitch(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case WITHERX:
                    rpgmob = new RPGWither(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, drops);
                    break;
                case WOLFX:
                    rpgmob = new RPGWolf(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, drops);
                    break;
                case ZOMBIEX:
                    baby = section.getBoolean("baby");
                    villager = section.getBoolean("villager");
                    rpgmob = new RPGZombie(level, name, type, aType, bType, dammageattack, movementspeed, knockback, followrange, maxhealth, attackspeed, rangeddamage, (float) rangedstrength, money, exp, baby, villager, drops);
                    break;
            }
            mobList.put("[LVL" + level + "] " + name, rpgmob);
            mobIdList.put(Integer.parseInt(id), "[LVL" + level + "] " + name);

        }
    }

    private void loadMS() {
        spawnerFileConfig = YamlConfiguration.loadConfiguration(spawnersFile);
        plugin.getLogger().info("Loading spawners...");
        World world;
        RPGChunk rpgchunk;
        RPGSpawner rpgspawner;
        Location loc;
        int spawnerid;
        String name;
        String mobid;
        int maxmobs;
        int chunkX;
        int chunkZ;
        int radius;
        int cooldown; //every X time mob/s reapper
        double posX;
        double posY;
        double posZ;
        boolean enabled;
        Set<String> worlds = spawnerFileConfig.getKeys(false);
        for (String stringworld : worlds) {
            plugin.getLogger().info(stringworld);
            world = plugin.getServer().getWorld(stringworld);
            ConfigurationSection wsection = spawnerFileConfig.getConfigurationSection(stringworld);
            Set<String> chunks = wsection.getKeys(false);
            for (String chunk : chunks) {
                plugin.getLogger().info(chunk);

                chunkX = Integer.parseInt(chunk.split("x")[0]);
                chunkZ = Integer.parseInt(chunk.split("x")[1]);
                rpgchunk = new RPGChunk(chunkX, chunkZ, world);
                ConfigurationSection csection = wsection.getConfigurationSection(chunk);
                Set<String> ids = csection.getKeys(false);
                Map<String, RPGSpawner> spawners = new HashMap<>();
                for (String id : ids) {
                    plugin.getLogger().info(id);
                    plugin.getLogger().info(world.getName() + "." + chunk + "." + id);
                    ConfigurationSection ssection = csection.getConfigurationSection(id);
                    name = ssection.getString("name");
                    posX = ssection.getDouble("x");
                    posY = ssection.getDouble("y");
                    posZ = ssection.getDouble("z");
                    enabled = ssection.getBoolean("enabled");
                    maxmobs = ssection.getInt("maxmobs");
                    radius = ssection.getInt("radius");
                    cooldown = ssection.getInt("cooldown");
                    mobid = ssection.getString("rpgmob");
                    loc = new Location(world, posX, posY, posZ);
                    String mobname = mobIdList.get("" + mobid);
                    RPGMob mob = mobList.get(mobname);
                    spawners.put(name, new RPGSpawner(plugin,name, loc, mobid, maxmobs, radius, cooldown));
                }
                spawnerList.put(rpgchunk, spawners);
            }
        }
    }

    /**
     *
     * @param chunk
     * @return
     */
    public boolean chunkHasSpawners(RPGChunk chunk) {
        return spawnerList.containsKey(chunk);
    }

    public RPGMob getRPGMobByName(String mobName) {
        return mobList.get(mobName);
    }

    /**
     *
     * @param chunk
     * @return
     */
    public RPGChunk getRPGChunkfromChunk(Chunk chunk) {
        return new RPGChunk(chunk.getX(), chunk.getZ(), chunk.getWorld());
    }

//    void start(RPGSpawner value) {
//        plugin.getLogger().info("arrancar y asociar al spawner "+value.getId());
//        value.start();
//    }

    public Entity spawnRPGMobAtLocation(RPGMob rpgm, Location loc) {
        net.minecraft.server.v1_8_R3.World world = ((CraftWorld) loc.getWorld()).getHandle();
        Entity nmsent=null;
        switch(rpgm.getType()){
        case BATX:
            nmsent= CustomEntityType.spawnEntity(new CBat(rpgm, world, loc), loc);
            break;
        case BLAZEX:
            nmsent=CustomEntityType.spawnEntity(new CBlaze(rpgm, world, loc), loc);
            break;
        case CAVESPIDERX:
            nmsent=CustomEntityType.spawnEntity(new CCaveSpider(rpgm, world, loc), loc);
            break;
        case CHICKENX:
            nmsent=CustomEntityType.spawnEntity(new CChicken(rpgm, world, loc), loc);
            break;
        case COWX:
            nmsent=CustomEntityType.spawnEntity(new CCow(rpgm, world, loc), loc);
            break;
        case CREEPERX:
            nmsent=CustomEntityType.spawnEntity(new CCreeper(rpgm, world, loc), loc);
            break;
        case ENDERDRAGONX:
            nmsent=CustomEntityType.spawnEntity(new CEnderDragon(rpgm, world, loc), loc);
            break;
        case ENDERMANX:
            nmsent=CustomEntityType.spawnEntity(new CEnderman(rpgm, world, loc), loc);
            break;
        case ENDERMITEX:
            nmsent=CustomEntityType.spawnEntity(new CEndermite(rpgm, world, loc), loc);
            break;
        case GHASTX:
            nmsent=CustomEntityType.spawnEntity(new CGhast(rpgm, world, loc), loc);
            break;
        case GIANTX:
            nmsent=CustomEntityType.spawnEntity(new CGiant(rpgm, world, loc), loc);
            break;
        case GUARDIANX:
            nmsent=CustomEntityType.spawnEntity(new CGuardian(rpgm, world, loc), loc);
            break;
        case HORSEX:
            nmsent=CustomEntityType.spawnEntity(new CHorse(rpgm, world, loc), loc);
            break;
        case IRONGOLEMX:
            nmsent=CustomEntityType.spawnEntity(new CIronGolem(rpgm, world, loc), loc);
            break;
        case MAGMACUBEX:
            nmsent=CustomEntityType.spawnEntity(new CMagmaCube(rpgm, world, loc), loc);
            break;
        case MUSHROOMCOWX:
            nmsent=CustomEntityType.spawnEntity(new CMushroomCow(rpgm, world, loc), loc);
            break;
        case OCELOTX:
            nmsent=CustomEntityType.spawnEntity(new COcelot(rpgm, world, loc), loc);
            break;
        case PIGX:
            nmsent=CustomEntityType.spawnEntity(new CPig(rpgm, world, loc), loc);
            break;
        case PIGZOMBIEX:
            nmsent=CustomEntityType.spawnEntity(new CPigZombie(rpgm, world, loc), loc);
            break;
        case RABBITX:
            nmsent=CustomEntityType.spawnEntity(new CRabbit(rpgm, world, loc),loc);
            break;
        case SHEEPX:
            nmsent=CustomEntityType.spawnEntity(new CSheep(rpgm, world, loc),loc);
            break;
        case SILVERFISHX:
            nmsent=CustomEntityType.spawnEntity(new CSilverfish(rpgm, world, loc),loc);
            break;
        case SKELETONX:
            nmsent=CustomEntityType.spawnEntity(new CSkeleton(rpgm, world, loc),loc);
            break;
        case SLIMEX:
            nmsent=CustomEntityType.spawnEntity(new CSlime(rpgm, world, loc),loc);
            break;
        case SNOWGOLEMX:
            nmsent=CustomEntityType.spawnEntity(new CSnowGolem(rpgm, world, loc),loc);
            break;
        case SPIDERX:
            nmsent=CustomEntityType.spawnEntity(new CSpider(rpgm, world, loc),loc);
            break;
        case SQUIDX:
            nmsent=CustomEntityType.spawnEntity(new CSquid(rpgm, world, loc),loc);
            break;
        case VILLAGERX:
            nmsent=CustomEntityType.spawnEntity(new CVillager(rpgm,world,loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
            break;
        case WITCHX:
            nmsent=CustomEntityType.spawnEntity(new CWitch(rpgm, world, loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
            break;
        case WITHERX:
            nmsent=CustomEntityType.spawnEntity(new CWither(rpgm, world, loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
            break;
        case WOLFX:
            nmsent=CustomEntityType.spawnEntity(new CWolf(rpgm, world, loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
            break;
        case ZOMBIEX:
            nmsent=CustomEntityType.spawnEntity(new CZombie(rpgm, world,loc), new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
            break;
            
        }
        return nmsent;
    }

    public Map<RPGChunk, Map<String, RPGSpawner>> getSpawnerList() {
        return spawnerList;
    }

    public void setSpawnerList(Map<RPGChunk, Map<String, RPGSpawner>> spawnerList) {
        this.spawnerList = spawnerList;
    }
    
}
