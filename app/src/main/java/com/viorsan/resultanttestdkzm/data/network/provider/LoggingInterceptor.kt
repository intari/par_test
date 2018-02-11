package com.viorsan.resultanttestdkzm.data.network.provider

import com.viorsan.resultanttestdkzm.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */

/**
 * Configure logging levels
 */
fun makeLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}