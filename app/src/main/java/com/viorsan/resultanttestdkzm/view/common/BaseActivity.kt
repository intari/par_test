package com.viorsan.resultanttestdkzm.view.common

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Base members for our activities
 */
open class BaseActivity : AppCompatActivity() {
    //Kodein support.
    protected val kodein = LazyKodein(appKodein)

}
