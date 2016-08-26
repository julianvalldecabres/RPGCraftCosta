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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.entities;

/**
 *
 * @author jail
 */
public class MobDrop {

    private EnumTypeDrop type;
    private String itemname;
    private int quantity;
    private double probability;

    public MobDrop(String dropname, EnumTypeDrop droptype, int dropquantity, double dropprob) {
        this.itemname=dropname;
        this.type=droptype;
        this.quantity=dropquantity;
        this.probability=dropprob;
    }

    public EnumTypeDrop getType() {
        return type;
    }

    public void setType(EnumTypeDrop type) {
        this.type = type;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

}
