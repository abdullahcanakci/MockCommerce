package com.mockcommerce.models

data class UserModel(
    val name: String,
    val surname: String,
    val password: String,
    val email: String,
    val phone: String,
    var addresses: ArrayList<AddressModel> = ArrayList(),
    var basket: ArrayList<ProductModel> = ArrayList(),
    var postponed: ArrayList<ProductModel> = ArrayList()
)