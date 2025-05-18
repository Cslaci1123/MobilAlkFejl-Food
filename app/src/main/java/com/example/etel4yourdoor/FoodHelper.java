package com.example.etel4yourdoor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodHelper extends RecyclerView.Adapter<FoodHelper.ViewHolder> {
    private List<FoodItem> foodList;

    public FoodHelper(List<FoodItem> foodList) {
        this.foodList = foodList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, priceView;
        Button addButton;

        public ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.foodName);
            priceView = view.findViewById(R.id.foodPrice);
            addButton = view.findViewById(R.id.addToCartButton);
        }
    }

    @NonNull
    @Override
    public FoodHelper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FoodHelper.ViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.nameView.setText(item.getName());
        holder.priceView.setText(item.getPrice() + " Ft");

        holder.addButton.setOnClickListener(v -> {
            KosarHelper.getInstance().addToCart(item);
            Toast.makeText(v.getContext(), item.getName() + " a kosárba került", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}