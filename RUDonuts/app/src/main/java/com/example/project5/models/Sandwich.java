package com.example.project5.models;
import java.util.ArrayList;

/**
 * Represents a sandwich menu item consisting of
 * a bread selection, protein, and optional add-ons.
 * @author Raahil Khan.
 */
public class Sandwich extends MenuItem {
    private static final double BEEF_PRICE = 12.99;
    private static final double CHICKEN_PRICE = 10.99;
    private static final double SALMON_PRICE = 14.99;
    private static final double VEGGIE_PRICE = 0.30;
    private static final double CHEESE_PRICE = 1.00;

    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Constructs a Sandwich item.
     * @param bread the selected bread type
     * @param protein the selected protein type
     * @param addOns the list of chosen add-ons
     * @param quantity the quantity ordered
     */
    public Sandwich(Bread bread, Protein protein, ArrayList<AddOns> addOns, int quantity) {
        super(quantity);
        this.bread = bread;
        this.protein = protein;
        if (addOns != null) {
            this.addOns = addOns;
        } else {
            this.addOns = new ArrayList<>();
        }
    }

    /**
     * Computes the price of the sandwich based on protein and add-ons.
     * @return total price of this sandwich order
     */
    @Override
    public double price() {
        double basePrice;
        switch (protein) {
            case BEEF:
                basePrice = BEEF_PRICE;
                break;
            case CHICKEN:
                basePrice = CHICKEN_PRICE;
                break;
            case SALMON:
                basePrice = SALMON_PRICE;
                break;
            default:
                basePrice = 0;
        }
        double addOnCost = 0;
        for (AddOns add : addOns) {
            if (add == AddOns.CHEESE) {
                addOnCost += CHEESE_PRICE;
            } else {
                addOnCost += VEGGIE_PRICE;
            }
        }
        return (basePrice + addOnCost) * quantity;
    }

    /**
     * String representation used in current-order and order-history views.
     */
    @Override
    public String toString() {
        return "Sandwich (" + protein +
                ", " + bread +
                ", Add-ons: " + addOns +
                ", Quantity: " + quantity +
                ", Price: $" + String.format("%.2f", price()) + ")";
    }

    /**
     * Gets bread type.
     * @return the bread type
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Gets protein type.
     * @return the protein type
     */
    public Protein getProtein() {
        return protein;
    }

    /**
     * Gets the addons
     * @return the addons
     */
    public ArrayList<AddOns> getAddOns() {
        return addOns;
    }

    /**
     * Sets the new bread type.
     * @param bread the new bread type
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }

    /**
     * Sets the new protein type.
     * @param protein the new protein type
     */
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    /**
     * Sets the new addons.
     * @param addOns the new addons
     */
    public void setAddOns(ArrayList<AddOns> addOns) {
        this.addOns = addOns;
    }
}
