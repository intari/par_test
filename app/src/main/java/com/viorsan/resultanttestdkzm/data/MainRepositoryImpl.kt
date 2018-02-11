package com.viorsan.resultanttestdkzm.data

import com.viorsan.resultanttestdkzm.data.network.ServerAPI
import com.viorsan.resultanttestdkzm.data.network.provider.retrofit
import com.viorsan.resultanttestdkzm.model.CurrencyItem
import io.reactivex.Single
import net.intari.AndroidToolboxCore.Extensions.logThrowable
import net.intari.AndroidToolboxCore.Extensions.logger

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 * Implementation of Main Repository with network calls
 */
class MainRepositoryImpl: MainRepository {
    //our API instance
    val api = retrofit.create(ServerAPI::class.java)

    override fun getStocks(): Single<List<CurrencyItem>> = api.getStocks()
            .map {
                logger.info { "asof:${it.as_of}., stock:${it.stock}" }
                it.stock.orEmpty().map ( ::CurrencyItem )
            }

    companion object {

        val logger= logger<MainRepositoryImpl>()

    }
}


