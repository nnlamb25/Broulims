package com.broulims.broulims;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Made by Nathan
 * This activity is a web view that views Broulim's website.  There's a bottom navigation bar which you can use to
 * start our other main activites (such as the map view or list view)
 * Had help from
 * http://www.codebind.com/android-tutorials-and-examples/convert-website-android-application-using-android-studio/
 */

public class WebsiteView extends AppCompatActivity {
    private WebView webView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        Menu menu = bottomNavigationView.getMenu();

        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Bottombaritemone:

                        break;

                    case R.id.Bottombaritemtwo:
                        startActivity(new Intent(WebsiteView.this, BroulimsMap.class));
                        break;

                    case R.id.Bottombaritemothree:
                        startActivity(new Intent(WebsiteView.this, search_test.class));
                        break;

                }


                return false;
            }
        });
        webView = (WebView) findViewById(R.id.website_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                findViewById(R.id.splash).animate().alpha(0f).setDuration(800);
                webView.animate().alpha(1f).setDuration(800);
            }
        });
        webView.loadUrl("https://broulims.com/");

        // Sets up the bottom navigation bar
        /*
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.home: // The user presses the home page button
                        if (webView.getUrl().toString() != "https://broulims.com/")
                        {
                            Log.i("URL", webView.getUrl().toString());
                            Log.i("URL == broulims", String.valueOf((webView.getUrl().toString() != webView.getOriginalUrl().toString())));
                            webView.loadUrl("https://broulims.com/");
                        }
                        break;
                    case R.id.list: // The user presses the list button
                        startActivity(new Intent(getApplicationContext(), IndoorDemoActivity.class));
                        break;
                    case R.id.search: // The user presses the search button
                        startActivity(new Intent(getApplicationContext(), search_test.class));
                }

                return true;
            }
        });
    }

    // The back button at the bottom is overwritten so it works as a back button on the web page
    @Override
    public void onBackPressed()
    {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }*/
    }
}
