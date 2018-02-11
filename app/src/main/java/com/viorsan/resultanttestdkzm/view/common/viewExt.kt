package com.viorsan.resultanttestdkzm.view.common

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Lazy delegate which provide view by it's it
 */
fun <T: View> RecyclerView.ViewHolder.bindView(viewId: Int) = lazy { itemView.findViewById<T>(viewId)}

/**
 * Simplify SwipeToRefresh usage
 */
fun Activity.bindToSwipeRefresh(@IdRes swipeRefreshLayoutId: Int): ReadWriteProperty<Any?, Boolean> = SwipeToRefreshBinding(lazy {
    findViewById<SwipeRefreshLayout>(swipeRefreshLayoutId) })

private class SwipeToRefreshBinding(lazyViewProvider: Lazy<SwipeRefreshLayout>):ReadWriteProperty<Any?, Boolean> {
    val view by lazyViewProvider
    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return view.isRefreshing
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        view.isRefreshing=value
    }
}