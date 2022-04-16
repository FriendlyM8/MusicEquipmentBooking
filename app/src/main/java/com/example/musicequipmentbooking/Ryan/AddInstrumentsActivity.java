package com.example.musicequipmentbooking.Ryan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicequipmentbooking.Adrian.TeacherProfileActivity;
import com.example.musicequipmentbooking.Alden.CISInstrument;
import com.example.musicequipmentbooking.Alden.InstrumentsListActivity;
import com.example.musicequipmentbooking.MainActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

/**
 * This class allows teacher to add new instrument for students to borrow
 */
public class AddInstrumentsActivity extends AppCompatActivity
{
    // define local variable
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore fireStoreRef;

    private String owner;
    private boolean open;
    private String TAG= "myTag";
    private EditText instrumenttype;
    private EditText instrumentborrowlimit;
    private EditText instrumentID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instruments);

        // Firebase connection
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreRef = FirebaseFirestore.getInstance();

        owner = mUser.getEmail();
        open = true;

        // link layout items to local variables
        instrumenttype = (EditText) findViewById(R.id.InstrumentType);
        instrumentborrowlimit = (EditText) findViewById(R.id.InstrumentBorrowLimit);
        instrumentID = (EditText) findViewById(R.id.InstrumentID);
    }

    /**
     * This method updates Firebase to add the instrument object
     * @param v
     */
    public void add_instrument(View v)
    {
       // retrieve new instrument information from layout fields
        String TypeInput = instrumenttype.getText().toString();
        int BorrowInput = Integer.parseInt(instrumentborrowlimit.getText().toString());
        String instrumentIDInput = UUID.randomUUID().toString();

        //add info on new instrument into firebase
        CISInstrument currInstrument = new CISInstrument(TypeInput, instrumentIDInput, 0, 0, BorrowInput, null, false, true);
       // fireStoreRef.collection("Instruments").document(instrumentIDInput).set(currInstrument);

        fireStoreRef.collection("Instruments")
                .add(currInstrument)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added Instrument with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        //message to user
        Toast messageUser = Toast.makeText(getApplicationContext(), "Successfully added instrument", Toast.LENGTH_LONG);
        messageUser.show();

        //go to main page
        Intent startPage = new Intent(this, TeacherProfileActivity.class);
        startActivity(startPage);
    }

    /**
     * This method allows user to click to go back to Teacher profile
     * @param v
     */
    public void backButton(View v){
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        startActivity(intent);
    }
}