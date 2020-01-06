package com.sbl.foags.activity.register

import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.statusbar.StatusBarUtil


class RegisterActivity : BaseActivity() {

    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_register


    override fun initView() {
    }

    override fun loadData() {
    }

}