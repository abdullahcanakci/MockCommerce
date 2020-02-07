package com.mockcommerce.utils

import okhttp3.Interceptor
import okhttp3.Response

// Source https://medium.com/@theanilpaudel/using-the-power-of-retrofit-okhttp-and-dagger-2-for-jwt-token-authentication-ad8db6121eac

class TokenInterceptor : Interceptor {
    var token: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Only endpoints in the "/user" and "/checkout" requires jwt tokens
        if(!original.url.encodedPath.contains("/user") || !original.url.encodedPath.contains(("/checkout"))) {
            return chain.proceed(original)
        }

        val request = original
            .newBuilder()
            .addHeader("Authorization", token)
            .url(original.url)
            .build()

        return chain.proceed(request)
    }

}
