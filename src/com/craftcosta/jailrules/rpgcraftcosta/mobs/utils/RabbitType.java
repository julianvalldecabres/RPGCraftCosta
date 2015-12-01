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
public enum RabbitType {

    BROWN(0), WHITE(1), BLACK(2), BLACKWHITE(3), GOLD(4), SALTPEPPER(5), KILLER(99);

    private int num;

    private RabbitType(int n) {
        this.num = n;
    }

    public int getNumber() {
        return this.num;
    }

    public static RabbitType getRabbitType(String name) {
        for (RabbitType type : RabbitType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public static RabbitType[] getAllRabbitTypes() {
        return RabbitType.values();
    }
}
