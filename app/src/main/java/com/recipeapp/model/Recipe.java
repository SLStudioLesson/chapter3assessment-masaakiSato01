package com.recipeapp.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients ;
    public Recipe(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
    public String getName() {
        return name;
    }
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    
}
