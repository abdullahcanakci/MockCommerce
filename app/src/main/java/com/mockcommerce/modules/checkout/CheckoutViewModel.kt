package com.mockcommerce.modules.checkout

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.CardModel
import timber.log.Timber

class CheckoutViewModel(val appRepository: AppRepository) : ViewModel() {

    var selectedBillingAddressId: String? = null

    var selectedShipmentAddressId: String? = null

    var basketTotal = appRepository.basketTotal

    val cardModel = CardModel("", "", 0, 0, "")

    fun shipmentAddressSelected(id: String) {
        selectedShipmentAddressId = id
    }

    fun billingAddressSelected(id: String) {
        selectedBillingAddressId = id
    }

    fun completePayment() {
        Timber.d("Shipment $selectedShipmentAddressId Billing $selectedBillingAddressId")
        appRepository.confirmPayment(selectedShipmentAddressId!!, selectedBillingAddressId!!)
    }
}