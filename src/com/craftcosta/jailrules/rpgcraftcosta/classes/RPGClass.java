/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.classes;

/**
 *
 * @author jail
 */
public abstract class RPGClass {
    String nameClass;
    long baseDextery;       //Increases & improves Attack Speed, Evasion, HitRates
    long baseStrength;      //Increases Attack, Mana& Health 
    long baseIntelligence;  //Increases Mana & MagicAttack
    long baseConstitution;  //Increases MaxHealth & MaxMana
    long baseAttack;        //Physic attack unarmed
    long baseMagicAttack;   //Magical attack unarmed
    long baseDefense;       //Physical defense
    long baseMagicDefense;  //Magical defense
    long baseEvasion;       //% to evade damage
    long baseCriticalAttack;//% to make critical attack
    
    public String getName(){
        return this.nameClass;
    }
}
