package com.craftcosta.jailrules.rpgcraftcosta.items;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.armor.RPGArmorManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.jewels.RPGJewelManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.questitems.RPGQuestItemManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.weapons.RPGWeaponManager;
import java.util.ArrayList;

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

    public RPGItemManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.RPGWMan = new RPGWeaponManager(plugin);
        this.RPGAMan = new RPGArmorManager(plugin);
        this.RPGJMan = new RPGJewelManager(plugin);
        this.RPGQMan = new RPGQuestItemManager(plugin);
        this.RPGLMan= new RPGLoreManager(plugin);

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

}
