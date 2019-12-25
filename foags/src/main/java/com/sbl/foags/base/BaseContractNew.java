package com.sbl.foags.base;

import androidx.annotation.StringRes;

import java.util.List;

public interface BaseContractNew {

    interface BaseView {

        void showBaseLoading();

        void showBaseContent();

        void showBaseEmpty();

        void showBaseNetError();

        void showLoading();

        void hideLoading();

        void showSuccess(String msg);

        void showErrorMsg(String msg);

        void showErrorMsg(@StringRes int msg);
    }

    interface BasePresent<V> {

        void attachView(V view);

        void detachView();
    }

    /**
     * 使用列表滑动请实现此接口
     */
    interface BaseListView<T> {

        void onGetListData(List<T> datas, String pageIndex, boolean isLastPage);

        void onGetListData(List<T> datas, int pageIndex, boolean isLastPage);

        void onGetListData(List<T> datas, String pageIndex);

        void onGetListData(List<T> datas, int pageIndex);

    }

}
