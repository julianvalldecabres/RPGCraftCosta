/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources;


/**
 *
 * @author jail
 */
public enum EnumWeapon {
    WOOD_SWORD("logic/resources/items/"+"wooden_sword.png","WOOD_SWORD",268,0),
    WOOD_SPADE("logic/resources/items/"+"wooden_shovel.png","WOOD_SPADE",269,0),
    WOOD_PICKAXE("logic/resources/items/"+"wooden_pickaxe.png","WOOD_PICKAXE",270,0),
    WOOD_AXE("logic/resources/items/"+"wooden_axe.png","WOOD_AXE",271,0),
    WOOD_HOE("logic/resources/items/"+"wooden_hoe.png","WOOD_HOE",290,0),
    
    STONE_SWORD("logic/resources/items/"+"stone_sword.png","STONE_SWORD",272,0),
    STONE_SHOVEL("logic/resources/items/"+"stone_shovel.png","STONE_SHOVEL",273,0),
    STONE_PICKAXE("logic/resources/items/"+"stone_pickaxe.png","STONE_PICKAXE",274,0),
    STONE_AXE("logic/resources/items/"+"stone_axe.png","STONE_AXE",275,0),
    STONE_HOE("logic/resources/items/"+"stone_hoe.png","STONE_HOE",291,0),
    
    IRON_SPADE("logic/resources/items/"+"iron_shovel.png","IRON_SHOVEL",256,0),
    IRON_PICKAXE("logic/resources/items/"+"iron_pickaxe.png","IRON_PICKAXE",257,0),
    IRON_AXE("logic/resources/items/"+"iron_axe.png","IRON_AXE",258,0),
    IRON_SWORD("logic/resources/items/"+"iron_sword.png","IRON_SWORD",267,0),
    IRON_HOE("logic/resources/items/"+"iron_hoe.png","IRON_HOE",292,0),
    
    DIAMOND_SWORD("logic/resources/items/"+"diamond_sword.png","DIAMOND_SWORD",276,0),
    DIAMOND_SHOVEL("logic/resources/items/"+"diamond_shovel.png","DIAMOND_SPADE",277,0),
    DIAMOND_PICKAXE("logic/resources/items/"+"diamond_pickaxe.png","DIAMOND_PICKAXE",278,0),
    DIAMOND_AXE("logic/resources/items/"+"diamond_axe.png","DIAMOND_AXE",279,0),
    DIAMOND_HOE("logic/resources/items/"+"diamond_hoe.png","DIAMOND_HOE",293,0),
    
    GOLD_HOE("logic/resources/items/"+"golden_hoe.png","GOLDEN_HOE",294,0),
    GOLD_SWORD("logic/resources/items/"+"golden_sword.png","GOLDEN_SWORD",283,0),
    GOLD_SPADE("logic/resources/items/"+"golden_shovel.png","GOLDEN_SHOVEL",284,0),
    GOLD_PICKAXE("logic/resources/items/"+"golden_pickaxe.png","GOLDEN_PICKAXE",285,0),
    GOLD_AXE("logic/resources/items/"+"golden_axe.png","GOLDEN_AXE",286,0);
            
    String path;
    String material;
    int id;
    int datavalue;
   

    private EnumWeapon(String path,String mat,int id,int datavalue) {
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