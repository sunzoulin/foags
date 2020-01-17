package com.sbl.foags.activity.cube.comment.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.sbl.foags.R
import com.sbl.foags.utils.KeyBoardUtil


class EditCommentSendDialog(val activity: Activity, private val listener: EditCommentSendListener):
    Dialog(activity, R.style.EditCommentSendDialog),
    TextWatcher,
    View.OnClickListener {


    private lateinit var commentEditView: EditText
    private lateinit var totalCountView: TextView
    private lateinit var sendView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_comment_send)


        val layoutParams = window!!.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.gravity = Gravity.BOTTOM
        onWindowAttributesChanged(layoutParams)

        initViews()

        KeyBoardUtil.openKeyBoard(commentEditView, context)
    }

    private fun initViews() {
        commentEditView = findViewById(R.id.commentEditView)
        totalCountView = findViewById(R.id.totalCountView)
        sendView = findViewById(R.id.sendView)

        commentEditView.requestFocus()
        commentEditView.addTextChangedListener(this)
        sendView.setOnClickListener(this)
    }


    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.isNullOrEmpty()) {
            totalCountView.text = "0/300"
        } else {
            totalCountView.text = "${s.length}/300"
        }
    }

    override fun dismiss() {
        KeyBoardUtil.closeKeyBoard(commentEditView, activity)
        super.dismiss()
    }

    override fun onClick(v: View?) {
        when(v){
            sendView -> {
                if(commentEditView.text.isNotEmpty()){
                    listener.onSendComment(commentEditView.text.toString())
                    commentEditView.text.clear()
                    commentEditView.clearFocus()
                    dismiss()
                }
            }
        }
    }

}