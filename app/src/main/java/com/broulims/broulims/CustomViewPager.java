package com.broulims.broulims;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by NathanLamb on 7/3/17.
 * Had help from
 * https://stackoverflow.com/questions/31000076/how-to-make-swipe-disable-in-a-view-pager-in-android
 */

class CustomViewPager extends ViewPager
{
    public CustomViewPager(Context context)
    {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        return false;
    }
}
