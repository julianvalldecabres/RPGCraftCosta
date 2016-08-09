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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;

/**
 *
 * @author jail
 */
public enum EnumEntities {

    /**
     *
     */
    BATX(RPGFinals.entitiesImagePackage+"bat.png","Bat","BatX",60),

    /**
     *
     */
    BLAZEX(RPGFinals.entitiesImagePackage+"blaze.png","Blaze","BlazeX", 61),

    /**
     *
     */
    CAVESPIDERX(RPGFinals.entitiesImagePackage+"cavespider.png","Cavespider","CaveSpiderX", 59),

    /**
     *
     */
    CHICKENX(RPGFinals.entitiesImagePackage+"chicken.png","Chicken","ChickenX", 93),

    /**
     *
     */
    COWX(RPGFinals.entitiesImagePackage+"cow.png","Cow","CowX", 92),

    /**
     *
     */
    CREEPERX(RPGFinals.entitiesImagePackage+"creeper.png","Creeper","CreeperX", 50),

    /**
     *
     */
    ENDERDRAGONX(RPGFinals.entitiesImagePackage+"enderdragon.png","Enderdragon","EnderDragonX", 63),

    /**
     *
     */
    ENDERMANX(RPGFinals.entitiesImagePackage+"endermen.png","Enderman","EndermanX", 58),

    /**
     *
     */
    ENDERMITEX(RPGFinals.entitiesImagePackage+"endermite.png","Endermite","EndermiteX", 67),

    /**
     *
     */
    GHASTX(RPGFinals.entitiesImagePackage+"ghast.png","Ghast","GhastX", 56),

    /**
     *
     */
    GIANTX(RPGFinals.entitiesImagePackage+"giantzombie.png","Giant","GiantX", 53),

    /**
     *
     */
    GUARDIANX(RPGFinals.entitiesImagePackage+"guardian.png","Guardian","GuardianX", 68),

    /**
     *
     */
    HORSEX(RPGFinals.entitiesImagePackage+"horse.png","Horse","HorseX", 100),

    /**
     *
     */
    IRONGOLEMX(RPGFinals.entitiesImagePackage+"irongolem.png","Irongolem","IronGolemX", 99),

    /**
     *
     */
    MAGMACUBEX(RPGFinals.entitiesImagePackage+"magmacube.png","Magmacube","MagmaCubeX", 62),

    /**
     *
     */
    MUSHROOMCOWX(RPGFinals.entitiesImagePackage+"mushroomcow.png","Mushroomcow","MushroomCowX", 96),

    /**
     *
     */
    OCELOTX(RPGFinals.entitiesImagePackage+"ocelot.png","Ocelot","OcelotX", 98),

    /**
     *
     */
    PIGX(RPGFinals.entitiesImagePackage+"pig.png","Pig","PigX", 90),

    /**
     *
     */
    PIGZOMBIEX(RPGFinals.entitiesImagePackage+"zombiepigmen.png","Pigzombie","PigZombieX", 57),

    /**
     *
     */
    RABBITX(RPGFinals.entitiesImagePackage+"rabbit.png","Rabbit","RabbitX", 101),

    /**
     *
     */
    SHEEPX(RPGFinals.entitiesImagePackage+"sheep.png","Sheep","SheepX", 91),

    /**
     *
     */
    SILVERFISHX(RPGFinals.entitiesImagePackage+"silverfish.png","Sirverfish","SilverFishX", 60),

    /**
     *
     */
    SKELETONX(RPGFinals.entitiesImagePackage+"skeleton.png","Skeleton","SkeletonX", 51),

    /**
     *
     */
    SLIMEX(RPGFinals.entitiesImagePackage+"slime.png","Slime","SlimeX", 55),

    /**
     *
     */
    SNOWGOLEMX(RPGFinals.entitiesImagePackage+"snowgolem.png","Snowgolem","SnowGolemX", 97),

    /**
     *
     */
    SPIDERX(RPGFinals.entitiesImagePackage+"spider.png","Spider","SpiderX", 52),

    /**
     *
     */
    SQUIDX(RPGFinals.entitiesImagePackage+"squid.png","Squid","SquidX", 94),

    /**
     *
     */
    VILLAGERX(RPGFinals.entitiesImagePackage+"villager.png","Villager","VillagerX", 120),

    /**
     *
     */
    WITCHX(RPGFinals.entitiesImagePackage+"witch.png","Witch","WitchX", 66),

    /**
     *
     */
    WITHERX(RPGFinals.entitiesImagePackage+"wither.png","wither","WitherX", 64),

    /**
     *
     */
    WOLFX(RPGFinals.entitiesImagePackage+"wolf.png","Wolf","WolfX", 95),

    /**
     *
     */
    ZOMBIEX(RPGFinals.entitiesImagePackage+"zombie.png","Zombie","ZombieX", 54);

    private String path;
    private String name;
    private String internalName;
    private int id;

    private EnumEntities(String path, String name, String internalName, int id) {
        this.path = path;
        this.name = name;
        this.internalName = internalName;
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getInternalName() {
        return internalName;
    }

    /**
     *
     * @param internalName
     */
    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    
}
