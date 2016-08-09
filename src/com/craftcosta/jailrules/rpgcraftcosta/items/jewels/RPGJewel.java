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
package com.craftcosta.jailrules.rpgcraftcosta.items.jewels;

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
public class RPGJewel extends RPGItem {

    private boolean combinable;
    private double physicalattack;
    private double physicaldefense;
    private double physicalevasion;
    private double physicalhitrate;
    private double magicalattack;
    private double magicaldefense;
    private double magicalevasion;
    private double magicalhitrate;
    private double health;
    private double mana;
    private double critical;
    private double healthsteal;
    private double manasteal;

    /**
     *
     * @param item
     * @param name
     * @param sellprice
     * @param buyprice
     * @param quality
     * @param combinable
     * @param comerciable
     * @param physicalattack
     * @param physicaldefense
     * @param physicalevasion
     * @param physicalhitrate
     * @param magicalattack
     * @param magicaldefense
     * @param magicalevasion
     * @param magicalhitrate
     * @param health
     * @param mana
     * @param critical
     * @param healthsteal
     * @param manasteal
     * @param apbonus
     * @param xpbonus
     * @param moneybonus
     */
    public RPGJewel(ItemStack item, String name, int sellprice, int buyprice, Quality quality, boolean combinable, boolean comerciable, double physicalattack, double physicaldefense, double physicalevasion, double physicalhitrate, double magicalattack, double magicaldefense, double magicalevasion, double magicalhitrate, double health, double mana, double critical, double healthsteal, double manasteal, double apbonus, double xpbonus, double moneybonus) {
        this.item = item;
        this.name = name;
        this.quality = quality;
        this.sellPrice = sellprice;
        this.buyPrice = buyprice;
        this.comerciable = comerciable;
        this.combinable = combinable;
        this.physicalattack = physicalattack;
        this.physicaldefense = physicaldefense;
        this.physicalevasion = physicalevasion;
        this.physicalhitrate = physicalhitrate;
        this.magicalattack = magicalattack;
        this.magicaldefense = magicaldefense;
        this.magicalevasion = magicalevasion;
        this.magicalhitrate = magicalhitrate;
        this.health = health;
        this.mana = mana;
        this.critical = critical;
        this.healthsteal = healthsteal;
        this.manasteal = manasteal;
        this.APBonus = apbonus;
        this.XPBonus = xpbonus;
        this.moneyBonus = moneybonus;

        ItemMeta iMeta = item.getItemMeta();
        //Añadimos nombre descriptivo al arma hay que tener especial cuidado con nombres de mas de una palabra
        iMeta.setDisplayName(quality.getColor() + name);
        //Y preparamos el lore del arma actual
        List<String> lores = new ArrayList<>();
        if (physicalattack > 0) {
            lores.add(RPGLores.PHYSICALATTACK.getLoreString(RPGLores.PHYSICALATTACK, physicalattack));
        }
        if (physicalhitrate > 0) {
            lores.add(RPGLores.PHYSICALHITRATE.getLoreString(RPGLores.PHYSICALHITRATE, physicalhitrate));
        }
        if (physicaldefense > 0) {
            lores.add(RPGLores.PHYSICALDEFENSE.getLoreString(RPGLores.PHYSICALDEFENSE, physicaldefense));
        }
        if (physicalevasion > 0) {
            lores.add(RPGLores.PHYSICALEVASION.getLoreString(RPGLores.PHYSICALEVASION, physicalevasion));
        }
        if (magicalattack > 0) {
            lores.add(RPGLores.MAGICALATTACK.getLoreString(RPGLores.MAGICALATTACK, magicalattack));
        }
        if (magicalhitrate > 0) {
            lores.add(RPGLores.MAGICALHITRATE.getLoreString(RPGLores.MAGICALHITRATE, magicalhitrate));
        }
        if (magicaldefense > 0) {
            lores.add(RPGLores.MAGICALDEFENSE.getLoreString(RPGLores.MAGICALDEFENSE, magicaldefense));
        }
        if (magicalevasion > 0) {
            lores.add(RPGLores.MAGICALEVASION.getLoreString(RPGLores.MAGICALEVASION, magicalevasion));
        }
        if (health > 0) {
            lores.add(RPGLores.HEALTH.getLoreString(RPGLores.HEALTH, health));
        }
        if (mana > 0) {
            lores.add(RPGLores.MANA.getLoreString(RPGLores.MANA, mana));
        }
        if (healthsteal > 0) {
            lores.add(RPGLores.HEALTHSTEAL.getLoreString(RPGLores.HEALTHSTEAL, healthsteal));
        }
        if (manasteal > 0) {
            lores.add(RPGLores.MANASTEAL.getLoreString(RPGLores.MANASTEAL, manasteal));
        }
        if (critical > 0) {
            lores.add(RPGLores.CRITICAL.getLoreString(RPGLores.CRITICAL, critical));
        }
        if (XPBonus > 0) {
            lores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, XPBonus));
        }
        if (APBonus > 0) {
            lores.add(RPGLores.APBONUS.getLoreString(RPGLores.APBONUS, APBonus));
        }
        if (moneyBonus > 0) {
            lores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, moneyBonus));
        }
        if (this.comerciable) {
            lores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, buyPrice));
            lores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, sellPrice));
        } else {
            lores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
        }
        if (!combinable) {
            lores.add(ChatColor.RED + RPGLores.NOCOMBINABLE.getLoreName());
        }
        iMeta.setLore(lores);
        item.setItemMeta(iMeta);
    }

    /**
     *
     * @return
     */
    public boolean isCombinable() {
        return combinable;
    }

    /**
     *
     * @param combinable
     */
    public void setCombinable(boolean combinable) {
        this.combinable = combinable;
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
    public double getPhysicaldefense() {
        return physicaldefense;
    }

    /**
     *
     * @param physicaldefense
     */
    public void setPhysicaldefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    /**
     *
     * @return
     */
    public double getPhysicalevasion() {
        return physicalevasion;
    }

    /**
     *
     * @param physicalevasion
     */
    public void setPhysicalevasion(double physicalevasion) {
        this.physicalevasion = physicalevasion;
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
    public double getMagicaldefense() {
        return magicaldefense;
    }

    /**
     *
     * @param magicaldefense
     */
    public void setMagicaldefense(double magicaldefense) {
        this.magicaldefense = magicaldefense;
    }

    /**
     *
     * @return
     */
    public double getMagicalevasion() {
        return magicalevasion;
    }

    /**
     *
     * @param magicalevasion
     */
    public void setMagicalevasion(double magicalevasion) {
        this.magicalevasion = magicalevasion;
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
    public double getHealth() {
        return health;
    }

    /**
     *
     * @param health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     *
     * @return
     */
    public double getMana() {
        return mana;
    }

    /**
     *
     * @param mana
     */
    public void setMana(double mana) {
        this.mana = mana;
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

    /**
     *
     * @return
     */
    public boolean isComerciable() {
        return comerciable;
    }

    /**
     *
     * @param comerciable
     */
    public void setComerciable(boolean comerciable) {
        this.comerciable = comerciable;
    }
}
