package com.viorsan.resultanttestdkzm.data.network.dto


/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
data class PriceDto (val currency:String, val amount:Float)
/*
class PriceDto {
    lateinit var currency: String
    var amount: Float=0.0f;//should be lateinit but not possible for primitives

}
*/