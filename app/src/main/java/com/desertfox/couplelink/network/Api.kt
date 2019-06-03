package com.desertfox.couplelink.network

import com.desertfox.couplelink.model.request.*
import com.desertfox.couplelink.model.responses.MemberModel
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Single<String>

    @GET("/members")
    fun getMembers(@Query("connectionNumber") connectionNumber: String): Single<List<MemberModel>>

    @GET("/members/{memberId}")
    fun getMember(@Path("memberId") memberId: Int): Single<MemberModel>

    @GET("/members/me")
    fun getMe(): Single<MemberModel>

    @POST("/couples")
    fun createCouple(@Body coupleRequest: CoupleRequest)

    @GET("/couples/{coupleId}")
    fun getCouple(@Path("coupleId") coupleId: Int)

    @PUT("/couples/{coupleId}/members/me")
    fun updateCoupleMember(@Path("coupleId") coupleId: Int, @Body updateCoupleMemberRequest: UpdateCoupleMemberRequest)

    @GET("/couples/{coupleId}/cookies")
    fun getCookies(@Path("coupleId") coupleId: Int, @Query("page") page: Int = 0, @Query("writer") writer: String = "ALL")

    @POST("/couples/{coupleId}/cookies")
    fun createCookies(@Path("coupleId") coupleId: Int, @Body createFortuneCookieRequest: CreateFortuneCookieRequest)

    @GET("/couples/{coupleId}/cookies/{cookieId}")
    fun getCookie(@Path("coupleId") coupleId: Int, @Path("cookieId") cookieId: Int)

    @DELETE("/couples/{coupleId}/cookies/{cookieId}")
    fun deleteCookie(@Path("coupleId") coupleId: Int, @Path("cookieId") cookieId: Int)

    @POST("/couples/{coupleId}/cookies/{cookieId}/read")
    fun readCookie(@Path("coupleId") coupleId: Int, @Path("cookieId") cookieId: Int)

    @GET("/couples/{coupleId}/banned-terms")
    fun getBannedTerms(@Path("coupleId") coupleId: Int, @Query("page") page: Int = 0)

    @POST("/couples/{coupleId}/banned-terms")
    fun createBannedTerm(@Path("coupleId") coupleId: Int,@Body bannedTermRequest: BannedTermRequest)

    @GET("/couples/{coupleId}/banned-terms/{bannedTermId}")
    fun getBannedTerm(@Path("coupleId") coupleId: Int,@Path("bannedTermId ") bannedTermId : Int)

    @DELETE("/couples/{coupleId}/banned-terms/{bannedTermId}")
    fun deleteBannedTerm(@Path("coupleId") coupleId: Int,@Path("bannedTermId ") bannedTermId : Int)

}