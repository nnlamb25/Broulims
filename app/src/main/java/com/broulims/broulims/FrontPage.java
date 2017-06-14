package com.broulims.broulims;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FrontPage extends AppCompatActivity {
    public static final String userString = "com.broulims.userString";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
    }

    public void callDB(View view) {
        Intent intent = new Intent(this, Database.class);
        startActivity(intent);
    }

    public void showDB(View view) {
        Intent intent = new Intent(this, DatabaseView.class);
        startActivity(intent);
    }


    public void theSearch(View view) {
        Intent intent = new Intent(this, search_test.class);
        startActivity(intent);
    }
}