package com.desertfox.couplelink.splash

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.data.UserData
import com.desertfox.couplelink.model.request.CoupleRequest
import com.desertfox.couplelink.model.responses.MemberModel
import com.desertfox.couplelink.util.coupleLinkApi
import com.desertfox.couplelink.util.spannableString
import com.desertfox.couplelink.util.throttleClicks
import com.desertfox.couplelink.util.toast
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_connection.*


class ConnectionActivity : BaseActivity() {

    private lateinit var currentMember: MemberModel

    private enum class OtherStatus {
        NORMAL, SELECT, ERROR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        UserData.currentMember?.apply {
            currentMember = this
        } ?: apply {
            toast("값이읎쪄")
            return finish()
        }

        connection_mycode.text = currentMember.connectionNumber
        connection_othercode_edit.hint =
            spannableString(R.string.str_connection_othercode_hint, R.font.spoqahansanslight)

        connection_mycode.throttleClicks().subscribe {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("CoupleLInk", connection_mycode.text.toString())
            clipboardManager.primaryClip = clipData
            toast("내 코드가 복사되었습니다.")
        }.bind()


        connection_othercode_edit.textChanges().subscribe {
            if (it.toString().isEmpty()) {
                changeOtherStatus(OtherStatus.NORMAL)
            } else {
                changeOtherStatus(OtherStatus.SELECT)
            }
        }.bind()

        connection_btn.throttleClicks().subscribe {
            coupleLinkApi.createCouple(CoupleRequest(connection_othercode_edit.text.toString()))
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    UserData.currentCouple = it
                    startActivity(Intent(this@ConnectionActivity, InfoinputActivity::class.java))
                    finish()
                }, {
                    it.printStackTrace()
                    if (it.message.orEmpty().contains("404")) {
                        changeOtherStatus(OtherStatus.ERROR)
                    } else if (it.message.orEmpty().contains("400")) { //TODO 임시조치
                        startActivity(Intent(this@ConnectionActivity, InfoinputActivity::class.java))
                        finish()
                    }
                }).bind()
        }.bind()
    }

    private fun changeOtherStatus(status: OtherStatus) {
        var color = 0
        when (status) {
            OtherStatus.NORMAL -> {
                color = Color.parseColor("#80727272")
                connection_othercode_error.isVisible = false
            }
            OtherStatus.SELECT -> {
                color = Color.parseColor("#9aaaff")
                connection_othercode_error.isVisible = false
            }
            OtherStatus.ERROR -> {
                color = Color.parseColor("#ff829a")
                connection_othercode_error.isVisible = true
            }
        }
        connection_othercode_txt.setTextColor(color)
        connection_othercode_edit.setTextColor(color)
        connection_othercode_line.setBackgroundColor(color)
    }

}