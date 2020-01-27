package com.mockcommerce.models

data class OrderModel(
    val id: String,
    val date: Long,
    val total: Float,
    val paymentType: String,
    val status: String,
    val products: ArrayList<ProductModel>
)