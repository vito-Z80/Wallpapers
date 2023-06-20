package com.serdjuk.wallpapers.provider.net

import com.serdjuk.wallpapers.API_KEY
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class Client {

    private val headers = Headers.headersOf("Authorization", API_KEY)
    private val client = OkHttpClient()

    fun response(urlPath: String): Response {
        val urlBuilder: HttpUrl.Builder = urlPath.toHttpUrlOrNull()!!.newBuilder()
        val url: String = urlBuilder.build().toString()
        val request: Request = Request.Builder().url(url).headers(headers).build()
        return client.newCall(request).execute()
    }

//    fun body(response: Response) = response.body?.string() ?: ""
//    fun headers(response: Response) = response.headers
//    fun statusCode(response: Response) = response.code
}