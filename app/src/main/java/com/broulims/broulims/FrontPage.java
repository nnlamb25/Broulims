package com.broulims.broulims;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.broulims.broulims.Fragment.BroulimsMap;
import com.broulims.broulims.Fragment.WebsiteView;
import com.broulims.broulims.Fragment.search_test;

/**
 * This is the first page that loads when opening the app.
 * The front page loads the
 */

public class FrontPage extends AppCompatActivity {
    public static ProductDatabase productDatabase;
    private ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    // Fragments
    WebsiteView websiteView;
    BroulimsMap broulimsMap;
    search_test searchTest;

    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        Intent appLinkIntent = getIntent();
        //String appLinkAction = appLinkIntent.getAction();
        //Uri appLinkData = appLinkIntent.getData();
        productDatabase = new ProductDatabase();

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.Bottombaritemone:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.Bottombaritemtwo:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.Bottombaritemothree:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                // do nothing
            }

            @Override
            public void onPageSelected(int position)
            {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                menuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                // do nothing
            }
        });

        // Disable ViewPager Swipe
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        websiteView=new WebsiteView();
        broulimsMap=new BroulimsMap();
        searchTest=new search_test();
        adapter.addFragment(websiteView);
        adapter.addFragment(broulimsMap);
        adapter.addFragment(searchTest);
        viewPager.setAdapter(adapter);
    }
}