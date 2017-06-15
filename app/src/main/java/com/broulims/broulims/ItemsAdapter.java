package com.broulims.broulims;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NathanLamb on 6/14/17.
 * Had help from
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private List<Item> products;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, aisle;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            aisle = (TextView) view.findViewById(R.id.aisle);
        }
    }

    public ItemsAdapter(List<Item> products)
    {
        this.products = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = products.get(position);
        holder.name.setText(item.getItemDescription());
        holder.price.setText(item.getBasePrice().toString());
        holder.aisle.setText("Aisle " + item.getAisle().toString());
    }

    @Override
    public int getItemCount() {
        //System.out.println("ITEM COUNT: " + products.size());
        return products.size();
    }
}