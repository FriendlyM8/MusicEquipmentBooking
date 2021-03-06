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
import com.example.musicequipmentbooking.Ryan.StudentProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class is for login page
 */
public class AuthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    protected EditText emailField;
    protected EditText passwordField;
    protected String userType;
    protected Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Firebase connection setting
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // link the layout fields to local variables
        emailField = findViewById(R.id.addEmailText);
        passwordField = findViewById(R.id.addPasswordText);

        // define drop down spinner for user type
        spinner = findViewById(R.id.userTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        //userType = spinner.getSelectedItem().toString();  //moved to updateUI from onCreate
    }

    /**
     * This method allows users to sign in with their emails and password.
     *
     * @param v
     */
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

    /**
     * This method allows user to signup with email address
     * @param v
     */
    public void signUp(View v) {
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        userType = spinner.getSelectedItem().toString();
        System.out.println("Sign Up emil: "+emailString+" passwd: "+passwordString+" User type: "+userType);

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SIGN UP", "Successfully signed up the user");
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userIDString = user.getUid();
                    Log.d("AuthActivity", "UserType"+ userType);

                    if(userType.equals("Teacher")){
                        System.out.println("*** at signup Teacher clause");
                        CISTeacher newUser = new CISTeacher(emailString, passwordString, userIDString, userType);
                        firestore.collection("Users").document(newUser.getUserID()).set(newUser);
                    }
                    if(userType.equals("Student")){
                        System.out.println("*** at signup Student clause");
                        CISUser newUser = new CISUser(emailString, passwordString, userIDString, userType, 0, null);
                        firestore.collection("Users").document(newUser.getUserID()).set(newUser);
                    }
                    updateUI(user);
                }
                else {
                    Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
                }
            }
        });
    }

    /**
     * If user is signed in or created, goes to next screen
     * @param currentUser
     */
    public void updateUI(FirebaseUser currentUser)
    {
        if (currentUser != null) {
            userType = spinner.getSelectedItem().toString();
            if(userType.equals("Teacher")){
                Intent intent = new Intent(this, TeacherProfileActivity.class);
                intent.putExtra("userType", "Teacher");
                startActivity(intent);
                Log.d("Auth Activity", "Intent to TeacherProfileActivity");
            }
            if(userType.equals("Student")){
                Intent intent = new Intent(this, StudentProfileActivity.class);
                intent.putExtra("userType", "Student");
                startActivity(intent);
                Log.d("Auth Activity", "Intent to UserProfileActivity");
            }
        }
    }

    /**
     * This method populates a toast to user when different user type is selected from spinner
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}