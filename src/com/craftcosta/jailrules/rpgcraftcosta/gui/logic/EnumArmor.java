/*
 * Copyright (C) 2016 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;

/**
 *
 * @author jail
 */
public enum EnumArmor {
    LEATHER_HELMET(RPGFinals.itemImagePackage +"leather_helmet.png","LEATHER_HELMET",298,0),
    LEATHER_CHESTPLATE(RPGFinals.itemImagePackage +"leather_chestplate.png","LEATHER_CHESTPLATE",299,0),
    LEATHER_LEGGINGS(RPGFinals.itemImagePackage +"leather_leggings.png","LEATHER_LEGGINGS",300,0),
    LEATHER_BOOTS(RPGFinals.itemImagePackage +"leather_boots.png","LEATHER_BOOTS",301,0),
    
    IRON_HELMET(RPGFinals.itemImagePackage +"iron_helmet.png","IRON_HELMET",306,0),
    IRON_CHESTPLATE(RPGFinals.itemImagePackage +"iron_chestplate.png","IRON_CHESTPLATE",307,0),
    IRON_LEGGINGS(RPGFinals.itemImagePackage +"iron_leggings.png","IRON_LEGGINGS",308,0),
    IRON_BOOTS(RPGFinals.itemImagePackage +"iron_boots.png","IRON_BOOTS",309,0),
    
    GOLD_HELMET(RPGFinals.itemImagePackage +"gold_helmet.png","GOLDEN_HELMET",314,0),
    GOLD_CHESTPLATE(RPGFinals.itemImagePackage +"gold_chestplate.png","GOLDEN_CHESTPLATE",315,0),
    GOLD_LEGGINGS(RPGFinals.itemImagePackage +"gold_leggings.png","GOLDEN_LEGGINGS",316,0),
    GOLD_BOOTS(RPGFinals.itemImagePackage +"gold_boots.png","GOLDEN_BOOTS",317,0),
    
    DIAMOND_HELMET(RPGFinals.itemImagePackage +"diamond_helmet.png","DIAMOND_HELMET",310,0),
    DIAMOND_CHESTPLATE(RPGFinals.itemImagePackage +"diamond_chestplate.png","DIAMOND_CHESTPLATE",311,0),
    DIAMOND_LEGGINGS(RPGFinals.itemImagePackage +"diamond_leggings.png","DIAMOND_LEGGINGS",312,0),
    DIAMOND_BOOTS(RPGFinals.itemImagePackage +"diamond_boots.png","DIAMOND_BOOTS",313,0),
    
    CHAINMAIL_HELMET(RPGFinals.itemImagePackage +"chainmail_helmet.png","CHAINMAIL_HELMET",302,0),
    CHAINMAIL_CHESTPLATE(RPGFinals.itemImagePackage +"chainmail_chestplate.png","CHAINMAIL_CHESTPLATE",303,0),
    CHAINMAIL_LEGGINGS(RPGFinals.itemImagePackage +"chainmail_leggings.png","CHAINMAIL_LEGGINGS",304,0),
    CHAINMAIL_BOOTS(RPGFinals.itemImagePackage +"chainmail_boots.png","CHAINMAIL_BOOTS",305,0);
    
    String path;
    String material;
    int id;
    int datavalue;
   

    private EnumArmor(String path,String mat,int id,int datavalue) {
        this.path = path;
        this.material=mat;
        this.id=id;
        this.datavalue=datavalue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(int datavalue) {
        this.datavalue = datavalue;
    }
    
    
}
