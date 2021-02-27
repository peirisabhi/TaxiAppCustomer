package com.zonebecreations.taxiappcustomer.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zonebecreations.taxiappcustomer.R;

public class TicketHolder extends RecyclerView.ViewHolder{

    public TextView name;

    public TicketHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
    }
}
