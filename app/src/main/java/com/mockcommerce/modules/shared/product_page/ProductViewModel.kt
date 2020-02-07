package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class ProductViewModel(val appRepository: AppRepository) : ViewModel() {

    var favourite: Boolean = false
    var productId: String? = ""


    fun addToBasket() {
        appRepository.addToBasket(productId)
    }

    fun getProduct(id: String?): LiveData<ProductModel>{
        id?.let{
            return appRepository.getProduct(id)
        }
        return appRepository.getProduct(productId!!)
    }

    fun toggleFavourite() {
        favourite != favourite
    }
}
