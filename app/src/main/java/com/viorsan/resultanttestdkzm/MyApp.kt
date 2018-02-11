package com.viorsan.resultanttestdkzm

import android.app.Application
import android.content.Context
import android.os.Build
import com.akaita.java.rxjava2debug.RxJava2Debug
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidModule
import com.viorsan.resultanttestdkzm.Constants.*
import com.viorsan.resultanttestdkzm.Interfaces.CreatorInterface
import com.viorsan.resultanttestdkzm.data.MainRepository
import com.viorsan.resultanttestdkzm.data.MainRepositoryImpl
import com.viorsan.resultanttestdkzm.view.main.MainActivity
import net.intari.AndroidToolboxCore.Extensions.logger
import java.util.*

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */

// No need to inherit from Multidex app yet
class MyApp : Application(), KodeinAware {

    //Init Kodein
    override val kodein by Kodein.lazy {
        /* bindings */
        import(androidModule)

        // Make it possible to replace AboutUs Activity (or other activities in ufutre
        bind<CreatorInterface>("AboutUsActivity") with singleton { AboutUsActivity.creator() }
        bind<CreatorInterface>("MainActivity") with singleton { MainActivity.creator() }
        constant("SERVER") with SERVER
        bind<Context>("AppContext") with singleton { applicationContext }
        bind<MainRepository>("MainRepository") with provider { MainRepositoryImpl() } //real network repository
    }


    fun injectKodein() = injector.inject(kodein)
    private val injector = KodeinInjector()



    protected fun initCrashHandler() {
        //if it was real app, I would inited crashlytcis here
        //Must be first line before we use logger and error handler.
        //Fabric.with(this, new Crashlytics());
    }




    protected fun initLogging() {
        //init logging subsystem
        //I  use NSLogger for development purposes
        logger.IsDebug { BuildConfig.DEBUG }
        logger.logDestination(LOG_HOST,LOG_PORT)
        logger.context { this }

        //measure delay by logging calls themselves
        logger.verbose { "Logging...123" }
        logger.verbose { "Logging...456" }
        logger.verbose { "Version Name:" + BuildConfig.VERSION_NAME }
        logger.verbose { "Version Code:" + Integer.valueOf(BuildConfig.VERSION_CODE) !!.toString() }
        logger.verbose { "DeviceModel:" + Build.MODEL }
        logger.verbose { "DeviceManufacturer:" + Build.MANUFACTURER }
        logger.verbose { "DeviceProduct:" + Build.PRODUCT }

        logger.verbose { "Language ${Locale.getDefault()}" }
        logger.verbose { "Country: ${Locale.getDefault().country}" }
        logger.verbose { "Locale: ${Locale.getDefault().toString()}" }
        logger.verbose { "buildType: ${BuildConfig.BUILD_TYPE}" }
        logger.verbose { "buildFlavour: ${BuildConfig.FLAVOR}" }
        logger.verbose {  resources.getString(R.string.appVersionLong,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE,
                BuildConfig.BUILD_DATE_TIME
        ) }

    }

    /**
     * Enhanced Stack Traces for rxJava2 code
     * https://github.com/akaita/RxJava2Debug
     */
    protected fun initRxJava2Debug() {
        logger.verbose {  "Enabling enhanced stack tracking for rxJava2" }
        //highlight packages
        RxJava2Debug.enableRxJava2AssemblyTracking(arrayOf("com.viorsan.campusandroid"))
    }




    override fun onCreate() {
        super.onCreate()

        initCrashHandler()
        initLogging()


        //init rxjava2debug, must be done after crash-reporter is ready
        initRxJava2Debug()


        injectKodein() //we need it ourselves so inject us

    }

    companion object {

        val logger=logger<MyApp>()
        operator fun get(context: Context): MyApp {
            return context.applicationContext as MyApp
        }


    }


}