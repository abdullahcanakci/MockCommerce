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
    @POST("user/")
    fun register(user: UserModel) : Call<UserModel>

    @GET("user/")
    fun getUserInfo() : Call<UserModel>

    @GET("user/addresses")
    fun getAddresses() : Call<List<AddressModel>>

    @POST("user/addresses")
    fun addAddress(address: AddressModel)

    @GET("user/addresses/{id}")
    fun getAddressById(@Path("id") addressId: String) : Call<AddressModel>

    @POST("user/basket")
    fun addToBasket(product_id: String, amount: Int = 1)

    @GET("user/basket")
    fun getBasket() : Call<List<ProductModel>>

    @POST("user/postponed")
    fun addToPostponed(product_id: String, amount: Int = 1)

    @GET("user/postponed")
    fun getPostponed() : Call<List<ProductModel>>


    @GET("products/")
   fun getAllProducts() : Call<List<ProductModel>>

    @GET("products/{id}")
    fun getProduct(@Path("id") productId: String) : Call<ProductModel>

    @GET("categories/")
    fun getCategories() : Call<List<CategoryModel>>

    @GET("categories/{id}")
    fun getProductsByCategory(@Path("id") categoryId: String) : Call<List<ProductModel>>
}