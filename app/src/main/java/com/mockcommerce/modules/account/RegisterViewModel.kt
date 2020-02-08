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


    fun getUser(): UserModel {
        return UserModel(name, surname, password, email, phone)
    }

    fun isUserValid(): Boolean {
        return name.isEmpty() && surname.isEmpty() && password.isEmpty() && email.isEmpty() && phone.isEmpty()
    }
}