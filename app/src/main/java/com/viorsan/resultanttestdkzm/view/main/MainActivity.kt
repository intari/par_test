package com.viorsan.resultanttestdkzm.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.github.salomonbrys.kodein.instance
import com.viorsan.resultanttestdkzm.Constants
import com.viorsan.resultanttestdkzm.view.common.BaseActivity
import com.viorsan.resultanttestdkzm.Interfaces.CreatorInterface
import com.viorsan.resultanttestdkzm.R
import com.viorsan.resultanttestdkzm.data.MainRepository
import com.viorsan.resultanttestdkzm.model.CurrencyItem
import com.viorsan.resultanttestdkzm.presenter.MainPresenter
import com.viorsan.resultanttestdkzm.view.common.BaseActivityWithPresenter
import com.viorsan.resultanttestdkzm.view.common.bindToSwipeRefresh
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_activity_list.*
import net.intari.AndroidToolboxCore.Extensions.logException
import net.intari.AndroidToolboxCore.Extensions.logThrowable
import net.intari.AndroidToolboxCore.Extensions.logger
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS



class MainActivity : BaseActivityWithPresenter(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private val aboutUsActivityCreator by kodein.instance<CreatorInterface>("AboutUsActivity")
    private val mainRepository by kodein.instance<MainRepository>("MainRepository")

    override var refresh by bindToSwipeRefresh(R.id.swipeRefreshView)

    override val presenter by lazy { MainPresenter(this,  mainRepository) }

    protected var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        //launch screen is done using https://habrahabr.ru/post/345380/
        //restore theme
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Activate swipe-to-refresh
        swipeRefreshView.setOnRefreshListener {
            presenter.onRefresh()
        }
        presenter.onViewCreated()

        //TODO:right side
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        //Configure regular liner layout manager for recycler view
        recyclerView.layoutManager=LinearLayoutManager(this)


    }

    /**
     * Show/update 'loading' items
     */
    override fun show(items:List<CurrencyItem>) {
        //create list o
        val currenclyAdapters = items
                .map(::CurrencyItemAdapter)

        //create & set main adapter
        recyclerView.adapter=MainListAdapter(currenclyAdapters)

        //alternative is have single big class for adapter and

    }

    /**
     * Show error if any
     */
    override fun showError(error: Throwable) {
        toast("Error ${error.message}")
        logger.logThrowable(error)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_about -> {
                launchAboutUs()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_refresh -> {
                //handle refresh
                presenter.onRefresh()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.END)
        return true
    }

    /**
     * Launch 'AboutUs' activity using it's creator
     */
    fun launchAboutUs() {
        val intent = aboutUsActivityCreator.getIntent(this)
        startActivity(intent)

    }

    override  fun onResume() {
        super.onResume()
        //activate automatic refresh
        Observable
                .interval(Constants.AUTOREFRESH_INTERVAL, TimeUnit.SECONDS)
                .doOnNext { presenter.onRefresh()}

    }
    companion object:CreatorInterface {
        val logger = logger<MainActivity>()
        override fun getIntent(context: Context): Intent {
            return  Intent(context, MainActivity::class.java)
        }
        override fun creator(): CreatorInterface {
            return this
        }
    }

}
