package com.viorsan.resultanttestdkzm.helpers

import com.viorsan.resultanttestdkzm.model.CurrencyItem

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 12.02.2018.
 */
object Example {

    val exampleCurrencyItem = CurrencyItem("Example Name",1100,1.37f)
    val exampleItemList= listOf(
            CurrencyItem("PHP",1200,13.5f),
            CurrencyItem("Name 2",1300,3.14f),
            CurrencyItem("Name 3",1400,2.78f))

}