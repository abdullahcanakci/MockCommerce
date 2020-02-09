package com.mockcommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mockcommerce.models.*
import com.mockcommerce.utils.MockCommerceApi
import com.mockcommerce.utils.TokenInterceptor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.CacheControl
import okhttp3.ResponseBody

class AppRepository(val client: MockCommerceApi, val tokenInterceptor: TokenInterceptor) {

    private val CACHE_POLICY = CacheControl.FORCE_NETWORK

    private val offlineBasket = MutableLiveData<ArrayList<String>>(ArrayList())
    private val offlinePostponed = MutableLiveData<ArrayList<String>>(ArrayList())
    var favourites = MutableLiveData<ArrayList<Int>>(ArrayList())
    var basketTotal = MutableLiveData<Float>(0.0F)

    private var loggedInUser: UserModel? = null

    fun getProductList(id: String): Single<List<ProductModel>> {
        return client
            .getProductsByCategory(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getProduct(id: String): Single<ProductModel> {
        return client
            .getProduct(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFavouriteProducts(): LiveData<ArrayList<ProductModel>> {
        val temp = ArrayList<ProductModel>()

        return MutableLiveData(temp)
    }

    fun setFavourite(productId: String): Single<ProductModel> {
        return client
            .toggleFavouriteProduct(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBasket(): Single<List<ProductModel>> {
        return client
            .getBasket()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBasketPostponed(): Single<List<ProductModel>> {
        return client
            .getPostponed()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addToBasket(id: String): Single<ProductModel> {
        return client
            .addToBasket(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setBasketAmount(id: String, amount: Int): Single<ResponseBody> {
        return client
            .setBasket(id, amount)
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

    fun getCategory(): Single<List<CategoryModel>> {
        return client
            .getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun confirmPayment(shipmentId: String, billingId: String) {
        //TODO implement
    }

    fun login(email: String, password: String): Single<String> {
        return client
            .login(UserModel("", "", password, email, ""))
            .subscribeOn(Schedulers.io())
            .map { result -> result.token }
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }

    fun register(user: UserModel): Single<UserModel> {
        return client
            .register(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addAddress(address: AddressModel): Single<ResponseBody> {
        return client
            .addAddress(address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAddresses(): Single<List<AddressModel>> {
        return client
            .getAddresses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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