/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.player;

import com.craftcosta.jailrules.rpgcraftcosta.classes.RPGClass;
import com.craftcosta.jailrules.rpgcraftcosta.economy.RPGEconomy;
import com.craftcosta.jailrules.rpgcraftcosta.guilds.RPGGuild;
import org.bukkit.entity.Player;

/**
 *
 * @author jail
 */
public class RPGPlayer {
    //Player atributes
    String name;
    Player playerb;
    RPGEconomy econ;
    RPGGuild guild;
    RPGClass playerClass;
    long actualLevel;
    long actualExp;
    long actualhealth;
    long actualmana;
    //Dependent rpg attributes
    long maxMana;
    long maxHealth;
    int constitutionP;
    int dexteryP;
    int intelligenceP;
    int strengthP;
    float physicalAttack;
    float physicalDefense;
    float magicAttack;
    float magicDefense;
    float physicalHitRate;
    float MagicHitRate;
    float physicalEvasion;
    float magicEvasion;
    float critical;
    float deadly;
    
    public RPGPlayer(Player p){
        this.name=p.getName();
        this.playerb=p;
        this.econ=new RPGEconomy();
        this.guild=null;
        this.playerClass=null;
        this.actualLevel=0;
        this.playerb=p;
        this.econ=new RPGEconomy();
        this.actualExp=0;
        this.actualhealth=20;
        this.actualmana=20;
        this.maxMana=20;
        this.maxHealth=20;
        this.constitutionP=0;
        this.dexteryP=0;
        this.intelligenceP=0;
        this.strengthP=0;
        this.physicalAttack=1;
        this.physicalDefense=1;
        this.magicAttack=1;
        this.magicDefense=1;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerb(Player playerb) {
        this.playerb = playerb;
    }

    public void setEcon(RPGEconomy econ) {
        this.econ = econ;
    }

    public void setGuild(RPGGuild guild) {
        this.guild = guild;
    }

    public void setPlayerClass(RPGClass playerClass) {
        this.playerClass = playerClass;
    }

    public void setActualLevel(long actualLevel) {
        this.actualLevel = actualLevel;
    }

    public void setActualExp(long actualExp) {
        this.actualExp = actualExp;
    }

    public void setActualhealth(long actualhealth) {
        this.actualhealth = actualhealth;
    }

    public void setActualmana(long actualmana) {
        this.actualmana = actualmana;
    }

    public void setMaxMana(long maxMana) {
        this.maxMana = maxMana;
    }

    public void setMaxHealth(long maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setConstitutionP(int constitutionP) {
        this.constitutionP = constitutionP;
    }

    public void setDexteryP(int dexteryP) {
        this.dexteryP = dexteryP;
    }

    public void setIntelligenceP(int intelligenceP) {
        this.intelligenceP = intelligenceP;
    }

    public void setStrengthP(int strengthP) {
        this.strengthP = strengthP;
    }

    public void setPhysicalAttack(float physicalAttack) {
        this.physicalAttack = physicalAttack;
    }

    public void setPhysicalDefense(float physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public void setMagicAttack(float magicAttack) {
        this.magicAttack = magicAttack;
    }

    public void setMagicDefense(float magicDefense) {
        this.magicDefense = magicDefense;
    }

    public void setPhysicalHitRate(float physicalHitRate) {
        this.physicalHitRate = physicalHitRate;
    }

    public void setMagicHitRate(float MagicHitRate) {
        this.MagicHitRate = MagicHitRate;
    }

    public void setPhysicalEvasion(float physicalEvasion) {
        this.physicalEvasion = physicalEvasion;
    }

    public void setMagicEvasion(float magicEvasion) {
        this.magicEvasion = magicEvasion;
    }

    public void setCritical(float critical) {
        this.critical = critical;
    }

    public void setDeadly(float deadly) {
        this.deadly = deadly;
    }

    public String getName() {
        return name;
    }

    public Player getPlayerb() {
        return playerb;
    }

    public RPGEconomy getEcon() {
        return econ;
    }

    public RPGClass getPlayerClass() {
        return playerClass;
    }

    public long getActualLevel() {
        return actualLevel;
    }

    public long getActualExp() {
        return actualExp;
    }

    public long getActualhealth() {
        return actualhealth;
    }

    public long getActualmana() {
        return actualmana;
    }

    public long getMaxMana() {
        return maxMana;
    }

    public long getMaxHealth() {
        return maxHealth;
    }

    public int getConstitutionP() {
        return constitutionP;
    }

    public int getDexteryP() {
        return dexteryP;
    }

    public int getIntelligenceP() {
        return intelligenceP;
    }

    public int getStrengthP() {
        return strengthP;
    }

    public float getPhysicalAttack() {
        return physicalAttack;
    }

    public float getPhysicalDefense() {
        return physicalDefense;
    }

    public float getMagicAttack() {
        return magicAttack;
    }

    public float getMagicDefense() {
        return magicDefense;
    }

    public float getPhysicalHitRate() {
        return physicalHitRate;
    }

    public float getMagicHitRate() {
        return MagicHitRate;
    }

    public float getPhysicalEvasion() {
        return physicalEvasion;
    }

    public float getMagicEvasion() {
        return magicEvasion;
    }

    public float getCritical() {
        return critical;
    }

    public float getDeadly() {
        return deadly;
    }
    
    
    //String password;
    
    
    public long getPLevel(){
        return this.actualLevel;
    }
    
    public void setLevel(long l){
        this.actualLevel=l;
    }
    public Player getPlayer(){
        return this.playerb;
    }
    
    public boolean hasGuild(){
        return this.guild!=null;
    }
    
    public RPGGuild getGuild(){
        return this.guild;
    }
    
    public void loadRPGPlayer(){
    
    }
        
    public void saveRPGPlayer(){
        
    }
}
