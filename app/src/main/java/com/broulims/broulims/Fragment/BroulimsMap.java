/* Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.broulims.broulims.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.broulims.broulims.Item;
import com.broulims.broulims.mapItemsAdapter;
import com.broulims.broulims.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


/**
 * This is the map activity for the app. It is accessible from the
 * bottom navigation and, when opened, goes to a google maps view
 * showing the store map. It will eventually have additional user
 * interface allowing the user to press a section of the map to
 * receive information about it, and the search function will
 * open the map and display a pop-up to show the location of an
 * item.
 * <br></br>
 * There is also a bottom navigation bar which you can use to
 * start our other main activities (such as the map view or list view)
 * <p></p>
 * Had help from:
 * https://developers.google.com/maps/documentation/android-api/
 *
 * @author Adam
 */
public class BroulimsMap extends Fragment
{

    private static WebView webView;
    private static List<Item> products;
    private static RecyclerView productsView;
    private static mapItemsAdapter mapItemsAdapter;
    private static ImageView openSideBar;
    private static ImageView closeSideBar;
    private static TextView clearItems;
    private static RelativeLayout sideMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.broulims_map, container, false);
        products = new ArrayList<>();

        openSideBar = (ImageView) view.findViewById(R.id.openSideMenu);
        closeSideBar = (ImageView) view.findViewById(R.id.closeSideMenu);
        clearItems = (TextView) view.findViewById(R.id.clearButton);
        sideMenu = (RelativeLayout) view.findViewById(R.id.sideMenu);

        sideMenu.setTranslationX(300
        );

        // Load the webpage for the map, yeah say goodbye to google maps
        webView = (WebView) view.findViewById(R.id.map_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/broulimsmap.html");

        productsView = (RecyclerView) view.findViewById(R.id.Items);
        productsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productsView.setItemAnimator(new DefaultItemAnimator());
        productsView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));

        mapItemsAdapter = new mapItemsAdapter(getActivity(), products);

        // Inflate the layout for this fragment
        return view;
    }

    /*******************************************
     * addToList()
     * adds an item from the side list
     ********************************************/
    public static void addToList(Context context, Item item){
            products.add(item);
            Log.i("Added", item.getItemDescription());
            final int items = products.size();
            String count = " " + items;
            Log.i("count", count);

            loadList();
    }

    /*******************************************
     * removeFromList()
     * removes an item from the side list
     ********************************************/
    public static void removeFromList(Item item)
    {
        products.remove(item);

        Log.i("Added", item.getItemDescription());

        final int items = products.size();
        String count = " " + items;
        Log.i("count", count);

        loadList();
    }

     /*******************************************
      * loadList()
      * this functions takes all the items in the list
      * and loads it to the map using javascript.
     ********************************************/
    public static void loadList(){
        webView.loadUrl("file:///android_asset/broulimsmap.html");
        String line = "";
        mapItemsAdapter.setFilter(products);
        productsView.setAdapter(mapItemsAdapter);

        for (int i = 0; i < products.size(); i++)
        {
            final int item = i;
            line = line + "javascript:createMarkers('" + products.get(item).getAisle() + products.get(item).getSide() + "' ,\" " +  products.get(item).getItemDescription() + "\");";
            Log.i("script", line);
        }

        final String URL = line;
        webView.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onPageFinished(WebView view, String url)
            {
                webView.evaluateJavascript(URL,null);
            }
        });
    }

    /*******************************************
     * alreadyInList()
     * keeps track if an item has already been selected
     ********************************************/
    public static boolean alreadyInList(Item item)
    {
        if (products.contains(item))
            return true;
        else
            return false;
    }


    public static void openMenu(View view)
    {
        sideMenu.animate().translationXBy(-1 * productsView.getWidth()).setDuration(200);
        webView.animate().translationXBy(-300).setDuration(200);
        openSideBar.setVisibility(GONE);
        openSideBar.setEnabled(false);
        clearItems.setEnabled(true);
        closeSideBar.setEnabled(true);
    }

    public static void closeMenu(View view)
    {
        sideMenu.animate().translationXBy(productsView.getWidth()).setDuration(200);
        webView.animate().translationXBy(300).setDuration(200);
        openSideBar.setVisibility(View.VISIBLE);
        openSideBar.setEnabled(true);
        clearItems.setEnabled(false);
        closeSideBar.setEnabled(false);
    }

    public static void clearItems(View view)
    {
        products.clear();
        loadList();
    }

}
