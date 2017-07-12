package com.broulims.broulims.Fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.broulims.broulims.Item;
import com.broulims.broulims.ItemsAdapter;
import com.broulims.broulims.ProductDatabase;
import com.broulims.broulims.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    ArrayList<Item> viewItemsList;
    ProgressBar loadingSpinner;
    EditText search;
    LinearLayout sortingLayout;
    LinearLayout choosePriceLayout;
    ToggleButton sortName;
    ToggleButton sortBrand;
    ToggleButton sortPrice;
    ToggleButton priceLowToHigh;
    ToggleButton priceHighToLow;
    ImageView upArrow;
    ImageView downArrow;
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
        sortingLayout = (LinearLayout) view.findViewById(R.id.sortByLayout);
        choosePriceLayout = (LinearLayout) view.findViewById(R.id.choosePriceLayout);
        upArrow = (ImageView) view.findViewById(R.id.pullUp);
        downArrow = (ImageView) view.findViewById(R.id.dropDown);
        sortName = (ToggleButton) view.findViewById(R.id.sortName);
        sortBrand = (ToggleButton) view.findViewById(R.id.sortBrand);
        sortPrice = (ToggleButton) view.findViewById(R.id.sortPrice);
        priceHighToLow = (ToggleButton) view.findViewById(R.id.highToLow);
        priceLowToHigh = (ToggleButton) view.findViewById(R.id.lowToHigh);
        viewItemsList = new ArrayList<>();

        initializeSortingButtons();

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
                    viewItemsList.clear();
                    if (itemsAdapter != null)
                    {
                        for (Item i : productList)
                        {
                            String name = i.getItemDescription().toLowerCase();
                            if (name.contains(newText))
                                viewItemsList.add(i);
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run()
                            {
                                viewItemsList = sortItemsList(viewItemsList);
                                itemsAdapter.setFilter(viewItemsList);
                            }
                        });
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

    private ArrayList<Item> sortItemsList(ArrayList<Item> itemsList)
    {
        if (sortName.isChecked())
        {
            Collections.sort(itemsList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2)
                {
                    return o1.getItemDescription().compareTo(o2.getItemDescription());
                }
            });
        }
        else if (sortBrand.isChecked())
        {
            Collections.sort(itemsList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2)
                {
                    return o1.getItemDescription().compareTo(o2.getItemDescription());
                }
            });
        }
        else if (priceLowToHigh.isChecked())
        {
            Collections.sort(itemsList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2)
                {
                    String price1String = o1.getBasePrice().replace("$", "");
                    String price2String = o2.getBasePrice().replace("$", "");

                    Float price1 = Float.parseFloat(price1String);
                    Float price2 = Float.parseFloat(price2String);

                    return price1.compareTo(price2);
                }
            });
        }
        else if (priceHighToLow.isChecked())
        {
            Collections.sort(itemsList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2)
                {
                    String price1String = o1.getBasePrice().replace("$", "");
                    String price2String = o2.getBasePrice().replace("$", "");

                    Float price1 = Float.parseFloat(price1String);
                    Float price2 = Float.parseFloat(price2String);

                    return price1.compareTo(price2);
                }
            });

            Collections.reverse(itemsList);
        }

        return itemsList;
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

    public void dropDownSortingOptions(View view)
    {
        if (view.getId() == R.id.dropDown)
        {
            choosePriceLayout.setVisibility(view.GONE);
            sortingLayout.animate().translationYBy(70f).setDuration(130);
            products.animate().translationYBy(70f).setDuration(130);
            setButtonEnables(true);

            if (priceHighToLow.isChecked() || priceLowToHigh.isChecked())
                sortPrice.setChecked(true);

            upArrow.setVisibility(View.VISIBLE);
            downArrow.setVisibility(View.GONE);
        }
        else if (view.getId() == R.id.pullUp)
        {
            if (sortingLayout.getVisibility() == View.GONE)
            {
                sortingLayout.setVisibility(View.VISIBLE);
                choosePriceLayout.setVisibility(View.GONE);
            }

            setButtonEnables(false);
            sortingLayout.animate().translationYBy(-70f).setDuration(130);
            products.animate().translationYBy(-70f).setDuration(130);
            upArrow.setVisibility(View.GONE);
            downArrow.setVisibility(View.VISIBLE);
        }
    }

    private void setButtonEnables(boolean enable)
    {
        sortName.setEnabled(enable);
        sortBrand.setEnabled(enable);
        sortPrice.setEnabled(enable);
    }


    public void showPriceOptions(View view)
    {
        choosePriceLayout.setVisibility(View.VISIBLE);
        sortingLayout.setVisibility(View.GONE);
        sortPrice.setChecked(false);
    }

    public void showSortLayout(View view)
    {
        choosePriceLayout.setVisibility(View.GONE);
        sortingLayout.setVisibility(View.VISIBLE);
    }

    private void initializeSortingButtons()
    {
        sortName.setText("Name"); sortName.setTextOff("Name"); sortName.setTextOn("Name");
        sortBrand.setText("Brand"); sortBrand.setTextOff("Brand"); sortBrand.setTextOn("Brand");
        sortPrice.setText("Price"); sortPrice.setTextOff("Price"); sortPrice.setTextOn("Price");
        priceLowToHigh.setText("Low to High"); priceLowToHigh.setTextOff("Low to High"); priceLowToHigh.setTextOn("Low to High");
        priceHighToLow.setText("High to Low"); priceHighToLow.setTextOff("High to Low"); priceHighToLow.setTextOn("High to Low");

        sortName.setChecked(true);

        sortingLayout.setVisibility(View.VISIBLE);
        sortingLayout.setY(-70f);
    }

    public void sortBy(View view)
    {
        switch(view.getId())
        {
            case R.id.sortName:
                sortName.setChecked(true);
                sortBrand.setChecked(false);
                sortPrice.setChecked(false);
                priceHighToLow.setChecked(false);
                priceLowToHigh.setChecked(false);
                break;
            case R.id.sortBrand:
                sortName.setChecked(false);
                sortBrand.setChecked(true);
                sortPrice.setChecked(false);
                priceHighToLow.setChecked(false);
                priceLowToHigh.setChecked(false);
                break;
            case R.id.lowToHigh:
                sortName.setChecked(false);
                sortBrand.setChecked(false);
                sortPrice.setChecked(true);
                priceHighToLow.setChecked(false);
                priceLowToHigh.setChecked(true);
                break;
            case R.id.highToLow:
                sortName.setChecked(false);
                sortBrand.setChecked(false);
                sortPrice.setChecked(true);
                priceHighToLow.setChecked(true);
                priceLowToHigh.setChecked(false);
                break;
        }

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
