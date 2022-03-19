package com.example.musicequipmentbooking.Ryan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.musicequipmentbooking.Adrian.InstrumentsListActivity;
import com.example.musicequipmentbooking.Adrian.ReturnInsTeacherActivity;
import com.example.musicequipmentbooking.Alden.InstrumentReturnActivity;
import com.example.musicequipmentbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

    };

    public void borrowButton(View v)
    {
        Intent intent = new Intent(this, InstrumentsListActivity.class);
        startActivity(intent);
    }

    public void returnButton(View v)
    {
        Intent intent = new Intent(this, InstrumentReturnActivity.class);
        startActivity(intent);
    }
}