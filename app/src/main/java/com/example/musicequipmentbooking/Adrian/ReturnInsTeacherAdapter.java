package com.example.musicequipmentbooking.Adrian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicequipmentbooking.Alden.CISInstrument;
import com.example.musicequipmentbooking.R;

import java.util.ArrayList;

/**
 * This class is used by recycler view, it includes the ViewHolder and ClickListener
 */
public class ReturnInsTeacherAdapter extends RecyclerView.Adapter<ReturnInsTeacherAdapter.ReturnInsTeacherActivityHolder> {

    // defines local variable
    private ArrayList<CISInstrument> allInstruments;
    private RecyclerViewClickListener listener;  // for RV click

    // constructor
    public ReturnInsTeacherAdapter (ArrayList<CISInstrument> myInstruments, RecyclerViewClickListener listener)
    {
        allInstruments = myInstruments;
        this.listener = listener; // for RV click
        System.out.println("*** at end of Adaptor");
    }

    /**
     * This class is Activity Holder class for RecyclerView, with OnClickListener for click
     */
    public class ReturnInsTeacherActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // the two fields to display on RV
        protected TextView insTypeText;
        protected TextView borrowerEmailText;

        // Activity Holder for RV with onClick listener
        public ReturnInsTeacherActivityHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // for RV click, bind the listener

            // this is Activity Holder of the Name and Status fields of recycler view
            insTypeText = itemView.findViewById(R.id.retRecTextName);
            borrowerEmailText = itemView.findViewById(R.id.retRecTextQuantity);
        }

        @Override
        public void onClick(View view)
        {
            listener.onClick(view, getAdapterPosition());  // call the onClick in listener
        }
    }

    /**
     * This is the ViewHolder for the recycler view
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public ReturnInsTeacherActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_ins_row_view, parent, false);
        ReturnInsTeacherActivityHolder holder = new ReturnInsTeacherActivityHolder(myView);
        return holder;
    }

    /**
     * This method pass the vehicle data to ViewHolder to display
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ReturnInsTeacherActivityHolder holder, int position) {
        CISInstrument v = allInstruments.get(position);
        holder.insTypeText.setText("    "+v.getInstrumentType());
        holder.borrowerEmailText.setText("Borrower: "+v.getInstrumentBorrower());
    }

    @Override
    public int getItemCount()
    {
        return allInstruments.size();
    }

    /**
     * This is the interface for OnClick listener
     */
    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }
}