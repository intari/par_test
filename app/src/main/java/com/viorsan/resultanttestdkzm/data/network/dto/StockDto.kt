package com.viorsan.resultanttestdkzm.data.network.dto


/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
data class StockDto(val name:String, val price:PriceDto, val percent_change:Float,val volume:Long,val symbol:String)
