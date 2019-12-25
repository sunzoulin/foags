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


public class MomentSinglePhotoView extends FrameLayout {

    private ImageView singleCoverView;
    private MomentContentViewListener listener;
    private String coverUrl;

    public MomentSinglePhotoView(Context context, String coverUrl, MomentContentViewListener listener) {
        super(context);

        this.coverUrl = coverUrl;
        this.listener = listener;
        initViews();
    }

    public MomentSinglePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MomentSinglePhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_view_moment_single_photo_layout, this);
        singleCoverView = findViewById(R.id.singleCoverView);
        Glide.with(getContext()).load(coverUrl).transform(new GlideRoundTransform(UIUtils.dip2px(6f))).into(singleCoverView);

        setOnClickListener(v -> {
            if (listener != null) {
                listener.onClickMomentPhotoItem(0);
            }
        });
    }
}
