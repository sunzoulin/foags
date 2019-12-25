package com.sbl.foags.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sbl.foags.R;
import com.facebook.drawee.view.AspectRatioMeasure;


public class MyAspectRatioFrameLayout extends FrameLayout {

    private final AspectRatioMeasure.Spec mMeasureSpec = new AspectRatioMeasure.Spec();
    private float mAspectRatio = 0;

    public MyAspectRatioFrameLayout(Context context) {
        super(context);
    }

    public MyAspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        analysisAttrs(attrs);
    }

    public MyAspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        analysisAttrs(attrs);
    }

    private void analysisAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray gdhAttrs = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.MyAspectRatioFrameLayout);
            final int indexCount = gdhAttrs.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                final int attr = gdhAttrs.getIndex(i);
                if (attr == R.styleable.MyAspectRatioFrameLayout_myFrameAspectRatio) {
                    mAspectRatio = gdhAttrs.getFloat(attr, 0);
                }
            }
            gdhAttrs.recycle();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureSpec.width = widthMeasureSpec;
        mMeasureSpec.height = heightMeasureSpec;
        AspectRatioMeasure.updateMeasureSpec(
                mMeasureSpec,
                mAspectRatio,
                getLayoutParams(),
                getPaddingLeft() + getPaddingRight(),
                getPaddingTop() + getPaddingBottom());
        super.onMeasure(mMeasureSpec.width, mMeasureSpec.height);
    }

    public void setAspectRatio(float aspectRatio) {
        if (aspectRatio == mAspectRatio) {
            return;
        }
        mAspectRatio = aspectRatio;
        requestLayout();
    }
}
