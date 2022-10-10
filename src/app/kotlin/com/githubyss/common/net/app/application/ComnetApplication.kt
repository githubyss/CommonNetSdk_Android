package com.githubyss.common.net.app.application

import com.githubyss.common.kit.base.application.BaseApplication
import kotlin.properties.Delegates


/**
 * ComnetApplication
 *
 * @author Ace Yan
 * @github githubyss
 * @createdTime 2021/08/24 19:34:38
 */
class ComnetApplication : BaseApplication() {

    /** ****************************** Properties ****************************** */

    companion object {
        var instance: ComnetApplication by Delegates.notNull()
            private set

        private val TAG: String = ComnetApplication::class.java.simpleName
    }


    /** ****************************** Override ****************************** */

    override fun onCreate() {
        super.onCreate()
        instance = this

        initARouter(instance)
    }
}
