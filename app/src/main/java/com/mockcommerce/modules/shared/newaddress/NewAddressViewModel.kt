package com.mockcommerce.modules.shared.newaddress

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.AddressModel

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


    fun getAddress(): AddressModel {
        val addressModel = AddressModel(
            null,
            addressName,
            address,
            "$name $surname",
            phone,
            city,
            district,
            false
        )

        return addressModel
    }
}
