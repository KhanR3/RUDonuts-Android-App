package com.example.project5.models;

/**
 * The abstract superclass for all menu items at RU Donuts.
 * Specific menu items such as Coffee, Donut, and Sandwich extend this class.
 * @author Raahil Khan
 */
public abstract class MenuItem {
    protected int quantity;

    /**
     * Creates a MenuItem with the given quantity.
     * @param quantity the quantity of this item
     */
    public MenuItem(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Computes the price of the item based on type, selection,
     * and quantity.
     * @return the total price for this item
     */
    public abstract double price();

    /**
     * Returns a string representation of this menu item.
     * @return a basic string representation
     */
    @Override
    public String toString() {
        return "Quantity: " + quantity;
    }

    /**
     * Gets the quantity of this menu item.
     * @return the quantity ordered
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this menu item.
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
