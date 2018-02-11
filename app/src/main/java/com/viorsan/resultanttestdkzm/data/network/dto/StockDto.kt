package com.viorsan.resultanttestdkzm.data.network.dto


/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
data class StockDto(val name:String, val price:PriceDto, val percent_change:Float,val volume:Long,val symbol:String)
/*
class StockDto {
    lateinit var name: String
    lateinit var price: PriceDto
    var percent_change: Float = 0.0f // by Delegates.notNull<Float>()
    var volume: Long = 0L //by Delegates.notNull<Long>()
    lateinit var symbol: String

}
        */