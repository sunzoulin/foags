package com.sbl.foags.view.recycler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter;


public class FloatHeaderAndFooterRecyclerView extends RecyclerView {

    public static final int DURATION = 300;

    public static final int MIN_DISTANCE = 20;

    private View floatHeader;

    /**
     * 悬浮header占位
     */
    private View headerPlaceHolder;

    private boolean headerPlaceHolderAttach;

    private ObjectAnimator showFloatHeaderAnimator;

    private ObjectAnimator hideFloatHeaderAnimator;

    private ObjectAnimator scrollFloatHeaderAnimator;

    private OnFloatViewStateChangedListener listener;

    /**
     * PlaceHolder的占位高度
     */
    private int placeHolderHeight;

    /**
     * 是否adapter调用了notify方法
     */
    private boolean isNotify;

    public FloatHeaderAndFooterRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public FloatHeaderAndFooterRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatHeaderAndFooterRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        headerPlaceHolder = new View(context);
        initRecyclerView(context);
    }

    private void initRecyclerView(Context context) {
        setLayoutManager(new LinearLayoutManager(context));
        addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                if (view == headerPlaceHolder) {
                    headerPlaceHolderAttach = true;
                    if (floatHeader != null) {
                        floatHeader.setVisibility(View.VISIBLE);
                    }
                }
                if (isNotify) {
                    isNotify = false;
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                if (view == headerPlaceHolder) {
                    headerPlaceHolderAttach = false;
                    if (floatHeader != null && !isHideFloatHeaderAnimRunning()) {
                        floatHeader.setVisibility(View.GONE);
                        // 判断是否调用了notify方法
                        if (!isNotify) {
                            if (listener != null) {
                                listener.onFloatHeaderHide();
                            }
                        }

                    }
                }
            }
        });
    }

    public void setFloatHeader(@LayoutRes int layoutId) {
        setFloatHeader(LayoutInflater.from(getContext()).inflate(layoutId, this, false));
    }

    public void setFloatHeader(final View floatHeader) {
        this.floatHeader = floatHeader;
    }

    public void addHeaderPlaceHolder(BaseRecycleViewAdapter adapter) {
        adapter.addHeaderView(headerPlaceHolder);
        if (this.floatHeader == null){
            return;
        }
        // 如果没有设置占位的高度，默认使用FloatHeader的高度
        if (this.placeHolderHeight == 0) {
            this.floatHeader.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            setHeaderPlaceHolderHeight(floatHeader.getHeight());
                            updatePlaceHolderHeight();
                            floatHeader.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                    });
        } else {
            updatePlaceHolderHeight();
        }
    }

    /**
     * 设置占位Header的高度，不设置会默认使用FloatView的高度
     */
    public void setHeaderPlaceHolderHeight(int height) {
        this.placeHolderHeight = height;
        // 如果已经设置了adapter，直接更新占位的高度
        if (getAdapter() != null) {
            updatePlaceHolderHeight();
        }
    }

    /**
     * 更新占位Header的高度
     */
    private void updatePlaceHolderHeight() {
        // 设置header占位的高度
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) headerPlaceHolder.getLayoutParams();
        if (params == null) {
            params = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, placeHolderHeight);
        } else {
            params.height = placeHolderHeight;
        }
        headerPlaceHolder.setLayoutParams(params);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        // 注册adapter的监听，防止刷新的时候，误触发了隐藏和显示floatView的情况
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                isNotify = true;
            }
        });
    }

    /**
     * 是否floatView已经显示在屏幕中
     */
    private boolean isFloatViewShowing(){
        return floatHeader.getVisibility() == View.VISIBLE && floatHeader.getTop() == 0;
    }

    /**
     * 手动判断是否要显示header或者是底部
     */
    public void shouldShowFloatHeader() {
        // 如果占位正在显示，显示floatView
        if (headerPlaceHolderAttach) {
            showFloatHeader();
        }
    }

    /**
     * RecyclerView 滑动监听
     */
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (this.floatHeader == null){
            return;
        }
//        Log.e("lzp", "dy:" + dy);
//        Log.e("lzp", "headerPlaceHolder:" + headerPlaceHolder.getTop());
        if (headerPlaceHolderAttach) {
            setFloatHeaderTranslateY(dy);
        } else {
            if (Math.abs(dy) < MIN_DISTANCE) {
                return;
            }
            // floatHeader正在显示，如果向下滑动
            if (dy > 0) {
                hideFloatHeader();
            }
            // floatHeader没有显示，如果向上滑动
            else if (dy < 0) {
                showFloatHeader();
            }
        }
    }

    /**
     * 设置floatHeader的translationY
     */
    private void setFloatHeaderTranslateY(int dy) {
        // 向下滑动
        if (dy < 0) {
            if (headerPlaceHolder.getTop() > floatHeader.getTranslationY()) {
                cancelShowFloatHeaderAnim();
                floatHeader.setTranslationY(headerPlaceHolder.getTop());
            }
        }
        // 向下滑动
        else {
            // 如果floatHeader正在显示中，启动一个动画滑动到指定的位置
            if (floatHeader.getTranslationY() == 0) {
                scrollFloatHeader(headerPlaceHolder.getTop());
            } else {
                floatHeader.setTranslationY(headerPlaceHolder.getTop());
            }
        }

        if (listener != null) {
            listener.onFloatHeaderScroll();
        }
    }

    private synchronized void showFloatHeader() {
        if (floatHeader == null) {
            return;
        }
        if (isShowFloatHeaderAnimRunning()) {
            return;
        }
        if (isFloatViewShowing()) {
            return;
        }
        // 取消隐藏动画
        cancelHideFloatHeaderAnim();
        if (showFloatHeaderAnimator == null) {
            showFloatHeaderAnimator = ObjectAnimator.ofFloat(floatHeader, "translationY", floatHeader.getTranslationY(), 0);
            showFloatHeaderAnimator.setDuration(DURATION);
            showFloatHeaderAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationEnd(animation);
                    floatHeader.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    floatHeader.setVisibility(View.VISIBLE);
                }
            });
        } else {
            showFloatHeaderAnimator.setFloatValues(floatHeader.getTranslationY(), 0);
        }
        showFloatHeaderAnimator.start();
        if (listener != null) {
            listener.onFloatHeaderShow();
        }
    }

    public void showFloatHeaderNoAnimator(){
        floatHeader.setVisibility(View.VISIBLE);
        floatHeader.setTranslationY(0);
        if (listener != null) {
            listener.onFloatHeaderShow();
        }
    }

    /**
     * 判断显示floatHeader动画是否正在显示
     */
    private boolean isShowFloatHeaderAnimRunning() {
        return showFloatHeaderAnimator != null && showFloatHeaderAnimator.isRunning();
    }

    private void cancelShowFloatHeaderAnim() {
        if (showFloatHeaderAnimator != null) {
            showFloatHeaderAnimator.cancel();
        }
    }

    private synchronized void hideFloatHeader() {
        if (floatHeader == null) {
            return;
        }
        if (isHideFloatHeaderAnimRunning()) {
            return;
        }
        if (!isFloatViewShowing()) {
            return;
        }
        // 取消显示动画
        cancelShowFloatHeaderAnim();
        if (hideFloatHeaderAnimator == null) {
            hideFloatHeaderAnimator = ObjectAnimator.ofFloat(floatHeader, "translationY",
                    floatHeader.getTranslationY(), -floatHeader.getHeight());
            hideFloatHeaderAnimator.setDuration(DURATION);
            hideFloatHeaderAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    floatHeader.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse) {
                    floatHeader.setVisibility(View.GONE);
                }
            });
        } else {
            hideFloatHeaderAnimator.setFloatValues(floatHeader.getTranslationY(), -floatHeader.getHeight());
        }
        hideFloatHeaderAnimator.start();
        if (listener != null) {
            listener.onFloatHeaderHide();
        }
    }

    /**
     * 判断隐藏floatHeader动画是否正在显示
     */
    private boolean isHideFloatHeaderAnimRunning() {
        return hideFloatHeaderAnimator != null && hideFloatHeaderAnimator.isRunning();
    }

    private void cancelHideFloatHeaderAnim() {
        if (hideFloatHeaderAnimator != null) {
            hideFloatHeaderAnimator.end();
        }
    }

    /**
     * 滚动floatHeader到指定的位置
     */
    private synchronized void scrollFloatHeader(float translationY) {
        if (floatHeader == null) {
            return;
        }
        if (scrollFloatHeaderAnimator != null && scrollFloatHeaderAnimator.isRunning()) {
            return;
        }
        if (floatHeader.getVisibility() == GONE) {
            return;
        }
        cancelShowFloatHeaderAnim();
        if (scrollFloatHeaderAnimator == null) {
            scrollFloatHeaderAnimator = ObjectAnimator.ofFloat(floatHeader, "translationY",
                    floatHeader.getTranslationY(), translationY);
        } else {
            scrollFloatHeaderAnimator.setFloatValues(floatHeader.getTranslationY(), translationY);
        }
        // 按照滑动距离的比例，计算动画的时间
        scrollFloatHeaderAnimator.setDuration((long) (Math.abs(translationY - floatHeader.getTranslationY()) / floatHeader.getHeight() * DURATION));
        scrollFloatHeaderAnimator.start();
    }

    public void setListener(OnFloatViewStateChangedListener listener) {
        this.listener = listener;
    }

    public interface OnFloatViewStateChangedListener {

        void onFloatHeaderShow();

        void onFloatHeaderHide();

        void onFloatHeaderScroll();

    }


}
