package com.zonebecreations.taxiappcustomer.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zonebecreations.taxiappcustomer.R;
import com.zonebecreations.taxiappcustomer.adapter.ProductAdapter;
import com.zonebecreations.taxiappcustomer.databinding.FragmentHomeBinding;
import com.zonebecreations.taxiappcustomer.holder.ProductHolder;
import com.zonebecreations.taxiappcustomer.model.Product;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    FragmentHomeBinding fragmentHomeBinding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;

    FirestoreRecyclerAdapter fsFirestoreRecyclerAdapter;

    private HomeViewModel homeViewModel;

    RecyclerView productList;
    private FirestoreRecyclerAdapter<Product, ProductHolder> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storageRef = FirebaseStorage.getInstance().getReference();

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                        } else {
                            System.out.println("Error getting documents." + task.getException());
                        }
                    }
                });

//

        productList = fragmentHomeBinding.productRecycler;
        productList.setLayoutManager(new LinearLayoutManager(requireActivity()));


        Query loadJobs = db.collection("products");

        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.Builder<Product>().setQuery(loadJobs, Product.class).build();

        adapter = new FirestoreRecyclerAdapter<Product, ProductHolder>(recyclerOptions) {


            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product, parent, false);

                return new ProductHolder(view);
            }
            Uri uri1 = null;

            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Product model) {
                holder.name.setText(model.getName());
                holder.price.setText(model.getPrice());



                storageRef.child("productImages/" + model.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println(uri);
                        uri1 = uri;
                        Glide.with(requireActivity()).load(uri).into(holder.img);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        FragmentHome
                        NavDirections navDirections = HomeFragmentDirections.actionNavigationHomeToProductDetailFragment2(String.valueOf(model.getId()), model.getName(), model.getDescription(), model.getPrice(), uri1);
                        Navigation.findNavController(view).navigate(navDirections);
                    }
                });

            }
        };

        productList.setAdapter(adapter);
    }


//    private void setUpRecyclerView() {
//        Query query = db.collection("products");
//        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
//                .setQuery(query, Product.class)
//                .build();
//
//        System.out.println(options);
//
//        fsFirestoreRecyclerAdapter = new ProductAdapter(options, requireActivity());
//        RecyclerView recyclerView = getView().findViewById(R.id.product_recycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
//        recyclerView.setAdapter(fsFirestoreRecyclerAdapter);
//    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        fsFirestoreRecyclerAdapter.startListening();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        fsFirestoreRecyclerAdapter.stopListening();
//    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}