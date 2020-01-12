package com.sbl.foags.activity.authenticate.common.state

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil


class AuthenticateInfoSubmitSuccessActivity: BaseActivity(), View.OnClickListener {


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var weChatLayout: LinearLayout
    private lateinit var weChatView: TextView
    private lateinit var submitView: TextView

    private var weChat = "bbtangkf"

    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_authenticate_info_submit_success


    override fun initView() {
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        weChatLayout = findViewById(R.id.weChatLayout)
        weChatView = findViewById(R.id.weChatView)
        submitView = findViewById(R.id.submitView)

        backView.setOnClickListener(this)
        weChatLayout.setOnClickListener(this)
        submitView.setOnClickListener(this)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.submit_success)
        weChatView.text = weChat
    }


    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            weChatLayout -> {
                if(copyText(weChat)){
                    Toast.makeText(this, UIUtils.getString(R.string.copied_to_pasteboard), Toast.LENGTH_SHORT).show()
                }
            }

            submitView -> {
                finish()
            }
        }
    }

    private fun copyText(copyStr: String): Boolean {
        return try {
            val cm = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newPlainText("Label", copyStr)
            cm.setPrimaryClip(mClipData)
            true
        } catch (e: Exception) {
            false
        }
    }
}