package com.desertfox.couplelink.chatting

import android.os.Bundle
import android.text.TextUtils
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.network.StompUrl
import com.desertfox.couplelink.util.StompUtil
import com.desertfox.couplelink.util.toast
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ua.naiksoftware.stomp.Stomp

class MainActivity : BaseActivity() {
    private val stompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, StompUrl.OPEN_STOMP)
    }
    private var restPingDisposable: Disposable? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initStomp()
    }

    private fun initView() {
        setSupportActionBar(toolbar_main)

        ib_main_send.setOnClickListener {
            val msg = et_main_input_msg.text
            if (!TextUtils.isEmpty(msg)) {
                sendMsg(msg.toString())
            }
        }
    }

    private fun initStomp() {
        initSubscriptions()
        stompClient.connect()
        StompUtil.lifecycle(stompClient)
        stompClient.withClientHeartbeat(1000).withServerHeartbeat(1000)

        // 메세지 수신
        val dispTopic = stompClient.topic(StompUrl.receiveMsg(0, 0))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ msg ->
                toast("receive msg successfully : " + msg.payload)
            }, { t ->
                toast(t.message.toString())
            })

        compositeDisposable!!.add(dispTopic)
    }

    // 메세지 발신
    private fun sendMsg(msg: String) {
        compositeDisposable?.add(
            stompClient.send(StompUrl.sendMsg(0, 0), msg)
                .compose(applySchedulers())
                .subscribe({
                    toast("send msg successfully")
                    // 리스트에 MsgType.MINE 타입의 메세지를 추가
                }, { t ->
                    toast("error : " + t.message)
                })
        )
    }

    private fun applySchedulers(): CompletableTransformer =
        CompletableTransformer { upstream ->
            upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    private fun initSubscriptions() {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        } else {
            compositeDisposable?.dispose()
        }
    }

    override fun onDestroy() {
        stompClient.disconnect()
        compositeDisposable?.dispose()
        restPingDisposable?.dispose()

        super.onDestroy()
    }
}
