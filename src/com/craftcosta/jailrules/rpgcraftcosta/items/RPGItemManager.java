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
package com.craftcosta.jailrules.rpgcraftcosta.items;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.potions.RPGPotionManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.questitems.RPGQuestItemManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGItemManager {

    private RPGCraftCosta plugin;
    private RPGWeaponManager RPGWMan;
    private RPGArmorManager RPGAMan;
    private RPGJewelManager RPGJMan;
    private RPGQuestItemManager RPGQMan;
    //RPGPotionManager RPGPMan;
    private ArrayList<RPGItem> listItems = new ArrayList<>();
    private RPGLoreManager RPGLMan;
    private RPGPotionManager RPGPMan;

    /**
     *
     * @param plugin
     */
    public RPGItemManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGWMan = new RPGWeaponManager(plugin);
        this.RPGAMan = new RPGArmorManager(plugin);
        this.RPGJMan = new RPGJewelManager(plugin);
        this.RPGQMan = new RPGQuestItemManager(plugin);
        this.RPGLMan = new RPGLoreManager(plugin);

    }

    /**
     *
     * @return
     */
    public RPGWeaponManager getRPGWeaponManager() {
        return RPGWMan;
    }

    /**
     *
     * @return
     */
    public RPGJewelManager getRPGJewelManager() {
        return RPGJMan;
    }

    /**
     *
     * @return
     */
    public RPGQuestItemManager getRPGQMan() {
        return RPGQMan;
    }

    /**
     *
     * @return
     */
    public RPGArmorManager getRPGArmorManager() {
        return RPGAMan;
    }

    /**
     *
     * @return
     */
    public RPGLoreManager getRPGLoreManager() {
        return RPGLMan;
    }

    /**
     *
     * @param name
     * @return
     */
    public ItemStack getRPGArmor(String name) {
        return RPGAMan.getRPGArmorByName(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public ItemStack getRPGWeapon(String name) {
        return RPGWMan.getRPGWeaponByName(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public ItemStack getRPGQuestItem(String name) {
        return RPGQMan.getRPGQuestItemByName(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public ItemStack getRPGPotion(String name){
        return RPGPMan.getRPGPotionByName(name);
    }

    /**
     *
     * @param name
     * @param type
     * @return
     */
    public ItemStack getRPGItem(String name, ItemType type) {
        switch (type) {
            case ARMOR:
                return RPGAMan.getRPGArmorByName(name);
            case WEAPON:
                return RPGWMan.getRPGWeaponByName(name);
            case QUESTITEM:
                return RPGQMan.getRPGQuestItemByName(name);
            case JEWEL:
                return RPGJMan.getRPGJewelByName(name);
            case POTION:
                return RPGPMan.getRPGPotionByName(name);
        }
        return null;
    }
}
