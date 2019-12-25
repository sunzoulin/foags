package com.sbl.foags.view.recycler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.sbl.foags.view.recycler.listener.OnItemClickListener;
import com.sbl.foags.view.recycler.listener.OnItemLongClickListener;

import java.util.ArrayList;


public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_VIEW = 100000;
    //头视图
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    //尾视图
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private View loadMoreFooter;
    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();
    private ArrayList<Integer> mFooterViewTypes = new ArrayList<>();

    protected Context mContext;

    protected ArrayList<T> mDatas;

    protected OnItemClickListener<T> mOnItemClickListener;

    private OnItemLongClickListener<T> mOnItemLongClickListener;

    /**
     * 设置点击事件监听器
     *
     * @param l 监听器对象
     */
    public void setOnItemClickListener(OnItemClickListener<T> l) {
        this.mOnItemClickListener = l;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    /**
     * 构造器
     *
     * @param context
     * @param mDatas
     */
    protected BaseRecycleViewAdapter(Context context, ArrayList<T> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    /**
     * 返回指定的item的layoutId
     */
    protected abstract int getLayoutId(int viewType);


    /**
     * 可以添加多个头视图
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        if (mHeaderViews.contains(headerView)) {
            return;
        }
        mHeaderViews.add(headerView);
    }

    /**
     * 可以添加多个头视图
     *
     * @param headerView
     */
    public void addHeaderView(View headerView, int position) {
        if (mHeaderViews.contains(headerView)) {
            return;
        }
        mHeaderViews.add(position, headerView);
    }

    /**
     * 移除所有headers
     */
    public void removeAllheaders() {
        mHeaderViews.clear();
    }

    /**
     * 可以添加多个尾视图
     *
     * @param footerView 尾视图
     */
    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }

    /**
     * 获取加载更多的footer
     */
    public View getLoadFooterView() {
        return loadMoreFooter;
    }

    /**
     * 加载更多的尾视图
     *
     * @param footerView 尾视图
     */
    public void addLoadFooterView(View footerView) {
        loadMoreFooter = footerView;
        //这里在添加加载更多footer时移除之前的footer
        if (loadMoreFooter != null) {
            removeLoadFooterView();
        }
        loadMoreFooter.setTag("loadFooter");
        mFooterViews.add(loadMoreFooter);

    }

    /**
     * 移除加载更多footer
     */
    public void removeLoadFooterView() {
        mFooterViews.remove(loadMoreFooter);
    }

    public void removeHeaderView(View headerView) {
        mHeaderViews.remove(headerView);
    }


    public void removeFooterView(View headerView) {
        mFooterViews.remove(headerView);
    }


    /**
     * Grid  header footer占据位图
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == position * TYPE_VIEW
                            ? getHeaderAndFooterSpan(gridManager) : 1;
                }
            });
        }
    }

    protected int getHeaderAndFooterSpan(GridLayoutManager gridManager) {
        return gridManager.getSpanCount();
    }

    /**
     * StaggeredGrid  header footer占据位图
     */
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (holder.getLayoutPosition() < mHeaderViews.size() || holder.getLayoutPosition() >= getItemCount() - mFooterViews.size())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            //占据全部空间
            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
            //用position作为HeaderView 的   ViewType标记
            //记录每个ViewType标记
            mHeaderViewTypes.add(position * TYPE_VIEW);
            return position * TYPE_VIEW;
        }

        if (mFooterViews.size() > 0 && position > getListCount() - 1 + mHeaderViews.size()) {
            //用position作为FooterView 的   ViewType标记
            //记录每个ViewType标记
            mFooterViewTypes.add(position * TYPE_VIEW);
            return position * TYPE_VIEW;
        }

        if (mHeaderViews.size() > 0) {
            return getListViewType(position - mHeaderViews.size());
        }


        return getListViewType(position);
    }

    /**
     * Item页布局类型个数，默认为1
     *
     * item的type不要使用0
     */
    protected int getListViewType(int position) {
        return 1;
    }

    public int getListCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    /**
     * 设置每个页面显示的内容
     *
     * @param holder itemHolder
     * @param item   每一Item显示的数据
     */
    protected abstract void convert(ViewHolder holder, T item, int position);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViewTypes.contains(viewType)) {
            return new HeaderHolder(mHeaderViews.get(viewType / TYPE_VIEW));
        }

        if (mFooterViewTypes.contains(viewType)) {
            int index = viewType / TYPE_VIEW - getListCount() - mHeaderViews.size();
            return new FooterHolder(mFooterViews.get(index));
        }

        return onCreateBaseViewHolder(parent, viewType);
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   RecycleView对象
     * @param viewType view类型
     * @return Holder对象
     */
    private RecyclerView.ViewHolder  onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(mContext, null, parent, getLayoutId(viewType));
    }

    private void setListener(final ViewHolder viewHolder, int viewType, final int position) {
        if (!isEnabled(viewType)) {
            return;
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                if (position < 0 || mDatas.size() <= position) {
                    Log.d("setOnClickListener", "" + position);
                    return;
                }
                mOnItemClickListener.onItemClick(viewHolder.itemView, mDatas.get(position), position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(v -> {
            if (mOnItemLongClickListener != null)

            {
                if (position < 0 || mDatas.size() <= position) {
                    return true;
                }
                mOnItemLongClickListener.onItemLongClick(v, position, mDatas.get(position));
            }
            return false;
        });
    }

    private boolean isEnabled(int viewType) {
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mFooterViews.size() > 0 && (position > getListCount() - 1 + mHeaderViews.size())) {
            return;
        }
        if (mHeaderViews.size() > 0) {
            if (position < mHeaderViews.size()) {
                return;
            }
            onBindBaseViewHolder((ViewHolder) holder, position - mHeaderViews.size());
            setListener((ViewHolder) holder, getItemViewType(position), position - mHeaderViews.size());
            return;
        }
        onBindBaseViewHolder((ViewHolder) holder, position);
        setListener((ViewHolder) holder, getItemViewType(position), position);
    }

    private void onBindBaseViewHolder(ViewHolder holder, int i) {
        convert(holder, mDatas.get(i), i);
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        FooterHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
            return getListCount() + mHeaderViews.size() + mFooterViews.size();
        }
        if (mHeaderViews.size() > 0) {
            return getListCount() + mHeaderViews.size();
        }
        if (mFooterViews.size() > 0) {
            return getListCount() + mFooterViews.size();
        }

        return getListCount();
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public ArrayList<T> getLists() {
        return mDatas;
    }

    /**
     * 根据位置插入
     *
     * @param position
     * @param t
     */
    public void addOne(int position, T t) {
        mDatas.add(position, t);
        notifyItemInserted(position);
    }

    /**
     * 插入一组list
     *
     * @param list
     */
    public void setList(ArrayList<T> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 插入一组list
     *
     * @param list
     */
    public void setData(ArrayList<T> list) {
        mDatas = list;
    }

    /**
     * 单独移除某一个
     *
     * @param position
     */
    public void removeOne(int position) {
        if (!mDatas.isEmpty()) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }

    /**
     * 全部移除
     */
    public void removeAll() {
        if (!mDatas.isEmpty()) {
            mDatas.clear();
            this.notifyDataSetChanged();
        }
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /**
     * 判断当前Item是否是header
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position < mHeaderViews.size();
    }

    /**
     * 判断当前Item是否是footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - mFooterViews.size();
        return getFooterViewsCount() > 0 && position >= lastPosition;
    }

    /**
     * Created by lizhipeng
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;
        private Context mContext;
        private int mLayoutId;

        public ViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            mConvertView = itemView;
        }

        private ViewHolder(Context context, View itemView) {
            this(itemView);
            mContext = context;
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId) {
            if (convertView == null) {
                View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                        false);
                ViewHolder holder = new ViewHolder(context, itemView);
                holder.mLayoutId = layoutId;
                return holder;
            } else {
                return (ViewHolder) convertView.getTag();
            }
        }


        /**
         * 通过viewId获取控件
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 设置TextView的值
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }

        public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        public ViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        public ViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return this;
        }

        public ViewHolder setTextColorRes(int viewId, int textColorRes) {
            TextView view = getView(viewId);
            view.setTextColor(mContext.getResources().getColor(textColorRes));
            return this;
        }

        @SuppressLint("NewApi")
        public ViewHolder setAlpha(int viewId, float value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getView(viewId).setAlpha(value);
            } else {
                // Pre-honeycomb hack to set Alpha value
                AlphaAnimation alpha = new AlphaAnimation(value, value);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                getView(viewId).startAnimation(alpha);
            }
            return this;
        }

        public ViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return this;
        }

        public ViewHolder linkify(int viewId) {
            TextView view = getView(viewId);
            Linkify.addLinks(view, Linkify.ALL);
            return this;
        }

        public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
            for (int viewId : viewIds) {
                TextView view = getView(viewId);
                view.setTypeface(typeface);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            return this;
        }

        public ViewHolder setProgress(int viewId, int progress) {
            ProgressBar view = getView(viewId);
            view.setProgress(progress);
            return this;
        }

        public ViewHolder setProgress(int viewId, int progress, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            view.setProgress(progress);
            return this;
        }

        public ViewHolder setMax(int viewId, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            return this;
        }

        public ViewHolder setRating(int viewId, float rating) {
            RatingBar view = getView(viewId);
            view.setRating(rating);
            return this;
        }

        public ViewHolder setRating(int viewId, float rating, int max) {
            RatingBar view = getView(viewId);
            view.setMax(max);
            view.setRating(rating);
            return this;
        }

        public ViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return this;
        }

        public ViewHolder setTag(int viewId, int key, Object tag) {
            View view = getView(viewId);
            view.setTag(key, tag);
            return this;
        }

        public ViewHolder setChecked(int viewId, boolean checked) {
            Checkable view = getView(viewId);
            view.setChecked(checked);
            return this;
        }

        /**
         * 关于事件的
         */
        public ViewHolder setOnClickListener(int viewId,
                                             View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        public ViewHolder setOnTouchListener(int viewId,
                                             View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return this;
        }

        public ViewHolder setOnLongClickListener(int viewId,
                                                 View.OnLongClickListener listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return this;
        }

        public int getLayoutId() {
            return mLayoutId;
        }

    }
}
