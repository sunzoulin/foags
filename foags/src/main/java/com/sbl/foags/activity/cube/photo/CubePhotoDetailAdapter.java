package com.sbl.foags.activity.cube.photo;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.sbl.foags.R;

import java.util.ArrayList;
import java.util.List;


public class CubePhotoDetailAdapter extends PagerAdapter {

    private Activity mContext;
    private LayoutInflater inflater;
    private List<String> mImages = new ArrayList<>();

    private ImageReviewAdapterListener listener;

    public CubePhotoDetailAdapter(Activity context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setListener(ImageReviewAdapterListener listener) {
        this.listener = listener;
    }

    public void setData(List<String> images) {
        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        View view = inflater.inflate(R.layout.adapter_item_cube_photo_detail, container, false);
//        HorizontalScrollView scrollView =  view.findViewById(R.id.imageViewLayout);
//        PhotoView image = view.findViewById(R.id.imageView);
        PhotoView otherImage = view.findViewById(R.id.otherImageView);
        View loading = view.findViewById(R.id.loading);
        View failView = view.findViewById(R.id.failView);

        loading.setVisibility(View.VISIBLE);
        failView.setVisibility(View.GONE);


        //用于显示原图
        Glide.with(mContext).load(mImages.get(position)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                loading.setVisibility(View.GONE);
                failView.setVisibility(View.VISIBLE);


                failView.setOnClickListener(v -> {
                    loading.setVisibility(View.VISIBLE);
                    failView.setVisibility(View.GONE);


                    Glide.with(mContext).load(mImages.get(position)).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e1, Object model1, Target<Drawable> target1, boolean isFirstResource1) {
                            loading.setVisibility(View.GONE);
                            failView.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model1, Target<Drawable> target1, DataSource dataSource, boolean isFirstResource1) {
                            loading.setVisibility(View.GONE);
                            failView.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(otherImage);
                });
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                loading.setVisibility(View.GONE);
                failView.setVisibility(View.GONE);
                otherImage.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.setOnImageClickListener(position);
                    }
                });
//                image.setOnClickListener(v -> {
//                    if (listener != null) {
//                        listener.setOnImageClickListener(position);
//                    }
//                });
                return false;
            }
        }).into(otherImage);


        //http://pic35.nipic.com/20131110/1338599_221817467119_2.jpg

//        //用于获取Bitmap大小
//        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(new SimpleTarget<Bitmap>(SIZE_ORIGINAL, dm.heightPixels) {
//
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                super.onLoadFailed(errorDrawable);
//                loading.setVisibility(View.GONE);
//                failView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
//                scrollView.setVisibility(bitmap.getWidth() > dm.widthPixels ? View.VISIBLE : View.GONE);
//                otherImage.setVisibility(bitmap.getWidth() > dm.widthPixels ? View.GONE : View.VISIBLE);
//
//                //用于显示原图
//                Glide.with(mContext).load(mImages.get(position)).listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        loading.setVisibility(View.GONE);
//                        failView.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        loading.setVisibility(View.GONE);
//                        failView.setVisibility(View.GONE);
//                        otherImage.setOnClickListener(v -> {
//                            if (listener != null) {
//                                listener.setOnImageClickListener(position);
//                            }
//                        });
//                        image.setOnClickListener(v -> {
//                            if (listener != null) {
//                                listener.setOnImageClickListener(position);
//                            }
//                        });
//                        return false;
//                    }
//                }).into(bitmap.getWidth() > dm.widthPixels ? image : otherImage);
//            }
//        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public interface ImageReviewAdapterListener {
        void setOnImageClickListener(int position);
    }
}
