package com.mockcommerce.models

import android.view.View

open class ProductModel(
    val id: Int,
    val name: String,
    val price: Float,
    val oldPrice: Float? = null,
    var numbersInBasket: Int = 0
) {

    fun getPrice(): String {
        return price.toString()
    }

    fun getOldPrice(): String {
        return oldPrice.toString()
    }

    fun getDiscountVisibility(): Int {
        if (oldPrice != null)
            return View.VISIBLE
        return View.GONE
    }

    fun getDiscountPercent(): Int {
        if (oldPrice != null)
            return View.VISIBLE
        return View.INVISIBLE
    }

    fun getDiscount(): String {
        if (oldPrice == null)
            return "0"

        return "%" + ((1 - (price / oldPrice)) * 100).toInt().toString()
    }

}