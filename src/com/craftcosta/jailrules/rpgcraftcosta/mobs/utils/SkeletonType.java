/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.mobs.utils;

/**
 *
 * @author jail
 */
public enum SkeletonType {

    NORMAL(0), WITHER(1);

    private int number;

    private SkeletonType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }
}
