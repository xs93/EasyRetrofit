package com.github.xs93.retrofit.strategy

import android.content.Context
import com.github.xs93.retrofit.EasyRetrofit
import com.github.xs93.retrofit.cookie.CookieJarManager
import com.github.xs93.retrofit.cookie.SharedPreferencesCookieStore
import com.github.xs93.retrofit.interceptor.DomainInterceptor
import com.itkacher.okprofiler.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 *
 *
 * @author xushuai
 * @date   2022/9/2-14:12
 * @email  466911254@qq.com
 */
class EasyRetrofitBuildStrategy : IRetrofitBuildStrategy {
    override fun okHttpClient(): OkHttpClient {
        val context: Context = EasyRetrofit.getApp()
        val cacheFile = File(context.cacheDir, "OkHttpCache")
        if (!cacheFile.exists()) {
            cacheFile.mkdirs()
        }
        val cache = Cache(cacheFile, 50L * 1024L * 1024L)

        val builder = OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(DomainInterceptor())
            cache(cache)
            cookieJar(CookieJarManager(SharedPreferencesCookieStore(context)))
            if (BuildConfig.DEBUG) {
                addInterceptor(OkHttpProfilerInterceptor())
            }
        }
        return builder.build()
    }

    override fun converterFactory(): Converter.Factory? {
        return MoshiConverterFactory.create()
    }

    override fun callAdapterFactory(): CallAdapter.Factory? {
        return null
    }
}