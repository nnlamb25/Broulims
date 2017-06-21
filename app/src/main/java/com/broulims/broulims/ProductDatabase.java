package com.broulims.broulims;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NathanLamb on 6/20/17.
 */

class ProductDatabase
{
    protected DatabaseReference databaseReference;
    protected List<Item> productList = new ArrayList<>();

    protected ProductDatabase()
    {
        productList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
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
