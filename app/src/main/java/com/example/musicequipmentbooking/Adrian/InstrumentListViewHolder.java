package com.example.musicequipmentbooking.Adrian;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicequipmentbooking.R;

public class InstrumentListViewHolder extends RecyclerView.ViewHolder
{
    protected TextView instrumentText;
    protected TextView instrumentQuantity;

    public InstrumentListViewHolder(@NonNull View itemView)
    {
        super(itemView);

        instrumentText = itemView.findViewById(R.id.insRecTextName);
        instrumentQuantity = itemView.findViewById(R.id.insRecTextQuantity);
    }
}
