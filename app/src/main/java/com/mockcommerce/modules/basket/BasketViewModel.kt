package com.mockcommerce.modules.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class BasketViewModel(val appRepository: AppRepository) : ViewModel() {
    val basketItems: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())

    var postponedItems: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())

    var basketTotal: MutableLiveData<String> = MutableLiveData("0 TL")

    init {
        appRepository.getBasket {
            basketItems.postValue(it)
            calculateTotal(it)
        }
        appRepository.getBasketPostponed {
            postponedItems.postValue(it)
        }
    }

    fun calculateTotal(products: ArrayList<ProductModel>) {
        var total: Float = 0.0F

        products.forEach {
            total += it.numbersInBasket * it.price
        }

        basketTotal.postValue("Tutar %.2f TL".format(total))
    }

    fun removeFromPostPoned(product: ProductModel) {
        val temp = postponedItems.value
        temp!!.remove(product)
        postponedItems.postValue(temp)
    }

    fun removeFromBasket(product: ProductModel) {
        val temp = basketItems.value
        temp!!.remove(product)
        basketItems.postValue(temp)
        calculateTotal(temp)
    }

    fun postpone(product: ProductModel) {
        val temp = postponedItems.value
        product.numbersInBasket = 1
        temp!!.add(product)
        postponedItems.postValue(temp)
        removeFromBasket(product)
    }

    fun addToBasket(product: ProductModel) {
        val temp = basketItems.value
        product.numbersInBasket = 1
        temp!!.add(product)
        basketItems.postValue(temp)
        removeFromPostPoned(product)
        calculateTotal(temp)
    }

    fun add(product: ProductModel) {
        val temp = basketItems.value
        val index = temp!!.indexOf(product)
        product.numbersInBasket += 1
        temp[index] = product
        basketItems.postValue(temp)
        calculateTotal(temp)
    }

    fun substract(product: ProductModel) {
        val temp = basketItems.value
        val index = temp!!.indexOf(product)
        product.numbersInBasket =
            if (product.numbersInBasket == 1) 1 else product.numbersInBasket - 1
        temp[index] = product
        basketItems.postValue(temp)
        calculateTotal(temp)
    }

}
