package com.mockcommerce.modules.shared.newaddress

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.AddressModel
import java.util.*

class NewAddressViewModel(val appRepository: AppRepository) : ViewModel() {
    var name: String = ""
    var surname: String = ""
    var phone: String = ""

    var selectedCityIndex = 0
    var city: String = ""
    var district: String = ""

    var address: String = ""
    var addressName: String = ""

    var isBilling = false


    fun saveAddress() {
        val addressModel = AddressModel(
            UUID.randomUUID().toString(),
            addressName,
            address,
            "$name $surname",
            phone,
            city,
            district,
            false
        )

        appRepository.addAddress(addressModel)
    }
}
