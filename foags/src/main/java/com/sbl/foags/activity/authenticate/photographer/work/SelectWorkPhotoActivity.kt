package com.sbl.foags.activity.authenticate.photographer.work

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.sbl.foags.R
import com.sbl.foags.adapter.BaseDragGridViewListener
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.GlideV4Engine
import com.sbl.foags.utils.PermissionUtils
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.DragGridView
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import java.io.File
import java.util.ArrayList


class SelectWorkPhotoActivity: BaseActivity(),
    View.OnClickListener,
    BaseDragGridViewListener<File>,
    AdapterView.OnItemClickListener {


    companion object {
        private const val MIN_COUNT: Int = 5
        private const val MAX_COUNT: Int = 9
        private const val REQUEST_WORK_PHOTO: Int = 101
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var scrollView: NestedScrollView
    private lateinit var workPhotoView: DragGridView
    private lateinit var uploadView: TextView


    private var resultWorkPhotoList: ArrayList<File> = arrayListOf()

    private lateinit var adapter: SelectWorkPhotoAdapter

    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }


    override fun initLayout(): Int = R.layout.activity_select_work_photo


    override fun initView() {
        bindViews()
    }


    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        scrollView = findViewById(R.id.scrollView)
        workPhotoView = findViewById(R.id.workPhotoView)
        uploadView = findViewById(R.id.uploadView)

        backView.setOnClickListener(this)
        uploadView.setOnClickListener(this)

        workPhotoView.onItemClickListener = this
        workPhotoView.setScrollView(scrollView)
    }


    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.upload_representative_works)

        resultWorkPhotoList = arrayListOf()
        adapter = SelectWorkPhotoAdapter(this, MAX_COUNT)
        adapter.setListener(this)
        adapter.setInitList(resultWorkPhotoList)
        workPhotoView.adapter = adapter
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }

            uploadView -> {

            }
        }
    }

    override fun onItemDeleteClick(position: Int) {
        resultWorkPhotoList.removeAt(position)
        adapter.setInitList(resultWorkPhotoList)
    }

    override fun onItemAddClick() {
        getWorkPhoto()
    }

    override fun moveChange(arrayList: ArrayList<File>) {
        resultWorkPhotoList.clear()
        resultWorkPhotoList.addAll(arrayList)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    private fun getWorkPhoto() {
        if (!PermissionUtils.hasPermissions(this, permissions)) {
            PermissionUtils.requestRuntimePermission(this, object : PermissionUtils.OnPermissionListener {
                override fun onPermissionGranted(permissionList: MutableList<String>?) {
                    if (permissionList?.size == permissions.size) {
                        getWorkPhotoNext()
                    }
                }

                override fun onPermissionDenied(data: MutableList<String>?) {
                    SystemUtil.gotoOpenPermission(this@SelectWorkPhotoActivity)
                }

            }, permissions)
        } else {
            getWorkPhotoNext()
        }
    }


    private fun getWorkPhotoNext(){
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
            .maxSelectable(MAX_COUNT - resultWorkPhotoList.size)
            //.addFilter()
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(GlideV4Engine())
            .forResult(REQUEST_WORK_PHOTO)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (requestCode) {
            REQUEST_WORK_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path: List<String> = Matisse.obtainPathResult(data)
                    for (p in path) {
                        val imageFile = File(p)
                        if (imageFile.exists()) {
                            resultWorkPhotoList.add(imageFile)
                        }
                    }
                    adapter.setInitList(resultWorkPhotoList)
                }
            }
        }
    }
}