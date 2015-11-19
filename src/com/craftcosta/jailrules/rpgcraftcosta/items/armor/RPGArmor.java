/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.armor;

import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItem;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLores;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGArmor extends RPGItem {

    private int level;
    private boolean upgradable;
    private int armorlevel;

    private Material material;
    private String setName;// Vamos a ver si nos hace falta
    private double physicaldefense;
    private double magicaldefense;
    private double incphysicaldefense;
    private double incmagicaldefense;
    private double physicalevasion;
    private double magicalevasion;
    private double incmagicalevasion;
    private double incphysicalevasion;
    private double actualphysicaldefense;
    private double actualphysicalevasion;
    private double actualmagicaldefense;
    private double actualmagicalevasion;

    public RPGArmor(int level, ItemStack item, Quality quality, boolean upgradable, boolean comerciable, int armorlevel, Material material, String setName, double physicaldefense, double incphysicaldefense, double physicalevasion, double incphysicalevasion, double magicaldefense, double incmagicaldefense, double magicalevasion, double incmagicalevasion, double moneybonus, double apbonus, double xpbonus, int buyprice, int sellprice) {
        this.setName = setName;
        this.material = material;
        this.quality = quality;
        this.comerciable = comerciable;
        this.buyPrice = buyprice;
        this.sellPrice = sellprice;

        this.level = level;
        this.upgradable = upgradable;
        this.armorlevel = armorlevel;
        this.APBonus = apbonus;
        this.XPBonus = xpbonus;
        this.moneyBonus = moneybonus;
        this.magicaldefense = magicaldefense;
        this.incmagicaldefense = incmagicaldefense;
        this.magicalevasion = magicalevasion;
        this.incmagicalevasion = incmagicalevasion;
        this.physicaldefense = physicaldefense;
        this.incphysicaldefense = incphysicaldefense;
        this.physicalevasion = physicalevasion;
        this.incphysicalevasion = incphysicalevasion;
        if (physicaldefense > 0 && incphysicaldefense > 0) {
            this.actualphysicaldefense = physicaldefense + incphysicaldefense * armorlevel;
        }
        if (physicalevasion > 0 && incphysicalevasion > 0) {
            this.actualphysicalevasion = physicalevasion + incphysicalevasion * armorlevel;
        }
        if (magicaldefense > 0 && incmagicaldefense > 0) {
            this.actualmagicaldefense = magicaldefense + incmagicaldefense * armorlevel;
        }
        if (magicalevasion > 0 && incmagicalevasion > 0) {
            this.actualmagicalevasion = magicalevasion + incmagicalevasion * armorlevel;
        }
        String[] matparts = material.toString().split("_");
        ItemMeta aMeta = item.getItemMeta();
        aMeta.setDisplayName(quality.getColor() + "[LVL" + level + "] " + setName + " " + matparts[1].toLowerCase() + " +" + armorlevel);
        List<String> newLores = new ArrayList<>();
        if (actualphysicaldefense > 0) {
            newLores.add(RPGLores.PHYSICALDEFENSE.getLoreString(RPGLores.PHYSICALDEFENSE, actualphysicaldefense));
        }
        if (actualphysicalevasion > 0) {
            newLores.add(RPGLores.PHYSICALEVASION.getLoreString(RPGLores.PHYSICALEVASION, actualphysicalevasion));
        }
        if (actualmagicaldefense > 0) {
            newLores.add(RPGLores.MAGICALDEFENSE.getLoreString(RPGLores.MAGICALDEFENSE, actualmagicaldefense));
        }
        if (actualmagicalevasion > 0) {
            newLores.add(RPGLores.MAGICALEVASION.getLoreString(RPGLores.MAGICALEVASION, actualmagicalevasion));
        }
        if (XPBonus > 0) {
            newLores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, XPBonus));
        }
        if (APBonus > 0) {
            newLores.add(RPGLores.APBONUS.getLoreString(RPGLores.APBONUS, APBonus));
        }
        if (moneyBonus > 0) {
            newLores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, moneyBonus));
        }
        if (this.comerciable) {
            newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, buyPrice));
            newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, sellPrice));
        } else {
            newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
        }
        if (!this.upgradable) {
            newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
        }
        aMeta.setLore(newLores);
        item.setItemMeta(aMeta);
        this.item = item;
    }

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

    public int getArmorlevel() {
        return armorlevel;
    }

    public void setArmorlevel(int armorlevel) {
        this.armorlevel = armorlevel;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public double getPhysicalDefense() {
        return this.physicaldefense;
    }

    public void setPhysicalDefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    public double getIncPhysicalDefense() {
        return this.incphysicaldefense;
    }

    public void setIncPhysicalDefense(double incphysicaldefense) {
        this.incphysicaldefense = incphysicaldefense;
    }

    public double getPhysicalEvasion() {
        return this.physicalevasion;
    }

    public void setPhysicalEvasion(double physicalevasion) {
        this.physicalevasion = physicalevasion;
    }

    public double getIncPhysicalEvasion() {
        return this.incphysicalevasion;
    }

    public void setIncPhysicalEvasion(double incPhysicalEvasion) {
        this.incphysicalevasion = incPhysicalEvasion;
    }

    public double getActualPhysicalDefense() {
        return actualphysicaldefense;
    }

    public double getActualPhysicalEvasion() {
        return actualphysicalevasion;
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

    @Override
    public String toString() {
        return "Weapon :\n"
                + "\tname: " + getName()
                + "\n\ttype: " + item.getType().toString()
                + "\n\tquality: " + quality.toString()
                + "\n\tlevel required: " + getLevel()
                + "\n\tupgradable: " + isUpgradable();
    }

    public ItemStack upgradeArmor(ItemStack armor) {
        ItemMeta aMeta = armor.getItemMeta();
        String aLongName = aMeta.getDisplayName();
        String[] aLongNameParts = aLongName.split(" ");

        int sizeLongNameParts = aLongNameParts.length;
        String aName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            aName += aLongNameParts[i] + " ";
        }
        int nivelactual = getLevel(aLongNameParts[sizeLongNameParts - 1]);
        int nivelup = nivelactual + 1;
        String displayname = aLongNameParts[0] + " " + aName + "+" + nivelup;
        aMeta.setDisplayName(displayname);
        if (aMeta.hasLore()) {
            List<String> lores = aMeta.getLore();
            List<String> newLores = new ArrayList<>();
            for (String lore : lores) {
                String[] loreparts = lore.split(" ");
                String newLore = loreparts[0] + " " + loreparts[1];
                switch (newLore) {
                    case "Physical Defense":
                        newLores.add(RPGLores.PHYSICALDEFENSE.getLoreString(RPGLores.PHYSICALDEFENSE, this.physicaldefense + incphysicaldefense * nivelup));
                        break;
                    case "Physical Evasion":
                        newLores.add(RPGLores.PHYSICALEVASION.getLoreString(RPGLores.PHYSICALEVASION, this.physicalevasion + incphysicalevasion * nivelup));
                        break;
                    case "Magical Defense":
                        newLores.add(RPGLores.MAGICALDEFENSE.getLoreString(RPGLores.MAGICALDEFENSE, this.magicaldefense + incmagicaldefense * nivelup));
                        break;
                    case "Magical Evasion":
                        newLores.add(RPGLores.MAGICALEVASION.getLoreString(RPGLores.MAGICALEVASION, this.magicalevasion + incmagicalevasion * nivelup));
                        break;
                    case "XP Bonus":
                        newLores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, XPBonus));
                        break;
                    case "AP Bonus":
                        newLores.add(RPGLores.APBONUS.getLoreString(RPGLores.APBONUS, APBonus));
                        break;
                    case "Money Bonus":
                        newLores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, moneyBonus));
                        break;
                }
            }
            if (isComerciable()) {
                newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, buyPrice));
                newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, sellPrice));
            } else {
                newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
            }
            if (!isUpgradable()) {
                newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
            }
            aMeta.setLore(newLores);
            armor.setItemMeta(aMeta);
        }
        return armor;
    }

    public ItemStack downgradeArmor(ItemStack armor) {
        ItemMeta aMeta = armor.getItemMeta();
        String aLongName = aMeta.getDisplayName();
        String[] aLongNameParts = aLongName.split(" ");

        int sizeLongNameParts = aLongNameParts.length;
        String aName = "";
        for (int i = 1; i < sizeLongNameParts - 1; i++) {
            aName += aLongNameParts[i] + " ";
        }
        int nivelactual = getLevel(aLongNameParts[sizeLongNameParts - 1]);
        int niveldown = nivelactual - 1;
        String displayname = aLongNameParts[0] + " " + aName + "+" + niveldown;
        aMeta.setDisplayName(displayname);
        if (aMeta.hasLore()) {
            List<String> lores = aMeta.getLore();
            List<String> newLores = new ArrayList<>();
            for (String lore : lores) {
                String[] loreparts = lore.split(" ");
                String newLore = loreparts[0] + " " + loreparts[1];
                switch (newLore) {
                    case "Physical Defense":
                        newLores.add(RPGLores.PHYSICALDEFENSE.getLoreString(RPGLores.PHYSICALDEFENSE, this.physicaldefense + incphysicaldefense * niveldown));
                        break;
                    case "Physical Evasion":
                        newLores.add(RPGLores.PHYSICALEVASION.getLoreString(RPGLores.PHYSICALEVASION, this.physicalevasion + incphysicalevasion * niveldown));
                        break;
                    case "Magical Defense":
                        newLores.add(RPGLores.MAGICALDEFENSE.getLoreString(RPGLores.MAGICALDEFENSE, this.magicaldefense + incmagicaldefense * niveldown));
                        break;
                    case "Magical Evasion":
                        newLores.add(RPGLores.MAGICALEVASION.getLoreString(RPGLores.MAGICALEVASION, this.magicalevasion + incmagicalevasion * niveldown));
                        break;
                    case "XP Bonus":
                        newLores.add(RPGLores.XPBONUS.getLoreString(RPGLores.XPBONUS, XPBonus));
                        break;
                    case "AP Bonus":
                        newLores.add(RPGLores.APBONUS.getLoreString(RPGLores.APBONUS, APBonus));
                        break;
                    case "Money Bonus":
                        newLores.add(RPGLores.MONEYBONUS.getLoreString(RPGLores.MONEYBONUS, moneyBonus));
                        break;
                }
            }
            if (isComerciable()) {
                newLores.add(RPGLores.BUYPRICE.getLoreString(RPGLores.BUYPRICE, buyPrice));
                newLores.add(RPGLores.SELLPRICE.getLoreString(RPGLores.SELLPRICE, sellPrice));
            } else {
                newLores.add(ChatColor.RED + RPGLores.NOCOMERCIABLE.getLoreName());
            }
            if (!isUpgradable()) {
                newLores.add(ChatColor.RED + RPGLores.NOUPGRADABLE.getLoreName());
            }
            aMeta.setLore(newLores);
            armor.setItemMeta(aMeta);
        }
        return armor;
    }

    public int getLevel(String actualLevel) {
        return Integer.parseInt(actualLevel.substring(1));
    }
}
