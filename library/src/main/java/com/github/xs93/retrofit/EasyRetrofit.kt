package com.github.xs93.retrofit

import android.annotation.SuppressLint
import android.content.Context
import com.github.xs93.retrofit.retorfit.EasyRetrofitClient
import com.github.xs93.retrofit.retorfit.IRetrofitClient
import com.github.xs93.retrofit.strategy.EasyRetrofitBuildStrategy
import com.github.xs93.retrofit.strategy.IRetrofitBuildStrategy

/**
 *
 * EasyRetrofit管理类
 *
 * @author xushuai
 * @date   2022/9/2-14:10
 * @email  466911254@qq.com
 */
@SuppressLint("StaticFieldLeak")
object EasyRetrofit {

    private var mApp: Context? = null
    private var mBaseUrl: String? = null
    private var mStrategy: IRetrofitBuildStrategy? = null

    fun init(context: Context, baseUrl: String, strategy: IRetrofitBuildStrategy? = null) {
        mApp = context.applicationContext
        mBaseUrl = baseUrl
        mStrategy = strategy
    }

    fun getApp(): Context {
        return mApp ?: throw IllegalStateException("please call HttpManager.init() method")
    }

    fun getBaseUrl(): String {
        return mBaseUrl ?: throw IllegalStateException("please call HttpManager.init() method")
    }

    fun getStrategy(): IRetrofitBuildStrategy {
        return mStrategy ?: EasyRetrofitBuildStrategy().also {
            mStrategy = it
        }
    }

    fun <T> create(retrofitClient: IRetrofitClient = EasyRetrofitClient, service: Class<T>): T {
        return retrofitClient.create(service)
    }
}