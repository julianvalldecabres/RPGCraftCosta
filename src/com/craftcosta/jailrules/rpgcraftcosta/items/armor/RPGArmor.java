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

    /**
     *
     * @param level
     * @param item
     * @param quality
     * @param upgradable
     * @param comerciable
     * @param armorlevel
     * @param material
     * @param setName
     * @param physicaldefense
     * @param incphysicaldefense
     * @param physicalevasion
     * @param incphysicalevasion
     * @param magicaldefense
     * @param incmagicaldefense
     * @param magicalevasion
     * @param incmagicalevasion
     * @param moneybonus
     * @param apbonus
     * @param xpbonus
     * @param buyprice
     * @param sellprice
     */
    public RPGArmor(int level, ItemStack item, Quality quality, boolean upgradable, boolean comerciable, int armorlevel, Material material, String setName, double physicaldefense, double incphysicaldefense, double physicalevasion, double incphysicalevasion, double magicaldefense, double incmagicaldefense, double magicalevasion, double incmagicalevasion, double moneybonus, double xpbonus, int buyprice, int sellprice) {
        this.setName = setName;
        this.material = material;
        this.quality = quality;
        this.comerciable = comerciable;
        this.buyPrice = buyprice;
        this.sellPrice = sellprice;

        this.level = level;
        this.upgradable = upgradable;
        this.armorlevel = armorlevel;

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
    public int getArmorlevel() {
        return armorlevel;
    }

    /**
     *
     * @param armorlevel
     */
    public void setArmorlevel(int armorlevel) {
        this.armorlevel = armorlevel;
    }

    /**
     *
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *
     * @param material
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     *
     * @return
     */
    public String getSetName() {
        return setName;
    }

    /**
     *
     * @param setName
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    /**
     *
     * @return
     */
    public double getPhysicalDefense() {
        return this.physicaldefense;
    }

    /**
     *
     * @param physicaldefense
     */
    public void setPhysicalDefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    /**
     *
     * @return
     */
    public double getIncPhysicalDefense() {
        return this.incphysicaldefense;
    }

    /**
     *
     * @param incphysicaldefense
     */
    public void setIncPhysicalDefense(double incphysicaldefense) {
        this.incphysicaldefense = incphysicaldefense;
    }

    /**
     *
     * @return
     */
    public double getPhysicalEvasion() {
        return this.physicalevasion;
    }

    /**
     *
     * @param physicalevasion
     */
    public void setPhysicalEvasion(double physicalevasion) {
        this.physicalevasion = physicalevasion;
    }

    /**
     *
     * @return
     */
    public double getIncPhysicalEvasion() {
        return this.incphysicalevasion;
    }

    /**
     *
     * @param incPhysicalEvasion
     */
    public void setIncPhysicalEvasion(double incPhysicalEvasion) {
        this.incphysicalevasion = incPhysicalEvasion;
    }

    /**
     *
     * @return
     */
    public double getActualPhysicalDefense() {
        return actualphysicaldefense;
    }

    /**
     *
     * @return
     */
    public double getActualPhysicalEvasion() {
        return actualphysicalevasion;
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

    public double getPhysicaldefense() {
        return physicaldefense;
    }

    public void setPhysicaldefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    public double getMagicaldefense() {
        return magicaldefense;
    }

    public void setMagicaldefense(double magicaldefense) {
        this.magicaldefense = magicaldefense;
    }

    public double getIncphysicaldefense() {
        return incphysicaldefense;
    }

    public void setIncphysicaldefense(double incphysicaldefense) {
        this.incphysicaldefense = incphysicaldefense;
    }

    public double getIncmagicaldefense() {
        return incmagicaldefense;
    }

    public void setIncmagicaldefense(double incmagicaldefense) {
        this.incmagicaldefense = incmagicaldefense;
    }

    public double getPhysicalevasion() {
        return physicalevasion;
    }

    public void setPhysicalevasion(double physicalevasion) {
        this.physicalevasion = physicalevasion;
    }

    public double getMagicalevasion() {
        return magicalevasion;
    }

    public void setMagicalevasion(double magicalevasion) {
        this.magicalevasion = magicalevasion;
    }

    public double getIncmagicalevasion() {
        return incmagicalevasion;
    }

    public void setIncmagicalevasion(double incmagicalevasion) {
        this.incmagicalevasion = incmagicalevasion;
    }

    public double getIncphysicalevasion() {
        return incphysicalevasion;
    }

    public void setIncphysicalevasion(double incphysicalevasion) {
        this.incphysicalevasion = incphysicalevasion;
    }

    public double getActualphysicaldefense() {
        return actualphysicaldefense;
    }

    public void setActualphysicaldefense(double actualphysicaldefense) {
        this.actualphysicaldefense = actualphysicaldefense;
    }

    public double getActualphysicalevasion() {
        return actualphysicalevasion;
    }

    public void setActualphysicalevasion(double actualphysicalevasion) {
        this.actualphysicalevasion = actualphysicalevasion;
    }

    public double getActualmagicaldefense() {
        return actualmagicaldefense;
    }

    public void setActualmagicaldefense(double actualmagicaldefense) {
        this.actualmagicaldefense = actualmagicaldefense;
    }

    public double getActualmagicalevasion() {
        return actualmagicalevasion;
    }

    public void setActualmagicalevasion(double actualmagicalevasion) {
        this.actualmagicalevasion = actualmagicalevasion;
    }

}
