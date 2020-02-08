package com.mockcommerce.utils

import com.mockcommerce.models.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MockCommerceApi {
    @POST("/api/user/")
    fun register(user: UserModel): Single<UserModel>

    @POST("/api/login")
    fun login(@Body user: UserModel): Single<TokenModel>

    @GET("/api/user/")
    fun getUserInfo(): Single<UserModel>

    @GET("/api/user/addresses")
    fun getAddresses(): Observable<List<AddressModel>>

    @POST("/api/user/favourite/{id}")
    fun toggleFavouriteProduct(@Path("id") id: String): Single<ProductModel>

    @POST("/api/user/addresses")
    fun addAddress(@Body address: AddressModel): Single<ResponseBody>

    @GET("/api/user/addresses/{id}")
    fun getAddressById(@Path("id") addressId: String): Call<AddressModel>

    @GET("/api/user/basket")
    fun getBasket(): Single<List<ProductModel>>

    @POST("/api/user/basket/{id}")
    fun addToBasket(@Path("id") productId: String): Single<ResponseBody>

    @POST("/api/user/basket/{id}/{amount}")
    fun setBasket(@Path("id") productId: String, @Path("amount") amount: Int): Single<ResponseBody>

    @POST("/api/user/postponed{id}")
    fun addToPostponed(@Path("id") productId: String)

    @GET("/api/user/postponed")
    fun getPostponed(): Single<List<ProductModel>>

    @GET("/api/products/")
    fun getAllProducts(): Single<List<ProductModel>>

    @GET("/api/products/{id}")
    fun getProduct(@Path("id") productId: String): Single<ProductModel>

    @GET("/api/categories/")
    fun getCategories(): Single<List<CategoryModel>>

    @GET("/api/categories/{id}")
    fun getProductsByCategory(@Path("id") categoryId: String): Single<List<ProductModel>>
}