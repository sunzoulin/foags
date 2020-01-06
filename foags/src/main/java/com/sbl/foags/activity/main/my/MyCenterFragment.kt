package com.sbl.foags.activity.main.my

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.moenybag.MyMoneyBagActivity
import com.sbl.foags.activity.setting.SettingActivity
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.view.MySwitchView
import com.sbl.foags.view.UserMemberView


class MyCenterFragment: BaseFragment(), OnRefreshListener, View.OnClickListener {


    private lateinit var swipeToLoadLayout: SwipeToLoadLayout

    private lateinit var settingView: ImageView
    private lateinit var nickNameView: TextView
    private lateinit var memberView: UserMemberView
    private lateinit var starsLayout: LinearLayout
    private lateinit var starsTextView: TextView
    private lateinit var diamondLayout: LinearLayout
    private lateinit var diamondTextView: TextView
    private lateinit var headPicView: ImageView
    private lateinit var friendLayout: LinearLayout
    private lateinit var friendTextView: TextView
    private lateinit var followLayout: LinearLayout
    private lateinit var followTextView: TextView
    private lateinit var fansLayout: LinearLayout
    private lateinit var fansTextView: TextView
    private lateinit var unlockingTimesView: TextView
    private lateinit var shareFriendsView: TextView
    private lateinit var todoShareView: TextView

    private lateinit var dailyTaskLayout: LinearLayout
    private lateinit var moneyBagLayout: LinearLayout
    private lateinit var collectionBoughtLayout: LinearLayout
    private lateinit var authenticationCenterLayout: LinearLayout
    private lateinit var notDisturbSwitchView: MySwitchView
    private lateinit var safeSwitchView: MySwitchView
    private lateinit var serviceCenterLayout: LinearLayout



    override fun initLayout(): Int = R.layout.fragment_my_center

    override fun initView() {
        bindViews()

        setData()
    }

    private fun bindViews(){
        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        settingView = getViewById(R.id.settingView)
        nickNameView = getViewById(R.id.nickNameView)
        memberView = getViewById(R.id.memberView)
        starsLayout = getViewById(R.id.starsLayout)
        starsTextView = getViewById(R.id.starsTextView)
        diamondLayout = getViewById(R.id.diamondLayout)
        diamondTextView = getViewById(R.id.diamondTextView)
        headPicView = getViewById(R.id.headPicView)
        friendLayout = getViewById(R.id.friendLayout)
        friendTextView = getViewById(R.id.friendTextView)
        followLayout = getViewById(R.id.followLayout)
        followTextView = getViewById(R.id.followTextView)
        fansLayout = getViewById(R.id.fansLayout)
        fansTextView = getViewById(R.id.fansTextView)
        unlockingTimesView = getViewById(R.id.unlockingTimesView)
        shareFriendsView = getViewById(R.id.shareFriendsView)
        todoShareView = getViewById(R.id.todoShareView)

        dailyTaskLayout = getViewById(R.id.dailyTaskLayout)
        moneyBagLayout = getViewById(R.id.moneyBagLayout)
        collectionBoughtLayout = getViewById(R.id.collectionBoughtLayout)
        authenticationCenterLayout = getViewById(R.id.authenticationCenterLayout)
        notDisturbSwitchView = getViewById(R.id.notDisturbSwitchView)
        safeSwitchView = getViewById(R.id.safeSwitchView)
        serviceCenterLayout = getViewById(R.id.serviceCenterLayout)

        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)

        settingView.setOnClickListener(this)
        starsLayout.setOnClickListener(this)
        diamondLayout.setOnClickListener(this)
        headPicView.setOnClickListener(this)
        friendLayout.setOnClickListener(this)
        followLayout.setOnClickListener(this)
        fansLayout.setOnClickListener(this)
        todoShareView.setOnClickListener(this)

        dailyTaskLayout.setOnClickListener(this)
        moneyBagLayout.setOnClickListener(this)
        collectionBoughtLayout.setOnClickListener(this)
        authenticationCenterLayout.setOnClickListener(this)
        notDisturbSwitchView.setOnClickListener(this)
        safeSwitchView.setOnClickListener(this)
        serviceCenterLayout.setOnClickListener(this)

    }

    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }

    override fun onClick(v: View?) {
        when(v){
            moneyBagLayout -> {
                openActivity(MyMoneyBagActivity::class.java)
            }

            settingView -> {
                openActivity(SettingActivity::class.java)
            }
        }
    }

    fun setData(){
        nickNameView.text = "随心就睡觉"

        memberView.setLevel(22)

        starsTextView.text = "3224"
        diamondTextView.text = "300"
        Glide.with(requireContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578054129644&di=93980a815c09b29c3da4327fa4a6006d&imgtype=0&src=http%3A%2F%2Fpic4.zhimg.com%2F50%2Fv2-848b1a190d937e270e8d062d00865493_hd.jpg").transform(CircleCrop()).into(headPicView)
        friendTextView.text = "88"
        followTextView.text = "123"
        fansTextView.text = "438"
        unlockingTimesView.text = "2"
        shareFriendsView.text = "234"

        safeSwitchView.isOpen = true
    }
}