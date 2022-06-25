package fr.pitdev.dayoff.data.remote.interceptors

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit


class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val cacheControl: CacheControl = CacheControl.Builder().maxAge(365, TimeUnit.DAYS)
            .build()
        Log.d(TAG, "intercept: ${chain.request()}-> $cacheControl")
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }

    companion object {
        val TAG: String = CacheInterceptor::class.java.simpleName
    }
}