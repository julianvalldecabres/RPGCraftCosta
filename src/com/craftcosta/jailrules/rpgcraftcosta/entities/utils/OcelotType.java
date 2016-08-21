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
public enum OcelotType {

    /**
     *
     */
    JUNGLE(0), 

    /**
     *
     */
    SMOKING(1), 

    /**
     *
     */
    TABBY(2), 

    /**
     *
     */
    SIAMESE(3);

    private int num;

    private OcelotType(int n) {
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
     * @return
     */
    public String[] getAllOcelotNames() {
        String[] names = new String[4];
        int i = 0;
        for (OcelotType type : OcelotType.values()) {
            names[i] = type.name();
            i++;
        }
        return names;
    }

    /**
     *
     * @param name
     * @return
     */
    public static OcelotType getOcelotType(String name) {
        for (OcelotType type : OcelotType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
