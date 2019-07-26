package com.desertfox.couplelink.splash

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.desertfox.couplelink.BaseActivity
import com.desertfox.couplelink.R
import com.desertfox.couplelink.chatting.MainActivity
import com.desertfox.couplelink.data.UserData
import com.desertfox.couplelink.model.request.UpdateCoupleMemberRequest
import com.desertfox.couplelink.util.coupleLinkApi
import com.desertfox.couplelink.util.spannableString
import com.desertfox.couplelink.util.throttleClicks
import com.desertfox.couplelink.util.toast
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_infoinput.*
import java.text.SimpleDateFormat
import java.util.*

class InfoinputActivity : BaseActivity() {


    private lateinit var gender: Gender
    private var profileImg = ""

    private enum class Gender {
        MALE, FEMALE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infoinput)

        init()

        var dateTextView = infoinput_birth_txt
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            dateTextView.setTextColor(Color.parseColor("#9aaaff"))
            dateTextView.text = spannableString(String.format(getString(R.string.str_infoinput_date_format), year, month + 1, dayOfMonth), R.font.spoqahansansbold)
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
            val name = infoinput_name_edit.text.toString()
            val birth = infoinput_birth_txt.text.toString()
            val date = infoinput_date_txt.text.toString()
            when {
                name.isEmpty() -> toast(getString(R.string.str_infoinput_name_hint))
                birth == getString(R.string.str_infoinput_birth_hint) -> toast(birth)
                date == getString(R.string.str_infoinput_date_hint) -> toast(date)
                else -> {
                    coupleLinkApi.updateCoupleMember(UserData.myMemberModel.coupleId
                            ?: -1, UpdateCoupleMemberRequest(changeDateFormat(birth), gender.name, name, profileImg, changeDateFormat(date)))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                UserData.currentCouple = it
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }, {
                                it.printStackTrace()
                                toast(it.message.toString())
                            }).bind()
                }
            }

        }.bind()
    }

    private fun changeDateFormat(date: String): String {
        val strDateFormat = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return serverDateFormat.format(strDateFormat.parse(date)).toString()
    }

    private fun init() {
        infoinput_name_edit.hint = spannableString(R.string.str_infoinput_name_hint, R.font.spoqahansansregular)
        changeGenderCheck(Gender.FEMALE)
        getProfileImage()
    }

    private fun changeGenderCheck(gender: Gender) {
        val isCheck = Gender.MALE == gender
        infoinput_gender_male_check.isSelected = isCheck
        infoinput_gender_male_txt.isSelected = isCheck
        infoinput_gender_female_check.isSelected = !isCheck
        infoinput_gender_female_txt.isSelected = !isCheck
        this.gender = gender
    }

    private fun getProfileImage() {
        val keys = listOf("properties.profile_image")

        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {}

            override fun onSessionClosed(errorResult: ErrorResult) {}

            override fun onSuccess(response: MeV2Response) {
                profileImg = response.profileImagePath
                Glide.with(this@InfoinputActivity).load(profileImg).apply(RequestOptions.circleCropTransform()).into(infoinput_profile_imng)
            }
        })
    }

}