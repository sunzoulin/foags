package com.sbl.foags.activity.authenticate.agreement

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sbl.foags.R


class AuthenticateAgreementDialog(content: Context, private val listener: AuthenticateAgreementListener):
    Dialog(content, R.style.CommonDialog), View.OnClickListener {


    private lateinit var refuseView: TextView
    private lateinit var agreeView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_authenticate_agreement)


        val dm = context.resources.displayMetrics
        setWidthAndHeight((dm.widthPixels * 0.77).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)

        refuseView = findViewById(R.id.refuseView)
        agreeView = findViewById(R.id.agreeView)
        refuseView.setOnClickListener(this)
        agreeView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            refuseView -> {
                dismiss()
                listener.onStatusAgreement(false)
            }

            agreeView -> {
                dismiss()
                listener.onStatusAgreement(true)
            }
        }
    }

    private fun setWidthAndHeight(width: Int, height: Int) {
        val wl = window?.attributes
        wl?.width = width
        wl?.height = height
        onWindowAttributesChanged(wl)
    }
}