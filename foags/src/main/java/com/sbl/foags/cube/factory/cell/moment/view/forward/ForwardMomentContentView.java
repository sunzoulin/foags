package com.sbl.foags.cube.factory.cell.moment.view.forward;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sbl.foags.cube.bean.WorkContentType;
import com.sbl.foags.cube.factory.cell.home.view.WorkAlbumView;
import com.sbl.foags.cube.factory.cell.home.view.WorkContentViewListener;
import com.sbl.foags.cube.factory.cell.home.view.WorkVideoView;

import java.util.ArrayList;


public class ForwardMomentContentView extends FrameLayout {

    private View v = null;

    public ForwardMomentContentView(@NonNull Context context) {
        super(context);
    }

    public ForwardMomentContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ForwardMomentContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ForwardMomentContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void showContent(WorkContentType contentType,
                            ArrayList<String> photosUrl,
                            int totalPhotoCount,
                            String videoUrl,
                            long duration,
                            WorkContentViewListener listener) {

        if (contentType == WorkContentType.PHOTO) {

            if(photosUrl != null && !photosUrl.isEmpty()){
                v = new WorkAlbumView(getContext(), photosUrl, totalPhotoCount, listener);
            }

        } else if (contentType == WorkContentType.VIDEO) {
            if(!videoUrl.isEmpty()) {
                v = new WorkVideoView(getContext(), videoUrl, duration, listener);
            }
        }
        if (v != null) {
            addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

}
