package com.example.project5.models;
import java.util.ArrayList;

/**
 * Represents a complete order in the RU Donuts system.
 * @author Raahil Khan
 */
public class Order {
    private static final double TAX_RATE = 0.06625;

    private int orderNumber;
    private ArrayList<MenuItem> items;

    /**
     * Constructs an Order.
     * @param orderNumber the unique order number
     */
    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.items = new ArrayList<>();
    }

    /**
     * Adds a menu item to the order.
     * @param item the item to add
     */
    public void addItem(MenuItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    /**
     * Removes the menu item from the order.
     * @param item the item to remove
     */
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    /**
     * Removes all items from the order.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Calculates the subtotal.
     * @return subtotal of the order
     */
    public double getSubtotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.price();
        }
        return total;
    }

    /**
     * Calculates the tax based on the subtotal.
     * @return tax amount
     */
    public double getTax() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Calculates the final total including tax.
     * @return total cost including tax
     */
    public double getTotal() {
        return getSubtotal() + getTax();
    }

    /**
     * Returns a string representation of the order.
     */
    @Override
    public String toString() {
        String result = "Order #" + orderNumber + "\n";

        for (MenuItem item : items) {
            result += "  - " + item.toString() + "\n";
        }
        result += "Subtotal: $" + String.format("%.2f", getSubtotal()) + "\n";
        result += "Tax: $" + String.format("%.2f", getTax()) + "\n";
        result += "Total: $" + String.format("%.2f", getTotal());

        return result;
    }

    /**
     * Gets the order number.
     * @return the order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Gets the menu items.
     * @return the menu items
     */
    public ArrayList<MenuItem> getItems() {
        return items;
    }

    /**
     * Sets new order number.
     * @param orderNumber the new order number
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Sets new menu items.
     * @param items new menu items
     */
    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }
}
