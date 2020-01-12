package com.sbl.foags.activity.authenticate.model.three;

import android.content.Context;
import android.view.View;

import com.jzxiang.pickerview.adapters.ArrayWheelAdapter;
import com.jzxiang.pickerview.wheel.WheelView;
import com.sbl.foags.R;


class SelectThreeCirclesPickerWheel {

    private Context mContext;

    private WheelView bust;
    private WheelView waist;
    private WheelView hip;

    private ArrayWheelAdapter mBustAdapter;
    private ArrayWheelAdapter mWaistAdapter;
    private ArrayWheelAdapter mHipAdapter;

    private SelectThreeCirclesPickerConfig mPickerConfig;


    public SelectThreeCirclesPickerWheel(View view, SelectThreeCirclesPickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;

        mContext = view.getContext();
        initialize(view);
    }

    private void initialize(View view) {
        initView(view);
    }

    private void initView(View view) {
        bust = view.findViewById(R.id.bust);
        waist = view.findViewById(R.id.waist);
        hip = view.findViewById(R.id.hip);

        bust.setShowLeftLine(false);
        waist.setShowLeftLine(false);
        hip.setShowLeftLine(false);

        initBust();
        initWaist();
        initHip();
    }


    private void initBust() {
        mBustAdapter = new ArrayWheelAdapter(mContext, mPickerConfig.bustList);
        mBustAdapter.setConfig(mPickerConfig);
        bust.setViewAdapter(mBustAdapter);
        bust.setCurrentItem(0);
    }


    private void initWaist(){
        mWaistAdapter = new ArrayWheelAdapter(mContext, mPickerConfig.waistList);
        mWaistAdapter.setConfig(mPickerConfig);
        waist.setViewAdapter(mWaistAdapter);
        waist.setCurrentItem(0);
    }


    private void initHip(){
        mHipAdapter = new ArrayWheelAdapter(mContext, mPickerConfig.hipList);
        mHipAdapter.setConfig(mPickerConfig);
        hip.setViewAdapter(mHipAdapter);
        hip.setCurrentItem(0);
    }

    public String getCurrentBust() {
        return mBustAdapter.getItemText(bust.getCurrentItem()).toString();
    }

    public String getCurrentWaist() {
        return mWaistAdapter.getItemText(waist.getCurrentItem()).toString();
    }

    public String getCurrentHip() {
        return mHipAdapter.getItemText(hip.getCurrentItem()).toString();
    }
}
