package com.mockcommerce.modules.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository

class CheckoutViewModel(val appRepository: AppRepository) : ViewModel() {

    var addresses = appRepository.getAddresses()

    var productInBasket = appRepository.getBasket()

    var selectedBillingAddressId: String? = null

    var selectedShipmentAddressId: String? = null

    var basketTotal = MutableLiveData<Float>(7.0F)

    // Card information
    var paymentCardNumber: String? = null

    var paymentCardHolder: String? = null

    var paymentCardValidMonth: String? = null

    var paymentCardValidYear: String? = null

    var paymentCardCode: String? = null

    fun shipmentAddressSelected(id: String) {
        selectedShipmentAddressId = id
    }

    fun billingAddressSelected(id: String) {
        selectedBillingAddressId = id
    }
}