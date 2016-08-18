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
    private RPGTradeCommands tcom;//TradeCommands
    private RPGTradeInventory tinv;
    private RPGTradeListeners tlis;//TradeListeners1

    public RPGTradeManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        System.out.println("Iniciando modulo de comandos");
        this.tcom = new RPGTradeCommands(plugin, this);//TradeCommands
        System.out.println("Comandos cargado correctamente");
        System.out.println("Iniciando modulo de Listeners");
        this.tlis = new RPGTradeListeners(plugin);//Changed
        System.out.println("Listeners cargado correctamente");
        System.out.println("Iniciando modulo de inventarios");
        this.tinv = new RPGTradeInventory(plugin);
        System.out.println("Inventarios cargado correctamente");

    }

    public RPGTradeCommands getTradeCommands() {//Changed
        return this.tcom;
    }

    public RPGTradeInventory getTradeInventory() {
        return this.tinv;
    }

    public RPGTradeListeners getTradeListeners() {//Changed
        return this.tlis;
    }

    public RPGTradeManager getTradeManager() {
        return this;
    }
}
