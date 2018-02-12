package com.viorsan.resultanttestdkzm

import com.viorsan.resultanttestdkzm.data.MainRepository
import com.viorsan.resultanttestdkzm.model.CurrencyItem
import com.viorsan.resultanttestdkzm.presenter.MainPresenter
import com.viorsan.resultanttestdkzm.view.main.MainView
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.02.2018.
 */

@Suppress("IllegalIdentifier") //allow 'fancy' test names
class MainPresenterTest {

    @Test
    fun `Интересное имя теста`() {

    }

    @Test
    fun `After view was created, list of items is loaded and displayed`() {
        assertOnAction { onViewCreated() }
                .thereIsSameListDisplayed()
    }
    
    @Test
    fun `New list is shown after view was refreshed`() {
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
            val view = object : MainView {
                override var refresh: Boolean = false
                override fun show(items: List<CurrencyItem>) {
                    displayedList = items
                }

                override fun showError(error: Throwable) {
                    fail()
                }
            }

            //fake main repository, returns example list
            val mainRepository = object : MainRepository {
                override fun getStocks(): Single<List<CurrencyItem>> = Single.just(exampleList)
            }

            val mainPresenter = MainPresenter(view,mainRepository)

            //When we do action on main presenter..
            mainPresenter.actionOnPresenter()

            //result is list example list == displayed list (so it was correctly passed to main view)
            assertEquals(exampleList,displayedList)

        }
    }
}