package com.github.xs93.retrofit.strategy

import android.content.Context
import com.github.xs93.retrofit.EasyRetrofit
import com.github.xs93.retrofit.cookie.CookieJarManager
import com.github.xs93.retrofit.cookie.SharedPreferencesCookieStore
import com.github.xs93.retrofit.interceptor.DomainInterceptor
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Cache
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 构建一个Retrofit client需要的客户端
 *
 *
 * @author xushuai
 * @date   2022/9/2-14:11
 * @email  466911254@qq.com
 */
interface IRetrofitBuildStrategy {
    /** 构建Retrofit 的OkHttpClient */
    fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(getTimeout(), TimeUnit.SECONDS)
            writeTimeout(getTimeout(), TimeUnit.SECONDS)
            readTimeout(getTimeout(), TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            cache(getCache())
            cookieJar(getCookieJar())
            addInterceptor(DomainInterceptor())
            getInterceptors().let {
                for (interceptor in it) {
                    addInterceptor(interceptor)
                }
            }
            getNetworkInterceptors().let {
                for (interceptor in it) {
                    addNetworkInterceptor(interceptor)
                }
            }

            if (EasyRetrofit.isOpenOkHttpProfiler()) {
                addInterceptor(OkHttpProfilerInterceptor())
            }
        }
        return builder.build()
    }

    /** 构建Retrofit 的Converter.Factory */
    fun converterFactory(): Converter.Factory?

    /** 构建Retrofit 的CallAdapter.Factory */
    fun callAdapterFactory(): CallAdapter.Factory?

    fun getTimeout(): Long {
        return 30
    }

    fun getCache(): Cache {
        val context: Context = EasyRetrofit.getApp()
        val cacheFile = File(context.cacheDir, "OkHttpCache")
        if (!cacheFile.exists()) {
            cacheFile.mkdirs()
        }
        return Cache(cacheFile, 50L * 1024L * 1024L)
    }

    fun getCookieJar(): CookieJar {
        val context: Context = EasyRetrofit.getApp()
        return CookieJarManager(SharedPreferencesCookieStore(context))
    }

    fun getInterceptors(): List<Interceptor> {
        return emptyList()
    }

    fun getNetworkInterceptors(): List<Interceptor> {
        return emptyList()
    }
}