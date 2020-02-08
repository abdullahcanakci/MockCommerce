package com.mockcommerce.modules.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class BasketViewModel(val appRepository: AppRepository) : ViewModel() {
    val basketItems = MutableLiveData<List<ProductModel>>()

    var postponedItems = MutableLiveData<List<ProductModel>>()

    var basketTotal = appRepository.basketTotal

    fun removeFromPostponed(product: ProductModel) {
        val tempPostponed = postponedItems.value as ArrayList

        tempPostponed.remove(product)

        postponedItems.postValue(tempPostponed)
    }

    fun removeFromBasket(product: ProductModel) {
        val tempBasket = basketItems.value as ArrayList

        tempBasket.remove(product)

        basketItems.postValue(tempBasket)
    }

    fun postpone(product: ProductModel) {
        val tempPostponed = postponedItems.value as ArrayList
        val tempBasket = basketItems.value as ArrayList
        tempPostponed.add(product)
        tempBasket.remove(product)
        postponedItems.postValue(tempPostponed)
        basketItems.postValue(tempBasket)
    }

    fun addToBasket(product: ProductModel) {
        val tempPostponed = postponedItems.value as ArrayList
        val tempBasket = basketItems.value as ArrayList
        tempPostponed.remove(product)
        tempBasket.add(product)
        postponedItems.postValue(tempPostponed)
        basketItems.postValue(tempBasket)
    }

    fun add(product: ProductModel) {
        val tempBasket = basketItems.value as ArrayList

        tempBasket.forEach {
            if (it.id == product.id) {
                it.amount++
            }
        }

        basketItems.postValue(tempBasket)
    }

    fun subtract(product: ProductModel) {
        val tempBasket = basketItems.value as ArrayList

        tempBasket.forEach {
            if (it.id == product.id && it.amount > 1) {
                it.amount--
            }
        }

        basketItems.postValue(tempBasket)
    }

    fun isLoggedIn(): Boolean {
        return appRepository.isLoggedIn()
    }

    fun isBasketPopulated(): Boolean {
        return basketItems.value!!.isNotEmpty()

    }

}
