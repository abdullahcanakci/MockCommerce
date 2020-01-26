package com.mockcommerce.modules.account

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository

class AccountViewModel(val repository: AppRepository) : ViewModel() {
    fun logout() {
        repository.logout()
    }
}
