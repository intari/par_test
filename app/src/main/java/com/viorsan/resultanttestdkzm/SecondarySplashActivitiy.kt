package com.viorsan.resultanttestdkzm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import com.viorsan.resultanttestdkzm.Interfaces.CreatorInterface
import com.viorsan.resultanttestdkzm.view.common.BaseActivity
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.doAsync
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.R.attr.delay
import io.reactivex.Observable
import net.intari.AndroidToolboxCore.Extensions.logger
import java.util.concurrent.TimeUnit


/**
 * Only need to show spash screen even when we don't actually need to show it except for user
 */
class SecondarySplashActivitiy : BaseActivity() {
    private val mainActivityCreator by kodein.instance<CreatorInterface>("MainActivity")

    private var launchedMainBefore = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary_splash_activitiy)

        //re-launch main
        if (launchedMainBefore) {
            logger.info { "re-launching main" }
            launchMainActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        //wait specified time, Rx way.
        //alternative is Handler().postDelayed(new Runnable.... )
        Observable.just(true).delay(Constants.MIN_SPLASH_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {  launchMainActivity() }
                .subscribe()
     }
    fun launchMainActivity() {
        logger.info { "Timer expired. Launching main" }
        val intent = mainActivityCreator.getIntent(this)
        startActivity(intent)
        launchedMainBefore=true

    }
    companion object {

        val logger=logger<SecondarySplashActivitiy>()

    }
}
