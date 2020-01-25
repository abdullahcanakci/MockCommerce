package com.mockcommerce.modules.account

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository

class AccountViewModel(val repository: AppRepository) : ViewModel() {
    val user = repository.getUser()

    fun logout() {
        repository.logout()
    }
}
