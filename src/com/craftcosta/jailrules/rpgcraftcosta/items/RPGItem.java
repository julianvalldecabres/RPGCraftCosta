/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public abstract class RPGItem {

    public ItemStack item;//Item propio del RPGItem
    public Quality quality; //Calidad del item cambia el color del item
    public int sellPrice;
    public int buyPrice; //Las dos ultimas lineas del lore seran el precio de venta y compra
    public String name; //displayname
    public double XPBonus; //multiplicador de experiencia
    public double APBonus; //multiplicador de puntos de habilidad
    public double moneyBonus; //multiplicador de dinero
    public boolean comerciable = true;

    public ItemStack getItem() {
        return this.item;
    }

    public boolean isArmor() {
        return (item.getType().equals(Material.CHAINMAIL_BOOTS)
                || item.getType().equals(Material.CHAINMAIL_CHESTPLATE)
                || item.getType().equals(Material.CHAINMAIL_LEGGINGS)
                || item.getType().equals(Material.CHAINMAIL_HELMET)
                || item.getType().equals(Material.DIAMOND_BOOTS)
                || item.getType().equals(Material.DIAMOND_CHESTPLATE)
                || item.getType().equals(Material.DIAMOND_HELMET)
                || item.getType().equals(Material.DIAMOND_LEGGINGS)
                || item.getType().equals(Material.GOLD_BOOTS)
                || item.getType().equals(Material.GOLD_CHESTPLATE)
                || item.getType().equals(Material.GOLD_HELMET)
                || item.getType().equals(Material.GOLD_LEGGINGS)
                || item.getType().equals(Material.IRON_BOOTS)
                || item.getType().equals(Material.IRON_CHESTPLATE)
                || item.getType().equals(Material.IRON_HELMET)
                || item.getType().equals(Material.IRON_LEGGINGS)
                || item.getType().equals(Material.LEATHER_BOOTS)
                || item.getType().equals(Material.LEATHER_CHESTPLATE)
                || item.getType().equals(Material.LEATHER_HELMET)
                || item.getType().equals(Material.LEATHER_LEGGINGS));
    }

    public boolean isPotion() {
        return (item.getType().equals(Material.POTION));
    }
}
