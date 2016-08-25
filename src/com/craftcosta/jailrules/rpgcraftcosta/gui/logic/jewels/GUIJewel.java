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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.jewels;

import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;

/**
 *
 * @author jail
 */
public class GUIJewel {

    private String name;
    private EnumItems item;
    private EnumQuality quality;
    private boolean combinable;
    private boolean comerciable;
    private long sellprice;
    private long buyprice;
    private double physicalattack;
    private float physicalhitrate;
    private float physicalevasion;
    private double physicaldefense;
    private float magicalevasion;
    private double magicaldefense;
    private double magicalattack;
    private float magicalhitrate;
    private float health;
    private float mana;
    private float critical;
    private float healthsteal;
    private float manasteal;
    private float xpbonus;
    private float moneybonus;

    public GUIJewel(String name, EnumItems item, EnumQuality quality, boolean combinable, boolean comerciable, long sellprice, long buyprice, double physicalattack, float physicalhitrate, float physicalevasion, double physicaldefense, float magicalevasion, double magicaldefense, double magicalattack, float magicalhitrate, float health, float mana, float critical, float healthsteal, float manasteal, float xpbonus, float moneybonus) {
        this.name = name;
        this.item = item;
        this.quality = quality;
        this.combinable = combinable;
        this.comerciable = comerciable;
        this.sellprice = sellprice;
        this.buyprice = buyprice;
        this.physicalattack = physicalattack;
        this.physicalhitrate = physicalhitrate;
        this.physicalevasion = physicalevasion;
        this.physicaldefense = physicaldefense;
        this.magicalevasion = magicalevasion;
        this.magicaldefense = magicaldefense;
        this.magicalattack = magicalattack;
        this.magicalhitrate = magicalhitrate;
        this.health = health;
        this.mana = mana;
        this.critical = critical;
        this.healthsteal = healthsteal;
        this.manasteal = manasteal;
        this.xpbonus = xpbonus;
        this.moneybonus = moneybonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumItems getItem() {
        return item;
    }

    public void setItem(EnumItems item) {
        this.item = item;
    }

    public EnumQuality getQuality() {
        return quality;
    }

    public void setQuality(EnumQuality quality) {
        this.quality = quality;
    }

    public boolean isCombinable() {
        return combinable;
    }

    public void setCombinable(boolean combinable) {
        this.combinable = combinable;
    }

    public boolean isComerciable() {
        return comerciable;
    }

    public void setComerciable(boolean comerciable) {
        this.comerciable = comerciable;
    }

    public long getSellprice() {
        return sellprice;
    }

    public void setSellprice(long sellprice) {
        this.sellprice = sellprice;
    }

    public long getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(long buyprice) {
        this.buyprice = buyprice;
    }

    public double getPhysicalattack() {
        return physicalattack;
    }

    public void setPhysicalattack(double physicalattack) {
        this.physicalattack = physicalattack;
    }

    public float getPhysicalhitrate() {
        return physicalhitrate;
    }

    public void setPhysicalhitrate(float physicalhitrate) {
        this.physicalhitrate = physicalhitrate;
    }

    public float getPhysicalevasion() {
        return physicalevasion;
    }

    public void setPhysicalevasion(float physicalevasion) {
        this.physicalevasion = physicalevasion;
    }

    public double getPhysicaldefense() {
        return physicaldefense;
    }

    public void setPhysicaldefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    public float getMagicalevasion() {
        return magicalevasion;
    }

    public void setMagicalevasion(float magicalevasion) {
        this.magicalevasion = magicalevasion;
    }

    public double getMagicaldefense() {
        return magicaldefense;
    }

    public void setMagicaldefense(double magicaldefense) {
        this.magicaldefense = magicaldefense;
    }

    public double getMagicalattack() {
        return magicalattack;
    }

    public void setMagicalattack(double magicalattack) {
        this.magicalattack = magicalattack;
    }

    public float getMagicalhitrate() {
        return magicalhitrate;
    }

    public void setMagicalhitrate(float magicalhitrate) {
        this.magicalhitrate = magicalhitrate;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public float getCritical() {
        return critical;
    }

    public void setCritical(float critical) {
        this.critical = critical;
    }

    public float getHealthsteal() {
        return healthsteal;
    }

    public void setHealthsteal(float healthsteal) {
        this.healthsteal = healthsteal;
    }

    public float getManasteal() {
        return manasteal;
    }

    public void setManasteal(float manasteal) {
        this.manasteal = manasteal;
    }

    public float getXpbonus() {
        return xpbonus;
    }

    public void setXpbonus(float xpbonus) {
        this.xpbonus = xpbonus;
    }

    public float getMoneybonus() {
        return moneybonus;
    }

    public void setMoneybonus(float moneybonus) {
        this.moneybonus = moneybonus;
    }

}
