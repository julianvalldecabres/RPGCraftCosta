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
package com.craftcosta.jailrules.rpgcraftcosta.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public abstract class RPGItem {

    /**
     *
     */
    public ItemStack item;//Item propio del RPGItem

    /**
     *
     */
    public Quality quality; //Calidad del item cambia el color del item

    /**
     *
     */
    public int sellPrice;

    /**
     *
     */
    public int buyPrice; //Las dos ultimas lineas del lore seran el precio de venta y compra

    /**
     *
     */
    public String name; //displayname

    /**
     *
     */
    public double XPBonus; //multiplicador de experiencia

    /**
     *
     */
    public double APBonus; //multiplicador de puntos de habilidad

    /**
     *
     */
    public double moneyBonus; //multiplicador de dinero

    /**
     *
     */
    public boolean comerciable = true;

    /**
     *
     * @return
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     *
     * @param item
     */
    public void setItem(ItemStack item) {
        this.item = item;
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
