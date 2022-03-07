package com.example.musicequipmentbooking.Alden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicequipmentbooking.MainActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

class InstrumentListActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore firestore;

    RecyclerView recView;
    ArrayList<CISInstrument> instrumentsList;
    ArrayList<String> instrumentsListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruments_list);

        instrumentsList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        recView = findViewById(R.id.instrumentRecView);

        InstrumentListBookAdapter myAdapter = new InstrumentListBookAdapter(instrumentsList);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("RecyclerView", "Rec View Success");

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        firestore.collection("Instruments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        CISInstrument curInstrument = document.toObject(CISInstrument.class);
                        instrumentsListString.add("Instrument Type: "+ curInstrument.getInstrumentType() +"Instrument Days Requested: "+ curInstrument.getInstrumentDaysRequest() +"Instrument Days Borrowed: "+ curInstrument.getInstrumentDaysBorrowed()+ "Current Borrower: "+ curInstrument.getInstrumentBorrower());
                        instrumentsList.add(curInstrument);
                    }
                }
                else{
                    Log.d("Get Instrument", "Failed to get Instruments", task.getException());
                }
            }
        });

    }

    //Allow users to sign out
    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void returnButton(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}