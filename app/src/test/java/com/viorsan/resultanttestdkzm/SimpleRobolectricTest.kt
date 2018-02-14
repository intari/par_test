package com.viorsan.resultanttestdkzm

import android.content.Intent
import android.os.Build.VERSION_CODES.*
import com.viorsan.resultanttestdkzm.R.id.recyclerView
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertNotNull
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
import org.robolectric.android.controller.ActivityController
import com.viorsan.resultanttestdkzm.view.main.MainActivity
import android.os.Bundle
import org.robolectric.RuntimeEnvironment


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
        //sdk = intArrayOf(ALL_SDKS)
        sdk= intArrayOf( LOLLIPOP,M,N, O)
        )
class SimpleRobolectricTest {
    private lateinit var controller: ActivityController<MainActivity>

    @Before
    fun init(){
        controller =  Robolectric.buildActivity(MainActivity::class.java)

    }

    @After
    fun teardown() {
        // Destroy activity after every test
        controller
                .pause()
                .start()
                .destroy()
    }
    fun createActivity() = controller
                .create()
                .start()
                .resume()
                .visible() //see http://robolectric.org/activity-lifecycle/
                .get()

    @Test
    fun `AboutUs activity Can be launched directly`() {

        val activity= createActivity()
        val shadowActivity: ShadowActivity = Shadows.shadowOf(activity)
        activity.launchAboutUs()
        val intent: Intent =shadowActivity.nextStartedActivity
        val shadowIntent: ShadowIntent =shadowOf(intent)
        //very simple resolution
        //this code only check intent class and not other things
        //see other tests for how to do this correctly
        assertThat(shadowIntent.intentClass.name, equalTo(AboutUsActivity::class.java.getName()))
    }

    @Test
    fun `Options menu of MainActivity is correctly initialized`() {
        val activity= createActivity()
        validateActivity(activity)
    }

    @Test
    fun `AboutUs activity can be launched by selecting from Options Menu`() {
        val activity= createActivity()
        val shadowActivity: ShadowActivity = Shadows.shadowOf(activity)
        shadowActivity.clickMenuItem(R.id.action_about)
        val expectedIntent = Intent(activity, AboutUsActivity::class.java)
        val actual=shadowOf(activity).nextStartedActivity

        // Determine if two intents are the same for the purposes of intent resolution (filtering).
        // That is, if their action, data, type, class, and categories are the same. This does
        // not compare any extra data included in the intents
        assertThat("Expected intent:${expectedIntent}, actualIntent:${actual}",actual.filterEquals(expectedIntent))
    }

    @Test
    fun `Simulate phone call - pause-and-resume`() {
        val activity= createActivity()
        controller.pause().resume()

        validateActivity(activity)
    }

    @Test
    fun `Simululate activity re-creation`() {
        val bundle = Bundle()

        val initialActivity=createActivity()
        validateActivity(initialActivity)

        // Destroy the original activity
        controller
                .saveInstanceState(bundle)
                .pause()
                .stop()
                .destroy()

        // Bring up a new activity
        controller = Robolectric.buildActivity(MainActivity::class.java)
                .create(bundle)
                .start()
                .restoreInstanceState(bundle)
                .resume()
                .visible()
        val activity = controller.get()

        validateActivity(activity)
    }

    @Test
    @Config(qualifiers = "+port")
    fun `Simulate rotation`() {

        val activity= createActivity()
        validateActivity(activity)
        RuntimeEnvironment.setQualifiers("+land");
        controller.configurationChange();
        validateActivity(activity)



    }
    fun validateActivity( activity:MainActivity) {
        //confirm options menu is correct
        val shadowActivity: ShadowActivity = Shadows.shadowOf(activity)
        val menu = shadowActivity.optionsMenu
        assertNotNull(menu)
        val aboutItem = menu.findItem(R.id.action_about)
        val expectedTitle=activity.getText(R.string.action_about)
        assertThat("Title from resources ${expectedTitle} equal real title ${aboutItem.title}",expectedTitle.equals(aboutItem.title))

        //check recyclerview is here
        val recyclerView=shadowActivity.findViewById(recyclerView)
        assertNotNull("main list still here",recyclerView)

    }

}