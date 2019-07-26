package com.desertfox.couplelink.util

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent.Type.*

/**
 * stomp client 상태를 체크하는 함수
 */
object StompUtil {
    @SuppressLint("CheckResult")
    fun lifecycle(stompClient: StompClient) {
        stompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when (event.type) {
                    OPENED -> d("Stomp status : Stomp connection opened")

                    ERROR -> e("Stomp status : " + event.exception)

                    CLOSED -> d("Stomp status : Stomp connection closed")

                    else -> e("Stomp status : Stomp failed server heartbeat")
                }
            }
    }
}