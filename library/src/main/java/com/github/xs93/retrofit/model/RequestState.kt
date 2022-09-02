package com.github.xs93.retrofit.model

/**
 *
 * 反应请求流程状态对象
 *
 * @author xushuai
 * @date   2022/9/2-14:37
 * @email  466911254@qq.com
 */
sealed class RequestState<out T> {

    object Loading : RequestState<Nothing>()

    data class Success<out T>(val data: T? = null) : RequestState<T>()

    data class Error(val throwable: Throwable) : RequestState<Nothing>()
}