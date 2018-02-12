package com.viorsan.resultanttestdkzm.presenter

import com.viorsan.resultanttestdkzm.data.MainRepository
import com.viorsan.resultanttestdkzm.data.applySchedulers
import com.viorsan.resultanttestdkzm.view.main.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.intari.AndroidToolboxCore.Extensions.logger

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
class MainPresenter(val view: MainView, val repository: MainRepository):BasePresenter() {
    fun onViewCreated() {
        loadStocks()
    }
    fun onRefresh() {
        loadStocks()
    }
    private fun loadStocks() {
        //add subscription to ours and load stocks
        subscriptions += repository.getStocks()
                .applySchedulers()
                .doOnSubscribe { view.refresh = true } //so we can have 'updating' animations
                .doFinally { view.refresh = false}
                .subscribeBy(
                        onSuccess = view::show,
                        onError = view::showError)
    }
    companion object {
        val logger = logger<MainPresenter>()
    }

}