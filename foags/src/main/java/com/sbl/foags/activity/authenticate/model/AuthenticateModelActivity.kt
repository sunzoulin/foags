package com.sbl.foags.activity.authenticate.model

import android.Manifest
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.activity.authenticate.agreement.AuthenticateAgreementDialog
import com.sbl.foags.activity.authenticate.agreement.AuthenticateAgreementListener
import com.sbl.foags.activity.authenticate.model.video.RecordVideoActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.PermissionUtils
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil


class AuthenticateModelActivity: BaseActivity(), View.OnClickListener,
    AuthenticateAgreementListener {



    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_authenticate_model


    override fun initView() {
        bindViews()

        AuthenticateAgreementDialog(this, this).show()
    }


    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)

        backView.setOnClickListener(this)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.authenticate_new_model)
    }


    override fun onClick(v: View?) {
        when(v){
            backView -> {
                if (!PermissionUtils.hasPermissions(this, permissions)) {
                    PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                        override fun onPermissionGranted(permissionList: MutableList<String>?) {
                            if (permissionList?.size == permissions.size) {
                                openActivity(RecordVideoActivity::class.java)
                            }
                        }

                        override fun onPermissionDenied(data: MutableList<String>?) {
                            SystemUtil.gotoOpenPermission(this@AuthenticateModelActivity)
                        }

                    }, permissions)
                } else {
                    openActivity(RecordVideoActivity::class.java)
                }
            }
        }
    }

    override fun onStatusAgreement(isAgree: Boolean) {
        if(!isAgree){
            finish()
        }
    }
}