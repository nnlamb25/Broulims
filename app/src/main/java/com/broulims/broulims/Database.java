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
import java.util.ArrayList;

public class Database extends AppCompatActivity {

    private static final String TAG = Database.class.getName();
    private DatabaseReference mDatabase;


    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        data = (TextView) findViewById(R.id.dBReturn);

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void parseFile(View view) {
        // parse the "file"
        for (int i = 0; i < 5; i++)
        {
            Item item = new Item("Apple", "0" + i, "in the back", 12.00);
            updateDB(item);
        }
    }

    protected void updateDB(Item item){
        String key = mDatabase.child("items").push().getKey();
        mDatabase.child(key).setValue(item);
        String Item = mDatabase.child(key).getKey();
        data.setText(Item);


    }
}
