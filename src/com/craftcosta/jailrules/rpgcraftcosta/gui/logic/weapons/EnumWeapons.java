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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.weapons;

import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;

/**
 *
 * @author jail
 */
public enum EnumWeapons {

    /**
     *
     */
    WOOD_SWORD(RPGFinals.itemImagePackage +"wooden_sword.png","WOOD_SWORD",268,0),

    /**
     *
     */
    WOOD_SPADE(RPGFinals.itemImagePackage +"wooden_shovel.png","WOOD_SPADE",269,0),

    /**
     *
     */
    WOOD_PICKAXE(RPGFinals.itemImagePackage +"wooden_pickaxe.png","WOOD_PICKAXE",270,0),

    /**
     *
     */
    WOOD_AXE(RPGFinals.itemImagePackage +"wooden_axe.png","WOOD_AXE",271,0),

    /**
     *
     */
    WOOD_HOE(RPGFinals.itemImagePackage +"wooden_hoe.png","WOOD_HOE",290,0),
    
    /**
     *
     */
    STONE_SWORD(RPGFinals.itemImagePackage +"stone_sword.png","STONE_SWORD",272,0),

    /**
     *
     */
    STONE_SHOVEL(RPGFinals.itemImagePackage +"stone_shovel.png","STONE_SHOVEL",273,0),

    /**
     *
     */
    STONE_PICKAXE(RPGFinals.itemImagePackage +"stone_pickaxe.png","STONE_PICKAXE",274,0),

    /**
     *
     */
    STONE_AXE(RPGFinals.itemImagePackage +"stone_axe.png","STONE_AXE",275,0),

    /**
     *
     */
    STONE_HOE(RPGFinals.itemImagePackage +"stone_hoe.png","STONE_HOE",291,0),
    
    /**
     *
     */
    IRON_SPADE(RPGFinals.itemImagePackage +"iron_shovel.png","IRON_SHOVEL",256,0),

    /**
     *
     */
    IRON_PICKAXE(RPGFinals.itemImagePackage +"iron_pickaxe.png","IRON_PICKAXE",257,0),

    /**
     *
     */
    IRON_AXE(RPGFinals.itemImagePackage +"iron_axe.png","IRON_AXE",258,0),

    /**
     *
     */
    IRON_SWORD(RPGFinals.itemImagePackage +"iron_sword.png","IRON_SWORD",267,0),

    /**
     *
     */
    IRON_HOE(RPGFinals.itemImagePackage +"iron_hoe.png","IRON_HOE",292,0),
    
    /**
     *
     */
    DIAMOND_SWORD(RPGFinals.itemImagePackage +"diamond_sword.png","DIAMOND_SWORD",276,0),

    /**
     *
     */
    DIAMOND_SHOVEL(RPGFinals.itemImagePackage +"diamond_shovel.png","DIAMOND_SPADE",277,0),

    /**
     *
     */
    DIAMOND_PICKAXE(RPGFinals.itemImagePackage +"diamond_pickaxe.png","DIAMOND_PICKAXE",278,0),

    /**
     *
     */
    DIAMOND_AXE(RPGFinals.itemImagePackage +"diamond_axe.png","DIAMOND_AXE",279,0),

    /**
     *
     */
    DIAMOND_HOE(RPGFinals.itemImagePackage +"diamond_hoe.png","DIAMOND_HOE",293,0),
    
    /**
     *
     */
    GOLD_HOE(RPGFinals.itemImagePackage +"golde_hoe.png","GOLDEN_HOE",294,0),

    /**
     *
     */
    GOLD_SWORD(RPGFinals.itemImagePackage +"golden_sword.png","GOLDEN_SWORD",283,0),

    /**
     *
     */
    GOLD_SPADE(RPGFinals.itemImagePackage +"golden_shovel.png","GOLDEN_SHOVEL",284,0),

    /**
     *
     */
    GOLD_PICKAXE(RPGFinals.itemImagePackage +"golden_pickaxe.png","GOLDEN_PICKAXE",285,0),

    /**
     *
     */
    GOLD_AXE(RPGFinals.itemImagePackage +"golden_axe.png","GOLDEN_AXE",286,0);
    
    String path;
    String material;
    int id;
    int datavalue;
   

    private EnumWeapons(String path,String mat,int id,int datavalue) {
        this.path = path;
        this.material=mat;
        this.id=id;
        this.datavalue=datavalue;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     * @return
     */
    public String getMaterial() {
        return material;
    }

    /**
     *
     * @param material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getDatavalue() {
        return datavalue;
    }

    /**
     *
     * @param datavalue
     */
    public void setDatavalue(int datavalue) {
        this.datavalue = datavalue;
    }
}
