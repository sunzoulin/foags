package com.sbl.foags.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.sbl.foags.R
import com.sbl.foags.manager.ActivityManager
import com.sbl.foags.manager.IndicatorManager
import com.sbl.foags.rxbus.RxManager
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.LoadingLayout

abstract class BaseActivity : AppCompatActivity(), BaseContractNew.BaseView, LoadingLayout.EmptyRefreshListener, LoadingLayout.ErrorRefreshListener {


    protected lateinit var mLoadingLayout: LoadingLayout

    protected lateinit var rootView: ViewGroup

    private var mRxManager: RxManager? = null

    protected var showBackTip: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBarMode()

        setContentView(R.layout.activity_base)
        rootView = findViewById(R.id.activity_base)

        mLoadingLayout = findViewById(R.id.loading_layout)
        mLoadingLayout.addEmptyRefreshListener(this)
        mLoadingLayout.addErrorRefreshListener(this)

        mLoadingLayout.addView(layoutInflater.inflate(initLayout(), mLoadingLayout, false))

        initView()

        loadData()
    }


    abstract fun initStatusBarMode()

    abstract fun initLayout(): Int

    abstract fun initView()

    abstract fun loadData()


    fun getRxManager(): RxManager {
        if (mRxManager == null) {
            mRxManager = RxManager()
        }
        return mRxManager!!
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mRxManager != null) {
            mRxManager!!.clear()
            mRxManager = null
        }
    }

    override fun finish() {
        val fm = supportFragmentManager
        val fragments = fm.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                if (f is BaseFragment) {
                    f.finish()
                }
            }
        }
        super.finish()
    }


    fun getLoadingLayout(): LoadingLayout {
        return mLoadingLayout
    }

    override fun emptyRefresh() {
        loadData()
    }

    override fun errorRefresh() {
        loadData()
    }

    override fun showBaseLoading() {
        mLoadingLayout.showLoading()
    }

    override fun showBaseContent() {
        mLoadingLayout.showContent()
    }

    override fun showBaseEmpty() {
        mLoadingLayout.showEmpty()
    }

    override fun showBaseNetError() {
        mLoadingLayout.showError()
    }

    override fun showLoading() {
        IndicatorManager.showLoading(this)
    }

    override fun hideLoading() {
        IndicatorManager.dismissLoading()
    }

    override fun showSuccess(msg: String?) {
        IndicatorManager.showSuccess(msg)
    }

    override fun showErrorMsg(msg: String?) {
        IndicatorManager.showError(msg)
    }

    override fun showErrorMsg(msg: Int) {
        IndicatorManager.showError(msg)
    }


    fun openActivity(pClass: Class<*>) {
        openActivity(pClass, null)
    }

    fun openActivity(pClass: Class<*>, pBundle: Bundle?) {
        val intent = Intent(this@BaseActivity, pClass)
        if (pBundle != null) {
            intent.putExtras(pBundle)
        }
        startActivity(intent)
    }

    fun openActivity(context: Context, pClass: Class<*>, pBundle: Bundle?) {
        val intent = Intent(context, pClass)
        if (pBundle != null) {
            intent.putExtras(pBundle)
        }
        context.startActivity(intent)
    }


    /**
     * 上次点击返回的时间
     */
    private var lastBackTime: Long = 0

    override fun onBackPressed() {

        // Fragment的消耗优先于Activity
        // 如果被Fragment消耗掉，事件结束
        val fm = supportFragmentManager
        val fragments = fm.fragments
        if (fragments.size > 0) {
            for (f in fragments) {
                if (f is BaseFragment) {
                    if (f.onBackPress()) {
                        return
                    }
                }
            }
        }

        // activity得到返回事件
        if (showBackTip) {
            if (System.currentTimeMillis() - lastBackTime < 2000) {
                confirmBack()
            } else {
                // 执行确认返回的操作
                showBackTip()
                lastBackTime = System.currentTimeMillis()
            }
        } else {
            super.onBackPressed()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (showBackTip) {
                if (System.currentTimeMillis() - lastBackTime < 2000) {
                    confirmBack()
                } else {
                    // 执行确认返回的操作
                    showBackTip()
                    lastBackTime = System.currentTimeMillis()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)

    }


    /**
     * 点击返回的提示
     */
    protected open fun showBackTip() {}

    /**
     * 确认返回的操作
     */
    protected open fun confirmBack() {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val fm = supportFragmentManager
        val fragments = fm.fragments
        if (fragments != null && fragments.size > 0) {
            for (f in fragments) {
                f.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    fun exitApp() {
        ActivityManager.getInstance().killAllActivity()
        finish()
    }
}