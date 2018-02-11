package com.viorsan.resultanttestdkzm.data.network

import com.viorsan.resultanttestdkzm.Constants
import com.viorsan.resultanttestdkzm.data.network.dto.StockListDto
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
interface ServerAPI {
    @GET(Constants.STOCKS)
    fun getStocks(): Single<StockListDto>

}