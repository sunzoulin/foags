package com.sbl.foags.activity.main.selected.recommend;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.sbl.foags.R;
import com.sbl.foags.activity.main.selected.recommend.data.BannerBean;
import com.sbl.foags.utils.GlideRoundTransform;
import com.sbl.foags.utils.UIUtils;

public class SelectedRecommendBannerHolderView extends Holder<BannerBean> {

    private Context context;
    private ImageView imageView;

    public SelectedRecommendBannerHolderView(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.photoView);
    }

    @Override
    public void updateUI(BannerBean data) {
        Glide.with(context).load(data.getUrl()).transform(new GlideRoundTransform(UIUtils.dip2px(6f))).into(imageView);
    }
}
