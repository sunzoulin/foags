package com.sbl.foags.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sbl.foags.R;
import com.sbl.foags.manager.IndicatorManager;
import com.sbl.foags.view.LoadingLayout;

import java.util.List;


public abstract class BaseFragment extends Fragment
        implements BaseContractNew.BaseView, LoadingLayout.EmptyRefreshListener,
        LoadingLayout.ErrorRefreshListener {


    public int myTag = 0;

    protected final String TAG = this.getClass().getSimpleName();
    protected Activity activity;

    public View getRootView() {
        return rootView;
    }

    protected View rootView;
    protected boolean isVisible = false;
    /**
     * 默认是true
     */
    protected boolean isLazyLoad = true;
    protected LoadingLayout loadingLayout;
    protected String mPageName = "";
    private boolean hasPaused = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
            loadingLayout = rootView.findViewById(R.id.loading_layout);
            loadingLayout.addEmptyRefreshListener(this);
            loadingLayout.addErrorRefreshListener(this);
            loadingLayout.addView(inflater.inflate(initLayout(), container, false));
            loadingLayout.showContent();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        // 如果不是懒加载，创建就加载数据
        // 如果对用户可见，加载数据
        if (!isLazyLoad || getUserVisibleHint()) {
            isLazyLoad = false;
            lazyLoad();
        }
    }

    protected abstract int initLayout();

    protected abstract void initView();

    @Override
    public void showBaseLoading() {
        loadingLayout.showLoading();
    }

    @Override
    public void showBaseContent() {
        loadingLayout.showContent();
    }

    @Override
    public void showBaseEmpty() {
        loadingLayout.showEmpty();
    }

    @Override
    public void showBaseNetError() {
        loadingLayout.showError();
    }

    @Override
    public void showSuccess(String msg) {
        IndicatorManager.showSuccess(msg);
    }

    @Override
    public void showLoading() {
        IndicatorManager.showLoading(requireActivity());
    }

    @Override
    public void hideLoading() {
        IndicatorManager.dismissLoading();
    }

    /**
     * setArguments 在onHidden 和 setUserVisibleHint 之前执行，最好来初始化非常重要的信息
     * <p>
     * 例如talkingdata的mPageName
     */
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {

        if (rootView != null) {
            return (T) rootView.findViewById(id);
        }

        return null;
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    private void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent(activity, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);
    }


    private boolean hasViewAdded() {
        return isAdded() && getView() != null
                && getView().getWindowToken() != null;
    }

    /**
     * 懒加载
     *
     * @param isVisibleToUser 是否对用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!isVisible) {
                isVisible = true;
                onVisible();
            }

            if (isLazyLoad && rootView != null) {
                isLazyLoad = false;
                lazyLoad();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                if (hasViewAdded()) {
                    onInVisible();
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!isVisible) {
                isVisible = true;
                onVisible();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                if (hasViewAdded()) {
                    onInVisible();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments.size() > 0) {
            for (Fragment f : fragments) {
                if (f != null) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }


    protected void onInVisible() {
    }

    protected void onVisible() {
    }

    @Override
    public void emptyRefresh() {
    }

    @Override
    public void errorRefresh() {

    }

    protected void lazyLoad() {
    }

    @Override
    public void onPause() {
        super.onPause();
        hasPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        hasPaused = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void showErrorMsg(String msg) {
        IndicatorManager.showError(msg);
    }

    @Override
    public void showErrorMsg(int msg) {
        IndicatorManager.showError(msg);
    }


    protected boolean hasPaused() {
        return hasPaused;
    }

    /**
     * 设置懒加载
     */
    public void setLazyLoad(boolean lazyLoad) {
        this.isLazyLoad = lazyLoad;
    }

    private String initPageName() {
        if (mPageName == null) {
            mPageName = getPageName();
        }
        return mPageName;
    }

    protected String getPageName() {
        return null;
    }

    public boolean onBackPress() {
        return false;
    }

    public void finish() {
    }

}
