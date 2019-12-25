package com.sbl.foags.view;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class RecycleSimpleDraweeView extends SimpleDraweeView {

    private Uri uri;

    public RecycleSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public RecycleSimpleDraweeView(Context context) {
        super(context);
    }

    public RecycleSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecycleSimpleDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageUrl(String url) {
        // 没有显示图片，加载图片
        if (uri == null && !TextUtils.isEmpty(url)) {
            uri = Uri.parse(url);
            setImageURI(uri);
        }
        // 判断是否是相同图片
        else if (!TextUtils.isEmpty(url)) {
            Uri newUri = Uri.parse(url);
            if (!uri.equals(newUri)) {
                uri = newUri;
                setImageURI(uri);
            }
        } else {
            uri = null;
            setImageURI((Uri) null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            Log.d("RecycleSimpleDraweeView", "RecycleSimpleDraweeView  -> onDraw() Canvas: trying to use a recycled bitmap");
            e.printStackTrace();
        }
    }

}
