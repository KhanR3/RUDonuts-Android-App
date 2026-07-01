package com.example.project5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project5.models.MenuItem;
import com.example.project5.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for viewing and managing all placed orders.
 * Displays order history with order numbers, totals, and items.
 * Allows canceling orders from history.
 * @author Rahul Battula
 */
public class OrderHistoryActivity extends AppCompatActivity {

    private ListView listViewOrders;
    private TextView textViewOrderDetails;
    private Button buttonCancelOrder;

    private ArrayAdapter<String> orderAdapter;
    private List<Order> orderHistory;
    private int selectedOrderPosition;

    /**
     * Initializes the order history activity and sets up UI components.
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupListView();
        setupListeners();
    }

    /**
     * Initializes all UI components by finding their views.
     */
    private void initializeViews() {
        listViewOrders = findViewById(R.id.listViewOrders);
        textViewOrderDetails = findViewById(R.id.textViewOrderDetails);
        buttonCancelOrder = findViewById(R.id.buttonCancelOrder);

        selectedOrderPosition = -1;
    }

    /**
     * Configures the ListView with order history data.
     */
    private void setupListView() {
        orderHistory = OrderManager.getInstance().getOrderHistory();
        List<String> orderSummaries = new ArrayList<>();

        for (Order order : orderHistory) {
            String summary = String.format("Order #%d - Total: $%.2f (%d items)",
                    order.getOrderNumber(),
                    order.getTotal(),
                    order.getItems().size());
            orderSummaries.add(summary);
        }

        orderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                orderSummaries);
        listViewOrders.setAdapter(orderAdapter);
        listViewOrders.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (orderHistory.isEmpty()) {
            textViewOrderDetails.setText(R.string.no_orders_placed);
        }

        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOrderPosition = position;
                displayOrderDetails(orderHistory.get(position));
            }
        });
    }

    /**
     * Sets up click listeners for buttons.
     */
    private void setupListeners() {
        buttonCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSelectedOrder();
            }
        });
    }

    /**
     * Displays detailed information about the selected order.
     * @param order The order to display.
     */
    private void displayOrderDetails(Order order) {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Order #%d\n\n", order.getOrderNumber()));
        details.append("Items:\n");

        for (MenuItem item : order.getItems()) {
            details.append("  • ").append(item.toString()).append("\n");
        }

        details.append(String.format("\nSubtotal: $%.2f", order.getSubtotal()));
        details.append(String.format("\nTax (6.625%%): $%.2f", order.getTax()));
        details.append(String.format("\nTotal: $%.2f", order.getTotal()));

        textViewOrderDetails.setText(details.toString());
    }

    /**
     * Cancels the selected order after confirmation.
     */
    private void cancelSelectedOrder() {
        if (selectedOrderPosition == -1) {
            Toast.makeText(this, R.string.toast_select_order, Toast.LENGTH_SHORT).show();
            return;
        }

        Order orderToCancel = orderHistory.get(selectedOrderPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_cancel_title);
        builder.setMessage(String.format("Cancel Order #%d?\nThis cannot be undone.",
                orderToCancel.getOrderNumber()));

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            OrderManager.getInstance().cancelOrder(orderToCancel);
            Toast.makeText(this, R.string.toast_order_cancelled, Toast.LENGTH_SHORT).show();

            // Refresh the list
            selectedOrderPosition = -1;
            setupListView();
            textViewOrderDetails.setText("");
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Refreshes the order history when activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}
