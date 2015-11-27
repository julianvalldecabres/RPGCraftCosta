
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.items.jewels;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.items.Quality;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLoreManager;
import com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLores;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGPlayerUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGJewelManager {

    private RPGCraftCosta plugin;
    private HashMap<String, RPGJewel> jewelList;
    private File jewelFile;
    private File jewelConfig;
    private FileConfiguration config;
    private FileConfiguration jConfig;
    private double breakp;
    private double losep;
    private double losepperlore;
    private double nothingp;

    public RPGJewelManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.plugin.getLogger().info("Loading jewels module....");
        this.jewelList = new HashMap<>();
        this.jewelFile = new File(RPGFinals.jewelFilePath);
        this.jewelConfig = new File(RPGFinals.jewelsConfigPath);
        if (!jewelFile.exists()) {
            plugin.getLogger().info("Loading default jewels...");
            jewelFile.getParentFile().mkdirs();
            copy(this.plugin.getResource("jewels.yml"), jewelFile);
        }
        if (!jewelConfig.exists()) {
            plugin.getLogger().info("Loading default jewels config...");
            jewelConfig.getParentFile().mkdirs();
            copy(this.plugin.getResource("jewelsConfig.yml"), jewelConfig);
        }
        loadJewelsConfig();
        loadJewels();
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadJewels() {
        if (!jewelFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.jewelFilePath));
        } else {
            config = YamlConfiguration.loadConfiguration(jewelFile);
        }
        plugin.getLogger().info("Loading jewels...");
        String name;
        ItemStack item; //En el fichero se guardara y salvara el ID del Material (ej. DIAMOND_SWORD)
        int sellprice;
        int buyprice;
        Quality quality;
        boolean comerciable;
        boolean combinable;
        double physicalattack;
        double physicaldefense;
        double physicalevasion;
        double physicalhitrate;
        double magicalattack;
        double magicaldefense;
        double magicalevasion;
        double magicalhitrate;
        double health;
        double mana;
        double critical;
        double healthsteal;
        double manasteal;
        double apbonus;
        double xpbonus;
        double moneybonus;

        Set<String> jewels = config.getKeys(false);
        for (String jewel : jewels) {
            ConfigurationSection section = config.getConfigurationSection(jewel);
            name = jewel;
            item = new ItemStack(Material.matchMaterial(section.getString("item")));
            comerciable = section.getBoolean("comerciable");
            sellprice = section.getInt("sellprice");
            buyprice = section.getInt("buyprice");
            quality = Enum.valueOf(Quality.class, section.getString("quality"));
            combinable = section.getBoolean("combinable");
            physicalattack = section.getDouble("physicalattack");
            physicaldefense = section.getDouble("physicaldefense");
            physicalevasion = section.getDouble("physicalevasion");
            physicalhitrate = section.getDouble("physicalhitrate");
            magicalattack = section.getDouble("magicalattack");
            magicaldefense = section.getDouble("magicaldefense");
            magicalevasion = section.getDouble("magicalevasion");
            magicalhitrate = section.getDouble("magicalhitrate");
            health = section.getDouble("health");
            mana = section.getDouble("mana");
            critical = section.getDouble("critical");
            healthsteal = section.getDouble("healthsteal");
            manasteal = section.getDouble("manasteal");
            apbonus = section.getDouble("apbonus");
            xpbonus = section.getDouble("xpbonus");
            moneybonus = section.getDouble("moneybonus");
            jewelList.put(jewel, new RPGJewel(item, name, sellprice, buyprice, quality, combinable, comerciable, physicalattack, physicaldefense, physicalevasion, physicalhitrate, magicalattack, magicaldefense, magicalevasion, magicalhitrate, health, mana, critical, healthsteal, manasteal, apbonus, xpbonus, moneybonus));

            //modificar constructor
        }
    }

    public ItemStack getRPGJewelByName(String name) {
        plugin.getLogger().info(name);
        RPGJewel jewel = jewelList.get(ChatColor.stripColor(name));
        return jewel.getItem();
    }

    public RPGJewel getRPGJewelByItem(ItemStack item) {
        return this.jewelList.get(getRPGJewelNameByItem(item));
    }

    public String getRPGJewelNameByItem(ItemStack item) {
        return ChatColor.stripColor(item.getItemMeta().getDisplayName());
    }

    public Set<String> getAllJewelNames() {
        Set<String> allWeaponNames = new HashSet<>();
        for (Map.Entry<String, RPGJewel> entrySet : jewelList.entrySet()) {
            String key = entrySet.getKey();
            allWeaponNames.add(key);
        }
        return allWeaponNames;
    }

    public boolean isRPGJewel(ItemStack item) {
        return this.jewelList.containsKey(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
    }

    private void loadJewelsConfig() {
        if (!jewelConfig.exists()) {
            jConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.jewelsConfigPath));
        } else {
            jConfig = YamlConfiguration.loadConfiguration(jewelConfig);
        }
        plugin.getLogger().info("Loading jewels config...");

        this.breakp = jConfig.getDouble("breakp");
        this.losep = jConfig.getDouble("losep");
        this.losepperlore = jConfig.getDouble("losepperlore");
        this.nothingp = jConfig.getDouble("nothingp");

    }

    public double getBreakp() {
        return breakp;
    }

    public double getLosep() {
        return losep;
    }

    public double getLosepperlore() {
        return losepperlore;
    }

    public double getNothingp() {
        return nothingp;
    }

    public String upgradeJewel() {
        Double caso = new Random().nextDouble();
        if (caso <= this.breakp) {
            return "break";
        } else if (this.breakp < caso && caso <= this.losep) {
            return "lose";
        } else if (this.losep < caso && caso <= this.nothingp) {
            return "nothing";
        } else {
            return "combine";
        }
    }

    ItemStack combineloseJewels(ItemStack cursor, ItemStack currentItem) {
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cursorLores = rpgLMan.getListOfLoresFromItem(cursor);
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> currentItemLores = rpgLMan.getListOfLoresFromItem(currentItem);
        //recoger los lores que nos interesan de ambos
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cIGLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : currentItemLores) {
            if (!rpglore.getLoretype().equals(RPGLores.BUYPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.SELLPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMERCIABLE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMBINABLE)) {
                cIGLores.add(rpglore);
            }
        }
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cUGLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : cursorLores) {
            if (!rpglore.getLoretype().equals(RPGLores.BUYPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.SELLPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMERCIABLE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMBINABLE)) {
                cUGLores.add(rpglore);
            }
        }
        //Ahora que tenemos que lores tiene cada uno vamos a combinarlos
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> newLores = new ArrayList<>();
        List<Boolean> cIAddedL = new ArrayList<>(Collections.nCopies(cIGLores.size(), false));
        List<Boolean> cUAddedL = new ArrayList<>(Collections.nCopies(cUGLores.size(), false));
        //Aqui sumamos los lores que son iguales
        for (int i = 0; i < cIGLores.size(); i++) {
            for (int j = 0; j < cUGLores.size(); j++) {
                //Solo los que coincidan
                if (cIGLores.get(i).getLoretype().equals(cUGLores.get(j).getLoretype())) {
                    com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                    if (RPGPlayerUtils.checkProbability(0.5)) {
                        lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cIGLores.get(i).getValue());
                    } else {
                        lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cUGLores.get(i).getValue());
                    }
                    newLores.add(lore);
                    cIAddedL.set(i, Boolean.TRUE);
                    cUAddedL.set(i, Boolean.TRUE);
                }
            }
        }
        //Aqui añadimos los que faltan
        for (int i = 0; i < cIGLores.size(); i++) {
            if (!cIAddedL.get(i)) {
                if (RPGPlayerUtils.checkProbability(losepperlore)) {
                    com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                    lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cIGLores.get(i).getValue());
                    newLores.add(lore);
                }
            }
        }
        for (int i = 0; i < cUGLores.size(); i++) {
            if (!cUAddedL.get(i)) {
                if (RPGPlayerUtils.checkProbability(losepperlore)) {
                    com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                    lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cUGLores.get(i).getLoretype(), cUGLores.get(i).getValue());
                    newLores.add(lore);
                }
            }
        }
        if (newLores.isEmpty()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(0).getLoretype(), cIGLores.get(0).getValue());
            newLores.add(lore);
        }
        //Obtener nombre
        RPGJewel rpgJewel = getRPGJewelByItem(cursor);
        //Añadir precios atributos especiales
        if (rpgJewel.isComerciable()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.BUYPRICE, rpgJewel.getBuyPrice());
            newLores.add(lore);
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.SELLPRICE, rpgJewel.getSellPrice());
            newLores.add(lore);
        } else {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.NOCOMERCIABLE, null);
            newLores.add(lore);
        }
        if (!rpgJewel.isCombinable()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.NOCOMBINABLE, null);
            newLores.add(lore);
        }
        List<String> stringLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : newLores) {
            switch (rpglore.getLoretype()) {
                case NOCOMERCIABLE:
                case NOCOMBINABLE:
                    stringLores.add(ChatColor.RED + rpglore.toString());
                default:
                    stringLores.add(rpglore.toString());
            }
        }
        ItemStack newJewel = currentItem;
        ItemMeta jMeta = newJewel.getItemMeta();
        jMeta.setLore(stringLores);
        newJewel.setItemMeta(jMeta);
        return newJewel;
    }

    ItemStack combineNoSumJewels(ItemStack cursor, ItemStack currentItem) {
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cursorLores = rpgLMan.getListOfLoresFromItem(cursor);
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> currentItemLores = rpgLMan.getListOfLoresFromItem(currentItem);
        //recoger los lores que nos interesan de ambos
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cIGLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : currentItemLores) {
            if (!rpglore.getLoretype().equals(RPGLores.BUYPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.SELLPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMERCIABLE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMBINABLE)) {
                cIGLores.add(rpglore);
            }
        }
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cUGLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : cursorLores) {
            if (!rpglore.getLoretype().equals(RPGLores.BUYPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.SELLPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMERCIABLE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMBINABLE)) {
                cUGLores.add(rpglore);
            }
        }
        //Ahora que tenemos que lores tiene cada uno vamos a combinarlos
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> newLores = new ArrayList<>();
        List<Boolean> cIAddedL = new ArrayList<>(Collections.nCopies(cIGLores.size(), false));
        List<Boolean> cUAddedL = new ArrayList<>(Collections.nCopies(cUGLores.size(), false));
        //Aqui sumamos los lores que son iguales
        for (int i = 0; i < cIGLores.size(); i++) {
            for (int j = 0; j < cUGLores.size(); j++) {
                //Solo los que coincidan
                if (cIGLores.get(i).getLoretype().equals(cUGLores.get(j).getLoretype())) {
                    com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                    if (RPGPlayerUtils.checkProbability(0.5)) {
                        lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cIGLores.get(i).getValue());
                    } else {
                        lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cUGLores.get(i).getValue());
                    }
                    newLores.add(lore);
                    cIAddedL.set(i, Boolean.TRUE);
                    cUAddedL.set(i, Boolean.TRUE);
                }
            }
        }
        //Aqui añadimos los que faltan
        for (int i = 0; i < cIGLores.size(); i++) {
            if (!cIAddedL.get(i)) {

                com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cIGLores.get(i).getValue());
                newLores.add(lore);

            }
        }
        for (int i = 0; i < cUGLores.size(); i++) {
            if (!cUAddedL.get(i)) {

                com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cUGLores.get(i).getLoretype(), cUGLores.get(i).getValue());
                newLores.add(lore);

            }
        }
        //Obtener nombre
        RPGJewel rpgJewel = getRPGJewelByItem(cursor);
        //Añadir precios atributos especiales
        if (rpgJewel.isComerciable()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.BUYPRICE, rpgJewel.getBuyPrice());
            newLores.add(lore);
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.SELLPRICE, rpgJewel.getSellPrice());
            newLores.add(lore);
        } else {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.NOCOMERCIABLE, null);
            newLores.add(lore);
        }
        if (!rpgJewel.isCombinable()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.NOCOMBINABLE, null);
            newLores.add(lore);
        }
        List<String> stringLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : newLores) {
            switch (rpglore.getLoretype()) {
                case NOCOMERCIABLE:
                case NOCOMBINABLE:
                    stringLores.add(ChatColor.RED + rpglore.toString());
                default:
                    stringLores.add(rpglore.toString());
            }
        }
        ItemStack newJewel = currentItem;
        ItemMeta jMeta = newJewel.getItemMeta();
        jMeta.setLore(stringLores);
        newJewel.setItemMeta(jMeta);
        return newJewel;
    }

    public ItemStack combineJewels(ItemStack cursor, ItemStack currentItem) {
        RPGLoreManager rpgLMan = plugin.getRPGItemManager().getRPGLoreManager();
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cursorLores = rpgLMan.getListOfLoresFromItem(cursor);
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> currentItemLores = rpgLMan.getListOfLoresFromItem(currentItem);
        //recoger los lores que nos interesan de ambos
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cIGLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : currentItemLores) {
            if (!rpglore.getLoretype().equals(RPGLores.BUYPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.SELLPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMERCIABLE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMBINABLE)) {
                cIGLores.add(rpglore);
            }
        }
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> cUGLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : cursorLores) {
            if (!rpglore.getLoretype().equals(RPGLores.BUYPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.SELLPRICE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMERCIABLE)
                    && !rpglore.getLoretype().equals(RPGLores.NOCOMBINABLE)) {
                cUGLores.add(rpglore);
            }
        }
        //Ahora que tenemos que lores tiene cada uno vamos a combinarlos
        List<com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore> newLores = new ArrayList<>();
        List<Boolean> cIAddedL = new ArrayList<>(Collections.nCopies(cIGLores.size(), false));
        List<Boolean> cUAddedL = new ArrayList<>(Collections.nCopies(cUGLores.size(), false));
        //Aqui sumamos los lores que son iguales
        for (int i = 0; i < cIGLores.size(); i++) {
            for (int j = 0; j < cUGLores.size(); j++) {
                //Solo los que coincidan
                if (cIGLores.get(i).getLoretype().equals(cUGLores.get(j).getLoretype())) {
                    com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                    lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), sumObjects(cIGLores.get(i).getValue(),cUGLores.get(j).getValue()));
                    newLores.add(lore);
                    cIAddedL.set(i, Boolean.TRUE);
                    cUAddedL.set(i, Boolean.TRUE);
                }
            }
        }
        for (int i = 0; i < cIGLores.size(); i++) {
            if (!cIAddedL.get(i)) {
                com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cIGLores.get(i).getLoretype(), cIGLores.get(i).getValue());
                newLores.add(lore);
            }
        }
        for (int i = 0; i < cUGLores.size(); i++) {
            if (!cUAddedL.get(i)) {
                com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
                lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(cUGLores.get(i).getLoretype(), cUGLores.get(i).getValue());
                newLores.add(lore);
            }
        }
        //Obtener nombre
        RPGJewel rpgJewel = getRPGJewelByItem(cursor);
        //Añadir precios atributos especiales
        if (rpgJewel.isComerciable()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.BUYPRICE, rpgJewel.getBuyPrice());
            newLores.add(lore);
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.SELLPRICE, rpgJewel.getSellPrice());
            newLores.add(lore);
        } else {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.NOCOMERCIABLE, null);
            newLores.add(lore);
        }
        if (!rpgJewel.isCombinable()) {
            com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore lore = null;
            lore = new com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore(RPGLores.NOCOMBINABLE, null);
            newLores.add(lore);
        }
        List<String> stringLores = new ArrayList<>();
        for (com.craftcosta.jailrules.rpgcraftcosta.items.lores.RPGLore rpglore : newLores) {
            switch (rpglore.getLoretype()) {
                case NOCOMERCIABLE:
                case NOCOMBINABLE:
                    stringLores.add(ChatColor.RED + rpglore.toString());
                default:
                    stringLores.add(rpglore.toString());
            }
        }
        ItemStack newJewel = currentItem;
        ItemMeta jMeta = newJewel.getItemMeta();
        jMeta.setLore(stringLores);
        newJewel.setItemMeta(jMeta);
        return newJewel;
    }
    
    public double sumObjects(Object o1,Object o2){
        return (double)o1+(double)o2;
    }
}