package com.zonebecreations.taxiappcustomer;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.zonebecreations.taxiappcustomer.databinding.ProductDetailsBinding;

public class ProductDetailFragment extends Fragment {

    ProductDetailsBinding binding;
//    ProductDetailsBinding

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProductDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

//        return   inflater.inflate(R.layout.product_details, container, false);;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("getArguments()" + getArguments());
        if (getArguments() != null) {
            Bundle arguments = getArguments();
//            ProductDetailFragmentArgs args = ProductDetailFragmentArgs.fromBundle(getArguments());
//
//            String productId = args.getProductId();
//            String productName = args.getProductName();
//            String description = args.getDescription();
//            String price = args.getPrice();
//            String image = args.getImage();

            String productId = arguments.getString("product_id");
            String productName = arguments.getString("product_name");
            String description = arguments.getString("description");
            String price = arguments.getString("price");
            Uri image = (Uri) arguments.get("image");
//
            binding.textView9.setText(productName);
            Glide.with(requireActivity()).load(image).into(binding.imageProduct);
            binding.textView12.setText(description);
            binding.textView14.setText(price);

            binding.materialButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductDetailFragmentDirections.ActionProductDetailFragment2ToCheckOutFragment actionProductDetailFragment2ToCheckOutFragment = ProductDetailFragmentDirections.actionProductDetailFragment2ToCheckOutFragment(productId);
                    Navigation.findNavController(view).navigate(actionProductDetailFragment2ToCheckOutFragment);
                }
            });

//            binding.textView2.setText(productModel.getProduct_description());
//            binding.textView3.setText("Rs." + String.valueOf(productModel.getPrice()));
////            Picasso.get().load("http://192.168.1.3:8080/images/" + productModel.getImage()).into(binding.imageProduct);
//
//            Glide.with(requireActivity())
//                    .load(Uri.parse("http://192.168.1.3:8080/images/" + productModel.getImage()))
//                    .into(binding.imageProduct);
//
//            binding.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ProductDetailFragmentDirections.ActionProductDetailFragmentToCheckOutFragment action =
//                            ProductDetailFragmentDirections.actionProductDetailFragmentToCheckOutFragment(productModel.getProduct_id());
//                    Navigation.findNavController(requireView()).navigate(action);
//                }
//            });
//
        }
    }
}