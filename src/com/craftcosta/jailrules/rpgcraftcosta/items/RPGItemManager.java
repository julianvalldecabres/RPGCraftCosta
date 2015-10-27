/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jail
 */
public class RPGItemManager {

    public RPGCraftCosta plugin;
    public RPGWeaponManager RPGWMan;
    //RPGArmorManager RPGAMan;
    //RPGJewelManager RPGJMan;
    //RPGPotionManager RPGPMan;
    public ArrayList<RPGItem> listItems = new ArrayList<>();

    public RPGItemManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGWMan = new RPGWeaponManager(plugin);
    }

    public RPGWeaponManager getRPGWeaponManager() {
        return RPGWMan;
    }

}
