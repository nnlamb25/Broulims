package com.broulims.broulims;

import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by Nathan on 6/14/2017
 * Had help from
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 **********************/
public class search_test extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView products;
    ItemsAdapter itemsAdapter;
    List<Item> productList = new ArrayList<>();
    ProgressBar loadingSpinner;
    SearchView searchView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_view);

        handler = new Handler();

        RecyclerView.LayoutManager productLayoutManager = new LinearLayoutManager(getApplicationContext());

        products = (RecyclerView) findViewById(R.id.products);
        products.setLayoutManager(productLayoutManager);
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        loadingSpinner = (ProgressBar) findViewById(R.id.progressSpinner);

        loadingSpinner.setVisibility(View.VISIBLE);

        final Runnable runnable = new Runnable() {
            @Override
            public void run()
            {
                while (!FrontPage.productDatabase.isDataReady())
                {
                    Log.i("SearchTest dataNotReady", "NOT READY");
                }

                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        loadingSpinner.setVisibility(View.GONE);

                        productList = FrontPage.productDatabase.productList;

                        itemsAdapter = new ItemsAdapter(productList);
                        products.setAdapter(itemsAdapter);

                        Log.i("SearchTest dataNotReady", "READY");
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        return true;
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
