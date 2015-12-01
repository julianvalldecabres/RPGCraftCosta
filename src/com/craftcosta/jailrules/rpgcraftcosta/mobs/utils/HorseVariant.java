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
public enum HorseVariant {

    WHITE(0, "White Horse"), CREAMY(1, "Creamy Horse"), CHESTNUT(2, "Chestnut Horse"), BROWN(3, "Brown Horse"), BLACK(4, "Black Horse"), GRAY(5, "Gray Horse"), DARKBROWN(6, "Dark Brown Horse"), INVISIBLE(7, "Invisible Horse"),
    WHITEWM(256, "White Horse with White Marks"), CREAMYWM(257, "Creamy Horse with White Marks"), CHESTNUTWM(258, "Chestnut Horse with White Marks"), BROWNWM(259, "Brown Horse with White Marks"), BLACKWM(260, "Black Horse with White Marks"), GRAYWM(261, "Gray Horse with White Marks"), DARKBROWNWM(262, "Dark Brown Horse with White Marks"),
    WHITEWF(512, "White Horse with White Fields"), CREAMYWF(513, "Creamy Horse with White Fields"), CHESTNUTWF(514, "Chestnut Horse with White Fields"), BROWNWF(515, "Brown Horse with White Fields"), BLACKWF(516, "Black Horse with White Fields"), GRAYWF(517, "Gray Horse with White Fields"), DARKBROWNWF(518, "Dark Brown Horse with White Fields"),
    WHITEWD(768, "White Horse with White Dots"), CREAMYWD(769, "Creamy Horse with White Dots"), CHESTNUTWD(770, "Chestnut Horse with White Dots"), BROWNWD(771, "Brown Horse with White Dots"), BLACKWD(772, "Black Horse with White Dots"), GRAYWD(773, "Gray Horse with White Dots"), DARKBROWNWD(774, "Dark Brown Horse with White Dots"),
    WHITEBD(1024, "White Horse with Black Dots"), CREAMYBD(1025, "Creamy Horse with Black Dots"), CHESTNUTBD(1026, "Chestnut Horse with Black Dots"), BROWNBD(1027, "Brown Horse with Black Dots"), BLACKBD(1028, "Black Horse with Black Dots"), GRAYBD(1029, "Gray Horse with Black Dots"), DARKBROWNBD(1030, "Dark Brown Horse with Black Dots");
    private String name;
    private int num;

    private HorseVariant(int n, String name) {
        this.name = name;
        this.num = n;
    }

    public int getNumber() {
        return this.num;
    }

    public static HorseVariant getHorseVariant(String name) {
        for (HorseVariant variant : HorseVariant.values()) {
            if (variant.name().equalsIgnoreCase(name)) {
                return variant;
            }
        }
        return null;
    }

    public static HorseVariant[] getAllHorseVariants() {
        return HorseVariant.values();
    }

}
