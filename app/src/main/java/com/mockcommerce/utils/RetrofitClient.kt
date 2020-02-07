package com.mockcommerce.utils

import com.mockcommerce.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { TokenInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideBackendApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit
        .Builder()
        //.baseUrl("https://www.example.com")
        .baseUrl(BuildConfig.SERVER_ROOT)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(logging)
        .build()
}

fun provideBackendApi(retrofit: Retrofit): MockCommerceApi {
    return retrofit.create(MockCommerceApi::class.java)
}