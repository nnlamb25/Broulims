package com.broulims.broulims;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**********************
 * Had help from
 * https://stackoverflow.com/questions/43561708/android-list-view-with-firebase-subitem
 * and
 * https://developer.android.com/training/material/lists-cards.html
 **********************/
public class DatabaseView extends AppCompatActivity {


    ListView products;
    DatabaseReference databaseReference;
    ArrayList<Item> productList = new ArrayList<>();
    ArrayAdapter<Item> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);

        products  = (ListView) findViewById(R.id.products);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        products.setAdapter(adapter);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    // Had to cast this to a double, wasn't sure how to
                    // do it in the constructors arguments.
                    //
                    // Never worked... fix price later
                    // double price = (double) ds.child("price").getValue();

                    Item item = new Item(ds.child("name").getValue().toString(),
                            ds.child("code").getValue().toString(),
                            ds.child("location").getValue().toString(),
                            10.25);
                    productList.add(item);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };

        databaseReference.addValueEventListener(listener);
    }
}
