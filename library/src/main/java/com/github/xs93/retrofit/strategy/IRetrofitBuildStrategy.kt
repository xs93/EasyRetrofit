package com.github.xs93.retrofit.strategy

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter

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
    fun okHttpClient(): OkHttpClient

    /** 构建Retrofit 的Converter.Factory */
    fun converterFactory(): Converter.Factory?

    /** 构建Retrofit 的CallAdapter.Factory */
    fun callAdapterFactory(): CallAdapter.Factory?
}