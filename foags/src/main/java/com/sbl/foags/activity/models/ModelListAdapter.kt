package com.sbl.foags.activity.models

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.models.data.ModelListType
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.view.UserLevelView
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*


class ModelListAdapter(val context: Context, private val type: ModelListType, list: ArrayList<User>) : BaseRecycleViewAdapter<User>(context, list) {

    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_model_list

    override fun convert(holder: ViewHolder, item: User, position: Int) {
        val headPicView = holder.getView<ImageView>(R.id.headPicView)
        val nickNameView = holder.getView<TextView>(R.id.nickNameView)
        val levelView = holder.getView<UserLevelView>(R.id.levelView)
        val addressView = holder.getView<TextView>(R.id.addressView)
        val fansView = holder.getView<TextView>(R.id.fansView)
        val tagsView = holder.getView<TextView>(R.id.tagsView)
        val followView = holder.getView<TextView>(R.id.followView)

        Glide.with(context).load(item.headPic).transform(CircleCrop()).into(headPicView)

        nickNameView.text = item.nickName

        if(item.level > 0){
            levelView.visibility = View.VISIBLE
            levelView.setLevel(item.level)
        }else{
            levelView.visibility = View.GONE
        }

        if(item.address.isEmpty()){
            addressView.visibility = View.GONE
        }else{
            addressView.visibility = View.VISIBLE
            addressView.text = item.address
        }

        fansView.text = "${item.fansCount}"

        var tagsResult = ""

        if(!item.tags.isNullOrEmpty()){
            for(t in item.tags!!){
                tagsResult = "$tagsResult$t "
            }
        }

        if(tagsResult.isEmpty()){
            tagsView.visibility = View.INVISIBLE
        }else{
            tagsView.visibility = View.VISIBLE
            tagsView.text = tagsResult
        }


        if(item.followStatus == UserFollowStatus.Already || item.followStatus == UserFollowStatus.Mutual){
            followView.setBackgroundResource(R.drawable.bg_user_follow)
        }else{
            followView.setBackgroundResource(R.drawable.bg_user_not_follow)
        }
    }
}