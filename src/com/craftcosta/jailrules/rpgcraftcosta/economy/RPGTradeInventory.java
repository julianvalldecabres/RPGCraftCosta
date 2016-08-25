/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.economy;

import java.util.ArrayList;
import java.util.List;
import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGTradeInventory {

    private RPGCraftCosta plugin;
    public static ArrayList<Integer> slotsIzq = new ArrayList<>();
    public static ArrayList<Integer> slotsDer = new ArrayList<>();

    public RPGTradeInventory(RPGCraftCosta plugin) {
        this.plugin = plugin;
        inicializarSlots();
    }

    public ItemStack divisor() {
        ItemStack divisor = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0, (byte) 15);
        ItemMeta divisorMeta = divisor.getItemMeta();
        divisorMeta.setDisplayName(ChatColor.RED + "Divisor");
        divisor.setItemMeta(divisorMeta);
        return divisor;
    }

    public ItemStack totaldinero(int dinero) {
        ItemStack totaldinero = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta dineroMeta = totaldinero.getItemMeta();
        dineroMeta.setDisplayName(ChatColor.GOLD + "Dinero");
        List<String> lores = new ArrayList<String>();
        lores.add(dinero + "$");
        dineroMeta.setLore(lores);
        totaldinero.setItemMeta(dineroMeta);
        return totaldinero;
    }

    public ItemStack noaceptado() {
        ItemStack aceptar = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 0);
        ItemMeta aceptarMeta = aceptar.getItemMeta();
        aceptarMeta.setDisplayName(ChatColor.GREEN + "Pulsa para aceptar");
        aceptar.setItemMeta(aceptarMeta);
        return aceptar;
    }

    public ItemStack aceptar() {
        ItemStack aceptar = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 5);
        ItemMeta aceptarMeta = aceptar.getItemMeta();
        aceptarMeta.setDisplayName(ChatColor.GREEN + "Pulsa para confirmar");
        aceptar.setItemMeta(aceptarMeta);
        return aceptar;
    }

    public ItemStack confirmar() {
        ItemStack confirmar = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 10);
        ItemMeta confirmarMeta = confirmar.getItemMeta();
        confirmarMeta.setDisplayName(ChatColor.DARK_PURPLE + "Confirmar");
        confirmar.setItemMeta(confirmarMeta);
        return confirmar;
    }

    public ItemStack cancelar() {
        ItemStack cancelar = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 14);
        ItemMeta cancelarMeta = cancelar.getItemMeta();
        cancelarMeta.setDisplayName(ChatColor.DARK_PURPLE + "Cancelar");
        cancelar.setItemMeta(cancelarMeta);
        return cancelar;
    }

    public ItemStack dinero1() {
        ItemStack dinero1 = new ItemStack(Material.COAL);
        ItemMeta dinero1Meta = dinero1.getItemMeta();
        dinero1Meta.setDisplayName(ChatColor.GOLD + "1$");
        ArrayList<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "Click izquierdo para añadir 1$ al trato");
        lores.add(ChatColor.WHITE + "Click derecho para restar 1$ al trato");
        dinero1Meta.setLore(lores);
        dinero1.setItemMeta(dinero1Meta);
        return dinero1;
    }

    public ItemStack dinero10() {
        ItemStack dinero10 = new ItemStack(Material.IRON_INGOT);
        ItemMeta dinero10Meta = dinero10.getItemMeta();
        dinero10Meta.setDisplayName(ChatColor.GOLD + "10$");
        ArrayList<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "Click izquierdo para añadir 10$ al trato");
        lores.add(ChatColor.WHITE + "Click derecho para restar 10$ al trato");
        dinero10Meta.setLore(lores);
        dinero10.setItemMeta(dinero10Meta);
        return dinero10;
    }

    public ItemStack dinero100() {
        ItemStack dinero100 = new ItemStack(Material.GOLD_INGOT);
        ItemMeta dinero100Meta = dinero100.getItemMeta();
        dinero100Meta.setDisplayName(ChatColor.GOLD + "100$");
        ArrayList<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "Click izquierdo para añadir 100$ al trato");
        lores.add(ChatColor.WHITE + "Click derecho para restar 100$ al trato");
        dinero100Meta.setLore(lores);
        dinero100.setItemMeta(dinero100Meta);
        return dinero100;
    }

    public ItemStack dinero1000() {
        ItemStack dinero1000 = new ItemStack(Material.DIAMOND);
        ItemMeta dinero1000Meta = dinero1000.getItemMeta();
        dinero1000Meta.setDisplayName(ChatColor.GOLD + "1000$");
        ArrayList<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "Click izquierdo para añadir 1000$ al trato");
        lores.add(ChatColor.WHITE + "Click derecho para restar 1000$ al trato");
        dinero1000Meta.setLore(lores);
        dinero1000.setItemMeta(dinero1000Meta);
        return dinero1000;
    }

    public ItemStack dinero10000() {
        ItemStack dinero10000 = new ItemStack(Material.NETHER_STAR);
        ItemMeta dinero10000Meta = dinero10000.getItemMeta();
        dinero10000Meta.setDisplayName(ChatColor.GOLD + "10000$");
        ArrayList<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "Click izquierdo para añadir 10000$ al trato");
        lores.add(ChatColor.WHITE + "Click derecho para restar 10000$ al trato");
        dinero10000Meta.setLore(lores);
        dinero10000.setItemMeta(dinero10000Meta);
        return dinero10000;
    }

    private void inicializarSlots() {
        slotsIzq.add(Integer.parseInt("1"));
        slotsIzq.add(Integer.parseInt("10"));
        slotsIzq.add(Integer.parseInt("11"));
        slotsIzq.add(Integer.parseInt("12"));
        slotsIzq.add(Integer.parseInt("19"));
        slotsIzq.add(Integer.parseInt("20"));
        slotsIzq.add(Integer.parseInt("21"));
        slotsIzq.add(Integer.parseInt("28"));
        slotsIzq.add(Integer.parseInt("29"));
        slotsIzq.add(Integer.parseInt("30"));
        slotsIzq.add(Integer.parseInt("37"));
        slotsIzq.add(Integer.parseInt("38"));
        slotsIzq.add(Integer.parseInt("39"));

        slotsDer.add(Integer.parseInt("7"));
        slotsDer.add(Integer.parseInt("14"));
        slotsDer.add(Integer.parseInt("15"));
        slotsDer.add(Integer.parseInt("16"));
        slotsDer.add(Integer.parseInt("23"));
        slotsDer.add(Integer.parseInt("24"));
        slotsDer.add(Integer.parseInt("25"));
        slotsDer.add(Integer.parseInt("32"));
        slotsDer.add(Integer.parseInt("33"));
        slotsDer.add(Integer.parseInt("34"));
        slotsDer.add(Integer.parseInt("41"));
        slotsDer.add(Integer.parseInt("42"));
        slotsDer.add(Integer.parseInt("43"));
    }

    public static ArrayList<Integer> getSlotsIzq() {
        return slotsIzq;
    }

    public static ArrayList<Integer> getSlotsDer() {
        return slotsDer;
    }

    public void rellenarInventarioplayer1(Inventory inv1) {
        ItemStack divisor = divisor();
        ItemStack noaceptado = noaceptado();
        ItemStack aceptar = aceptar();
        ItemStack cancelar = cancelar();
        ItemStack dinero1 = dinero1();
        ItemStack dinero10 = dinero10();
        ItemStack dinero100 = dinero100();
        ItemStack dinero1000 = dinero1000();
        ItemStack dinero10000 = dinero10000();
        ItemStack total = totaldinero(0);
        ItemStack confirmar = confirmar();

        //sistema monetario
        inv1.setItem(2, total);
        inv1.setItem(0, divisor);
        inv1.setItem(9, divisor);
        inv1.setItem(18, divisor);
        inv1.setItem(27, divisor);
        inv1.setItem(36, divisor);
        inv1.setItem(6, total);
        inv1.setItem(8, dinero1);
        inv1.setItem(17, dinero10);
        inv1.setItem(26, dinero100);
        inv1.setItem(35, dinero1000);
        inv1.setItem(44, dinero10000);
        //divisor de espacio
        inv1.setItem(4, cancelar);
        inv1.setItem(13, divisor);
        inv1.setItem(22, divisor);
        inv1.setItem(31, divisor);
        inv1.setItem(40, divisor);

        //aceptar confirmar cancelar
        inv1.setItem(3, noaceptado);
        inv1.setItem(5, noaceptado);
    }

    public void rellenarInventarioplayer2(Inventory inv2) {
        ItemStack divisor = divisor();
        ItemStack aceptar = aceptar();
        ItemStack noaceptado = noaceptado();
        ItemStack cancelar = cancelar();
        ItemStack dinero1 = dinero1();
        ItemStack dinero10 = dinero10();
        ItemStack dinero100 = dinero100();
        ItemStack dinero1000 = dinero1000();
        ItemStack dinero10000 = dinero10000();
        ItemStack total = totaldinero(0);
        ItemStack confirmar = confirmar();

        //sistema monetario
        inv2.setItem(6, total);
        inv2.setItem(8, divisor);
        inv2.setItem(17, divisor);
        inv2.setItem(26, divisor);
        inv2.setItem(35, divisor);
        inv2.setItem(44, divisor);
        inv2.setItem(2, total);
        inv2.setItem(0, dinero1);
        inv2.setItem(9, dinero10);
        inv2.setItem(18, dinero100);
        inv2.setItem(27, dinero1000);
        inv2.setItem(36, dinero10000);
        //divisor de espacio
        inv2.setItem(4, cancelar);
        inv2.setItem(13, divisor);
        inv2.setItem(22, divisor);
        inv2.setItem(31, divisor);
        inv2.setItem(40, divisor);

        //aceptar confirmar cancelar
        inv2.setItem(3, noaceptado);
        inv2.setItem(5, noaceptado);
    }
}
