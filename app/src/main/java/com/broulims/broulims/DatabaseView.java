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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**********************
 * Had help from
 * https://stackoverflow.com/questions/41434475/how-to-list-data-from-firebase-database-in-listview
 **********************/
public class DatabaseView extends AppCompatActivity {

    ListView products;
    DatabaseReference dref;
    ArrayList<String> productList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);
        products  = (ListView) findViewById(R.id.products);
        dref = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);

        products.setAdapter(adapter);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map2list((HashMap) dataSnapshot.getValue());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };

        dref.addValueEventListener(listener);
    }


    public void map2list(HashMap<String, Long> hMap) {
        productList.clear();
        for(HashMap.Entry<String, Long> entry : hMap.entrySet()) {

            Long key = Long.parseLong(entry.getKey());
            String d = DateFormat.getDateTimeInstance().format(key);
            Long value = entry.getValue();
            productList.add(d + ": " + value);
        }
    }
}
