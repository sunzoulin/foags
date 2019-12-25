package com.sbl.foags.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sbl.foags.R
import com.sbl.foags.activity.login.LoginActivity
import com.sbl.foags.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        startActivity(Intent(this, LoginActivity::class.java))

        finish()
    }
}
