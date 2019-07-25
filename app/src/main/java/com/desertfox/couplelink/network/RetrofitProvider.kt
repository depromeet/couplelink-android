package com.desertfox.couplelink.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val coupleLinkApi: Api by lazy {
    Retrofit.Builder()
        .baseUrl(StompUrl.BASE_URL)
        .client(provideOkHttpClient(provideLoggingInterceptor()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}

private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    val b = OkHttpClient.Builder()
    b.addInterceptor { chain ->
        return@addInterceptor chain.proceed(chain.request().newBuilder().build())
    }
    b.addInterceptor(interceptor)
    return b.build()
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return interceptor
}