package com.sbl.foags

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MyApplication : Application(){

    companion object {
        lateinit var instance: MyApplication
        var invitedChannel:String?=null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


        Fresco.initialize(this)
    }
}