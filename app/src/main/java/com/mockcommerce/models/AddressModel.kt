package com.mockcommerce.models

data class AddressModel(
    val id: String,
    val title: String,
    val address: String,
    val receiver: String,
    val phoneNumber: String,
    val city: String,
    val district: String,
    var selected: Boolean = false
)