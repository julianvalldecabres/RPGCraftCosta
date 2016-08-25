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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumItems;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumWeapon;

/**
 *
 * @author jail
 */
public class GUIWeapon {

    private String name;
    private EnumWeapon item;
    private int level;
    private int weaponlevel;
    private long sellprice;
    private long buyprice;
    private EnumQuality quality;
    private boolean upgradable;
    private boolean comerciable;
    private double physicalattack;
    private double incphysicalattack;
    private float physicalhitrate;
    private float incphysicalhitrate;
    private double magicalattack;
    private double incmagicalattack;
    private float magicalhitrate;
    private float incmagicalhitrate;
    private float critical;
    private float inccritical;
    private float healthsteal;
    private float inchealthsteal;
    private float manasteal;
    private float incmanasteal;
    private float xpbonus;
    private float moneybonus;

    public GUIWeapon(String name, EnumWeapon item, int level, int weaponlevel, long sellprice, long buyprice, EnumQuality quality, boolean upgradable, boolean comerciable, double physicalattack, double incphysicalattack, float physicalhitrate, float incphysicalhitrate, double magicalattack, double incmagicalattack, float magicalhitrate, float incmagicalhitrate, float critical, float inccritical, float healthsteal, float inchealthsteal, float manasteal, float incmanasteal, float xpbonus, float moneybonus) {
        this.name = name;
        this.item = item;
        this.level = level;
        this.weaponlevel = weaponlevel;
        this.sellprice = sellprice;
        this.buyprice = buyprice;
        this.quality = quality;
        this.upgradable = upgradable;
        this.comerciable = comerciable;
        this.physicalattack = physicalattack;
        this.incphysicalattack = incphysicalattack;
        this.physicalhitrate = physicalhitrate;
        this.incphysicalhitrate = incphysicalhitrate;
        this.magicalattack = magicalattack;
        this.incmagicalattack = incmagicalattack;
        this.magicalhitrate = magicalhitrate;
        this.incmagicalhitrate = incmagicalhitrate;
        this.critical = critical;
        this.inccritical = inccritical;
        this.healthsteal = healthsteal;
        this.inchealthsteal = inchealthsteal;
        this.manasteal = manasteal;
        this.incmanasteal = incmanasteal;
        this.xpbonus = xpbonus;
        this.moneybonus = moneybonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumWeapon getItem() {
        return item;
    }

    public void setItem(EnumWeapon item) {
        this.item = item;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWeaponlevel() {
        return weaponlevel;
    }

    public void setWeaponlevel(int weaponlevel) {
        this.weaponlevel = weaponlevel;
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

    public EnumQuality getQuality() {
        return quality;
    }

    public void setQuality(EnumQuality quality) {
        this.quality = quality;
    }

    public boolean isUpgradable() {
        return upgradable;
    }

    public void setUpgradable(boolean upgradable) {
        this.upgradable = upgradable;
    }

    public boolean isComerciable() {
        return comerciable;
    }

    public void setComerciable(boolean comerciable) {
        this.comerciable = comerciable;
    }

    public double getPhysicalattack() {
        return physicalattack;
    }

    public void setPhysicalattack(double physicalattack) {
        this.physicalattack = physicalattack;
    }

    public double getIncphysicalattack() {
        return incphysicalattack;
    }

    public void setIncphysicalattack(double incphysicalattack) {
        this.incphysicalattack = incphysicalattack;
    }

    public float getPhysicalhitrate() {
        return physicalhitrate;
    }

    public void setPhysicalhitrate(float physicalhitrate) {
        this.physicalhitrate = physicalhitrate;
    }

    public float getIncphysicalhitrate() {
        return incphysicalhitrate;
    }

    public void setIncphysicalhitrate(float incphysicalhitrate) {
        this.incphysicalhitrate = incphysicalhitrate;
    }

    public double getMagicalattack() {
        return magicalattack;
    }

    public void setMagicalattack(double magicalattack) {
        this.magicalattack = magicalattack;
    }

    public double getIncmagicalattack() {
        return incmagicalattack;
    }

    public void setIncmagicalattack(double incmagicalattack) {
        this.incmagicalattack = incmagicalattack;
    }

    public float getMagicalhitrate() {
        return magicalhitrate;
    }

    public void setMagicalhitrate(float magicalhitrate) {
        this.magicalhitrate = magicalhitrate;
    }

    public float getIncmagicalhitrate() {
        return incmagicalhitrate;
    }

    public void setIncmagicalhitrate(float incmagicalhitrate) {
        this.incmagicalhitrate = incmagicalhitrate;
    }

    public float getCritical() {
        return critical;
    }

    public void setCritical(float critical) {
        this.critical = critical;
    }

    public float getInccritical() {
        return inccritical;
    }

    public void setInccritical(float inccritical) {
        this.inccritical = inccritical;
    }

    public float getHealthsteal() {
        return healthsteal;
    }

    public void setHealthsteal(float healthsteal) {
        this.healthsteal = healthsteal;
    }

    public float getInchealthsteal() {
        return inchealthsteal;
    }

    public void setInchealthsteal(float inchealthsteal) {
        this.inchealthsteal = inchealthsteal;
    }

    public float getManasteal() {
        return manasteal;
    }

    public void setManasteal(float manasteal) {
        this.manasteal = manasteal;
    }

    public float getIncmanasteal() {
        return incmanasteal;
    }

    public void setIncmanasteal(float incmanasteal) {
        this.incmanasteal = incmanasteal;
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
