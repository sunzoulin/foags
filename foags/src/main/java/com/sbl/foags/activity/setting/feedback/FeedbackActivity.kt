package com.sbl.foags.activity.setting.feedback

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils

class FeedbackActivity: BaseActivity(), View.OnClickListener {

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var feedbackView: EditText

    override fun initLayout(): Int = R.layout.activity_feedback


    override fun initView() {
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        feedbackView = findViewById(R.id.feedbackView)

        backView.setOnClickListener(this)
    }

    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.feedback)
    }

    override fun onClick(v: View?) {
        when(v) {
            backView -> {
                finish()
            }
        }
    }
}