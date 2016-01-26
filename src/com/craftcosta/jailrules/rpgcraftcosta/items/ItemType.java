/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items;

/**
 *
 * @author jail
 */
public enum ItemType {

    WEAPON(0), ARMOR(1), JEWEL(2), POTION(3), FOOD(4), QUESTITEM(5);
    private int num;

    private ItemType(int num) {
        this.num = num;
    }

}
