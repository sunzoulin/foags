package com.sbl.foags.adapter;

import androidx.fragment.app.Fragment;

public interface OnFragmentLoadListener {

    /**
     * fragment的数量
     *
     * @return 数量
     */
    int getPagerAdapterCount();

    /**
     * 加载指定位置的fragment
     *
     * @param position 加载的位置
     * @return fragment
     */
    Fragment getFragmentPosition(int position);

    /**
     * 加载指定位置的fragment的标题
     *
     * @param position 加载的位置
     * @return 标题
     */
    String getFragmentPositionTitle(int position);
}
