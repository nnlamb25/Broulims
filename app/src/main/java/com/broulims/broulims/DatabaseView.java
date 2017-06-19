package com.broulims.broulims;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by Nathan on 6/14/2017
 * Had help from
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 **********************/

public class DatabaseView extends AppCompatActivity {


    RecyclerView products;
    ItemsAdapter itemsAdapter;
    DatabaseReference databaseReference;
    List<Item> productList = new ArrayList<>();
    ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadingSpinner = (ProgressBar) findViewById(R.id.progressSpinner);

        products = (RecyclerView) findViewById(R.id.products);

        RecyclerView.LayoutManager productLayoutManager = new LinearLayoutManager(getApplicationContext());
        products.setLayoutManager(productLayoutManager);
        products.setItemAnimator(new DefaultItemAnimator());
        itemsAdapter = new ItemsAdapter(productList);
        products.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Found on https://stackoverflow.com/questions/24471109/recyclerview-onclick
        products.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), products ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getBaseContext(), productList.get(position).getItemDescription() + " clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(getBaseContext(), productList.get(position).getItemDescription() + " selected", Toast.LENGTH_SHORT).show();
                    }
                })
        );

        products.setAdapter(itemsAdapter);

        loadingSpinner.setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), "Updating Products", Toast.LENGTH_SHORT).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Item item = ds.getValue(Item.class);

                    productList.add(item);
                }
                //System.out.println("LIST SIZE: " + productList.size());
                loadingSpinner.setVisibility(View.GONE);
                products.setAdapter(itemsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        });
    }
}
