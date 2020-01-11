package com.mockcommerce.modules.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.models.ProductModel

class BasketViewModel : ViewModel() {
    val basket_items: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList(listOf(
        )))

    var postponed_items: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())
}
