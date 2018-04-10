package com.example.rahul.cookingrecipes;

public class Recipe {

    String name;
    String description;
    String recipeId;


    public Recipe(String name, String description, String recipeId) {
        this.name = name;
        this.description = description;
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRecipeId() {
        return recipeId;
    }
}
