package com.sbl.foags.activity.authenticate.common.string;

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


public class SelectArrayStringListDialog extends DialogFragment implements View.OnClickListener {

    private SelectArrayStringListPickerConfig mPickerConfig;
    private SelectArrayStringListWheel mWheel;

    private static SelectArrayStringListDialog newInstance(SelectArrayStringListPickerConfig pickerConfig) {
        SelectArrayStringListDialog dialog = new SelectArrayStringListDialog();
        dialog.initialize(pickerConfig);
        return dialog;
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

    private void initialize(SelectArrayStringListPickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_NoTitle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        return dialog;
    }

    private View initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_select_array_string_list_layout, null);
        TextView title = view.findViewById(R.id.titleView);
        title.setText(mPickerConfig.title);
        TextView cancel = view.findViewById(R.id.cancelView);
        cancel.setOnClickListener(this);
        TextView sure = view.findViewById(R.id.finishView);
        sure.setOnClickListener(this);
        mWheel = new SelectArrayStringListWheel(view, mPickerConfig);
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancelView) {
            dismiss();
        } else if (i == R.id.finishView) {
            if (mPickerConfig.listener != null) {
                mPickerConfig.listener.onClickConfirm(mWheel.getCurrentIndex(), mWheel.getCurrentString());
            }
            dismiss();
        }
    }

    public static class Builder {
        SelectArrayStringListPickerConfig mPickerConfig;

        public Builder() {
            mPickerConfig = new SelectArrayStringListPickerConfig();
        }

        public Builder setArrayStringList(ArrayList<String> strings) {
            mPickerConfig.strings = strings;
            return this;
        }

        public Builder setCurrentString(String string) {
            mPickerConfig.currentString = string;
            return this;
        }

        public Builder setListener(SelectArrayStringListListener listener) {
            mPickerConfig.listener = listener;
            return this;
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

        public Builder setTitle(String title) {
            mPickerConfig.title = title;
            return this;
        }

        public SelectArrayStringListDialog build() {
            return newInstance(mPickerConfig);
        }
    }
}
