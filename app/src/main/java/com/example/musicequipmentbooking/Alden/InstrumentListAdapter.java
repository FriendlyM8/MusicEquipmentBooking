package com.example.musicequipmentbooking.Alden;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicequipmentbooking.R;

import java.util.ArrayList;

/**
 * This class is Adapter for recycler view used by InstrumentsListActivity
 * This is written jointly by Alden and Adrian
 * Alden wrote the part for Recycler View
 * Adrian wrote the part for OnClick listener of the RecyclerView
 */
public class InstrumentListAdapter extends RecyclerView.Adapter<InstrumentListAdapter.InstrumentListActivityHolder>
{
    // defines local variable
    private ArrayList<CISInstrument> allInstruments;
    private InstrumentListAdapter.RecyclerViewClickListener listener;  // for RV click

    // constructor
    public InstrumentListAdapter (ArrayList<CISInstrument> myInstruments, InstrumentListAdapter.RecyclerViewClickListener listener)
    {
        allInstruments = myInstruments;
        this.listener = listener; // for RV click
    }

    /**
     * This class is Activity Holder class for RecyclerView, with OnClickListener for click
     * The part for Recycler View is written by Alden
     * The part for OnClick listener is written by Adrian
     */
    public class InstrumentListActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // the two fields to display on RV
        protected TextView insTypeText;
        protected TextView borrowerEmailText;

        // Activity Holder for RV with onClick listener
        public InstrumentListActivityHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // for RV click, bind the listener

            // this is Activity Holder of the Name and Status fields of recycler view
            insTypeText = itemView.findViewById(R.id.insRecTextName);
            borrowerEmailText = itemView.findViewById(R.id.insRecTextQuantity);
        }

        /**
         * This method implements the onClick listener
         * This method is written by Adrian
         */
        @Override
        public void onClick(View view)
        {
            listener.onClick(view, getAdapterPosition());  // call the onClick in listener
        }
    }

    /**
     * This is the ViewHolder for the recycler view
     * This method is written by Alden
     */

    @NonNull
    @Override
    public InstrumentListAdapter.InstrumentListActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_list_row_view, parent, false);
        InstrumentListAdapter.InstrumentListActivityHolder holder = new InstrumentListAdapter.InstrumentListActivityHolder(myView);
        return holder;
    }

    /**
     * This method pass the vehicle data to ViewHolder to display
     * This method is written by Alden
     */
    @Override
    public void onBindViewHolder(@NonNull InstrumentListAdapter.InstrumentListActivityHolder holder, int position) {
        CISInstrument v = allInstruments.get(position);
        holder.insTypeText.setText("    "+v.getInstrumentType());
        holder.borrowerEmailText.setText("Max borrow days: "+v.getInstrumentBorrowLimit());
    }

    @Override
    public int getItemCount()
    {
        return allInstruments.size();
    }

    /**
     * This is the interface for OnClick listener
     * This Interface is written by Adrian
     */
    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }
}
