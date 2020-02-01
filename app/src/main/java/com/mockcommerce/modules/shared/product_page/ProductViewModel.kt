package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class ProductViewModel(val appRepository: AppRepository) : ViewModel() {

    var favourite: Boolean = false
    val product = MutableLiveData<ProductModel>()
    var productId: Int = 0
        set(value) {
            field = value
            updateProduct()
        }

    fun addToBasket() {
        appRepository.addToBasket(productId)
    }

    fun toggleFavourite() {
        appRepository.setFavourite(productId, !favourite)
        updateProduct()
    }

    private fun updateProduct() {
        product.postValue(appRepository.getProduct(productId))
    }
}
