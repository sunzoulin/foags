package com.sbl.foags.activity.rank

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.rank.data.RankBean
import com.sbl.foags.activity.rank.data.RankType
import com.sbl.foags.view.MyAspectRatioFrameLayout


class TypeCycleRankListHeader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    MyAspectRatioFrameLayout(context, attrs, defStyleAttr) {


    private var headerBG1View: MyAspectRatioFrameLayout
    private var secondLayout: LinearLayout
    private var secondHeadPicView: ImageView
    private var secondNameView: TextView
    private var secondDescView: TextView

    private var firstLayout: LinearLayout
    private var firstHeadPicView: ImageView
    private var firstNameView: TextView
    private var firstDescView: TextView

    private var thirdLayout: LinearLayout
    private var thirdHeadPicView: ImageView
    private var thirdNameView: TextView
    private var thirdDescView: TextView
    private var headerBG2View: ImageView


    init {
        LayoutInflater.from(context).inflate(R.layout.header_type_cycle_rank_list, this, true)
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        headerBG1View = findViewById(R.id.headerBG1View)
        secondLayout = findViewById(R.id.secondLayout)
        secondHeadPicView = findViewById(R.id.secondHeadPicView)
        secondNameView = findViewById(R.id.secondNameView)
        secondDescView = findViewById(R.id.secondDescView)
        firstLayout = findViewById(R.id.firstLayout)
        firstHeadPicView = findViewById(R.id.firstHeadPicView)
        firstNameView = findViewById(R.id.firstNameView)
        firstDescView = findViewById(R.id.firstDescView)
        thirdLayout = findViewById(R.id.thirdLayout)
        thirdHeadPicView = findViewById(R.id.thirdHeadPicView)
        thirdNameView = findViewById(R.id.thirdNameView)
        thirdDescView = findViewById(R.id.thirdDescView)
        headerBG2View = findViewById(R.id.headerBG2View)
    }

    fun showData(type: RankType, first: RankBean?, second: RankBean?, third: RankBean?) {

        when(type){

            RankType.NearRelation -> {
                headerBG1View.setBackgroundResource(R.drawable.ic_dmb_bg)
                headerBG2View.setImageResource(R.drawable.ic_dmb_ph)
            }
            RankType.HotLive -> {
                headerBG1View.setBackgroundResource(R.drawable.ic_rbb_bg)
                headerBG2View.setImageResource(R.drawable.ic_rbb_ph)
            }
            RankType.HotRich -> {
                headerBG1View.setBackgroundResource(R.drawable.ic_thb_bg)
                headerBG2View.setImageResource(R.drawable.ic_thb_ph)
            }
        }


        if (first != null) {
            firstLayout.visibility = View.VISIBLE
            showFirst(type, first)
        } else {
            firstLayout.visibility = View.INVISIBLE
        }

        if (second != null) {
            secondLayout.visibility = View.VISIBLE
            showSecond(type, second)
        } else {
            secondLayout.visibility = View.INVISIBLE
        }

        if (third != null) {
            thirdLayout.visibility = View.VISIBLE
            showThird(type, third)
        } else {
            thirdLayout.visibility = View.INVISIBLE
        }
    }

    private fun showFirst(type: RankType, first: RankBean) {
        Glide.with(context).load(first.user.headPic).transform(CircleCrop()).into(firstHeadPicView)

        firstHeadPicView.setOnClickListener { onClickHeaderView(first) }
        firstNameView.text = first.user.nickName

        when (type) {
            RankType.NearRelation -> {
                firstDescView.text = "作品${first.buyCount}次打赏"
            }
            RankType.HotLive -> {
                firstDescView.text = "直播${first.liveTime}小时"
            }
            RankType.HotRich -> {
                firstDescView.text = "贡献${first.lollipopCount}棒棒糖"
            }
        }
    }

    private fun showSecond(type: RankType, second: RankBean) {
        Glide.with(context).load(second.user.headPic).transform(CircleCrop()).into(secondHeadPicView)

        secondHeadPicView.setOnClickListener { onClickHeaderView(second) }
        secondNameView.text = second.user.nickName

        when (type) {
            RankType.NearRelation -> {
                secondDescView.text = "作品${second.buyCount}次打赏"
            }
            RankType.HotLive -> {
                secondDescView.text = "直播${second.liveTime}小时"
            }
            RankType.HotRich -> {
                secondDescView.text = "贡献${second.lollipopCount}棒棒糖"
            }
        }
    }

    private fun showThird(type: RankType, third: RankBean) {
        Glide.with(context).load(third.user.headPic).transform(CircleCrop()).into(thirdHeadPicView)

        thirdHeadPicView.setOnClickListener { onClickHeaderView(third) }
        thirdNameView.text = third.user.nickName

        when (type) {
            RankType.NearRelation -> {
                thirdDescView.text = "作品${third.buyCount}次打赏"
            }
            RankType.HotLive -> {
                thirdDescView.text = "直播${third.liveTime}小时"
            }
            RankType.HotRich -> {
                thirdDescView.text = "贡献${third.lollipopCount}棒棒糖"
            }
        }
    }

    private fun onClickHeaderView(bean: RankBean) {
//        NewUserInfoActivity.open(context, bean.userId)

    }

}