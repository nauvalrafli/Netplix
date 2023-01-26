package com.example.netplix.data.repo

import com.example.netplix.data.api.ApiUrl
import com.example.netplix.data.api.TMDBApi
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

@ActivityScoped
class BaseRepo @Inject constructor() {

    private fun createRetrofit(): Retrofit {
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(ApiUrl.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideApi() = createRetrofit().create<TMDBApi>()

    val apiKey = "d899c886e98c93082a835ffeb6898b75"
}