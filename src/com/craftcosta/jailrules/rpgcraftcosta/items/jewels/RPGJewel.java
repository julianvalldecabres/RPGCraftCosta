/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public boolean isCombinable() {
        return combinable;
    }

    public void setCombinable(boolean combinable) {
        this.combinable = combinable;
    }

    public double getPhysicalattack() {
        return physicalattack;
    }

    public void setPhysicalattack(double physicalattack) {
        this.physicalattack = physicalattack;
    }

    public double getPhysicaldefense() {
        return physicaldefense;
    }

    public void setPhysicaldefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    public double getPhysicalevasion() {
        return physicalevasion;
    }

    public void setPhysicalevasion(double physicalevasion) {
        this.physicalevasion = physicalevasion;
    }

    public double getPhysicalhitrate() {
        return physicalhitrate;
    }

    public void setPhysicalhitrate(double physicalhitrate) {
        this.physicalhitrate = physicalhitrate;
    }

    public double getMagicalattack() {
        return magicalattack;
    }

    public void setMagicalattack(double magicalattack) {
        this.magicalattack = magicalattack;
    }

    public double getMagicaldefense() {
        return magicaldefense;
    }

    public void setMagicaldefense(double magicaldefense) {
        this.magicaldefense = magicaldefense;
    }

    public double getMagicalevasion() {
        return magicalevasion;
    }

    public void setMagicalevasion(double magicalevasion) {
        this.magicalevasion = magicalevasion;
    }

    public double getMagicalhitrate() {
        return magicalhitrate;
    }

    public void setMagicalhitrate(double magicalhitrate) {
        this.magicalhitrate = magicalhitrate;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getHealthsteal() {
        return healthsteal;
    }

    public void setHealthsteal(double healthsteal) {
        this.healthsteal = healthsteal;
    }

    public double getManasteal() {
        return manasteal;
    }

    public void setManasteal(double manasteal) {
        this.manasteal = manasteal;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getXPBonus() {
        return XPBonus;
    }

    public void setXPBonus(double XPBonus) {
        this.XPBonus = XPBonus;
    }

    public double getAPBonus() {
        return APBonus;
    }

    public void setAPBonus(double APBonus) {
        this.APBonus = APBonus;
    }

    public double getMoneyBonus() {
        return moneyBonus;
    }

    public void setMoneyBonus(double moneyBonus) {
        this.moneyBonus = moneyBonus;
    }

    public boolean isComerciable() {
        return comerciable;
    }

    public void setComerciable(boolean comerciable) {
        this.comerciable = comerciable;
    }
}
