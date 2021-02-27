package com.zonebecreations.taxiappcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AuthenticateActivity extends AppCompatActivity {

    public String email;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}