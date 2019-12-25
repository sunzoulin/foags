package com.sbl.foags.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;


public class CanNotScrollViewPager extends ViewPager {

    private boolean canScrollHorizontal;

    public CanNotScrollViewPager(Context context) {
        super(context);
    }

    public CanNotScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScrollHorizontal && super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScrollHorizontal && super.onTouchEvent(ev);
    }

    public void setCanScrollHorizontal(boolean canScrollHorizontal) {
        this.canScrollHorizontal = canScrollHorizontal;
    }
}