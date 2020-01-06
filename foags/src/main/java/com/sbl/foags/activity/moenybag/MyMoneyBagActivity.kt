package com.sbl.foags.activity.moenybag

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.ScreenUtils
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.MyAspectRatioFrameLayout


class MyMoneyBagActivity: BaseActivity(), View.OnClickListener {


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView

    private lateinit var topLayout: MyAspectRatioFrameLayout
    private lateinit var myLollipopLayout: LinearLayout
    private lateinit var myLollipopTextView: TextView
    private lateinit var myDiamondsLayout: LinearLayout
    private lateinit var myDiamondsTextView: TextView
    private lateinit var rechargeLayout: LinearLayout
    private lateinit var budgetDefiniteLayout: LinearLayout
    private lateinit var cashWithdrawalLayout: LinearLayout


    override fun initStatusBarMode() {
        StatusBarUtil.setTranslucentStatus(this)
    }


    override fun initLayout(): Int = R.layout.activity_my_money_bag


    override fun initView() {

        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        topLayout = findViewById(R.id.topLayout)
        myLollipopLayout = findViewById(R.id.myLollipopLayout)
        myLollipopTextView = findViewById(R.id.myLollipopTextView)
        myDiamondsLayout = findViewById(R.id.myDiamondsLayout)
        myDiamondsTextView = findViewById(R.id.myDiamondsTextView)
        rechargeLayout = findViewById(R.id.rechargeLayout)
        budgetDefiniteLayout = findViewById(R.id.budgetDefiniteLayout)
        cashWithdrawalLayout = findViewById(R.id.cashWithdrawalLayout)

        backView.setOnClickListener(this)
        myLollipopLayout.setOnClickListener(this)
        myDiamondsLayout.setOnClickListener(this)
        rechargeLayout.setOnClickListener(this)
        budgetDefiniteLayout.setOnClickListener(this)
        cashWithdrawalLayout.setOnClickListener(this)

        ScreenUtils.addStatusHeightPadding(topLayout)
    }

    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.my_money_bag)
        myLollipopTextView.text = "438"
        myDiamondsTextView.text = "99"
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }
        }
    }
}