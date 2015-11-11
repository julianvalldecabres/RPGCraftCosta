/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.jewels;

import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItem;
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
    private double evasionp;
    private double blockp;
    private double healthp; //porcentaje de vida añadida al player
    private double criticalp; //porcentaje de critico añadido al player
    private double healthstealp; //porcentaje de robo de vida añadido al player

    public RPGJewel(ItemStack item, String name, boolean comerciable, int sellprice, int buyprice, Quality quality, boolean combinable, double healthp, double criticalp, double evasionp, double blockp, double healthstealp, double apbonus, double xpbonus, double moneybonus) {
        this.item = item;
        this.name = name;
        this.quality = quality;
        this.sellPrice = sellprice;
        this.buyPrice = buyprice;
        this.comerciable = comerciable;
        this.combinable = combinable;
        this.APBonus = apbonus;
        this.XPBonus = xpbonus;
        this.moneyBonus = moneybonus;

        this.evasionp = evasionp;
        this.blockp = blockp;
        this.healthp = healthp;
        this.criticalp = criticalp;
        this.healthstealp = healthstealp;

        //Modificacion del item
        ItemMeta iMeta = item.getItemMeta();
        //Añadimos nombre descriptivo al arma hay que tener especial cuidado con nombres de mas de una palabra
        iMeta.setDisplayName(quality.getColor() + name);
        //Y preparamos el lore del arma actual
        List<String> lores = new ArrayList<>();
        if (evasionp > 0) {
            lores.add("Evasion +" + evasionp + "%");
        }
        if (healthp > 0) {
            lores.add("Health +" + healthp + "%");
        }
        if (blockp > 0) {
            lores.add("Block +" + blockp + "%");
        }
        if (criticalp > 0) {
            lores.add("Critical +" + criticalp + "%");
        }
        if (healthstealp > 0) {
            lores.add("HealthSteal +" + healthstealp + "%");
        }
        if (XPBonus > 0) {
            lores.add("XP Bonus +" + XPBonus + "%");
        }
        if (APBonus > 0) {
            lores.add("AP Bonus +" + APBonus + "%");
        }
        if (moneyBonus > 0) {
            lores.add("Money Bonus +" + moneyBonus + "%");
        }
        if (comerciable) {
            lores.add("Buy price: " + buyPrice + "$");
            lores.add("Sell price: " + sellPrice + "$");
        } else {
            lores.add(ChatColor.RED + "No comerciable");
        }
        if (!combinable) {
            lores.add(ChatColor.RED + "No combinable");
        }
        iMeta.setLore(lores);
        item.setItemMeta(iMeta);

    }

    //GETTER AND SETTERS
    public void setItem(ItemStack item) {
        this.item = item;
    }

    public double getCriticalp() {
        return criticalp;
    }

    public void setCriticalp(double criticalp) {
        this.criticalp = criticalp;
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

    public boolean isCombinable() {
        return combinable;
    }

    public void setCombinable(boolean combinable) {
        this.combinable = combinable;
    }

    public double getEvasionp() {
        return evasionp;
    }

    public void setEvasionp(double evasionp) {
        this.evasionp = evasionp;
    }

    public double getBlockp() {
        return blockp;
    }

    public void setBlockp(double blockp) {
        this.blockp = blockp;
    }

    public double getHealthp() {
        return healthp;
    }

    public void setHealthp(double healthp) {
        this.healthp = healthp;
    }

    public double getHealthstealp() {
        return healthstealp;
    }

    public void setHealthstealp(double healthstealp) {
        this.healthstealp = healthstealp;
    }

    public boolean isComerciable() {
        return comerciable;
    }

    public void setComerciable(boolean comerciable) {
        this.comerciable = comerciable;
    }

    @Override
    public String toString() {
        return "Weapon :\n"
                + "\tname: " + getName()
                + "\n\ttype: " + item.getType().toString()
                + "\n\tquality: " + quality.toString()
                + "\n\tcombinable: " + isCombinable();
    }

}
