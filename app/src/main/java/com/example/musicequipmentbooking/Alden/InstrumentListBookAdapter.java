package com.example.musicequipmentbooking.Alden;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicequipmentbooking.R;

import java.util.ArrayList;

public class InstrumentListBookAdapter extends RecyclerView.Adapter<InstrumentListViewHolder> {
    ArrayList<String> mData;

    public InstrumentListBookAdapter(ArrayList data){
        mData = data;
    }

    @NonNull
    @Override
    public InstrumentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_list_row_view, parent, false);

        InstrumentListViewHolder holder = new InstrumentListViewHolder(myView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentListViewHolder holder, int position) {
        holder.instrumentText.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
