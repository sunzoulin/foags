package com.sbl.foags.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sbl.foags.R;
import com.sbl.foags.utils.UIUtils;

public class UserLevelView extends LinearLayout {

    private int textSize = 0;

    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;

    private TextView levelTextView;

    public UserLevelView(Context context) {
        super(context);
    }

    public UserLevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public UserLevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public UserLevelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }


    private void initView(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.view_user_level_layout, this);
        levelTextView = findViewById(R.id.levelTextView);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UserLevelView);
        textSize = typedArray.getDimensionPixelSize(R.styleable.UserLevelView_textSize, UIUtils.dip2px(10));
        paddingLeft = typedArray.getDimensionPixelSize(R.styleable.UserLevelView_paddingLeft, UIUtils.dip2px(5));
        paddingTop = typedArray.getDimensionPixelSize(R.styleable.UserLevelView_paddingTop, UIUtils.dip2px(1));
        paddingRight = typedArray.getDimensionPixelSize(R.styleable.UserLevelView_paddingRight, UIUtils.dip2px(5));
        paddingBottom = typedArray.getDimensionPixelSize(R.styleable.UserLevelView_paddingBottom, UIUtils.dip2px(1));
        typedArray.recycle();


        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        levelTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }


    public void setLevel(int level){
        if(level > 0){

            int textColor = UIUtils.getColor(R.color.color_FF5E99);
            if(level <= 100){

            }

            setBackground(getLevelBgDrawable(textColor));
            levelTextView.setTextColor(textColor);
            levelTextView.setText("LV" + level);
        }
    }

    @SuppressLint("WrongConstant")
    private GradientDrawable getLevelBgDrawable(int Color){
        GradientDrawable gd = new GradientDrawable();
        gd.setGradientType(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(UIUtils.dip2px(360f));
        gd.setStroke(UIUtils.dip2px(1f), Color);
        return gd;
    }
}
