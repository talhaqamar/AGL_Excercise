package au.com.agl.kotlincats.common

interface Callback<T> {
    fun onSuccess(data: T)
    fun onError(error: Throwable)
}