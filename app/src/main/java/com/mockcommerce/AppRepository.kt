package com.mockcommerce

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mockcommerce.models.CategoryModel
import com.mockcommerce.models.ProductModel
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class AppRepository(val client: OkHttpClient) {
    private val CACHE_POLICY = CacheControl.FORCE_NETWORK

    private val root = "https://raw.githubusercontent.com/abdullahcanakci/MockCommerce/master/mockserver"
    private val images = "/images"
    private val products = "/product"
    private val categories = "/categories"

    fun getProductList(callback: (ArrayList<ProductModel>) -> Unit) {
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
                val list : ArrayList<ProductModel> = Gson().fromJson(str)
                callback(list)
            }
        })
    }

    fun getProduct(id: Int, callback: (ProductModel) -> Unit){
        val request = Request.Builder()
            .url("$root$products/$id.json")
            .cacheControl(CACHE_POLICY)
            .build()

        val call = client.newCall(request)

        Timber.d("Product request for ${request.url}")

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Timber.d("No response received")
            }
            override fun onResponse(call: Call, response: Response) {
                val str = response.body!!.string()
                val model : ProductModel = Gson().fromJson(str)
                callback(model)
            }
        })
        return
    }

    fun getBasket(callback: (ArrayList<ProductModel>) -> Unit) {
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
                val model : ArrayList<ProductModel> = Gson().fromJson(str)
                callback(model)
            }
        })
        return
    }

    fun getBasketPostponed(callback: (ArrayList<ProductModel>) -> Unit) {
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
                val model : ArrayList<ProductModel> = Gson().fromJson(str)
                callback(model)
            }
        })
        return
    }

    fun getCategory(id: Int?, callback: (ArrayList<CategoryModel>) -> Unit) {
        var path: String
        if(id == null) {
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
                val list : ArrayList<CategoryModel> = Gson().fromJson(str)
                callback(list)
            }
        })
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

}