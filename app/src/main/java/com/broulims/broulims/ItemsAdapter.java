package com.broulims.broulims;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NathanLamb on 6/14/17.
 * Had help from
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 * and from the commments of the video
 * https://www.youtube.com/watch?v=j9_hcfWVkIc
 * ^^ comments explained how to implement onclick listener
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Item item = products.get(position);
        holder.name.setText(item.getItemDescription());
        holder.price.setText(item.getBasePrice().toString());
        holder.aisle.setText("Aisle " + item.getAisle().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), products.get(position).getItemDescription() + " clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), IndoorDemoActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

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