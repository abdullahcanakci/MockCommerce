package com.mockcommerce.models

data class CardModel(
    var number: String,
    var holder: String,
    var dueMonth: Int,
    var dueYear: Int,
    var securityCode: String
)