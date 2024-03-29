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
public enum ZombieType {

    /**
     *
     */
    NORMAL("normal", 0),
    /**
     *
     */
    VILLAGER("villager", 1);
    private int number;
    private String sType;

    ZombieType(String stype, int number) {
        this.sType = stype;
        this.number = number;
    }

    /**
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     *
     * @return
     */
    public String getsType() {
        return sType;
    }

    /**
     *
     * @param sType
     */
    public void setsType(String sType) {
        this.sType = sType;
    }

}
