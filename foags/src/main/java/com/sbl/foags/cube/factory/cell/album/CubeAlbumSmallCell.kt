package com.sbl.foags.cube.factory.cell.album

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.joooonho.SelectableRoundedImageView
import com.sbl.foags.R
import com.sbl.foags.cube.bean.CubeAlbumBean
import com.sbl.foags.cube.factory.cell.BaseCell
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


class CubeAlbumSmallCell(val context: Context,
                         private val holder: BaseRecycleViewAdapter.ViewHolder) : BaseCell<CubeAlbumBean>,
        View.OnClickListener {

    companion object {
        @JvmStatic
        fun getLayoutId(): Int = R.layout.cell_cube_album_small
    }


    private lateinit var albumCoverView: SelectableRoundedImageView
    private lateinit var headPicView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var countView: TextView


    override fun showData(albumBean: CubeAlbumBean) {

        albumCoverView = findViewById(R.id.albumCoverView)
        headPicView = findViewById(R.id.headPicView)
        nickNameView = findViewById(R.id.nickNameView)
        countView = findViewById(R.id.countView)


        Glide.with(context).load(albumBean.albumCover).into(albumCoverView)

        Glide.with(context).load(albumBean.user.headPic).transform(CircleCrop()).into(headPicView)
        nickNameView.text = albumBean.user.nickName
        countView.text = "${albumBean.totalPhotoCount}"
    }


    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    fun <T : View> findViewById(id: Int): T = holder.getView(id)
}