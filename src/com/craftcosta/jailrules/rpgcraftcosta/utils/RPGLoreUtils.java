/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.utils;

import java.util.Objects;

/**
 *
 * @author jail
 */
public class RPGLoreUtils {

    private String lorename;
    private double value;

    public RPGLoreUtils(String lorename, double value) {
        this.lorename = lorename;
        this.value = value;
    }

    public String getLorename() {
        return lorename;
    }

    public void setLorename(String lorename) {
        this.lorename = lorename;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
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
        final RPGLoreUtils other = (RPGLoreUtils) obj;
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
        final RPGLoreUtils other = (RPGLoreUtils) obj;
        if (!getLorename().equals(other.getLorename())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RPGLoreUtils other = (RPGLoreUtils) obj;
        if (!Objects.equals(this.lorename, other.lorename)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RPGLoreUtils{" + "lorename=" + lorename + ", value=" + value + '}';
    }

}
