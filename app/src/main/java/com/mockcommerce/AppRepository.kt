package com.mockcommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mockcommerce.models.AddressModel
import com.mockcommerce.models.CategoryModel
import com.mockcommerce.models.ProductModel
import com.mockcommerce.models.UserModel
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class AppRepository(val client: OkHttpClient) {
    private val CACHE_POLICY = CacheControl.FORCE_NETWORK

    private val root =
        "https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver"
    private val categories = "/categories"

    private var products = ArrayList<ProductModel>()

    private var addresses = MutableLiveData<ArrayList<AddressModel>>()
    private var basket = MutableLiveData<ArrayList<ProductModel>>()
    private var postponed = MutableLiveData<ArrayList<ProductModel>>()

    private var loggedInUser: UserModel? = null

    private var users: ArrayList<UserModel> = ArrayList(
        listOf(
            UserModel(
                "Empty",
                "User",
                "",
                "",
                "333 123 98 12"
            ),
            UserModel(
                "Admin",
                "Adminson",
                "123456",
                "admin",
                "500 123 45 67"
            )
        )
    )

    init {
        getProductList()
    }

    fun getProductList(): LiveData<ArrayList<ProductModel>> {
        val responseList = MutableLiveData<ArrayList<ProductModel>>()
        if (products.isEmpty()) {
            val request = Request.Builder()
                .url("$root/product_list.json")
                .cacheControl(CACHE_POLICY)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Timber.d("No response received")
                }

                override fun onResponse(call: Call, response: Response) {
                    val str = response.body!!.string()
                    val list: ArrayList<ProductModel> = Gson().fromJson(str)
                    products.addAll(list)
                }
            })
        }
        responseList.postValue(products)
        return responseList
    }

    fun getProduct(id: Int): ProductModel? {
        Timber.d("Product request for $id")

        products.forEach { product ->
            if (product.id == id) {
                return product.copy()
            }
        }
        return null
    }

    fun getBasket(): LiveData<ArrayList<ProductModel>> {
        return basket
        // Removed network request for more streamlined user feel for demo
    }

    fun getBasketPostponed(): LiveData<ArrayList<ProductModel>> {
        return postponed
        // Removed network request for more streamlined user feel for demo
    }

    fun addToBasket(id: Int): Boolean {
        return addToList(id, basket)
    }

    fun addToPostponed(id: Int): Boolean {
        return addToList(id, postponed)
    }

    fun moveToPostponed(id: Int): Boolean {
        return removeFromBasket(id) && addToPostponed(id)
    }

    fun moveToBasket(id: Int): Boolean {
        return removeFromPostponed(id) && addToBasket(id)
    }

    private fun addToList(id: Int, list: MutableLiveData<ArrayList<ProductModel>>): Boolean {

        val temp = list.value
        var added = false
        temp!!.forEach {
            if (it.id == id) {
                it.numbersInBasket++
                added = true
            }
        }
        if (!added) {
            getProduct(id)?.let { product ->
                product.numbersInBasket = 1
                temp.add(product)
            }
        }
        list.postValue(temp)
        return true

    }


    fun removeFromBasket(id: Int): Boolean {
        val temp = basket.value
        temp!!.forEach { product ->
            if (product.id == id) {
                temp.remove(product)
                basket.postValue(temp)
                return true
            }
        }
        return false
    }

    fun removeFromPostponed(id: Int): Boolean {
        val temp = postponed.value
        temp!!.forEach { product ->
            if (product.id == id) {
                temp.remove(product)
                postponed.postValue(temp)
                return true
            }
        }
        return false
    }

    fun getCategory(id: Int?, callback: (ArrayList<CategoryModel>) -> Unit) {
        val path: String
        if (id == null) {
            path = "$root/categories.json"
        } else {
            path = "$root$categories/$id.json"
        }
        Timber.d("Category request for $path")
        val request = Request.Builder()
            .url(path)
            .cacheControl(CACHE_POLICY)
            .build()

        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Timber.d("No response received")
            }

            override fun onResponse(call: Call, response: Response) {
                val str = response.body!!.string()
                val list: ArrayList<CategoryModel> = Gson().fromJson(str)
                callback(list)
            }
        })
    }

    fun login(email: String, password: String): Boolean {
        users.forEach { user ->
            if (user.email == email && user.password == password) {
                loggedInUser = user
                addresses.postValue(loggedInUser!!.addresses)
                basket.postValue(loggedInUser!!.basket)
                postponed.postValue(loggedInUser!!.postponed)
                return true
            }
        }

        return false
    }

    fun logout(): Boolean {
        Timber.d("User logged out.")
        if (!isLoggedIn()) {
            return false
        }

        loggedInUser!!.basket = basket.value!!
        loggedInUser!!.addresses = addresses.value!!
        loggedInUser!!.postponed = postponed.value!!
        addresses.postValue(ArrayList())
        basket.postValue(ArrayList())
        postponed.postValue(ArrayList())
        loggedInUser = null
        return true
    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }

    fun register(user: UserModel): Boolean {
        users.add(user)
        Timber.d(user.toString())
        return true
    }

    fun addAddress(address: AddressModel) {
        val temp = addresses.value
        var added = false

        if (temp!!.size == 0) {
            address.selected = true
        }

        for (addressModel in temp) {
            if (addressModel.id == address.id) {
                added = true
            }
        }

        if (!added) {
            temp.add(address)
            addresses.postValue(temp)
        }
    }

    fun getAddresses(): LiveData<ArrayList<AddressModel>> {
        return addresses
    }

    fun setDefaultAddress(id: String) {
        val addr = addresses.value
        addr!!.forEach { address ->
            address.selected = address.id == id
        }
        addresses.postValue(addr)
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)

}