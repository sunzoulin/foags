package com.sbl.foags.cube.factory.cell.moment

import android.content.Context
import android.widget.FrameLayout
import com.sbl.foags.R
import com.sbl.foags.cube.bean.CubeMomentBean
import com.sbl.foags.cube.factory.cell.moment.view.MomentContentView
import com.sbl.foags.cube.factory.cell.moment.view.MomentContentViewListener
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


class CubeMomentNormalCell(context: Context, holder: BaseRecycleViewAdapter.ViewHolder) :
    CubeMomentCell(context, holder),
    MomentContentViewListener {


    private lateinit var contentBaseLayout: FrameLayout

    override fun showContentBeanData(data: CubeMomentBean) {
        contentBaseLayout = findViewById(R.id.contentBaseLayout)
        contentBaseLayout.removeAllViews()

        val momentContentView = MomentContentView(context)
        momentContentView.showContent(data.contentType, data.photoList, data.video, this)
        contentBaseLayout.addView(momentContentView)
    }

    override fun onClickMomentPhotoItem(position: Int) {

    }

    override fun onClickMomentVideoPlay() {

    }
}