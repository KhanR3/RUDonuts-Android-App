package com.example.project5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
 * Activity for displaying order summary with ListView of items.
 * Shows subtotal, tax, and total with options to modify order.
 * @author Rahul Battula
 */
public class OrderSummaryActivity extends AppCompatActivity {

    private static final double TAX_RATE = 0.06625;

    private ListView listViewOrderItems;
    private TextView textViewSubtotalValue;
    private TextView textViewTaxValue;
    private TextView textViewTotalValue;
    private Button buttonRemoveItem;
    private Button buttonClearAll;
    private Button buttonPlaceOrder;

    private OrderItemAdapter orderAdapter;
    private Order currentOrder;
    private List<Boolean> selectedItems;

    /**
     * Initializes the order summary activity and sets up UI components.
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupListView();
        setupListeners();
        updateOrderTotals();
    }

    /**
     * Initializes all UI components by finding their views.
     */
    private void initializeViews() {
        listViewOrderItems = findViewById(R.id.listViewOrderItems);
        textViewSubtotalValue = findViewById(R.id.textViewSubtotalValue);
        textViewTaxValue = findViewById(R.id.textViewTaxValue);
        textViewTotalValue = findViewById(R.id.textViewTotalValue);
        buttonRemoveItem = findViewById(R.id.buttonRemoveItem);
        buttonClearAll = findViewById(R.id.buttonClearAll);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
    }

    /**
     * Configures the ListView with order items.
     */
    private void setupListView() {
        currentOrder = OrderManager.getInstance().getCurrentOrder();
        selectedItems = new ArrayList<>();
        for (int i = 0; i < currentOrder.getItems().size(); i++) {
            selectedItems.add(false);
        }

        orderAdapter = new OrderItemAdapter(this, currentOrder.getItems(), selectedItems);
        listViewOrderItems.setAdapter(orderAdapter);
    }



    /**
     * Sets up click listeners for buttons.
     */
    private void setupListeners() {
        buttonRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedItem();
            }
        });

        buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllItems();
            }
        });

        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    /**
     * Removes the selected items from the order.
     */
    private void removeSelectedItem() {
        List<Integer> itemsToRemove = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i)) {
                itemsToRemove.add(i);
            }
        }

        if (itemsToRemove.isEmpty()) {
            Toast.makeText(this, R.string.toast_select_item, Toast.LENGTH_SHORT).show();
            return;
        }

        String message = itemsToRemove.size() == 1 ? 
                getString(R.string.dialog_remove_message) : 
                getString(R.string.dialog_remove_multiple_message, itemsToRemove.size());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_remove_title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            // Remove items in reverse order to maintain correct indices
            for (int i = itemsToRemove.size() - 1; i >= 0; i--) {
                int position = itemsToRemove.get(i);
                currentOrder.removeItem(currentOrder.getItems().get(position));
            }
            
            // Update selection list
            selectedItems.clear();
            for (int i = 0; i < currentOrder.getItems().size(); i++) {
                selectedItems.add(false);
            }
            
            orderAdapter.notifyDataSetChanged();
            updateOrderTotals();
            Toast.makeText(this, R.string.toast_item_removed, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Clears all items from the order.
     */
    private void clearAllItems() {
        if (currentOrder.getItems().isEmpty()) {
            Toast.makeText(this, R.string.toast_order_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_clear_title);
        builder.setMessage(R.string.dialog_clear_message);

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            OrderManager.getInstance().clearCurrentOrder();
            selectedItems.clear();
            orderAdapter.notifyDataSetChanged();
            updateOrderTotals();
            Toast.makeText(this, R.string.toast_item_removed, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Places the current order and shows confirmation.
     */
    private void placeOrder() {
        if (currentOrder.getItems().isEmpty()) {
            Toast.makeText(this, R.string.toast_order_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_confirm_title);

        double subtotal = currentOrder.getSubtotal();
        double tax = currentOrder.getTax();
        double total = currentOrder.getTotal();

        String message = String.format("%s\n\nSubtotal: $%.2f\nTax: $%.2f\nTotal: $%.2f",
                getString(R.string.dialog_confirm_message), subtotal, tax, total);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            Toast.makeText(this, R.string.toast_order_placed, Toast.LENGTH_LONG).show();
            OrderManager.getInstance().placeOrder();
            selectedItems.clear();
            orderAdapter.notifyDataSetChanged();
            updateOrderTotals();
            finish();
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Updates the displayed order totals.
     */
    private void updateOrderTotals() {
        double subtotal = currentOrder.getSubtotal();
        double tax = currentOrder.getTax();
        double total = currentOrder.getTotal();

        textViewSubtotalValue.setText(String.format("$%.2f", subtotal));
        textViewTaxValue.setText(String.format("$%.2f", tax));
        textViewTotalValue.setText(String.format("$%.2f", total));
    }

    /**
     * Refreshes the order display when activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        currentOrder = OrderManager.getInstance().getCurrentOrder();
        selectedItems.clear();
        for (int i = 0; i < currentOrder.getItems().size(); i++) {
            selectedItems.add(false);
        }
        orderAdapter.notifyDataSetChanged();
        updateOrderTotals();
    }

    /**
     * Custom adapter for order items with checkboxes.
     */
    private class OrderItemAdapter extends BaseAdapter {
        private Context context;
        private List<MenuItem> items;
        private List<Boolean> selections;

        /**
         * Creates a new OrderItemAdapter.
         * @param context Application context.
         * @param items List of menu items in the order.
         * @param selections List tracking checkbox selections.
         */
        public OrderItemAdapter(Context context, List<MenuItem> items, List<Boolean> selections) {
            this.context = context;
            this.items = items;
            this.selections = selections;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
                holder = new ViewHolder();
                holder.checkBox = convertView.findViewById(R.id.checkBoxSelectItem);
                holder.textView = convertView.findViewById(R.id.textViewOrderItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MenuItem item = items.get(position);
            holder.textView.setText(item.toString());
            holder.checkBox.setChecked(selections.get(position));

            holder.checkBox.setOnClickListener(v -> {
                selections.set(position, holder.checkBox.isChecked());
            });

            return convertView;
        }

        /**
         * ViewHolder pattern for efficient view recycling.
         */
        private class ViewHolder {
            CheckBox checkBox;
            TextView textView;
        }
    }
}
