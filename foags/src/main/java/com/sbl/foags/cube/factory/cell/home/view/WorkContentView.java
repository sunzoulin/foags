package com.sbl.foags.cube.factory.cell.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sbl.foags.cube.bean.WorkContentType;

import java.util.ArrayList;


public class WorkContentView extends FrameLayout {

    private View v = null;

    public WorkContentView(@NonNull Context context) {
        super(context);
    }

    public WorkContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void showContent(WorkContentType contentType,
                            int totalPhotoCount,
                            ArrayList<String> photos,
                            String video,
                            long duration,
                            WorkContentViewListener listener) {

        if (contentType == WorkContentType.PHOTO) {

            if(photos != null && !photos.isEmpty()) {
                v = new WorkPhotoView(getContext(), photos, totalPhotoCount, listener);
            }
        } else if (contentType == WorkContentType.VIDEO) {
            if(!video.isEmpty()) {
                v = new WorkVideoView(getContext(), video, duration, listener);
            }
        }
        if (v != null) {
            addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

}
