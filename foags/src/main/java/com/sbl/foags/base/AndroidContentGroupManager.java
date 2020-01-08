package com.sbl.foags.base;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;


public class AndroidContentGroupManager {

    private FrameLayout baseContentGroup;

    public AndroidContentGroupManager(Activity activity) {
        baseContentGroup = new FrameLayout(activity);
        ViewGroup contentGroup = activity.findViewById(Window.ID_ANDROID_CONTENT);
        contentGroup.addView(baseContentGroup);
    }

    public void addLandScapeVideoView(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        baseContentGroup.addView(view, 0, params);
    }

    public void removeLandScapeVideoView(View view) {
        baseContentGroup.removeView(view);
    }
}
