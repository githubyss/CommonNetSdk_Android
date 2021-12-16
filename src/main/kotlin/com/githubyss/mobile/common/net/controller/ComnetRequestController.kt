package com.githubyss.mobile.common.net.controller

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.githubyss.mobile.common.net.ComnetApplicationConfig
import com.githubyss.mobile.common.net.cache.ComnetBitmapCache

/**
 * ComnetRequestController.kt
 * <Description>
 * <Details>
 *
 * @author Ace Yan
 * @github githubyss
 */
class ComnetRequestController private constructor() {
    companion object {
        var instance = Holder.INSTANCE
        val TAG: String = "ComnetRequestController"
    }

    private object Holder {
        val INSTANCE = ComnetRequestController()
    }


    private var loginTag: String? = null
    var requestQueue: RequestQueue? = null
        private set
    var imageLoader: ImageLoader? = null
        private set


    init {
        requestQueue = requestQueue ?: Volley.newRequestQueue(ComnetApplicationConfig.application)
        imageLoader = imageLoader ?: ImageLoader(requestQueue, ComnetBitmapCache())
    }


    fun <T> addToRequestQueue(request: Request<T>,
                              tag: Any = TAG,
                              shouldCache: Boolean = false) {
        if (tag is String) {
            loginTag = tag
        }

        request.tag = tag
        request.setShouldCache(shouldCache)
        requestQueue?.add(request)
    }

    fun <T> addToRequestQueueWithoutCache(request: Request<T>, tag: Any) {
        addToRequestQueue(request, tag, false)
    }

    fun cancelPendingRequests(tag: Any) {
        requestQueue?.cancelAll(tag)
    }
}
