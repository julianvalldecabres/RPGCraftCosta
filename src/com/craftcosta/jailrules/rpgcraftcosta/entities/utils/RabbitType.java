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
public enum RabbitType {

    /**
     *
     */
    BROWN(0),
    /**
     *
     */
    WHITE(1),
    /**
     *
     */
    BLACK(2),
    /**
     *
     */
    BLACKWHITE(3),
    /**
     *
     */
    GOLD(4),
    /**
     *
     */
    SALTPEPPER(5),
    /**
     *
     */
    KILLER(99);

    private int num;

    private RabbitType(int n) {
        this.num = n;
    }

    /**
     *
     * @return
     */
    public int getNumber() {
        return this.num;
    }

    /**
     *
     * @param name
     * @return
     */
    public static RabbitType getRabbitType(String name) {
        for (RabbitType type : RabbitType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static RabbitType[] getAllRabbitTypes() {
        return RabbitType.values();
    }
}
