package com.sbl.foags.activity.splash

import android.content.Intent
import com.sbl.foags.R
import com.sbl.foags.activity.login.LoginActivity
import com.sbl.foags.base.BaseActivity


class SplashActivity : BaseActivity() {


    override fun initLayout(): Int = R.layout.activity_splash

    override fun initView() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun loadData() {
    }
}
