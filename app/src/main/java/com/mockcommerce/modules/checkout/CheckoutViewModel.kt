package com.mockcommerce.modules.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    var basketTotal = MutableLiveData<Float>(0.0F)

    var shipmentAddresses = MutableLiveData<ArrayList<ShipmentAddressModel>>(
        ArrayList(
            listOf(
                ShipmentAddressModel(
                    "1",
                    "title 1",
                    "address 1",
                    "receiver 1",
                    "111111111",
                    "city 1",
                    "district 1",
                    true
                ),
                ShipmentAddressModel(
                    "2",
                    "title 2",
                    "address 2",
                    "receiver 2",
                    "222222222",
                    "city 2",
                    "district 2",
                    false
                ),
                ShipmentAddressModel(
                    "3",
                    "title 3",
                    "address 3",
                    "receiver 3",
                    "333333333",
                    "city 3",
                    "district 3",
                    false
                )
            )
        )
    )

    var shipmentAddressesCollapsed = MutableLiveData<Boolean>(true)


    var billingAddresses = MutableLiveData<ArrayList<ShipmentAddressModel>>(
        ArrayList(
            listOf(
                ShipmentAddressModel(
                    "1",
                    "title 1",
                    "address 1",
                    "receiver 1",
                    "111111111",
                    "city 1",
                    "district 1",
                    true
                ),
                ShipmentAddressModel(
                    "2",
                    "title 2",
                    "address 2",
                    "receiver 2",
                    "222222222",
                    "city 2",
                    "district 2",
                    false
                ),
                ShipmentAddressModel(
                    "3",
                    "title 3",
                    "address 3",
                    "receiver 3",
                    "333333333",
                    "city 3",
                    "district 3",
                    false
                )
            )
        )
    )


    var billingAddressesCollapsed = MutableLiveData<Boolean>(true)

    var paymentCardNumber: String? = null

    var paymentCardHolder: String? = null

    var paymentCardValidMonth: String? = null

    var paymentCardValidYear: String? = null

    var paymentCardCode: String? = null

    var selectedBillingAddressId: String? = null

    var selectedShipmentAddressId: String? = null

    fun getProducts() {
        productInBasket = appRepository.getBasket() as MutableLiveData<ArrayList<ProductModel>>
        Transformations.map(
            productInBasket
        ) {
            var temp = 0.0F
            for (productModel in it) {
                temp += productModel.numbersInBasket * productModel.price
            }
            basketTotal.postValue(temp)
        }
    }

    fun getAddresses() {

    }

    fun shipmentAddressSelected(id: String) {
        selectedShipmentAddressId = id
        val collapsed = shipmentAddressesCollapsed.value
        val addresses = shipmentAddresses.value
        collapsed!!
        addresses!!

        addresses.forEach {
            if (it.id != id) {
                it.selected = false
            } else {
                if (!it.selected) {
                    it.selected = true
                }
            }

        }

        shipmentAddressesCollapsed.postValue(true)
        shipmentAddresses.postValue(addresses)
    }


    fun billingAddressSelected(id: String) {
        selectedBillingAddressId = id
        val collapsed = billingAddressesCollapsed.value
        val addresses = billingAddresses.value
        collapsed!!
        addresses!!

        addresses.forEach {
            if (it.id != id) {
                it.selected = false
            } else {
                if (!it.selected) {
                    it.selected = true
                }
            }

        }

        billingAddressesCollapsed.postValue(true)
        billingAddresses.postValue(addresses)
    }
}