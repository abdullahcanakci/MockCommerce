package com.mockcommerce.modules.shared.product_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.models.ProductModel

class ProductViewModel : ViewModel() {
    val product = MutableLiveData<ProductModel>()

}
