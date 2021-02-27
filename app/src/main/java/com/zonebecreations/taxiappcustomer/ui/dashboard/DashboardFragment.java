package com.zonebecreations.taxiappcustomer.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zonebecreations.taxiappcustomer.MainActivity;
import com.zonebecreations.taxiappcustomer.R;
import com.zonebecreations.taxiappcustomer.animDialog.AnimDialog;
import com.zonebecreations.taxiappcustomer.databinding.FragmentDashboardBinding;
import com.zonebecreations.taxiappcustomer.holder.ProductHolder;
import com.zonebecreations.taxiappcustomer.holder.TicketHolder;
import com.zonebecreations.taxiappcustomer.model.Product;
import com.zonebecreations.taxiappcustomer.model.Ticket;
import com.zonebecreations.taxiappcustomer.ui.home.HomeFragmentDirections;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    FragmentDashboardBinding fragmentDashboardBinding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AnimDialog responseAnimDialog;
    KProgressHUD hud;

    RecyclerView ticketList;

    private FirestoreRecyclerAdapter<Ticket, TicketHolder> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);

        return fragmentDashboardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadTickets();

        if (responseAnimDialog == null) {
            responseAnimDialog = new AnimDialog(requireActivity());
        }


        hud = KProgressHUD.create(requireActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        fragmentDashboardBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Ticket ticket = new Ticket();
                ticket.setTitle(fragmentDashboardBinding.txtFname.getText().toString());
                ticket.setBody(fragmentDashboardBinding.txtAddress.getText().toString());
                ticket.setCategory(fragmentDashboardBinding.gender.getSelectedItem().toString());
                ticket.setCustomerDocId(((MainActivity)getActivity()).getCustomerDocumentId());

                db.collection("tickets").add(ticket).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

                                                fragmentDashboardBinding.txtFname.setText("");
                                                fragmentDashboardBinding.txtAddress.setText("");
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
                }).addOnFailureListener(new OnFailureListener() {
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
                });

            }
        });

    }



    public void loadTickets(){
        ticketList = fragmentDashboardBinding.productRecycler;
        ticketList.setLayoutManager(new LinearLayoutManager(requireActivity()));

        Query loadJobs = db.collection("tickets");

        FirestoreRecyclerOptions recyclerOptions = new FirestoreRecyclerOptions.Builder<Ticket>().setQuery(loadJobs, Ticket.class).build();

        adapter = new FirestoreRecyclerAdapter<Ticket, TicketHolder>(recyclerOptions) {


            @NonNull
            @Override
            public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

                return new TicketHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TicketHolder ticketHolder, int i, @NonNull Ticket ticket) {
                ticketHolder.name.setText(ticket.getTitle());
            }


        };

        ticketList.setAdapter(adapter);
    }


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