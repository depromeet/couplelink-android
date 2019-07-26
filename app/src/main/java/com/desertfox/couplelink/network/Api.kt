package com.desertfox.couplelink.network

import com.desertfox.couplelink.model.request.*
import com.desertfox.couplelink.model.responses.*
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginModel>

    @GET("/api/members")
    fun getMembers(@Query("connectionNumber") connectionNumber: String): Single<List<MemberModel>>

    @GET("/api/members/{memberId}")
    fun getMember(@Path("memberId") memberId: Int): Single<MemberModel>

    @GET("/api/members/me")
    fun getMe(): Single<MemberModel>

    @POST("/api/couples")
    fun createCouple(@Body coupleRequest: CoupleRequest): Single<CoupleModel>

    @GET("/api/couples/{coupleId}")
    fun getCouple(@Path("coupleId") coupleId: Int): Single<CoupleModel>

    @PUT("/api/couples/{coupleId}/members/me")
    fun updateCoupleMember(@Path("coupleId") coupleId: Int, @Body updateCoupleMemberRequest: UpdateCoupleMemberRequest): Single<CoupleModel>

    @GET("/api/couples/{coupleId}/cookies")
    fun getCookies(@Path("coupleId") coupleId: Int, @Query("page") page: Int = 0, @Query("writer") writer: String = "ALL"): Single<List<FortuneCookieModel>>

    @POST("/api/couples/{coupleId}/cookies")
    fun createCookies(@Path("coupleId") coupleId: Int, @Body createFortuneCookieRequest: CreateFortuneCookieRequest): Single<FortuneCookieModel>

    @GET("/api/couples/{coupleId}/cookies/{cookieId}")
    fun getCookie(@Path("coupleId") coupleId: Int, @Path("cookieId") cookieId: Int): Single<FortuneCookieModel>

    @DELETE("/api/couples/{coupleId}/cookies/{cookieId}")
    fun deleteCookie(@Path("coupleId") coupleId: Int, @Path("cookieId") cookieId: Int): Single<Response<ResponseBody>>

    @POST("/api/couples/{coupleId}/cookies/{cookieId}/read")
    fun readCookie(@Path("coupleId") coupleId: Int, @Path("cookieId") cookieId: Int): Single<FortuneCookieModel>

    @GET("/api/couples/{coupleId}/banned-terms")
    fun getBannedTerms(@Path("coupleId") coupleId: Int, @Query("size") page: Int = 1000): Single<List<BannedTermModel>>

    @POST("/api/couples/{coupleId}/banned-terms")
    fun createBannedTerm(@Path("coupleId") coupleId: Int, @Body bannedTermRequest: BannedTermRequest): Single<BannedTermModel>

    @GET("/api/couples/{coupleId}/banned-terms/{bannedTermId}")
    fun getBannedTerm(@Path("coupleId") coupleId: Int, @Path("bannedTermId") bannedTermId: Int): Single<BannedTermModel>

    @DELETE("/api/couples/{coupleId}/banned-terms/{bannedTermId}")
    fun deleteBannedTerm(@Path("coupleId") coupleId: Int, @Path("bannedTermId") bannedTermId: Int): Single<Response<ResponseBody>>

}