package com.sbl.foags.cube.factory.cell.home.view;

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sbl.foags.R
import com.sbl.foags.utils.GlideRoundTransform
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.MyAspectRatioFrameLayout
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.ArrayList


class WorkPhotoAdapter(val context: Context,
                       val listener: WorkContentViewListener,
                       val list: ArrayList<String>,
                       private val totalPhotoCount: Int) : BaseRecycleViewAdapter<String>(context, list) {

    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_work_photo

    override fun convert(holder: ViewHolder, item: String, position: Int) {

        val emptyView = holder.getView<View>(R.id.emptyView)
        val photoLayout = holder.getView<MyAspectRatioFrameLayout>(R.id.photoLayout)
        val photoView = holder.getView<ImageView>(R.id.photoView)
        val moreLayout = holder.getView<FrameLayout>(R.id.moreLayout)
        val moreView = holder.getView<TextView>(R.id.moreView)



        if(position == 0){
            emptyView.visibility = View.VISIBLE
        }else{
            emptyView.visibility = View.GONE
        }

        Glide.with(context).load(item).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(photoView)


        if (position == list.size - 1 && list.size < totalPhotoCount) {
            moreLayout.visibility = View.VISIBLE
            moreView.text = "${totalPhotoCount - list.size}P"
        } else {
            moreLayout.visibility = View.GONE
        }

        photoLayout.setOnClickListener {
            listener.onClickWorkPhotoItem(position)
        }
    }
}