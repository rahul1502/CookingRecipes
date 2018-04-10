package com.example.rahul.cookingrecipes;

public class Recipe {

    String name;
    String description;
    String recipeId;
    float rating;


    public Recipe(String name, String description, String recipeId, float rating) {
        this.name = name;
        this.description = description;
        this.recipeId = recipeId;
        this.rating = rating;
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

    public float getRating() {
        return rating;
    }
}
