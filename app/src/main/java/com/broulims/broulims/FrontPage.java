package com.broulims.broulims;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FrontPage extends AppCompatActivity {
    /*
    public static final String userString = "com.broulims.userString";
    protected static ProductDatabase productDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        productDatabase = new ProductDatabase();
    }


    public void theSearch(View view) {
        startActivity(new Intent(this, search_test.class));
    }

    public void viewMap(View view) {

        startActivity(new Intent(this, IndoorDemoActivity.class));
    }

    public void webView(View view)
    {
        startActivity(new Intent(this, WebsiteView.class));
    }
    */

    private WebView webView;
    protected static ProductDatabase productDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);
        Intent appLinkIntent = getIntent();
        //String appLinkAction = appLinkIntent.getAction();
        //Uri appLinkData = appLinkIntent.getData();
        productDatabase = new ProductDatabase();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
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
                        Intent intent2 = new Intent(FrontPage.this, IndoorDemoActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.Bottombaritemothree:
                        Intent intent3 = new Intent(FrontPage.this, search_test.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });
        webView = (WebView) findViewById(R.id.website_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://broulims.com/");
        webView.setWebViewClient(new WebViewClient());

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
    }
    */

    }
}