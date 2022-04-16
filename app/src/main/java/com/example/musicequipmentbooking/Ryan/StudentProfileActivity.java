package com.example.musicequipmentbooking.Ryan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicequipmentbooking.Adrian.StudentReturnActivity;
import com.example.musicequipmentbooking.Alden.AuthActivity;
import com.example.musicequipmentbooking.Alden.CISUser;
import com.example.musicequipmentbooking.Alden.InstrumentsListActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class shows student basic information and list of buttons for students to
 * carry out different functions
 */
public class StudentProfileActivity extends AppCompatActivity {

    // define local variables
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser mUser;
    private TextView displayUserText;
    private String myUserType;
    private CISUser myUserObj;
    private String TAG= "myTag";
    private String currUserType;
    private String currEmail;

    /**
     * This onCreate is for the text for displaying the current student user
     * This method is written by Alden
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase connection
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();

        FirebaseUser user = mAuth.getCurrentUser();
        String userIDString = user.getUid();

        setContentView(R.layout.activity_student_profile);

        // receive parameters passed from login screen
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            myUserType = extras.getString("userType");
            System.out.println("***** passed intent to fields: "+myUserType);
        }

/** this is to retrieve user type from Firebase to see if it matches selected user type
        firestore.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    myUserObj = new CISUser();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        myUserObj = document.toObject(CISUser.class);

                        // Found the User object for current user
                        if(mUser.getEmail().equals(myUserObj.getEmail()))
                        {
                            // display user information on screen
                            currUserType = myUserObj.getUserType();
                            System.out.println("***currUserType "+currUserType);
                        }
                    }**/

        firestore.collection("Users").document(userIDString).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();
                    CISUser curUser = ds.toObject(CISUser.class);
                    currEmail = user.getEmail(); // error with curUser.getEmail();
                    //currUserType = curUser.getUserType();
                    displayUserText = findViewById(R.id.displayUserText);
                    Log.d("StudentProfileActivity", "Current user email"+ currEmail);
                    displayUserText.setText("WELCOME BACK, "+ currEmail);

                }
            }
        });
/**
        if(!currUserType.equals(myUserType))
        {
            System.out.println("user type mismatch "+currUserType);
            // if selected user type mismatch with drop down user type, toast nmessage
            Toast messageUser = Toast.makeText(getApplicationContext(), "User type mismatch!", Toast.LENGTH_LONG);
            messageUser.show();
            //System.out.println("user type mismatch "+currUserType);

            // logout the user, back to login screen
            FirebaseAuth.getInstance().signOut();
            Intent startActivity = new Intent(this, AuthActivity.class);
            startActivity(startActivity);
        }**/
    }


    /**
     * This method allows user to Go to InstrumentsList Activity
     */
    public void goInstrumentsListActivity(View v) {
        Intent startActivity = new Intent(this, InstrumentsListActivity.class);
        startActivity(startActivity);
    }

    /**
     * This method allows user to return instrument
     * @param v
     */
    public void goStudentReturnActivity(View v) {
        Intent startActivity = new Intent(this, StudentReturnActivity.class);
        startActivity(startActivity);
    }

    /**
     * This signs the user out while bringing the user back to Auth Activity
     * This method is written by Ryan
     */
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