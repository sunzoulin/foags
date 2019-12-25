package com.sbl.foags.cube.factory.cell.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sbl.foags.R;
import com.sbl.foags.utils.GlideRoundTransform;
import com.sbl.foags.utils.UIUtils;


public class WorkVideoView extends FrameLayout {

    private ImageView videoCoverView;
    private ImageView playView;
    private TextView durationView;

    private String videoUrl;
    private long duration;
    private WorkContentViewListener listener;

    public WorkVideoView(Context context,
                         String videoUrl,
                         long duration,
                         WorkContentViewListener listener) {
        super(context);
        this.listener = listener;
        this.videoUrl = videoUrl;
        this.duration = duration;

        initViews();
    }

    public WorkVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews() {

        LayoutInflater.from(getContext()).inflate(R.layout.content_view_work_video_layout, this);

        videoCoverView = findViewById(R.id.videoCoverView);
        durationView = findViewById(R.id.durationView);
        playView = findViewById(R.id.playView);


        Glide.with(getContext()).load(videoUrl).transform(new GlideRoundTransform(UIUtils.dip2px(6f))).into(videoCoverView);
        if(duration <= 0){
            durationView.setVisibility(View.GONE);
        }else{
            durationView.setVisibility(View.VISIBLE);
            durationView.setText(getVideoTimeString(duration));
        }

        playView.setOnClickListener(v -> listener.onClickWorkVideoPlay());
    }

    private String getVideoTimeString(long duration) {
        long hourEnd = duration % (60 * 60 * 24) / (60 * 60);
        long minuteEnd = duration % (60 * 60) / 60;
        long secondEnd = duration % 60;

        long minuteResult;
        long secondResult;

        if (hourEnd > 0) {
            minuteResult = minuteEnd + hourEnd * 60;
            if (secondEnd > 0) {
                secondResult = secondEnd;
            } else {
                secondResult = 0;
            }
        } else if (minuteEnd > 0) {
            minuteResult = minuteEnd;
            if (secondEnd > 0) {
                secondResult = secondEnd;
            } else {
                secondResult = 0;
            }
        } else if (secondEnd > 0) {
            minuteResult = 0;
            secondResult = secondEnd;
        } else {
            minuteResult = 0;
            secondResult = 0;
        }
        return String.format("%02d", minuteResult) + ":" + String.format("%02d", secondResult);
    }

}
