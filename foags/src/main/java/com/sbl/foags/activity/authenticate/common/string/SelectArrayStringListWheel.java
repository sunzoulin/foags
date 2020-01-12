package com.sbl.foags.activity.authenticate.common.string;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.jzxiang.pickerview.adapters.ArrayWheelAdapter;
import com.jzxiang.pickerview.wheel.WheelView;
import com.sbl.foags.R;


public class SelectArrayStringListWheel {

    private Context mContext;
    private WheelView wheelView;
    private ArrayWheelAdapter mAdapter;
    private SelectArrayStringListPickerConfig mPickerConfig;

    public SelectArrayStringListWheel(View view, SelectArrayStringListPickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
        mContext = view.getContext();
        initialize(view);
    }

    private void initialize(View view) {
        initView(view);
        initWheel();
    }

    private void initView(View view) {
        wheelView = view.findViewById(R.id.wheelView);
        wheelView.setShowLeftLine(false);
    }

    private void initWheel() {
        mAdapter = new ArrayWheelAdapter(mContext, mPickerConfig.strings);
        mAdapter.setConfig(mPickerConfig);
        wheelView.setViewAdapter(mAdapter);
        if (!TextUtils.isEmpty(mPickerConfig.currentString)) {
            wheelView.setCurrentItem(mPickerConfig.strings.indexOf(mPickerConfig.currentString));
        }
    }

    public String getCurrentString() {
        return mAdapter.getItemText(wheelView.getCurrentItem()).toString();
    }

    public int getCurrentIndex() {
        return wheelView.getCurrentItem();
    }
}
