package com.mockcommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mockcommerce.models.*
import okhttp3.*
import kotlin.collections.ArrayList

class AppRepository(val client: OkHttpClient) {
    private val CACHE_POLICY = CacheControl.FORCE_NETWORK

    var basket = MutableLiveData<ArrayList<ProductModel>>(ArrayList())
    var postponed = MutableLiveData<ArrayList<ProductModel>>(ArrayList())
    var favourites = MutableLiveData<ArrayList<Int>>(ArrayList())
    var basketTotal = MutableLiveData<Float>(0.0F)

    private var loggedInUser: UserModel? = null

    fun getProductList(): LiveData<ArrayList<ProductModel>> {
        val responseList = MutableLiveData<ArrayList<ProductModel>>()

        return responseList
    }

    fun getProduct(id: String): MutableLiveData<ProductModel> {
        val response = MutableLiveData<ProductModel>()
        return response
    }

    fun getFavouriteProducts(): LiveData<ArrayList<ProductModel>> {
        val temp = ArrayList<ProductModel>()

        return MutableLiveData(temp)
    }

    fun setFavourite(productId: Int, addToFav: Boolean) {

    }

    fun getBasket(): LiveData<ArrayList<ProductModel>> {
        return basket
        // Removed network request for more streamlined user feel for demo
    }

    fun getBasketPostponed(): LiveData<ArrayList<ProductModel>> {
        return postponed
        // Removed network request for more streamlined user feel for demo
    }

    fun addToBasket(id: String?): Boolean {
        //TODO implement
        return false
    }

    fun subtract(id: String) {
        //TODO implement
    }

    fun addToPostponed(id: Int): Boolean {
        //TODO implement
        return false
    }

    fun moveToPostponed(id: String): Boolean {
        //TODO implement
        return false
    }

    fun moveToBasket(id: String): Boolean {
        //TODO implement
        return false
    }


    fun removeFromBasket(id: String): Boolean {
        //TODO implement
        return false
    }

    fun removeFromPostponed(id: String): Boolean {
        //TODO implement
        return false
    }

    fun getCategory(): LiveData<ArrayList<CategoryModel>> {
        val response = MutableLiveData<ArrayList<CategoryModel>>()

        //TODO implement
        return response
    }

    fun confirmPayment(shipmentId: String, billingId: String) {
        //TODO implement
    }

    fun login(email: String, password: String): Boolean {
        //TODO implement
        return false
    }

    fun logout(): Boolean {
        //TODO implement
        return false
    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }

    fun register(user: UserModel): Boolean {
        //TODO implement
        return false
    }

    fun addAddress(address: AddressModel) {
        //TODO implement
    }

    fun getAddresses(): LiveData<ArrayList<AddressModel>> {
        val response = MutableLiveData<ArrayList<AddressModel>>()
        //TODO implement
        return response
    }

    fun getAddress(id: String): LiveData<AddressModel> {
        val response = MutableLiveData<AddressModel>()

        //TODO implement

        return response

    }

    fun setDefaultAddress(id: String) {
        //TODO implement

    }

    fun getOrder(id: String): LiveData<OrderModel> {
        val response = MutableLiveData<OrderModel>()

        //TODO implement

        return response
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)

}