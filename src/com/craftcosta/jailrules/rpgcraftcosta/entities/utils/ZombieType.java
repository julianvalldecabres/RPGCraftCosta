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

/**
 *
 * @author jail
 */
public enum ZombieType {
    NORMAL("normal",0),
    VILLAGER("villager",1);
    private int number;
    private String sType;
    ZombieType(String stype,int number){
        this.sType=stype;
        this.number=number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }
    
    
    
    
}
