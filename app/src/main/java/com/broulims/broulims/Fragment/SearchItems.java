package com.broulims.broulims.Fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.broulims.broulims.Item;
import com.broulims.broulims.ItemsAdapter;
import com.broulims.broulims.ProductDatabase;
import com.broulims.broulims.R;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by Nathan on 6/14/2017
 * Had help from
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 **********************/
public class SearchItems extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView products;
    ItemsAdapter itemsAdapter;
    List<Item> productList = new ArrayList<>();
    ProgressBar loadingSpinner;
    SearchView searchView;
    Handler handler;
    ProductDatabase productDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View view = inflater.inflate(R.layout.activity_database_view, container, false);

        productDatabase = new ProductDatabase();
        handler = new Handler();

        RecyclerView.LayoutManager productLayoutManager = new LinearLayoutManager(getActivity());

        products = (RecyclerView) view.findViewById(R.id.products);
        products.setLayoutManager(productLayoutManager);
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));

        loadingSpinner = (ProgressBar) view.findViewById(R.id.progressSpinner);

        loadingSpinner.setVisibility(View.VISIBLE);



        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        final Runnable runnable = new Runnable() {
            @Override
            public void run()
            {
                while (!productDatabase.isDataReady())
                {

                }

                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        loadingSpinner.setVisibility(View.GONE);

                        productList = productDatabase.productList;

                        itemsAdapter = new ItemsAdapter(productList);
                        products.setAdapter(itemsAdapter);

                        Log.i("Is Data Ready", "READY");
                    }
                });
            }
        };


        new Thread(runnable).start();


    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                        itemsAdapter.setFilter(productList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        final ArrayList<Item> newList = new ArrayList<>();
        for(Item i : productList) {
            String name = i.getItemDescription().toLowerCase();
            if(name.contains(newText))
                newList.add(i);
        }
        itemsAdapter.setFilter(newList);

        return true ;
    }
}
