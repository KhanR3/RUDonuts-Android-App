package com.example.project5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for RecyclerView displaying donut items with images and selection checkboxes.
 * Implements ViewHolder pattern for efficient view recycling.
 * @author Rahul Battula
 */
public class DonutAdapter extends RecyclerView.Adapter<DonutAdapter.DonutViewHolder> {

    private List<DonutActivity.DonutItem> donutList;
    private Context context;
    private OnItemClickListener listener;

    /**
     * Creates a new donut adapter.
     * @param donutList List of donut items to display.
     * @param context Application context for resource access.
     */
    public DonutAdapter(List<DonutActivity.DonutItem> donutList, Context context) {
        this.donutList = donutList;
        this.context = context;
    }

    /**
     * Creates new ViewHolder instances for RecyclerView items.
     * @param parent Parent ViewGroup to attach the new view to.
     * @param viewType Type of the new view.
     * @return DonutViewHolder instance containing the inflated view.
     */
    @NonNull
    @Override
    public DonutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donut_item, parent, false);
        return new DonutViewHolder(view);
    }

    /**
     * Binds data to ViewHolder at specified position.
     * @param holder ViewHolder to bind data to.
     * @param position Position of item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull DonutViewHolder holder, int position) {
        DonutActivity.DonutItem donut = donutList.get(position);

        holder.textViewDonutName.setText(donut.getName());
        holder.textViewDonutType.setText(donut.getType());
        holder.imageViewDonut.setImageResource(donut.getImageResource());
        holder.checkBoxDonutSelect.setChecked(donut.isSelected());

        holder.checkBoxDonutSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donut.setSelected(holder.checkBoxDonutSelect.isChecked());
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newState = !donut.isSelected();
                donut.setSelected(newState);
                holder.checkBoxDonutSelect.setChecked(newState);
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    /**
     * Gets the total number of items in the list.
     * @return Integer count of donut items.
     */
    @Override
    public int getItemCount() {
        return donutList.size();
    }

    /**
     * Gets list of currently selected donuts.
     * @return List of selected DonutItem objects.
     */
    public List<DonutActivity.DonutItem> getSelectedDonuts() {
        List<DonutActivity.DonutItem> selected = new ArrayList<>();
        for (DonutActivity.DonutItem donut : donutList) {
            if (donut.isSelected()) {
                selected.add(donut);
            }
        }
        return selected;
    }

    /**
     * Clears all selections in the donut list.
     */
    public void clearSelections() {
        for (DonutActivity.DonutItem donut : donutList) {
            donut.setSelected(false);
        }
        notifyDataSetChanged();
    }

    /**
     * Sets the click listener for item interactions.
     * @param listener OnItemClickListener to handle clicks.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        /**
         * Called when an item is clicked.
         * @param position Position of clicked item in list.
         */
        void onItemClick(int position);
    }

    /**
     * ViewHolder class for efficient view recycling in RecyclerView.
     * Holds references to views in the donut item layout.
     */
    public static class DonutViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewDonut;
        TextView textViewDonutName;
        TextView textViewDonutType;
        CheckBox checkBoxDonutSelect;

        /**
         * Creates a new ViewHolder with view references.
         * @param itemView Root view of the donut item layout.
         */
        public DonutViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewDonut = itemView.findViewById(R.id.imageViewDonut);
            textViewDonutName = itemView.findViewById(R.id.textViewDonutName);
            textViewDonutType = itemView.findViewById(R.id.textViewDonutType);
            checkBoxDonutSelect = itemView.findViewById(R.id.checkBoxDonutSelect);
        }
    }
}
