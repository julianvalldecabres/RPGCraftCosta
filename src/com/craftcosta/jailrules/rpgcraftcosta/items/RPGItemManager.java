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

    public RPGItemManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGWMan = new RPGWeaponManager(plugin);
        this.RPGAMan = new RPGArmorManager(plugin);
        this.RPGJMan = new RPGJewelManager(plugin);
        this.RPGQMan = new RPGQuestItemManager(plugin);
        this.RPGLMan = new RPGLoreManager(plugin);

    }

    public RPGWeaponManager getRPGWeaponManager() {
        return RPGWMan;
    }

    public RPGJewelManager getRPGJewelManager() {
        return RPGJMan;
    }

    public RPGQuestItemManager getRPGQMan() {
        return RPGQMan;
    }

    public RPGArmorManager getRPGArmorManager() {
        return RPGAMan;
    }

    public RPGLoreManager getRPGLoreManager() {
        return RPGLMan;
    }

    public ItemStack getRPGArmor(String name) {
        return RPGAMan.getRPGArmorByName(name);
    }

    public ItemStack getRPGWeapon(String name) {
        return RPGWMan.getRPGWeaponByName(name);
    }

    public ItemStack getRPGQuestItem(String name) {
        return RPGQMan.getRPGQuestItemByName(name);
    }

    public ItemStack getRPGPotion(String name){
        return RPGPMan.getRPGPotionByName(name);
    }
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
