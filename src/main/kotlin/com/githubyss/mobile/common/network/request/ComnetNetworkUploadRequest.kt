package com.githubyss.mobile.common.network.request

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.githubyss.mobile.common.kit.util.LogcatUtils
import com.githubyss.mobile.common.network.config.ComnetConfig
import com.githubyss.mobile.common.network.model.ComnetBasicNetworkModel
import org.apache.http.Consts
import org.apache.http.HttpEntity
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException

/**
 * ComnetNetworkUploadRequest.kt
 * <Description>
 * <Details>
 *
 * @author Ace Yan
 * @github githubyss
 */
class ComnetNetworkUploadRequest : Request<ComnetBasicNetworkModel> {
    constructor(method: Int = Method.POST,
                url: String,
                map: Map<String, Any>,
                repeatCount: Int = 0,
                listener: Response.Listener<ComnetBasicNetworkModel>,
                errorListener: Response.ErrorListener) : super(method, url, errorListener) {
        this@ComnetNetworkUploadRequest.listener = listener
        val builder = MultipartEntityBuilder.create()
        for (entry in map.entries) {
            val key = entry.key
            val value = entry.value
            if (value is String) {
                builder.addPart(key, StringBody(value, ContentType.create("text/plain", Consts.ASCII)))
            } else if (value is ByteArray) {
                builder.addPart(key, ByteArrayBody(value, "$key.png"))
            }
        }
        httpEntity = builder.build()
        retryPolicy = DefaultRetryPolicy(ComnetConfig.SO_TIME_OUT, repeatCount, 1.0F)
    }


    private var headersMap: HashMap<String, String>? = null
    private var listener: Response.Listener<ComnetBasicNetworkModel>? = null
    private var httpEntity: HttpEntity? = null


    fun setHeaders(headersMap: Map<String, String>) {
        this@ComnetNetworkUploadRequest.headersMap ?: HashMap<String, String>()
        this@ComnetNetworkUploadRequest.headersMap?.putAll(headersMap)
    }


    override fun getHeaders(
    ): Map<String, String> = this@ComnetNetworkUploadRequest.headersMap ?: super.getHeaders()

    override fun getBody(): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        return try {
            httpEntity?.writeTo(byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        } catch (exception: IOException) {
            null
        }
    }

    override fun getBodyContentType(): String {
        return httpEntity?.contentType?.value ?: ""
    }

    override fun deliverResponse(response: ComnetBasicNetworkModel?) {
        listener?.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<ComnetBasicNetworkModel> {
        try {
            for (entry in response?.headers?.entries ?: emptyMap<String, String>().entries) {
                if (entry.key == "login.flag") {
                    LogcatUtils.d(tag = "Volley network result", msg = "login.flag")
                    val map = HashMap<String, String>()
                    map.put("responseCode", ComnetConfig.NEED_LOGIN_CODE)
                    map.put("responseMsg", "login.flag")
                    val jsonObject = JSONObject(map as Map<*, *>)
                    val networkModel = ComnetBasicNetworkModel(jsonObject)
                    return Response.success(networkModel, HttpHeaderParser.parseCacheHeaders(response))
                }
            }

            val result = java.lang.String(response?.data, HttpHeaderParser.parseCharset(response?.headers)).toString()
            LogcatUtils.d(tag = "Volley network result", msg = result)
            val jsonObject = JSONObject(result)
            val networkModel = ComnetBasicNetworkModel(jsonObject)
            return Response.success(networkModel, HttpHeaderParser.parseCacheHeaders(response))
        } catch (exception: UnsupportedEncodingException) {
            return Response.error(ParseError(exception))
        } catch (exception: JSONException) {
            return Response.error(ParseError(exception))
        }
    }
}
