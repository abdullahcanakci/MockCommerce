package com.mockcommerce.models

data class CarouselModel (
    val title: String,
    var seeMore: Boolean = false,
    var items: List<CarouselItem>
)