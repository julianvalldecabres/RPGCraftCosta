/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.entities.utils;

import net.minecraft.server.v1_8_R3.EnumColor;

/**
 *
 * @author jail
 */
public enum SheepColor {

    /**
     *
     */
    BLACK("black", EnumColor.BLACK, EnumColor.BLACK.getColorIndex()),
    /**
     *
     */
    BLUE("blue", EnumColor.BLUE, EnumColor.BLUE.getColorIndex()),
    /**
     *
     */
    BROWN("brown", EnumColor.BROWN, EnumColor.BROWN.getColorIndex()),
    /**
     *
     */
    CYAN("cyan", EnumColor.CYAN, EnumColor.CYAN.getColorIndex()),
    /**
     *
     */
    GRAY("gray", EnumColor.GRAY, EnumColor.GRAY.getColorIndex()),
    /**
     *
     */
    GREEN("green", EnumColor.GREEN, EnumColor.GREEN.getColorIndex()),
    /**
     *
     */
    LIGHT_BLUE("light_blue", EnumColor.LIGHT_BLUE, EnumColor.LIGHT_BLUE.getColorIndex()),
    /**
     *
     */
    LIME("lime", EnumColor.LIME, EnumColor.LIME.getColorIndex()),
    /**
     *
     */
    MAGENTA("magenta", EnumColor.MAGENTA, EnumColor.MAGENTA.getColorIndex()),
    /**
     *
     */
    ORANGE("orange", EnumColor.ORANGE, EnumColor.ORANGE.getColorIndex()),
    /**
     *
     */
    PINK("pink", EnumColor.PINK, EnumColor.PINK.getColorIndex()),
    /**
     *
     */
    PURPLE("purple", EnumColor.PURPLE, EnumColor.PURPLE.getColorIndex()),
    /**
     *
     */
    RED("red", EnumColor.RED, EnumColor.RED.getColorIndex()),
    /**
     *
     */
    SILVER("silver", EnumColor.SILVER, EnumColor.SILVER.getColorIndex()),
    /**
     *
     */
    WHITE("white", EnumColor.WHITE, EnumColor.WHITE.getColorIndex()),
    /**
     *
     */
    YELLOW("yellow", EnumColor.YELLOW, EnumColor.YELLOW.getColorIndex());

    private String name;
    private EnumColor color;
    private int index;

    SheepColor(String name, EnumColor color, int index) {
        this.name = name;
        this.color = color;
        this.index = index;
    }

    /**
     *
     * @return
     */
    public EnumColor getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(EnumColor color) {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *
     * @param name
     * @return
     */
    public int getIndexByName(String name) {
        return EnumColor.valueOf(name.toUpperCase()).getColorIndex();
    }
}
