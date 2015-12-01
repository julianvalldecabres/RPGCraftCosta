/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.utils;

/**
 *
 * @author jail
 */
public enum EntityAge {

    BABY(-1), ADULT(1);
    private int num;

    private EntityAge(int n) {
        this.num = n;
    }

    public int getNumber() {
        return this.num;
    }

    public static EntityAge getEntityAge(String name) {
        for (EntityAge age : EntityAge.values()) {
            if (age.name().equalsIgnoreCase(name)) {
                return age;
            }
        }
        return null;
    }
}
