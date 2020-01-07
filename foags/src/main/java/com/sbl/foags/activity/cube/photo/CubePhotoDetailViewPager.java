package com.sbl.foags.activity.cube.photo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;


public class CubePhotoDetailViewPager extends ViewPager {

    private int startX;

    private int criticalValue = 200;

    private onSideListener mOnSideListener;

    public CubePhotoDetailViewPager(Context context) {
        this(context, null);
    }

    public CubePhotoDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnSideListener(onSideListener listener) {
        this.mOnSideListener = listener;
    }


    public void setCriticalValue(int criticalValue) {
        this.criticalValue = criticalValue;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    break;
            }
            return super.dispatchTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (startX - event.getX() > criticalValue && (getCurrentItem() == getAdapter().getCount() - 1)) {
                        if (null != mOnSideListener) {
                            mOnSideListener.onRightSide();
                        }
                    }
                    if ((event.getX() - startX) > criticalValue && (getCurrentItem() == 0)) {
                        if (null != mOnSideListener) {
                            mOnSideListener.onLeftSide();
                        }
                    }
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public interface onSideListener {

        void onLeftSide();

        void onRightSide();
    }
}
