package com.sbl.foags.utils;

import android.content.Context;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.util.HashSet;
import java.util.Set;


public class SelectPhotoFilter extends Filter {

    private long mMaxSize;
    private String tipContent;

    public SelectPhotoFilter(long maxSizeInBytes, String tipContent) {
        this.mMaxSize = maxSizeInBytes;
        this.tipContent = tipContent;
    }

    @Override
    protected Set<MimeType> constraintTypes() {
        return new HashSet<MimeType>() {
            private static final long serialVersionUID = 506254628804563815L;

            {
                add(MimeType.JPEG);
                add(MimeType.PNG);
                add(MimeType.BMP);
                add(MimeType.WEBP);
            }
        };
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;
        if (item.size > mMaxSize) {
            return new IncapableCause(IncapableCause.DIALOG, tipContent);
        }
        return null;
    }
}
