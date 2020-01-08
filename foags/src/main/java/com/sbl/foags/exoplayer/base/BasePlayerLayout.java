package com.sbl.foags.exoplayer.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class BasePlayerLayout extends FrameLayout {

    public BasePlayerLayout(@NonNull Context context) {
        super(context);
    }

    public BasePlayerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePlayerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean needCache(String url) {
        return (!url.contains(".m3u8") && !url.contains(".ts")) && (url.contains("http") || url.contains("https"));
    }

}
