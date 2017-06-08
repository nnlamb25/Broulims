package com.broulims.broulims;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NathanLamb on 6/7/17.
 * https://developer.android.com/training/material/lists-cards.html
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stringTextView;
        public ViewHolder(TextView v) {
            super(v);
            stringTextView = v;
        }
    }
    public MyAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
            .inflate(R.layout.activity_database_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stringTextView.setText(items.get(position).getName().toString()
        + "\n" + items.get(position).getPrice()
        + "\n" + items.get(position).getLocation().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
