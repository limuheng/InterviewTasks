package com.muheng.m800task.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.muheng.media17task.network.GithubApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private val instance: RetrofitClient by lazy { RetrofitClient() }

        fun get(): GithubApiService {
            return instance.retrofit.create(GithubApiService::class.java)
        }
    }

    private var retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GithubApiService.API_BASE_RUL)
                    .build()
}