package com.broulims.broulims.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.broulims.broulims.R;

/**
 * Made by Nathan
 * This activity is a web view that views Broulim's website.  There's a bottom navigation bar which you can use to
 * start our other main activites (such as the map view or list view)
 * Had help from
 * http://www.codebind.com/android-tutorials-and-examples/convert-website-android-application-using-android-studio/
 */

public class WebsiteView extends Fragment
{
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.activity_website_view, container, false);
        webView = (WebView) view.findViewById(R.id.website_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://broulims.com/");
        //getActivity().getActionBar().hide();
        // Inflate the layout for this fragment
        return view;
    }
}
