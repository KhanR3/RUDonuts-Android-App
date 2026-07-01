package com.example.project5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main activity that serves as the landing screen for RU Cafe application.
 * Provides navigation to donut ordering, coffee ordering, and order summary screens.
 * @author Rahul Battula
 */
public class MainActivity extends AppCompatActivity {

    private Button buttonOrderDonuts;
    private Button buttonOrderCoffee;
    private Button buttonOrderSandwiches;
    private Button buttonViewOrders;
    private Button buttonOrderHistory;

    /**
     * Initializes the main activity and sets up button click listeners.
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupListeners();
    }

    /**
     * Initializes all UI components by finding their views.
     */
    private void initializeViews() {
        buttonOrderDonuts = findViewById(R.id.buttonOrderDonuts);
        buttonOrderCoffee = findViewById(R.id.buttonOrderCoffee);
        buttonOrderSandwiches = findViewById(R.id.buttonOrderSandwiches);
        buttonViewOrders = findViewById(R.id.buttonViewOrders);
        buttonOrderHistory = findViewById(R.id.buttonOrderHistory);
    }

    /**
     * Sets up click listeners for navigation buttons.
     */
    private void setupListeners() {
        buttonOrderDonuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDonutActivity();
            }
        });

        buttonOrderCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCoffeeActivity();
            }
        });

        buttonOrderSandwiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSandwichActivity();
            }
        });

        buttonViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderSummaryActivity();
            }
        });

        buttonOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrderHistoryActivity();
            }
        });
    }

    /**
     * Opens the donut ordering activity.
     */
    private void openDonutActivity() {
        Intent intent = new Intent(this, DonutActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the coffee ordering activity.
     */
    private void openCoffeeActivity() {
        Intent intent = new Intent(this, CoffeeActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the sandwich ordering activity.
     */
    private void openSandwichActivity() {
        Intent intent = new Intent(this, SandwichActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the order summary activity.
     */
    private void openOrderSummaryActivity() {
        Intent intent = new Intent(this, OrderSummaryActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the order history activity to view all placed orders.
     */
    private void openOrderHistoryActivity() {
        Intent intent = new Intent(this, OrderHistoryActivity.class);
        startActivity(intent);
    }
}