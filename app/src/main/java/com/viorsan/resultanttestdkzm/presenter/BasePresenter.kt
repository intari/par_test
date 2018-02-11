package com.viorsan.resultanttestdkzm.presenter

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Base presenter class so we can dispose of subscriptions when not needed
 */
abstract class BasePresenter: Presenter {
    protected var subscriptions = CompositeDisposable()

    override fun onViewDestroyed() {
        subscriptions.dispose()
    }
}