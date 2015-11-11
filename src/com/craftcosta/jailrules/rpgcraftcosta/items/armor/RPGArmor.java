/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.armor;

import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.items.RPGItem;
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
    private int armor;
    private int incArmor;
    private double evasion;
    private double incEvasion;
    private double block;
    private double incBlock;
    private int actualArmor;
    private double actualEvasion;
    private double actualBlock;

    public RPGArmor(int level, ItemStack item, Quality quality, boolean upgradable, boolean comerciable, int armorlevel, Material material, String setName, int armor, int incArmor, double evasion, double incEvasion, double block, double incBlock, double moneybonus, double apbonus, double xpbonus, int buyprice, int sellprice) {
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

        this.armor = armor;
        this.incArmor = incArmor;
        this.evasion = evasion;
        this.incEvasion = incEvasion;
        this.block = block;
        this.incBlock = incBlock;

        if (armor > 0 && incArmor > 0) {
            this.actualArmor = armor + incArmor * armorlevel;
        }
        if (evasion > 0 && incEvasion > 0) {
            this.actualEvasion = evasion + incEvasion * armorlevel;
        }
        if (block > 0 && incBlock > 0) {
            this.actualBlock = block + incBlock * armorlevel;
        }

        String[] matparts = material.toString().split("_");
        ItemMeta aMeta = item.getItemMeta();
        aMeta.setDisplayName(quality.getColor() + "[LVL" + level + "] " + setName + " " + matparts[1].toLowerCase() + " +" + armorlevel);
        List<String> newLores = new ArrayList<>();
        System.out.println("actualArmor :" + actualArmor + " paramArmor: " + armor + " paramIncArmor" + incArmor + " paramEvasion" + evasion);
        if (actualArmor > 0) {
            newLores.add("Armor Defense +" + actualArmor);
        }
        if (actualBlock > 0) {
            newLores.add("Block Pct. +" + actualBlock + "%");
        }
        if (actualEvasion > 0) {
            newLores.add("Evasion Pct. +" + actualEvasion + "%");
        }
        if (XPBonus > 0) {
            newLores.add("XP Bonus +" + XPBonus + "%");
        }
        if (APBonus > 0) {
            newLores.add("AP Bonus +" + APBonus + "%");
        }
        if (moneyBonus > 0) {
            newLores.add("Money Bonus +" + moneyBonus + "%");
        }
        if (this.comerciable) {
            newLores.add("Buy price: " + buyPrice + "$");
            newLores.add("Sell price: " + sellPrice + "$");
        } else {
            newLores.add(ChatColor.RED + "No comerciable");
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

    public double getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public double getIncArmor() {
        return incArmor;
    }

    public void setIncArmor(int incArmor) {
        this.incArmor = incArmor;
    }

    public double getEvasion() {
        return evasion;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    public double getIncEvasion() {
        return incEvasion;
    }

    public void setIncEvasion(double incEvasion) {
        this.incEvasion = incEvasion;
    }

    public double getBlock() {
        return block;
    }

    public void setBlock(double block) {
        this.block = block;
    }

    public double getIncBlock() {
        return incBlock;
    }

    public void setIncBlock(double incBlock) {
        this.incBlock = incBlock;
    }

    public double getActualArmor() {
        return actualArmor;
    }

    public void setActualArmor(int actualArmor) {
        this.actualArmor = actualArmor;
    }

    public double getActualEvasion() {
        return actualEvasion;
    }

    public void setActualEvasion(double actualEvasion) {
        this.actualEvasion = actualEvasion;
    }

    public double getActualBlock() {
        return actualBlock;
    }

    public void setActualBlock(double actualBlock) {
        this.actualBlock = actualBlock;
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
                System.out.println(loreparts.length);
                String newLore = loreparts[0] + " " + loreparts[1];
                System.out.println(newLore);
                switch (newLore) {
                    case "Armor Defense":
                        newLores.add(newLore + " +" + (this.armor + incArmor * nivelup));
                        break;
                    case "Evasion Pct.":
                        newLores.add(newLore + " +" + (this.evasion + incEvasion * nivelup) + "%");
                        break;
                    case "Block Pct.":
                        newLores.add(newLore + " +" + (this.block + incBlock * nivelup) + "%");
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
                newLores.add(org.bukkit.ChatColor.RED + "No comerciable");
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
                System.out.println(loreparts.length);
                String newLore = loreparts[0] + " " + loreparts[1];
                System.out.println(newLore);
                switch (newLore) {
                    case "Armor Defense":
                        newLores.add(newLore + " +" + (this.armor + incArmor * niveldown));
                        break;
                    case "Evasion Pct.":
                        newLores.add(newLore + " +" + (this.evasion + incEvasion * niveldown) + "%");
                        break;
                    case "Block Pct.":
                        newLores.add(newLore + " +" + (this.block + incBlock * niveldown) + "%");
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
                newLores.add(org.bukkit.ChatColor.RED + "No comerciable");
            }
            aMeta.setLore(newLores);
            armor.setItemMeta(aMeta);
        }
        return armor;
    }

    public int getLevel(String actualLevel) {
        System.out.println(" " + actualLevel);
        return Integer.parseInt(actualLevel.substring(1));
    }
}
