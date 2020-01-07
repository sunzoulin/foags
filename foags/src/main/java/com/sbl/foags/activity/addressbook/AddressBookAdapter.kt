package com.sbl.foags.activity.addressbook

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.addressbook.data.AddressBookType
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*


class AddressBookAdapter(val context: Context, private val type: AddressBookType, list: ArrayList<User>) : BaseRecycleViewAdapter<User>(context, list) {

    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_address_book

    override fun convert(holder: ViewHolder, item: User, position: Int) {
        val headPicView = holder.getView<ImageView>(R.id.headPicView)
        val nickNameView = holder.getView<TextView>(R.id.nickNameView)
        val descView = holder.getView<TextView>(R.id.descView)
        val followView = holder.getView<TextView>(R.id.followView)

        Glide.with(context).load(item.headPic).transform(CircleCrop()).into(headPicView)

        nickNameView.text = item.nickName


        if(item.signature.isEmpty()){
            descView.visibility = View.INVISIBLE
        }else{
            descView.visibility = View.VISIBLE
            descView.text = item.address
        }

        when(item.followStatus){
            UserFollowStatus.Already -> {
                followView.setBackgroundResource(R.drawable.bg_address_book_follow)
                followView.setTextColor(UIUtils.getColor(R.color.color_BECCD9))
            }
            UserFollowStatus.Mutual -> {
                followView.setBackgroundResource(R.drawable.bg_address_book_mutual_follow)
                followView.setTextColor(UIUtils.getColor(R.color.color_BECCD9))
            }
            UserFollowStatus.Not -> {
                followView.setBackgroundResource(R.drawable.bg_address_book_not_follow)
                followView.setTextColor(UIUtils.getColor(R.color.color_FD596A))
            }
        }
    }
}