package com.desertfox.couplelink.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.desertfox.couplelink.network.Api
import com.desertfox.couplelink.network.RetrofitProvider
import com.jakewharton.rxbinding3.view.clicks

/**
 * Toast R.String.~~로 접근
 */
fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

/**
 * Toast String 으로 접근
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

/**
 * ViewClick 구현시 300ms 내에 중복클릭은 제외
 */
fun View.throttleClicks() = this.clicks().throttleFirst(300, java.util.concurrent.TimeUnit.MILLISECONDS)!!

/**
 * sharedPreferences 구현
 */
fun Context.sharedPreferences() = this.getSharedPreferences(COUPLE_LINK, Context.MODE_PRIVATE)!!

val Context.coupleLinkApi: Api
    get() = RetrofitProvider(this).coupleLinkApi