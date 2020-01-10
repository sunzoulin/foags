package com.sbl.foags.activity.authenticate.common.identity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.*
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import java.io.File


class SelectIdentityInfoActivity: BaseActivity(), View.OnClickListener {


    companion object {
        private const val REQUEST_POSITIVE_PHOTO: Int = 101
        private const val REQUEST_NEGATIVE_PHOTO: Int = 102
        private const val REQUEST_HOLD_PHOTO: Int = 103
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView

    private lateinit var realNameEditView: EditText
    private lateinit var identityIDNumberEditView: EditText

    private lateinit var positivePhotoLayout: LinearLayout
    private lateinit var positivePhotoNormalLayout: FrameLayout
    private lateinit var positivePhotoShowLayout: FrameLayout
    private lateinit var positivePhotoView: ImageView

    private lateinit var negativePhotoLayout: LinearLayout
    private lateinit var negativePhotoNormalLayout: FrameLayout
    private lateinit var negativePhotoShowLayout: FrameLayout
    private lateinit var negativePhotoView: ImageView

    private lateinit var holdPhotoLayout: LinearLayout
    private lateinit var holdPhotoNormalLayout: FrameLayout
    private lateinit var holdPhotoShowLayout: FrameLayout
    private lateinit var holdPhotoView: ImageView

    private lateinit var uploadView: TextView



    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_select_identity_info


    override fun initView() {
        bindViews()
    }


    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        realNameEditView = findViewById(R.id.realNameEditView)
        identityIDNumberEditView = findViewById(R.id.identityIDNumberEditView)
        positivePhotoLayout = findViewById(R.id.positivePhotoLayout)
        positivePhotoNormalLayout = findViewById(R.id.positivePhotoNormalLayout)
        positivePhotoShowLayout = findViewById(R.id.positivePhotoShowLayout)
        positivePhotoView = findViewById(R.id.positivePhotoView)
        negativePhotoLayout = findViewById(R.id.negativePhotoLayout)
        negativePhotoNormalLayout = findViewById(R.id.negativePhotoNormalLayout)
        negativePhotoShowLayout = findViewById(R.id.negativePhotoShowLayout)
        negativePhotoView = findViewById(R.id.negativePhotoView)
        holdPhotoLayout = findViewById(R.id.holdPhotoLayout)
        holdPhotoNormalLayout = findViewById(R.id.holdPhotoNormalLayout)
        holdPhotoShowLayout = findViewById(R.id.holdPhotoShowLayout)
        holdPhotoView = findViewById(R.id.holdPhotoView)
        uploadView = findViewById(R.id.uploadView)

        backView.setOnClickListener(this)
        positivePhotoLayout.setOnClickListener(this)
        negativePhotoLayout.setOnClickListener(this)
        holdPhotoLayout.setOnClickListener(this)
        uploadView.setOnClickListener(this)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.identity_authenticate)
    }


    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            positivePhotoLayout -> {
                getPhoto(REQUEST_POSITIVE_PHOTO)
            }

            negativePhotoLayout -> {
                getPhoto(REQUEST_NEGATIVE_PHOTO)
            }

            holdPhotoLayout -> {
                getPhoto(REQUEST_HOLD_PHOTO)
            }

            uploadView -> {

            }
        }
    }


    private fun getPhoto(code: Int) {
        if (!PermissionUtils.hasPermissions(this, permissions)) {
            PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                override fun onPermissionGranted(permissionList: MutableList<String>?) {
                    if (permissionList?.size == permissions.size) {
                        getPhotoNext(code)
                    }
                }

                override fun onPermissionDenied(data: MutableList<String>?) {
                    SystemUtil.gotoOpenPermission(this@SelectIdentityInfoActivity)
                }

            }, permissions)
        } else {
            getPhotoNext(code)
        }
    }


    private fun getPhotoNext(code: Int){
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
            .forResult(code)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (requestCode) {
            REQUEST_POSITIVE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path: List<String> = Matisse.obtainPathResult(data)
                    val imageFile = File(path[0])
                    if (imageFile.exists()) {
                        positivePhotoNormalLayout.visibility = View.GONE
                        positivePhotoShowLayout.visibility = View.VISIBLE
                        Glide.with(this).load(imageFile).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(positivePhotoView)
                    }

                }
            }

            REQUEST_NEGATIVE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path: List<String> = Matisse.obtainPathResult(data)
                    val imageFile = File(path[0])
                    if (imageFile.exists()) {
                        negativePhotoNormalLayout.visibility = View.GONE
                        negativePhotoShowLayout.visibility = View.VISIBLE
                        Glide.with(this).load(imageFile).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(negativePhotoView)
                    }

                }
            }

            REQUEST_HOLD_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path: List<String> = Matisse.obtainPathResult(data)
                    val imageFile = File(path[0])
                    if (imageFile.exists()) {
                        holdPhotoNormalLayout.visibility = View.GONE
                        holdPhotoShowLayout.visibility = View.VISIBLE
                        Glide.with(this).load(imageFile).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(holdPhotoView)
                    }

                }
            }
        }
    }
}