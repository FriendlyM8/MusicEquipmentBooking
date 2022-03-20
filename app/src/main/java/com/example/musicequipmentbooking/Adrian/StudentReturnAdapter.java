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

public class StudentReturnAdapter extends RecyclerView.Adapter<StudentReturnAdapter.StudentReturnActivityHolder> {

    // defines local variable
    private ArrayList<CISInstrument> allInstruments;
    private StudentReturnAdapter.RecyclerViewClickListener listener;  // for RV click

    // constructor
    public StudentReturnAdapter (ArrayList<CISInstrument> myInstruments, StudentReturnAdapter.RecyclerViewClickListener listener)
    {
        allInstruments = myInstruments;
        this.listener = listener; // for RV click
        System.out.println("*** at end of Adaptor");
    }

    /**
     * This class is Activity Holder class for RecyclerView, with OnClickListener for click
     */
    public class StudentReturnActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // the two fields to display on RV
        protected TextView insTypeText;
        protected TextView borrowerEmailText;

        // Activity Holder for RV with onClick listener
        public StudentReturnActivityHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // for RV click, bind the listener

            // this is Activity Holder of the Name and Status fields of recycler view
            insTypeText = itemView.findViewById(R.id.studentRecTextName);
            borrowerEmailText = itemView.findViewById(R.id.studentRecTextQuantity);
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
    public StudentReturnAdapter.StudentReturnActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_return_row_view, parent, false);
        StudentReturnAdapter.StudentReturnActivityHolder holder = new StudentReturnAdapter.StudentReturnActivityHolder(myView);
        return holder;
    }

    /**
     * This method pass the vehicle data to ViewHolder to display
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull StudentReturnAdapter.StudentReturnActivityHolder holder, int position) {
        CISInstrument v = allInstruments.get(position);
        holder.insTypeText.setText("    "+v.getInstrumentType());
        holder.borrowerEmailText.setText("Days borrowed: "+v.getInstrumentDaysBorrowed());
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