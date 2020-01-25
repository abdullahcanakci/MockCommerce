package com.mockcommerce.modules.shared.newaddress

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.AddressModel
import java.util.*

class NewAddressViewModel(val appRepository: AppRepository) : ViewModel() {
    var name: String = ""
    var surname: String = ""
    var phone: String = ""

    var city: Int = 0
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
            city.toString(),
            district,
            false
        )

        appRepository.addAddress(addressModel)
    }
}
