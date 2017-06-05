package com.broulims.broulims;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Database extends AppCompatActivity {

    private static final String TAG = Database.class.getName();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = rootRef.child("condition");
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        //this is a call to the database!!!
        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value); //saves to log

                data = (TextView) findViewById(R.id.dBReturn);
                data.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });

    }

    protected void onStart() {
        Database.super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                data.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void parseFile(View view) {
        Item item = new Item("Apple", "123214", "in the back", 12.00);
        Gson gson = new Gson();
        String test = gson.toJson(item);
        Log.d(TAG, "Value is: " + test); //saves to log

    }
}
