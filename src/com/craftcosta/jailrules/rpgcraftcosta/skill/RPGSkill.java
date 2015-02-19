/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.skill;

import org.bukkit.Effect;

/**
 *
 * @author jail
 */
public class RPGSkill {
    String skillName;
    String []reqSkills;
    String type;        //Active or Passive
    int reqLevel;       //level required to adquire skill
    String reqClass;    //required rpgclass
    long damage;
    long magicDamage;
    long defense;
    long magicDefense;  
    int radious;        //blocks
    int cooldown;       //seconds
    int duration;       //seconds  
    Effect effect;      //TO STUDY
}
