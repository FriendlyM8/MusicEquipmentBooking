package com.example.musicequipmentbooking.Ryan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicequipmentbooking.Adrian.InstrumentsListActivity;
import com.example.musicequipmentbooking.Adrian.ReturnInsTeacherActivity;
import com.example.musicequipmentbooking.Alden.InstrumentReturnActivity;
import com.example.musicequipmentbooking.MainActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;

    private EditText userID;
    private EditText name;
    private EditText email;
    private EditText userType;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        userID = (EditText) findViewById(R.id.UserID);
        name = (EditText) findViewById(R.id.UserName);
        email = (EditText) findViewById(R.id.UserEmail);
        userType = (EditText) findViewById(R.id.UserType);
        password = (EditText) findViewById(R.id.Password);
    }

    public void SignUp(View v) {
        System.out.println("Sign Up!");
        String userIDInput = userID.getText().toString();
        String nameInput = name.getText().toString();
        String emailInput = email.getText().toString();
        String userTypeInput = userType.getText().toString();
        String passwordInput = password.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SIGN UP", "createUserWithEmail: success");
                    FirebaseUser mUser = mAuth.getCurrentUser();
                    updateUI(mUser);
                    //send the message to user
                    Toast messageUser = Toast.makeText(getApplicationContext(), "Successfully signed up by user! Welcome " + emailInput, Toast.LENGTH_LONG);
                    messageUser.show();
                } else {
                    Log.w("SIGN UP", "createUserWithEmail: failure", task.getException());
                    updateUI(null);
                    //send the message to user
                    Toast messageToUser = Toast.makeText(getApplicationContext(), "Sign up by user failed, maybe you have already signed up with this account.", Toast.LENGTH_LONG);
                    messageToUser.show();
                }
            }
        });
        //store the information into the firebase database
        //the firebase Authentication store the user
        User currUser = new User(userIDInput, nameInput, emailInput, userTypeInput, passwordInput);
        firestore.collection("User").document(nameInput).set(currUser);
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            //go to MainActivity
            Intent startPage = new Intent(this, MainActivity.class);
            startActivity(startPage);
        }
    }
}