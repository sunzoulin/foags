package com.sbl.foags.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.sbl.foags.R;

import java.util.ArrayList;


public class MomentFollowPersonTipView extends FrameLayout {

    private FrameLayout headPicOneLayout;
    private FrameLayout headPicTwoLayout;
    private FrameLayout headPicThreeLayout;
    private FrameLayout headPicFourLayout;

    private ImageView headPicOneView;
    private ImageView headPicTwoView;
    private ImageView headPicThreeView;
    private ImageView headPicFourView;

    public MomentFollowPersonTipView(@NonNull Context context) {
        super(context);
        initView();
    }

    public MomentFollowPersonTipView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MomentFollowPersonTipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MomentFollowPersonTipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.moment_follow_person_tip_view_layout, this);

        headPicOneLayout = findViewById(R.id.headPicOneLayout);
        headPicTwoLayout = findViewById(R.id.headPicTwoLayout);
        headPicThreeLayout = findViewById(R.id.headPicThreeLayout);
        headPicFourLayout = findViewById(R.id.headPicFourLayout);


        headPicOneView = findViewById(R.id.headPicOneView);
        headPicTwoView = findViewById(R.id.headPicTwoView);
        headPicThreeView = findViewById(R.id.headPicThreeView);
        headPicFourView = findViewById(R.id.headPicFourView);

        headPicOneLayout.setVisibility(View.GONE);
        headPicTwoLayout.setVisibility(View.GONE);
        headPicThreeLayout.setVisibility(View.GONE);
        headPicFourLayout.setVisibility(View.GONE);
    }


    public void show(ArrayList<String> headPicUrls){
        if(headPicUrls == null || headPicUrls.isEmpty()){
            headPicOneLayout.setVisibility(View.GONE);
            headPicTwoLayout.setVisibility(View.GONE);
            headPicThreeLayout.setVisibility(View.GONE);
            headPicFourLayout.setVisibility(View.GONE);
            return;
        }

        switch (headPicUrls.size()){
            case 1:

                headPicOneLayout.setVisibility(View.VISIBLE);
                headPicTwoLayout.setVisibility(View.GONE);
                headPicThreeLayout.setVisibility(View.GONE);
                headPicFourLayout.setVisibility(View.GONE);

                Glide.with(getContext()).load(headPicUrls.get(0)).transform(new CircleCrop()).into(headPicOneView);

                break;
            case 2:

                headPicOneLayout.setVisibility(View.VISIBLE);
                headPicTwoLayout.setVisibility(View.VISIBLE);
                headPicThreeLayout.setVisibility(View.GONE);
                headPicFourLayout.setVisibility(View.GONE);

                Glide.with(getContext()).load(headPicUrls.get(0)).transform(new CircleCrop()).into(headPicOneView);
                Glide.with(getContext()).load(headPicUrls.get(1)).transform(new CircleCrop()).into(headPicTwoView);

                break;
            case 3:

                headPicOneLayout.setVisibility(View.VISIBLE);
                headPicTwoLayout.setVisibility(View.VISIBLE);
                headPicThreeLayout.setVisibility(View.VISIBLE);
                headPicFourLayout.setVisibility(View.GONE);

                Glide.with(getContext()).load(headPicUrls.get(0)).transform(new CircleCrop()).into(headPicOneView);
                Glide.with(getContext()).load(headPicUrls.get(1)).transform(new CircleCrop()).into(headPicTwoView);
                Glide.with(getContext()).load(headPicUrls.get(2)).transform(new CircleCrop()).into(headPicThreeView);

                break;
            default:

                headPicOneLayout.setVisibility(View.VISIBLE);
                headPicTwoLayout.setVisibility(View.VISIBLE);
                headPicThreeLayout.setVisibility(View.VISIBLE);
                headPicFourLayout.setVisibility(View.VISIBLE);

                Glide.with(getContext()).load(headPicUrls.get(0)).transform(new CircleCrop()).into(headPicOneView);
                Glide.with(getContext()).load(headPicUrls.get(1)).transform(new CircleCrop()).into(headPicTwoView);
                Glide.with(getContext()).load(headPicUrls.get(2)).transform(new CircleCrop()).into(headPicThreeView);
                Glide.with(getContext()).load(headPicUrls.get(3)).transform(new CircleCrop()).into(headPicFourView);

                break;
        }
    }
}
