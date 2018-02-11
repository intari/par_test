package com.viorsan.resultanttestdkzm.data.network.dto

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
data class StockListDto (val stock:List<StockDto>,val as_of:String)
/*
class StockListDto {
    lateinit var stock:List<StockDto>
    lateinit var as_of:String
}
        */