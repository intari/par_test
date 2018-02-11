package com.viorsan.resultanttestdkzm.model
import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.viorsan.resultanttestdkzm.data.network.dto.StockDto
import net.intari.AndroidToolboxCore.Extensions.logger

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 11.02.2018.
 */
@SuppressLint("ParcelCreator") //bug in Kotlin
@Parcelize
class CurrencyItem(val name:String, val volume:Long, val amount:Float): Parcelable {

    /**
     * Construct CurrencyItem from DTO
     * per Название валюты - поле "name", цена - поле "volume", количество - поле "amount".
     * This is very strange and questions should be asked if this is REALLY thing customer needed
     */
    constructor(stockDto: StockDto): this (
            name = stockDto.price.currency,
            amount = stockDto.price.amount,
            volume = stockDto.volume
    )

}