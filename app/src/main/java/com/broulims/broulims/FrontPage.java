package com.broulims.broulims;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FrontPage extends AppCompatActivity {
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



    public void showDB(View view) {
        Intent intent = new Intent(this, DatabaseView.class);
        startActivity(intent);
    }


    public void theSearch(View view) {
        Intent intent = new Intent(this, search_test.class);
        startActivity(intent);
    }

    public void viewMap(View view) {

        startActivity(new Intent(this, IndoorDemoActivity.class));
    }

    public void webView(View view)
    {
        startActivity(new Intent(this, WebsiteView.class));
    }

}