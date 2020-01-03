package com.sbl.foags.cube.factory.cell.home

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.cube.bean.CubeWorkBean
import com.sbl.foags.cube.factory.cell.BaseCell
import com.sbl.foags.cube.factory.cell.home.view.WorkContentView
import com.sbl.foags.cube.factory.cell.home.view.WorkContentViewListener
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.UserLevelView
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


class CubeWorkCell(val context: Context,
                               private val holder: BaseRecycleViewAdapter.ViewHolder) : BaseCell<CubeWorkBean>,
        View.OnClickListener,
        WorkContentViewListener {


    companion object {
        @JvmStatic
        fun getLayoutId(): Int = R.layout.cell_cube_work
    }

    private lateinit var contentBaseLayout: FrameLayout
    private lateinit var headPicView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var levelView: UserLevelView
    private lateinit var descView: TextView
    private lateinit var priceView: TextView


    override fun showData(workBean: CubeWorkBean) {

        contentBaseLayout = findViewById(R.id.contentBaseLayout)
        headPicView = findViewById(R.id.headPicView)
        nickNameView = findViewById(R.id.nickNameView)
        levelView = findViewById(R.id.levelView)
        descView = findViewById(R.id.descView)
        priceView = findViewById(R.id.priceView)


        Glide.with(context).load(workBean.user.headPic).transform(CircleCrop()).into(headPicView)
        nickNameView.text = workBean.user.nickName
        if(workBean.user.level > 0){
            levelView.visibility = View.VISIBLE
            levelView.setLevel(workBean.user.level)
        }else{
            levelView.visibility = View.GONE
        }


        if(workBean.haveSeePermission()){
            descView.visibility = View.VISIBLE
            priceView.visibility = View.VISIBLE
            descView.text = "${workBean.buyCount}次打赏"
            priceView.text = "${UIUtils.cleanZero(workBean.price)}"
        }else{
            descView.visibility = View.INVISIBLE
            priceView.visibility = View.GONE
        }

        contentBaseLayout.removeAllViews()

        val contentView = WorkContentView(context)
        contentView.showContent(workBean.contentType,
            workBean.totalPhotoCount,
            workBean.photoList,
            workBean.video,
            workBean.duration,
            this)

        contentBaseLayout.addView(contentView)
    }


    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    fun <T : View> findViewById(id: Int): T = holder.getView(id)

    override fun onClickWorkPhotoItem(position: Int) {

    }

    override fun onClickWorkVideoPlay() {

    }
}