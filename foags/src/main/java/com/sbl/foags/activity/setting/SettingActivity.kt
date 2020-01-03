package com.sbl.foags.activity.setting

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.activity.login.LoginActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.manager.ActivityManager
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.utils.UIUtils


class SettingActivity: BaseActivity(), View.OnClickListener {

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView

    private lateinit var feedbackLayout: LinearLayout
    private lateinit var aboutUsLayout: LinearLayout
    private lateinit var clearCacheLayout: LinearLayout
    private lateinit var cacheTextView: TextView
    private lateinit var checkUpdateLayout: LinearLayout
    private lateinit var versionTextView: TextView
    private lateinit var logoutView: TextView


    override fun initLayout(): Int = R.layout.activity_setting

    override fun initView() {
        bindViews()
    }

    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)

        feedbackLayout = findViewById(R.id.feedbackLayout)
        aboutUsLayout = findViewById(R.id.aboutUsLayout)
        clearCacheLayout = findViewById(R.id.clearCacheLayout)
        cacheTextView = findViewById(R.id.cacheTextView)
        checkUpdateLayout = findViewById(R.id.checkUpdateLayout)
        versionTextView = findViewById(R.id.versionTextView)
        logoutView = findViewById(R.id.logoutView)

        backView.setOnClickListener(this)
        feedbackLayout.setOnClickListener(this)
        aboutUsLayout.setOnClickListener(this)
        clearCacheLayout.setOnClickListener(this)
        checkUpdateLayout.setOnClickListener(this)
        logoutView.setOnClickListener(this)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.setting)
        versionTextView.text = SystemUtil.getAppVersion(this)
    }


    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            logoutView -> {
                openActivity(LoginActivity::class.java)
                ActivityManager.getInstance().exit()
            }
        }
    }
}