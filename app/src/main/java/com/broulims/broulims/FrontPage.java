package com.broulims.broulims;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.broulims.broulims.Fragment.BroulimsMap;
import com.broulims.broulims.Fragment.WebsiteView;
import com.broulims.broulims.Fragment.SearchItems;

/**
 * This is the primary page which holds all the fragments.
 * This android studio program on github helped a ton
 * https://github.com/jaisonfdo/BottomNavigation
 */

public class FrontPage extends AppCompatActivity {
    private ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    // Fragments
    WebsiteView websiteView;
    BroulimsMap broulimsMap;
    SearchItems searchItems;

    MenuItem menuItem;
    ImageView splashScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        getSupportActionBar().hide();

        Intent appLinkIntent = getIntent();
        //String appLinkAction = appLinkIntent.getAction();
        //Uri appLinkData = appLinkIntent.getData();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        splashScreen = (ImageView) findViewById(R.id.SplashScreen);
        viewPager.setOffscreenPageLimit(2);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                splashScreen.animate().alpha(0f).setDuration(1000);
                viewPager.animate().alpha(1f).setDuration(1000);
                bottomNavigationView.animate().alpha(1f).setDuration(1000);
            }
        }, 2000);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.Bottombaritemone:
                        getSupportActionBar().hide();
                        if (viewPager.getCurrentItem() != 0)
                            viewPager.setCurrentItem(0);
                        else
                        {
                            if (websiteView.getCurrentURL() != websiteView.getHomePage())
                            {
                                websiteView.setURL(websiteView.getHomePage());
                            }
                        }
                        break;
                    case R.id.Bottombaritemtwo:
                        getSupportActionBar().hide();
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.Bottombaritemothree:
                        getSupportActionBar().show();
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
        searchItems=new SearchItems();
        adapter.addFragment(websiteView);
        adapter.addFragment(broulimsMap);
        adapter.addFragment(searchItems);
        viewPager.setAdapter(adapter);
    }
}