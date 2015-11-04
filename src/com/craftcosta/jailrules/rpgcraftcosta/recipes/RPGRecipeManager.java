/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.recipes;

import com.craftcosta.jailrules.rpgcraftcosta.RPGCraftCosta;
import com.craftcosta.jailrules.rpgcraftcosta.utils.RPGFinals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jail
 */
public class RPGRecipeManager {

    private RPGCraftCosta plugin;
    private Map<String, RPGRecipe> recipeList;
    private File recipeFile;
    private File recipeConfig;
    private FileConfiguration rConfig;
    private FileConfiguration config;
    private ItemStack weaponUpgrader;
    private ItemStack armorUpgrader;
    private double breakprobability;
    private double detteroriateprobability;
    private double nothingprobability;
    private double improveprobability;

    public RPGRecipeManager(RPGCraftCosta plugin) {
        this.plugin = plugin;
        this.recipeList = new HashMap<>();
        this.recipeFile = new File(RPGFinals.recipeFilePath);
        this.recipeConfig = new File(RPGFinals.recipeConfigPath);
        if (!recipeFile.exists()) {
            plugin.getLogger().info("Loading default recipes...");
            recipeFile.getParentFile().mkdirs();
            copy(plugin.getResource("recipes.yml"), recipeFile);
        }
        loadRecipes();
        if (!recipeConfig.exists()) {
            plugin.getLogger().info("Loading default recipes config...");
            recipeConfig.getParentFile().mkdirs();
            copy(plugin.getResource("recipesConfig.yml"), recipeConfig);
        }
        loadRecipeConfig();
    }

    private void loadRecipes() {
        if (!recipeFile.exists()) {
            config = YamlConfiguration.loadConfiguration(new File(RPGFinals.recipeFilePath));
        } else {
            config = YamlConfiguration.loadConfiguration(recipeFile);
        }
        plugin.getLogger().info("Loading recipes");
        String name;

        Set<String> recipelist = config.getKeys(false);
        for (String recipe : recipelist) {
            ConfigurationSection section = config.getConfigurationSection(recipe);
            name = recipe;
            List<RPGIngredient> ingredients = new ArrayList<>();
            plugin.getLogger().info(recipe);
            String result = section.getString("result");
            Material resultMat = Material.matchMaterial(result);
            for (String sec : section.getKeys(false)) {
                String ingredient = sec;
                Material mat = Material.matchMaterial(sec);
                plugin.getLogger().info("\t" + sec);
                if (!sec.equals("result")) {
                    ConfigurationSection section2 = config.getConfigurationSection(recipe + "." + sec);
                    int count = section2.getInt("count");
                    int rawdata = section2.getInt("rawdata");
                    RPGIngredient ingrediente = new RPGIngredient(mat, count, rawdata);
                    ingredients.add(ingrediente);
                }
            }
            ShapelessRecipe shapelessReceta = new ShapelessRecipe(new ItemStack(resultMat));
            RPGRecipe receta = new RPGRecipe(name, shapelessReceta, ingredients);
            this.recipeList.put(name, receta);
        }
        for (Map.Entry<String, RPGRecipe> entrySet : recipeList.entrySet()) {
            Recipe rec = entrySet.getValue().getRecipe();
            plugin.getLogger().info(rec.toString());
            plugin.getLogger().info(rec.getResult().toString());
            plugin.getServer().addRecipe(rec);
        }

    }

    public Map<String, RPGRecipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(Map<String, RPGRecipe> recipeList) {
        this.recipeList = recipeList;
    }

    public RPGRecipe getRPGRecipeByShapelessRecipe(ShapelessRecipe recipe) {
        for (Map.Entry<String, RPGRecipe> entrySet : recipeList.entrySet()) {
            if (entrySet.getValue().getRecipe().equals(recipe)) {
                return entrySet.getValue();
            }
        }
        return null;
    }

    private void loadRecipeConfig() {
        if (!recipeConfig.exists()) {
            rConfig = YamlConfiguration.loadConfiguration(new File(RPGFinals.recipeConfigPath));
        } else {
            rConfig = YamlConfiguration.loadConfiguration(recipeConfig);
        }
        plugin.getLogger().info("Loading recipes config...");
        //Upgrader de armor
        ConfigurationSection section = rConfig.getConfigurationSection("armor");
        Material armorMat = Material.matchMaterial(section.getString("material"));
        String armordisplayname = section.getString("name");
        List<String> armorlores = section.getStringList("lores");
        this.armorUpgrader = new ItemStack(armorMat);
        ItemMeta armorMeta = armorUpgrader.getItemMeta();
        armorMeta.setDisplayName(ChatColor.DARK_PURPLE + armordisplayname);
        armorMeta.setLore(armorlores);
        this.armorUpgrader.setItemMeta(armorMeta);
        //Upgrader de weapon
        section = rConfig.getConfigurationSection("weapon");
        Material weaponMat = Material.matchMaterial(section.getString("material"));
        String weapondisplayname = section.getString("name");
        List<String> weaponlores = section.getStringList("lores");
        this.weaponUpgrader = new ItemStack(weaponMat);
        ItemMeta weaponMeta = weaponUpgrader.getItemMeta();
        weaponMeta.setDisplayName(weapondisplayname);
        weaponMeta.setLore(weaponlores);
        this.weaponUpgrader.setItemMeta(weaponMeta);
        this.breakprobability = Double.parseDouble(rConfig.getString("breakprobability"));
        this.detteroriateprobability = Double.parseDouble(rConfig.getString("detteroriateprobability"));
        this.nothingprobability = Double.parseDouble(rConfig.getString("nothingprobability"));
        this.improveprobability = Double.parseDouble(rConfig.getString("improveprobability"));
    }

    public double getBreakprobability() {
        return breakprobability;
    }

    public double getDetteroriateprobability() {
        return detteroriateprobability;
    }

    public double getNothingprobability() {
        return nothingprobability;
    }

    public double getImproveprobability() {
        return improveprobability;
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

    public ItemStack getWeaponUpgrader() {
        return weaponUpgrader;
    }

    public ItemStack getArmorUpgrader() {
        return armorUpgrader;
    }

    public String getUpgradeResult() {
        Double caso = new Random().nextDouble();
        if (caso<= this.breakprobability) {
            return "break";
        }else if(this.breakprobability< caso && caso <=this.detteroriateprobability){
            return "detteriorate";
        }else if(this.detteroriateprobability<caso && caso<=this.nothingprobability){
            return "nothing";
        }else{
            return "improve";
        }
        
    }
}
