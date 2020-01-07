package com.sbl.foags.activity.cube.photo

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.cube.comment.AllCommentActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.utils.statusbar.StatusBarUtil
import java.util.ArrayList


class CubePhotoDetailActivity: BaseActivity(), View.OnClickListener,
    ViewPager.OnPageChangeListener,
    CubePhotoDetailViewPager.onSideListener,
    CubePhotoDetailAdapter.ImageReviewAdapterListener {

    private lateinit var photoViewPager: CubePhotoDetailViewPager

    private lateinit var headerLayout: FrameLayout
    private lateinit var backView: ImageView
    private lateinit var headPicView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var currentCountView: TextView
    private lateinit var totalCountView: TextView
    private lateinit var followView: ImageView

    private lateinit var footerLayout: LinearLayout
    private lateinit var commentView: TextView
    private lateinit var commentCountView: TextView
    private lateinit var likeCountView: TextView
    private lateinit var shareView: ImageView


    private var photoDetailAdapter: CubePhotoDetailAdapter? = null

    private var viewAnimation: AlphaAnimation? = null
    private var isAnimationRun: Boolean = false
    private var isHide: Boolean = false

    private var totalPhotoCount: Int = 0
    private var images = ArrayList<String>()
    private var index: Int? = 0
    private var user: User? = null



    override fun initStatusBarMode() {
        StatusBarUtil.setTranslucentStatus(this)
    }

    override fun initLayout(): Int = R.layout.activity_cube_photo_detail

    override fun initView() {
        bindViews()
    }

    private fun bindViews(){
        photoViewPager = findViewById(R.id.photoViewPager)
        headerLayout = findViewById(R.id.headerLayout)
        backView = findViewById(R.id.backView)
        headPicView = findViewById(R.id.headPicView)
        nickNameView = findViewById(R.id.nickNameView)
        currentCountView = findViewById(R.id.currentCountView)
        totalCountView = findViewById(R.id.totalCountView)
        followView = findViewById(R.id.followView)
        footerLayout = findViewById(R.id.footerLayout)
        commentView = findViewById(R.id.commentView)
        commentCountView = findViewById(R.id.commentCountView)
        likeCountView = findViewById(R.id.likeCountView)
        shareView = findViewById(R.id.shareView)

        backView.setOnClickListener(this)
        commentView.setOnClickListener(this)

        photoViewPager.setOnSideListener(this)
        photoViewPager.addOnPageChangeListener(this)
        photoDetailAdapter = CubePhotoDetailAdapter(this)
        photoDetailAdapter!!.setListener(this)
        photoViewPager.adapter = photoDetailAdapter
    }

    override fun loadData() {
        val photos0 = arrayListOf<String>()
        photos0.add("http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg")
        photos0.add("http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg")
        photos0.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
        photos0.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")


        val user11 = User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四")
        user11.followStatus = UserFollowStatus.Mutual

        setData(photos0.size, photos0, 0, user11)
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            commentView -> {
                openActivity(AllCommentActivity::class.java)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (images.isNotEmpty()) {
            currentCountView.text = (position + 1).toString()
            totalCountView.text = totalPhotoCount.toString()
        }
    }

    override fun onLeftSide() {

    }

    override fun onRightSide() {

    }

    override fun setOnImageClickListener(position: Int) {
        if (isAnimationRun) {
            return
        }
        if (viewAnimation == null) {
            viewAnimation = if (isHide) {
                AlphaAnimation(0f, 1f)
            } else {
                AlphaAnimation(1f, 0f)
            }

            viewAnimation!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    isAnimationRun = true
                    if (isHide) {
                        headerLayout.visibility = View.VISIBLE
                        footerLayout.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    if (isHide) {
                        isHide = false
                    } else {
                        isHide = true
                        headerLayout.visibility = View.GONE
                        footerLayout.visibility = View.GONE
                    }
                    isAnimationRun = false
                    viewAnimation = null
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            viewAnimation!!.duration = 400
        }
        if (viewAnimation != null) {
            headerLayout.startAnimation(viewAnimation)
            footerLayout.startAnimation(viewAnimation)
        }
    }


    fun setData(totalPhotoCount: Int, images: ArrayList<String>, index: Int, user: User) {
        this.totalPhotoCount = totalPhotoCount
        this.images = images
        this.index = index
        this.user = user

        Glide.with(this).load(user.headPic).transform(CircleCrop()).into(headPicView)
        nickNameView.text = user.nickName

        updateFollowStatus()

        if (photoDetailAdapter != null) {
            photoDetailAdapter!!.setData(images)
            photoViewPager.currentItem = index

            onPageSelected(index)
        }
    }

    private fun updateFollowStatus(){
        if(user!!.followStatus == UserFollowStatus.Already || user!!.followStatus == UserFollowStatus.Mutual){
            followView.setImageResource(R.drawable.ic_image_followed)
        }else{
            followView.setImageResource(R.drawable.ic_image_follow)
        }
    }
}