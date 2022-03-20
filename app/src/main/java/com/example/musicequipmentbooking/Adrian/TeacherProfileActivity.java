package com.example.musicequipmentbooking.Adrian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musicequipmentbooking.Alden.CISUser;
import com.example.musicequipmentbooking.R;
import com.example.musicequipmentbooking.Ryan.AddInstrumentsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class shows teacher's profile
 */
public class TeacherProfileActivity extends AppCompatActivity {

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private CISUser myUserObj;
    private String TAG= "myTag";
    private CISUser currUserObj;
    TextView user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        // connections to firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        // show currently signed in user name
        user_email = (TextView) this.findViewById(R.id.TP_email);
        user_email.setText(mUser.getEmail());

        // link layout items to variables
        TextView user_id = findViewById(R.id.TP_ID);

        // retrieve user information from firebase
        firestore.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myUserObj = new CISUser();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myUserObj = document.toObject(CISUser.class);

                                // Found the User object for current user
                                if(mUser.getEmail().equals(myUserObj.getEmail()))
                                {
                                    // display user information on screen
                                    user_id.setText(myUserObj.getUserID());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void seeReturns(View v)
    {
        Intent intent = new Intent(this, ReturnInsTeacherActivity.class);
        startActivity(intent);
    }

    public void addInstrument(View v)
    {
        Intent intent = new Intent(this, AddInstrumentsActivity.class);
        startActivity(intent);
    }
}