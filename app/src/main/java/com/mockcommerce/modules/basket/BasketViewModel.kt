package com.mockcommerce.modules.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class BasketViewModel(val appRepository: AppRepository) : ViewModel() {
    val basketItems: LiveData<ArrayList<ProductModel>> = appRepository.getBasket()

    var postponedItems: LiveData<ArrayList<ProductModel>> = appRepository.getBasketPostponed()

    var basketTotal = appRepository.basketTotal

    fun removeFromPostponed(product: ProductModel) {
        appRepository.removeFromPostponed(product.id)
    }

    fun removeFromBasket(product: ProductModel) {
        appRepository.removeFromBasket(product.id)
    }

    fun postpone(product: ProductModel) {
        appRepository.moveToPostponed(product.id)
    }

    fun addToBasket(product: ProductModel) {
        appRepository.moveToBasket(product.id)
    }

    fun add(product: ProductModel) {
        appRepository.addToBasket(product.id)
    }

    fun subtract(product: ProductModel) {
        appRepository.subtract(product.id)
    }

    fun isLoggedIn(): Boolean {
        return appRepository.isLoggedIn()
    }

    fun isBasketPopulated(): Boolean {
        return basketItems.value!!.size > 0

    }

}
