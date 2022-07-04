package com.vitalidudarenka.data.network.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "I9mj2ppmx6e2mIDeGJne77tte0TW4MpF"

class HeaderInterceptor (context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request =
            chain.request().newBuilder()
                .addHeader("apikey", API_KEY).build()
        return chain.proceed(request)
    }

}