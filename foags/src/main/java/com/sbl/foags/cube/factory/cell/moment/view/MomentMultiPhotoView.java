package com.sbl.foags.cube.factory.cell.moment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.sbl.foags.R;
import com.sbl.foags.view.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;


public class MomentMultiPhotoView extends FrameLayout {

    private GridViewForScrollView coversView;
    private MomentContentViewListener listener;
    private MomentMultiPhotoAdapter adapter;

    private List<String> coversUrl;

    public MomentMultiPhotoView(Context context, ArrayList<String> coversUrl, MomentContentViewListener listener) {
        super(context);

        this.coversUrl = coversUrl;
        this.listener = listener;
        initViews();
    }

    public MomentMultiPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MomentMultiPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews() {

        LayoutInflater.from(getContext()).inflate(R.layout.content_view_moment_multi_photo_layout, this);
        coversView = findViewById(R.id.coversView);

        adapter = new MomentMultiPhotoAdapter(getContext());
        coversView.setAdapter(adapter);
        adapter.setData(coversUrl);

        coversView.setOnItemClickListener((parent, view, position, id) -> {
            if (listener != null) {
                listener.onClickMomentPhotoItem(position);
            }
        });
    }
}
