package com.mockcommerce.utils

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


//https://proandroiddev.com/demystifying-retrofit-network-call-with-kotlin-livedata-27437439137a


class NetworkCall<T> {
    lateinit var call : Call<T>

    fun makeCall(call: Call<T>) : MutableLiveData<Resource<T>>{
        this.call = call
        val callback = CallbackKT<T>()
        callback.result.value = Resource.loading(null)
        this.call.clone().enqueue(callback)
        return callback.result
    }

    fun cancel() {
        if(::call.isInitialized){
            call.cancel()
        }
    }

    class CallbackKT<T> : Callback<T> {

        var result: MutableLiveData<Resource<T>> = MutableLiveData()

        override fun onFailure(call: Call<T>, t: Throwable) {
            result.value = Resource.error(ResourceError())
            Timber.d("Error while handling network call \n ${t.printStackTrace()}")
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if(response.isSuccessful){
                result.value = Resource.success(response.body())
            }
        }
    }
}