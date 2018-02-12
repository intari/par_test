package com.viorsan.resultanttestdkzm.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io


/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.02.2018.
 * Helper classes
 */

/**
 * To avoid typing 2 lines -:)
 */
fun <T> Single<T>.applySchedulers(): Single<T> = this
        .subscribeOn(io())
        .observeOn(AndroidSchedulers.mainThread())


fun <T> Observable<T>.applySchedulers(): Observable<T> = this
        .subscribeOn(io())
        .observeOn(AndroidSchedulers.mainThread())


