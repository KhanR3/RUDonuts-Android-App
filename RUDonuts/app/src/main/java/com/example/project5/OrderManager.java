package com.example.project5;

import com.example.project5.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage the current order and order history throughout the application.
 * Provides a single point of access to the current order and all placed orders for all activities.
 * @author Rahul Battula
 */
public class OrderManager {
    private static OrderManager instance;
    private Order currentOrder;
    private int nextOrderNumber;
    private ArrayList<Order> orderHistory;

    /**
     * Private constructor to prevent instantiation.
     */
    private OrderManager() {
        nextOrderNumber = 1;
        currentOrder = new Order(nextOrderNumber);
        orderHistory = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of OrderManager.
     * @return the singleton OrderManager instance.
     */
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    /**
     * Gets the current order.
     * @return the current Order object.
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Places the current order by adding it to history and creating a new order.
     * Only adds to history if current order has items.
     */
    public void placeOrder() {
        if (!currentOrder.getItems().isEmpty()) {
            orderHistory.add(currentOrder);
        }
        nextOrderNumber++;
        currentOrder = new Order(nextOrderNumber);
    }

    /**
     * Clears all items from the current order without incrementing order number.
     */
    public void clearCurrentOrder() {
        currentOrder.clear();
    }

    /**
     * Gets the list of all placed orders.
     * @return List of Order objects representing order history.
     */
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    /**
     * Cancels (removes) an order from the order history.
     * @param order The order to cancel.
     * @return true if the order was found and removed, false otherwise.
     */
    public boolean cancelOrder(Order order) {
        return orderHistory.remove(order);
    }

    /**
     * Cancels (removes) an order from the order history by order number.
     * @param orderNumber The order number to cancel.
     * @return true if the order was found and removed, false otherwise.
     */
    public boolean cancelOrderByNumber(int orderNumber) {
        for (Order order : orderHistory) {
            if (order.getOrderNumber() == orderNumber) {
                return orderHistory.remove(order);
            }
        }
        return false;
    }
}
