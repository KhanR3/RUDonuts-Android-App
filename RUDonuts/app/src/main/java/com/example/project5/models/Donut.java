package com.example.project5.models;
import java.util.Objects;

/**
 * Represents a donut menu item.
 * A donut has a donut type and a flavor.
 * @author Raahil Khan
 */
public class Donut extends MenuItem {
    private static final double YEAST_PRICE = 1.99;
    private static final double CAKE_PRICE = 2.19;
    private static final double HOLE_PRICE = 0.39;
    private static final double SEASONAL_PRICE = 2.49;

    private DonutType type;
    private DonutFlavor flavor;

    /**
     * Constructs a Donut item.
     * @param type    the donut type
     * @param flavor  the flavor name
     * @param quantity the quantity ordered
     */
    public Donut(DonutType type, DonutFlavor flavor, int quantity) {
        super(quantity);
        this.type = type;
        this.flavor = flavor;
    }

    /**
     * Computes the price for this donut based on type and quantity.
     * @return total price for this donut order
     */
    @Override
    public double price() {
        double basePrice;
        switch (type) {
            case YEAST:
                basePrice = YEAST_PRICE;
                break;
            case CAKE:
                basePrice = CAKE_PRICE;
                break;
            case HOLE:
                basePrice = HOLE_PRICE;
                break;
            case SEASONAL:
                basePrice = SEASONAL_PRICE;
                break;
            default:
                basePrice = 0.0;
        }
        return basePrice * quantity;
    }

    /**
     * Returns a formatted string for display in Current Order.
     */
    @Override
    public String toString() {
        return type + " Donut (" + flavor +
                "), Quantity: " + quantity +
                ", Price: $" + String.format("%.2f", price());
    }

    /**
     * Determines if two donut objects are the same.
     * @param o   the reference object with which to compare.
     * @return true if they are same; return false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donut)) return false;

        Donut donut = (Donut) o;
        return quantity == donut.quantity &&
                type == donut.type &&
                Objects.equals(flavor, donut.flavor);
    }

    /**
     * Gets donut type.
     * @return the donut type
     */
    public DonutType getType() {
        return type;
    }

    /**
     * Sets new donut type.
     * @param type type of donut
     */
    public void setType(DonutType type) {
        this.type = type;
    }

    /**
     * Gets donut flavor.
     * @return the flavor of the donut
     */
    public DonutFlavor getFlavor() {
        return flavor;
    }

    /**
     * Sets new donut flavor.
     * @param flavor the flavor of the donut
     */
    public void setFlavor(DonutFlavor flavor) {
        this.flavor = flavor;
    }
}

