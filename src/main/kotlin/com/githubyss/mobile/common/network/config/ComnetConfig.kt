package com.githubyss.mobile.common.network.config

import com.android.volley.VolleyLog

/**
 * ComnetConfig.kt
 * <Description>
 * <Details>
 *
 * @author Ace Yan
 * @github githubyss
 */
object ComnetConfig {
    var DEBUG = VolleyLog.DEBUG
    val ENCODE = "UTF-8"
    val CONNECT_TIME_OUT = 15 * 1000
    val SO_TIME_OUT = 15 * 1000
    val LOGIN_TAG = "xxx_login"
    val NEED_LOGIN_CODE = "0123"
}
