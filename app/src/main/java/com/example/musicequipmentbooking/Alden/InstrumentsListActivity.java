package com.example.musicequipmentbooking.Alden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicequipmentbooking.Adrian.BorrowActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

/**
 * This class contains recycler view showing list of all instruments
 * This class is written jointly by Alden and Adrian
 * Alden wrote the RecyclerView and database connection portion
 * Adrian added OnClick feature to the RecyclerView
 */
public class InstrumentsListActivity extends AppCompatActivity {

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private RecyclerView myRecyclerView;
    private ArrayList<CISInstrument> insList;
    private InstrumentListAdapter.RecyclerViewClickListener listener;  // for RV click
    private String insDocID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruments_list);

        // retrieve current user and link Firebase
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // link the recyclerView layout item to variable
        myRecyclerView = findViewById(R.id.instrumentRecView);

        // call method to populate vehicle data to RV
        getAndPopulateData();
    }

    /**
     * This method populates all instruments from firebase to recyclerview
     */
    public void getAndPopulateData(){

        // retrieve all Vehicles from Firebase
        // this part is written by Alden
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
                                System.out.println("*** borrowedStatus: "+document.get("borrowedStatus"));
                                if(document.get("borrowedStatus").equals(false))
                                {
                                    insList.add(document.toObject(CISInstrument.class));
                                }
                            }

                            // set RV to display contents from arraylist
                            // this part is written by Adrian
                            setOnClickListener();  // for RV click, initialize the listener
                            InstrumentListAdapter myAdaptor = new InstrumentListAdapter(insList, listener); // include onClick listener
                            myRecyclerView.setAdapter(myAdaptor);
                            myRecyclerView.setLayoutManager(new LinearLayoutManager(InstrumentsListActivity.this));
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
     * This method is written by Adrian
     */
    private void setOnClickListener()
    {
        System.out.println("*** at setOnclickListener #1");
        listener = new InstrumentListAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick (View v, int position)
            {
                // vehicle information to pass to VehicleProfileActivity intent
                System.out.println("*** at setOnclickListner1.5");
                Intent intent = new Intent(getApplicationContext(), BorrowActivity.class);

                intent.putExtra("type", insList.get(position).getInstrumentType());
                intent.putExtra("id", insList.get(position).getInstrumentID());
                intent.putExtra("days", insList.get(position).getInstrumentBorrowLimit());
                System.out.println("***** end of onClick");

                startActivity(intent);
                finish();
            }
        };
    }

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void backButton(View v){
        Intent intent = new Intent(this, StudentProfileActivity.class);
        startActivity(intent);
    }
}