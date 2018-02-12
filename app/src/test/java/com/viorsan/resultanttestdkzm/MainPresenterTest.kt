package com.viorsan.resultanttestdkzm

import com.viorsan.resultanttestdkzm.data.MainRepository
import com.viorsan.resultanttestdkzm.helpers.BaseMainView
import com.viorsan.resultanttestdkzm.helpers.BaseRepository
import com.viorsan.resultanttestdkzm.helpers.Example
import com.viorsan.resultanttestdkzm.model.CurrencyItem
import com.viorsan.resultanttestdkzm.presenter.MainPresenter
import com.viorsan.resultanttestdkzm.view.main.MainView
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.02.2018.
 */

@Suppress("IllegalIdentifier") //allow 'fancy' test names
class MainPresenterTest {

    /*
      We using at least AndroidSchedulers from rx on network mock test
      Not a good idea:
      - Sync issues in tests
      - http://tools.android.com/tech-docs/unit-testing-support#TOC-Method-...-not-mocked.- due to inability to init AndroidSchedulers
      We need to modify configuration of schedulers
     */
    @Before
    fun setUp() {
        //just use scheduler which works sequentially on current thread for android main thread scheduler
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        // set same for Rx's 'IO' scheduler
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        // ..and Rx's computation one
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        // ..and even basic newThread one
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `И такое имя теста тоже можно`() {

    }

    @Test
    fun `After view was created, list of items is loaded and displayed`() {
        assertOnAction { onViewCreated() }
                .thereIsSameListDisplayed()
    }

    @Test
    fun `List is shown after view was refreshed`() {
        assertOnAction { onRefresh() }
                .thereIsSameListDisplayed()
    }

    private fun assertOnAction(action: MainPresenter.() -> Unit) = PresenterActionAssertion(action)

    private class PresenterActionAssertion(val actionOnPresenter: MainPresenter.()->Unit) {
        fun thereIsSameListDisplayed() {
            // Initial
            val exampleList= listOf(
                    CurrencyItem("PHP",1200,13.5f),
                    CurrencyItem("Name 2",1300,3.14f),
                    CurrencyItem("Name 3",1400,2.78f))

            var displayedList : List<CurrencyItem>? = null

            //fake main view
            val view = BaseMainView(onShow = {
                items -> displayedList=items},
                    onShowError = { fail()}
            )

            //fake main repository, returns example list
            val mainRepository = BaseRepository( onGetItems = { Single.just(Example.exampleItemList)})

            val mainPresenter = MainPresenter(view,mainRepository)

            //When we do action on main presenter..
            mainPresenter.actionOnPresenter()

            //result is list example list == displayed list (so it was correctly passed to main view)
            assertEquals(Example.exampleItemList,displayedList)

        }
    }
}