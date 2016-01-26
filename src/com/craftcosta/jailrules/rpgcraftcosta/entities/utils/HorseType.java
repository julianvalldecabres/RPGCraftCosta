/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities.utils;

/**
 *
 * @author jail
 */
public enum HorseType {

    NORMAL(0), DONKEY(1), MULE(2), ZOMBIE(3), SKELETON(4);
    private int num;

    private HorseType(int n) {
        this.num = n;
    }

    public int getNumber() {
        return this.num;
    }

    public static HorseType getHorseType(String name) {
        for (HorseType type : HorseType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public HorseType[] getAllHorseTypes() {
        return HorseType.values();
    }
}
