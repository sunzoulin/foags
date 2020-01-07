package com.sbl.foags.activity.cube.comment

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.cube.comment.data.CommentBean
import com.sbl.foags.view.UserMemberView
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*


class AllCommentAdapter(val context: Context, list: ArrayList<CommentBean>) : BaseRecycleViewAdapter<CommentBean>(context, list) {


    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_all_comment

    override fun convert(holder: ViewHolder, item: CommentBean, position: Int) {
        val headPicView = holder.getView<ImageView>(R.id.headPicView)
        val nickNameView = holder.getView<TextView>(R.id.nickNameView)
        val memberView = holder.getView<UserMemberView>(R.id.memberView)
        val commentView = holder.getView<TextView>(R.id.commentView)
        val releaseTimeView = holder.getView<TextView>(R.id.releaseTimeView)

        Glide.with(context).load(item.user.headPic).transform(CircleCrop()).into(headPicView)

        nickNameView.text = item.user.nickName

        if(item.user.memberLevel > 0){
            memberView.visibility = View.VISIBLE
            memberView.setLevel(item.user.memberLevel)
        }else{
            memberView.visibility = View.GONE
        }

        commentView.text = item.comment
        releaseTimeView.text = item.releaseTimeStr
    }
}