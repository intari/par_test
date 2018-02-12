package com.viorsan.resultanttestdkzm.model
import com.viorsan.resultanttestdkzm.data.network.dto.StockDto

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */

class CurrencyItem(val name:String, val price:Long, val amount:Float){

    /**
     * Construct CurrencyItem from DTO
     * per Название валюты - поле "name", цена - поле "volume", количество - поле "amount".
     * This is very strange and questions should be asked if this is REALLY thing customer needed
     */
    constructor(stockDto: StockDto): this (
            name = stockDto.name,
            amount = stockDto.price.amount,
            price = stockDto.volume
    )

}