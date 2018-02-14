package com.viorsan.resultanttestdkzm

import android.content.Intent
import android.os.Build.VERSION_CODES.*
import com.viorsan.resultanttestdkzm.view.main.MainActivity
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.Config.ALL_SDKS
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowIntent

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 14.02.2018.
 * Strictly speaking, this is integration and not unit test
 * Issues:
 * - Regular network interaction will happen here - it is NOT mocked.
 *   it's better to use https://github.com/square/okhttp/issues/2533#issuecomment-285238088 or network errors will happen
 *   it's not necessary that will fail tests...
 * - NSLogger interaction will fail
 * - If you don't want to look at constraintLayout's NPEs - disable M,N,O (even if you don't do so - tests will NOT fail on them)
 * If we need to perform device-specific test - just configure tests themselves (or create testcases) via http://robolectric.org/device-configuration/
 */

@Suppress("IllegalIdentifier") //allow 'fancy' test names
@RunWith(RobolectricTestRunner::class)
@Config(constants=BuildConfig::class,
        packageName = "com.viorsan.resultanttestdkzm",
        sdk = intArrayOf(ALL_SDKS)
        //sdk= intArrayOf( LOLLIPOP,M,N, O)
        )
class SimpleRobolectricTest {
    lateinit var mainActivity:MainActivity

    @Before
    fun init(){
        mainActivity =  Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .start()
                .resume()
                .visible() //see http://robolectric.org/activity-lifecycle/
                .get();

    }

    @Test
    fun `AboutUs activity Can be launched directly`() {

        val shadowActivity: ShadowActivity = Shadows.shadowOf(mainActivity)
        mainActivity.launchAboutUs()
        val intent: Intent =shadowActivity.nextStartedActivity
        val shadowIntent: ShadowIntent =shadowOf(intent)
        assertThat(shadowIntent.intentClass.name, equalTo(AboutUsActivity::class.java!!.getName()))
    }

    @Test
    fun `Options menu of MainActivity is correctly initialized`() {

        val shadowActivity: ShadowActivity = Shadows.shadowOf(mainActivity)
        val menu = shadowActivity.optionsMenu
        assertNotNull(menu)
        val aboutItem = menu.findItem(R.id.action_about)
        assert(aboutItem.title.equals(mainActivity.getText(R.string.action_about)))

    }

    @Test
    fun `AboutUs activity can be launched by selecting from Options Menu`() {
        val shadowActivity: ShadowActivity = Shadows.shadowOf(mainActivity)
        shadowActivity.clickMenuItem(R.id.action_about)
        val expectedIntent = Intent(mainActivity, AboutUsActivity::class.java)
        assert(shadowOf(mainActivity).nextStartedActivity.equals(expectedIntent))
    }
}