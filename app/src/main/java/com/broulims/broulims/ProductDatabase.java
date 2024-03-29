package com.broulims.broulims;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * This class holds the product list array from
 * the database we get from broulims via the
 * firebase database. The class also update
 * the database each time a value is added
 * or taken away.
 *
 * @author NathanLamb on 6/20/17.
 */

public class ProductDatabase
{
    public List<Item> productList = new ArrayList<>();
    private boolean dataReady;


    // Constructor
    public ProductDatabase()
    {
        productList = new ArrayList<>();
        dataReady = false;

        // This saves the database to the phone, so it does not need to download
        // the whole database each time the app is opened

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        // This updates the database on the phone each time the database online is changed
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Item item = ds.getValue(Item.class);
                    item.setKey(ds.getKey());
                    productList.add(item);
                }


                dataReady = true;
                Log.i("Database Size", String.valueOf(productList.size()) + " items");
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                System.out.println(databaseError.toException());
            }
        });
        Log.i("Value Event Listener", "Attached to database reference");
    }

    /**
     * check to see if the data is ready to fill in
     * and open for the user
     * @return true when data is ready
     */
    public boolean isDataReady()
    {
        return dataReady;
    }
}
