package com.example.project5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.models.Donut;
import com.example.project5.models.DonutFlavor;
import com.example.project5.models.DonutType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity for ordering donuts with a RecyclerView displaying available options.
 * Implements spinner for donut type selection and quantity input.
 * @author Rahul Battula
 */
public class DonutActivity extends AppCompatActivity {

    private static final int MAX_QUANTITY = 99;
    private static final int MIN_QUANTITY = 1;

    private Spinner spinnerDonutType;
    private RecyclerView recyclerViewDonuts;
    private EditText editTextDonutQuantity;
    private TextView textViewDonutSubtotal;
    private Button buttonAddDonutToOrder;
    private ImageView imageViewDonutType;

    private DonutAdapter donutAdapter;
    private List<DonutItem> currentDonutList;

    /**
     * Initializes the donut ordering activity and sets up UI components.
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        setupSpinner();
        setupRecyclerView();
        setupListeners();
    }

    /**
     * Initializes all UI components by finding their views.
     */
    private void initializeViews() {
        spinnerDonutType = findViewById(R.id.spinnerDonutType);
        recyclerViewDonuts = findViewById(R.id.recyclerViewDonuts);
        editTextDonutQuantity = findViewById(R.id.editTextDonutQuantity);
        textViewDonutSubtotal = findViewById(R.id.textViewDonutSubtotal);
        buttonAddDonutToOrder = findViewById(R.id.buttonAddDonutToOrder);
        imageViewDonutType = findViewById(R.id.imageViewDonutType);
    }

    /**
     * Configures the spinner with donut type options.
     */
    private void setupSpinner() {
        String[] donutTypes = {
                getString(R.string.donut_yeast),
                getString(R.string.donut_cake),
                getString(R.string.donut_hole),
                getString(R.string.donut_seasonal)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                donutTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDonutType.setAdapter(adapter);

        spinnerDonutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDonutList(position);
                updateDonutTypeImage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Configures the RecyclerView with layout manager and adapter.
     */
    private void setupRecyclerView() {
        currentDonutList = new ArrayList<>();
        donutAdapter = new DonutAdapter(currentDonutList, this);
        recyclerViewDonuts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDonuts.setAdapter(donutAdapter);

        updateDonutList(0);
    }

    /**
     * Sets up click listeners for buttons and RecyclerView items.
     */
    private void setupListeners() {
        buttonAddDonutToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDonutsToOrder();
            }
        });

        donutAdapter.setOnItemClickListener(new DonutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                calculateSubtotal();
            }
        });
    }

    /**
     * Updates the donut list based on selected type.
     * @param typePosition Position of selected donut type in spinner.
     */
    private void updateDonutList(int typePosition) {
        currentDonutList.clear();

        DonutType selectedType = getDonutTypeFromPosition(typePosition);
        List<DonutFlavor> flavors = getFlavorsForType(selectedType);
        String typeName = getDonutTypeName(typePosition);
        
        for (DonutFlavor flavor : flavors) {
            String flavorName = formatFlavorName(flavor);
            int imageResource = getFlavorImageResource(flavor);
            currentDonutList.add(new DonutItem(flavorName, typeName, 
                    imageResource, flavor));
        }

        donutAdapter.notifyDataSetChanged();
        calculateSubtotal();
    }

    /**
     * Gets DonutType enum from spinner position.
     * @param position Position in spinner.
     * @return DonutType enum value.
     */
    private DonutType getDonutTypeFromPosition(int position) {
        switch (position) {
            case 0:
                return DonutType.YEAST;
            case 1:
                return DonutType.CAKE;
            case 2:
                return DonutType.HOLE;
            case 3:
                return DonutType.SEASONAL;
            default:
                return DonutType.YEAST;
        }
    }

    /**
     * Gets the list of flavors for a given donut type.
     * @param type The donut type.
     * @return List of DonutFlavor enums for that type.
     */
    private List<DonutFlavor> getFlavorsForType(DonutType type) {
        List<DonutFlavor> flavors = new ArrayList<>();
        switch (type) {
            case YEAST:
                flavors.add(DonutFlavor.GLAZED);
                flavors.add(DonutFlavor.CHOCOLATE);
                flavors.add(DonutFlavor.STRAWBERRY);
                flavors.add(DonutFlavor.BOSTON_CREAM);
                flavors.add(DonutFlavor.JELLY);
                flavors.add(DonutFlavor.CINNAMON_SUGAR);
                break;
            case CAKE:
                flavors.add(DonutFlavor.BLUEBERRY);
                flavors.add(DonutFlavor.OLD_FASHIONED);
                flavors.add(DonutFlavor.RED_VELVET);
                break;
            case HOLE:
                flavors.add(DonutFlavor.GLAZED_HOLE);
                flavors.add(DonutFlavor.CHOCOLATE_HOLE);
                flavors.add(DonutFlavor.POWDERED_HOLE);
                break;
            case SEASONAL:
                flavors.add(DonutFlavor.SPOOKY);
                flavors.add(DonutFlavor.PUMPKIN_SPICE);
                break;
        }
        return flavors;
    }

    /**
     * Formats a DonutFlavor enum to a display string.
     * @param flavor The DonutFlavor enum.
     * @return Formatted string for display.
     */
    private String formatFlavorName(DonutFlavor flavor) {
        String name = flavor.name().replace('_', ' ');
        String[] words = name.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }

    /**
     * Gets the donut type name based on spinner position.
     * @param position Position in spinner.
     * @return String representing donut type name.
     */
    private String getDonutTypeName(int position) {
        switch (position) {
            case 0:
                return getString(R.string.donut_yeast);
            case 1:
                return getString(R.string.donut_cake);
            case 2:
                return getString(R.string.donut_hole);
            case 3:
                return getString(R.string.donut_seasonal);
            default:
                return getString(R.string.donut_yeast);
        }
    }



    /**
     * Gets the image resource ID for a donut type based on spinner position.
     * @param position Position in the spinner (0=Yeast, 1=Cake, 2=Hole, 3=Seasonal).
     * @return Drawable resource ID for the donut type image.
     */
    private int getTypeImageResource(int position) {
        switch (position) {
            case 0: // Yeast
                return R.drawable.yeast;
            case 1: // Cake
                return R.drawable.cake;
            case 2: // Hole
                return R.drawable.holes;
            case 3: // Seasonal
                return R.drawable.seasonal;
            default:
                return R.drawable.yeast;
        }
    }

    /**
     * Gets the image resource ID for a specific donut flavor.
     * @param flavor DonutFlavor enum value.
     * @return Drawable resource ID for the flavor image.
     */
    private int getFlavorImageResource(DonutFlavor flavor) {
        switch (flavor) {
            case GLAZED:
                return R.drawable.glazed;
            case CHOCOLATE:
                return R.drawable.chocolate;
            case STRAWBERRY:
                return R.drawable.strawberry;
            case BOSTON_CREAM:
                return R.drawable.boston;
            case JELLY:
                return R.drawable.jelly;
            case CINNAMON_SUGAR:
                return R.drawable.cinnamon;
            case BLUEBERRY:
                return R.drawable.blueberry;
            case OLD_FASHIONED:
                return R.drawable.oldfashioned;
            case RED_VELVET:
                return R.drawable.redvelvet;
            case GLAZED_HOLE:
                return R.drawable.glazedhole;
            case CHOCOLATE_HOLE:
                return R.drawable.chocolateholes;
            case POWDERED_HOLE:
                return R.drawable.powderedholes;
            case SPOOKY:
                return R.drawable.spooky;
            case PUMPKIN_SPICE:
                return R.drawable.pumpkinspice;
            default:
                return R.drawable.yeast;
        }
    }

    /**
     * Updates the donut type image based on the selected spinner position.
     * @param position Position in the spinner (0=Yeast, 1=Cake, 2=Hole, 3=Seasonal).
     */
    private void updateDonutTypeImage(int position) {
        imageViewDonutType.setImageResource(getTypeImageResource(position));
    }

    /**
     * Calculates and displays the subtotal for selected donuts.
     */
    private void calculateSubtotal() {
        List<DonutItem> selectedDonuts = donutAdapter.getSelectedDonuts();
        int quantity = getQuantity();

        if (selectedDonuts.isEmpty() || quantity == 0) {
            textViewDonutSubtotal.setText(getString(R.string.donut_subtotal));
            return;
        }

        double subtotal = 0.0;
        DonutType currentType = getDonutTypeFromPosition(spinnerDonutType.getSelectedItemPosition());
        
        for (DonutItem item : selectedDonuts) {
            Donut donut = new Donut(currentType, item.getFlavor(), quantity);
            subtotal += donut.price();
        }

        textViewDonutSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    /**
     * Gets the quantity from the input field.
     * @return Integer quantity value, defaults to 1 if invalid.
     */
    private int getQuantity() {
        String quantityText = editTextDonutQuantity.getText().toString().trim();
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
     * Adds selected donuts to the order and shows confirmation.
     */
    private void addDonutsToOrder() {
        List<DonutItem> selectedDonuts = donutAdapter.getSelectedDonuts();

        if (selectedDonuts.isEmpty()) {
            Toast.makeText(this, R.string.toast_select_item, Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = getQuantity();
        if (quantity < MIN_QUANTITY) {
            Toast.makeText(this, R.string.toast_invalid_quantity, Toast.LENGTH_SHORT).show();
            return;
        }

        showConfirmationDialog(selectedDonuts, quantity);
    }

    /**
     * Displays a confirmation dialog for adding donuts to order.
     * @param selectedDonuts List of selected donut items.
     * @param quantity Number of each donut to add.
     */
    private void showConfirmationDialog(List<DonutItem> selectedDonuts, int quantity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_confirm_title);
        builder.setMessage(getString(R.string.dialog_confirm_message) + "\n" +
                selectedDonuts.size() + " donut types, " + quantity + " each");

        builder.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            DonutType currentType = getDonutTypeFromPosition(
                    spinnerDonutType.getSelectedItemPosition());
            
            for (DonutItem item : selectedDonuts) {
                Donut donut = new Donut(currentType, item.getFlavor(), quantity);
                OrderManager.getInstance().getCurrentOrder().addItem(donut);
            }
            
            Toast.makeText(this, R.string.toast_item_added, Toast.LENGTH_SHORT).show();
            donutAdapter.clearSelections();
            editTextDonutQuantity.setText("");
            calculateSubtotal();
        });

        builder.setNegativeButton(R.string.dialog_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Inner class representing a donut item for display in RecyclerView.
     */
    public static class DonutItem {
        private String name;
        private String type;
        private int imageResource;
        private boolean selected;
        private DonutFlavor flavor;

        /**
         * Creates a new donut item.
         * @param name Flavor name of the donut.
         * @param type Type of donut (Yeast, Cake, Hole).
         * @param imageResource Drawable resource ID for donut image.
         * @param flavor DonutFlavor enum value.
         */
        public DonutItem(String name, String type, int imageResource, DonutFlavor flavor) {
            this.name = name;
            this.type = type;
            this.imageResource = imageResource;
            this.flavor = flavor;
            this.selected = false;
        }

        /**
         * Gets the donut name.
         * @return String donut flavor name.
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the donut type.
         * @return String donut type.
         */
        public String getType() {
            return type;
        }

        /**
         * Gets the image resource ID.
         * @return Integer resource ID.
         */
        public int getImageResource() {
            return imageResource;
        }

        /**
         * Checks if donut is selected.
         * @return Boolean selection state.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Sets the selection state.
         * @param selected Boolean selection state.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Gets the donut flavor enum.
         * @return DonutFlavor enum value.
         */
        public DonutFlavor getFlavor() {
            return flavor;
        }
    }
}
