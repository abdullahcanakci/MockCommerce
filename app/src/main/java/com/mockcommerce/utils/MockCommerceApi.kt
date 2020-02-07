package com.mockcommerce.utils

import com.mockcommerce.models.AddressModel
import com.mockcommerce.models.CategoryModel
import com.mockcommerce.models.ProductModel
import com.mockcommerce.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MockCommerceApi {
    @POST("/api/user/")
    fun register(user: UserModel): Call<UserModel>

    @GET("/api/user/")
    fun getUserInfo(): Call<UserModel>

    @GET("/api/user/addresses")
    fun getAddresses(): Call<List<AddressModel>>

    @POST("/api/user/addresses")
    fun addAddress(address: AddressModel)

    @GET("/api/user/addresses/{id}")
    fun getAddressById(@Path("id") addressId: String): Call<AddressModel>

    @POST("/api/user/basket")
    fun addToBasket(product_id: String, amount: Int = 1)

    @GET("/api/user/basket")
    fun getBasket(): Call<List<ProductModel>>

    @POST("/api/user/postponed")
    fun addToPostponed(product_id: String, amount: Int = 1)

    @GET("/api/user/postponed")
    fun getPostponed(): Call<List<ProductModel>>

    @GET("/api/products/")
    fun getAllProducts(): Call<List<ProductModel>>

    @GET("/api/products/{id}")
    fun getProduct(@Path("id") productId: String): Call<ProductModel>

    @GET("/api/categories/")
    fun getCategories(): Call<List<CategoryModel>>

    @GET("/api/categories/{id}")
    fun getProductsByCategory(@Path("id") categoryId: String): Call<List<ProductModel>>


}