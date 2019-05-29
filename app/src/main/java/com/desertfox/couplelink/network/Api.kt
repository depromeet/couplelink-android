package com.desertfox.couplelink.network

import com.desertfox.couplelink.model.TestModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("test")
    fun test(@Query("test") id:String):Single<TestModel>

}