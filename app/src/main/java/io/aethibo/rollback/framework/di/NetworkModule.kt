package io.aethibo.rollback.framework.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.aethibo.data.api.ApiService
import io.aethibo.rollback.framework.utils.AppConst
import io.aethibo.rollback.framework.utils.interceptors.TimberLoggingInterceptor
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule: Module = module {

    single {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(TimberLoggingInterceptor())
            .build()

        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(moshi)

        Retrofit.Builder()
            .baseUrl(AppConst.baseUrl)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}