package com.desertfox.couplelink.splash

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.util.CustomTypefaceSpan
import com.desertfox.couplelink.util.coupleLinkApi
import com.desertfox.couplelink.util.throttleClicks
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import kotlinx.android.synthetic.main.activity_infoinput.*
import java.util.*

class InfoinputActivity : BaseActivity() {


    private enum class Gender {
        MALE, FEMALE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infoinput)

        init()

        var dateTextView = infoinput_birth_txt
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

            val spannableString = SpannableString(String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth))
            spannableString.setSpan(
                    CustomTypefaceSpan(ResourcesCompat.getFont(this, R.font.spoqahansansbold)!!),
                    0,
                    spannableString.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            dateTextView.setTextColor(Color.parseColor("#9aaaff"))
            dateTextView.text = spannableString
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

        infoinput_birth_txt.throttleClicks().subscribe {
            dateTextView = infoinput_birth_txt
            datePickerDialog.show()
        }.bind()

        infoinput_date_txt.throttleClicks().subscribe {
            dateTextView = infoinput_date_txt
            datePickerDialog.show()
        }.bind()

        infoinput_gender_female_btn.throttleClicks().subscribe {
            changeGenderCheck(Gender.FEMALE)
        }.bind()

        infoinput_gender_male_btn.throttleClicks().subscribe {
            changeGenderCheck(Gender.MALE)
        }.bind()

        infoinput_confirm_btn.throttleClicks().subscribe {
            //TODO 커플정보 등록 필요
        }.bind()
    }

    private fun init() {
        val spannableString = SpannableString(resources.getString(R.string.str_infoinput_name_hint))
        spannableString.setSpan(
                CustomTypefaceSpan(ResourcesCompat.getFont(this, R.font.spoqahansansregular)!!),
                0,
                spannableString.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        infoinput_name_edit.hint = spannableString
        changeGenderCheck(Gender.FEMALE)
        getProfileImage()
    }

    private fun changeGenderCheck(gender: Gender) {
        val isCheck = Gender.MALE == gender
        infoinput_gender_male_check.isSelected = isCheck
        infoinput_gender_male_txt.isSelected = isCheck
        infoinput_gender_female_check.isSelected = !isCheck
        infoinput_gender_female_txt.isSelected = !isCheck
    }

    private fun getProfileImage() {
        val keys = listOf("properties.profile_image")

        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {}

            override fun onSessionClosed(errorResult: ErrorResult) {}

            override fun onSuccess(response: MeV2Response) {
                Glide.with(this@InfoinputActivity).load(response.profileImagePath).apply(RequestOptions.circleCropTransform()).into(infoinput_profile_imng)
            }
        })
    }

}