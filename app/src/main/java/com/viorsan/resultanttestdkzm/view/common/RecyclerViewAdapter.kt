package com.viorsan.resultanttestdkzm.view.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * This is more universal solution than requested
 * We can have more than one item type for example
 * Alternatives:
 * - simple ListView
 * - more simple RecylerView implementation
 */
open class RecyclerViewAdapter(
        var items: List<AnyItemAdapter> = listOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override final fun getItemCount() = items.size
    override final fun getItemViewType(position: Int): Int =
        items[position].layoutId


    override final fun onCreateViewHolder(parent: ViewGroup, layoutId:Int): RecyclerView.ViewHolder {

        //Inflate layout
        val itemView = LayoutInflater.from(parent.context)
                .inflate(layoutId,parent,false)

        // We will use layout to distinguish between item types
        return items.first {
            it.layoutId == layoutId
        }.onCreateViewHolder(itemView)

    }

    override final fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].bindViewHolder(holder)
    }
}

typealias AnyItemAdapter = ItemAdapter<out RecyclerView.ViewHolder> //Just make it simple
