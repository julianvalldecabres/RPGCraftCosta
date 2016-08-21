/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.economy;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;

/**
 *
 * @author jail
 */
public class RPGTradeManager {

    private RPGCraftCosta plugin;
    private RPGTradeInventory tinv;
    public RPGTradeManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        tinv= new RPGTradeInventory(plugin);
    }
    public RPGTradeManager getTradeManager() {
        return this;
    }

    public RPGTradeInventory getTinv() {
        return tinv;
    }
    
    
}
