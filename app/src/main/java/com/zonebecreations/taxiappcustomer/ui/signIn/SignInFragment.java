package com.zonebecreations.taxiappcustomer.ui.signIn;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.zonebecreations.taxiappcustomer.AuthenticateActivity;
import com.zonebecreations.taxiappcustomer.MainActivity;
import com.zonebecreations.taxiappcustomer.R;
import com.zonebecreations.taxiappcustomer.databinding.SignInFragmentBinding;
import com.zonebecreations.taxiappcustomer.model.Customer;
import com.zonebecreations.taxiappcustomer.ui.signUp.SignUpFragmentDirections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;

public class SignInFragment extends Fragment {

    private SignInViewModel mViewModel;
    private SignInFragmentBinding signInFragmentBinding;
    private FirebaseFirestore db;

    private static final int RC_SIGN_IN = 123;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        signInFragmentBinding = SignInFragmentBinding.inflate(inflater, container, false);
        return signInFragmentBinding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        signInFragmentBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
                Navigation.findNavController(view).navigate(navDirections);
            }
        });

        signInFragmentBinding.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        signInFragmentBinding.authGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSignInIntent();
            }
        });

    }



    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
                Toast.makeText(requireActivity(), "Success", LENGTH_LONG).show();

                ((AuthenticateActivity)getActivity()).setEmail(user.getEmail());
                ((AuthenticateActivity)getActivity()).setName(user.getDisplayName());



                db.collection("customers").whereEqualTo("email", user.getEmail()).get().
                        addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<Customer> customers = queryDocumentSnapshots.toObjects(Customer.class);

                                System.out.println(customers.size());
                                try {
                                    if(customers.size() != 0){

                                        if(customers.get(0).isActiveStatus()) {
                                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                                            intent.putExtra("name", user.getDisplayName() + "");
                                            intent.putExtra("email", user.getEmail() + "");
                                            intent.putExtra("mobile", customers.get(0).getTp());
                                            intent.putExtra("customerDocId", documentSnapshot.getId());

                                            startActivity(intent);
                                            getActivity().finish();
                                        }else{
                                            Toast.makeText(requireActivity(), "Your not active user", LENGTH_LONG).show();
                                        }
                                    }else {
                                        ((AuthenticateActivity)getActivity()).setEmail(user.getEmail());
                                        ((AuthenticateActivity)getActivity()).setName(user.getDisplayName());

                                        SignInFragmentDirections.ActionSignInFragmentToSignUpDetailsFragment action = SignInFragmentDirections.actionSignInFragmentToSignUpDetailsFragment();
                                        Navigation.findNavController(signInFragmentBinding.getRoot()).navigate(action);

                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }).
                        addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("FIRE", e.getMessage());
                            }
                        });


            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(requireActivity(), "Error", LENGTH_LONG).show();
            }
        }
    }
    // [END auth_fui_result]

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(requireActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(requireActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .setLogo(R.drawable.my_great_logo)      // Set logo drawable
//                        .setTheme(R.style.MySuperAppTheme)      // Set theme
//                        .build(),
//                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }

}