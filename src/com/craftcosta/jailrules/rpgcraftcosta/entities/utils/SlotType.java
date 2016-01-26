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
public enum SlotType {

    HELMET("helmet"), LEGGINGS("leggings"), CHESTPLATE("chestplate"), BOOTS("boots"), WEAPON("weapon");

    private String name;

    private SlotType(String text) {
        this.name = text;
    }

    public static SlotType getSlotType(String name) {
        for (SlotType type : SlotType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String toString() {
        return this.name;
    }
}
