package com.sbl.foags.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.annotation.Nullable;


public class CheckedTextView extends androidx.appcompat.widget.AppCompatTextView implements Checkable {

    private boolean isChecked;

    public CheckedTextView(Context context) {
        this(context, null);
    }

    public CheckedTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, new int[]{android.R.attr.state_checked});
        }
        return drawableState;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
        refreshDrawableState();
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                drawable.jumpToCurrentState();
            }
        }
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
