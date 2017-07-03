package com.broulims.broulims.Fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
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

/**
 * This is the search activity for the program. The program allows the user
 * to use a text box to input a search query. The function will search through
 * the data base for the query and then display any matching items.
 * <br></br>
 * There is also a bottom navigation bar which you can use to
 * start our other main activities (such as the map view or list view)
 * <p></p>
 * Had help from:
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 *
 * @author Nathan Lamb on 6/14/2017
 */
public class SearchItems extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView products;
    ItemsAdapter itemsAdapter;
    List<Item> productList = new ArrayList<>();
    ProgressBar loadingSpinner;
    SearchView searchView;
    Handler handler;
    public static ProductDatabase productDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                    // Waiting for data to load...
                }

                handler.post(new Runnable() {
                    @Override
                    public void run()
                    {
                        loadingSpinner.setVisibility(View.GONE);

                        productList = productDatabase.productList;

                        itemsAdapter = new ItemsAdapter(productList);
                        products.setAdapter(itemsAdapter);
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
        inflater.inflate(R.menu.search_menu, menu);
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
