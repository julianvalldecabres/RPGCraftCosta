/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.spawner;

import com.craftcosta.jailrules.rpgcraftcosta.mobs.CustomEntityType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.AttackType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.BehaviourType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.EntityAge;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.HorseType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.HorseVariant;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.OcelotType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.RabbitType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.SkeletonType;
import com.craftcosta.jailrules.rpgcraftcosta.mobs.utils.VillagerType;
import org.bukkit.Location;

/**
 *
 * @author jail
 */
public class RPGMobSpawner {

    String Id;
    CustomEntityType type;
    AttackType aType;
    BehaviourType bType;
    EntityAge eAge;
    HorseType horseType;
    HorseVariant horseVar;
    OcelotType oType;
    RabbitType rType;
    SkeletonType sType;
    VillagerType vType;
    Location loc;
    int level;
    public double cooldown;
    public Integer radius;
    public Integer maxMobs;
    public boolean enabled;
    

}
