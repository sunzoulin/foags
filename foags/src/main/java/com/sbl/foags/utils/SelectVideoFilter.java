package com.sbl.foags.utils;

import android.content.Context;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.util.HashSet;
import java.util.Set;


public class SelectVideoFilter extends Filter {

    private long mMaxSize;
    private long mMaxDuration;
    private String tipContent;

    public SelectVideoFilter(long mMaxDuration, String tipContent) {
        this.mMaxDuration = mMaxDuration;
        this.mMaxSize = 0;
        this.tipContent = tipContent;
    }

    public SelectVideoFilter(long mMaxDuration, long maxSizeInBytes, String tipContent) {
        this.mMaxDuration = mMaxDuration;
        this.mMaxSize = maxSizeInBytes;
        this.tipContent = tipContent;
    }

    @Override
    protected Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {{
            add(MimeType.MP4);
        }};
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;
        if (item.duration > mMaxDuration) {
            return new IncapableCause(IncapableCause.DIALOG, tipContent);
        } else if (item.size > mMaxSize && mMaxSize > 0) {
            return new IncapableCause(IncapableCause.DIALOG, tipContent);
        }
        return null;
    }
}
