package com.example.musicequipmentbooking.Adrian;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicequipmentbooking.Adrian.InstrumentListViewHolder;
import com.example.musicequipmentbooking.Alden.CISInstrument;
import com.example.musicequipmentbooking.R;

import java.util.ArrayList;

public class InstrumentListAdapter extends RecyclerView.Adapter<InstrumentListViewHolder> {
    ArrayList<String> mData;
    ArrayList<CISInstrument> allInstruments;

    public InstrumentListAdapter(ArrayList<CISInstrument> data)
    {
        allInstruments = data;
    }

    @NonNull
    @Override
    public InstrumentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_list_row_view, parent, false);
        InstrumentListViewHolder holder = new InstrumentListViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentListViewHolder holder, int position)
    {
        CISInstrument c = allInstruments.get(position);
        holder.instrumentText.setText("  "+c.getInstrumentType()+"  ");
       // holder.instrumentQuantity.setText("  "+c.getQuantity());
    }

    @Override
    public int getItemCount()
    {
        return allInstruments.size();
    }
}
