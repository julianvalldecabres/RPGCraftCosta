/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    
    public RPGWeapon(ItemStack item, String name, boolean comerciable, int sellprice, int buyprice, Quality quality, int level, boolean upgradable, int weaponLevel, double physicalattack, double incphysicalattack,double physicalhitrate,double incphysicalhitrate, double magicalattack,double incmagicalattack,double magicalhitrate,double incmagicalhitrate, double healthsteal, double inchealthsteal, double manasteal,double incmanasteal,double critical, double inccritical,double apbonus, double xpbonus, double moneybonus) {
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
        this.actualphysicalattack = physicalattack+incphysicalattack*weaponLevel;
        
        this.physicalhitrate = physicalhitrate;
        this.incphysicalhitrate = incphysicalhitrate;
        this.actualphysicalhitrate = physicalhitrate+incphysicalhitrate*weaponLevel;
        
        this.magicalattack = magicalattack;
        this.incmagicalattack = incmagicalattack;
        this.actualmagicalattack = magicalattack+incmagicalattack*weaponLevel;
        
        this.magicalhitrate = magicalhitrate;
        this.incmagicalhitrate = incmagicalhitrate;
        this.actualmagicalhitrate = magicalhitrate+incmagicalhitrate*weaponLevel;
        
        this.healthsteal = healthsteal;
        this.inchealthsteal = inchealthsteal;
        this.actualhealthsteal = healthsteal+inchealthsteal*weaponLevel;
        
        this.manasteal = manasteal;
        this.incmanasteal = incmanasteal;
        this.actualmanasteal = manasteal+incmanasteal*weaponLevel;
        
        this.critical = critical;
        this.inccritical = inccritical;
        this.actualcritical = critical+inccritical*weaponLevel;
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
        if(this.actualcritical>0){
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isUpgradable() {
        return upgradable;
    }

    public void setUpgradable(boolean upgradable) {
        this.upgradable = upgradable;
    }

    public int getWeaponLevel() {
        return weaponLevel;
    }

    public void setWeaponLevel(int weaponLevel) {
        this.weaponLevel = weaponLevel;
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

    public double getActualphysicalattack() {
        return actualphysicalattack;
    }

    public void setActualphysicalattack(double actualphysicalattack) {
        this.actualphysicalattack = actualphysicalattack;
    }

    public double getPhysicalhitrate() {
        return physicalhitrate;
    }

    public void setPhysicalhitrate(double physicalhitrate) {
        this.physicalhitrate = physicalhitrate;
    }

    public double getIncphysicalhitrate() {
        return incphysicalhitrate;
    }

    public void setIncphysicalhitrate(double incphysicalhitrate) {
        this.incphysicalhitrate = incphysicalhitrate;
    }

    public double getActualphysicalhitrate() {
        return actualphysicalhitrate;
    }

    public void setActualphysicalhitrate(double actualphysicalhitrate) {
        this.actualphysicalhitrate = actualphysicalhitrate;
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

    public double getActualmagicalattack() {
        return actualmagicalattack;
    }

    public void setActualmagicalattack(double actualmagicalattack) {
        this.actualmagicalattack = actualmagicalattack;
    }

    public double getMagicalhitrate() {
        return magicalhitrate;
    }

    public void setMagicalhitrate(double magicalhitrate) {
        this.magicalhitrate = magicalhitrate;
    }

    public double getIncmagicalhitrate() {
        return incmagicalhitrate;
    }

    public void setIncmagicalhitrate(double incmagicalhitrate) {
        this.incmagicalhitrate = incmagicalhitrate;
    }

    public double getActualmagicalhitrate() {
        return actualmagicalhitrate;
    }

    public void setActualmagicalhitrate(double actualmagicalhitrate) {
        this.actualmagicalhitrate = actualmagicalhitrate;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getInccritical() {
        return inccritical;
    }

    public void setInccritical(double inccritical) {
        this.inccritical = inccritical;
    }

    public double getActualcritical() {
        return actualcritical;
    }

    public void setActualcritical(double actualcritical) {
        this.actualcritical = actualcritical;
    }

    public double getHealthsteal() {
        return healthsteal;
    }

    public void setHealthsteal(double healthsteal) {
        this.healthsteal = healthsteal;
    }

    public double getInchealthsteal() {
        return inchealthsteal;
    }

    public void setInchealthsteal(double inchealthsteal) {
        this.inchealthsteal = inchealthsteal;
    }

    public double getActualhealthsteal() {
        return actualhealthsteal;
    }

    public void setActualhealthsteal(double actualhealthsteal) {
        this.actualhealthsteal = actualhealthsteal;
    }

    public double getManasteal() {
        return manasteal;
    }

    public void setManasteal(double manasteal) {
        this.manasteal = manasteal;
    }

    public double getIncmanasteal() {
        return incmanasteal;
    }

    public void setIncmanasteal(double incmanasteal) {
        this.incmanasteal = incmanasteal;
    }

    public double getActualmanasteal() {
        return actualmanasteal;
    }

    public void setActualmanasteal(double actualmanasteal) {
        this.actualmanasteal = actualmanasteal;
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
    

    @Override
    public String toString() {
        return "Weapon :\n"
                + "\tname: " + getName()
                + "\n\ttype: " + item.getType().toString()
                + "\n\tquality: " + quality.toString()
                + "\n\tlevel required: " + getLevel()
                + "\n\tupgradable: " + isUpgradable();
    }

    public int getLevel(String actualLevel) {
        return Integer.parseInt(actualLevel.substring(1));
    }

    private boolean isComerciable() {
        return this.comerciable;
    }
}
