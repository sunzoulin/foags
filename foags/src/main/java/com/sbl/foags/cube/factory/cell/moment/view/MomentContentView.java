package com.sbl.foags.cube.factory.cell.moment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sbl.foags.cube.bean.MomentContentType;

import java.util.ArrayList;


public class MomentContentView extends FrameLayout {

    private View v = null;

    public MomentContentView(@NonNull Context context) {
        super(context);
    }

    public MomentContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MomentContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MomentContentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void showContent(MomentContentType contentType,
                          ArrayList<String> photos,
                          String video,
                          MomentContentViewListener listener) {

        if (contentType == MomentContentType.PHOTO) {

            if(photos != null && !photos.isEmpty()) {
                switch (photos.size()) {
                    case 1:
                        v = new MomentSinglePhotoView(getContext(), photos.get(0), listener);
                        break;
                    default:
                        v = new MomentMultiPhotoView(getContext(), photos, listener);
                        break;
                }
            }
        } else if (contentType == MomentContentType.VIDEO) {
            if(!video.isEmpty()) {
                v = new MomentVideoView(getContext(), video, listener);
            }
        }
        if (v != null) {
            addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

}
