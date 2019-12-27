package com.sbl.foags.activity.login

import android.view.View
import android.widget.*
import com.sbl.foags.R
import com.sbl.foags.activity.main.MainActivity
import com.sbl.foags.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var closeView: ImageView
    private lateinit var countryCodeLayout: LinearLayout
    private lateinit var countryCodeView: TextView
    private lateinit var inputPhoneNumberView: EditText
    private lateinit var inputIdentifyingCodeView: EditText
    private lateinit var sendIdentifyingCodeView: TextView
    private lateinit var timeIdentifyingCodeView: TextView
    private lateinit var doLoginView: TextView
    private lateinit var agreementView: TextView


    private val timeInterval: Int = 120
    private var dis: Disposable? = null


    override fun onDestroy() {
        super.onDestroy()
        dis?.dispose()
        dis = null
    }


    override fun initView() {
        setBaseContentView(R.layout.activity_login)

        bindViews()
    }

    override fun loadData() {

        countryCodeView.text = "+86"
    }


    private fun bindViews(){


        closeView = findViewById(R.id.closeView)
        countryCodeLayout = findViewById(R.id.countryCodeLayout)
        countryCodeView = findViewById(R.id.countryCodeView)
        inputPhoneNumberView = findViewById(R.id.inputPhoneNumberView)
        inputIdentifyingCodeView = findViewById(R.id.inputIdentifyingCodeView)
        sendIdentifyingCodeView = findViewById(R.id.sendIdentifyingCodeView)
        timeIdentifyingCodeView = findViewById(R.id.timeIdentifyingCodeView)
        doLoginView = findViewById(R.id.doLoginView)
        agreementView = findViewById(R.id.agreementView)

        closeView.setOnClickListener(this)
        countryCodeLayout.setOnClickListener(this)
        sendIdentifyingCodeView.setOnClickListener(this)
        doLoginView.setOnClickListener(this)
        agreementView.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v){
            closeView -> {
                finish()
            }

            countryCodeLayout -> {

            }

            sendIdentifyingCodeView -> {
                todoSendIdentifyingCode()
            }

            doLoginView -> {

                if(inputPhoneNumberView.text.isEmpty()){
                    Toast.makeText(this, getString(R.string.please_input_phone_number), Toast.LENGTH_SHORT).show()
                    return
                }

                if(inputIdentifyingCodeView.text.isEmpty()){
                    Toast.makeText(this, getString(R.string.please_input_phone_number), Toast.LENGTH_SHORT).show()
                    return
                }

                MainActivity.open(this)

            }

            agreementView -> {

            }
        }
    }


    private fun todoSendIdentifyingCode(){
        if(sendIdentifyingCodeView.visibility == View.VISIBLE){
            successSendIdentifyingCode()
        }
    }


    private fun successSendIdentifyingCode(){
        if(sendIdentifyingCodeView.visibility == View.VISIBLE){

            timeIdentifyingCodeView.text = "${timeInterval}s"
            sendIdentifyingCodeView.visibility = View.GONE
            timeIdentifyingCodeView.visibility = View.VISIBLE

            dis?.dispose()
            dis = Observable.interval(1, TimeUnit.SECONDS)
                .take((timeInterval + 1).toLong())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ aLong ->

                    val result = timeInterval - (aLong!!.toInt() + 1)
                    if(result < 0){
                        timeIdentifyingCodeView.visibility = View.GONE
                        sendIdentifyingCodeView.visibility = View.VISIBLE
                        dis = null
                    }else{
                        timeIdentifyingCodeView.text = "${result}s"
                    }
                }, {
                    timeIdentifyingCodeView.visibility = View.GONE
                    sendIdentifyingCodeView.visibility = View.VISIBLE
                    dis = null
                })
        }
    }
}