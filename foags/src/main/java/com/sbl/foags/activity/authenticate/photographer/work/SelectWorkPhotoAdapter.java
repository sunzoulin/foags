package com.sbl.foags.activity.authenticate.photographer.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sbl.foags.R;
import com.sbl.foags.adapter.BaseDragGridViewAdapter;
import com.sbl.foags.adapter.BaseDragGridViewHolder;
import com.sbl.foags.utils.GlideRoundTransform;
import com.sbl.foags.utils.UIUtils;


public class SelectWorkPhotoAdapter extends BaseDragGridViewAdapter<String> {


    public SelectWorkPhotoAdapter(Context context, int maxCount) {
        super(context, maxCount);
    }


    @Override
    protected View getConvertView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_item_select_work_photo, parent, false);
    }

    @Override
    protected void bindViewHolder(BaseDragGridViewHolder viewHolder) {
        viewHolder.addView(R.id.addPhotoLayout);
        viewHolder.addView(R.id.photoLayout);
        viewHolder.addView(R.id.photoView);
        viewHolder.addView(R.id.deleteView);
    }

    @Override
    protected void setViewValue(BaseDragGridViewHolder holder, final int position) {
        FrameLayout addPhotoLayout = (FrameLayout) holder.getView(R.id.addPhotoLayout);
        FrameLayout photoLayout = (FrameLayout) holder.getView(R.id.photoLayout);

        ImageView photoView = (ImageView) holder.getView(R.id.photoView);
        RoundedImageView deleteView = (RoundedImageView) holder.getView(R.id.deleteView);

        addPhotoLayout.setOnClickListener(v -> {
            if (!isOnlyReview) {
                mkListener.onItemAddClick();
            }
        });

        deleteView.setOnClickListener(v -> mkListener.onItemDeleteClick(position));

        if(strList.size() == 0 && position == 0){
            addPhotoLayout.setVisibility(View.VISIBLE);
            photoLayout.setVisibility(View.GONE);
            return;

        }else if(strList.size() < maxCount && strList.size() == position){

            addPhotoLayout.setVisibility(View.VISIBLE);
            photoLayout.setVisibility(View.GONE);
            return;
        }


        addPhotoLayout.setVisibility(View.GONE);
        photoLayout.setVisibility(View.VISIBLE);


        if (isOnlyReview) {
            deleteView.setVisibility(View.GONE);
        } else {
            deleteView.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(strList.get(position)).transform(new GlideRoundTransform(UIUtils.dip2px(6f))).into(photoView);
    }
}
