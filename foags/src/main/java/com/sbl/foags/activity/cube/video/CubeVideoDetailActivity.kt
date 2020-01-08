package com.sbl.foags.activity.cube.video

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.cube.comment.dialog.EditCommentSendListener
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.exoplayer.ExoPlayerLayout
import com.sbl.foags.exoplayer.ExoPlayerListener
import com.sbl.foags.utils.statusbar.StatusBarUtil


class CubeVideoDetailActivity: BaseActivity(),
    ExoPlayerListener,
    OnRefreshListener,
    View.OnClickListener,
    EditCommentSendListener,
    NestedScrollView.OnScrollChangeListener {


    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var scrollView: NestedScrollView

    private lateinit var backView: ImageView
    private lateinit var moreView: ImageView
    private lateinit var playerContentLayout: FrameLayout

    private var videoPlayerView: ExoPlayerLayout? = null


    override fun initStatusBarMode() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        StatusBarUtil.setTranslucentStatus(this)
    }


    override fun initLayout(): Int = R.layout.activity_cube_video_detail


    override fun initView() {
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout)
        scrollView = findViewById(R.id.scrollView)
        backView = findViewById(R.id.backView)
        moreView = findViewById(R.id.moreView)
        playerContentLayout = findViewById(R.id.playerContentLayout)

        moreView.visibility = View.VISIBLE

        scrollView.setOnScrollChangeListener(this)
        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)
    }

    override fun loadData() {
        setVideoData("https://img-album.youguoquan.com/202001151746/ecaa119758671787b77f774f746434b2/prod/video/5ccd1bf08c661d5f0d5463e0/cbb_zNdyP8hd.m3u8")
    }

    override fun playerStart() {

    }

    override fun playerEnd() {

    }

    override fun playerError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (videoPlayerView != null) {
            videoPlayerView!!.onDestroy()
        }
    }

    override fun onResume() {
        super.onResume()
        if (videoPlayerView != null) {
            videoPlayerView!!.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoPlayerView != null) {
            videoPlayerView!!.onPause()
        }
    }

    override fun onRefresh() {
        setVideoData("https://img-album.youguoquan.com/202001151746/ecaa119758671787b77f774f746434b2/prod/video/5ccd1bf08c661d5f0d5463e0/cbb_zNdyP8hd.m3u8")
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }


    override fun onClick(v: View?) {
        when(v){


        }
    }

    override fun onSendComment(content: String) {

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(videoPlayerView == null){
            return
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            contentGroupManager.removeLandScapeVideoView(videoPlayerView)
            playerContentLayout.removeView(videoPlayerView)
            playerContentLayout.addView(videoPlayerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        } else {
            playerContentLayout.removeView(videoPlayerView)
            contentGroupManager.removeLandScapeVideoView(videoPlayerView)
            contentGroupManager.addLandScapeVideoView(videoPlayerView)
        }
    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if(v != null) {
            val contentView = v.getChildAt(0)
            swipeToLoadLayout.isRefreshEnabled =
                !(contentView != null && contentView.measuredHeight == (v.scrollY + v.height))
        }
    }


    fun setVideoData(url: String){
        if(videoPlayerView != null){
            videoPlayerView!!.release()
        }
        videoPlayerView = ExoPlayerLayout(this)
        videoPlayerView!!.setLaunchDate(this, this, url)
        playerContentLayout.addView(videoPlayerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        videoPlayerView!!.play(false)
    }

    override fun onBackPressed() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }else{
            finish()
        }
    }
}