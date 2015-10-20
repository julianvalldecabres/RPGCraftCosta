/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items;

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGItem{
    ItemStack item;//Item propio del RPGItem
    private Quality quality; //Calidad del item cambia el color del item
    private int sellPrice;
    private int buyPrice; //Las dos ultimas lineas del lore seran el precio de venta y compra
    private String name; //displayname
    private int id;      //id para organizar o no...???
    private ItemType iType;//tipo del item herramienta, arma, armadura, consumible
    //Definicion de Lores
    private double armor; //daño que refleja si equipada (SOLO ARMOR)
    private double damage; //daño que da al ser usado (WEAPON,TOOL y POTION)
    private double dodge; //esquivar
    private double block; //bloqueo
    private double criticalp; //probabilidad de critico
    private double criticaldamage; //daño de critico
    private double health;
    private double mana;
    private double healthsteal;
    private double damagereflect;
    private double XPBonus;
    private double MoneyBonus;
    
}
