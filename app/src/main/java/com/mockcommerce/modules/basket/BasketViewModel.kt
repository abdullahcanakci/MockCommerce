package com.mockcommerce.modules.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel

class BasketViewModel(val appRepository: AppRepository) : ViewModel() {
    val basketItems: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())

    var postponedItems: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())

    init {
        appRepository.getBasket {
            basketItems.postValue(it)
        }
        appRepository.getBasketPostponed {
            postponedItems.postValue(it)
        }
    }
}
