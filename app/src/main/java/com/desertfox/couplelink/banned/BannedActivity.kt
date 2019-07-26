package com.desertfox.couplelink.banned

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.data.UserData
import com.desertfox.couplelink.model.request.BannedTermRequest
import com.desertfox.couplelink.util.coupleLinkApi
import com.desertfox.couplelink.util.spannableString
import com.desertfox.couplelink.util.toast
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_bannedterm.*

class BannedActivity : BaseActivity() {

    private lateinit var bannedAdapter: BannedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bannedterm)

        bannedterm_back_btn.clicks().subscribe {
            finish()
        }.bind()

        bannedterm_my_btn.clicks().subscribe {
            tabChange(bannedterm_my_btn)
        }.bind()

        bannedterm_partner_btn.clicks().subscribe {
            tabChange(bannedterm_partner_btn)
        }.bind()

        bannedAdapter = BannedAdapter(object : BannedAdapter.Callback {
            override fun addBanned(name: String) = this@BannedActivity.addBanned(name)
            override fun deleteBanned(id: Int) = this@BannedActivity.deleteBanned(id)
        }).apply {
            bannedterm_contents.adapter = this
        }

        getBanneds(UserData.myMemberModel.id)
        tabChange(bannedterm_my_btn)
    }

    private fun getBanneds(userId: Int) {
        coupleLinkApi.getBannedTerms(UserData.myMemberModel.coupleId, userId).observeOn(AndroidSchedulers.mainThread()).subscribe({
            bannedAdapter.setData(it, UserData.myMemberModel.id == userId)
        }, {
            it.printStackTrace()
        }).bind()
    }

    private fun addBanned(word: String) {
        coupleLinkApi.createBannedTerm(UserData.myMemberModel.coupleId, BannedTermRequest(word)).observeOn(AndroidSchedulers.mainThread()).subscribe({
            bannedAdapter.appendData(it)
        }, {
            it.printStackTrace()
            toast(it.message.toString())
        }).bind()
    }

    private fun deleteBanned(bannedTermId: Int) {
        coupleLinkApi.deleteBannedTerm(UserData.myMemberModel.coupleId, bannedTermId).observeOn(AndroidSchedulers.mainThread()).subscribe({
            bannedAdapter.deleteData(bannedTermId)
        }, {
            it.printStackTrace()
            toast(it.message.toString())
        }).bind()
    }

    private fun tabChange(status: ViewGroup) {
        when (status) {
            bannedterm_my_btn -> {
                bannedterm_my_txt.text = spannableString(R.string.str_my_bannedterm, R.font.spoqahansansbold)
                bannedterm_my_txt.setTextColor(Color.parseColor("#9aaaff"))
                bannedterm_my_line.setBackgroundColor(Color.parseColor("#9aaaff"))

                bannedterm_partner_txt.text = spannableString(R.string.str_partner_bannedterm, R.font.spoqahansanslight)
                bannedterm_partner_txt.setTextColor(Color.parseColor("#a2a2a2"))
                bannedterm_partner_line.setBackgroundColor(Color.parseColor("#a2a2a2"))
                getBanneds(UserData.myMemberModel.id)
            }
            bannedterm_partner_btn -> {
                bannedterm_partner_txt.text = spannableString(R.string.str_partner_bannedterm, R.font.spoqahansansbold)
                bannedterm_partner_txt.setTextColor(Color.parseColor("#9aaaff"))
                bannedterm_partner_line.setBackgroundColor(Color.parseColor("#9aaaff"))

                bannedterm_my_txt.text = spannableString(R.string.str_my_bannedterm, R.font.spoqahansanslight)
                bannedterm_my_txt.setTextColor(Color.parseColor("#a2a2a2"))
                bannedterm_my_line.setBackgroundColor(Color.parseColor("#a2a2a2"))
                getBanneds(UserData.partnerMemberModel.id)
            }
        }
    }


}