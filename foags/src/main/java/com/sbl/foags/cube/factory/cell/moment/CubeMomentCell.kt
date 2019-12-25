package com.sbl.foags.cube.factory.cell.moment

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.cube.bean.CubeMomentBean
import com.sbl.foags.cube.factory.cell.BaseCell
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


abstract class CubeMomentCell(val context: Context, val holder: BaseRecycleViewAdapter.ViewHolder) : BaseCell<CubeMomentBean> {



    private lateinit var headPicView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var levelView: TextView
    private lateinit var releaseTimeView: TextView
    private lateinit var moreBtnView: ImageView
    private lateinit var descView: TextView
    private lateinit var priceView: TextView
    private lateinit var buyCountView: TextView
    private lateinit var commentCountView: TextView
    private lateinit var likeCountView: TextView
    private lateinit var shareBtnView: ImageView



    override fun showData(momentBean: CubeMomentBean) {


        headPicView = findViewById(R.id.headPicView)
        nickNameView = findViewById(R.id.nickNameView)
        levelView = findViewById(R.id.levelView)
        releaseTimeView = findViewById(R.id.releaseTimeView)
        moreBtnView = findViewById(R.id.moreBtnView)
        descView = findViewById(R.id.descView)
        priceView = findViewById(R.id.priceView)
        buyCountView = findViewById(R.id.buyCountView)
        commentCountView = findViewById(R.id.commentCountView)
        likeCountView = findViewById(R.id.likeCountView)
        shareBtnView = findViewById(R.id.shareBtnView)

        Glide.with(context).load(momentBean.user.headPic).transform(CircleCrop()).into(headPicView)
        nickNameView.text = momentBean.user.nickName
        levelView.text = "LV${momentBean.user.level}"
        releaseTimeView.text = momentBean.releaseTimeStr
        if(momentBean.desc.isEmpty()){
            descView.visibility = View.GONE
        }else{
            descView.visibility = View.VISIBLE
            descView.text = momentBean.desc
        }

        if(momentBean.haveSeePermission()){
            priceView.visibility = View.VISIBLE
            buyCountView.visibility = View.VISIBLE
            priceView.text = "${UIUtils.cleanZero(momentBean.price)}"
            buyCountView.text = "已有${momentBean.buyCount}人打赏"
        }else{
            priceView.visibility = View.GONE
            buyCountView.visibility = View.GONE
        }

        commentCountView.text = "${momentBean.commentCount}"
        likeCountView.text = "${momentBean.likeCount}"

        showContentBeanData(momentBean)
    }



    abstract fun showContentBeanData(data: CubeMomentBean)



    fun <T : View> findViewById(id: Int): T = holder.getView(id)
}