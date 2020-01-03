package com.sbl.foags.activity.rank

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.rank.data.RankBean
import com.sbl.foags.activity.rank.data.RankType
import com.sbl.foags.view.UserLevelView
import com.sbl.foags.view.UserMemberView
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*


class TypeCycleRankListAdapter(val context: Context, private val type: RankType, list: ArrayList<RankBean>) : BaseRecycleViewAdapter<RankBean>(context, list) {

    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_type_cycle_rank_list

    override fun convert(holder: ViewHolder, item: RankBean, position: Int) {


        val rankingLevelView = holder.getView<TextView>(R.id.rankingLevelView)
        val headPicView = holder.getView<ImageView>(R.id.headPicView)
        val nickNameView = holder.getView<TextView>(R.id.nickNameView)

        val levelView = holder.getView<UserLevelView>(R.id.levelView)

        val memberView = holder.getView<UserMemberView>(R.id.memberView)

        val descView = holder.getView<TextView>(R.id.descView)
        val rankTrendView = holder.getView<ImageView>(R.id.rankTrendView)

        rankingLevelView.text = "${position + 4}"


        Glide.with(context).load(item.user.headPic).transform(CircleCrop()).into(headPicView)
        nickNameView.text = item.user.nickName

        if(type == RankType.HotRich){
            levelView.visibility = View.GONE

            memberView.visibility = View.VISIBLE
            memberView.setLevel(item.user.memberLevel)

        }else{
            levelView.visibility = View.VISIBLE
            levelView.setLevel(item.user.level)

            memberView.visibility = View.GONE
        }


        when (type) {
            RankType.NearRelation -> {
                descView.text = "作品${item.buyCount}次打赏"
            }
            RankType.HotLive -> {
                descView.text = "直播${item.liveTime}小时"
            }
            RankType.HotRich -> {
                descView.text = "贡献${item.lollipopCount}棒棒糖"
            }
        }

        rankTrendView.setImageResource(R.drawable.ic_qmb_up)
    }
}