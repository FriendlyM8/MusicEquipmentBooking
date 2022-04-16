package com.example.musicequipmentbooking.Alden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

/**
 * Need to cnofirm this is Student Return Activity and is replaced by StudentReturnActivity class
 */
public class InstrumentReturnActivity extends AppCompatActivity {

    // define local variable
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    RecyclerView recView;
    ArrayList<CISInstrument> instrumentsList;
    ArrayList<String> instrumentReturnedString;
    private InstrumentListAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_return);

        // Firebased connection
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        instrumentsList = new ArrayList<>();
        instrumentReturnedString = new ArrayList();

        // defined recycler view adaptor
        InstrumentListAdapter myAdapter = new InstrumentListAdapter(instrumentsList, listener);
        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("RecyclerView", "Rec View Success");

        // get current user email
        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        // Firebase connection to retrieve list of instruments the student borrow
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


    /**
     * This is for user to click into an instrument to return
     * @param position
     */
    public void onInstrumentClick(int position) {
        instrumentsList.get(position);
        Intent intent = new Intent(this, InstrumentReturnActivity.class);
        intent.putExtra("selectedReturnedInstrument", String.valueOf(instrumentsList.get(position)));
        Log.d("selectedReturnedInstrument", "Put Extra: success");
        startActivity(intent);
    }

    /**
     * This method allows user to click to signout
     * @param v
     */
    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    /**
     * This method allows user to click to go back to Teacher's profile
     * @param v
     */
    public void backButton(View v){
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        startActivity(intent);
    }

}