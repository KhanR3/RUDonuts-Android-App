package com.example.project5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project5.models.AddIns;
import com.example.project5.models.Coffee;
import com.example.project5.models.CupSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for ordering coffee with size selection and add-in options.
 * Implements spinner, checkboxes, and toast notifications.
 * @author Rahul Battula
 */
public class CoffeeActivity extends AppCompatActivity {

    private static final int MAX_QUANTITY = 99;
    private static final int MIN_QUANTITY = 1;

    private Spinner spinnerCoffeeSize;
    private EditText editTextCoffeeQuantity;
    private CheckBox checkBoxCream;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSyrup;
    private CheckBox checkBoxCaramel;
    private CheckBox checkBoxWhippedCream;
    private TextView textViewCoffeeSubtotal;
    private Button buttonAddCoffeeToOrder;

    private int selectedSizePosition;

    /**
     * Initializes the coffee ordering activity and sets up UI components.
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupSpinner();
        setupListeners();
        calculateSubtotal();
    }

    /**
     * Initializes all UI components by finding their views.
     */
    private void initializeViews() {
        spinnerCoffeeSize = findViewById(R.id.spinnerCoffeeSize);
        editTextCoffeeQuantity = findViewById(R.id.editTextCoffeeQuantity);
        checkBoxCream = findViewById(R.id.checkBoxCream);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxSyrup = findViewById(R.id.checkBoxSyrup);
        checkBoxCaramel = findViewById(R.id.checkBoxCaramel);
        checkBoxWhippedCream = findViewById(R.id.checkBoxWhippedCream);
        textViewCoffeeSubtotal = findViewById(R.id.textViewCoffeeSubtotal);
        buttonAddCoffeeToOrder = findViewById(R.id.buttonAddCoffeeToOrder);

        selectedSizePosition = 0;
    }

    /**
     * Configures the spinner with coffee size options.
     */
    private void setupSpinner() {
        String[] coffeeSizes = {
                getString(R.string.coffee_size_short),
                getString(R.string.coffee_size_tall),
                getString(R.string.coffee_size_grande),
                getString(R.string.coffee_size_venti)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                coffeeSizes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoffeeSize.setAdapter(adapter);

        spinnerCoffeeSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSizePosition = position;
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
        CompoundButton.OnCheckedChangeListener checkedChangeListener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        calculateSubtotal();
                    }
                };

        checkBoxCream.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxMilk.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxSyrup.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxCaramel.setOnCheckedChangeListener(checkedChangeListener);
        checkBoxWhippedCream.setOnCheckedChangeListener(checkedChangeListener);

        buttonAddCoffeeToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoffeeToOrder();
            }
        });
    }

    /**
     * Calculates and displays the subtotal for current coffee configuration.
     */
    private void calculateSubtotal() {
        int quantity = getQuantity();
        CupSize size = getCupSizeFromPosition(selectedSizePosition);
        ArrayList<AddIns> addIns = getSelectedAddins();

        Coffee coffee = new Coffee(size, addIns, quantity);
        double subtotal = coffee.price();

        textViewCoffeeSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    /**
     * Gets CupSize enum from spinner position.
     * @param position Position in spinner.
     * @return CupSize enum value.
     */
    private CupSize getCupSizeFromPosition(int position) {
        switch (position) {
            case 0:
                return CupSize.SHORT;
            case 1:
                return CupSize.TALL;
            case 2:
                return CupSize.GRANDE;
            case 3:
                return CupSize.VENTI;
            default:
                return CupSize.SHORT;
        }
    }

    /**
     * Gets the quantity from the input field.
     * @return Integer quantity value, defaults to 1 if invalid.
     */
    private int getQuantity() {
        String quantityText = editTextCoffeeQuantity.getText().toString().trim();
        if (quantityText.isEmpty()) {
            return MIN_QUANTITY;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            return Math.max(MIN_QUANTITY, Math.min(quantity, MAX_QUANTITY));
        } catch (NumberFormatException e) {
            return MIN_QUANTITY;
        }
    }

    /**
     * Gets ArrayList of selected add-ins as enum values.
     * @return ArrayList of AddIns enum values.
     */
    private ArrayList<AddIns> getSelectedAddins() {
        ArrayList<AddIns> addins = new ArrayList<>();
        if (checkBoxWhippedCream.isChecked()) {
            addins.add(AddIns.WHIPPED_CREAM);
        }
        if (checkBoxMilk.isChecked()) {
            addins.add(AddIns.MILK_2PCT);
        }
        if (checkBoxSyrup.isChecked()) {
            addins.add(AddIns.VANILLA);
        }
        if (checkBoxCaramel.isChecked()) {
            addins.add(AddIns.CARAMEL);
        }
        if (checkBoxCream.isChecked()) {
            addins.add(AddIns.MOCHA);
        }
        return addins;
    }

    /**
     * Gets list of selected add-in names for display.
     * @return List of strings representing selected add-ins.
     */
    private List<String> getSelectedAddinNames() {
        List<String> addinNames = new ArrayList<>();
        if (checkBoxCream.isChecked()) {
            addinNames.add(getString(R.string.coffee_cream));
        }
        if (checkBoxMilk.isChecked()) {
            addinNames.add(getString(R.string.coffee_milk));
        }
        if (checkBoxSyrup.isChecked()) {
            addinNames.add(getString(R.string.coffee_syrup));
        }
        if (checkBoxCaramel.isChecked()) {
            addinNames.add(getString(R.string.coffee_caramel));
        }
        if (checkBoxWhippedCream.isChecked()) {
            addinNames.add(getString(R.string.coffee_whipped_cream));
        }
        return addinNames;
    }

    /**
     * Adds coffee to the order and shows confirmation.
     */
    private void addCoffeeToOrder() {
        int quantity = getQuantity();
        if (quantity < MIN_QUANTITY) {
            Toast.makeText(this, R.string.toast_invalid_quantity, Toast.LENGTH_SHORT).show();
            return;
        }

        String size = spinnerCoffeeSize.getSelectedItem().toString();
        List<String> addinNames = getSelectedAddinNames();

        showConfirmationDialog(size, addinNames, quantity);
    }

    /**
     * Displays a confirmation dialog for adding coffee to order.
     * @param size Selected coffee size.
     * @param addins List of selected add-ins.
     * @param quantity Number of coffees to add.
     */
    private void showConfirmationDialog(String size, List<String> addins, int quantity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_confirm_title);

        StringBuilder message = new StringBuilder();
        message.append(getString(R.string.dialog_confirm_message)).append("\n\n");
        message.append("Size: ").append(size).append("\n");
        message.append("Quantity: ").append(quantity).append("\n");
        if (!addins.isEmpty()) {
            message.append("Add-ins: ").append(String.join(", ", addins));
        }

        builder.setMessage(message.toString());

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            CupSize cupSize = getCupSizeFromPosition(selectedSizePosition);
            ArrayList<AddIns> coffeeAddins = getSelectedAddins();
            
            Coffee coffee = new Coffee(cupSize, coffeeAddins, quantity);
            OrderManager.getInstance().getCurrentOrder().addItem(coffee);
            
            Toast.makeText(this, R.string.toast_item_added, Toast.LENGTH_SHORT).show();
            resetForm();
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Resets the form to default values.
     */
    private void resetForm() {
        spinnerCoffeeSize.setSelection(0);
        editTextCoffeeQuantity.setText("");
        checkBoxCream.setChecked(false);
        checkBoxMilk.setChecked(false);
        checkBoxSyrup.setChecked(false);
        checkBoxCaramel.setChecked(false);
        checkBoxWhippedCream.setChecked(false);
        calculateSubtotal();
    }
}
