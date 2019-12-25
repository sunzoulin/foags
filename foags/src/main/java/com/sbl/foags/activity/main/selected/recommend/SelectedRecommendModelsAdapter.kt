package com.sbl.foags.activity.main.selected.recommend

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.bean.User
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*


class SelectedRecommendModelsAdapter(val context: Context, list: ArrayList<User>) : BaseRecycleViewAdapter<User>(context, list) {

    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_selected_recommend_models

    override fun convert(holder: ViewHolder, item: User, position: Int) {

        val modelView = holder.getView<LinearLayout>(R.id.modelView)
        val moreModelView = holder.getView<TextView>(R.id.moreModelView)
        val headView = holder.getView<ImageView>(R.id.headPicView)
        val nickNameView = holder.getView<TextView>(R.id.nickNameView)

        if (item.id == UIUtils.getString(R.string.more_models)) {

            modelView.visibility = View.INVISIBLE
            moreModelView.visibility = View.VISIBLE


        } else {
            modelView.visibility = View.VISIBLE
            moreModelView.visibility = View.INVISIBLE

            Glide.with(context).load(item.headPic).transform(CircleCrop()).into(headView)
            nickNameView.text = item.nickName

        }

    }
}