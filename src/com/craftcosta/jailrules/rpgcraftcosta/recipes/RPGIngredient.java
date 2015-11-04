/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.recipes;

import org.bukkit.Material;

/**
 *
 * @author jail
 */
public class RPGIngredient {

    private Material material;
    private int count;
    private int rawData;

    public RPGIngredient(Material material, int count, int rawData) {
        this.material = material;
        this.count = count;
        this.rawData = rawData;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRawData() {
        return rawData;
    }

    public void setRawData(int rawData) {
        this.rawData = rawData;
    }

    @Override
    public String toString() {
        return "RPGIngredient{" + "material=" + material.toString() + ", count=" + count + ", rawData=" + rawData + '}';
    }

}
