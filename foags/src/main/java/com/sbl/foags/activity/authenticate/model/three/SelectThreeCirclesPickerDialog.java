package com.sbl.foags.activity.authenticate.model.three;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sbl.foags.R;

import java.util.ArrayList;


public class SelectThreeCirclesPickerDialog extends DialogFragment implements View.OnClickListener {

    private SelectThreeCirclesPickerConfig mPickerConfig;
    private SelectThreeCirclesPickerWheel mThreeCirclesWheel;

    private static SelectThreeCirclesPickerDialog newInstance(SelectThreeCirclesPickerConfig pickerConfig) {
        SelectThreeCirclesPickerDialog pickerDialog = new SelectThreeCirclesPickerDialog();
        pickerDialog.initialize(pickerConfig);
        return pickerDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    private void initialize(SelectThreeCirclesPickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), com.jzxiang.pickerview.R.style.Dialog_NoTitle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        return dialog;
    }

    private View initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_select_three_circles_layout, null);
        TextView sure = view.findViewById(R.id.finishView);
        sure.setOnClickListener(this);
        TextView cancel = view.findViewById(R.id.cancelView);
        cancel.setOnClickListener(this);
        mThreeCirclesWheel = new SelectThreeCirclesPickerWheel(view, mPickerConfig);
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancelView) {
            dismiss();
        } else if (i == R.id.finishView) {
            sureClicked();
        }
    }

    private void sureClicked() {
        if(mPickerConfig.listener != null){
            mPickerConfig.listener.onSelectThreeCircles(mThreeCirclesWheel.getCurrentBust(),
                    mThreeCirclesWheel.getCurrentWaist(),
                    mThreeCirclesWheel.getCurrentHip());
        }
        dismiss();
    }


    public static class Builder {
        SelectThreeCirclesPickerConfig mPickerConfig;

        public Builder() {
            mPickerConfig = new SelectThreeCirclesPickerConfig();
        }

        public Builder setWheelItemTextNormalColor(int color) {
            mPickerConfig.mWheelTVNormalColor = color;
            return this;
        }

        public Builder setWheelItemTextSelectorColor(int color) {
            mPickerConfig.mWheelTVSelectorColor = color;
            return this;
        }

        public Builder setWheelItemTextSize(int size) {
            mPickerConfig.mWheelTVSize = size;
            return this;
        }

        public Builder setCyclic(boolean cyclic) {
            mPickerConfig.cyclic = cyclic;
            return this;
        }

        public Builder setListener(SelectThreeCirclesListener listener) {
            mPickerConfig.listener = listener;
            return this;
        }

        public Builder setBustList(ArrayList<String> bustList) {
            mPickerConfig.bustList = bustList;
            return this;
        }

        public Builder setWaistList(ArrayList<String> waistList) {
            mPickerConfig.waistList = waistList;
            return this;
        }

        public Builder setHipList(ArrayList<String> hipList) {
            mPickerConfig.hipList = hipList;
            return this;
        }

        public SelectThreeCirclesPickerDialog build() {
            return newInstance(mPickerConfig);
        }
    }
}
