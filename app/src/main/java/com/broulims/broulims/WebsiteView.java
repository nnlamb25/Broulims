package com.broulims.broulims;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Had help from
 * http://www.codebind.com/android-tutorials-and-examples/convert-website-android-application-using-android-studio/
 */

public class WebsiteView extends AppCompatActivity
{
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);

        webView = (WebView) findViewById(R.id.website_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://broulims.com/");
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed()
    {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.product_search:
                startActivity(new Intent(this, search_test.class));
                break;
            case R.id.product_map:
                startActivity(new Intent(this, IndoorDemoActivity.class));
                break;
        }

        return true;
    }
}
