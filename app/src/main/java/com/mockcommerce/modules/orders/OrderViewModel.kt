package com.mockcommerce.modules.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.AddressModel
import com.mockcommerce.models.OrderModel

class OrderViewModel(val appRepository: AppRepository) : ViewModel() {
    fun getOrder(id: String): LiveData<OrderModel> {
        return appRepository.getOrder(id)
    }

    fun getAddress(id: String): LiveData<AddressModel> {
        return appRepository.getAddress(id)
    }
}
