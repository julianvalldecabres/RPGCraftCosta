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
public enum OcelotType {

    JUNGLE(0), SMOKING(1), TABBY(2), SIAMESE(3);

    private int num;

    private OcelotType(int n) {
        this.num = n;
    }

    public int getNumber() {
        return this.num;
    }

    public String[] getAllOcelotNames() {
        String[] names = new String[4];
        int i = 0;
        for (OcelotType type : OcelotType.values()) {
            names[i] = type.name();
            i++;
        }
        return names;
    }

    public static OcelotType getOcelotType(String name) {
        for (OcelotType type : OcelotType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
