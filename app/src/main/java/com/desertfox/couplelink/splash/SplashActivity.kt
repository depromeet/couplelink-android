package com.desertfox.couplelink.splash

import android.content.Intent
import android.os.Bundle
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.util.ACCESS_TOKEN
import com.desertfox.couplelink.util.sharedPreferences
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Observable.timer(1, TimeUnit.SECONDS).subscribe {
            val accessToken = sharedPreferences().getString(ACCESS_TOKEN, "")
            if (!accessToken.isNullOrEmpty()) {
                startActivity(Intent(this@SplashActivity, ConnectionActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
        }.bind()
    }
}