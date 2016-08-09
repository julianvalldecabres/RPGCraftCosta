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

    /**
     *
     * @param damager
     * @param damaged
     * @param damage
     * @param res
     */
    public Attack(Entity damager, Entity damaged, double damage, AttackResult res) {
        this.damager = damager;
        this.damaged = damaged;
        this.damage = damage;
        this.res = res;
    }

    /**
     *
     * @return
     */
    public double getDamage() {
        return damage;
    }

    /**
     *
     * @param damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     *
     * @param res
     */
    public void setRes(AttackResult res) {
        this.res = res;
    }

    /**
     *
     * @return
     */
    public AttackResult getRes() {
        return res;
    }

    /**
     *
     * @return
     */
    public Entity getDamaged() {
        return damaged;
    }

    /**
     *
     * @param damaged
     */
    public void setDamaged(Entity damaged) {
        this.damaged = damaged;
    }

    /**
     *
     * @return
     */
    public Entity getDamager() {
        return damager;
    }

    /**
     *
     * @param damager
     */
    public void setDamager(Entity damager) {
        this.damager = damager;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
