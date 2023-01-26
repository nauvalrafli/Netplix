package com.example.netplix.utils

import retrofit2.Response

suspend fun <T> Response<T>.onResponseResult(
    onSuccess : (data : T) -> Unit,
    onError : (String) -> Unit
) {
    if (this.code() == 200) {
        this.body()?.let { onSuccess(it) }
    } else {
        onError(this.message())
    }
}