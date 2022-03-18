package com.example.musicequipmentbooking.Alden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.musicequipmentbooking.Adrian.InstrumentListAdapter;
import com.example.musicequipmentbooking.Adrian.TeacherProfileActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class InstrumentReturnActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    RecyclerView recView;
    ArrayList<CISInstrument> instrumentsList;
    ArrayList<String> instrumentReturnedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_return);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        instrumentsList = new ArrayList<>();
        instrumentReturnedString = new ArrayList();

        InstrumentListAdapter myAdapter = new InstrumentListAdapter(instrumentsList);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("RecyclerView", "Rec View Success");

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        firestore.collection("Instruments").whereEqualTo("returnedChecked", false).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        CISInstrument curInstrument = document.toObject(CISInstrument.class);
                        instrumentReturnedString.add("Instrument Type: "+ curInstrument.getInstrumentType() +"Instrument Days Borrowed: "+ curInstrument.getInstrumentDaysBorrowed()+ "Borrower: "+ curInstrument.getInstrumentBorrower());
                        instrumentsList.add(curInstrument);
                    }
                    recView.setAdapter(myAdapter);
                    recView.setLayoutManager(new LinearLayoutManager(InstrumentReturnActivity.this));
                }
                else{
                    Log.d("Get Instrument", "Failed to get Instruments", task.getException());
                }
            }
        });
    }


 //   @Override
    public void onInstrumentClick(int position) {
        instrumentsList.get(position);
        Intent intent = new Intent(this, InstrumentReturnActivity.class);
        intent.putExtra("selectedReturnedInstrument", String.valueOf(instrumentsList.get(position)));
        Log.d("selectedReturnedInstrument", "Put Extra: success");
        startActivity(intent);
    }

    //Allow users to sign out
    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void backButton(View v){
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        startActivity(intent);
    }




}