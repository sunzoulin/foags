package com.sbl.foags.view.recycler.listener;

import android.view.View;


public interface OnItemLongClickListener<T> {
    void onItemLongClick(View view, int position, T data);
}
