package com.broulims.broulims;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**********************
 * Had help from
 * https://stackoverflow.com/questions/43561708/android-list-view-with-firebase-subitem
 * and
 * https://developer.android.com/training/material/lists-cards.html
 * and most recently
 * https://stackoverflow.com/questions/3313347/how-to-update-simpleadapter-in-android
 * ^^ This helped display sub items as well as items
 **********************/
public class DatabaseView extends AppCompatActivity {


    ListView products;
    DatabaseReference databaseReference;
    List<Map<String, String>> productList = new ArrayList<>();
    SimpleAdapter simpleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);

        products  = (ListView) findViewById(R.id.products);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        simpleAdapter = new SimpleAdapter(this, productList,
                android.R.layout.simple_list_item_2,
                new String[] {"Name", "Price"},
                new int[] {android.R.id.text1, android.R.id.text2});

        products.setAdapter(simpleAdapter);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    //Price wasn't working, will fix it later
                    Map<String, String> item = new HashMap<>(2);
                    item.put("Name", ds.child("ItemDescription").getValue().toString());
                    item.put("Price", "Price: " + ds.child("BasePrice").getValue().toString() + " Aisle: " + ds.child("Aisle").getValue().toString());

                    productList.add(item);
                }

                simpleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };

        databaseReference.addValueEventListener(listener);
    }
}
