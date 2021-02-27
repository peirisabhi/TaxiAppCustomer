package com.zonebecreations.taxiappcustomer.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.zonebecreations.taxiappcustomer.R;

public class ProductHolder extends RecyclerView.ViewHolder{

    public TextView name,price;
    public ImageView img;
    public CardView cardView;

    public ProductHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.label_product_name);
        price = itemView.findViewById(R.id.label_price);
        img = itemView.findViewById(R.id.image_product);
        cardView = (CardView) itemView;
    }
}
