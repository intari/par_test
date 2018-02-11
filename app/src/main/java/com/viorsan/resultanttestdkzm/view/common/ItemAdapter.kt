package com.viorsan.resultanttestdkzm.view.common

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Base abstract RecyclerView ItemAdapter
 * This is baicall row holder
 */
abstract class ItemAdapter<T: RecyclerView.ViewHolder> (@LayoutRes open val layoutId: Int) {
    abstract fun onCreateViewHolder(itemView: View): T

    @SuppressWarnings("UNCHECKED_CAST") //we knew it's safe to cast here so hide warning
    fun bindViewHolder(holder: RecyclerView.ViewHolder) {
        (holder as T).onBindViewHolder()
    }

    abstract fun T.onBindViewHolder()

}