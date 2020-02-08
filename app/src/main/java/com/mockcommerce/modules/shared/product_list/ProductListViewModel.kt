package com.mockcommerce.modules.shared.product_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.models.ProductModel

class ProductListViewModel : ViewModel() {
    val products = MutableLiveData<List<ProductModel>>()

    var page = 1
}
