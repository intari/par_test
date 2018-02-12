package com.viorsan.resultanttestdkzm.helpers

import com.viorsan.resultanttestdkzm.data.MainRepository
import io.reactivex.Single
import com.viorsan.resultanttestdkzm.model.CurrencyItem

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.02.2018.
 * Test helper to more easily produce fake main repository instead of with 'object'
 */
class BaseRepository(val onGetItems: () -> Single<List<CurrencyItem>>):MainRepository {
    override fun getStocks(): Single<List<CurrencyItem>>  = onGetItems()
}