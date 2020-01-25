package com.mockcommerce.models

data class UserModel(
    val name: String,
    val surname: String,
    val password: String,
    val email: String,
    val phone: String,
    val addresses: ArrayList<AddressModel> = ArrayList()
)