package com.sbl.foags.activity.authenticate.center

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.activity.authenticate.model.AuthenticateModelActivity
import com.sbl.foags.activity.authenticate.photographer.AuthenticatePhotographerActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.MyAspectRatioLinearLayout


class AuthenticateCenterActivity: BaseActivity(), View.OnClickListener {

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var authModelLayout: MyAspectRatioLinearLayout
    private lateinit var authPhotographerLayout: MyAspectRatioLinearLayout


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_authenticate_center

    override fun initView() {
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        authModelLayout = findViewById(R.id.authModelLayout)
        authPhotographerLayout = findViewById(R.id.authPhotographerLayout)

        backView.setOnClickListener(this)
        authModelLayout.setOnClickListener(this)
        authPhotographerLayout.setOnClickListener(this)
    }

    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.authentication_center)
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            authModelLayout -> {
                openActivity(AuthenticateModelActivity::class.java)
            }

            authPhotographerLayout -> {
                openActivity(AuthenticatePhotographerActivity::class.java)
            }
        }
    }
}