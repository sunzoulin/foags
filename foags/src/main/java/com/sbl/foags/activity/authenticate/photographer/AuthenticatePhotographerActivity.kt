package com.sbl.foags.activity.authenticate.photographer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.activity.authenticate.agreement.AuthenticateAgreementDialog
import com.sbl.foags.activity.authenticate.agreement.AuthenticateAgreementListener
import com.sbl.foags.activity.authenticate.common.address.SelectAddressListener
import com.sbl.foags.activity.authenticate.common.address.SelectAddressPickerDialog
import com.sbl.foags.activity.authenticate.common.identity.SelectIdentityInfoActivity
import com.sbl.foags.activity.authenticate.common.string.SelectArrayStringListDialog
import com.sbl.foags.activity.authenticate.common.string.SelectArrayStringListListener
import com.sbl.foags.activity.authenticate.photographer.work.SelectWorkPhotoActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.GlideV4Engine
import com.sbl.foags.utils.PermissionUtils
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import java.io.File


class AuthenticatePhotographerActivity: BaseActivity(),
    View.OnClickListener,
    AuthenticateAgreementListener,
    SelectAddressListener,
    SelectArrayStringListListener {

    companion object {
        private const val REQUEST_HEAD_PIC_PHOTO: Int = 101
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private lateinit var backView: ImageView
    private lateinit var titleView: TextView

    private lateinit var headPicLayout: LinearLayout
    private lateinit var headPicView: ImageView
    private lateinit var genderLayout: LinearLayout
    private lateinit var genderTextView: TextView
    private lateinit var cityWhereLayout: LinearLayout
    private lateinit var cityWhereTextView: TextView
    private lateinit var identityAuthenticateLayout: LinearLayout
    private lateinit var identityAuthenticateTextView: TextView
    private lateinit var uploadRepresentativeWorksLayout: LinearLayout
    private lateinit var uploadRepresentativeWorksTextView: TextView


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_authenticate_photographer


    override fun initView() {
        bindViews()

        AuthenticateAgreementDialog(this, this).show()
    }


    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)

        headPicLayout = findViewById(R.id.headPicLayout)
        headPicView = findViewById(R.id.headPicView)
        genderLayout = findViewById(R.id.genderLayout)
        genderTextView = findViewById(R.id.genderTextView)
        cityWhereLayout = findViewById(R.id.cityWhereLayout)
        cityWhereTextView = findViewById(R.id.cityWhereTextView)
        identityAuthenticateLayout = findViewById(R.id.identityAuthenticateLayout)
        identityAuthenticateTextView = findViewById(R.id.identityAuthenticateTextView)
        uploadRepresentativeWorksLayout = findViewById(R.id.uploadRepresentativeWorksLayout)
        uploadRepresentativeWorksTextView = findViewById(R.id.uploadRepresentativeWorksTextView)

        backView.setOnClickListener(this)
        headPicLayout.setOnClickListener(this)
        genderLayout.setOnClickListener(this)
        cityWhereLayout.setOnClickListener(this)
        identityAuthenticateLayout.setOnClickListener(this)
        uploadRepresentativeWorksLayout.setOnClickListener(this)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.authenticate_new_model)
    }


    @SuppressLint("ResourceType")
    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            headPicLayout -> {
                getHeadPicPhoto()
            }

            genderLayout -> {
                val mDialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_gender))
                    .setListener(this)
                    .setArrayStringList(UIUtils.getStringArray(R.array.gender))
                    .build()
                mDialog.show(supportFragmentManager, "SelectArrayStringListDialog")
            }

            cityWhereLayout -> {
                val mDialog = SelectAddressPickerDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setListener(this)
                    .build()
                mDialog.show(supportFragmentManager, "SelectAddressPickerDialog")
            }

            identityAuthenticateLayout -> {
                openActivity(SelectIdentityInfoActivity::class.java)
            }

            uploadRepresentativeWorksLayout -> {
                openActivity(SelectWorkPhotoActivity::class.java)
            }
        }
    }

    override fun onStatusAgreement(isAgree: Boolean) {
        if(!isAgree){
            finish()
        }
    }

    override fun onSelectAddress(province: String, city: String, county: String) {
        cityWhereTextView.text = county
    }

    override fun onClickConfirm(index: Int, selectString: String) {
        genderTextView.text = selectString
    }


    private fun getHeadPicPhoto() {
        if (!PermissionUtils.hasPermissions(this, permissions)) {
            PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                override fun onPermissionGranted(permissionList: MutableList<String>?) {
                    if (permissionList?.size == permissions.size) {
                        getHeadPicPhotoNext()
                    }
                }

                override fun onPermissionDenied(data: MutableList<String>?) {
                    SystemUtil.gotoOpenPermission(this@AuthenticatePhotographerActivity)
                }

            }, permissions)
        } else {
            getHeadPicPhotoNext()
        }
    }

    private fun getHeadPicPhotoNext(){
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
            .forResult(REQUEST_HEAD_PIC_PHOTO)
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
        }
    }
}