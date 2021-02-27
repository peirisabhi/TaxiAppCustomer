package com.zonebecreations.taxiappcustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zonebecreations.taxiappcustomer.animDialog.AnimDialog;
import com.zonebecreations.taxiappcustomer.databinding.FragmentCheckOutBinding;
import com.zonebecreations.taxiappcustomer.model.OrderModel;


public class CheckOutFragment extends Fragment {

    FragmentCheckOutBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AnimDialog responseAnimDialog;
    KProgressHUD hud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCheckOutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (responseAnimDialog == null) {
            responseAnimDialog = new AnimDialog(requireActivity());
        }


        hud = KProgressHUD.create(requireActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getArguments()!=null) {
                    System.out.println(getArguments().get("product_id"));
//


                    OrderModel orderModel = new OrderModel();
                    orderModel.setFname(binding.txtFname.getText().toString());
                    orderModel.setLname(binding.txtLname.getText().toString());
                    orderModel.setAddress(binding.txtAddress.getText().toString());
                    orderModel.setEmail(binding.txtEmail.getText().toString());
                    orderModel.setPhone(binding.txtMobileNumber.getText().toString());
                    orderModel.setNote(binding.txtNote.getText().toString());
                    orderModel.setProduct_master_id(Integer.parseInt((String) getArguments().get("product_id")));
                    orderModel.setCustomerDocId(((MainActivity)getActivity()).getCustomerDocumentId());
                    orderModel.setStatus("Pending");

                    hud.show();

                    db.collection("orders").add(orderModel).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hud.dismiss();


                            responseAnimDialog.createAnimatedSingleDialog()
                                    .setAnimation(R.raw.error)
                                    .setTitle("Error")
                                    .setContent("")
                                    .setButton1(
                                            "Ok", 0, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    responseAnimDialog.dismiss();
                                                }
                                            });
                            responseAnimDialog.show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            hud.dismiss();


                            responseAnimDialog.createAnimatedSingleDialog()
                                    .setAnimation(R.raw.success)
                                    .setTitle("Success")
                                    .setContent("")
                                    .setButton1(
                                            "Ok", 0, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    responseAnimDialog.dismiss();
                                                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                                                    startActivity(intent);
                                                    requireActivity().finish();
                                                }
                                            })
                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            System.out.println("dismissed");
                                        }
                                    });
                            responseAnimDialog.show();
                        }
                    });

                }
            }
        });

    }
}