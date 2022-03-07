package com.githubyss.mobile.common.net.request

import android.text.TextUtils
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.githubyss.mobile.common.kit.util.LogUtils
import com.githubyss.mobile.common.net.config.ComnetConfig
import com.githubyss.mobile.common.net.model.ComnetBasicNetworkModel
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.collections.HashMap

/**
 * ComnetNetworkRequest.kt
 * <Description>
 * <Details>
 *
 * @author Ace Yan
 * @github githubyss
 */
class ComnetNetworkRequest : JsonRequest<ComnetBasicNetworkModel> {

    /** ****************************** Properties ****************************** */

    companion object {
        val TAG: String = ComnetNetworkRequest::class.java.simpleName
        private const val PROTOCOL_CHARSET = "utf-8"
        private val PROTOCOL_CONTENT_TYPE = String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET)
    }

    private var headersMap = HashMap<String, String>()
    private var uuid = "" /* Universally Unique Identifier */
    private var bodyType = ""


    /** ****************************** Constructors ****************************** */

    constructor(method: Int = Method.GET, url: String, requestBody: String? = null, listener: Response.Listener<ComnetBasicNetworkModel>, errorListener: Response.ErrorListener) : super(method, url, requestBody, listener, errorListener) {
        bodyType = PROTOCOL_CONTENT_TYPE
        retryPolicy = DefaultRetryPolicy(ComnetConfig.SO_TIME_OUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        uuid = UUID.randomUUID()
            .toString()
    }

    constructor(url: String, listener: Response.Listener<ComnetBasicNetworkModel>, errorListener: Response.ErrorListener) : this(Method.GET, url, null, listener, errorListener)

    constructor(url: String, requestBody: String, listener: Response.Listener<ComnetBasicNetworkModel>, errorListener: Response.ErrorListener) : this(Method.POST, url, requestBody, listener, errorListener)


    /** ****************************** Override ****************************** */

    override fun getHeaders(): MutableMap<String, String> {
        if (!TextUtils.isEmpty(uuid)) {
            headersMap.put("uuid", uuid)
        }
        return headersMap
    }

    override fun getBodyContentType(): String {
        if (TextUtils.isEmpty(bodyType)) {
            bodyType = PROTOCOL_CONTENT_TYPE
        }
        return bodyType
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ComnetBasicNetworkModel> {
        try {
            for (entry in response?.headers?.entries ?: emptyMap<String, String>().entries) {
                if (entry.key == "login.flag") {
                    logD(TAG, "Volley network result >> login.flag")
                    val map = HashMap<String, String>()
                    map["responseCode"] = ComnetConfig.NEED_LOGIN_CODE
                    map["responseMsg"] = "login.flag"
                    val jsonObject = JSONObject(map as Map<*, *>)
                    val networkModel = ComnetBasicNetworkModel(jsonObject)
                    return Response.success(networkModel, HttpHeaderParser.parseCacheHeaders(response))
                }
            }

            val result = java.lang.String(response?.data, HttpHeaderParser.parseCharset(response?.headers))
                .toString()
            logD(tag = "Volley network result", msg = result)
            val jsonObject = JSONObject(result)
            val networkModel = ComnetBasicNetworkModel(jsonObject)
            return Response.success(networkModel, HttpHeaderParser.parseCacheHeaders(response))
        }
        catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        }
        catch (e: JSONException) {
            return Response.error(ParseError(e))
        }
    }


    /** ****************************** Functions ****************************** */

    fun getUuid(): String {
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID()
                .toString()
        }
        return uuid
    }

    fun setUuid(uuid: String) {
        if (!TextUtils.isEmpty(uuid)) {
            this@ComnetNetworkRequest.uuid = uuid
        }
    }

    fun setHeaders(headersMap: Map<String, String>) {
        this@ComnetNetworkRequest.headersMap.putAll(headersMap)
    }

    fun setBodyType(bodyType: String) {
        this@ComnetNetworkRequest.bodyType = bodyType
    }
}
