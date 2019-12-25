package com.sbl.foags.cube.factory.cell.moment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sbl.foags.R;
import com.sbl.foags.utils.GlideRoundTransform;
import com.sbl.foags.utils.UIUtils;


public class MomentVideoView extends FrameLayout {

    private ImageView singleCoverView;
    private ImageView playView;

    private String videoUrl;
    private MomentContentViewListener listener;

    public MomentVideoView(Context context,
                           String videoUrl,
                           MomentContentViewListener listener) {
        super(context);

        this.listener = listener;
        this.videoUrl = videoUrl;

        initViews();
    }

    public MomentVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MomentVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews() {

        LayoutInflater.from(getContext()).inflate(R.layout.content_view_moment_video_layout, this);
        singleCoverView = findViewById(R.id.singleCoverView);
        playView = findViewById(R.id.playView);

        Glide.with(getContext()).load(videoUrl).transform(new GlideRoundTransform(UIUtils.dip2px(6f))).into(singleCoverView);

        playView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickMomentVideoPlay();
            }
        });
    }
}
