package com.mockcommerce.modules.account

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository

class AddressesViewModel(val repository: AppRepository) : ViewModel() {
    val addresses = repository.getAddresses()

    fun addressSelected(id: String) {
        repository.setDefaultAddress(id)
    }

}
