/*
 * Copyright (C) 2015 jail
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities.utils;

import net.minecraft.server.v1_8_R3.EnumColor;

/**
 *
 * @author jail
 */
public enum SheepColor {
    BLACK("black",EnumColor.BLACK,EnumColor.BLACK.getColorIndex()),
    BLUE("blue",EnumColor.BLUE,EnumColor.BLUE.getColorIndex()),
    BROWN("brown",EnumColor.BROWN,EnumColor.BROWN.getColorIndex()),
    CYAN("cyan",EnumColor.CYAN,EnumColor.CYAN.getColorIndex()),
    GRAY("gray",EnumColor.GRAY,EnumColor.GRAY.getColorIndex()),
    GREEN("green",EnumColor.GREEN,EnumColor.GREEN.getColorIndex()),
    LIGHT_BLUE("light_blue",EnumColor.LIGHT_BLUE,EnumColor.LIGHT_BLUE.getColorIndex()),
    LIME("lime",EnumColor.LIME,EnumColor.LIME.getColorIndex()),
    MAGENTA("magenta",EnumColor.MAGENTA,EnumColor.MAGENTA.getColorIndex()),
    ORANGE("orange",EnumColor.ORANGE,EnumColor.ORANGE.getColorIndex()),
    PINK("pink",EnumColor.PINK,EnumColor.PINK.getColorIndex()),
    PURPLE("purple",EnumColor.PURPLE,EnumColor.PURPLE.getColorIndex()),
    RED("red",EnumColor.RED,EnumColor.RED.getColorIndex()),
    SILVER("silver",EnumColor.SILVER,EnumColor.SILVER.getColorIndex()),
    WHITE("white",EnumColor.WHITE,EnumColor.WHITE.getColorIndex()),
    YELLOW("yellow",EnumColor.YELLOW,EnumColor.YELLOW.getColorIndex());
    
    private String name;
    private EnumColor color;
    private int index;
    SheepColor(String name,EnumColor color, int index){
        this.name=name;
        this.color=color;
        this.index=index;
    }

    public EnumColor getColor() {
        return color;
    }

    public void setColor(EnumColor color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndexByName(String name){
        return EnumColor.valueOf(name.toUpperCase()).getColorIndex();
    }
}
