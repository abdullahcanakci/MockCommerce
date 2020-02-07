package com.mockcommerce.utils

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { TokenInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideBackendApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl("192.168.1.102/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(tokenInterceptor)
        .build()
}

fun provideBackendApi(retrofit: Retrofit) : MockCommerceApi = retrofit.create(MockCommerceApi::class.java)