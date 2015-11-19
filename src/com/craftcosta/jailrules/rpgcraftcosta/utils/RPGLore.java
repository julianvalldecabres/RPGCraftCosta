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
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.util.Objects;

public class RPGLore {

    private String lorename;
    private double value;

    public RPGLore(String lorename, double value) {
        this.lorename = lorename;
        this.value = value;
    }

    public String getLorename() {
        return this.lorename;
    }

    public void setLorename(String lorename) {
        this.lorename = lorename;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public boolean sameLoreValue(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RPGLore other = (RPGLore) obj;
        if (getValue() != other.getValue()) {
            return false;
        }
        return true;
    }

    public boolean sameLoreName(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RPGLore other = (RPGLore) obj;
        if (!getLorename().equals(other.getLorename())) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RPGLore other = (RPGLore) obj;
        if (!Objects.equals(this.lorename, other.lorename)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "RPGLore{lorename=" + this.lorename + ", value=" + this.value + '}';
    }
}
