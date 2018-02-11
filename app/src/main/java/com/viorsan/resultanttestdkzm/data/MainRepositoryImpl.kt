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

    /*
    fun getStocks2(): Single<List<CurrencyItem>> = api.getStocks()
            .map {
                logger.info { "asof:${it.data?.results?.as_of}.data:${it.data}, stock:${it.data?.results?.stock}" }
                it.data?.results?.stock.orEmpty().map ( ::CurrencyItem )
        }
        */
    override fun getStocks(): Single<List<CurrencyItem>> = api.getStocks()
            .map {
                logger.info { "asof:${it.as_of}., stock:${it.stock}" }
                it.stock.orEmpty().map ( ::CurrencyItem )
            }

    /*
    override fun getStocks2(): Single<List<CurrencyItem>> {
        val stocks=api.getStocks()
        logger.info { "Stocks:${stocks}"}
        try {
            val rr= stocks.blockingGet()
            logger.info { rr.toString() }

        } catch (t:Throwable) {
            logger.logThrowable(t)
        }
        stocks.map {
            logger.info { "it:${it}" }
        }

        return getStocks()
    }
    */



    companion object {

        val logger= logger<MainRepositoryImpl>()

    }
}


