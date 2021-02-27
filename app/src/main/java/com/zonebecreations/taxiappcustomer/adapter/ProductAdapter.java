package com.zonebecreations.taxiappcustomer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.zonebecreations.taxiappcustomer.R;
import com.zonebecreations.taxiappcustomer.model.Product;

import java.util.List;

public class ProductAdapter extends FirestoreRecyclerAdapter<Product, ProductAdapter.ProductHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */


    private List<Product> productList;

    private Context context;

    private Listener listener;

    public interface  Listener{
        void onClick(int position);
    }

    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Product> options, Context context) {
        super(options);
        this.productList = productList;
        this.context = context;
    }

    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductHolder productHolder, int i, @NonNull Product product) {
        productHolder.textViewTitle.setText(product.getName());
        productHolder.price.setText(product.getPrice());
        Glide.with(context).load(product.getImage()).into(productHolder.image);


    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product, parent, false);

        return new ProductHolder(v);

    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView price;
        ImageView image;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.label_product_name);
            price = itemView.findViewById(R.id.label_price);
            image = itemView.findViewById(R.id.image_product);
        }
    }
}
