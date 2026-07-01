package com.example.project5.models;
import java.util.ArrayList;

/**
 * Represents a brewed coffee menu item with a cup size and optional add-ins.
 * Each add-in affects the price, and the price scales with quantity.
 * @author Raahil Khan
 */
public class Coffee extends MenuItem {
    private static final double BASE_PRICE_SHORT = 2.39;
    private static final double SIZE_UPCHARGE = 0.60;
    private static final double ADDIN_PRICE = 0.25;

    private CupSize size;
    private ArrayList<AddIns> addIns;

    /**
     * Constructs a Coffee item.
     * @param size the selected cup size
     * @param addIns the list of selected add-ins
     * @param quantity the quantity ordered
     */
    public Coffee(CupSize size, ArrayList<AddIns> addIns, int quantity) {
        super(quantity);
        this.size = size;
        if (addIns != null) {
            this.addIns = addIns;
        } else {
            this.addIns = new ArrayList<>();
        }
    }

    /**
     * Computes the price for one coffee of the selected size and add-ins.
     * @return total price for this Coffee item
     */
    @Override
    public double price() {
        double price = BASE_PRICE_SHORT;
        switch (size) {
            case TALL:
                price += SIZE_UPCHARGE;
                break;
            case GRANDE:
                price += SIZE_UPCHARGE * 2;
                break;
            case VENTI:
                price += SIZE_UPCHARGE * 3;
                break;
            default:
                break;
        }
        price += addIns.size() * ADDIN_PRICE;
        return price * quantity;
    }

    /**
     * String representation used for displaying in order views.
     */
    @Override
    public String toString() {
        return "Coffee (" + size +
                ", Add-ins: " + addIns +
                ", Quantity: " + quantity +
                ", Price: $" + String.format("%.2f", price()) + ")";
    }

    /**
     * Gets the cup size.
     * @return the cup size
     */
    public CupSize getSize() {
        return size;
    }

    /**
     * Gets the addins.
     * @return the addins
     */
    public ArrayList<AddIns> getAddIns() {
        return addIns;
    }

    /**
     * Sets the new cup size.
     * @param size the size of the cup
     */
    public void setSize(CupSize size) {
        this.size = size;
    }

    /**
     * Sets the new addins
     * @param addIns the new addins
     */
    public void setAddIns(ArrayList<AddIns> addIns) {
        this.addIns = addIns;
    }
}
