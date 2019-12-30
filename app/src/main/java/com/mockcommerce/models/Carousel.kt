package com.mockcommerce.models

data class Carousel (
    val title: String,
    var seeMore: Boolean = false,
    var items: List<CarouselItem>
)