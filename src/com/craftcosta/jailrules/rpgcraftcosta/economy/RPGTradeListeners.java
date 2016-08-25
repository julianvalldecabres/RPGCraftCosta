/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.economy;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayer;
import com.craftcosta.jailrules.rpgcraftcosta.player.RPGPlayerManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGTradeListeners implements Listener {

    private RPGCraftCosta plugin;
    private RPGTradeCommands tcom;//Changed
    private RPGTradeInventory tinv;
    private RPGTradeManager tman;
    private RPGPlayerManager rpgPMan;
    public ArrayList<Player> aceptados = new ArrayList();
    public ArrayList<Player> confirmados = new ArrayList();

    public ArrayList<Integer> slotsDer = RPGTradeInventory.slotsDer;
    public ArrayList<Integer> slotsIzq = RPGTradeInventory.slotsIzq;

    public HashMap<String, Inventory> listaInventarios = RPGTradeCommands.listaInventarios;
    public HashMap<Player, Player> peticiones = RPGTradeCommands.peticiones;
    public Map<Player, Player> tratos = RPGTradeCommands.tratos;

    public RPGTradeListeners(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.tman = plugin.getTradeManager();
        this.rpgPMan = plugin.getRPGPlayerManager();
        //this.tcom = this.tman.getTradeCommands();
        this.tinv = this.tman.getTinv();
    }

    /**
     * Evento que se dispara si intenta dejar caer un objeto fuera del
     * inventario El objeto de manera normal se dropearia, por tanto hay que
     * cancelar el evento
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (playerEnTrato(p)) {
            ItemStack drop = event.getItemDrop().getItemStack();
            ItemStack air = new ItemStack(Material.AIR);
            event.getItemDrop().remove();
            p.setItemOnCursor(air);
            p.getInventory().addItem(new ItemStack[]{drop});
            p.updateInventory();
        }
    }

    /**
     * Evento que se dispara al hacer click en cualquier inventario Se
     * diferenciará el inventario de intercambios para controlarlos Esqueleto
     * condicional: Si es el inventario de intercambio: Si el slot clicado tiene
     * que ver con el inventario: Si el cursor no tiene un objeto -> Sin acción
     * Si se hace click sobre los contadores de dinero -> Sin acción Si se hace
     * click sobre los divisores -> Sin acción Si se hace click sobre los
     * modificadores de dinero: Si es de clase dineroX: Si el trato tiene estado
     * aceptado o confirmado devolver a estado inicial Si es el creador del
     * trato: Si se hace click izquierdo: - Aumentar en X dinero el contador del
     * creador - Actualizar vistas del inventario de intercambio de los
     * participantes Si se hace click derecho: - Reducir en X dinero el contador
     * del creador - Actualizar vistas del inventario de intercambio de los
     * participantes Si es el participante del trato: Si se hace click
     * izquierdo: - Aumentar en X dinero el contador del creador - Actualizar
     * vistas del inventario de intercambio de los participantes Si se hace
     * click derecho: - Reducir en X dinero el contador del creador - Actualizar
     * vistas del inventario de intercambio de los participantes
     *
     * Objetos permanentes: Objetos de dinero -> añadiran o restaran dinero a la
     * cantidad total Objetos separadores -> Sin acción Objetos Aceptar,
     * Cancelar, Confirmar -> Cambiarán el estado del trato Objeto en el cursor
     * y click en el inventario del personaje: - el objeto será devuelto al
     * inventario del personaje Objeto en el cursor y click en el inventario de
     * intercambio: - el objeto será colocado en el inventario de intercambio si
     * es posible - si el objeto se deja en una posición permitida se efectuará
     * - si el objeto se deja en una posición no permitida permanecerá en el
     * cursor Objetos en el inventario del personaje: - si ya hay un objeto en
     * el cursor se intercambiará por el clicado - si no hay objeto en el cursos
     * se pondrá en el cursor Objetos en el inventario de intercambio: - si hay
     * objeto en el cursor se intercambiará con el clicado si es una posición
     * permitida - si no hay objeto en el cursor se dejará en el hueco clicado
     * si es una posición permitida
     *
     * @param event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws InsufficientFundsException, NegativeMoneyException {
        RPGTradeInventory tinv = this.plugin.getTradeManager().getTinv();
        Inventory inventario = event.getInventory();
        String nombreInventario = ChatColor.GREEN + "Intercambio de objetos";
        Player p = (Player) event.getWhoClicked();
        ItemStack aceptar = this.tinv.aceptar();
        ItemStack noaceptado = this.tinv.noaceptado();
        ItemStack cancelar = this.tinv.cancelar();
        ItemStack confirmar = this.tinv.confirmar();
        ItemStack divisor = this.tinv.divisor();
        ItemStack dinero1 = this.tinv.dinero1();
        ItemStack dinero10 = this.tinv.dinero10();
        ItemStack dinero100 = this.tinv.dinero100();
        ItemStack dinero1000 = this.tinv.dinero1000();
        ItemStack dinero10000 = this.tinv.dinero10000();
        ItemStack totalDinero = this.tinv.totaldinero(0);
        ItemStack air = new ItemStack(Material.AIR);
        //Si se trata del inventario de intercambio
        if (inventario.getTitle().equals(nombreInventario)) {
            if (event.getRawSlot() < inventario.getSize()) {
                //si no clican nada no se realiza ninguna acción
                if (event.getCurrentItem() == null || event.getCurrentItem().equals(divisor) || (event.getCurrentItem().getType() == totalDinero.getType()) && (event.getSlot() == 2 || event.getSlot() == 6)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    return;
                }
                //Si se hace click sobre cualquier objeto de dineroXXXXX
                if ((event.getCurrentItem().getType() == dinero1.getType()) && (event.getSlot() == 0 || event.getSlot() == 8)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    Player participante = (Player) this.tratos.get(p);
                    if (event.isCancelled()) {
                        if (participante == null) {
                            //p es el participante
                            Player creador = obtenerCreador(p);
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(creador);
                                confirmados.remove(p);
                                confirmados.remove(creador);
                                cambiarPorNoaceptado(creador, p);
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");

                            }
                            if (event.isLeftClick()) {
                                sumarTotalP2(p, creador, 1);
                            } else if (event.isRightClick()) {
                                sumarTotalP2(p, creador, -1);
                            } else {
                                return;
                            }
                        } else {
                            //p es el creador
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(participante);
                                confirmados.remove(p);
                                confirmados.remove(participante);
                                cambiarPorNoaceptado(p, participante);
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + participante.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotal(p, participante, 1);
                            } else if (event.isRightClick()) {
                                sumarTotal(p, participante, -1);
                            }

                        }
                    }
                    event.setResult(Event.Result.DENY);
                    return;
                }
                if ((event.getCurrentItem().getType() == dinero10.getType()) && (event.getSlot() == 9 || event.getSlot() == 17)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    Player participante = (Player) this.tratos.get(p);
                    if (event.isCancelled()) {
                        if (participante == null) {
                            //p es el participante
                            Player creador = obtenerCreador(p);
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                confirmados.remove(p);
                                confirmados.remove(creador);
                                aceptados.remove(creador);
                                cambiarPorNoaceptado(creador, p);
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotalP2(p, creador, 10);
                            } else if (event.isRightClick()) {
                                sumarTotalP2(p, creador, -10);
                            } else {
                                return;
                            }
                        } else {
                            //p es el creador
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(participante);
                                confirmados.remove(p);
                                confirmados.remove(participante);
                                cambiarPorNoaceptado(p, participante);
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + participante.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotal(p, participante, 10);
                            } else if (event.isRightClick()) {
                                sumarTotal(p, participante, -10);
                            }

                        }
                    }
                    return;
                }
                if ((event.getCurrentItem().getType() == dinero100.getType()) && (event.getSlot() == 18 || event.getSlot() == 26)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    Player participante = (Player) this.tratos.get(p);
                    if (event.isCancelled()) {
                        if (participante == null) {
                            //p es el participante
                            Player creador = obtenerCreador(p);
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(creador);
                                confirmados.remove(p);
                                confirmados.remove(creador);
                                cambiarPorNoaceptado(creador, p);
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotalP2(p, creador, 100);
                            } else if (event.isRightClick()) {
                                sumarTotalP2(p, creador, -100);
                            } else {
                                return;
                            }
                        } else {
                            //p es el creador
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(participante);
                                confirmados.remove(p);
                                confirmados.remove(participante);
                                cambiarPorNoaceptado(p, participante);
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + participante.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotal(p, participante, 100);
                            } else if (event.isRightClick()) {
                                sumarTotal(p, participante, -100);
                            }

                        }
                    }
                    return;
                }
                if ((event.getCurrentItem().getType() == dinero1000.getType()) && (event.getSlot() == 27 || event.getSlot() == 35)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    Player participante = (Player) this.tratos.get(p);
                    if (event.isCancelled()) {
                        if (participante == null) {
                            //p es el participante
                            Player creador = obtenerCreador(p);
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(creador);
                                confirmados.remove(p);
                                confirmados.remove(creador);
                                cambiarPorNoaceptado(creador, p);
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotalP2(p, creador, 1000);
                            } else if (event.isRightClick()) {
                                sumarTotalP2(p, creador, -1000);
                            } else {
                                return;
                            }
                        } else {
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(participante);
                                confirmados.remove(p);
                                confirmados.remove(participante);
                                cambiarPorNoaceptado(p, participante);
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + participante.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            //p es el creador
                            if (event.isLeftClick()) {
                                sumarTotal(p, participante, 1000);
                            } else if (event.isRightClick()) {
                                sumarTotal(p, participante, -1000);
                            }

                        }
                    }
                    return;
                }
                if ((event.getCurrentItem().getType() == dinero10000.getType()) && (event.getSlot() == 36 || event.getSlot() == 44)) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                    Player participante = (Player) this.tratos.get(p);
                    if (event.isCancelled()) {
                        if (participante == null) {
                            //p es el participante
                            Player creador = obtenerCreador(p);
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(creador);
                                confirmados.remove(p);
                                confirmados.remove(creador);
                                cambiarPorNoaceptado(creador, p);
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotalP2(p, creador, 10000);
                            } else if (event.isRightClick()) {
                                sumarTotalP2(p, creador, -10000);
                            } else {
                                return;
                            }
                        } else {
                            //p es el creador
                            if (aceptados.contains(p) || confirmados.contains(p)) {
                                aceptados.remove(p);
                                aceptados.remove(participante);
                                confirmados.remove(p);
                                confirmados.remove(participante);
                                cambiarPorNoaceptado(p, participante);
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has cambiado el trato, debes re-aceptar el intercambio");
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + participante.getName() + " ha cambiado el trato, debes re-aceptar el intercambio");
                            }
                            if (event.isLeftClick()) {
                                sumarTotal(p, participante, 10000);
                            } else if (event.isRightClick()) {
                                sumarTotal(p, participante, -10000);
                            }

                        }
                    }
                    return;
                }

                //Si introduce items en el inventario de intercambio
                if ((slotsDer.contains(Integer.valueOf(event.getRawSlot())) || slotsIzq.contains(Integer.valueOf(event.getRawSlot())))) {
                    //Averiguar si es el creador o el participante del trato
                    Player participante = (Player) this.tratos.get(p);
                    if (participante == null) {
                        //Si el player p es el participante
                        Player creador = obtenerCreador(p);
                        //Si el slot pertenece al lado del participante(slotsDer) se permite
                        if (slotsDer.contains(Integer.valueOf(event.getRawSlot()))) {
                            //Si el slot esta vacio == aire
                            if (event.getCurrentItem().getType() == Material.AIR) {
                                if (event.getCursor().getType() == Material.AIR) {
                                    //No hay nada que intercambiar
                                    event.setCancelled(true);
                                } else {
                                    //Se pone el item del cursor en el slot del otro participante
                                    event.setCancelled(true);
                                    actualizarInventarios(p, event.getCursor(), event.getRawSlot());
                                    actualizarInventarios(creador, event.getCursor(), event.getRawSlot());
                                    p.setItemOnCursor(air);
                                    cambiarPorNoaceptado(creador, p);
                                }
                            } else {
                                //Si el slot esta ocupado por un item

                                if (event.getCursor().getType() == Material.AIR) {
                                    //Si el cursor no esta ocupado por un item
                                    event.setCancelled(true);
                                    p.setItemOnCursor(event.getCurrentItem());
                                    actualizarInventarios(p, air, event.getRawSlot());
                                    actualizarInventarios(creador, air, event.getRawSlot());
                                    cambiarPorNoaceptado(creador, p);
                                } else {
                                    ItemStack slotItem = event.getCurrentItem();
                                    ItemStack cursorItem = event.getCursor();
                                    event.setCancelled(true);
                                    p.setItemOnCursor(slotItem);
                                    actualizarInventarios(p, cursorItem, event.getRawSlot());
                                    actualizarInventarios(creador, cursorItem, event.getRawSlot());
                                    cambiarPorNoaceptado(creador, p);
                                }
                            }
                            //Si el slot pertenece al lado del creador (slotsIzq) se cancela
                        } else if (slotsIzq.contains(Integer.valueOf(event.getRawSlot()))) {
                            event.setCancelled(true);
                            p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Este slot es propiedad del otro participante del trato");
                        }
                    } else {
                        //Player p es el creador
                        if (slotsIzq.contains(Integer.valueOf(event.getRawSlot()))) {
                            //Si el slot esta vacio == aire
                            if (event.getCurrentItem().getType() == Material.AIR) {
                                if (event.getCursor().getType() == Material.AIR) {
                                    //No hay nada que intercambiar
                                    event.setCancelled(true);
                                } else {
                                    event.setCancelled(true);
                                    actualizarInventarios(p, event.getCursor(), event.getRawSlot());
                                    actualizarInventarios(participante, event.getCursor(), event.getRawSlot());
                                    p.setItemOnCursor(air);
                                    cambiarPorNoaceptado(p, participante);
                                }
                                //Si el slot esta ocupado por un item
                            } else {
                                //Si el cursor esta ocupado por un item
                                if (event.getCursor().getType() == Material.AIR) {
                                    event.setCancelled(true);
                                    p.setItemOnCursor(event.getCurrentItem());
                                    actualizarInventarios(p, air, event.getRawSlot());
                                    actualizarInventarios(participante, air, event.getRawSlot());
                                    cambiarPorNoaceptado(p, participante);
                                } else {
                                    ItemStack slotItem = event.getCurrentItem();
                                    ItemStack cursorItem = event.getCursor();
                                    event.setCancelled(true);
                                    p.setItemOnCursor(slotItem);
                                    actualizarInventarios(p, cursorItem, event.getRawSlot());
                                    actualizarInventarios(participante, cursorItem, event.getRawSlot());
                                    cambiarPorNoaceptado(p, participante);
                                }
                            }
                            //Si el slot pertenece al lado del creador (slotsIzq) se cancela
                        } else if (slotsDer.contains(Integer.valueOf(event.getRawSlot()))) {
                            event.setCancelled(true);
                            p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Este slot es propiedad del otro participante del trato");
                        }
                    }
                    return;
                }

                //Si hace click en noaceptado, acepta el trato formalizado pero no termina
                if ((event.getCurrentItem().equals(noaceptado)) && ((event.getSlot() == 3 || event.getSlot() == 5))) {
                    event.setCancelled(true);
                    Player participante = tratos.get(p);
                    if (participante == null) {
                        Player creador = obtenerCreador(p);
                        //El player p es el participante del trato
                        if (event.getSlot() == 3) {
                            p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Para aceptar debes hacer click en el otro boton");
                            return;
                        } else {

                            if (p.getItemOnCursor() != null) {
                                ItemStack item = p.getItemOnCursor();
                                p.setItemOnCursor(air);
                                p.getInventory().addItem(new ItemStack[]{item});
                            }
                            if (aceptados.contains(creador)) {
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has aceptado el trato, ahora puedes confirmar el trato");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " Ha aceptado el trato, ahora puedes confirmar el trato");

                            } else {
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has aceptado el trato, espera a que " + creador.getName() + " el intercambio");
                                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " Ha aceptado el trato, ahora puedes aceptar el trato");
                            }
                            //deberemos cambiar el noaceptado del participante por aceptado en ambos inventarios
                            cambiarNoAceptadoPorAceptadoP2(creador, p);
                            //anyadir p a aceptados
                            aceptados.add(p);
                            return;
                        }
                    } else {
                        //el player p es el creador
                        if (event.getSlot() == 5) {
                            p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Para aceptar debes hacer click en el otro boton");
                            return;
                        } else {
                            if (p.getItemOnCursor() != null) {
                                ItemStack item = p.getItemOnCursor();
                                p.setItemOnCursor(air);
                                p.getInventory().addItem(new ItemStack[]{item});
                            }
                            //Cambiar item no aceptado por aceptado en ambos inventarios de intercambio
                            cambiarNoAceptadoPorAceptadoP1(p, participante);
                            aceptados.add(p);
                            if (aceptados.contains(participante)) {
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has aceptado el trato, ahora puedes confirmar el trato");
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " Ha aceptado el trato, ahora puedes confirmar el trato");
                            } else {
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has aceptado el trato, espera a que " + participante.getName() + "acepte el intercambio");
                                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " " + p.getName() + " Ha aceptado el trato, ahora puedes aceptar el trato");
                            }
                            return;
                        }
                    }
                }
                //si clica aceptado, confirma el trato solo si ambos players han aceptado el trato si no no dejara confirmar
                if (((event.getCurrentItem().equals(aceptar))) && ((event.getSlot() == 3 || event.getSlot() == 5))) {
                    event.setCancelled(true);
                    Player participante = (Player) this.tratos.get(p); //tratos.put(creadornombre,participante)
                    if (participante == null) {
                        //el player es el participante del trato
                        Player creador = obtenerCreador(p);
                        if (!aceptados.contains(creador)) {
                            p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Para confirmar el trato el otro jugador debe haber aceptado el trato antes");
                            return;
                        } else {

                            if (event.getSlot() == 3) {
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Para confirmar debes hacer click en el otro boton de confirmacion");
                                return;
                            } else {
                                //Si el participante ya ha confirmado anteriormente
                                if (confirmados.contains(p)) {
                                    //Si ya confirmo anteriormente el creador del trato
                                    if (confirmados.contains(creador)) {
                                        //Aceptar trato.
                                        //Devolver items en el cursor
                                        if (p.getItemOnCursor().getType() != Material.AIR) {
                                            p.getInventory().addItem(p.getItemOnCursor());
                                            p.setItemOnCursor(air);
                                        }
                                        if (creador.getItemOnCursor().getType() != Material.AIR) {
                                            creador.getInventory().addItem(creador.getItemOnCursor());
                                            creador.setItemOnCursor(air);
                                        }
                                        aceptarTrato(creador, p);
                                    } else {
                                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Ya has confirmado el trato anteriormente, espera a que " + creador.getName() + " confirme el trato");
                                    }
                                } else {
                                    //Si el participante no ha confirmado
                                    //cambiar item aceptado a confirmado P2
                                    confirmados.add(p);
                                    cambiarAceptarPorConfirmadoP2(creador, p);
                                    if (confirmados.contains(creador)) {
                                        //Devolver items en el cursor
                                        if (p.getItemOnCursor().getType() != Material.AIR) {
                                            p.getInventory().addItem(p.getItemOnCursor());
                                            p.setItemOnCursor(air);
                                        }
                                        if (creador.getItemOnCursor().getType() != Material.AIR) {
                                            creador.getInventory().addItem(creador.getItemOnCursor());
                                            creador.setItemOnCursor(air);
                                        }
                                        aceptarTrato(creador, p);
                                    } else {
                                        //Mensaje de espera para el creador del trato
                                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has confirmado el trato, espera a que " + creador.getName() + " confirme el trato");
                                    }
                                }
                                return;
                            }
                        }
                    } else {
                        //el player p es el creador
                        if (!aceptados.contains(participante)) {
                            p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Para confirmar el trato el otro jugador debe haber aceptado el trato antes");
                            return;
                        } else {
                            if (event.getSlot() == 5) {
                                p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Para confirmar debes hacer click en el otro boton de confirmacion");
                                return;
                            } else {
                                if (confirmados.contains(p)) {
                                    if (confirmados.contains(participante)) {
                                        //Aceptar trato
                                        //Devolver items en el cursor
                                        if (participante.getItemOnCursor().getType() != Material.AIR) {
                                            participante.getInventory().addItem(participante.getItemOnCursor());
                                            participante.setItemOnCursor(air);
                                        }
                                        if (p.getItemOnCursor().getType() != Material.AIR) {
                                            p.getInventory().addItem(p.getItemOnCursor());
                                            p.setItemOnCursor(air);
                                        }
                                        aceptarTrato(p, participante);
                                    } else {
                                        //Mensaje de espera
                                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Ya has confirmado el trato anteriormente, espera a que " + participante.getName() + " confirme el trato");
                                    }
                                } else {
                                    //anyadir a confirmados
                                    confirmados.add(p);
                                    cambiarAceptarPorConfirmadoP1(p, participante);
                                    if (confirmados.contains(participante)) {
                                        //Aceptar trato
                                        //Devolver items en el cursor
                                        if (participante.getItemOnCursor().getType() != Material.AIR) {
                                            participante.getInventory().addItem(participante.getItemOnCursor());
                                            participante.setItemOnCursor(air);
                                        }
                                        if (p.getItemOnCursor().getType() != Material.AIR) {
                                            p.getInventory().addItem(p.getItemOnCursor());
                                            p.setItemOnCursor(air);
                                        }
                                        aceptarTrato(p, participante);
                                    } else {
                                        //Mensaje de espera
                                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.GREEN + " Has confirmado el trato, espera a que " + participante.getName() + " confirme el trato");

                                    }

                                }
                                if (p.getItemOnCursor() != null) {
                                    ItemStack item = p.getItemOnCursor();
                                    p.setItemOnCursor(air);
                                    p.getInventory().addItem(new ItemStack[]{item});
                                }
                                if (participante.getItemOnCursor() != null) {
                                    ItemStack item = participante.getItemOnCursor();
                                    participante.setItemOnCursor(air);
                                    participante.getInventory().addItem(new ItemStack[]{item});
                                }
                                return;
                            }
                        }
                    }
                }

                if ((event.getCurrentItem().equals(confirmar)) && ((event.getSlot() == 3 || event.getSlot() == 5))) {
                    event.setCancelled(true);
                }

                //si p hace click en item cancelar
                if ((event.getCurrentItem().equals(cancelar)) && ((event.getSlot() == 4))) {
                    event.setCancelled(true);
                    Player participante = (Player) this.tratos.get(p);
                    if (participante == null) {
                        //p es el participante que cancela el trato
                        participante = p;
                        for (Map.Entry<Player, Player> entrySet : tratos.entrySet()) {
                            Player key = entrySet.getKey();
                            Player value = entrySet.getValue();
                            if (value == participante) {
                                value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " has cancelado el intercambio con " + key.getName());
                                key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + value.getName() + " ha cancelado el intercambio");
                                cancelarTrato(key, value);
                            }
                        }
                    } else {
                        //p es el creador que cancela el trato
                        p.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " has cancelado el intercambio con " + participante.getName());
                        participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + p.getName() + " ha cancelado el intercambio");
                        cancelarTrato(p, participante);

                    }
                    return;
                }

            }
            //evitar que se reunan los items en un solo slot
            if ((event.getAction() == InventoryAction.COLLECT_TO_CURSOR) || (event.getClick() == ClickType.DOUBLE_CLICK)) {
                event.setCancelled(true);
                return;
            }
            //control de cambio entre inventarios
            if ((event.getRawSlot() > inventario.getSize()) && event.isShiftClick()) {
                event.setCancelled(true);
            }

        }

        if (event.isCancelled()) {
            p.updateInventory();
            p.saveData();
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        String nombreInventario = ChatColor.GREEN + "Intercambio de objetos";
        if (event.getInventory().getTitle().equals(nombreInventario)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        //Si cualquiera de los players muere en mitad de un intercambio se cancela la transaccion

        //Recuperamos el inventario y el player que muere
        Inventory inventario = event.getEntity().getInventory();
        Player p = (Player) event.getEntity().getPlayer();
        ItemStack air = new ItemStack(Material.AIR);
        //Si el player es parte de una transacción
        if (playerEnTrato(p)) {
            //Evitamos que se dropeen los objetos propios del inventario del player (armor, inventario, hotbar)
            event.setKeepInventory(true);
            //Si el inventario es el de intercambio
            String nombreInventario = ChatColor.GREEN + "Intercambio de objetos";
            if (inventario.getTitle().equals(nombreInventario)) {
                Player participante = this.tratos.get(p);
                if (participante == null) {
                    //El player p es el participante del trato
                    for (Map.Entry<Player, Player> entrySet : tratos.entrySet()) {
                        Player key = entrySet.getKey();
                        Player value = entrySet.getValue();
                        if (value == p) {
                            //Enviar mensaje
                            key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + value.getName() + " debido a que este se desconecto");
                            value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + key.getName() + " debido a tu desconexion");
                            cancelarTrato(key, value);
                        }
                    }
                } else {
                    //El player es el creador del trato
                    Player creador = p;
                    //Enviar mensaje
                    creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + participante.getName() + " debido a tu desconexion");
                    participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + creador.getName() + " debido a que este se desconecto");
                    cancelarTrato(creador, participante);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        //Al cerrar el inventario de intercambio se presupone que se cancela la transaccion
        Inventory inventario = event.getInventory();
        Player p = (Player) event.getPlayer();
        ItemStack air = new ItemStack(Material.AIR);
        if ((this.tratos.containsValue(p)) || (this.tratos.containsKey(p))) {
            String nombreInventario = ChatColor.GREEN + "Intercambio de objetos";
            if (inventario.getTitle().equals(nombreInventario)) {
                Player participante = (Player) this.tratos.get(p);
                if (participante == null) {
                    //El player p es el participante del trato
                    for (Map.Entry<Player, Player> entrySet : tratos.entrySet()) {
                        Player key = entrySet.getKey();
                        Player value = entrySet.getValue();
                        if (value == p) {
                            //Enviar mensaje
                            key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + value.getName() + " debido a que este cerro el inventario");
                            value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + key.getName() + " debido a que cerraste el inventario");
                            cancelarTrato(key, value);
                        }
                    }
                } else {
                    //El player es el creador del trato
                    Player creador = p;
                    //Enviar mensaje
                    creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + participante.getName() + " debido a que cerraste el inventario");
                    participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + creador.getName() + " debido a que este cerro el inventario");
                    cancelarTrato(creador, participante);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        ItemStack air = new ItemStack(Material.AIR);

        //Si el player abandona el juego
        Player p = event.getPlayer();
        //Si el player esta en una petición
        if (playerEnPeticion(p)) {
            //El player es creador o participante?
            Player participante = peticiones.get(p);
            if (participante == null) {
                //el player p es un participante de una peticion
                for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                    Player key = entrySet.getKey();
                    Player value = entrySet.getValue();
                    if (value == p) {
                        key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + value.getName() + " debido a que este se desconecto");
                        value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + key.getName() + " debido a tu desconexion");
                        peticiones.remove(key);
                    }
                }
            } else {
                //player p es el creador de la peticion
                for (Map.Entry<Player, Player> entrySet : peticiones.entrySet()) {
                    Player key = entrySet.getKey();
                    Player value = entrySet.getValue();
                    if (key == p) {
                        key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + value.getName() + " a tu desconexion");
                        value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + key.getName() + " debido debido a que este se desconecto");
                        peticiones.remove(key);
                    }
                }
            }
            //Si el player esta en un trato
        } else if (playerEnTrato(p)) {
            Player participante = tratos.get(p);
            if (participante == null) {
                //El player p es el participante del trato
                for (Map.Entry<Player, Player> entrySet : tratos.entrySet()) {
                    Player key = entrySet.getKey();
                    Player value = entrySet.getValue();
                    if (value == p) {
                        //Enviar mensaje
                        key.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + value.getName() + " debido a que este se desconecto");
                        value.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + key.getName() + " debido a tu desconexion");
                        cancelarTrato(key, value);
                    }
                }
            } else {
                //El player es el creador del trato
                Player creador = p;
                //Enviar mensaje
                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una petición de intercambio con " + participante.getName() + " debido a tu desconexion");
                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " Se ha cancelado una peticion de intercambio con " + creador.getName() + " debido a que este se desconecto");
                cancelarTrato(creador, participante);
            }
        } else {
            //El player no esta en trato ni en peticiones...
            return;
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        if (playerEnTrato(p)) {
            event.setCancelled(true);
        }
    }

    private Player obtenerCreador(Player p) {
        Player creador = null;
        for (Map.Entry<Player, Player> entrySet : tratos.entrySet()) {
            Player key = entrySet.getKey();
            Player value = entrySet.getValue();
            if (value == p) {
                creador = key;
            }
            break;
        }
        return creador;
    }

    private void aceptarTrato(Player creador, Player participante) throws InsufficientFundsException, NegativeMoneyException {
        //Comprobar dinero
        ItemStack air = new ItemStack(Material.AIR);
        RPGPlayer rpgpc = rpgPMan.getRPGPlayerByName(creador.getName());
        RPGPlayer rpgpp = rpgPMan.getRPGPlayerByName(participante.getName());
        if (rpgpc.getEcon().getMoney() >= moneyP1(creador) && rpgpp.getEcon().getMoney() >= moneyP2(participante)) {
            //Si ambos tienen el suficiente dinero continuamos con la transaccion
            //pagar a usuarios
            rpgpc.getEcon().withdrawMoney(moneyP1(creador));
            rpgpp.getEcon().withdrawMoney(moneyP2(creador));
            rpgpc.getEcon().addMoney(moneyP2(participante));
            rpgpp.getEcon().addMoney(moneyP1(creador));
            //Comprobar espacio en los inventarios
            if (hasFreeSlotsP1(creador) && hasFreeSlotsP2(participante)) {
                //Si ambos tienen suficiente espacio en sus inventarios
                //Pasar objetos directamente
                //pasar items al participante
                for (Integer a : slotsIzq) {
                    int x = a.intValue();
                    Inventory inv = participante.getOpenInventory().getTopInventory();
                    ItemStack item = inv.getItem(x);
                    //Si no es aire
                    if (item != null) {
                        //Si hay espacio libre
                        participante.getInventory().addItem(item);
                    }
                }
                //pasar items al creador
                for (Integer a : slotsDer) {
                    int x = a.intValue();
                    Inventory inv = creador.getOpenInventory().getTopInventory();
                    ItemStack item = inv.getItem(x);
                    //Si no es aire
                    if (item != null) {
                        //Si hay espacio libre
                        creador.getInventory().addItem(item);
                    }
                }

            } else {
                if (!hasFreeSlotsP1(creador)) {
                    for (Integer a : slotsDer) {
                        int x = a.intValue();
                        Inventory inv = creador.getOpenInventory().getTopInventory();
                        ItemStack item = inv.getItem(x);
                        if (item != null) {
                            if (creador.getInventory().firstEmpty() == -1) {
                                World w = creador.getWorld();
                                Location loc = creador.getLocation();
                                w.dropItemNaturally(loc, item);
                            } else {
                                creador.getInventory().addItem(item);
                            }
                        }
                    }
                } else {
                    for (Integer a : slotsDer) {
                        int x = a.intValue();
                        Inventory inv = creador.getOpenInventory().getTopInventory();
                        ItemStack item = inv.getItem(x);
                        if (item != null) {

                            creador.getInventory().addItem(item);
                        }
                    }
                }
                if (!hasFreeSlotsP2(participante)) {
                    for (Integer a : slotsIzq) {
                        int x = a.intValue();
                        Inventory inv = participante.getOpenInventory().getTopInventory();
                        ItemStack item = inv.getItem(x);
                        //Si no es aire
                        if (item != null) {
                            if (participante.getInventory().firstEmpty() == -1) {
                                World w = participante.getWorld();
                                Location loc = participante.getLocation();
                                w.dropItemNaturally(loc, item);
                            } else {
                                participante.getInventory().addItem(item);
                            }

                        }
                    }
                } else {
                    for (Integer a : slotsIzq) {
                        int x = a.intValue();
                        Inventory inv = participante.getOpenInventory().getTopInventory();
                        ItemStack item = inv.getItem(x);
                        //Si no es aire
                        if (item != null) {
                            //Si hay espacio libre
                            participante.getInventory().addItem(item);
                        }
                    }
                }
            }
            //Eliminar listas 
            tratos.remove(creador);
            aceptados.remove(creador);
            aceptados.remove(participante);
            confirmados.remove(creador);
            confirmados.remove(participante);
            listaInventarios.remove(creador.getName());
            listaInventarios.remove(participante.getName());
            participante.closeInventory();
            participante.updateInventory();
            creador.closeInventory();
            creador.updateInventory();
            return;
        } else {
            //Comprobar cual de los dos no tiene dinero suficiente
            if (rpgpc.getEcon().getMoney() < moneyP1(creador)) {
                //El creador no tiene suficiente dinero para aceptar el trato
                //Mensaje de error
                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " No tienes suficiente dinero para ofrecer en el trato");
            } else {
                creador.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " " + participante.getName() + " no tiene suficiente dinero para ofrecer en el trato");
            }
            if (rpgpp.getEcon().getMoney() < moneyP2(participante)) {
                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + " No tienes suficiente dinero para ofrecer en el trato");
            } else {
                participante.sendMessage(ChatColor.GOLD + "[TRATO]" + ChatColor.RED + "" + creador.getName() + " no tiene suficiente dinero para ofrecer en el trato");
            }
            cambiarPorNoaceptado(creador, participante);
            return;
        }
    }

    /**
     *
     * @param creador del trato
     * @param participante
     * @param item
     */
    private void actualizarInventarios(Player p, ItemStack item, int slot) {
        p.getOpenInventory().getTopInventory().setItem(slot, item);
    }

    private void cancelarTrato(Player creador, Player participante) {
        ItemStack air = new ItemStack(Material.AIR);
        if (creador.getItemOnCursor() != null) {
            ItemStack item = creador.getItemOnCursor();
            creador.setItemOnCursor(air);
            creador.getInventory().addItem(new ItemStack[]{item});
        }
        if (participante.getItemOnCursor() != null) {
            ItemStack item = participante.getItemOnCursor();
            participante.setItemOnCursor(air);
            participante.getInventory().addItem(new ItemStack[]{item});
        }
        for (Integer x : slotsIzq) {
            int i = x.intValue();
            Inventory inv = creador.getOpenInventory().getTopInventory();
            ItemStack f = inv.getItem(i);
            if (f != null && f.getType() != Material.AIR) {
                creador.getInventory().addItem(new ItemStack[]{f});
            }
        }
        for (Integer x : slotsDer) {
            int i = x.intValue();
            Inventory inv = participante.getOpenInventory().getTopInventory();
            ItemStack f = inv.getItem(i);
            if (f != null && f.getType() != Material.AIR) {
                participante.getInventory().addItem(new ItemStack[]{f});
            }
        }
        tratos.remove(creador);
        aceptados.remove(creador);
        aceptados.remove(participante);
        confirmados.remove(creador);
        confirmados.remove(participante);
        listaInventarios.remove(creador.getName());
        listaInventarios.remove(participante.getName());
        participante.closeInventory();
        participante.updateInventory();
        creador.closeInventory();
        creador.updateInventory();
    }

    private void sumarTotal(Player p, Player creadorTrato, int amount) {
        Inventory invP = p.getOpenInventory().getTopInventory();
        Inventory invC = creadorTrato.getOpenInventory().getTopInventory();
        ItemStack dinero = invP.getItem(2);
        ItemMeta dineroMeta = dinero.getItemMeta();
        List<String> lores = dineroMeta.getLore();
        int actual = Integer.parseInt(lores.get(0).substring(0, lores.get(0).length() - 1));
        int res = actual + amount;
        if (res < 0) {
            res = 0;
        }
        ItemStack nuevoDinero = this.tinv.totaldinero(res);
        invP.setItem(2, nuevoDinero);
        invC.setItem(2, nuevoDinero);
        p.updateInventory();
        creadorTrato.updateInventory();
    }

    private void sumarTotalP2(Player p, Player participante, int amount) {
        Inventory invP = p.getOpenInventory().getTopInventory();
        Inventory invC = participante.getOpenInventory().getTopInventory();
        ItemStack dinero = invP.getItem(6);
        ItemMeta dineroMeta = dinero.getItemMeta();
        List<String> lores = dineroMeta.getLore();
        int actual = Integer.parseInt(lores.get(0).substring(0, lores.get(0).length() - 1));
        int res = actual + amount;
        if (res < 0) {
            res = 0;
        }
        ItemStack nuevoDinero = this.tinv.totaldinero(res);
        invP.setItem(6, nuevoDinero);
        invC.setItem(6, nuevoDinero);
        p.updateInventory();
        participante.updateInventory();
    }

    private void cambiarPorNoaceptado(Player p1, Player p2) {
        aceptados.remove(p1);
        aceptados.remove(p2);
        confirmados.remove(p1);
        confirmados.remove(p2);
        ItemStack noaceptado = tinv.noaceptado();
        Inventory inv = p1.getOpenInventory().getTopInventory();
        inv.setItem(3, noaceptado);
        inv.setItem(5, noaceptado);
        inv = p2.getOpenInventory().getTopInventory();
        inv.setItem(3, noaceptado);
        inv.setItem(5, noaceptado);
    }

    public boolean playerEnTrato(Player p) {
        return tratos.containsKey(p) || tratos.containsValue(p);
    }

    public boolean playerEnPeticion(Player p) {
        return peticiones.containsKey(p) || peticiones.containsValue(p);
    }

    private void cambiarNoAceptadoPorAceptadoP2(Player key, Player value) {
        ItemStack aceptado = tinv.aceptar();
        key.getOpenInventory().getTopInventory().setItem(5, aceptado);
        value.getOpenInventory().getTopInventory().setItem(5, aceptado);
    }

    private void cambiarNoAceptadoPorAceptadoP1(Player key, Player value) {
        ItemStack aceptado = tinv.aceptar();
        key.getOpenInventory().getTopInventory().setItem(3, aceptado);
        value.getOpenInventory().getTopInventory().setItem(3, aceptado);
    }

    private void cambiarAceptarPorConfirmadoP2(Player key, Player value) {
        ItemStack confirmado = tinv.confirmar();
        key.getOpenInventory().getTopInventory().setItem(5, confirmado);
        value.getOpenInventory().getTopInventory().setItem(5, confirmado);
    }

    private void cambiarAceptarPorConfirmadoP1(Player key, Player value) {
        ItemStack confirmado = tinv.confirmar();
        key.getOpenInventory().getTopInventory().setItem(3, confirmado);
        value.getOpenInventory().getTopInventory().setItem(3, confirmado);
    }

    private int moneyP1(Player p) {
        ItemStack dinerop1 = p.getOpenInventory().getTopInventory().getItem(2);
        ItemMeta dineroMeta = dinerop1.getItemMeta();
        List<String> lores = dineroMeta.getLore();
        return Integer.parseInt(lores.get(0).substring(0, lores.get(0).length() - 1));
    }

    private int moneyP2(Player p) {
        ItemStack dinerop2 = p.getOpenInventory().getTopInventory().getItem(6);
        ItemMeta dineroMeta = dinerop2.getItemMeta();
        List<String> lores = dineroMeta.getLore();
        return Integer.parseInt(lores.get(0).substring(0, lores.get(0).length() - 1));
    }

    private int freeSlots(Player p) {
        int freeslots = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) {
                freeslots++;
            }
        }
        return freeslots;
    }

    private boolean hasFreeSlotsP1(Player p1) {
        int usedSlots = 0;
        Inventory invp1 = p1.getOpenInventory().getTopInventory();
        for (Integer a : slotsDer) {
            int x = a.intValue();
            if (invp1.getItem(x) != null) {
                if (invp1.getItem(x).getType() != Material.AIR) {
                    usedSlots++;
                }
            }
        }
        return freeSlots(p1) >= usedSlots;
    }

    private boolean hasFreeSlotsP2(Player p2) {
        int usedSlots = 0;
        Inventory invp1 = p2.getOpenInventory().getTopInventory();
        for (Integer a : slotsIzq) {
            int x = a.intValue();
            Bukkit.getLogger().info(invp1.toString());
            if (invp1.getItem(x) != null) {
                if (invp1.getItem(x).getType() != Material.AIR) {
                    usedSlots++;
                }
            }

        }

        return freeSlots(p2)
                >= usedSlots;
    }
}
