package com.mockcommerce.modules.shared.product_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class ProductListViewModel(private val appRepository: AppRepository) : ViewModel() {
    val productList = MutableLiveData<ArrayList<ProductModel>>()

    init {
        appRepository.getProductList {
            productList.postValue(it)
        }
    }
}
