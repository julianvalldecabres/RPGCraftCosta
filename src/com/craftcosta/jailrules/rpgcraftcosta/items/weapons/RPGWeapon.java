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
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItem;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLores;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGWeapon extends RPGItem {

    private int level; //nivel minimo para equiparla.. 
    //En caso de no tener el nivel se reduce altamente el daño aplicable y atributos
    private boolean upgradable;
    private int weaponLevel; //Por defecto 0 aumenta mejorando el arma

    private double physicalattack;
    private double incphysicalattack;
    private double actualphysicalattack;

    private double physicalhitrate;
    private double incphysicalhitrate;
    private double actualphysicalhitrate;

    private double magicalattack;
    private double incmagicalattack;
    private double actualmagicalattack;

    private double magicalhitrate;
    private double incmagicalhitrate;
    private double actualmagicalhitrate;

    private double critical;
    private double inccritical;
    private double actualcritical;

    private double healthsteal;
    private double inchealthsteal;
    private double actualhealthsteal;

    private double manasteal;
    private double incmanasteal;
    private double actualmanasteal;

    /**
     *
     * @param item
     * @param name
     * @param comerciable
     * @param sellprice
     * @param buyprice
     * @param quality
     * @param level
     * @param upgradable
     * @param weaponLevel
     * @param physicalattack
     * @param incphysicalattack
     * @param physicalhitrate
     * @param incphysicalhitrate
     * @param magicalattack
     * @param incmagicalattack
     * @param magicalhitrate
     * @param incmagicalhitrate
     * @param healthsteal
     * @param inchealthsteal
     * @param manasteal
     * @param incmanasteal
     * @param critical
     * @param inccritical
     * @param apbonus
     * @param xpbonus
     * @param moneybonus
     */
    public RPGWeapon(ItemStack item, String name, boolean comerciable, int sellprice, int buyprice, Quality quality, int level, boolean upgradable, int weaponLevel, double physicalattack, double incphysicalattack, double physicalhitrate, double incphysicalhitrate, double magicalattack, double incmagicalattack, double magicalhitrate, double incmagicalhitrate, double healthsteal, double inchealthsteal, double manasteal, double incmanasteal, double critical, double inccritical, double apbonus, double xpbonus, double moneybonus) {
        this.item = item;
        this.name = name;
        this.quality = quality;
        this.comerciable = comerciable;
        this.sellPrice = sellprice;
        this.buyPrice = buyprice;

        this.level = level;
        this.upgradable = upgradable;
        this.weaponLevel = weaponLevel;
        this.APBonus = apbonus;
        this.XPBonus = xpbonus;
        this.moneyBonus = moneybonus;

        this.physicalattack = physicalattack;
        this.incphysicalattack = incphysicalattack;
        this.actualphysicalattack = physicalattack + incphysicalattack * weaponLevel;

        this.physicalhitrate = physicalhitrate;
        this.incphysicalhitrate = incphysicalhitrate;
        this.actualphysicalhitrate = physicalhitrate + incphysicalhitrate * weaponLevel;

        this.magicalattack = magicalattack;
        this.incmagicalattack = incmagicalattack;
        this.actualmagicalattack = magicalattack + incmagicalattack * weaponLevel;

        this.magicalhitrate = magicalhitrate;
        this.incmagicalhitrate = incmagicalhitrate;
        this.actualmagicalhitrate = magicalhitrate + incmagicalhitrate * weaponLevel;

        this.healthsteal = healthsteal;
        this.inchealthsteal = inchealthsteal;
        this.actualhealthsteal = healthsteal + inchealthsteal * weaponLevel;

        this.manasteal = manasteal;
        this.incmanasteal = incmanasteal;
        this.actualmanasteal = manasteal + incmanasteal * weaponLevel;

        this.critical = critical;
        this.inccritical = inccritical;
        this.actualcritical = critical + inccritical * weaponLevel;
        //Modificacion del item
        ItemMeta iMeta = item.getItemMeta();
        //Añadimos nombre descriptivo al arma hay que tener especial cuidado con nombres de mas de una palabra
        iMeta.setDisplayName(quality.getColor() + "[LVL" + level + "] " + name + " +" + weaponLevel);
        //Y preparamos el lore del arma actual
        List<String> lores = new ArrayList<>();
        if (this.actualphysicalattack > 0) {
            lores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, actualphysicalattack));
        }
        if (this.actualphysicalhitrate > 0) {
            lores.add(RPGLores.PHYSICALHITRATE.getLoreString(RPGLores.PHYSICALHITRATE, actualphysicalhitrate));
        }
        if (this.actualmagicalattack > 0) {
            lores.add(RPGLores.MAGICALATTACK.getLoreString(RPGLores.MAGICALATTACK, actualmagicalattack));
        }
        if (this.actualmagicalhitrate > 0) {
            lores.add(RPGLores.MAGICALHITRATE.getLoreString(RPGLores.MAGICALHITRATE, actualmagicalhitrate));
        }
        if (this.actualhealthsteal > 0) {
            lores.add(RPGLores.HEALTHSTEAL.getLoreString(RPGLores.HEALTHSTEAL, actualhealthsteal));
        }
        if (this.actualmanasteal > 0) {
            lores.add(RPGLores.MANASTEAL.getLoreString(RPGLores.MANASTEAL, actualmanasteal));
        }
        if (this.actualcritical > 0) {
            lores.add(RPGLores.CRITICAL.getLoreString(RPGLores.CRITICAL, actualcritical));
        }
        if (XPBonus > 0) {
            lores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, this.XPBonus));
        }
        if (APBonus > 0) {
            lores.add(RPGLores.APBONUS.getLoreString(RPGLores.APBONUS, this.APBonus));
        }
        if (moneyBonus > 0) {
            lores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, this.moneyBonus));
        }
        if (this.comerciable) {
            lores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, buyPrice));
            lores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, sellPrice));
        } else {
            lores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
        }
        if (!this.upgradable) {
            lores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
        }
        iMeta.setLore(lores);
        item.setItemMeta(iMeta);
    }

    //GETTER AND SETTERS

    /**
     *
     * @return
     */
        public int getLevel() {
        return level;
    }

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return
     */
    public boolean isUpgradable() {
        return upgradable;
    }

    /**
     *
     * @param upgradable
     */
    public void setUpgradable(boolean upgradable) {
        this.upgradable = upgradable;
    }

    /**
     *
     * @return
     */
    public int getWeaponLevel() {
        return weaponLevel;
    }

    /**
     *
     * @param weaponLevel
     */
    public void setWeaponLevel(int weaponLevel) {
        this.weaponLevel = weaponLevel;
    }

    /**
     *
     * @return
     */
    public double getPhysicalattack() {
        return physicalattack;
    }

    /**
     *
     * @param physicalattack
     */
    public void setPhysicalattack(double physicalattack) {
        this.physicalattack = physicalattack;
    }

    /**
     *
     * @return
     */
    public double getIncphysicalattack() {
        return incphysicalattack;
    }

    /**
     *
     * @param incphysicalattack
     */
    public void setIncphysicalattack(double incphysicalattack) {
        this.incphysicalattack = incphysicalattack;
    }

    /**
     *
     * @return
     */
    public double getActualphysicalattack() {
        return actualphysicalattack;
    }

    /**
     *
     * @param actualphysicalattack
     */
    public void setActualphysicalattack(double actualphysicalattack) {
        this.actualphysicalattack = actualphysicalattack;
    }

    /**
     *
     * @return
     */
    public double getPhysicalhitrate() {
        return physicalhitrate;
    }

    /**
     *
     * @param physicalhitrate
     */
    public void setPhysicalhitrate(double physicalhitrate) {
        this.physicalhitrate = physicalhitrate;
    }

    /**
     *
     * @return
     */
    public double getIncphysicalhitrate() {
        return incphysicalhitrate;
    }

    /**
     *
     * @param incphysicalhitrate
     */
    public void setIncphysicalhitrate(double incphysicalhitrate) {
        this.incphysicalhitrate = incphysicalhitrate;
    }

    /**
     *
     * @return
     */
    public double getActualphysicalhitrate() {
        return actualphysicalhitrate;
    }

    /**
     *
     * @param actualphysicalhitrate
     */
    public void setActualphysicalhitrate(double actualphysicalhitrate) {
        this.actualphysicalhitrate = actualphysicalhitrate;
    }

    /**
     *
     * @return
     */
    public double getMagicalattack() {
        return magicalattack;
    }

    /**
     *
     * @param magicalattack
     */
    public void setMagicalattack(double magicalattack) {
        this.magicalattack = magicalattack;
    }

    /**
     *
     * @return
     */
    public double getIncmagicalattack() {
        return incmagicalattack;
    }

    /**
     *
     * @param incmagicalattack
     */
    public void setIncmagicalattack(double incmagicalattack) {
        this.incmagicalattack = incmagicalattack;
    }

    /**
     *
     * @return
     */
    public double getActualmagicalattack() {
        return actualmagicalattack;
    }

    /**
     *
     * @param actualmagicalattack
     */
    public void setActualmagicalattack(double actualmagicalattack) {
        this.actualmagicalattack = actualmagicalattack;
    }

    /**
     *
     * @return
     */
    public double getMagicalhitrate() {
        return magicalhitrate;
    }

    /**
     *
     * @param magicalhitrate
     */
    public void setMagicalhitrate(double magicalhitrate) {
        this.magicalhitrate = magicalhitrate;
    }

    /**
     *
     * @return
     */
    public double getIncmagicalhitrate() {
        return incmagicalhitrate;
    }

    /**
     *
     * @param incmagicalhitrate
     */
    public void setIncmagicalhitrate(double incmagicalhitrate) {
        this.incmagicalhitrate = incmagicalhitrate;
    }

    /**
     *
     * @return
     */
    public double getActualmagicalhitrate() {
        return actualmagicalhitrate;
    }

    /**
     *
     * @param actualmagicalhitrate
     */
    public void setActualmagicalhitrate(double actualmagicalhitrate) {
        this.actualmagicalhitrate = actualmagicalhitrate;
    }

    /**
     *
     * @return
     */
    public double getCritical() {
        return critical;
    }

    /**
     *
     * @param critical
     */
    public void setCritical(double critical) {
        this.critical = critical;
    }

    /**
     *
     * @return
     */
    public double getInccritical() {
        return inccritical;
    }

    /**
     *
     * @param inccritical
     */
    public void setInccritical(double inccritical) {
        this.inccritical = inccritical;
    }

    /**
     *
     * @return
     */
    public double getActualcritical() {
        return actualcritical;
    }

    /**
     *
     * @param actualcritical
     */
    public void setActualcritical(double actualcritical) {
        this.actualcritical = actualcritical;
    }

    /**
     *
     * @return
     */
    public double getHealthsteal() {
        return healthsteal;
    }

    /**
     *
     * @param healthsteal
     */
    public void setHealthsteal(double healthsteal) {
        this.healthsteal = healthsteal;
    }

    /**
     *
     * @return
     */
    public double getInchealthsteal() {
        return inchealthsteal;
    }

    /**
     *
     * @param inchealthsteal
     */
    public void setInchealthsteal(double inchealthsteal) {
        this.inchealthsteal = inchealthsteal;
    }

    /**
     *
     * @return
     */
    public double getActualhealthsteal() {
        return actualhealthsteal;
    }

    /**
     *
     * @param actualhealthsteal
     */
    public void setActualhealthsteal(double actualhealthsteal) {
        this.actualhealthsteal = actualhealthsteal;
    }

    /**
     *
     * @return
     */
    public double getManasteal() {
        return manasteal;
    }

    /**
     *
     * @param manasteal
     */
    public void setManasteal(double manasteal) {
        this.manasteal = manasteal;
    }

    /**
     *
     * @return
     */
    public double getIncmanasteal() {
        return incmanasteal;
    }

    /**
     *
     * @param incmanasteal
     */
    public void setIncmanasteal(double incmanasteal) {
        this.incmanasteal = incmanasteal;
    }

    /**
     *
     * @return
     */
    public double getActualmanasteal() {
        return actualmanasteal;
    }

    /**
     *
     * @param actualmanasteal
     */
    public void setActualmanasteal(double actualmanasteal) {
        this.actualmanasteal = actualmanasteal;
    }

    /**
     *
     * @return
     */
    public Quality getQuality() {
        return quality;
    }

    /**
     *
     * @param quality
     */
    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    /**
     *
     * @return
     */
    public int getSellPrice() {
        return sellPrice;
    }

    /**
     *
     * @param sellPrice
     */
    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     *
     * @return
     */
    public int getBuyPrice() {
        return buyPrice;
    }

    /**
     *
     * @param buyPrice
     */
    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
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
    public double getXPBonus() {
        return XPBonus;
    }

    /**
     *
     * @param XPBonus
     */
    public void setXPBonus(double XPBonus) {
        this.XPBonus = XPBonus;
    }

    /**
     *
     * @return
     */
    public double getAPBonus() {
        return APBonus;
    }

    /**
     *
     * @param APBonus
     */
    public void setAPBonus(double APBonus) {
        this.APBonus = APBonus;
    }

    /**
     *
     * @return
     */
    public double getMoneyBonus() {
        return moneyBonus;
    }

    /**
     *
     * @param moneyBonus
     */
    public void setMoneyBonus(double moneyBonus) {
        this.moneyBonus = moneyBonus;
    }

    @Override
    public String toString() {
        return "Weapon :\n"
                + "\tname: " + getName()
                + "\n\ttype: " + item.getType().toString()
                + "\n\tquality: " + quality.toString()
                + "\n\tlevel required: " + getLevel()
                + "\n\tupgradable: " + isUpgradable();
    }

    /**
     *
     * @param actualLevel
     * @return
     */
    public int getLevel(String actualLevel) {
        return Integer.parseInt(actualLevel.substring(1));
    }

    /**
     *
     * @return
     */
    public boolean isComerciable() {
        return this.comerciable;
    }
}
