/*
 * Copyright (C) 2016 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources;


/**
 *
 * @author jail
 */
public enum EnumEntities {

    BATX("logic/resources/entities/"+"bat.png","Bat","BatX",60),
    BLAZEX("logic/resources/entities/"+"blaze.png","Blaze","BlazeX", 61),
    CAVESPIDERX("logic/resources/entities/"+"cavespider.png","Cavespider","CaveSpiderX", 59),
    CHICKENX("logic/resources/entities/"+"chicken.png","Chicken","ChickenX", 93),
    COWX("logic/resources/entities/"+"cow.png","Cow","CowX", 92),
    CREEPERX("logic/resources/entities/"+"creeper.png","Creeper","CreeperX", 50),
    ENDERDRAGONX("logic/resources/entities/"+"enderdragon.png","Enderdragon","EnderDragonX", 63),
    ENDERMANX("logic/resources/entities/"+"endermen.png","Enderman","EndermanX", 58),
    ENDERMITEX("logic/resources/entities/"+"endermite.png","Endermite","EndermiteX", 67),
    GHASTX("logic/resources/entities/"+"ghast.png","Ghast","GhastX", 56),
    GIANTX("logic/resources/entities/"+"giantzombie.png","Giant","GiantX", 53),
    GUARDIANX("logic/resources/entities/"+"guardian.png","Guardian","GuardianX", 68),
    HORSEX("logic/resources/entities/"+"horse.png","Horse","HorseX", 100),
    IRONGOLEMX("logic/resources/entities/"+"irongolem.png","Irongolem","IronGolemX", 99),
    MAGMACUBEX("logic/resources/entities/"+"magmacube.png","Magmacube","MagmaCubeX", 62),
    MUSHROOMCOWX("logic/resources/entities/"+"mushroomcow.png","Mushroomcow","MushroomCowX", 96),
    OCELOTX("logic/resources/entities/"+"ocelot.png","Ocelot","OcelotX", 98),
    PIGX("logic/resources/entities/"+"pig.png","Pig","PigX", 90),
    PIGZOMBIEX("logic/resources/entities/"+"zombiepigmen.png","Pigzombie","PigZombieX", 57),
    RABBITX("logic/resources/entities/"+"rabbit.png","Rabbit","RabbitX", 101),
    SHEEPX("logic/resources/entities/"+"sheep.png","Sheep","SheepX", 91),
    SILVERFISHX("logic/resources/entities/"+"silverfish.png","Sirverfish","SilverFishX", 60),
    SKELETONX("logic/resources/entities/"+"skeleton.png","Skeleton","SkeletonX", 51),
    SLIMEX("logic/resources/entities/"+"slime.png","Slime","SlimeX", 55),
    SNOWGOLEMX("logic/resources/entities/"+"snowgolem.png","Snowgolem","SnowGolemX", 97),
    SPIDERX("logic/resources/entities/"+"spider.png","Spider","SpiderX", 52),
    SQUIDX("logic/resources/entities/"+"squid.png","Squid","SquidX", 94),
    VILLAGERX("logic/resources/entities/"+"villager.png","Villager","VillagerX", 120),
    WITCHX("logic/resources/entities/"+"witch.png","Witch","WitchX", 66),
    WITHERX("logic/resources/entities/"+"wither.png","wither","WitherX", 64),
    WOLFX("logic/resources/entities/"+"wolf.png","Wolf","WolfX", 95),
    ZOMBIEX("logic/resources/entities/"+"zombie.png","Zombie","ZombieX", 54);

    String path;
    String name;
    String internalName;
    int id;

    private EnumEntities(String path, String name, String internalName, int id) {
        this.path = path;
        this.name = name;
        this.internalName = internalName;
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
