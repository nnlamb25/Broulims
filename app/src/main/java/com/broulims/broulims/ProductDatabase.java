package com.broulims.broulims;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NathanLamb on 6/20/17.
 * This holds the product list array from the database, and updates the database each time
 * a value is added or taken away.
 */

public class ProductDatabase
{
    public List<Item> productList = new ArrayList<>();

    // Constructor
    public ProductDatabase()
    {
        productList = new ArrayList<>();

        // This saves the database to the phone, so it does not need to download
        // the whole database each time the app is opened
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.i("Value Event Listener", "About to attach");

        // This updates the database on the phone each time the database online is changed
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Item item = ds.getValue(Item.class);

                    productList.add(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                System.out.println(databaseError.toException());
            }
        });
        Log.i("Value Event Listener", "Attached to database reference");
    }

    // Checks to see if the product list has been filled and is ready to be displayed
    public boolean isDataReady()
    {
        if (productList.size() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
