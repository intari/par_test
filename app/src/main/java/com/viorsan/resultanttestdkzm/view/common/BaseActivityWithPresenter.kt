package com.viorsan.resultanttestdkzm.view.common

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.viorsan.resultanttestdkzm.presenter.Presenter

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
abstract class BaseActivityWithPresenter:BaseActivity() {

    //our presenter
    abstract val presenter: Presenter

    override fun onDestroy() {
        super.onDestroy()
        //so we actually unsubscrie
        presenter.onViewDestroyed()
    }
}