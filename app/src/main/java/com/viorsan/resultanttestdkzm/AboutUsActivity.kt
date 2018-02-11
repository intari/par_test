package com.viorsan.resultanttestdkzm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.viorsan.resultanttestdkzm.Interfaces.CreatorInterface
import com.viorsan.resultanttestdkzm.view.common.BaseActivity
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        //Configure initial value for 'appVersion'. Kotlin support rulez!
        appVersionTextView.text = resources.getString(R.string.appVersionLong,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE,
                BuildConfig.BUILD_DATE_TIME
        )

    }

    companion object: CreatorInterface {
        val logger= net.intari.AndroidToolboxCore.Extensions.logger<AboutUsActivity>()
        override fun getIntent(context: Context): Intent {
            return  Intent(context, AboutUsActivity::class.java)
        }
        override fun creator(): CreatorInterface {
            return this
        }}

}
