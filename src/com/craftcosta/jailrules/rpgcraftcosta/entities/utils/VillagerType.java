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
public enum VillagerType {

    //AQUI NOS QUEDAMOS CON LOS VILLAGERS

    /**
     *
     */
        FARMER(0),

    /**
     *
     */
    LIBRARIAN(1),

    /**
     *
     */
    CURE(2),

    /**
     *
     */
    BLACKSMITH(3),

    /**
     *
     */
    BUTCHER(4);

    private int number;

    private VillagerType(int number) {
        this.number = number;
    }

    /**
     *
     * @return
     */
    public int getNumber() {
        return this.number;
    }

    /**
     *
     * @param name
     * @return
     */
    public static VillagerType getVillagerType(String name) {
        for (VillagerType vType : VillagerType.values()) {
            if (vType.name().equalsIgnoreCase(name)) {
                return vType;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static VillagerType[] getAllVillagerTypes() {
        return VillagerType.values();
    }
}
