package com.sbl.foags.activity.authenticate.model.wechat

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.activity.authenticate.common.string.SelectArrayStringListDialog
import com.sbl.foags.activity.authenticate.common.string.SelectArrayStringListListener
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil


class EditWeChatActivity: BaseActivity(),
    View.OnClickListener,
    SelectArrayStringListListener {


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView

    private lateinit var priceLayout: LinearLayout
    private lateinit var priceTextView: TextView


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_edit_wechat


    override fun initView() {
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)

        priceLayout = findViewById(R.id.priceLayout)
        priceTextView = findViewById(R.id.priceTextView)

        backView.setOnClickListener(this)
        priceLayout.setOnClickListener(this)
    }


    override fun loadData() {

        titleView.text = UIUtils.getString(R.string.add_wechat)
    }


    @SuppressLint("ResourceType")
    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            priceLayout -> {
                val mDialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_price))
                    .setListener(this)
                    .setArrayStringList(UIUtils.getStringArray(R.array.weChatPrice))
                    .build()
                mDialog.show(supportFragmentManager, "SelectWeChatPriceDialog")
            }
        }
    }


    override fun onClickConfirm(index: Int, selectString: String) {
        priceTextView.text = selectString
    }
}