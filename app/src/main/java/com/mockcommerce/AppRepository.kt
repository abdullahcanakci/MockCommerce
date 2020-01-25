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
    private val images = "/images"
    private val categories = "/categories"


    private var addresses: MutableLiveData<ArrayList<AddressModel>>? = null
    private var products = ArrayList<ProductModel>()
    private var basket: MutableLiveData<ArrayList<ProductModel>>? = null
    private var postponed: MutableLiveData<ArrayList<ProductModel>>? = null

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
        if (basket == null) {
            basket = MutableLiveData()

            val request = Request.Builder()
                .url("$root/basket.json")
                .cacheControl(CACHE_POLICY)
                .build()

            val call = client.newCall(request)

            Timber.d("Requesting basket info")

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Timber.d("Basket request is unsuccessful")
                }

                override fun onResponse(call: Call, response: Response) {
                    val str = response.body!!.string()
                    val model: ArrayList<ProductModel> = Gson().fromJson(str)
                    basket!!.postValue(model)
                }
            })
        }
        return basket as LiveData<ArrayList<ProductModel>>
    }

    fun getBasketPostponed(): LiveData<ArrayList<ProductModel>> {
        if (postponed == null) {
            postponed = MutableLiveData()
            val request = Request.Builder()
                .url("$root/basket_postponed.json")
                .cacheControl(CACHE_POLICY)
                .build()

            val call = client.newCall(request)

            Timber.d("Requesting basket info")

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Timber.d("Basket request is unsuccessful")
                }

                override fun onResponse(call: Call, response: Response) {
                    val str = response.body!!.string()
                    val model: ArrayList<ProductModel> = Gson().fromJson(str)
                    postponed!!.postValue(model)
                }
            })
        }
        return postponed as LiveData<ArrayList<ProductModel>>
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

    private fun addToList(id: Int, list: MutableLiveData<ArrayList<ProductModel>>?): Boolean {
        list?.let {
            val temp = it.value
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
            it.postValue(temp)
            return true
        }
        return false
    }

    fun removeFromBasket(id: Int): Boolean {
        basket?.let {
            val temp = it.value
            temp!!.forEach { product ->
                if (product.id == id) {
                    temp.remove(product)
                    it.postValue(temp)
                    return true
                }
            }
        }
        return false
    }

    fun removeFromPostponed(id: Int): Boolean {
        postponed?.let {
            val temp = it.value
            temp!!.forEach { product ->
                if (product.id == id) {
                    temp.remove(product)
                    it.postValue(temp)
                    return true
                }
            }
        }
        return false
    }

    fun addToAddress(address: AddressModel): Boolean {
        addresses?.let {
            val temp = it.value
            var added = false
            temp!!.forEach {
                if (it.id == address.id) {
                    added = true
                }
            }
            if (!added) {
                temp.add(address)
            }

            it.postValue(temp)
        }
        return true
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

    fun savedLogin(): Boolean {
        return loggedInUser != null
    }

    fun login(email: String, password: String): Boolean {
        users.forEach { user ->
            if (user.email == email && user.password == password) {
                loggedInUser = user
                return true
            }
        }

        return false
    }

    fun logout(): Boolean {
        Timber.d("User logged out.")
        loggedInUser = null
        return true
    }

    fun register(user: UserModel): Boolean {
        users.add(user)
        Timber.d(user.toString())
        return true
    }

    fun addAddress(address: AddressModel) {
        loggedInUser?.addresses!!.add(address)
    }

    fun getUser(): UserModel? {
        return loggedInUser?.copy()
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)

}