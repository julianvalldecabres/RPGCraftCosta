/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.recipes;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

/**
 *
 * @author jail
 */
public class RPGRecipe {

    private String name;
    private ShapelessRecipe recipe;
    private List<RPGIngredient> ingredients;

    public RPGRecipe(String name, ShapelessRecipe recipe, List<RPGIngredient> ingredients) {
        this.name = name;
        this.recipe = recipe;
        this.ingredients = ingredients;
        for (RPGIngredient ingredient : ingredients) {
            recipe.addIngredient(ingredient.getMaterial());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShapelessRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(ShapelessRecipe recipe) {
        this.recipe = recipe;
    }

    public List<RPGIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RPGIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
