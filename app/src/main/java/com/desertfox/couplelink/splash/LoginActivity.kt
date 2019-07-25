package com.desertfox.couplelink.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.content.edit
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.model.request.LoginRequest
import com.desertfox.couplelink.util.ACCESS_TOKEN
import com.desertfox.couplelink.util.coupleLinkApi
import com.desertfox.couplelink.util.e
import com.desertfox.couplelink.util.sharedPreferences
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException


class LoginActivity : BaseActivity() {

    private var callback: SessionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            coupleLinkApi.login(LoginRequest(Session.getCurrentSession().tokenInfo.accessToken))
                .subscribe({
                    sharedPreferences().edit {
                        putString(ACCESS_TOKEN, it.accessToken)
                    }
                    startActivity(Intent(this@LoginActivity, ConnectionActivity::class.java))
                    finish()
                }, {
                    e(it.toString())
                }).bind()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                e(exception.toString())
            }
        }
    }
}