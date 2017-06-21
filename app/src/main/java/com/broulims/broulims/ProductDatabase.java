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
 */

class ProductDatabase
{
    protected List<Item> productList = new ArrayList<>();

    protected ProductDatabase()
    {
        productList = new ArrayList<>();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.i("Value Event Listener", "About to attach");
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

    public boolean isDataReady()
    {
        if (productList.size() == 0)
        {
            Log.i("Data Ready", String.valueOf(productList.size()));
            return false;
        }
        else
            return true;
    }
}
