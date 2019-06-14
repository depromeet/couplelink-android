package com.desertfox.couplelink.util

import android.annotation.SuppressLint

import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent.Type.*

/**
 * stomp client 상태를 체크하는 함수
 */
@SuppressLint("CheckResult")
fun lifecycle(stompClient: StompClient) {
    stompClient.lifecycle().subscribe { event ->
        when (event.type) {
            OPENED -> d("Stomp connection opened")

            ERROR -> e("Stomp error : " + event.exception)

            CLOSED -> d("Stomp connection closed")

            else -> e("Stomp failed server heartbeat")
        }
    }
}