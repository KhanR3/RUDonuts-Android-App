package com.example.project5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project5.models.AddOns;
import com.example.project5.models.Bread;
import com.example.project5.models.Protein;
import com.example.project5.models.Sandwich;

import java.util.ArrayList;

/**
 * Activity for ordering sandwiches with bread, protein, and add-on selections.
 * Implements spinners for single-select options and checkboxes for add-ons.
 * @author Rahul Battula
 */
public class SandwichActivity extends AppCompatActivity {

    private static final int MAX_QUANTITY = 99;
    private static final int MIN_QUANTITY = 1;

    private Spinner spinnerBread;
    private Spinner spinnerProtein;
    private EditText editTextSandwichQuantity;
    private CheckBox checkBoxLettuce;
    private CheckBox checkBoxTomatoes;
    private CheckBox checkBoxOnions;
    private CheckBox checkBoxCheese;
    private TextView textViewSandwichSubtotal;
    private Button buttonAddSandwichToOrder;

    private int selectedBreadPosition;
    private int selectedProteinPosition;

    /**
     * Initializes the sandwich ordering activity and sets up UI components.
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupSpinners();
        setupListeners();
        calculateSubtotal();
    }

    /**
     * Initializes all UI components by finding their views.
     */
    private void initializeViews() {
        spinnerBread = findViewById(R.id.spinnerBread);
        spinnerProtein = findViewById(R.id.spinnerProtein);
        editTextSandwichQuantity = findViewById(R.id.editTextSandwichQuantity);
        checkBoxLettuce = findViewById(R.id.checkBoxLettuce);
        checkBoxTomatoes = findViewById(R.id.checkBoxTomatoes);
        checkBoxOnions = findViewById(R.id.checkBoxOnions);
        checkBoxCheese = findViewById(R.id.checkBoxCheese);
        textViewSandwichSubtotal = findViewById(R.id.textViewSandwichSubtotal);
        buttonAddSandwichToOrder = findViewById(R.id.buttonAddSandwichToOrder);

        selectedBreadPosition = 0;
        selectedProteinPosition = 0;
    }

    /**
     * Configures the spinners with bread and protein options.
     */
    private void setupSpinners() {
        // Setup bread spinner
        String[] breadTypes = {
                getString(R.string.sandwich_bread_bagel),
                getString(R.string.sandwich_bread_wheat),
                getString(R.string.sandwich_bread_sourdough)
        };

        ArrayAdapter<String> breadAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                breadTypes
        );
        breadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBread.setAdapter(breadAdapter);

        spinnerBread.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBreadPosition = position;
                calculateSubtotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Setup protein spinner
        String[] proteinTypes = {
                getString(R.string.sandwich_protein_beef),
                getString(R.string.sandwich_protein_chicken),
                getString(R.string.sandwich_protein_salmon)
        };

        ArrayAdapter<String> proteinAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                proteinTypes
        );
        proteinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProtein.setAdapter(proteinAdapter);

        spinnerProtein.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProteinPosition = position;
                calculateSubtotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Sets up listeners for checkboxes and buttons.
     */
    private void setupListeners() {
        CheckBox.OnCheckedChangeListener checkedChangeListener =
                (buttonView, isChecked) -> calculateSubtotal();

        checkBoxLettuce.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxTomatoes.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxOnions.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxCheese.setOnCheckedChangeListener(checkedChangeListener);

        buttonAddSandwichToOrder.setOnClickListener(v -> addSandwichToOrder());
    }

    /**
     * Calculates and displays the subtotal for current sandwich configuration.
     */
    private void calculateSubtotal() {
        int quantity = getQuantity();
        Bread bread = getBreadFromPosition(selectedBreadPosition);
        Protein protein = getProteinFromPosition(selectedProteinPosition);
        ArrayList<AddOns> addOns = getSelectedAddOns();

        Sandwich sandwich = new Sandwich(bread, protein, addOns, quantity);
        double subtotal = sandwich.price();

        textViewSandwichSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    /**
     * Gets Bread enum from spinner position.
     * @param position Position in spinner.
     * @return Bread enum value.
     */
    private Bread getBreadFromPosition(int position) {
        switch (position) {
            case 0:
                return Bread.BAGEL;
            case 1:
                return Bread.WHEAT;
            case 2:
                return Bread.SOURDOUGH;
            default:
                return Bread.BAGEL;
        }
    }

    /**
     * Gets Protein enum from spinner position.
     * @param position Position in spinner.
     * @return Protein enum value.
     */
    private Protein getProteinFromPosition(int position) {
        switch (position) {
            case 0:
                return Protein.BEEF;
            case 1:
                return Protein.CHICKEN;
            case 2:
                return Protein.SALMON;
            default:
                return Protein.BEEF;
        }
    }

    /**
     * Gets the list of selected add-ons.
     * @return ArrayList of selected AddOns enums.
     */
    private ArrayList<AddOns> getSelectedAddOns() {
        ArrayList<AddOns> addOns = new ArrayList<>();

        if (checkBoxLettuce.isChecked()) {
            addOns.add(AddOns.LETTUCE);
        }
        if (checkBoxTomatoes.isChecked()) {
            addOns.add(AddOns.TOMATOES);
        }
        if (checkBoxOnions.isChecked()) {
            addOns.add(AddOns.ONIONS);
        }
        if (checkBoxCheese.isChecked()) {
            addOns.add(AddOns.CHEESE);
        }

        return addOns;
    }

    /**
     * Gets the quantity from the input field.
     * @return Integer quantity value, defaults to 1 if invalid.
     */
    private int getQuantity() {
        String quantityText = editTextSandwichQuantity.getText().toString().trim();
        if (quantityText.isEmpty()) {
            return 1;
        }

        try {
            int qty = Integer.parseInt(quantityText);
            return (qty >= MIN_QUANTITY && qty <= MAX_QUANTITY) ? qty : 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    /**
     * Adds the configured sandwich to the order.
     */
    private void addSandwichToOrder() {
        int quantity = getQuantity();

        if (quantity < MIN_QUANTITY) {
            Toast.makeText(this, R.string.toast_invalid_quantity, Toast.LENGTH_SHORT).show();
            return;
        }

        showConfirmationDialog(quantity);
    }

    /**
     * Shows confirmation dialog before adding sandwich to order.
     * @param quantity Number of sandwiches to add.
     */
    private void showConfirmationDialog(int quantity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_confirm_title);

        Bread bread = getBreadFromPosition(selectedBreadPosition);
        Protein protein = getProteinFromPosition(selectedProteinPosition);
        ArrayList<AddOns> addOns = getSelectedAddOns();
        Sandwich sandwich = new Sandwich(bread, protein, addOns, quantity);

        String message = String.format("%s\n\n%s on %s\nAdd-ons: %s\nQuantity: %d\nPrice: $%.2f",
                getString(R.string.dialog_confirm_message),
                protein.toString(),
                bread.toString(),
                addOns.isEmpty() ? "None" : formatAddOns(addOns),
                quantity,
                sandwich.price());

        builder.setMessage(message);

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            OrderManager.getInstance().getCurrentOrder().addItem(sandwich);
            Toast.makeText(this, R.string.toast_item_added, Toast.LENGTH_SHORT).show();
            editTextSandwichQuantity.setText("");
            clearSelections();
            calculateSubtotal();
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Formats add-ons list for display.
     * @param addOns List of selected add-ons.
     * @return Formatted string of add-ons.
     */
    private String formatAddOns(ArrayList<AddOns> addOns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addOns.size(); i++) {
            sb.append(addOns.get(i).toString());
            if (i < addOns.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Clears all checkbox selections.
     */
    private void clearSelections() {
        checkBoxLettuce.setChecked(false);
        checkBoxTomatoes.setChecked(false);
        checkBoxOnions.setChecked(false);
        checkBoxCheese.setChecked(false);
    }
}
