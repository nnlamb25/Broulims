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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.broulims.broulims.Item;
import com.broulims.broulims.R;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.broulims_map, container, false);
        products = new ArrayList<>();

        final int items = products.size();

        // Load the webpage for the map, yeah say goodbye to google maps
        webView = (WebView) view.findViewById(R.id.map_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/broulimsmap.html");

        loadList();
        // Inflate the layout for this fragment
        return view;
    }

    public static void addToList(Item item){
        products.add(item);
        Log.i("Added", item.getItemDescription());

        final int items = products.size();
        String count = " " + items;
        Log.i("count", count);

        loadList();
    }

    public static void loadList(){
        for (int i = 0; i < products.size(); i++)
        {
            final int item = i;
            Log.i("aisle", products.get(item).getAisle().toString());
            webView.loadUrl("file:///android_asset/broulimsmap.html");
            webView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url)
                {
                    webView.loadUrl("javascript:createMarkers(" + products.get(item).getAisle() + ");");
                }
            });
        }



    }


}
