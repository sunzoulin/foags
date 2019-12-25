package com.sbl.foags.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sbl.foags.R;

public class LoadingLayout extends FrameLayout {

    /**
     * 内容显示的位置
     */
    private static final int CONTENT_POSITION = 4;

    private EmptyRefreshListener emptyRefreshListener;

    private ErrorRefreshListener errorRefreshListener;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        try {
            int emptyViewId = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.empty_view);
            int errorViewId = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.error_view);
            int loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_view);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View emptyView = inflater.inflate(emptyViewId, this, false);
            final View errorView = inflater.inflate(errorViewId, this, false);
            errorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (errorRefreshListener != null && errorView.getVisibility() == View.VISIBLE) {
                        errorRefreshListener.errorRefresh();
                    }
                }
            });
            emptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (emptyRefreshListener != null && emptyView.getVisibility() == View.VISIBLE) {
                        emptyRefreshListener.emptyRefresh();
                    }
                }
            });
            addView(emptyView);
            addView(errorView);
            inflater.inflate(loadingView, this, true);
        } finally {
            a.recycle();
        }
    }

    public void addEmptyRefreshListener(EmptyRefreshListener emptyRefreshListener) {
        this.emptyRefreshListener = emptyRefreshListener;
    }

    public void addErrorRefreshListener(ErrorRefreshListener errorRefreshListener) {
        this.errorRefreshListener = errorRefreshListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void showEmpty() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showEmpty(String emptyContent) {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
                try {
                    TextView emptyText = child.findViewById(R.id.emptyText);
                    emptyText.setText(emptyContent);
                } catch (Exception e) {
                }
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    /**
     * 只隐藏loading，empty和error
     */
    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 3) {
                child.setVisibility(VISIBLE);
                break;
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public boolean isShowBaseError() {
        return getChildAt(1).getVisibility() == VISIBLE;
    }

    public boolean isInContentState() {
        return getChildCount() >= CONTENT_POSITION && getChildAt(3).getVisibility() == VISIBLE;
    }

    public interface EmptyRefreshListener {
        /**
         * 空白时刷新界面的回调函数
         */
        void emptyRefresh();
    }

    public interface ErrorRefreshListener {

        /**
         * 错误时刷新界面的回调函数
         */
        void errorRefresh();
    }
}
