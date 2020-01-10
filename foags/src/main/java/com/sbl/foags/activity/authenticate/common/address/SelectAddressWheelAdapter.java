package com.sbl.foags.activity.authenticate.common.address;

import android.content.Context;

import com.jzxiang.pickerview.adapters.AbstractWheelTextAdapter;
import com.sbl.foags.activity.authenticate.common.address.data.ChinaAddressBean;

import java.util.ArrayList;


public class SelectAddressWheelAdapter extends AbstractWheelTextAdapter {

    private ArrayList<ChinaAddressBean> items;

    public SelectAddressWheelAdapter(Context context, ArrayList<ChinaAddressBean> items) {
        super(context);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            ChinaAddressBean item = items.get(index);
            return item.getName();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }

    public ChinaAddressBean getCurrentAddressId(int position){
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }
}
