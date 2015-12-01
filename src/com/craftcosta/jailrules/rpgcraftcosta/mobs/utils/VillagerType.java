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
public enum VillagerType {

    //AQUI NOS QUEDAMOS CON LOS VILLAGERS

    FARMER(0),
    LIBRARIAN(1),
    CURE(2),
    BLACKSMITH(3),
    BUTCHER(4);

    private int number;

    private VillagerType(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public static VillagerType getHVillagerType(String name) {
        for (VillagerType vType : VillagerType.values()) {
            if (vType.name().equalsIgnoreCase(name)) {
                return vType;
            }
        }
        return null;
    }

    public static VillagerType[] getAllVillagerTypes() {
        return VillagerType.values();
    }
}
