package com.mockcommerce.modules.account

import androidx.lifecycle.ViewModel
import com.mockcommerce.AppRepository
import com.mockcommerce.models.UserModel

class RegisterViewModel(val repository: AppRepository) : ViewModel() {

    var name: String = ""
    var surname: String = ""
    var password: String = ""
    var email: String = ""
    var phone: String = ""

    fun register() {
        repository.register(
            UserModel(
                name, surname, password, email, phone
            )
        )
    }
}