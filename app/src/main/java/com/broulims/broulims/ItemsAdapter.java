package com.broulims.broulims;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.broulims.broulims.Fragment.BroulimsMap.addToList;
import static com.broulims.broulims.FragmentHolder.hideKeyboard;

/**
 * This allows the list view of items to be viewable in the recycle view
 * <p></p>
 * Had help from
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 * <br>
 * and from the comments of the video
 * https://www.youtube.com/watch?v=j9_hcfWVkIc
 * ^^ comments explained how to implement onclick listener
 *
 * @author NathanLamb on 6/14/17
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private Context context;
    private List<Item> products;
    public static long dbCount;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, aisle; // These are what we want to see in the recycle view

        // THe holder holds the 3 items which are in item_list_row.xml
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            aisle = (TextView) view.findViewById(R.id.aisle);
        }
    }

    // Constructor
    public ItemsAdapter(Context mContext, List<Item> products)
    {
        context = mContext;
        this.products = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    // Each row of the recycle view displays what is in the holder, and is clickable to show
    // more information on the item clicked
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item item = products.get(position);
        holder.name.setText(item.getItemDescription());
        holder.price.setText(item.getBasePrice().toString());
        holder.aisle.setText("Aisle " + item.getAisle().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //ProductDatabase database = new ProductDatabase();
                //database.getDatabase().getReference().child(pr)
                Log.i("UPC:", item.getKey());
                String UPC = item.getKey();
                // increments the count
                FirebaseDatabase.getInstance().getReference().child(UPC).child("Count").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        dbCount = (long) snapshot.getValue();
                        Log.i("CountValue:", String.valueOf(dbCount));

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                FirebaseDatabase.getInstance().getReference().child(UPC).child("Count").setValue(dbCount + 1);
                hideKeyboard(context);
                FragmentHolder.viewMap();
                addToList(products.get(position));

            }
        });
    }

    // This is the number of items the database the phone has access to currently holds
    @Override
    public int getItemCount() {
        //System.out.println("ITEM COUNT: " + products.size());
        return products.size();
    }

    public void setFilter(List<Item> newList) {
        this.products = new ArrayList<>();
        this.products.addAll(newList);
        notifyDataSetChanged();
    }
}