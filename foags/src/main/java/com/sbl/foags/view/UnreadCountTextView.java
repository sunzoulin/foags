package com.sbl.foags.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sbl.foags.R;
import com.sbl.foags.utils.DrawUtils;
import com.sbl.foags.utils.StringUtils;
import com.sbl.foags.utils.UIUtils;


public class UnreadCountTextView extends androidx.appcompat.widget.AppCompatTextView {

    private int mMaxValue = 99;
    private int mValue;
    private Paint mPaint;
    private int mBackgroundColor = UIUtils.getColor(R.color.color_FC483F);
    private int mBackgroundResource;
    private RectF rectF;

    public UnreadCountTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public UnreadCountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public UnreadCountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getBackground() == null && mPaint != null) {
            int w = getWidth();
            int h = getHeight();
            int r = w;

            if (r > h) {
                r = h;
            }
            // 避免重复创建RectF对象，造成对内存的浪费
            if (rectF == null) {
                rectF = new RectF(0, 0, getWidth(), getHeight());
            } else {
                rectF.set(0, 0, getWidth(), getHeight());
            }
            DrawUtils.drawRoundRect(true, true, true, true, getHeight() / 2, rectF, canvas, mPaint);
        }

        super.onDraw(canvas);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setPadding(DrawUtils.dip2px(getContext(), 3), 0, DrawUtils.dip2px(getContext(), 3), 0);
        setGravity(Gravity.CENTER);
        setIncludeFontPadding(false);
        setMaxLines(1);

        mPaint = new Paint();
        mPaint.setColor(mBackgroundColor);
        mPaint.setAntiAlias(true);

        if (attrs == null) {
            return;
        }

        //从 xml 布局文件读取控件属性设置
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UnreadCountTextView, defStyleAttr, defStyleRes);
        setMaxValue(ta.getInteger(R.styleable.UnreadCountTextView_maxValue, mMaxValue));
        // 自定义属性，必须回收，否则会造成内存泄漏
        ta.recycle();
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        if (mPaint != null) {
            mPaint.setColor(color);
            super.setBackgroundColor(Color.TRANSPARENT);
            invalidate();
        }
    }

    @Override
    public void setBackground(Drawable background) {
        if (background instanceof ColorDrawable) {
            setBackgroundColor(((ColorDrawable) background).getColor());
        } else {
            super.setBackground(background);
        }
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    private void setMaxValue(int maxValue) {
        this.mMaxValue = maxValue;
        refreshTextView();
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        this.mValue = value;
        refreshTextView();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (TextUtils.equals("99+", text.toString())) {
            return;
        }
        int value = StringUtils.str2Int(text.toString().trim());
        if (value != mValue) {
            mValue = value;
            refreshTextView();
        }
    }


    private void refreshTextView() {
        if (mValue > mMaxValue) {
            super.setText("99+");
        } else {
            super.setText(String.valueOf(mValue));
        }
    }
}
