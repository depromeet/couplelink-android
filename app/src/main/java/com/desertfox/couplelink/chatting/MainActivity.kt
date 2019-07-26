package com.desertfox.couplelink.chatting

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.adapter.ChatRecyclerViewAdapter
import com.desertfox.couplelink.banned.BannedActivity
import com.desertfox.couplelink.data.UserData
import com.desertfox.couplelink.model.request.MsgRequest
import com.desertfox.couplelink.model.responses.ChatModel
import com.desertfox.couplelink.model.responses.MsgModel
import com.desertfox.couplelink.network.StompUrl
import com.desertfox.couplelink.util.StompUtil
import com.desertfox.couplelink.util.throttleClicks
import com.desertfox.couplelink.util.toast
import com.google.gson.Gson
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ua.naiksoftware.stomp.Stomp


class MainActivity : BaseActivity() {
    private var restPingDisposable: Disposable? = null
    private var compositeDisposable: CompositeDisposable? = null
    private val gson = Gson()
    private val coupleId = UserData.currentCouple!!.id
    private val roomId = UserData.currentCouple!!.chatRoom.id
    private lateinit var chatAdapter: ChatRecyclerViewAdapter

    private val stompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, StompUrl.OPEN_STOMP)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initStomp()
        main_banned_btn.throttleClicks().subscribe {
            startActivity(Intent(this, BannedActivity::class.java))
        }.bind()
        main_capsule_btn.throttleClicks().subscribe {
            toast("준비중입니다.")
        }.bind()
        main_setting_btn.throttleClicks().subscribe {
            toast("준비중입니다.")
        }.bind()
        main_mypage_btn.throttleClicks().subscribe {
            toast("준비중입니다.")
        }.bind()
    }

    private fun initView() {
        setSupportActionBar(toolbar_main)

        chatAdapter = ChatRecyclerViewAdapter(this@MainActivity)
        rv_main.adapter = chatAdapter
        rv_main.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true // 아이템이 bottom to top으로 쌓이도록 설정
            reverseLayout = false
        }

        ib_main_send.setOnClickListener {
            val msg = et_main_input_msg.text
            if (!TextUtils.isEmpty(msg)) {
                sendMsg(msg.toString())
            }
        }

        iv_menu.setOnClickListener {
            layout_main_menu.visibility = if (layout_main_menu.isVisible) {
                iv_menu.setImageResource(R.drawable.ic_arrow_down)
                View.GONE
            } else {
                iv_menu.setImageResource(R.drawable.ic_arrow_up)
                View.VISIBLE
            }
        }
    }

    private fun initStomp() {
        initSubscriptions()
        stompClient.connect()
        StompUtil.lifecycle(stompClient)
        stompClient.withClientHeartbeat(1000).withServerHeartbeat(1000)

        // 메세지 수신
        val dispTopic = stompClient.topic(StompUrl.receiveMsg(coupleId, roomId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ msg ->
                val msgItem = gson.fromJson<MsgModel>(msg.payload, MsgModel::class.java)
                val chatItem = ChatModel(
                    msgItem,
                    if (msgItem.writer.id == UserData.myMemberModel.id) {
                        MsgType.MINE
                    } else {
                        MsgType.YOURS
                    }
                )
                chatAdapter.addItem(chatItem)
                rv_main.scrollToPosition(chatAdapter.itemCount - 1)
            }, { t ->
                Toast.makeText(this, t.message.toString(), Toast.LENGTH_LONG).show()
            })

        compositeDisposable!!.add(dispTopic)
    }

    // 메세지 발신
    private fun sendMsg(msg: String) {
        val msgRequest = MsgRequest(UserData.currentMember!!.id, msg)

        compositeDisposable?.add(
            stompClient.send(
                StompUrl.sendMsg(coupleId, roomId),
                gson.toJson(msgRequest).toString()
            ).compose(applySchedulers())
                .subscribe({
                    et_main_input_msg.text.clear()
                }, { t ->
                    Toast.makeText(this, t.message, Toast.LENGTH_LONG).show()
                })
        )
    }

    private fun applySchedulers(): CompletableTransformer =
        CompletableTransformer { upstream ->
            upstream.unsubscribeOn(Schedulers.newThread())
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
