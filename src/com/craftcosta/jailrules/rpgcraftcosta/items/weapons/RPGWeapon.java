/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.weapons;

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
public class RPGWeapon extends RPGItem {

    private int level; //nivel minimo para equiparla.. 
    //En caso de no tener el nivel se reduce altamente el daño aplicable y atributos
    private boolean upgradable;
    private int weaponLevel; //Por defecto 0 aumenta mejorando el arma

    private double damage;
    private double incDamage;
    private double criticalp; //probabilidad de critico
    private double incCriticalp;
    private double criticaldamage; //daño de critico
    private double incCriticalDamage;
    private double healthsteal;
    private double incHealthsteal;
    private double actualDamage;
    private double actualCriticalp;
    private double actualCriticalDamage;
    private double actualHealthsteal;

    public RPGWeapon(ItemStack item, String name, boolean comerciable, int sellprice, int buyprice, Quality quality, int level, boolean upgradable, int weaponLevel, double damage, double incdamage, double criticalp, double inccriticalp, double criticaldamage, double inccriticaldamage, double healthsteal, double inchealthsteal, double apbonus, double xpbonus, double moneybonus) {
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

        this.damage = damage;//daño base
        this.actualDamage = damage; //daño actual
        this.incDamage = incdamage; //Inc de daño al mejorar el arma
        this.criticalp = criticalp;
        this.actualCriticalp = criticalp;
        this.incCriticalp = inccriticalp;
        this.criticaldamage = criticaldamage;
        this.actualCriticalDamage = criticaldamage;
        this.incCriticalDamage = inccriticaldamage;
        this.healthsteal = healthsteal;
        this.actualHealthsteal = healthsteal;
        this.incHealthsteal = inchealthsteal;

        //Cada nuevo nivel adquirido aumenta las probabilidades de cada atributo del arma
        //Por defecto es cero y los valores actuales seran los basicos del arma
        if (damage > 0 && incdamage > 0) {
            this.actualDamage = damage + incdamage * weaponLevel;
        }
        if (criticalp > 0 && inccriticalp > 0) {
            this.actualCriticalp = criticalp + inccriticalp * weaponLevel;
        }
        if (criticaldamage > 0 && inccriticaldamage > 0) {
            this.actualCriticalDamage = criticaldamage + inccriticaldamage * weaponLevel;
        }
        if (healthsteal > 0 && inchealthsteal > 0) {
            this.actualHealthsteal = healthsteal + inchealthsteal * weaponLevel;
        }

        //Modificacion del item
        ItemMeta iMeta = item.getItemMeta();
        //Añadimos nombre descriptivo al arma hay que tener especial cuidado con nombres de mas de una palabra
        iMeta.setDisplayName(quality.getColor() + "[LVL" + level + "] " + name + " +" + weaponLevel);
        //Y preparamos el lore del arma actual
        List<String> lores = new ArrayList<>();
        if (actualDamage > 0) {
            lores.add("Attack Damage +" + actualDamage);
        }
        if (actualCriticalp > 0) {
            lores.add("Crit. Pct. +" + actualCriticalp + "%");
        }
        if (actualCriticalDamage > 0) {
            lores.add("Crit. Dam. +" + actualCriticalDamage);
        }
        if (actualHealthsteal > 0) {
            lores.add("Health Steal +" + actualHealthsteal + "%");
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
        if (this.comerciable) {
            lores.add("Buy price: " + buyPrice + "$");
            lores.add("Sell price: " + sellPrice + "$");
        } else {
            lores.add(ChatColor.RED + "No comerciable");
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

    public void setItem(ItemStack item) {
        this.item = item;
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

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getCriticalp() {
        return criticalp;
    }

    public void setCriticalp(double criticalp) {
        this.criticalp = criticalp;
    }

    public double getCriticaldamage() {
        return criticaldamage;
    }

    public void setCriticaldamage(double criticaldamage) {
        this.criticaldamage = criticaldamage;
    }

    public double getHealthsteal() {
        return healthsteal;
    }

    public void setHealthsteal(double healthsteal) {
        this.healthsteal = healthsteal;
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

    public ItemStack upgradeWeapon(ItemStack weapon) {
        ItemMeta wMeta = weapon.getItemMeta();
        String wLongName = wMeta.getDisplayName();
        String[] wLongNameParts = wLongName.split(" ");

        int sizeLongNameParts = wLongNameParts.length;
        String wName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            wName += wLongNameParts[i] + " ";
        }
        //wName = wLongNameParts[1];
        int nivelactual = getLevel(wLongNameParts[sizeLongNameParts - 1]);
        int nivelup = nivelactual + 1;
        String displayname = wLongNameParts[0] + " " + wName + "+" + nivelup;
        wMeta.setDisplayName(displayname);
        if (wMeta.hasLore()) {
            List<String> lores = wMeta.getLore();
            List<String> newLores = new ArrayList<>();
            for (String lore : lores) {
                //Separar el lore en partes
                String[] loreparts = lore.split(" ");
                String newLore = loreparts[0] + " " + loreparts[1];
                //Con newLore tenemos el string que necesitamos para comparar con los posibles atributos
                switch (newLore) {
                    case "Attack Damage":
                        newLores.add(newLore + " +" + (this.damage + incDamage * nivelup));
                        break;
                    case "Crit. pct.":
                        newLores.add(newLore + " +" + (this.criticalp + incCriticalp * nivelup) + "%");
                        break;
                    case "Crit. Dam.":
                        newLores.add(newLore + " +" + (this.criticaldamage + incCriticalDamage * nivelup));
                        break;
                    case "Health Steal":
                        newLores.add(newLore + " +" + (this.healthsteal + incHealthsteal * nivelup) + "%");
                        break;
                    case "XP Bonus":
                        newLores.add(newLore + " +" + (this.XPBonus) + "%");
                        break;
                    case "AP Bonus":
                        newLores.add(newLore + " +" + (this.APBonus) + "%");
                        break;
                    case "Money Bonus":
                        newLores.add(newLore + " +" + (this.moneyBonus) + "%");
                        break;
                }
            }
            if (isComerciable()) {
                newLores.add("Buy price: " + buyPrice + "$");
                newLores.add("Sell price: " + sellPrice + "$");
            } else {
                newLores.add(ChatColor.RED + "No comerciable");
            }
            wMeta.setLore(newLores);
            weapon.setItemMeta(wMeta);
        }
        return weapon;
    }

    ItemStack downgradeWeapon(ItemStack weapon) {
        ItemMeta wMeta = weapon.getItemMeta();
        String wLongName = wMeta.getDisplayName();
        String[] wLongNameParts = wLongName.split(" ");
        int sizeLongNameParts = wLongNameParts.length;
        //wLongNameParts[0] equivale a [LVLXX]
        //wLongNameParts[size-1] equivale al modificador
        //MODIFICAR NOMBRE
        String wName = "";
        if (sizeLongNameParts > 3) {
            for (int i = 1; i <= sizeLongNameParts - 3; i++) {
                wName += wLongNameParts[i] + " ";
            }
            wName += wLongNameParts[sizeLongNameParts - 2];
        } else {
            wName = wLongNameParts[1];
        }
        int nivelactual = getLevel(wLongNameParts[sizeLongNameParts - 1]);
        int niveldown = nivelactual - 1;
        String displayname = wLongNameParts[0] + " " + wName + " +" + niveldown;
        wMeta.setDisplayName(displayname);
        if (wMeta.hasLore()) {
            List<String> lores = wMeta.getLore();
            List<String> newLores = new ArrayList<>();
            for (String lore : lores) {
                //Separar el lore en partes
                String[] loreparts = lore.split(" ");
                String newLore = loreparts[0] + " " + loreparts[1];
                //Con newLore tenemos el string que necesitamos para comparar con los posibles atributos
                switch (newLore) {
                    case "Attack Damage":
                        newLores.add(newLore + " +" + (this.damage + incDamage * niveldown));
                        break;
                    case "Crit. pct.":
                        newLores.add(newLore + " +" + (this.criticalp + incCriticalp * niveldown) + "%");
                        break;
                    case "Crit. Dam.":
                        newLores.add(newLore + " +" + (this.criticaldamage + incCriticalDamage * niveldown));
                        break;
                    case "Health Steal":
                        newLores.add(newLore + " +" + (this.healthsteal + incHealthsteal * niveldown) + "%");
                        break;
                    case "XP Bonus":
                        newLores.add(newLore + " +" + (this.XPBonus) + "%");
                        break;
                    case "AP Bonus":
                        newLores.add(newLore + " +" + (this.APBonus) + "%");
                        break;
                    case "Money Bonus":
                        newLores.add(newLore + " +" + (this.moneyBonus) + "%");
                        break;
                }
            }
            if (isComerciable()) {
                newLores.add("Buy price: " + buyPrice + "$");
                newLores.add("Sell price: " + sellPrice + "$");
            } else {
                newLores.add(ChatColor.RED + "No comerciable");
            }
            wMeta.setLore(newLores);
            weapon.setItemMeta(wMeta);
        }
        return weapon;
    }

    public int getLevel(String actualLevel) {
        System.out.println(" " + actualLevel);
        return Integer.parseInt(actualLevel.substring(1));
    }

    private boolean isComerciable() {
        return this.comerciable;
    }

}
