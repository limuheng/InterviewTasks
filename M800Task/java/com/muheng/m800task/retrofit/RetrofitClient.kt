package com.muheng.m800task.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Muheng Li on 2018/8/16.
 */

class RetrofitClient private constructor() {

    companion object {
        private val instance: RetrofitClient by lazy { RetrofitClient() }

        fun get(): IiTuneSearchApi {
            return instance.retrofit.create(IiTuneSearchApi::class.java)
        }
    }

    private var retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(IiTuneSearchApi.API_BASE_RUL)
                    .build()
}