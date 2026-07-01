package com.example.project5;

import com.example.project5.models.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements Singleton design pattern for sharing data between difference activities.
 * @author Raahil Khan
 */
public class OrderSingleton {
    private static OrderSingleton instance;
    private Order currentOrder;
    private List<Order> allOrders;

    /**
     * Constructor for singleton.
     */
    private OrderSingleton() {
        currentOrder = new Order(1);
        allOrders = new ArrayList<>();
    }

    /**
     * Accesses singleton instance.
     * @return singleton instance
     */
    public static OrderSingleton getInstance() {
        if (instance == null) {
            instance = new OrderSingleton();
        }
        return instance;
    }

    /**
     * Places the current order into the list of all orders
     */
    public void placeCurrentOrder() {
        allOrders.add(currentOrder);
        int nextOrderNumber = currentOrder.getOrderNumber() + 1;
        currentOrder = new Order(nextOrderNumber);
    }

    /**
     * Gets the current order.
     * @return the current order
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Gets all the orders.
     * @return all of the orders
     */
    public List<Order> getAllOrders() {
        return allOrders;
    }

    /**
     * Removes an order from history.
     */
    public void removeOrder(Order order) {
        allOrders.remove(order);
    }

    /**
     * Clears all history
     */
    public void clearAllOrders() {
        allOrders.clear();
    }


}
