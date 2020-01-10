package com.sbl.foags.adapter;

import android.util.SparseArray;
import android.view.View;


public class BaseDragGridViewHolder {

    private SparseArray<View> array;

    private View view;


    public BaseDragGridViewHolder(View view) {
        this.view = view;
        this.array = new SparseArray<>();
    }

    public void addView(int id) {
        array.append(id, view.findViewById(id));
    }

    public View getView(int id) {
        return array.get(id);
    }
}
