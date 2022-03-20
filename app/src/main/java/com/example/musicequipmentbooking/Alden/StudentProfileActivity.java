package com.example.musicequipmentbooking.Alden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicequipmentbooking.Adrian.InstrumentsListActivity;
import com.example.musicequipmentbooking.R;
import com.example.musicequipmentbooking.Ryan.AddInstrumentsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private TextView displayUserText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        setContentView(R.layout.activity_student_profile);



        firestore.collection("Users").document(userIDString).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    CISUser curUser = ds.toObject(CISUser.class);
                    String email = curUser.getEmail();
                    displayUserText = findViewById(R.id.displayUserText);
                    Log.d("StudentProfileActivity", "Current user email"+ email);
                    displayUserText.setText("WELCOME BACK, "+ email);
                }
            }
        });
    }

    //Go to InstrumentsList Activity
    public void goInstrumentsListActivity(View v) {
        Intent startActivity = new Intent(this, InstrumentsListActivity.class);
        startActivity(startActivity);
    }

    public void signOut(View v) {
        //sign out the account and go back to AuthActivity
        FirebaseAuth.getInstance().signOut();
        Intent startActivity = new Intent(this, AuthActivity.class);
        startActivity(startActivity);
        //send message to user
        Toast messageUser = Toast.makeText(getApplicationContext(), "Successfully signed out with Email!", Toast.LENGTH_LONG);
        messageUser.show();
    }
}