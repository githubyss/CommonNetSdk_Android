package com.githubyss.common.net.model

import android.text.TextUtils
import org.json.JSONException
import org.json.JSONObject

/**
 * ComnetBasicNetworkModel.kt
 *
 * @author Ace Yan
 * @github githubyss
 */
class ComnetBasicNetworkModel private constructor() {
    constructor(jsonObject: JSONObject) : this() {
        try {
            setProperties(jsonObject)
        } catch (exception: JSONException) {

        }
    }


    var jsonObject: JSONObject = JSONObject()


    private fun setProperties(jsonObject: JSONObject) {
        if (jsonObject.has("encrypt")) {
            try {
                val responseData = jsonObject.optString("responseContent")
                if (!TextUtils.isEmpty(responseData)) {
                    val originalData = "Decrypted String"
                    this@ComnetBasicNetworkModel.jsonObject = JSONObject(originalData)
                }
            } catch (exception: Exception) {
                throw JSONException(exception.message)
            }
        } else {
            this@ComnetBasicNetworkModel.jsonObject = jsonObject
        }
    }
}
