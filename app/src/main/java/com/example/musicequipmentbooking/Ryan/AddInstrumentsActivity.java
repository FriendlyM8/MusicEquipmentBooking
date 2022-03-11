package com.example.musicequipmentbooking.Ryan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicequipmentbooking.Alden.CISInstrument;
import com.example.musicequipmentbooking.MainActivity;
import com.example.musicequipmentbooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddInstrumentsActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore fireStoreRef;

    private String owner;
    private boolean open;
    private EditText instrumenttype;
    private EditText instrumentborrowlimit;
    private EditText instrumentID;
    private EditText instrumentprice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instruments);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        fireStoreRef = FirebaseFirestore.getInstance();

        owner = mUser.getEmail();
        open = true;

        instrumenttype = (EditText) findViewById(R.id.InstrumentType);
        instrumentborrowlimit = (EditText) findViewById(R.id.InstrumentBorrowLimit);
        instrumentID = (EditText) findViewById(R.id.InstrumentID);
        instrumentprice = (EditText) findViewById(R.id.InstrumentPrice);
    }

    public void add_instrument(View v)
    {
        System.out.println("Add Vehicle!");
        String TypeInput = instrumenttype.getText().toString();
        int BorrowInput = Integer.parseInt(instrumentborrowlimit.getText().toString());
        String instrumentIDInput = UUID.randomUUID().toString();
        double priceInput = Double.parseDouble(instrumentprice.getText().toString());

        //add info on new instrument into firebase
        CISInstrument currInstrument = new CISInstrument(TypeInput, instrumentIDInput, 0, 0, null);
        fireStoreRef.collection("Instruments").document(instrumentIDInput).set(currInstrument);

        //message to user
        Toast messageUser = Toast.makeText(getApplicationContext(), "Successfully added instrument", Toast.LENGTH_LONG);
        messageUser.show();

        //go to main page
        Intent startPage = new Intent(this, MainActivity.class);
        startActivity(startPage);
    }
}