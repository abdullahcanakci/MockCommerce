package com.mockcommerce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mockcommerce.models.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class AppRepository(val client: OkHttpClient) {
    private val CACHE_POLICY = CacheControl.FORCE_NETWORK

    private val root =
        "https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver"
    private val categories = "/categories"

    private var products = ArrayList<ProductModel>()

    var addresses = MutableLiveData<ArrayList<AddressModel>>(ArrayList())
    var basket = MutableLiveData<ArrayList<ProductModel>>(ArrayList())
    var postponed = MutableLiveData<ArrayList<ProductModel>>(ArrayList())
    var orders = MutableLiveData<ArrayList<OrderModel>>(ArrayList())
    var favourites = MutableLiveData<ArrayList<Int>>(ArrayList())
    var basketTotal = MutableLiveData<Float>(0.0F)

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
                val temp = product.copy()
                temp.favourite = false
                if (favourites.value!!.contains(id)) {
                    temp.favourite = true
                }
                return temp
            }
        }
        return null
    }

    fun getFavouriteProducts(): LiveData<ArrayList<ProductModel>> {
        val temp = ArrayList<ProductModel>()
        loggedInUser?.favorites!!.forEach { productId ->
            getProduct(productId)?.let { temp.add(it) }
        }
        return MutableLiveData(temp)
    }

    fun setFavourite(productId: Int, addToFav: Boolean) {
        val temp = favourites.value!!
        if (addToFav) {
            if (!temp.contains(productId)) {
                temp.add(productId)
            }
        } else {
            if (temp.contains(productId)) {
                temp.remove(productId)
            }
        }
        Timber.d("Number of items in favourite is ${temp.size}, Product id is $productId")
        favourites.value = temp
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
        val r = addToList(id, basket)
        updateBasketTotal()
        return r
    }

    fun subtract(id: Int) {
        val temp = basket.value
        temp!!.forEach {
            if (it.id == id) {
                if (it.numbersInBasket > 1) {
                    it.numbersInBasket--
                    basket.postValue(temp)
                    updateBasketTotal()
                    return
                }
            }
        }
    }

    fun addToPostponed(id: Int): Boolean {
        return addToList(id, postponed)
    }

    fun moveToPostponed(id: Int): Boolean {
        updateBasketTotal()
        return removeFromBasket(id) && addToPostponed(id)
    }

    fun moveToBasket(id: Int): Boolean {
        updateBasketTotal()
        return removeFromPostponed(id) && addToBasket(id)
    }

    fun updateBasketTotal() {
        var temp = 0.0F

        basket.value!!.forEach {
            temp += it.price * it.numbersInBasket
        }

        Timber.d("Basket value is $temp")

        basketTotal.postValue(temp)
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
                updateBasketTotal()
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

    fun confirmPayment(shipmentId: String, billingId: String) {
        val temp = orders.value
        val order = OrderModel(
            UUID.randomUUID().toString(),
            Date().time,
            basketTotal.value!!,
            "Kredi Kartı",
            "Hazırlanıyor",
            shipmentId,
            billingId,
            basket.value!!
        )
        Timber.d("Order is " + order.toString())
        temp!!.add(order)
        orders.postValue(temp)
        basket.postValue(ArrayList())
        updateBasketTotal()
    }

    fun login(email: String, password: String): Boolean {
        users.forEach { user ->
            if (user.email == email && user.password == password) {
                loggedInUser = user
                addresses.postValue(loggedInUser!!.addresses)
                val tempBasket = loggedInUser!!.basket
                tempBasket.addAll(basket.value!!)
                basket.postValue(tempBasket)
                val tempPostpone = loggedInUser!!.postponed
                tempPostpone.addAll(postponed.value!!)
                postponed.postValue(tempPostpone)
                orders.postValue(loggedInUser!!.orders)
                val tempFavourites = loggedInUser!!.favorites
                tempFavourites.addAll(favourites.value!!)
                favourites.postValue(tempFavourites)
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
        loggedInUser!!.orders = orders.value!!
        loggedInUser!!.favorites = favourites.value!!
        addresses.postValue(ArrayList())
        basket.postValue(ArrayList())
        postponed.postValue(ArrayList())
        orders.postValue(ArrayList())
        favourites.postValue(ArrayList())
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

    fun getAddress(id: String): LiveData<AddressModel> {
        val response = MutableLiveData<AddressModel>()

        addresses.value!!.forEach { address ->
            if (address.id == id) {
                response.postValue(address.copy())
            }
        }

        return response

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

    fun getOrder(id: String): LiveData<OrderModel> {
        val response = MutableLiveData<OrderModel>()

        orders.value!!.forEach { order ->
            if (order.id == id) {
                response.postValue(order)
            }
        }

        return response
    }

}