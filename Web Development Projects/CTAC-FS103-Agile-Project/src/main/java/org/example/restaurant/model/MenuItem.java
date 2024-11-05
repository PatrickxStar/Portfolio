package org.example.restaurant.model;

/*
Represents a menu item with properties like name, description, price, etc.
 */

import java.util.List;

public class MenuItem {
    private String name;
    private String description;
    private double price;
    private List<String> ingredients;

    public MenuItem(String name, String description, double price, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public double getPrice () {
        return price;
    }

    public void setPrice (double price) {
        this.price = price;
    }

    public List<String> getIngredients () {
        return ingredients;
    }

    public void setIngredients (List<String> ingredients) {
        this.ingredients = ingredients;
    }
}