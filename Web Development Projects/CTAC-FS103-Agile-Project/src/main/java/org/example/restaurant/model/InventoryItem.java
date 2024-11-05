package org.example.restaurant.model;

/*
Represents an inventory item with properties like ingredient name, quantity, etc.
 */

public class InventoryItem {
    private String ingredientName;
    private int quantity;

    public InventoryItem(String ingredientName, int quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }
}
