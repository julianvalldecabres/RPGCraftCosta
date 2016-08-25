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
package com.craftcosta.jailrules.rpgcraftcosta.gui.logic.armor;

import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmorMaterial;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumArmorPart;
import com.craftcosta.jailrules.rpgcraftcosta.gui.logic.resources.EnumQuality;

/**
 *
 * @author jail
 */
public class GUIArmor {

    private String name;
    private int level;
    private int armorlevel;
    private EnumArmorMaterial material;
    private EnumQuality quality;
    private EnumArmorPart part;
    private boolean upgradable;
    private boolean comerciable;
    private double physicaldefense;
    private double incphysicaldefense;
    private float physicalevasion;
    private float incphysicalevasion;
    private double magicaldefense;
    private double incmagicaldefense;
    private float magicalevasion;
    private float incmagicalevasion;
    private long buyprice;
    private long sellprice;
    private float xpbonus;
    private float moneybonus;
    /*
     physicaldefense
     incphysicaldefense
     physicalevasion
     incphysicalevasion
     magicaldefense
     incmagicaldefense
     magicalevasion
     incmagicalevasion
     buyprice
     sellprice
     xpbonus
     moneybonus
     */

    public GUIArmor(String name, int level, int armorlevel, EnumArmorMaterial material, EnumQuality quality, EnumArmorPart part, boolean upgradable, boolean comerciable, double physicaldefense, double incphysicaldefense, float physicalevasion, float incphysicalevasion, double magicaldefense, double incmagicaldefense, float magicalevasion, float incmagicalevasion, long buyprice, long sellprice, float xpbonus, float moneybonus) {
        this.name = name;
        this.level = level;
        this.armorlevel = armorlevel;
        this.material = material;
        this.quality = quality;
        this.part = part;
        this.upgradable = upgradable;
        this.comerciable = comerciable;
        this.physicaldefense = physicaldefense;
        this.incphysicaldefense = incphysicaldefense;
        this.physicalevasion = physicalevasion;
        this.incphysicalevasion = incphysicalevasion;
        this.magicaldefense = magicaldefense;
        this.incmagicaldefense = incmagicaldefense;
        this.magicalevasion = magicalevasion;
        this.incmagicalevasion = incmagicalevasion;
        this.buyprice = buyprice;
        this.sellprice = sellprice;
        this.xpbonus = xpbonus;
        this.moneybonus = moneybonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getArmorlevel() {
        return armorlevel;
    }

    public void setArmorlevel(int armorlevel) {
        this.armorlevel = armorlevel;
    }

    public EnumArmorMaterial getMaterial() {
        return material;
    }

    public void setMaterial(EnumArmorMaterial material) {
        this.material = material;
    }

    public EnumQuality getQuality() {
        return quality;
    }

    public void setQuality(EnumQuality quality) {
        this.quality = quality;
    }

    public EnumArmorPart getPart() {
        return part;
    }

    public void setPart(EnumArmorPart part) {
        this.part = part;
    }

    public boolean isUpgradable() {
        return upgradable;
    }

    public void setUpgradable(boolean upgradable) {
        this.upgradable = upgradable;
    }

    public boolean isComerciable() {
        return comerciable;
    }

    public void setComerciable(boolean comerciable) {
        this.comerciable = comerciable;
    }

    public double getPhysicaldefense() {
        return physicaldefense;
    }

    public void setPhysicaldefense(double physicaldefense) {
        this.physicaldefense = physicaldefense;
    }

    public double getIncphysicaldefense() {
        return incphysicaldefense;
    }

    public void setIncphysicaldefense(double incphysicaldefense) {
        this.incphysicaldefense = incphysicaldefense;
    }

    public float getPhysicalevasion() {
        return physicalevasion;
    }

    public void setPhysicalevasion(float physicalevasion) {
        this.physicalevasion = physicalevasion;
    }

    public float getIncphysicalevasion() {
        return incphysicalevasion;
    }

    public void setIncphysicalevasion(float incphysicalevasion) {
        this.incphysicalevasion = incphysicalevasion;
    }

    public double getMagicaldefense() {
        return magicaldefense;
    }

    public void setMagicaldefense(double magicaldefense) {
        this.magicaldefense = magicaldefense;
    }

    public double getIncmagicaldefense() {
        return incmagicaldefense;
    }

    public void setIncmagicaldefense(double incmagicaldefense) {
        this.incmagicaldefense = incmagicaldefense;
    }

    public float getMagicalevasion() {
        return magicalevasion;
    }

    public void setMagicalevasion(float magicalevasion) {
        this.magicalevasion = magicalevasion;
    }

    public float getIncmagicalevasion() {
        return incmagicalevasion;
    }

    public void setIncmagicalevasion(float incmagicalevasion) {
        this.incmagicalevasion = incmagicalevasion;
    }

    public long getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(long buyprice) {
        this.buyprice = buyprice;
    }

    public long getSellprice() {
        return sellprice;
    }

    public void setSellprice(long sellprice) {
        this.sellprice = sellprice;
    }

    public float getXpbonus() {
        return xpbonus;
    }

    public void setXpbonus(float xpbonus) {
        this.xpbonus = xpbonus;
    }

    public float getMoneybonus() {
        return moneybonus;
    }

    public void setMoneybonus(float moneybonus) {
        this.moneybonus = moneybonus;
    }

}
