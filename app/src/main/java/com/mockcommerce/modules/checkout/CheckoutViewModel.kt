package com.mockcommerce.modules.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.ProductModel
import com.mockcommerce.models.ShipmentAddressModel

class CheckoutViewModel(val appRepository: AppRepository) : ViewModel() {
    var userId: String = ""
        set(value) {
            field = value
            getProducts()
            getAddresses()
        }

    var productInBasket = MutableLiveData<ArrayList<ProductModel>>(ArrayList())

    var shipmentAddresses = MutableLiveData<ArrayList<ShipmentAddressModel>>(ArrayList())

    var selectedShipmentAddressId: ShipmentAddressModel? = null

    var selectedBillingAddressId: ShipmentAddressModel? = null

    var paymentCardNumber: String? = null

    var paymentCardHolder: String? = null

    var paymentCardValidMonth: String? = null

    var paymentCardValidYear: String? = null

    var paymentCardCode: String? = null

    fun getProducts() {

    }

    fun getAddresses() {

    }
}