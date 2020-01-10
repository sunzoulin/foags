package com.sbl.foags.activity.authenticate.photographer.work;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.makeramen.roundedimageview.RoundedImageView;
import com.sbl.foags.R;
import com.sbl.foags.adapter.BaseDragGridViewAdapter;
import com.sbl.foags.adapter.BaseDragGridViewHolder;
import com.sbl.foags.view.RecycleSimpleDraweeView;

import java.io.File;


public class SelectWorkPhotoAdapter extends BaseDragGridViewAdapter<File> {


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

        RecycleSimpleDraweeView photoView = (RecycleSimpleDraweeView) holder.getView(R.id.photoView);
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

        photoView.setImageUrl(Uri.fromFile(strList.get(position)).toString());
    }
}
