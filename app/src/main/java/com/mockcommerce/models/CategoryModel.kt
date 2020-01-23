package com.mockcommerce.models

data class CategoryModel (
    val id: String,
    val name: String,
    val image: String,
    val destinationCategoryId: String? = null,
    val destinationListId: String? = null
)