package com.githubyss.mobile.common.network

import android.app.Application

/**
 * ComnetApplication.kt
 * <Description>
 * <Details>
 *
 * @author Ace Yan
 * @github githubyss
 */
class ComnetApplication private constructor() {
    companion object {
        var instance = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = ComnetApplication()
    }


    var application: Application? = null
        private set


    fun init(application: Application) {
        if (ComnetApplication.instance.application != null) {
            return
        }

        ComnetApplication.instance.application = application
    }
}
