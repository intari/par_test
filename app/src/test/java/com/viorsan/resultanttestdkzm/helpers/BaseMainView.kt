package com.viorsan.resultanttestdkzm.helpers

import com.viorsan.resultanttestdkzm.model.CurrencyItem
import com.viorsan.resultanttestdkzm.view.main.MainView


/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.02.2018.
 */
class BaseMainView(var onShow: (items: List<CurrencyItem>) -> Unit = {},
                   val onShowError:(error: Throwable) -> Unit = {},
                   override var refresh: Boolean = false) : MainView {
    override fun show(items: List<CurrencyItem>) {
        onShow(items)
    }

    override fun showError(error: Throwable) {
        onShowError(error)
    }
}