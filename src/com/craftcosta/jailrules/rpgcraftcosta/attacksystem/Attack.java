/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.attacksystem;

import org.bukkit.entity.Entity;

/**
 *
 * @author jail
 */
public class Attack {

    private Entity damager;
    private Entity damaged;
    private double damage;
    private AttackResult res;

    public Attack(Entity damager, Entity damaged, double damage, AttackResult res) {
        this.damager = damager;
        this.damaged = damaged;
        this.damage = damage;
        this.res = res;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setRes(AttackResult res) {
        this.res = res;
    }

    public AttackResult getRes() {
        return res;
    }

    public Entity getDamaged() {
        return damaged;
    }

    public void setDamaged(Entity damaged) {
        this.damaged = damaged;
    }

    public Entity getDamager() {
        return damager;
    }

    public void setDamager(Entity damager) {
        this.damager = damager;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
