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

/**
 *
 * @author jail
 */
public enum SlotType {

    /**
     *
     */
    HELMET("helmet"), 

    /**
     *
     */
    LEGGINGS("leggings"), 

    /**
     *
     */
    CHESTPLATE("chestplate"), 

    /**
     *
     */
    BOOTS("boots"), 

    /**
     *
     */
    WEAPON("weapon");

    private String name;

    private SlotType(String text) {
        this.name = text;
    }

    /**
     *
     * @param name
     * @return
     */
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
