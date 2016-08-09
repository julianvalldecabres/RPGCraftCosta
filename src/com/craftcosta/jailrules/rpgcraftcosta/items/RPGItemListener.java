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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

/**
 *
 * @author jail
 */
public class RPGItemListener implements Listener {

    RPGCraftCosta plugin;

    /**
     *
     * @param plugin
     */
    public RPGItemListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }

    /**
     * todo intercambio de items en la hotbar o los slots del armor del jugador
     * suponen un cambio de sus caracteristicas por ejemplo un cambio de
     * armadura puede mejorar o restar armadura al player un cambio en el hotbar
     * puede modificar otros atributos del jugador... ejemplo joyas objetos
     * especiales que atribuyen al player aumento de propiedades
     *
     * @param event
     */
    public RPGItemListener(InventoryClickEvent event) {
        //ATENCION SOLO FUNCIONA EN EL INVENTARIO SURVIVAL
        Player p = (Player) event.getWhoClicked();
        //SI se realizan cambios del armor desde los items del armor directamente
        if (event.getSlotType().equals(SlotType.ARMOR)) {
            //
            //Comprobar si se quita, se pone o se intercambia un item de estos slots
            if (event.getCursor().getType().equals(Material.AIR) || event.getCursor() == null) {
                //Si no hay item en el cursor
                if (event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem() == null) {
                    //Si no hay item en el slot
                    //No hacer nada
                    return;
                } else {
                //Si hay item en el slot
                    //Pasar objeto al cursor (OJO! RightCLick ShiftClick LeftClick)
                    //RightClick y LeftClick realizan la misma accion retirar el objeto y dejarlo en el cursor del player
                    //Shiftclick deja el objeto en el inventario

                    //Restar atributos al jugador
                }
            } else {
                //Si hay item en el cursor
                if (event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem() == null) {
                //Si no hay item en el slot

                    //Se pondra el item del cursor en el slot clicado siempre que sea compatible....
                    //LeftClick y RightClick realizan la misma accion ponen el objeto en el slot
                    //Añadir atributos al player
                } else {
                    //Si hay item en el slot
                    //Pasar objeto del slot al cursor (OJO! RightCLick ShiftClick LeftClick)
                    //Y pasar item del cursor al slot
                    //Restar atributos del item quitado y añadir atributos del item colocado al jugador
                }
            }
        } else if (event.getSlotType().equals(SlotType.QUICKBAR)) {
            //Comprobar si el item colocado
            if (event.getCursor().getType().equals(Material.AIR) || event.getCursor() == null) {
                //Si no hay item en el cursor
                if (event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem() == null) {
                    //Si no hay item en el slot
                    //No hacer nada
                    return;
                } else {
                    //Si hay item en el slot
                    //Pasar objeto al cursor (OJO! RightCLick ShiftClick LeftClick)
                    //Restar atributos al jugador
                }
            } else {
                //Si hay item en el cursor
                if (event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem() == null) {
                    //Si no hay item en el slot
                    //Se pondra el item del cursor en el slot clicado siempre que sea compatible....
                    //Añadir atributos al player
                } else {
                    //Si hay item en el slot
                    //Pasar objeto del slot al cursor (OJO! RightCLick ShiftClick LeftClick)
                    //Y pasar item del cursor al slot
                    //Restar atributos del item quitado y añadir atributos del item colocado al jugador
                }
            }
        }
    }
}
