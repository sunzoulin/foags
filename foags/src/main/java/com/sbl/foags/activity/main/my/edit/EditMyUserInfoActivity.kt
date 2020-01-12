package com.sbl.foags.activity.main.my.edit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.*
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import java.io.File


class EditMyUserInfoActivity: BaseActivity(), View.OnClickListener {


    companion object {
        private const val REQUEST_HEAD_PIC_PHOTO: Int = 101
        private const val REQUEST_BACKGROUND_PIC_PHOTO: Int = 102
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var headPicLayout: LinearLayout
    private lateinit var headPicView: ImageView
    private lateinit var backgroundPicLayout: LinearLayout
    private lateinit var backgroundPicView: ImageView
    private lateinit var submitView: TextView


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_edit_my_user_info


    override fun initView() {
        bindViews()
    }

    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        headPicLayout = findViewById(R.id.headPicLayout)
        headPicView = findViewById(R.id.headPicView)
        backgroundPicLayout = findViewById(R.id.backgroundPicLayout)
        backgroundPicView = findViewById(R.id.backgroundPicView)
        submitView = findViewById(R.id.submitView)

        backView.setOnClickListener(this)
        headPicLayout.setOnClickListener(this)
        backgroundPicLayout.setOnClickListener(this)
        submitView.setOnClickListener(this)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.my_user_info)
    }


    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            headPicLayout -> {
                getPicPhoto(REQUEST_HEAD_PIC_PHOTO)
            }

            backgroundPicLayout -> {
                getPicPhoto(REQUEST_BACKGROUND_PIC_PHOTO)
            }

            submitView -> {

            }
        }
    }


    private fun getPicPhoto(type: Int) {
        if (!PermissionUtils.hasPermissions(this, permissions)) {
            PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                override fun onPermissionGranted(permissionList: MutableList<String>?) {
                    if (permissionList?.size == permissions.size) {
                        getPicPhotoNext(type)
                    }
                }

                override fun onPermissionDenied(data: MutableList<String>?) {
                    SystemUtil.gotoOpenPermission(this@EditMyUserInfoActivity)
                }

            }, permissions)
        } else {
            getPicPhotoNext(type)
        }
    }


    private fun getPicPhotoNext(type: Int){
        Matisse.from(this)
            .choose(object : HashSet<MimeType>() {
                init {
                    add(MimeType.JPEG)
                    add(MimeType.PNG)
                    add(MimeType.BMP)
                    add(MimeType.WEBP)
                }
            })
            .showSingleMediaType(true)
            .countable(true)
            .maxSelectable(1)
            //.addFilter()
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideV4Engine())
            .forResult(type)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (requestCode) {
            REQUEST_HEAD_PIC_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path: List<String> = Matisse.obtainPathResult(data)
                    val imageFile = File(path[0])
                    if (imageFile.exists()) {
                        Glide.with(this).load(imageFile).transform(CircleCrop()).into(headPicView)
                    }
                }
            }

            REQUEST_BACKGROUND_PIC_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path: List<String> = Matisse.obtainPathResult(data)
                    val imageFile = File(path[0])
                    if (imageFile.exists()) {
                        Glide.with(this).load(imageFile).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(backgroundPicView)
                    }
                }
            }
        }
    }
}