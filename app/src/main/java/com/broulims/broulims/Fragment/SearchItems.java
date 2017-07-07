package com.broulims.broulims.Fragment;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.broulims.broulims.Item;
import com.broulims.broulims.ItemsAdapter;
import com.broulims.broulims.ProductDatabase;
import com.broulims.broulims.R;

import java.util.ArrayList;
import java.util.List;

import static com.broulims.broulims.FragmentHolder.dpToPx;
import static com.broulims.broulims.FragmentHolder.hideBottom;
import static com.broulims.broulims.FragmentHolder.showBottom;

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
public class SearchItems extends Fragment  {

    RecyclerView products;
    ItemsAdapter itemsAdapter;
    List<Item> productList = new ArrayList<>();
    ProgressBar loadingSpinner;
    EditText search;
    Handler handler;
    String newText;
    public static ProductDatabase productDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_database_view, container, false);

        productDatabase = new ProductDatabase();
        search = (EditText) view.findViewById(R.id.search_box);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = view.getRootView().getHeight() - view.getHeight();
                if (heightDiff > dpToPx(getContext(), 200)) {
                    hideBottom();
                }
                else
                {
                    showBottom();
                    //Log.i("key", "notshowing");
                }

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                if (search.getText().length() == 0)
                    products.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (products.getVisibility() == View.GONE && search.getText().length() != 0)
                    products.setVisibility(View.VISIBLE);
                else if (products.getVisibility() == View.VISIBLE && search.getText().length() == 0)
                    products.setVisibility(View.GONE);

                if (products.getVisibility() == View.VISIBLE)
                {
                    newText = String.valueOf(s).toLowerCase();
                    final ArrayList<Item> newList = new ArrayList<>();
                    if (itemsAdapter != null)
                    {
                        for (Item i : productList)
                        {
                            String name = i.getItemDescription().toLowerCase();
                            if (name.contains(newText))
                                newList.add(i);
                        }
                        itemsAdapter.setFilter(newList);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (search.getText().length() == 0)
                    products.setVisibility(View.GONE);
            }
        });
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

        //setHasOptionsMenu(true);

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

                        itemsAdapter = new ItemsAdapter(getActivity(),productList);
                        products.setAdapter(itemsAdapter);
                    }
                });
            }
        };

        new Thread(runnable).start();
    }
}
