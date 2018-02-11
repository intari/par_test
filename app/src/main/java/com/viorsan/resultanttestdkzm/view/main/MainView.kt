package com.viorsan.resultanttestdkzm.view.main

import com.viorsan.resultanttestdkzm.model.CurrencyItem

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
interface MainView {
    var refresh: Boolean
    fun show(items: List<CurrencyItem>)
    fun showError(error:Throwable)
}