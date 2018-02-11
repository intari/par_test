package com.viorsan.resultanttestdkzm.view.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.viorsan.resultanttestdkzm.R
import com.viorsan.resultanttestdkzm.model.CurrencyItem
import com.viorsan.resultanttestdkzm.view.common.ItemAdapter
import com.viorsan.resultanttestdkzm.view.common.bindView


/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Specialization of our 'universal' adapter for Currency Records
 */
class CurrencyItemAdapter(val currencyItem: CurrencyItem):
        ItemAdapter<CurrencyItemAdapter.ViewHolder>(R.layout.item_currency) {
    override fun onCreateViewHolder(itemView: View) = ViewHolder(itemView)


    override fun ViewHolder.onBindViewHolder() {

        /**
         * Ячейка валюты - название валюты, цена и количество.
         * Название валюты - поле "name", цена - поле "volume", количество - поле "amount". V
         * Volume отображается как целое число.
         * Amount - 2 знака после запятой. Высота ячейки - 50.
         * Есть подозрение что в постановке задачи - ошибка. Делаю по тексту.
         */
        currencyNameTextView.text = currencyItem.name
        currencyAmountTextView.text = "%.2f".format(currencyItem.amount) // it was requested that amount will have 2 decimal symbols
        currencyPriceTextView.text=currencyItem.volume.toString()
        //TODO:add click listeners, image loading etc here if needed in future
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyNameTextView by bindView<TextView>(R.id.currencyName)
        val currencyPriceTextView by bindView<TextView>(R.id.currencyPrice)
        val currencyAmountTextView by bindView<TextView>(R.id.currencyAmount)

    }

}
