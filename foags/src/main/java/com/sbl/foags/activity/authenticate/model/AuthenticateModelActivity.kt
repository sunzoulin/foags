package com.sbl.foags.activity.authenticate.model

import android.Manifest
import android.annotation.SuppressLint
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
import com.sbl.foags.activity.authenticate.agreement.AuthenticateAgreementDialog
import com.sbl.foags.activity.authenticate.agreement.AuthenticateAgreementListener
import com.sbl.foags.activity.authenticate.common.address.SelectAddressListener
import com.sbl.foags.activity.authenticate.common.address.SelectAddressPickerDialog
import com.sbl.foags.activity.authenticate.common.identity.SelectIdentityInfoActivity
import com.sbl.foags.activity.authenticate.common.state.AuthenticateInfoSubmitSuccessActivity
import com.sbl.foags.activity.authenticate.common.string.SelectArrayStringListDialog
import com.sbl.foags.activity.authenticate.common.string.SelectArrayStringListListener
import com.sbl.foags.activity.authenticate.model.three.SelectThreeCirclesListener
import com.sbl.foags.activity.authenticate.model.three.SelectThreeCirclesPickerDialog
import com.sbl.foags.activity.authenticate.model.video.RecordVideoActivity
import com.sbl.foags.activity.authenticate.model.wechat.EditWeChatActivity
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.GlideV4Engine
import com.sbl.foags.utils.PermissionUtils
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import java.io.File


class AuthenticateModelActivity: BaseActivity(),
    View.OnClickListener,
    AuthenticateAgreementListener,
    SelectAddressListener,
    SelectThreeCirclesListener {


    companion object {
        private const val REQUEST_HEAD_PIC_PHOTO: Int = 101
    }

    private val photoPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val videoPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView

    private lateinit var headPicLayout: LinearLayout
    private lateinit var headPicView: ImageView
    private lateinit var occupationTagLayout: LinearLayout
    private lateinit var occupationTagTextView: TextView
    private lateinit var genderLayout: LinearLayout
    private lateinit var genderTextView: TextView
    private lateinit var heightLayout: LinearLayout
    private lateinit var heightTextView: TextView
    private lateinit var weightLayout: LinearLayout
    private lateinit var weightTextView: TextView
    private lateinit var threeCirclesLayout: LinearLayout
    private lateinit var threeCirclesTextView: TextView
    private lateinit var constellationLayout: LinearLayout
    private lateinit var constellationTextView: TextView
    private lateinit var cityWhereLayout: LinearLayout
    private lateinit var cityWhereTextView: TextView
    private lateinit var mostSatisfactoryPositionLayout: LinearLayout
    private lateinit var mostSatisfactoryPositionTextView: TextView
    private lateinit var viewsOnSexLayout: LinearLayout
    private lateinit var viewsOnSexTextView: TextView
    private lateinit var identityAuthenticateLayout: LinearLayout
    private lateinit var identityAuthenticateTextView: TextView
    private lateinit var videoAuthenticateLayout: LinearLayout
    private lateinit var videoAuthenticateTextView: TextView
    private lateinit var addWeChatLayout: LinearLayout
    private lateinit var addWeChatTextView: TextView

    private lateinit var submitView: TextView


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
        headPicLayout = findViewById(R.id.headPicLayout)
        headPicView = findViewById(R.id.headPicView)
        occupationTagLayout = findViewById(R.id.occupationTagLayout)
        occupationTagTextView = findViewById(R.id.occupationTagTextView)
        genderLayout = findViewById(R.id.genderLayout)
        genderTextView = findViewById(R.id.genderTextView)
        heightLayout = findViewById(R.id.heightLayout)
        heightTextView = findViewById(R.id.heightTextView)
        weightLayout = findViewById(R.id.weightLayout)
        weightTextView = findViewById(R.id.weightTextView)
        threeCirclesLayout = findViewById(R.id.threeCirclesLayout)
        threeCirclesTextView = findViewById(R.id.threeCirclesTextView)
        constellationLayout = findViewById(R.id.constellationLayout)
        constellationTextView = findViewById(R.id.constellationTextView)
        cityWhereLayout = findViewById(R.id.cityWhereLayout)
        cityWhereTextView = findViewById(R.id.cityWhereTextView)
        mostSatisfactoryPositionLayout = findViewById(R.id.mostSatisfactoryPositionLayout)
        mostSatisfactoryPositionTextView = findViewById(R.id.mostSatisfactoryPositionTextView)
        viewsOnSexLayout = findViewById(R.id.viewsOnSexLayout)
        viewsOnSexTextView = findViewById(R.id.viewsOnSexTextView)
        identityAuthenticateLayout = findViewById(R.id.identityAuthenticateLayout)
        identityAuthenticateTextView = findViewById(R.id.identityAuthenticateTextView)
        videoAuthenticateLayout = findViewById(R.id.videoAuthenticateLayout)
        videoAuthenticateTextView = findViewById(R.id.videoAuthenticateTextView)
        addWeChatLayout = findViewById(R.id.addWeChatLayout)
        addWeChatTextView = findViewById(R.id.addWeChatTextView)
        submitView = findViewById(R.id.submitView)

        backView.setOnClickListener(this)
        headPicLayout.setOnClickListener(this)
        occupationTagLayout.setOnClickListener(this)
        genderLayout.setOnClickListener(this)
        heightLayout.setOnClickListener(this)
        weightLayout.setOnClickListener(this)
        threeCirclesLayout.setOnClickListener(this)
        constellationLayout.setOnClickListener(this)
        cityWhereLayout.setOnClickListener(this)
        mostSatisfactoryPositionLayout.setOnClickListener(this)
        viewsOnSexLayout.setOnClickListener(this)
        identityAuthenticateLayout.setOnClickListener(this)
        videoAuthenticateLayout.setOnClickListener(this)
        addWeChatLayout.setOnClickListener(this)
        submitView.setOnClickListener(this)
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

            occupationTagLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_occupation_tag))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            occupationTagTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.occupationTag))
                    .build()
                dialog.show(supportFragmentManager, "SelectOccupationTagDialog")
            }

            genderLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_gender))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            genderTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.gender))
                    .build()
                dialog.show(supportFragmentManager, "SelectGenderDialog")
            }

            heightLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_height))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            heightTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.height))
                    .build()
                dialog.show(supportFragmentManager, "SelectHeightDialog")
            }

            weightLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_weight))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            weightTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.weight))
                    .build()
                dialog.show(supportFragmentManager, "SelectWeightDialog")
            }

            threeCirclesLayout -> {
                val dialog = SelectThreeCirclesPickerDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setListener(this)
                    .setBustList(UIUtils.getStringArray(R.array.bustList))
                    .setWaistList(UIUtils.getStringArray(R.array.waistList))
                    .setHipList(UIUtils.getStringArray(R.array.hipList))
                    .build()
                dialog.show(supportFragmentManager, "SelectThreeCirclesDialog")
            }

            constellationLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.select_constellation))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            constellationTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.constellation))
                    .build()
                dialog.show(supportFragmentManager, "SelectConstellationDialog")
            }

            cityWhereLayout -> {
                val dialog = SelectAddressPickerDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setListener(this)
                    .build()
                dialog.show(supportFragmentManager, "SelectAddressDialog")
            }

            mostSatisfactoryPositionLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.please_select))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            mostSatisfactoryPositionTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.mostSatisfactoryPosition))
                    .build()
                dialog.show(supportFragmentManager, "SelectMostSatisfactoryPositionDialog")
            }

            viewsOnSexLayout -> {
                val dialog = SelectArrayStringListDialog.Builder()
                    .setCyclic(false)
                    .setWheelItemTextNormalColor(UIUtils.getColor(R.color.color_9EA7B9))
                    .setWheelItemTextSelectorColor(UIUtils.getColor(R.color.color_333333))
                    .setWheelItemTextSize(18)
                    .setTitle(UIUtils.getString(R.string.please_select))
                    .setListener(object: SelectArrayStringListListener {
                        override fun onClickConfirm(index: Int, selectString: String) {
                            viewsOnSexTextView.text = selectString
                        }
                    })
                    .setArrayStringList(UIUtils.getStringArray(R.array.viewsOnSex))
                    .build()
                dialog.show(supportFragmentManager, "SelectViewsOnSexLayoutDialog")
            }

            identityAuthenticateLayout -> {
                openActivity(SelectIdentityInfoActivity::class.java)
            }

            videoAuthenticateLayout -> {
                goVideoAuthenticate()
            }

            addWeChatLayout -> {
                openActivity(EditWeChatActivity::class.java)
            }

            submitView -> {
                openActivity(AuthenticateInfoSubmitSuccessActivity::class.java)
                finish()
            }
        }
    }


    override fun onStatusAgreement(isAgree: Boolean) {
        if(!isAgree){
            finish()
        }
    }


    private fun goVideoAuthenticate(){
        if (!PermissionUtils.hasPermissions(this, videoPermissions)) {
            PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                override fun onPermissionGranted(permissionList: MutableList<String>?) {
                    if (permissionList?.size == videoPermissions.size) {
                        openActivity(RecordVideoActivity::class.java)
                    }
                }

                override fun onPermissionDenied(data: MutableList<String>?) {
                    SystemUtil.gotoOpenPermission(this@AuthenticateModelActivity)
                }

            }, videoPermissions)
        } else {
            openActivity(RecordVideoActivity::class.java)
        }
    }


    override fun onSelectAddress(province: String, city: String, county: String) {
        cityWhereTextView.text = county
    }



    private fun getHeadPicPhoto() {
        if (!PermissionUtils.hasPermissions(this, photoPermissions)) {
            PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                override fun onPermissionGranted(permissionList: MutableList<String>?) {
                    if (permissionList?.size == photoPermissions.size) {
                        getHeadPicPhotoNext()
                    }
                }

                override fun onPermissionDenied(data: MutableList<String>?) {
                    SystemUtil.gotoOpenPermission(this@AuthenticateModelActivity)
                }

            }, photoPermissions)
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


    override fun onSelectThreeCircles(bust: String, waist: String, hip: String) {
        threeCirclesTextView.text = "$bust - $waist - $hip"
    }
}