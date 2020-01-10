package com.sbl.foags.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public abstract class BaseDragGridViewAdapter<T> extends BaseAdapter {

    public int maxCount;
    protected Context context;
    protected ArrayList<T> strList;
    protected int hidePosition = AdapterView.INVALID_POSITION;
    protected boolean isHideLastPosition = false;
    protected boolean isHideOnlyAdd = false;
    protected boolean isOnlyReview = false;
    protected BaseDragGridViewListener<T> mkListener = null;


    public BaseDragGridViewAdapter(Context context, int maxCount) {
        this.strList = new ArrayList<>();
        this.context = context;
        this.maxCount = maxCount;
    }

    public void setHideOnlyAdd(boolean hideOnlyAdd) {
        this.isHideOnlyAdd = hideOnlyAdd;
    }

    public void setOnlyReviewMode(boolean onlyReview) {
        this.isOnlyReview = onlyReview;
    }

    public void setListener(BaseDragGridViewListener<T> l) {
        this.mkListener = l;
    }

    public void setInitList(ArrayList<T> list) {
        this.strList.clear();
        this.strList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (strList.size() == 0) {

            if (!isHideOnlyAdd && !isOnlyReview) {
                return 1;
            }else{
                return 0;
            }

        } else if (strList.size() < maxCount) {

            if (!isOnlyReview) {
                return strList.size()+ 1;
            }else{
                return strList.size();
            }

        }else if (strList.size() == maxCount) {
            return strList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        return strList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseDragGridViewHolder viewHolder;
        if (convertView == null) {

            convertView = getConvertView(parent);
            viewHolder = new BaseDragGridViewHolder(convertView);
            bindViewHolder(viewHolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BaseDragGridViewHolder) convertView.getTag();
        }

        //hide时隐藏Text
        if (position != hidePosition) {
            if (isHideLastPosition && position == getCount() - 1) {
                convertView.setVisibility(View.GONE);
            } else {
                convertView.setVisibility(View.VISIBLE);
                setViewValue(viewHolder, position);
            }
        } else {
            convertView.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void hideView(int pos) {
        hidePosition = pos;
        notifyDataSetChanged();
    }

    public void showHideView() {
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    public void hideLastView() {
        isHideLastPosition = true;
        notifyDataSetChanged();
    }

    public void showHideLastView() {
        isHideLastPosition = false;
        notifyDataSetChanged();
    }

    //更新拖动时的gridView
    public void swapView(int draggedPos, int destPos) {
        //从前向后拖动，其他item依次前移
        if (draggedPos < destPos) {
            strList.add(destPos + 1, getItem(draggedPos));
            strList.remove(draggedPos);
        }
        //从后向前拖动，其他item依次后移
        else if (draggedPos > destPos) {
            strList.add(destPos, getItem(draggedPos));
            strList.remove(draggedPos + 1);
        }
        hidePosition = destPos;

        ArrayList<T> list = new ArrayList<>(strList);
        if (list.size() < maxCount) {
            list.remove(list.size() - 1);
        }
        mkListener.moveChange(list);
        notifyDataSetChanged();
    }

    protected abstract View getConvertView(ViewGroup parent);

    protected abstract void bindViewHolder(BaseDragGridViewHolder viewHolder);

    protected abstract void setViewValue(BaseDragGridViewHolder holder, final int position);
}
