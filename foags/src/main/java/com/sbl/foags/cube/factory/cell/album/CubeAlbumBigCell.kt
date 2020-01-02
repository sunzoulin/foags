package com.sbl.foags.cube.factory.cell.album

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.cube.bean.CubeAlbumBean
import com.sbl.foags.cube.factory.cell.BaseCell
import com.sbl.foags.utils.GlideRoundTransform
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


class CubeAlbumBigCell(val context: Context,
                       private val holder: BaseRecycleViewAdapter.ViewHolder) : BaseCell<CubeAlbumBean>,
        View.OnClickListener {

    companion object {
        @JvmStatic
        fun getLayoutId(): Int = R.layout.cell_cube_album_big
    }


    private lateinit var albumCoverView: ImageView
    private lateinit var headPicView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var descView: TextView
    private lateinit var countView: TextView
    private lateinit var priceView: TextView


    override fun showData(albumBean: CubeAlbumBean) {

        albumCoverView = findViewById(R.id.albumCoverView)
        headPicView = findViewById(R.id.headPicView)
        nickNameView = findViewById(R.id.nickNameView)
        descView = findViewById(R.id.descView)
        countView = findViewById(R.id.countView)
        priceView = findViewById(R.id.priceView)


        Glide.with(context).load(albumBean.albumCover).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(albumCoverView)
        Glide.with(context).load(albumBean.user.headPic).transform(CircleCrop()).into(headPicView)
        nickNameView.text = albumBean.user.nickName


        if(albumBean.haveSeePermission()){
            descView.visibility = View.VISIBLE
            priceView.visibility = View.VISIBLE
            descView.text = "${albumBean.buyCount}次打赏"
            priceView.text = "${UIUtils.cleanZero(albumBean.price)}"
        }else{
            descView.visibility = View.INVISIBLE
            priceView.visibility = View.GONE
        }

        countView.text = "${albumBean.totalPhotoCount}"
    }


    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    fun <T : View> findViewById(id: Int): T = holder.getView(id)
}