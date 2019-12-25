package com.sbl.foags.cube.factory.cell.moment

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.cube.bean.CubeMomentBean
import com.sbl.foags.cube.factory.cell.home.view.WorkContentViewListener
import com.sbl.foags.cube.factory.cell.moment.view.forward.ForwardMomentContentView
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


class CubeMomentForwardWorkCell(context: Context, holder: BaseRecycleViewAdapter.ViewHolder):
    CubeMomentCell(context, holder), WorkContentViewListener {


    private lateinit var contentBaseLayout: FrameLayout

    private lateinit var originalNickNameView: TextView
    private lateinit var originalDescView: TextView
    private lateinit var forwardMomentContentView: ForwardMomentContentView

    override fun showContentBeanData(data: CubeMomentBean) {
        contentBaseLayout = findViewById(R.id.contentBaseLayout)

        contentBaseLayout.removeAllViews()

        val contentView = LayoutInflater.from(context).inflate(R.layout.content_forward_moment_layout, null)
        originalNickNameView = contentView.findViewById(R.id.originalNickNameView)
        originalDescView = contentView.findViewById(R.id.originalDescView)
        forwardMomentContentView = contentView.findViewById(R.id.forwardMediaCoversView)

        originalNickNameView.text = "@${data.forwardWork!!.user.nickName}"
        originalDescView.text = "[${data.forwardWork!!.photographer!!.nickName}]${data.forwardWork!!.shotTime}&${data.forwardWork!!.user.nickName}"


        forwardMomentContentView.showContent(data.forwardWork!!.contentType,
            data.forwardWork!!.photoList,
            data.forwardWork!!.totalPhotoCount,
            data.forwardWork!!.video,
            data.forwardWork!!.duration,
            this)

        contentBaseLayout.addView(contentView)
    }

    override fun onClickWorkPhotoItem(position: Int) {

    }

    override fun onClickWorkVideoPlay() {

    }
}