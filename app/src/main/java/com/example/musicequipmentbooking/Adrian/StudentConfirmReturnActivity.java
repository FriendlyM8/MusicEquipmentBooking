package com.example.musicequipmentbooking.Adrian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musicequipmentbooking.Alden.CISInstrument;
import com.example.musicequipmentbooking.Alden.CISUser;
import com.example.musicequipmentbooking.R;
import com.example.musicequipmentbooking.Ryan.StudentProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentConfirmReturnActivity extends AppCompatActivity {

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String insType;
    private String insID;
    private int insDays;
    private String insDaysString;
    private FirebaseFirestore firestore;
    private String TAG= "myTag";
    private CISInstrument myInsObj;
    private CISUser myUserObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_confirm_return);

        // retrieve current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // link layout items to parameter
        TextView typeText = findViewById(R.id.RS_Ins_type);
        TextView idText = findViewById(R.id.RS_ins_id);
        TextView daysText = findViewById(R.id.RS_days_borrowed);

        // Bundle the data from RecyclerView
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            insType = extras.getString("type");
            insID = extras.getString("id");
            insDays = extras.getInt("days");
            insDaysString = String.valueOf(insDays);
            System.out.println("***** passed intent to fields");
        }

        // Show data on screen
        typeText.setText(insType);
        idText.setText(insID);
        daysText.setText(insDaysString);
    }

    public void confirmReturn(View v)
    {
        // connect to firebase
        System.out.println("***** At returnCheck method");
        firestore = FirebaseFirestore.getInstance();

        // locate the vehicle object to update on firebase
        firestore.collection("Instruments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // retrieve data from firebase and loop to identify current vehicle
                            myInsObj = new CISInstrument();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myInsObj = document.toObject(CISInstrument.class);

                                // Found the Vehicle object
                                if(insID.equals(myInsObj.getInstrumentID()))
                                {
                                    System.out.println("inside If loop found insID : "+myInsObj.getInstrumentID());

                                    // Switch borrow status
                                    myInsObj.setBorrowedStatus(false);

                                    // call method to update this object on Firebase
                                    System.out.println("***** instrument doc now : "+document.getId());
                                    updateInsStatus(document.getId(), myInsObj);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        // once done, navigate to stident profile screen
        Intent intent = new Intent(this, StudentProfileActivity.class);
        startActivity(intent);
    }

    public void updateInsStatus(String docID, CISInstrument v)
    {
        System.out.println("***** docID is : "+docID);
        // Update instrument object with parameter object
        firestore.collection("Instruments")
                .document(docID)
                .set(v)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        Log.d(TAG, "DocumentSnapshot updated with ID: " + docID);
    }
}