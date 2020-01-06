package com.sbl.foags.activity.splash

import android.content.Intent
import com.sbl.foags.R
import com.sbl.foags.activity.login.LoginActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.statusbar.StatusBarUtil


class SplashActivity : BaseActivity() {


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_splash

    override fun initView() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun loadData() {
    }
}
