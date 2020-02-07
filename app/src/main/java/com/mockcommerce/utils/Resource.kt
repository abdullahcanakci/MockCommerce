package com.mockcommerce.utils

//https://proandroiddev.com/demystifying-retrofit-network-call-with-kotlin-livedata-27437439137a

class Resource<T> private constructor(
    val status: Resource.Status,
    val data: T?,
    val resourceError: ResourceError?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    fun isSuccess(): Boolean {
        return status == Status.SUCCESS
    }

    companion object{
        fun <T> success(data: T?) : Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(resourceError: ResourceError?) : Resource<T> {
            return Resource(Status.ERROR, null, resourceError)
        }
        fun <T> loading(data: T?) : Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}