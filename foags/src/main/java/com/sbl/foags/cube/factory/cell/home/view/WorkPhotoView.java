package com.sbl.foags.cube.factory.cell.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sbl.foags.R;

import java.util.ArrayList;


public class WorkPhotoView extends FrameLayout {

    private RecyclerView coversView;
    private WorkContentViewListener listener;
    private WorkPhotoAdapter adapter;

    private int totalPhotoCount;
    private ArrayList<String> photosUrl;

    public WorkPhotoView(Context context, ArrayList<String> photosUrl, int totalPhotoCount, WorkContentViewListener listener) {
        super(context);
        this.listener = listener;
        this.totalPhotoCount = totalPhotoCount;
        this.photosUrl = photosUrl;

        initViews();
    }

    public WorkPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews() {

        LayoutInflater.from(getContext()).inflate(R.layout.content_view_work_photo_layout, this);

        coversView = findViewById(R.id.coversView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        coversView.setLayoutManager(layoutManager);

        adapter = new WorkPhotoAdapter(getContext(), listener, photosUrl, totalPhotoCount);
        coversView.setAdapter(adapter);
    }
}
