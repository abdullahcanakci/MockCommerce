package com.mockcommerce.models

data class CommentModel(
    val id: Int,
    val author: String,
    val points: Int,
    val date: Long,
    val title: String,
    val comment: String
)