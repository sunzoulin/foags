package com.sbl.foags.activity.setting.about

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil


class AboutUsActivity: BaseActivity(), View.OnClickListener {

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var versionView: TextView


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_about_us


    override fun initView() {
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        versionView = findViewById(R.id.versionView)

        backView.setOnClickListener(this)
    }

    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.about_us)
        versionView.text = "${UIUtils.getString(R.string.asian_model_stars)} ${SystemUtil.getAppVersion(this)}"
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }
        }
    }
}