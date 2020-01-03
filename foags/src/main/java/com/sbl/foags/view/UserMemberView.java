package com.sbl.foags.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sbl.foags.R;
import com.sbl.foags.utils.UIUtils;

public class UserMemberView extends LinearLayout {

    private int textSize = 0;

    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingRight = 0;
    private int paddingBottom = 0;
    private int drawablePadding = 0;


    private ImageView memberIconView;
    private TextView memberTextView;

    public UserMemberView(Context context) {
        super(context);
    }

    public UserMemberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public UserMemberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public UserMemberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_user_member_layout, this);
        memberTextView = findViewById(R.id.memberTextView);
        memberIconView = findViewById(R.id.memberIconView);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UserMemberView);
        textSize = typedArray.getDimensionPixelSize(R.styleable.UserMemberView_textSize, UIUtils.dip2px(10));
        paddingLeft = typedArray.getDimensionPixelSize(R.styleable.UserMemberView_paddingLeft, UIUtils.dip2px(5));
        paddingTop = typedArray.getDimensionPixelSize(R.styleable.UserMemberView_paddingTop, UIUtils.dip2px(0));
        paddingRight = typedArray.getDimensionPixelSize(R.styleable.UserMemberView_paddingRight, UIUtils.dip2px(8));
        paddingBottom = typedArray.getDimensionPixelSize(R.styleable.UserMemberView_paddingBottom, UIUtils.dip2px(0));
        drawablePadding = typedArray.getDimensionPixelSize(R.styleable.UserMemberView_drawablePadding, UIUtils.dip2px(3));
        typedArray.recycle();





        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        LayoutParams lp = (LinearLayout.LayoutParams) memberTextView.getLayoutParams();
        lp.leftMargin = drawablePadding;
        memberTextView.setLayoutParams(lp);
        memberTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }


    public void setLevel(int level) {
        if (level > 0) {

            int icon = R.drawable.ic_use_1;
            if (level <= 100) {

            }
            memberIconView.setImageResource(icon);
            memberTextView.setText("" + level);
            setBackgroundResource(R.drawable.bg_user_member);
        }
    }

}
