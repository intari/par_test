package com.viorsan.resultanttestdkzm.Interfaces

import android.content.Context
import android.content.Intent

/**
 * Created by Dmitriy Kazimirov, e-mail:dmitriy.kazimirov@viorsan.com on 26.01.2018.
 * Helper class for integration with Kodein
 * Allows tricks like   bind<CreatorInterface>("AboutUsActivity") with singleton { AboutUsActivity.creator() }
 * It's assumed that this will be used as interface on companion object of Activity/Service/etc
 * Nothing prevents user from using this with something other than Activity/Service but it's not usually needed
 */


interface CreatorInterface {
    /**
     * Returns intent which can be used to create this activity
     * @param context parent context
     * @return intent Intent(context, <<activity_class>>::class.java)
     */
    abstract fun getIntent(context: Context): Intent

    /**
     * Returns instance of this 'class' (even if it's not's class in some sense)
     * @return instance of this 'class'
     */
    abstract fun creator(): CreatorInterface
}

