package com.viorsan.resultanttestdkzm.data

import com.viorsan.resultanttestdkzm.data.network.dto.StockListDto
import com.viorsan.resultanttestdkzm.model.CurrencyItem
import io.reactivex.Single

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Definition of our main repository
 */
interface MainRepository {
    //function which return list of Currency items from server's DTOs
    fun getStocks(): Single<List<CurrencyItem>>
}