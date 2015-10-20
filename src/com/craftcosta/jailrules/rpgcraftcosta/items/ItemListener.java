/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

/**
 *
 * @author jail
 */
public class ItemListener implements Listener{
     RPGCraftCosta plugin;

    public ItemListener(RPGCraftCosta plugin) {
        this.plugin = plugin;
    }
     
    /**
     * todo intercambio de items en la hotbar o los slots del armor del jugador suponen un cambio de sus caracteristicas
     * por ejemplo un cambio de armadura puede mejorar o restar armadura al player
     * un cambio en el hotbar puede modificar otros atributos del jugador... ejemplo joyas objetos especiales que atribuyen
     * al player aumento de propiedades
     * @param event 
     */
    public ItemListener(InventoryClickEvent event){
        Player p= (Player) event.getWhoClicked();
        if(event.getSlotType().equals(SlotType.ARMOR)){
            //Comprobar si se quita, se pone o se intercambia un item de estos slots
            if(event.getCursor().getType().equals(Material.AIR) || event.getCursor().equals(null)){
            //Si no hay item en el cursor
                if(event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem().equals(null)){
                //Si no hay item en el slot
                    //No hacer nada
                }else{
                //Si hay item en el slot
                    //Pasar objeto al cursor (OJO! RightCLick ShiftClick LeftClick)
                    //Restar atributos al jugador
                }
            }else{
                //Si hay item en el cursor
                if(event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem().equals(null)){
                //Si no hay item en el slot
                    //Se pondra el item del cursor en el slot clicado siempre que sea compatible....
                    //Añadir atributos al player
                }else{
                //Si hay item en el slot
                    //Pasar objeto del slot al cursor (OJO! RightCLick ShiftClick LeftClick)
                    //Y pasar item del cursor al slot
                    //Restar atributos del item quitado y añadir atributos del item colocado al jugador
                }
            }
        }else if(event.getSlotType().equals(SlotType.QUICKBAR)){
            
        }
    }
}
