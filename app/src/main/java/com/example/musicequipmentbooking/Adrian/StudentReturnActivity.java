package com.example.musicequipmentbooking.Adrian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicequipmentbooking.Alden.CISInstrument;
import com.example.musicequipmentbooking.R;
import com.example.musicequipmentbooking.Ryan.StudentProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This class allows student to return instrument
 */
public class StudentReturnActivity extends AppCompatActivity {
    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;
    private String TAG= "myTag";
    private RecyclerView myRecyclerView;
    private ArrayList<CISInstrument> insList;
    private StudentReturnAdapter.RecyclerViewClickListener listener;  // for RV click
    private String insDocID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_return);

        // retrieve current user and link Firebase
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        // link the recyclerView layout item to variable
        myRecyclerView = findViewById(R.id.SR_RV);

        // call method to populate vehicle data to RV
        getAndPopulateData();
    }

    /**
     * This method populates all instruments from firebase to recyclerview
     */
    public void getAndPopulateData(){

        System.out.println("*** at begining of getAndPopulateData");

        // retrieve all Vehicles from Firebase
        firestore.collection("Instruments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            System.out.println("*** Firebase retrieve Instruments success");
                            // retrieve data from firebase and put in arraylist
                            insList = new ArrayList<CISInstrument>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //RV only show instruments borrowed by the student and not returned
                                if(document.get("borrowedStatus").equals(true))
                                {
                                    if(document.get("instrumentBorrower").equals(mUser.getEmail()))
                                    {
                                        insList.add(document.toObject(CISInstrument.class));
                                    }
                                }
                            }

                            // set RV to display contents from arraylist
                            setOnClickListener();  // for RV click, initialize the listener
                            StudentReturnAdapter myAdaptor = new StudentReturnAdapter(insList, listener); // include onClick listener
                            myRecyclerView.setAdapter(myAdaptor);
                            myRecyclerView.setLayoutManager(new LinearLayoutManager(StudentReturnActivity.this));

                        }
                        else
                        {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * This method is onClickListener for RV click to show instrument profile
     */
    private void setOnClickListener()
    {
        System.out.println("*** at setOnclickListener #1");
        listener = new StudentReturnAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick (View v, int position)
            {
                // vehicle information to pass to VehicleProfileActivity intent
                System.out.println("*** at setOnclickListner1.5");
                Intent intent = new Intent(getApplicationContext(), StudentConfirmReturnActivity.class);

                intent.putExtra("type", insList.get(position).getInstrumentType());
                intent.putExtra("id", insList.get(position).getInstrumentID());
                intent.putExtra("days", insList.get(position).getInstrumentDaysBorrowed());
                System.out.println("***** end of onClick");

                startActivity(intent);
                finish();
            }
        };
    }

    /**
     * This method allows user to click to go back to Student Profile
     * @param v
     */
    public void backToStudentProfile(View v)
    {
        // pass the vehicle information to next intent
        Intent intent = new Intent(getApplicationContext(), StudentProfileActivity.class);
        startActivity(intent);
    }
}