package com.zonebecreations.taxiappcustomer.ui.splash;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zonebecreations.taxiappcustomer.R;

public class SplashFragment extends Fragment {

    private SplashViewModel mViewModel;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_fragment, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavDirections action = SplashFragmentDirections.actionSplashFragmentToSignInFragment();
                Navigation.findNavController(view).navigate(action);
            }
        }, 500);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        // TODO: Use the ViewModel
    }

}