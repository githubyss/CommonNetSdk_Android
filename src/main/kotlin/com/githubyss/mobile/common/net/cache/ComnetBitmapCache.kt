package com.githubyss.mobile.common.net.cache

import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader

/**
 * ComnetBitmapCache.kt
 * <Description>
 * <Details>
 *
 * @author Ace Yan
 * @github githubyss
 */
class ComnetBitmapCache : ImageLoader.ImageCache {
    private var cache: LruCache<String, Bitmap>


    init {
        val maxSize = 10 * 1024 * 1024
        cache = object : LruCache<String, Bitmap>(maxSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                return (value?.rowBytes ?: 0) * (value?.height ?: 0)
            }
        }
    }


    override fun getBitmap(url: String?
    ): Bitmap = cache.get(url)

    override fun putBitmap(url: String?, bitmap: Bitmap?) {
        cache.put(url, bitmap)
    }
}
