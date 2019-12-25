package com.sbl.foags.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import com.sbl.foags.R;


public class TableView extends RelativeLayout implements Checkable {

//    private static Animation anim;

    private CheckedTextView mTitle;
    private UnreadCountTextView mUnread;
    private View mRedPoint;

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化View
        LayoutInflater.from(context).inflate(R.layout.table_view, this);
        initView();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TableView);
        mTitle.setText(typedArray.getString(R.styleable.TableView_text));
        mTitle.setTextColor(typedArray.getColorStateList(R.styleable.TableView_textColor));
        Drawable drawable = typedArray.getDrawable(R.styleable.TableView_drawable);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        mTitle.setCompoundDrawables(null, drawable, null, null);
        typedArray.recycle();
//        anim = AnimationUtils.loadAnimation(context, R.anim.anim_main_tab_select);
    }

    private void initView() {
        mTitle = findViewById(R.id.tab_title);
        mUnread = findViewById(R.id.tab_unread);
        mRedPoint = findViewById(R.id.tab_red_point);
        setUnread(0);
        showRedPoint(false);
    }

    @Override
    public boolean isChecked() {
        return mTitle.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
//        if (checked) {
//            startAnimation(anim);
//        } else {
//            clearAnimation();
//        }
        mTitle.setChecked(checked);
    }

    @Override
    public void toggle() {
        mTitle.setChecked(!mTitle.isChecked());
    }

    public void showRedPoint(boolean show) {
        mRedPoint.setVisibility(show ? VISIBLE : GONE);
    }

    public void setUnread(int value) {
        mUnread.setValue(value);
        if (value <= 0) {
            mUnread.setVisibility(GONE);
        } else {
            mUnread.setVisibility(VISIBLE);
        }
    }
}
