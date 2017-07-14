package com.broulims.broulims;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.broulims.broulims.Fragment.BroulimsMap;
import com.broulims.broulims.Fragment.WebsiteView;
import com.broulims.broulims.Fragment.SearchItems;

import static com.broulims.broulims.Fragment.BroulimsMap.loadList;

/**
 * This is the front page of the app, which contains
 * the bottom navigation and holds all the fragments.
 * <br></br>
 * This android studio program on github helped a ton
 * https://github.com/jaisonfdo/BottomNavigation
 */

public class FragmentHolder extends AppCompatActivity {
    protected static CustomViewPager viewPager;
    protected static BottomNavigationView bottomNavigationView;

    // Fragments
    WebsiteView websiteView;
    Context context;
    BroulimsMap broulimsMap;
    SearchItems searchItems;

    MenuItem menuItem;
    ImageView splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder);

        context = this;
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
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
                        //getSupportActionBar().hide();
                        if (viewPager.getCurrentItem() != 0)
                            viewPager.setCurrentItem(0);
                        else if (websiteView.getCurrentURL() != websiteView.getHomePage())
                            websiteView.setURL(websiteView.getHomePage());
                        break;
                    case R.id.Bottombaritemtwo:
                        viewPager.setCurrentItem(1);
                        loadList();
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
        searchItems=new SearchItems();
        adapter.addFragment(websiteView);
        adapter.addFragment(broulimsMap);
        adapter.addFragment(searchItems);
        viewPager.setAdapter(adapter);
    }

    static public void viewMap()
    {
        viewPager.setCurrentItem(1);
    }


    static public void showBottom() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    static public void hideBottom() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static void showKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public void dropDownSortingOptions(View view)
    {
        searchItems.dropDownSortingOptions(view);
    }

    public void showPriceOptions(View view)
    {
        searchItems.showPriceOptions(view);
    }

    public void showSortLayout(View view)
    {
        searchItems.showSortLayout(view);
    }

    public void sortBy(View view)
    {
        searchItems.sortBy(view);
    }

    public void showItems(View view)
    {
        broulimsMap.showItems(view);
    }
}