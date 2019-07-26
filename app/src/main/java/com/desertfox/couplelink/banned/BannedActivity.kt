package com.desertfox.couplelink.banned

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.util.coupleLinkApi
import com.desertfox.couplelink.util.spannableString
import com.jakewharton.rxbinding3.view.clicks
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

        bannedAdapter = BannedAdapter().apply {
            bannedterm_contents.adapter = this
        }


    }

    private fun getBanneds(userId:Int){
        coupleLinkApi.getBannedTerms(userId).subscribe({
            bannedAdapter.setData(it,true)
        },{
            it.printStackTrace()
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
            }
            bannedterm_partner_btn -> {
                bannedterm_partner_txt.text = spannableString(R.string.str_partner_bannedterm, R.font.spoqahansansbold)
                bannedterm_partner_txt.setTextColor(Color.parseColor("#9aaaff"))
                bannedterm_partner_line.setBackgroundColor(Color.parseColor("#9aaaff"))

                bannedterm_my_txt.text = spannableString(R.string.str_my_bannedterm, R.font.spoqahansanslight)
                bannedterm_my_txt.setTextColor(Color.parseColor("#a2a2a2"))
                bannedterm_my_line.setBackgroundColor(Color.parseColor("#a2a2a2"))
            }
        }
    }


}