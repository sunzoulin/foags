package com.sbl.foags.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.sbl.foags.R;


@SuppressLint("AppCompatCustomView")
public class MySwitchView extends ImageView {

    private boolean isOpen = false;


    public MySwitchView(Context context) {
        super(context);
        initView();
    }

    public MySwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MySwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {
        setImageResource(R.drawable.ic_kaig_n);
        isOpen = false;
    }


    public void setOpen(boolean open) {
        if (open) {
            setImageResource(R.drawable.ic_kaig_p);
            isOpen = true;
        }else{
            setImageResource(R.drawable.ic_kaig_n);
            isOpen = false;
        }
    }

    public boolean isOpen(){
        return isOpen;
    }
}
