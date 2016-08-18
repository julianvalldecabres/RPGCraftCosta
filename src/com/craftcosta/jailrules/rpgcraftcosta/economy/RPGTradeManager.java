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
