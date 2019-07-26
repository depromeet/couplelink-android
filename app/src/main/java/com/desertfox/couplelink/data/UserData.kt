package com.desertfox.couplelink.data

import com.desertfox.couplelink.model.responses.CoupleModel
import com.desertfox.couplelink.model.responses.MemberModel

object UserData {

    var currentMember: MemberModel? = null
    var currentCouple: CoupleModel? = null
        set(value) {
            field = value
            if(value != null) {
                field = value
                value.apply {
                    members.forEach {
                        if (it.id == currentMember?.id) {
                            myMemberModel = it
                        } else {
                            partnerMemberModel = it
                        }
                    }
                }
            }
        }
    lateinit var myMemberModel: MemberModel
    lateinit var partnerMemberModel: MemberModel

}