package com.example.rahul.cookingrecipes;

public class Recipe {

    String name;
    String description;
    String url;


    public Recipe(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
