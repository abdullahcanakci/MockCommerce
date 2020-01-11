package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class ProductViewModel(val appRepository: AppRepository) : ViewModel() {
    val product = MutableLiveData<ProductModel>()
    var productId: Int = 0
        set(value) {
            appRepository.getProduct(value) {
                product.postValue(it)
            }
        }

}
