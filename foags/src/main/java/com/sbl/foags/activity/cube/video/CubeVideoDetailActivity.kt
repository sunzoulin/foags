package com.sbl.foags.activity.cube.video

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.cube.comment.AllCommentAdapter
import com.sbl.foags.activity.cube.comment.data.CommentBean
import com.sbl.foags.activity.cube.comment.dialog.EditCommentSendDialog
import com.sbl.foags.activity.cube.comment.dialog.EditCommentSendListener
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.common.cache.RxACache
import com.sbl.foags.exoplayer.ExoPlayerLayout
import com.sbl.foags.exoplayer.ExoPlayerListener
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.FScrollView
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView
import com.sbl.foags.view.recycler.other.EndlessRecyclerOnScrollListener
import com.sbl.foags.view.recycler.other.HeaderAndFooterRecyclerViewAdapter
import com.sbl.foags.view.recycler.other.RecyclerViewStateUtils


class CubeVideoDetailActivity: BaseActivity(),
    ExoPlayerListener,
    OnRefreshListener,
    View.OnClickListener,
    EditCommentSendListener {


    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var backView: ImageView
    private lateinit var moreView: ImageView
    private lateinit var playerContentLayout: FrameLayout
    private lateinit var commentRecyclerView: FloatHeaderAndFooterRecyclerView
    private lateinit var commentView: TextView
    private lateinit var commentCountBottomView: TextView
    private lateinit var likeCountView: TextView
    private lateinit var shareView: ImageView

    private lateinit var headPicView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var fansCountView: TextView
    private lateinit var followView: TextView
    private lateinit var titleView: TextView
    private lateinit var priceView: TextView
    private lateinit var timeView: TextView
    private lateinit var unlockCountView: TextView
    private lateinit var descView: TextView
    private lateinit var commentCountView: TextView


    private var videoPlayerView: ExoPlayerLayout? = null
    private lateinit var commentsAdapter: AllCommentAdapter
    private lateinit var baseAdapter: HeaderAndFooterRecyclerViewAdapter
    private val comments = arrayListOf<CommentBean>()


    override fun initStatusBarMode() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        StatusBarUtil.setTranslucentStatus(this)
    }


    override fun initLayout(): Int = R.layout.activity_cube_video_detail


    override fun initView() {
        bindViews()
    }


    private fun bindViews(){
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout)
        backView = findViewById(R.id.backView)
        moreView = findViewById(R.id.moreView)
        playerContentLayout = findViewById(R.id.playerContentLayout)
        commentRecyclerView = findViewById(R.id.commentRecyclerView)
        commentView = findViewById(R.id.commentView)
        commentCountBottomView = findViewById(R.id.commentCountBottomView)
        likeCountView = findViewById(R.id.likeCountView)
        shareView = findViewById(R.id.shareView)


        moreView.visibility = View.VISIBLE
        commentRecyclerView.addOnScrollListener(getEndlessRecyclerOnScrollListener())
        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)
        backView.setOnClickListener(this)
        moreView.setOnClickListener(this)
        commentView.setOnClickListener(this)
        shareView.setOnClickListener(this)


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        commentRecyclerView.layoutManager = layoutManager

        commentsAdapter = AllCommentAdapter(this, comments)
        baseAdapter = HeaderAndFooterRecyclerViewAdapter(commentsAdapter)
        commentRecyclerView.adapter = baseAdapter

        addHeaderView()
    }


    private fun addHeaderView() {
        val headerView = LayoutInflater.from(this).inflate(R.layout.header_cube_video_detail_layout, commentRecyclerView, false)
        headPicView = headerView.findViewById(R.id.headPicView)
        nickNameView = headerView.findViewById(R.id.nickNameView)
        fansCountView = headerView.findViewById(R.id.fansCountView)
        followView = headerView.findViewById(R.id.followView)
        titleView = headerView.findViewById(R.id.titleView)
        priceView = headerView.findViewById(R.id.priceView)
        timeView = headerView.findViewById(R.id.timeView)
        unlockCountView = headerView.findViewById(R.id.unlockCountView)
        descView = headerView.findViewById(R.id.descView)
        commentCountView = headerView.findViewById(R.id.commentCountView)


        headPicView.setOnClickListener(this)
        followView.setOnClickListener(this)
        baseAdapter.addHeaderView(headerView)
    }


    override fun loadData() {
        setVideoData("https://img-album.youguoquan.com/202001151746/ecaa119758671787b77f774f746434b2/prod/video/5ccd1bf08c661d5f0d5463e0/cbb_zNdyP8hd.m3u8")
        setCommentData()
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
            backView -> {
                finish()
            }

            commentView -> {
                EditCommentSendDialog(this, this).show()
            }
        }
    }

    override fun onSendComment(content: String) {
        val commentBean = CommentBean("999", RxACache.getInstance().user, "刚刚", content)
        comments.add(0, commentBean)
        baseAdapter.notifyDataSetChanged()

        commentRecyclerView.scrollTo(0, 0)
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


    fun setCommentData(){
        val bean = User("12", "http://b-ssl.duitang.com/uploads/item/201704/10/20170410073535_HXVfJ.thumb.700_0.jpeg", "阿斯顿说")
        bean.level = 11
        bean.memberLevel = 12
        bean.fansCount = 300
        bean.address = "北京"
        bean.followStatus = UserFollowStatus.Already
        bean.followCount = 100
        bean.tags = arrayListOf("平面模特", "网红大V")

        val commentBean = CommentBean("11", bean, "21分钟前", "我好想和你一起去旅行")

        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)

        baseAdapter.notifyDataSetChanged()
    }


    private fun getEndlessRecyclerOnScrollListener(): EndlessRecyclerOnScrollListener {
        return object : EndlessRecyclerOnScrollListener() {
            override fun onLoadNextPage(view: View) {
                super.onLoadNextPage(view)

            }

            override fun onCurrentIsFirstTop(view: View?, isTop: Boolean) {
                swipeToLoadLayout.isRefreshEnabled = isTop
            }
        }
    }
}