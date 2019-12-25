package com.sbl.foags.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
import java.util.List;


public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter {

    protected int id;
    protected Context context;
    protected List<T> list = new ArrayList<>();
    private int selectPosition = -1;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    /**
     * 构造函数
     *
     * @param context
     */
    protected MyBaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置list
     *
     * @param list
     */
    public void setList(List<T> list) {
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * 重新设置内容
     * */
    public void setInitList(List<T> list){
        this.list.clear();
        setList(list);
    }

    /**
     * 返回list
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract int getLayoutId();

    /**
     * 设置adapter 图片宽比高
     *
     * @param width
     * @param height
     * @return
     */
    protected LayoutParams getLayoutParams(int width, int height) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.height = height;
        return params;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        }
        bindView(convertView, position);
        return convertView;
    }

    protected abstract void bindView(View convertView, int position);

    public static class ViewHolder {

        /**
         * 获取adapter view id
         *
         * @param view
         * @param id
         * @return
         */
        @SuppressWarnings({"unchecked"})
        public static <T extends View> T getViewID(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            } else {
                viewHolder = (SparseArray<View>) view.getTag();

            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }


    }

}
