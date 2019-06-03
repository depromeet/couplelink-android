package com.desertfox.couplelink.model.request

data class UpdateCoupleMemberRequest(
    val birthDate: String,
    val genderType: String,
    val name: String,
    val profileImageUrl: String,
    val startedAt: String
)