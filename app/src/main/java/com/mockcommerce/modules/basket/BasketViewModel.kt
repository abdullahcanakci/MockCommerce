package com.mockcommerce.modules.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel
import timber.log.Timber

class BasketViewModel(val appRepository: AppRepository) : ViewModel() {
    val basketItems: LiveData<ArrayList<ProductModel>> = appRepository.getBasket()

    var postponedItems: LiveData<ArrayList<ProductModel>> = appRepository.getBasketPostponed()

    var basketTotal: MutableLiveData<Float> = MutableLiveData(0.0F)

    init {
        Transformations.map(
            basketItems
        ) {
            var temp = 0.0F
            for (productModel in it) {
                temp += productModel.numbersInBasket * productModel.price
            }
            Timber.d("Basket total $temp")
            basketTotal.postValue(temp)
        }
    }

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
        Timber.d("Subtract not implemented")
    }

    fun isLoggedIn(): Boolean {
        return appRepository.isLoggedIn()
    }

}
