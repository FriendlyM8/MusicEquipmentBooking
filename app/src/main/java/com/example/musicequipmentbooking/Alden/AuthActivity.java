package com.example.musicequipmentbooking.Alden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.musicequipmentbooking.Adrian.TeacherProfileActivity;
import com.example.musicequipmentbooking.R;
import com.example.musicequipmentbooking.Ryan.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    protected EditText emailField;
    protected EditText passwordField;
    protected String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.addEmailText);
        passwordField = findViewById(R.id.addPasswordText);

        Spinner spinner = findViewById(R.id.userTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        userType = spinner.getSelectedItem().toString();
    }

    /**
     * This method allows users to sign in with their emails and password.
     *
     * @param v
     */
    //Allow user to sign in with a Google account
    public void signIn(View v) {
        System.out.println("Log in");
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        //Sign in with mAuth
        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SIGN IN", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("SIGN IN", "signInWithEmail:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    //Allow users to sign up with a Google account
    public void signUp(View v) {
        System.out.println("Sign Up");
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SIGN UP", "Successfully signed up the user");
                    updateUI(null);
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userIDString = user.getUid();

                    CISUser newUser = new CISUser(emailString, passwordString, userIDString, userType, 0, null);

                    firestore.collection("Users").document(newUser.getUserID()).set(newUser);

                } else {
                    Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    //If user is signed in or created, goes to next screen
    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            if(userType.equals("Teacher")){
                Intent intent = new Intent(this, TeacherProfileActivity.class);
                startActivity(intent);
            }
            if(userType.equals("Student")){
                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}