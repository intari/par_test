package com.viorsan.resultanttestdkzm.data.network.provider

import com.google.gson.Gson
import com.viorsan.resultanttestdkzm.Constants
import com.viorsan.resultanttestdkzm.Constants.CONNECT_TIMEOUT
import com.viorsan.resultanttestdkzm.Constants.READ_TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */

/**
 * Retrofit instanse to use (with lazy init)
 */
val retrofit by lazy { makeRetrofit() }

private fun makeRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.SERVER)
        .client(makeHttpClient())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

/**
 * Create our OkHttpclient instance
 * Not using DI/Kodein to pass constants here at this time
 */
private fun makeHttpClient() = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
        .addInterceptor(makeLoggingInterceptor())
        .build()
