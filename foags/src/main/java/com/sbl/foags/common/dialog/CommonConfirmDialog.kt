package com.sbl.foags.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sbl.foags.R


class CommonConfirmDialog private constructor(context: Context): Dialog(context, R.style.CommonDialog), View.OnClickListener {

    private var sureContent: String? = null
    private var cancelContent: String? = null
    private var cancelable: Boolean? =  true

    private lateinit var contentView: TextView
    private lateinit var cancelView: TextView
    private lateinit var sureView: TextView
    private var listener: CommonConfirmDialogListener? = null

    private var content: SpannableString? = null


    constructor(context: Context, sureContent: String?, cancelContent: String?, cancelable: Boolean, content: String): this(context){
        this.sureContent = sureContent
        this.cancelContent = cancelContent
        this.cancelable = cancelable
        this.content = SpannableString(content)
    }

    constructor(context: Context, sureContent: String?, cancelContent: String?, cancelable: Boolean, content: SpannableString): this(context){
        this.sureContent = sureContent
        this.cancelContent = cancelContent
        this.cancelable = cancelable
        this.content = content
    }


    private fun setWidthAndHeight(width: Int, height: Int) {
        val wl = window?.attributes
        wl?.width = width
        wl?.height = height
        onWindowAttributesChanged(wl)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_common_confirm)

        val dm = context.resources.displayMetrics
        setWidthAndHeight((dm.widthPixels * 0.77).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)

        setCancelable(cancelable!!)
        setCanceledOnTouchOutside(cancelable!!)

        contentView = findViewById(R.id.contentView)
        cancelView = findViewById(R.id.cancelView)
        sureView = findViewById(R.id.sureView)

        cancelView.setOnClickListener(this)
        sureView.setOnClickListener(this)

        contentView.text = content


        if (TextUtils.isEmpty(sureContent)) {
            sureView.visibility = View.GONE
        } else {
            sureView.visibility = View.VISIBLE
            sureView.text = sureContent
        }

        if (TextUtils.isEmpty(cancelContent)) {
            cancelView.visibility = View.GONE
        } else {
            cancelView.visibility = View.VISIBLE
            cancelView.text = cancelContent
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            sureView -> {
                dismiss()
                if (listener != null)
                    listener!!.onClickSure()
            }
            cancelView -> {
                dismiss()
                if (listener != null)
                    listener!!.onClickCancel()
            }
        }
    }


    fun setCommonConfirmDialogListener(listener: CommonConfirmDialogListener) {
        this.listener = listener
    }
}