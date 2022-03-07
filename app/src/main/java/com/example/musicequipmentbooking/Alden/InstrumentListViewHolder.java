package com.example.musicequipmentbooking.Alden;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicequipmentbooking.R;

public class InstrumentListViewHolder extends RecyclerView.ViewHolder {
    protected TextView instrumentText;

    public InstrumentListViewHolder(@NonNull View itemView) {
        super(itemView);

        instrumentText = itemView.findViewById(R.id.instrumentTextView);


    }
}
